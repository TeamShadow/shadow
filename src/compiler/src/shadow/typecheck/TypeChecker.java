package shadow.typecheck;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;

public class TypeChecker extends AbstractASTVisitor {

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
