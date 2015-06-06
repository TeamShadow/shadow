package shadow.doctool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.Logger;

import shadow.Configuration;
import shadow.ConfigurationException;
import shadow.Loggers;
import shadow.Main;
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
		Configuration.buildConfiguration(arguments.getMainArguments()[0],
				arguments.getConfigFileArg(), false);
		
		// Generate documentation for each file/package given on the command line
		for (String argument : arguments.getMainArguments())
		{
			// Locate the file in question
			Path currentFile = Paths.get(argument).toAbsolutePath();
			
			// Ensure that the main source file exists
			if (!Files.exists(currentFile))
				throw new FileNotFoundException("File at " + currentFile.toAbsolutePath() + " not found");
			
			Type.clearTypes();
			DocumentationTypeChecker checker = new DocumentationTypeChecker();
			
			try {
				checker.typeCheck(currentFile.toFile());
			} catch( TypeCheckException e ) {
				logger.error(currentFile + " FAILED TO TYPE CHECK");
				throw e;
			}
		}
	}
}
