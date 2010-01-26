package shadow.typecheck;

import java.util.HashMap;
import java.util.LinkedList;

import shadow.AST.ASTWalker;
import shadow.AST.AbstractASTVisitor;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;

public class TypeChecker extends AbstractASTVisitor {

	protected LinkedList<HashMap<String, Type>> symbolTable;
	protected HashMap<String, Type> fieldTable;
	protected HashMap<String, MethodSignature> methodTable;
	
	public TypeChecker() {
		symbolTable = new LinkedList<HashMap<String, Type>>();
		symbolTable.add(new HashMap<String, Type>());
		
		methodTable = new HashMap<String, MethodSignature>();
		fieldTable = new HashMap<String, Type>();
	}
	
	/**
	 * Given the root node of an AST, type-checks the AST.
	 * @param node The root node of the AST
	 * @return True of the type-check is OK, false otherwise (errors are printed)
	 * @throws ShadowException
	 */
	public boolean typeCheck(Node node) throws ShadowException {
		MethodAndFieldChecker mfc = new MethodAndFieldChecker(fieldTable, methodTable);
		ASTWalker walker = new ASTWalker(mfc);
		
		// walk the tree looking for methods & fields first
		walker.walk(node);
		
		System.out.println("METHOD & FIELD DONE");
		
		// see how many errors we found
		if(mfc.getErrorCount() > 0) {
			mfc.printErrors();
			return false;
		}
		
		ClassChecker cc = new ClassChecker(symbolTable, fieldTable, methodTable);
//		walker = new ASTWalker(cc);
		
		// now go through and check the whole class
		// we could have the method checker above provide a list of method nodes
		// to check so we don't have to walk the whole tree 
//		walker.walk(node);
		// see how many errors we found

		if(cc.getErrorCount() > 0) {
			cc.printErrors();
			return false;
		}
		
		return true;
	}
	

}
