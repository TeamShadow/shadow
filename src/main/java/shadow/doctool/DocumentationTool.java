package shadow.doctool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Logger;

import shadow.Arguments;
import shadow.Configuration;
import shadow.ConfigurationException;
import shadow.Loggers;
import shadow.Main;
import shadow.doctool.output.DocumentationTemplate;
import shadow.doctool.output.StandardTemplate;
import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.Package;
import shadow.typecheck.TypeCheckException;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.Type;

public class DocumentationTool 
{
	private static final String SRC_EXTENSION = ".shadow";
	private static final String PKG_INFO_FILE = "package-info.txt";
	
	private static Logger logger = Loggers.DOC_TOOL;
	
	public static void main(String[] args)
	{
		try {
			document(args);
		} catch(FileNotFoundException e) {
			System.err.println("FILE NOT FOUND: " + e.getLocalizedMessage());
			System.exit(Main.FILE_NOT_FOUND_ERROR);
		} catch(ParseException e) {
			System.err.println("PARSE ERROR: " + e.getLocalizedMessage());
			System.exit(Main.PARSE_ERROR);
		} catch (ShadowException e) {
			System.err.println("ERROR IN FILE: " + e.getLocalizedMessage());
			e.printStackTrace();
			System.exit(Main.TYPE_CHECK_ERROR);
		} catch (IOException e) {
			System.err.println("FILE DEPENDENCY ERROR: " + e.getLocalizedMessage());
			e.printStackTrace();
			System.exit(Main.TYPE_CHECK_ERROR);
		} catch (org.apache.commons.cli.ParseException e) {
			System.err.println("COMMAND LINE ERROR: " + e.getLocalizedMessage());
			DocumentationArguments.printHelp();
			System.exit(Main.COMMAND_LINE_ERROR);
		} catch (ConfigurationException e) {
			System.err.println("CONFIGURATION ERROR: " + e.getLocalizedMessage());
			DocumentationArguments.printHelp();
			System.exit(Main.CONFIGURATION_ERROR);
		} catch (TypeCheckException e) {
			System.err.println("TYPE CHECK ERROR: " + e.getLocalizedMessage());
			System.exit(Main.TYPE_CHECK_ERROR);
		} catch (DocumentationException e) {
			System.err.println("DOCUMENTATION ERROR: " + e.getLocalizedMessage());
			e.printStackTrace();
			System.exit(-1);
		} catch (HelpRequestedException e) {
			return;
		}
	}
	
	// TODO: Subdivide this into more manageable pieces
	public static void document(String[] args) 
			throws org.apache.commons.cli.ParseException, ConfigurationException,
			IOException, ShadowException, TypeCheckException, ParseException, 
			DocumentationException, HelpRequestedException
	{
		// Detect and establish the current settings and arguments
		DocumentationArguments arguments = new DocumentationArguments(args);
		
		// Exit if help was requested (Arguments handles printing)
		if (arguments.hasOption(Arguments.HELP))
			return;
		
		Configuration.buildConfiguration(arguments.getMainArguments()[0],
				arguments.getConfigFileArg(), false);
		
		/* TYPECHECKING */
		
		long startTime = System.currentTimeMillis(); // Time the type checking
		
		// Generate a list of source files from the command line arguments.
		// If packages/directories are specified, they will be searched for
		// source files
		Map<String, Documentation> pkgDocs = new HashMap<String, Documentation>();
		List<File> sourceFiles 
				= getRequestedFiles(arguments.getMainArguments(), pkgDocs);
		
		// Perform basic type-checking on each source file
		Set<Type> typesToDocument = DocumentationTypeChecker.typeCheck(sourceFiles);
		Set<Package> packagesToDocument = new HashSet<Package>();
		
		logger.info("Successfully type-checked all files in "
				+ (System.currentTimeMillis() - startTime) + "ms");
		
		/* DOCUMENTATION INFO ORGANIZATION */
		
		startTime = System.currentTimeMillis(); // Time the documentation
		
		// If a directory was provided use it. Otherwise, create docs/ in the
		// current working directory
		Path outputDirectory;
		if (arguments.hasOption(DocumentationArguments.OUTPUT_DIR))
			outputDirectory = Paths.get(arguments.getOutputDirectory())
			.toAbsolutePath();
		else
			outputDirectory = Paths.get("docs").toAbsolutePath();
		
		// Capture visible inner classes for documentation
		List<Type> outerClasses = new ArrayList<Type>(typesToDocument);
		for (Type outer : outerClasses)
			if (outer instanceof ClassType)
				for (Type inner : ((ClassType)outer).getInnerClasses().values())
					if (inner.getModifiers().isPublic() || inner.getModifiers().isProtected())
						typesToDocument.add(inner);
		
		// Capture all packages of classes being documented
		for (Type type : typesToDocument)
			packagesToDocument.addAll(type.getAllPackages());
		
		// Associate packages with package-info files
		for (Package pkg : packagesToDocument) {
			String pkgName = pkg.getQualifiedName();
			// The default package cannot be documented
			if (!pkgName.isEmpty() && pkgDocs.containsKey(pkgName))
				pkg.setDocumentation(pkgDocs.get(pkgName));
		}
		
		/* FORMATTED DOCUMENTATION GENERATION */
			
		DocumentationTemplate template 
			= new StandardTemplate(arguments.getTemplateArgs(), typesToDocument,
					packagesToDocument);
		template.write(outputDirectory);
		
		logger.info("Successfully generated all documentation in "
				+ (System.currentTimeMillis() - startTime) + "ms");
	}
	
	/**
	 * Locates all requested source files, either specified directly or within
	 * specified packages/directories. Verifies that the files actually exist
	 */
	public static List<File> getRequestedFiles(String[] givenPaths,
			Map<String, Documentation> pkgDocs) throws IOException, 
			DocumentationException, ShadowException
	{
		List<File> sourceFiles = new ArrayList<File>();
		for (String path : givenPaths) {
			Path current = Paths.get(path).toAbsolutePath();
			
			// Ensure that the source file exists
			if (!Files.exists(current))
				throw new FileNotFoundException("File at " 
						+ current.toAbsolutePath() + " not found");
			
			// If the file is a directory, process it as a package
			if (Files.isDirectory(current))
				sourceFiles.addAll(getPackageFiles(current, true, pkgDocs));
			else if (current.toString().endsWith(SRC_EXTENSION))
				sourceFiles.add(current.toFile());
			else if (current.getFileName().toString().equals(PKG_INFO_FILE))
				processPackageInfo(current, pkgDocs);
			else
				// Only do this for explicitly requested files
				throw new DocumentationException("File at " 
						+ current.toAbsolutePath() + " is not a package "
						+ "directory, " + PKG_INFO_FILE + " file, or a source "
						+ "file ending in " + SRC_EXTENSION);
		}
		return sourceFiles;
	}
	
	/** 
	 * Finds all Shadow source files within a directory (a package)
	 * 
	 * @param recursive Determines whether or not subdirectories/subpackages
	 * 					are also searched
	 */
	public static List<File> getPackageFiles(Path directory, boolean recursive,
			Map<String, Documentation> pkgDocs) throws IOException,
			DocumentationException, ShadowException
	{
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
			List<File> files = new ArrayList<File>();
			
			for (Path filePath : stream) {
				// Capture all source files
				if (filePath.toString().endsWith(SRC_EXTENSION)) 
					files.add(filePath.toFile());
				// Capture any package-info files
				else if (filePath.getFileName().toString().equals(PKG_INFO_FILE))
					processPackageInfo(filePath, pkgDocs);
				// Recurse into subdirectories if desired
				else if (recursive && Files.isDirectory(filePath))
					files.addAll(getPackageFiles(filePath, true, pkgDocs));
			}
			return files;
		}
	}
	
	private static final Pattern pkgDeclaration = 
			Pattern.compile("package[ \\t\\f]+([a-zA-Z][a-zA-Z0-9_]*(:[a-zA-Z][a-zA-Z0-9_]*)*);\\s*$");
	
	/** 
	 * Captures the documentation from a package-info file and places it into
	 * a central map for later use
	 */
	private static void processPackageInfo(Path infoFile, 
			Map<String, Documentation> pkgDocs) throws IOException, 
			DocumentationException, ShadowException
	{
		/* Get the contents of the file */
		BufferedReader info 
				= Files.newBufferedReader(infoFile, StandardCharsets.UTF_8);
		String declarationLine = null;
		DocumentationBuilder docBuilder = new DocumentationBuilder();
		while (info.ready()) {
			String line = info.readLine();
			if (info.ready()) // Capture all but the last line as comment text
				docBuilder.appendLine(line);
			else
				declarationLine = line; // The last line is the declaration
		}
		if (declarationLine == null)
			throw new DocumentationException("No lines in file: " 
					+ infoFile.toAbsolutePath());
		
		/* Parse the package declaration and doc comment */
		Matcher matcher = pkgDeclaration.matcher(declarationLine);
		if (!matcher.find() || matcher.start() != 0)
			throw new DocumentationException("No valid package declaration on"
					+ " last line: " + infoFile.toAbsolutePath());
		String pkgName = matcher.group(1);
		if (!insideValidDirectory(pkgName, infoFile))
			throw new DocumentationException("Declaration of package "
					+ pkgName + " does not match file path: " 
					+ infoFile.toAbsolutePath());
		
		/* Add the package documentation to the central map */
		if (pkgDocs.containsKey(pkgName))
			logger.warn("Already found a package-info file for " + pkgName
					+ ", ignoring: " + infoFile.toAbsolutePath());
		pkgDocs.put(pkgName, docBuilder.process());
	}
	
	/** Determines whether or not a package-info file is in a valid directory */
	private static boolean insideValidDirectory(String pkgName, Path infoFile)
	{
		String[] packages = pkgName.split(":");
		Path current = infoFile.getParent();
		for (int i = packages.length - 1; i >= 0; --i) {
			if (current != null 
					&& !current.getFileName().toString().equals(packages[i]))
				return false;
			current = current.getParent();
		}
		return true;
	}
}
