/**
 * 
 */
package shadow.output.c;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.log4j.Logger;

import shadow.Configuration;
import shadow.ConfigurationException;
import shadow.Loggers;
import shadow.TypeCheckException;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.tac.TACBuilder;
import shadow.tac.TACModule;
import shadow.typecheck.TypeChecker;
import shadow.typecheck.type.Type;


/**
 * @author wspeirs
 *
 */
public class Main {
	
	/*
	 * These are the error codes returned by the compiler
	 */
	public static final int NO_ERROR		 =  0;
	public static final int GENERAL_ERROR	 = -1;
	public static final int PARSE_ERROR		 = -2;
	public static final int TYPE_CHECK_ERROR = -3;
	public static final int TAC_ERROR		 = -4;
	
	private static final Logger logger = Loggers.SHADOW;
	private static Configuration config;

	/**
	 * This is the starting point of the compiler.
	 * 
	 * @param args Command line arguments to control the compiler
	 * @throws MalformedURLException 
	 * @throws ConfigurationException 
	 */
	public static void main(String[] args) throws MalformedURLException, ConfigurationException {
		int ret = test(args);
		
		if(ret != NO_ERROR)
			System.exit(ret);
	}
	
	/**
	 * Used for unit tests, provides a return value.
	 * @param args arguments
	 * @return error
	 * @throws ConfigurationException
	 */
	public static int test(String[] args) throws ConfigurationException {

		try {
			// create our command-line options
			Options options = Configuration.createCommandLineOptions();
			CommandLineParser cliParser = new PosixParser();
			CommandLine commandLine = cliParser.parse(options, args);
			
			// see if we should print the help
			if(commandLine.hasOption("h")) {
				HelpFormatter helpFormatter = new HelpFormatter();
				
				helpFormatter.printHelp("shadow", options);
				
				return NO_ERROR;
			}
			
			// parse out the command line
			config = new Configuration(commandLine);
			
		} catch (org.apache.commons.cli.ParseException e) {
			System.err.println("COMMAND LINE ERROR: " + e.getLocalizedMessage());
			e.printStackTrace();
			return GENERAL_ERROR;
		} 
		catch (ConfigurationException e) 
		{	
			System.err.println("CONFIGURATION ERROR: " + e.getLocalizedMessage());			
			return GENERAL_ERROR;
		}
		catch (IOException e)
		{
			System.err.println("TARGET FINDING ERROR: " + e.getLocalizedMessage());
			return GENERAL_ERROR;
		}
		
		TypeChecker checker = new TypeChecker();
			 
		try
		{
			// Compile the given, main()-containing source file
			File shadowFile = config.getMainFile();
			checker.setCurrentFile(shadowFile);
			
			FileInputStream sourceStream = new FileInputStream(shadowFile);
	       
	        TACBuilder tacBuilder = new TACBuilder();
	        
	        logger.info("Compiling " + shadowFile.getName());
	        
	        // get the start time for the compile
	        long startTime = System.currentTimeMillis();
	        
	        // type check the AST
	        Node node = null;
	        	
        	sourceStream.close();
	        
	        try
	        {
	        	node = checker.typeCheck(shadowFile);
	        }
	        catch( TypeCheckException e )
	        {
	        	logger.error(shadowFile.getPath() + " FAILED TO TYPE CHECK");		        	
	        	return TYPE_CHECK_ERROR;
	        }
	        
	        // we are only parsing & type checking
	        if(config.isCheckOnly()) 
	        {
		        long stopTime = System.currentTimeMillis();

		        System.err.println("FILE " + shadowFile.getPath() + " CHECKED IN " + (stopTime - startTime) + "ms");
	        }
	        else
	        {
		        // build the TAC
		        for(TACModule module : tacBuilder.build(node))
		            new COutput().build(module);
	    		
		        long stopTime = System.currentTimeMillis();

		        System.err.println("COMPILED " + shadowFile.getPath() + " in " + (stopTime - startTime) + "ms");
	        }
	        
	        Type.clearTypes();
	        
		} catch(FileNotFoundException fnfe) {
			System.err.println("FILE NOT FOUND: " + fnfe.getLocalizedMessage());
			return GENERAL_ERROR;
		} catch(ParseException pe)	{
			System.err.println("PARSE ERROR: " + pe.getLocalizedMessage());
			return PARSE_ERROR;			
		} catch (ShadowException e) {
			System.err.println("ERROR ON FILE: " + e.getLocalizedMessage());
			e.printStackTrace();
			return TYPE_CHECK_ERROR;
		} catch (IOException e)	{
			System.err.println("FILE DEPENDENCY ERROR: " + e.getLocalizedMessage());
			e.printStackTrace();
			return TYPE_CHECK_ERROR;
		}
		
		return NO_ERROR;
	}
	
}
