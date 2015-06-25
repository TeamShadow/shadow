package shadow.doctool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import shadow.doctool.output.ClassOrInterfacePage;
import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.TypeCheckException;
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
		
		///// BEGIN TYPECHECKING AND DOCUMENTING
		
		// Generate a list of source files from the command line arguments.
		// If packages/directories are specified, they will be searched for
		// source files
		List<Path> sourceFiles = getRequestedFiles(arguments.getMainArguments());
		Set<Type> compilationUnits = new HashSet<Type>();
		
		// Perform basic type-checking on each source file
		for (Path file : sourceFiles)
		{
			Type.clearTypes();
			compilationUnits.add(DocumentationTypeChecker
					.typeCheck(file.toFile()));
		}
		
		// If a directory was provided use it. Otherwise, create docs/ in the
		// current working directory
		Path outputDirectory;
		if (arguments.hasOption(DocumentationArguments.OUTPUT))
			outputDirectory = Paths.get(arguments.getOutputDirectory())
			.toAbsolutePath();
		else
			outputDirectory = Paths.get("docs").toAbsolutePath();
		
		// Document each class or interface
		for (Type compilationUnit : compilationUnits)
		{
			ClassOrInterfacePage page = 
					new ClassOrInterfacePage(compilationUnit, compilationUnits);
			page.make(outputDirectory);
		}
		
		// Export the stylesheet
		exportResource(outputDirectory.resolve("stylesheet.css"), 
				"/doctool/stylesheet.css");
	}
	
	/**
	 * Locates all given source files, either specified directly or within 
	 * packages/directories. Verifies that the files actually exist
	 */
	public static List<Path> getRequestedFiles(String[] givenPaths) throws IOException
	{
		List<Path> sourceFiles = new ArrayList<Path>();
		for (String path : givenPaths)
		{
			Path current = Paths.get(path).toAbsolutePath();
			
			// Ensure that the source file exists
			if (!Files.exists(current))
				throw new FileNotFoundException("File at " + current.toAbsolutePath() + " not found");
			
			// If the file is a directory, process it as a package
			if (Files.isDirectory(current))
				sourceFiles.addAll(getPackageFiles(current, true));
			else
				sourceFiles.add(current);
		}
		return sourceFiles;
	}
	
	/** 
	 * Finds all Shadow source files within a directory (a package)
	 * 
	 * @param recursive Determines whether or not subdirectories/subpackages
	 * 					are also searched
	 */
	public static List<Path> getPackageFiles(Path directory, boolean recursive) throws IOException
	{
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory))
		{
			List<Path> files = new ArrayList<Path>();
			
			for (Path file : stream)
			{
				// Capture all source files
				if (file.toString().endsWith(".shadow"))
					files.add(file);
				// Recurse into subdirectories if desired
				else if (recursive && Files.isDirectory(file))
					files.addAll(getPackageFiles(file, true));
			}
			
			return files;
		}
	}
	
	private static void exportResource(Path targetFile, String resource) throws DocumentationException, IOException
	{
		InputStream input = DocumentationTool.class.getResourceAsStream(resource);
		if (input == null)
			throw new DocumentationException("Could not load \"" + resource 
					+ "\" from JAR");
		
		Files.deleteIfExists(targetFile);
		Files.copy(input, targetFile);
	}
}
