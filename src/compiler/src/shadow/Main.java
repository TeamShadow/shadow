/**
 * 
 */
package shadow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import shadow.TAC.TACBuilder;
import shadow.TAC.TACClass;
import shadow.TAC.TACMethod;
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
		
		try {
			File sourceFile = new File(args[0]);
			FileInputStream sourceStream = new FileInputStream(sourceFile);
			ShadowParser parser = new ShadowParser(sourceStream);
	        TypeChecker tc = new TypeChecker(false);
	        TACBuilder tacBuilder = new TACBuilder(false);
	        SimpleNode node = parser.CompilationUnit();
	        
	        long startTime = System.currentTimeMillis();

	        // type check the tree
	        boolean result = tc.typeCheck(node);
	        
	        if(!result) {
	        	System.out.println(sourceFile.getPath() + "FAILED TO TYPE CHECK");
	        	return;
	        }

	        // build the TAC
	        tacBuilder.build(node);

	        for(TACClass c:tacBuilder.getClasses()) {
    			TACCVisitor cVisitor = new TACCVisitor(c);
    			TACLinearWalker linearWalker = new TACLinearWalker(cVisitor);
			
    			linearWalker.walk();
	        }
    		
	        long stopTime = System.currentTimeMillis();
	        long runTime = stopTime - startTime;

	        System.err.println("COMPILED " + args[0] + " in " + runTime + "ms");
			
		} catch(FileNotFoundException fnfe) {
			System.err.println("Source file (" + args[0] + ") not found: " + fnfe.getLocalizedMessage());
		} catch(ParseException pe) {
			System.err.println("Error parsing " + args[0]);
			System.err.println(pe.getLocalizedMessage());
		} catch (ShadowException e) {
			e.printStackTrace();
		}
	}

}
