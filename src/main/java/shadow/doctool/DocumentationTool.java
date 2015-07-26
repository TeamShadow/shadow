package shadow.doctool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		}
	}
	
	public static void document(String[] args) throws org.apache.commons.cli.ParseException, ConfigurationException, IOException, ShadowException, TypeCheckException, ParseException, DocumentationException
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
		List<File> sourceFiles = getRequestedFiles(arguments.getMainArguments());
		
		// Perform basic type-checking on each source file
		Set<Type> typesToDocument = DocumentationTypeChecker.typeCheck(sourceFiles);
		Set<Package> packagesToDocument = new HashSet<Package>();
		
		logger.info("Successfully type-checked all files in "
				+ (System.currentTimeMillis() - startTime) + "ms");
		
		/* FORMATTED DOCUMENTATION GENERATION */
		
		startTime = System.currentTimeMillis(); // Time the documentation
		
		// If a directory was provided use it. Otherwise, create docs/ in the
		// current working directory
		Path outputDirectory;
		if (arguments.hasOption(DocumentationArguments.OUTPUT))
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
		
		DocumentationTemplate template 
				= new StandardTemplate(typesToDocument, packagesToDocument);
		template.write(outputDirectory);
		
		logger.info("Successfully generated all documentation in "
				+ (System.currentTimeMillis() - startTime) + "ms");
	}
	
	/**
	 * Locates all given source files, either specified directly or within 
	 * packages/directories. Verifies that the files actually exist
	 */
	public static List<File> getRequestedFiles(String[] givenPaths) throws IOException
	{
		List<File> sourceFiles = new ArrayList<File>();
		for (String path : givenPaths) {
			Path current = Paths.get(path).toAbsolutePath();
			
			// Ensure that the source file exists
			if (!Files.exists(current))
				throw new FileNotFoundException("File at " + current.toAbsolutePath() + " not found");
			
			// If the file is a directory, process it as a package
			if (Files.isDirectory(current))
				sourceFiles.addAll(getPackageFiles(current, true));
			else
				sourceFiles.add(current.toFile());
		}
		return sourceFiles;
	}
	
	/** 
	 * Finds all Shadow source files within a directory (a package)
	 * 
	 * @param recursive Determines whether or not subdirectories/subpackages
	 * 					are also searched
	 */
	public static List<File> getPackageFiles(Path directory, boolean recursive) throws IOException
	{
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
			List<File> files = new ArrayList<File>();
			
			for (Path filePath : stream) {
				// Capture all source files
				if (filePath.toString().endsWith(".shadow"))
					files.add(filePath.toFile());
				// Recurse into subdirectories if desired
				else if (recursive && Files.isDirectory(filePath))
					files.addAll(getPackageFiles(filePath, true));
			}
			
			return files;
		}
	}
}
