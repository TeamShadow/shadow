package shadow.typecheck;

import java.util.HashMap;
import java.util.LinkedList;

import shadow.AST.ASTUtils;
import shadow.AST.AbstractASTVisitor;
import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.ASTAdditiveExpression;
import shadow.parser.javacc.ASTMultiplicativeExpression;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.SimpleNode;

public abstract class BaseChecker extends AbstractASTVisitor {

	public BaseChecker() {
	}
	
	/**
	 * Adds a variable or field declaration to the symbol table.
	 * @param node The node containing the declaration to add.
	 * @throws ShadowException
	 */
	public void addVarDec(SimpleNode node, HashMap<String, Type> symbolTable) throws ShadowException {
		// a field dec has a type followed by 1 or more idents
		Type type = node.jjtGetChild(0).jjtGetChild(0).getType();
		
		// go through inserting all the idents
		for(int i=1; i < node.jjtGetNumChildren(); ++i) {
			Node varDecl = node.jjtGetChild(i);
			String symbol = varDecl.jjtGetChild(0).getImage();
			
			// make sure we don't already have this symbol
			if(symbolTable.containsKey(symbol))
				throw new ShadowException("Multiply defined symbol: " + ASTUtils.getSymLineCol(varDecl.jjtGetChild(0)));
			
			System.out.println("ADDING: " + type + " " + symbol);
			symbolTable.put(symbol, type);
			
			// see if we have an assignment
			if(varDecl.jjtGetNumChildren() == 2) {
				Type assignType = varDecl.jjtGetChild(1).jjtGetChild(0).getType();
				
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
	static public String getType(String symbol, LinkedList<HashMap<String, String>> symbolTable) {
		// go through all the symbols looking for this one
		for(HashMap<String, String> map:symbolTable) {
			if(map.containsKey(symbol))
				return map.get(symbol);
		}
		
		return null;
	}
	
	/**
	 * Checks all the children to make sure they're the same type, then returns that type.
	 * @param node The node who's children we want to check.
	 * @return The type of this node.
	 */
	protected Type checkChildren(SimpleNode node) throws ShadowException {
		int numChildren = node.jjtGetNumChildren();
		
		if(numChildren == 0)
			return null;
		
		Type type = node.jjtGetChild(0).getType();
		
		for(int i=1; i < numChildren; ++i) {
			if(!type.equals(node.jjtGetChild(i).getType()))
					throw new ShadowException("TYPE MISMATCH: " + node.jjtGetChild(i).getLine() + ":" + node.jjtGetChild(i).getColumn());
		}
		
		return type;
	}

	public Object visit(ASTMultiplicativeExpression node, Object secondVisit) throws ShadowException {
		if((Boolean)secondVisit)
			node.setType(checkChildren(node));	// make sure all children are the same & set this type
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTAdditiveExpression node, Object secondVisit) throws ShadowException {
		if((Boolean)secondVisit)
			node.setType(checkChildren(node));	// make sure all children are the same & set this type
		
		return WalkType.POST_CHILDREN;
	}


}
