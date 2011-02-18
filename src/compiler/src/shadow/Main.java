/**
 * 
 */
package shadow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;

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

	/**
	 * This is the starting point of the compiler
	 * @param args Command line arguments to control the compiler
	 */
	public static void main(String[] args) {
		
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
				
				System.exit(0);
			}
			
			// parse out the command line
			config.parse(commandLine);
			
		} catch (org.apache.commons.cli.ParseException e) {
					System.err.println("COMMAND LINE ERROR: " + e.getLocalizedMessage());
					e.printStackTrace();
					System.exit(-1);
		} catch (ShadowException e) {
			System.err.println("CONFIGURATION ERROR: " + e.getLocalizedMessage());
			System.exit(-1);
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
		        
		        
		        // get the start time for the compile
		        long startTime = System.currentTimeMillis();

		        // parse the file
		        SimpleNode node = parser.CompilationUnit();
		        
		        // type check the AST
		        boolean result = tc.typeCheck(node, shadowFile);
		        
		        if(!result) {
		        	System.out.println(shadowFile.getPath() + "FAILED TO TYPE CHECK");
		        	
		        	System.exit(-1);	// we should figure out proper return codes for each stage the could fail
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
			System.exit(-1);
		} catch(ParseException pe) {
			System.err.println("PARSE ERROR " + config.current().getPath() + ": " + pe.getLocalizedMessage());
			System.exit(-1);
		} catch (ShadowException e) {
			System.err.println("ERROR ON FILE " + config.current().getPath() + ": " + e.getLocalizedMessage());
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
}
