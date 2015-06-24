package shadow.doctool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.Logger;

import shadow.Arguments;
import shadow.Configuration;
import shadow.ConfigurationException;
import shadow.Loggers;
import shadow.Main;
import shadow.doctool.output.Page;
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
			//printHelp();
			System.exit(Main.COMMAND_LINE_ERROR);
		} catch (ConfigurationException e) {
			System.err.println("CONFIGURATION ERROR: " + e.getLocalizedMessage());
			//printHelp();
			System.exit(Main.CONFIGURATION_ERROR);
		} catch (TypeCheckException e) {
			System.err.println("TYPE CHECK ERROR: " + e.getLocalizedMessage());
			System.exit(Main.TYPE_CHECK_ERROR);
		}
	}
	
	public static void document(String[] args) throws ConfigurationException, IOException, ParseException, ShadowException, TypeCheckException, org.apache.commons.cli.ParseException 
	{
		// Detect and establish the current settings and arguments
		DocumentationArguments arguments = new DocumentationArguments(args);
		
		// Exit if help was requested (Arguments handles printing)
		if (arguments.hasOption(Arguments.HELP))
			return;
		
		Configuration.buildConfiguration(arguments.getMainArguments()[0],
				arguments.getConfigFileArg(), false);
		
		// Generate a list of source files from the command line arguments.
		// If packages/directories are specified, they will be searched for
		// source files
		List<Path> sourceFiles = new ArrayList<Path>();
		for (String argument : arguments.getMainArguments())
		{
			Path current = Paths.get(argument).toAbsolutePath();
			
			// Ensure that the source file exists
			if (!Files.exists(current))
				throw new FileNotFoundException("File at " + current.toAbsolutePath() + " not found");
			
			if (Files.isDirectory(current))
				sourceFiles.addAll(findSourceFiles(current, true));
			else
				sourceFiles.add(current);
		}
		
		// Generate documentation for each source file
		for (Path file : sourceFiles)
		{
			Type.clearTypes();
			
			try {
				Page page = DocumentationTypeChecker.typeCheck(file.toFile());
				Path pageFile = Paths.get(file.toString().replace(".shadow", ".html"));
				PrintWriter writer = new PrintWriter(pageFile.toFile());
				writer.write(page.toString());
				writer.close();
			} catch( TypeCheckException e ) {
				logger.error(file + " FAILED TO TYPE CHECK");
				throw e;
			} catch (ParserConfigurationException e) {
				logger.error(file + " FAILED TO DOCUMENT");
				e.printStackTrace();
			} catch (DocumentationException e) {
				logger.error(file + " FAILED TO DOCUMENT");
				e.printStackTrace();
			} catch (IOException e) {
				logger.error(file + " FAILED TO DOCUMENT");
				e.printStackTrace();
			}
		}
	}
	
	/** 
	 * Finds all Shadow source files within a directory (a package)
	 * 
	 * @param recursive Determines whether or not subdirectories/subpackages
	 * 					are also searched
	 */
	public static List<Path> findSourceFiles(Path directory, boolean recursive) throws IOException
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
					files.addAll(findSourceFiles(file, true));
			}
			
			return files;
		}
	}
}
