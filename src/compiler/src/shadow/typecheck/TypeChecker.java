package shadow.typecheck;

import java.util.HashMap;
import java.util.LinkedList;

import shadow.AST.ASTWalker;
import shadow.AST.AbstractASTVisitor;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.Type;

public class TypeChecker extends AbstractASTVisitor {

	protected HashMap<String, Type> typeTable; /** Holds all of the types we know about */
	protected boolean debug;
	
	public TypeChecker(boolean debug) {
		//typeTable = new HashMap<String, Type>();
		//instantiate with TypeCollector
		this.debug = debug;
	}
	
	/**
	 * Given the root node of an AST, type-checks the AST.
	 * @param node The root node of the AST
	 * @return True of the type-check is OK, false otherwise (errors are printed)
	 * @throws ShadowException
	 */
	public boolean typeCheck(Node node) throws ShadowException {

		// Here is where we'd walk the import statements, and collect the types for those files
		// Right now we are only collecting the types from the current file
		
		TypeCollector collector = new TypeCollector(debug);
		ASTWalker walker = new ASTWalker( collector );		
		walker.walk(node);
		
		typeTable = collector.produceTypeTable();
		
		TypeBuilder tb = new TypeBuilder(typeTable, debug);
		walker = new ASTWalker(tb);
		
		// walk the tree building types
		walker.walk(node);
		
		if(debug)
			System.out.println("DEBUG: TYPE BUILDING DONE");
		
		// print out the type table as it stands now
/*		System.out.println("TYPE TABLE:");
		for(String tn:typeTable.keySet())
			System.out.println(tn + ": " + typeTable.get(tn));
*/		
		// see how many errors we found
		if(tb.getErrorCount() > 0) {
			tb.printErrors(System.out);
			return false;
		}
		
		ClassChecker cc = new ClassChecker(typeTable, debug);
		walker = new ASTWalker(cc);
		
		// now go through and check the whole class
		walker.walk(node);

		// see how many errors we found
		if(cc.getErrorCount() > 0) {
			cc.printErrors(System.out);
			return false;
		}
		
		return true;
	}
	

}
