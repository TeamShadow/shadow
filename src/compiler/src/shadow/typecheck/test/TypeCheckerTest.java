package shadow.typecheck.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.ShadowParser;
import shadow.parser.javacc.SimpleNode;
import shadow.test.BaseTest;
import shadow.typecheck.TypeChecker;

public class TypeCheckerTest extends BaseTest {
	private boolean dump;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException, ParseException {
		TypeCheckerTest tct = new TypeCheckerTest(true);
		
		// no args, we test everything
		if(args.length == 0)
			tct.testAll();
		else {
			for(String arg:args) {
				tct.testAll(new File(arg));
			}
		}	
	}
	
	public TypeCheckerTest(boolean dump) {
		super("./src/shadow/typecheck/test");
		this.dump = dump;
	}
	
	protected void runTest(File sourceFile) throws ParseException {
	    try {
	        FileInputStream fis = new FileInputStream(sourceFile);        
	        ShadowParser parser = new ShadowParser(fis);
	        TypeChecker tc = new TypeChecker();
	        SimpleNode node = parser.CompilationUnit();
	        
	        if(dump)
	        	node.dump("");
	        
	        long startTime = System.currentTimeMillis();

	        // type check the tree
	        tc.typeCheck(node);
	        
	        long stopTime = System.currentTimeMillis();
	        long runTime = stopTime - startTime;

	        printResult(sourceFile.getPath(), "PASS", runTime);

	    } catch (ParseException e) {
	        System.out.println("BAD PARSE");
	        System.out.println(e.getMessage());
	    } catch (ShadowException se) {
	    	System.out.println("BAD TYPE CHECK");
	    	System.out.println(se.getMessage());
	    } catch (Error e) {
	        System.out.println("Ooops");
	        System.out.println(e.getMessage());
	        e.printStackTrace();
	    } catch (FileNotFoundException e) {
	        System.out.println(e.getMessage());
	    }       
	}
	
}
