package shadow.typecheck;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import shadow.parser.javacc.ASTBlock;
import shadow.parser.javacc.ASTEqualityExpression;
import shadow.parser.javacc.ASTLocalVariableDeclaration;
import shadow.parser.javacc.ASTName;
import shadow.parser.javacc.ASTRelationalExpression;
import shadow.parser.javacc.ShadowException;

public class ClassChecker extends BaseChecker {

	public ClassChecker(LinkedList<HashMap<String, String>> symbolTable, HashSet<MethodSignature> methodTable) {
		super(symbolTable, methodTable);
	}

	public Object visit(ASTBlock node, Object secondVisit) throws ShadowException {
		// we have a new scope, so we need a new HashMap in the linked list
		if((Boolean)secondVisit)
			symbolTable.removeFirst();
		else
			symbolTable.addFirst(new HashMap<String, String>());
		
		return true;
	}
	
	

	public Object visit(ASTLocalVariableDeclaration node, Object secondVisit) throws ShadowException {		
		addVarDec(node);
		
		return false;
	}
	
	public Object visit(ASTName node, Object data) throws ShadowException {
		
		return false;
	}
	
	public Object visit(ASTRelationalExpression node, Object secondVisit) throws ShadowException {
		if(node.jjtGetNumChildren() != 2) {
			throw new ShadowException("TYPE MISMATCH: too many arguments at " + node.getLine() + ":" + node.getColumn());
		}
		
		// get the two types
		String t1 = node.jjtGetChild(0).getType();
		String t2 = node.jjtGetChild(1).getType();
		
		// TODO: Add in all the types that we can compare here
		if(!t1.equals("int") && !t1.equals("double") && !t1.equals("float"))
			throw new ShadowException("INCORRECT TYPE: " + t1 + " used in relation");
		
		if(!t2.equals("int") && !t2.equals("double") && !t2.equals("float"))
			throw new ShadowException("INCORRECT TYPE: " + t2 + " used in relation");
		
		node.setType("Boolean");	// relations are always booleans
		
		return false;
	}
	
	public Object visit(ASTEqualityExpression node, Object secondVisit) throws ShadowException {
		if(node.jjtGetNumChildren() != 2) {
			throw new ShadowException("TYPE MISMATCH: too many arguments at " + node.getLine() + ":" + node.getColumn());
		}
		
		// get the two types
		String t1 = node.jjtGetChild(0).getType();
		String t2 = node.jjtGetChild(1).getType();
		
		// TODO: Add in subtyping
		if(!t1.equals(t2))
			throw new ShadowException("TYPE MISMATCH: " + t1 + " and " + t2 + " are not comparable");
		
		node.setType("Boolean");	// relations are always booleans
		
		return false;
	}
}
