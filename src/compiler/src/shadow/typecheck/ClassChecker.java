package shadow.typecheck;

import java.util.HashMap;
import java.util.LinkedList;

import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.*;


//no automatic promotion for bitwise operators

public class ClassChecker extends BaseChecker {
	protected LinkedList<HashMap<String, Type>> symbolTable; /** List of scopes with a hash of symbols & types for each scope */
	protected HashMap<String, Type> fieldTable; /** Hash of symbols and types */
	protected HashMap<String, MethodSignature> methodTable;
	
	protected MethodSignature curMethod;

	public ClassChecker(LinkedList<HashMap<String, Type>> symbolTable, HashMap<String, Type> fieldTable, HashMap<String, MethodSignature> methodTable) {
		this.symbolTable = symbolTable;
		this.fieldTable = fieldTable;
		this.methodTable = methodTable;
		curMethod = null;
	}

	public Object visit(ASTBlock node, Object secondVisit) throws ShadowException {
		// we have a new scope, so we need a new HashMap in the linked list
		if((Boolean)secondVisit)
			symbolTable.removeFirst();
		else
			symbolTable.addFirst(new HashMap<String, Type>());
		
		return WalkType.POST_CHILDREN;
	}
	
	/**
	 * No need to type-check fields
	 */
	public Object visit(ASTFieldDeclaration node, Object secondVisit) throws ShadowException {
		return WalkType.NO_CHILDREN;	// we've already checked these
	}

	public Object visit(ASTMethodDeclarator node, Object secondVisit) throws ShadowException {
		String methodName = node.getImage();
		
		curMethod = methodTable.get(methodName);
		
		return WalkType.PRE_CHILDREN;	// don't need to come back here
	}

	public Object visit(ASTLocalVariableDeclaration node, Object secondVisit) throws ShadowException {		
		addVarDec(node, symbolTable.getFirst());
		
		return WalkType.PRE_CHILDREN;
	}
	
	public Object visit(ASTName node, Object data) throws ShadowException {
		String name = node.getImage();
		
		// see if the name is in the current scope
		if(!symbolTable.getFirst().containsKey(name)) {
			// now check the parameters of the method
			if(!curMethod.containsParam(name)) {
				// check to see if it's a field
				if(!fieldTable.containsKey(name))
					throw new ShadowException("UNDECLARED VARIABLE: " + name + " at " + node.getLine() + ":" + node.getColumn());
				
				// set the type of the node
				node.setType(fieldTable.get(name));
			} else {
				node.setType(curMethod.getParameterType(name));
			}
		} else {
			// otherwise, set the type
			node.setType(symbolTable.getFirst().get(name));
		}
		
		return WalkType.PRE_CHILDREN;
	}
	
	public Object visit(ASTAssignmentOperator node, Object secondVisit) throws ShadowException {
		if(node.jjtGetNumChildren() != 2) {
			throw new ShadowException("TYPE MISMATCH: too many arguments at " + node.getLine() + ":" + node.getColumn());
		}
		
		// get the two types
		Type t1 = node.jjtGetChild(0).getType();
		Type t2 = node.jjtGetChild(1).getType();
		
		// TODO: Add in all the types that we can compare here
		if( !t2.isSubtype(t1) )
			throw new ShadowException("TYPE MISMATCH\nfound   : " + t2 + "\nrequired:  " + t1);
				
		node.setType(t1);
		
		return WalkType.PRE_CHILDREN;
	}
	
	public Object visit(ASTRelationalExpression node, Object secondVisit) throws ShadowException {
		if(node.jjtGetNumChildren() != 2) {
			throw new ShadowException("TYPE MISMATCH: too many arguments at " + node.getLine() + ":" + node.getColumn());
		}
		
		// get the two types
		Type t1 = node.jjtGetChild(0).getType();
		Type t2 = node.jjtGetChild(1).getType();
		
		// TODO: Add in all the types that we can compare here
		if( !t1.isNumerical() )
			throw new ShadowException("INCORRECT TYPE: " + t1 + " used in relation");
		
		if( !t2.isNumerical() )
			throw new ShadowException("INCORRECT TYPE: " + t2 + " used in relation");
		
		node.setType(Type.BOOLEAN);	// relations are always booleans
		
		return WalkType.PRE_CHILDREN;
	}
	
	
	public Object visit(ASTEqualityExpression node, Object secondVisit) throws ShadowException {
		if(node.jjtGetNumChildren() != 2) {
			throw new ShadowException("TYPE MISMATCH: too many arguments at " + node.getLine() + ":" + node.getColumn());
		}
		
		// get the two types
		Type t1 = node.jjtGetChild(0).getType();
		Type t2 = node.jjtGetChild(1).getType();
		
		// TODO: Add in subtyping
		if(!t1.isSubtype(t2) && !t2.isSubtype(t1))  //works either way
			throw new ShadowException("TYPE MISMATCH: " + t1 + " and " + t2 + " are not comparable");
		
		node.setType(Type.BOOLEAN);	// relations are always booleans
		
		return WalkType.PRE_CHILDREN;
	}
	
	public Object visitShiftRotate(SimpleNode node ) throws ShadowException {	
		Type t1, t2;
		
		if(node.jjtGetNumChildren() > 3) {
			throw new ShadowException("TYPE MISMATCH: too many arguments at " + node.getLine() + ":" + node.getColumn());
		}
		
		t1 = node.jjtGetChild(0).getType();
		
		//strange hack because RightRotate() and RightShift() have their own productions
		if(node.jjtGetNumChildren()  == 2 )			
			t2 = node.jjtGetChild(1).getType();
		else
			t2 = node.jjtGetChild(2).getType();
				
		if(!t1.isIntegral() )
			throw new ShadowException("INCORRECT TYPE\nfound   : " + t1 + "\nintegral type required for shift and rotate operations");
		
		if(!t2.isIntegral() )
			throw new ShadowException("INCORRECT TYPE\nfound   : " + t2 + "\nintegral type required for shift and rotate operations");
		
		node.setType(t1);	// assume that result has the same type as the first argument
		
		return WalkType.PRE_CHILDREN;
	}
	
	public Object visit(ASTShiftExpression node, Object secondVisit) throws ShadowException {
		return visitShiftRotate( node );
	}
	
	public Object visit(ASTRotateExpression node, Object secondVisit) throws ShadowException {
		return visitShiftRotate( node );
	}
	
	public Object visitArithmetic(SimpleNode node) throws ShadowException {
		if(node.jjtGetNumChildren() != 2) {
			throw new ShadowException("TYPE MISMATCH: too many arguments at " + node.getLine() + ":" + node.getColumn());
		}
		
		// get the two types
		Type t1 = node.jjtGetChild(0).getType();
		Type t2 = node.jjtGetChild(1).getType();
				
		if(!t1.isNumerical())
			throw new ShadowException("INCORRECT TYPE\nfound   : " + t1 + "\nnumerical type required for arithmetic operations");
		
		if(!t2.isNumerical())
			throw new ShadowException("INCORRECT TYPE\nfound   : " + t2 + "\nnumerical type required for arithmetic operations");
		
		if(!t1.equals(t2))
			throw new ShadowException("TYPE MISMATCH\nfound   : " + t1 + " does not match " + t2 + " (strict typing)");
		
		node.setType(t1); // for now assume that result has the same type as the first argument
		
		return WalkType.PRE_CHILDREN;
	}
	
	public Object visit(ASTAdditiveExpression node, Object secondVisit) throws ShadowException {
		return visitArithmetic( node );
	}
	
	public Object visit(ASTMultiplicativeExpression node, Object secondVisit) throws ShadowException {
		return visitArithmetic( node );
	}
		
	public Object visit(ASTUnaryExpression node, Object secondVisit) throws ShadowException {
		if(node.jjtGetNumChildren() != 1) {
			throw new ShadowException("TYPE MISMATCH: too many arguments at " + node.getLine() + ":" + node.getColumn());
		}
				
		Type t = node.jjtGetChild(0).getType();
		
		if( node.getImage().startsWith("+") || node.getImage().startsWith("-") )
			if(!t.isNumerical())
				throw new ShadowException("INCORRECT TYPE\nfound   : " + t + "\nnumerical type required for arithmetic operations");
		
		node.setType(t);
		
		return WalkType.PRE_CHILDREN;
	}
		
	public Object visit(ASTUnaryExpressionNotPlusMinus node, Object secondVisit) throws ShadowException {
		if(node.jjtGetNumChildren() != 1) {
			throw new ShadowException("TYPE MISMATCH: too many arguments at " + node.getLine() + ":" + node.getColumn());
		}
				
		Type t = node.jjtGetChild(0).getType();
		
		if( node.getImage().startsWith("~") )
			if(!t.isIntegral())
				throw new ShadowException("INCORRECT TYPE\nfound   : " + t + "\nintegral type required for bitwise operations");
		
		if( node.getImage().startsWith("!") )
			if(!t.equals(Type.BOOLEAN))
				throw new ShadowException("INCORRECT TYPE\nfound   : " + t + "\nboolean type required for logical operations");
		
		node.setType(t);
		
		return WalkType.PRE_CHILDREN;
	}
	
	
	public Object visit(ASTConditionalExpression node, Object secondVisit) throws ShadowException {
		if(node.jjtGetNumChildren() == 1) {
			Type t = node.jjtGetChild(0).getType();
			node.setType(t);
		}
		else if(node.jjtGetNumChildren() == 3) {			
			Type t1 = node.jjtGetChild(0).getType();
			Type t2 = node.jjtGetChild(1).getType();
			Type t3 = node.jjtGetChild(2).getType();
			
			if( !t1.equals(Type.BOOLEAN) )				
					throw new ShadowException("INCORRECT TYPE\nfound   : " + t1 + "\nboolean type required for conditional operations");
			
			if( t2.isSubtype(t3) )
				node.setType(t3);
			else if( t3.isSubtype(t2) )
				node.setType(t2);
			else
				throw new ShadowException("TYPE MISMATCH\nfound   : " + t2 + " must match " + t3 + " in ternary operator");			
		}
		else
			throw new ShadowException("TYPE MISMATCH: too many arguments at " + node.getLine() + ":" + node.getColumn());
		
		
		return WalkType.PRE_CHILDREN;
	}
	
	public Object visitConditional(SimpleNode node ) throws ShadowException {	
		if(node.jjtGetNumChildren() != 2) {
			throw new ShadowException("TYPE MISMATCH: too many arguments at " + node.getLine() + ":" + node.getColumn());
		}
		
		// get the two types
		Type t1 = node.jjtGetChild(0).getType();
		Type t2 = node.jjtGetChild(1).getType();
				
		if(!t1.equals(Type.BOOLEAN) )
			throw new ShadowException("INCORRECT TYPE\nfound   : " + t1 + "\nboolean type required for conditional operations");
		
		if(!t2.equals(Type.BOOLEAN) )
			throw new ShadowException("INCORRECT TYPE\nfound   : " + t2 + "\nboolean type required for conditional operations");		
		
		node.setType(Type.BOOLEAN);
		
		return WalkType.PRE_CHILDREN;
	}
	
	public Object visit(ASTConditionalOrExpression node, Object secondVisit) throws ShadowException {
		return visitConditional( node );
	}
	
	public Object visit(ASTConditionalExclusiveOrExpression node, Object secondVisit) throws ShadowException {
		return visitConditional( node );
	}
	
	public Object visit(ASTConditionalAndExpression node, Object secondVisit) throws ShadowException {
		return visitConditional( node );
	}	

	public Object visitBitwise(SimpleNode node ) throws ShadowException {	
		if(node.jjtGetNumChildren() != 2) {
			throw new ShadowException("TYPE MISMATCH: too many arguments at " + node.getLine() + ":" + node.getColumn());
		}
		
		// get the two types
		Type t1 = node.jjtGetChild(0).getType();
		Type t2 = node.jjtGetChild(1).getType();
		
		if(!t1.isIntegral() )
			throw new ShadowException("INCORRECT TYPE\nfound   : " + t1 + "\nintegral type required for shift and rotate operations");
		
		if(!t2.isIntegral() )
			throw new ShadowException("INCORRECT TYPE\nfound   : " + t2 + "\nintegral type required for shift and rotate operations");
	
		if(!t1.equals(t2))
			throw new ShadowException("TYPE MISMATCH\nfound   : " + t1 + " does not match " + t2 + " (strict typing)");
		
		node.setType(t1);	// for assume that result has the same type as the first argument				
		
		return WalkType.PRE_CHILDREN;
	}
	
	public Object visit(ASTBitwiseOrExpression node, Object secondVisit) throws ShadowException {
		return visitConditional( node );
	}	
	
	public Object visit(ASTBitwiseExclusiveOrExpression node, Object secondVisit) throws ShadowException {
		return visitConditional( node );
	}	
	
	public Object visit(ASTBitwiseAndExpression node, Object secondVisit) throws ShadowException {
		return visitConditional( node );
	}	

		
}
