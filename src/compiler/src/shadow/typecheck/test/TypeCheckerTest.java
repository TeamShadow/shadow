package shadow.typecheck.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.ShadowParser;
import shadow.parser.javacc.SimpleNode;
import shadow.typecheck.TypeChecker;

public class TypeCheckerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    try {
	        String fileName = "src/shadow/typecheck/test/basic.shadow";
	        FileInputStream fis = new FileInputStream(fileName);        
	        ShadowParser parser = new ShadowParser(fis);
	        TypeChecker tc = new TypeChecker();
	        SimpleNode node = parser.CompilationUnit();
	        
	        node.dump("");
	        
	        // type check the tree
	        tc.typeCheck(node);
	        
	        System.out.println("GOOD TYPE CHECK");

	    } catch (ParseException e) {
	        System.out.println("BAD PARSE");
	        System.out.println(e.getMessage());
	    } catch (ShadowException se) {
	    	System.out.println("BAD TYPE CHECK");
	    	System.out.println(se.getMessage());
	    } catch (Error e) {
	        System.out.println("Ooops");
	        System.out.println(e.getMessage());
	    } catch (FileNotFoundException e) {
	        System.out.println(e.getMessage());
	    }       
	}
	
}
