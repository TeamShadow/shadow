/**
 * 
 */
package shadow.output.llvm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.logging.Log;

import shadow.Configuration;
import shadow.Loggers;
import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.ShadowParser;
import shadow.parser.javacc.SimpleNode;
import shadow.tac.TACBuilder;
import shadow.tac.TACModule;
import shadow.typecheck.TypeChecker;


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
	
	private static final Log logger = Loggers.SHADOW;

	/**
	 * This is the starting point of the compiler.
	 * 
	 * @param args Command line arguments to control the compiler
	 */
	public static void main(String[] args) {
		int ret = test(args);
		
		if(ret != NO_ERROR)
			System.exit(ret);
	}
	
	/**
	 * Used for unit tests, provides a return value.
	 * @param args arguments
	 * @return error
	 */
	public static int test(String[] args) {
		Configuration config = Configuration.getInstance();

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
			if(!config.parse(commandLine)) {
				logger.error("Command line parse error");
				return GENERAL_ERROR;
			}
			
		} catch (org.apache.commons.cli.ParseException e) {
			System.err.println("COMMAND LINE ERROR: " + e.getLocalizedMessage());
			e.printStackTrace();
			return GENERAL_ERROR;
		} catch (ShadowException e) {
			System.err.println("CONFIGURATION ERROR: " + e.getLocalizedMessage());
			return GENERAL_ERROR;
		}
		
		TypeChecker checker = new TypeChecker(false);
			 
		try
		{
			// loop through the source files, compiling them
			while(config.hasNext())
			{
				File shadowFile = config.next();
				checker.setCurrentFile(shadowFile);
				
				FileInputStream sourceStream = new FileInputStream(shadowFile);				
				ShadowParser parser = new ShadowParser(sourceStream);
		       
		        TACBuilder tacBuilder = new TACBuilder();
		        
		        logger.info("Compiling " + shadowFile.getName());
		        
		        // get the start time for the compile
		        long startTime = System.currentTimeMillis();

		        // parse the file
		        SimpleNode node = parser.CompilationUnit();
		        
		        if(Loggers.TYPE_CHECKER.isTraceEnabled())
		        	node.dump("");
		        
		        // type check the AST
		        boolean result = checker.typeCheck(node, shadowFile);
		        
		        if(!result) {
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
		        	TACModule module = tacBuilder.build(node);
		        	System.out.println(module);

			        // build the TAC
			        new LLVMOutput(true).build(module);

			        // verify the TAC
			        new LLVMOutput(false).build(module);

			        // write to file
			        new LLVMOutput(shadowFile).build(module);

			        long stopTime = System.currentTimeMillis();
	
			        System.err.println("COMPILED " + shadowFile.getPath() + " in " + (stopTime - startTime) + "ms");
		        }
			}			
		} catch(FileNotFoundException fnfe) {
			System.err.println("FILE " + checker.getCurrentFile() + ") NOT FOUND: " + fnfe.getLocalizedMessage());
			return GENERAL_ERROR;
		} catch(ParseException pe) {
			System.err.println("PARSE ERROR " + checker.getCurrentFile() + ": " + pe.getLocalizedMessage());
			return PARSE_ERROR;
		} catch (ShadowException e) {
			System.err.println("ERROR ON FILE " + checker.getCurrentFile() + ": " + e.getLocalizedMessage());
			e.printStackTrace();
			return TYPE_CHECK_ERROR;
		} catch (IOException e)	{
			System.err.println("FILE DEPENDENCY ERROR " + checker.getCurrentFile() + ": " + e.getLocalizedMessage());
			e.printStackTrace();
			return TYPE_CHECK_ERROR;
		}
		
		return NO_ERROR;
	}
	
}
