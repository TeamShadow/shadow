package shadow.typecheck;

import java.util.HashMap;
import java.util.LinkedList;

import shadow.AST.ASTWalker;
import shadow.AST.AbstractASTVisitor;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;

public class TypeChecker extends AbstractASTVisitor {

	protected HashMap<String, Type> typeTable; /** Holds all of the types we know about */
	
	public TypeChecker() {
		typeTable = new HashMap<String, Type>();
	}
	
	/**
	 * Given the root node of an AST, type-checks the AST.
	 * @param node The root node of the AST
	 * @return True of the type-check is OK, false otherwise (errors are printed)
	 * @throws ShadowException
	 */
	public boolean typeCheck(Node node) throws ShadowException {
		TypeBuilder tb = new TypeBuilder(typeTable);
		ASTWalker walker = new ASTWalker(tb);
		
		//
		// Here is where we'd walk the import statements, and make the types for those files
		//
		
		// walk the tree building types
		walker.walk(node);
		
		System.out.println("TYPE BUILDING DONE");
		
		// see how many errors we found
		if(tb.getErrorCount() > 0) {
			tb.printErrors();
			return false;
		}
		
		ClassChecker cc = new ClassChecker(typeTable);
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
