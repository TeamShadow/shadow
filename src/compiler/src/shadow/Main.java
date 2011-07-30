/**
 * 
 */
package shadow;

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
import org.apache.commons.logging.LogFactory;

import shadow.TAC.TACBuilder;
import shadow.TAC.TACClass;
import shadow.output.TACLinearWalker;
import shadow.output.C.TACCVisitor;
import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.ShadowParser;
import shadow.parser.javacc.SimpleNode;
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
	
	private static final Log logger = LogFactory.getLog("shadow");

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
	 * @param args
	 * @return
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
				logger.debug("Command line parse error");
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
			 
		try
		{
			// loop through the source files, compiling them
			while(config.hasNext())
			{
				File shadowFile = config.next();
				FileInputStream sourceStream = new FileInputStream(shadowFile);
				ShadowParser parser = new ShadowParser(sourceStream);
		        TypeChecker tc = new TypeChecker(false);
		        TACBuilder tacBuilder = new TACBuilder(false);
		        
		        logger.info("Compiling " + shadowFile.getName());
		        
		        // get the start time for the compile
		        long startTime = System.currentTimeMillis();

		        // parse the file
		        SimpleNode node = parser.CompilationUnit();
		        
		        // type check the AST
		        boolean result = tc.typeCheck(node, shadowFile);
		        
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
			        // build the TAC
			        tacBuilder.build(node);
	
			        for(TACClass c:tacBuilder.getClasses()) {
		    			TACCVisitor cVisitor = new TACCVisitor(c, shadowFile);
		    			TACLinearWalker linearWalker = new TACLinearWalker(cVisitor);
					
		    			linearWalker.walk();
			        }
		    		
			        long stopTime = System.currentTimeMillis();
	
			        System.err.println("COMPILED " + shadowFile.getPath() + " in " + (stopTime - startTime) + "ms");
		        }
			}			
		} catch(FileNotFoundException fnfe) {
			System.err.println("FILE " + config.current().getPath() + ") NOT FOUND: " + fnfe.getLocalizedMessage());
			return GENERAL_ERROR;
		} catch(ParseException pe) {
			System.err.println("PARSE ERROR " + config.current().getPath() + ": " + pe.getLocalizedMessage());
			return PARSE_ERROR;
		} catch (ShadowException e) {
			System.err.println("ERROR ON FILE " + config.current().getPath() + ": " + e.getLocalizedMessage());
			e.printStackTrace();
			return TYPE_CHECK_ERROR;
		} catch (IOException e)	{
			System.err.println("FILE DEPENDENCY ERROR " + config.current().getPath() + ": " + e.getLocalizedMessage());
			e.printStackTrace();
			return TYPE_CHECK_ERROR;
		}
		
		return NO_ERROR;
	}
	
}
