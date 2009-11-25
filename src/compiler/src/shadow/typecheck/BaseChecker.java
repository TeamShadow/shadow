package shadow.typecheck;

import java.util.HashMap;
import java.util.LinkedList;

import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.SimpleNode;

public abstract class BaseChecker extends AbstractASTVisitor {
	LinkedList<HashMap<String, String>> symbolTable;
	HashMap<String, MethodSignature> methodTable;

	public BaseChecker(LinkedList<HashMap<String, String>> symbolTable, HashMap<String, MethodSignature> methodTable) {
		this.symbolTable = symbolTable;
		this.methodTable = methodTable;
	}
	
	/**
	 * Adds a variable or field declaration to the symbol table.
	 * @param node The node containing the declaration to add.
	 * @throws ShadowException
	 */
	public void addVarDec(SimpleNode node) throws ShadowException {
		// a field dec has a type followed by 1 or more idents
		String type = node.jjtGetChild(0).jjtGetChild(0).getImage();
		
		// go through inserting all the idents
		for(int i=1; i < node.jjtGetNumChildren(); ++i) {
			Node varDecl = node.jjtGetChild(i);
			String symbol = varDecl.jjtGetChild(0).getImage();
			
			// make sure we don't already have this symbol
			if(symbolTable.getFirst().containsKey(symbol))
				throw new ShadowException("Multiply defined symbol");
			
			System.out.println("ADDING: " + type + " " + symbol);
			symbolTable.getFirst().put(symbol, type);
			
			// see if we have an assignment
			if(varDecl.jjtGetNumChildren() == 2) {
				String assignType = varDecl.jjtGetChild(1).jjtGetChild(0).getType();
				
				if(!assignType.equals(type))
					throw new ShadowException("TYPE MISMATCH: Assigning " + assignType + " to " + type);
			}
		}
	}
	
	/**
	 * Walks through all the symbol tables looking for the first occurrence of this symbol
	 * TODO: We'll need to add proper scoping not just first symbol to this
	 * @param symbol The symbol to lookup
	 * @return The type or null if not found
	 */
	public String getType(String symbol) {
		// go through all the symbols looking for this one
		for(HashMap<String, String> map:symbolTable) {
			if(map.containsKey(symbol))
				return map.get(symbol);
		}
		
		return null;
	}
}
