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

public class TypeCheckerTestOLD extends BaseTest {
	private boolean dump;
	private boolean debug;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException, ShadowException {
		TypeCheckerTestOLD tct = new TypeCheckerTestOLD(true, true);
		
		//args = new String[1];
		//args[0] = "./src/shadow/typecheck/test/switch.shadow";
		
		// no args, we test everything
		if(args.length == 0)
			tct.testAll();
		else {
			for(String arg:args) {
				tct.testAll(new File(arg));
			}
		}	
	}
	
	public TypeCheckerTestOLD(boolean dump, boolean debug) {
		super("./src/shadow/typecheck/test");
		this.dump = dump;
		this.debug = debug;
	}
	
	protected void runTest(File sourceFile) throws ShadowException {
	    try {
	    	System.out.println("CHECKING: " + sourceFile.getPath());
	        FileInputStream fis = new FileInputStream(sourceFile);        
	        ShadowParser parser = new ShadowParser(fis);
	        TypeChecker tc = new TypeChecker(debug);
	        SimpleNode node = parser.CompilationUnit();
	        
	        if(dump)
	        	node.dump("");
	        
	        long startTime = System.currentTimeMillis();

	        // type check the tree
	        boolean result = tc.typeCheck(node, sourceFile);
	        
	        long stopTime = System.currentTimeMillis();
	        long runTime = stopTime - startTime;

	        if(result)
	        	printResult(sourceFile.getPath(), "PASS", runTime);
	        else {
	        	printResult(sourceFile.getPath(), "FAIL", runTime);
	        	throw new ShadowException("");
	        }

	    } catch (ParseException e) {
	        System.out.println("BAD PARSE");
	        System.out.println(e.getMessage());
	        throw new ShadowException(e.getMessage());
	    } catch (ShadowException se) {
	    	System.out.println("BAD TYPE CHECK");
	    	System.out.println(se.getMessage());
	        throw se;
	    } catch (Error e) {
	        System.out.println("Oops");
	        System.out.println(e.getMessage());
	        e.printStackTrace(System.out);
	        throw new ShadowException(e.getMessage());
	    } catch (FileNotFoundException e) {
	        System.out.println(e.getMessage());
	        throw new ShadowException(e.getMessage());
	    } catch (IOException e)
		{
	    	System.out.println(e.getMessage());
	        throw new ShadowException(e.getMessage());
		}       
	}
	
}
