package shadow.typecheck;

import java.util.HashMap;
import java.util.LinkedList;

import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;

public class TypeChecker extends AbstractASTVisitor {

	protected LinkedList<HashMap<String, String>> symbolTable;
	protected HashMap<String, String> fieldTable;
	protected HashMap<String, MethodSignature> methodTable;
	
	public TypeChecker() {
		symbolTable = new LinkedList<HashMap<String, String>>();
		symbolTable.add(new HashMap<String, String>());
		
		methodTable = new HashMap<String, MethodSignature>();
		fieldTable = new HashMap<String, String>();
	}
	
	public void typeCheck(Node node) throws ShadowException {
		ASTWalker walker = new ASTWalker(new MethodAndFieldChecker(fieldTable, methodTable));
		
		// walk the tree looking for methods & fields first
		walker.walk(node);
		
		System.out.println("METHOD & FIELD DONE");
		
		walker = new ASTWalker(new ClassChecker(symbolTable, fieldTable, methodTable));
		
		// now go through and check the whole class
		// we could have the method checker above provide a list of method nodes
		// to check so we don't have to walk the whole tree 
		walker.walk(node);
	}
	

}
