package shadow.typecheck;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import shadow.parser.javacc.Node;
import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.ShadowParser;
import shadow.parser.javacc.SimpleNode;

public class TypeChecker extends AbstractASTVisitor {

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
	
	protected LinkedList<HashMap<String, String>> symbolTable;
	protected HashSet<MethodSignature> methodTable;
	
	public TypeChecker() {
		symbolTable = new LinkedList<HashMap<String, String>>();
		symbolTable.add(new HashMap<String, String>());
		
		methodTable = new HashSet<MethodSignature>();
	}
	
	public void typeCheck(Node node) throws ShadowException {
		ASTWalker walker = new ASTWalker(new MethodAndFieldChecker(symbolTable, methodTable));
		
		// walk the tree looking for methods & fields first
		walker.walk(node);
		
//		walker = new ASTWalker(new ClassChecker(symbolTable, methodTable));
		
		// now go through and check the whole class
//		walker.walk(node);
	}
	

}
