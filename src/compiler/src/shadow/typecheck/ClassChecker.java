package shadow.typecheck;

import java.util.HashMap;
import java.util.LinkedList;

import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.ASTAdditiveExpression;
import shadow.parser.javacc.ASTAssignmentOperator;
import shadow.parser.javacc.ASTBitwiseAndExpression;
import shadow.parser.javacc.ASTBitwiseExclusiveOrExpression;
import shadow.parser.javacc.ASTBitwiseOrExpression;
import shadow.parser.javacc.ASTBlock;
import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.ASTConditionalAndExpression;
import shadow.parser.javacc.ASTConditionalExclusiveOrExpression;
import shadow.parser.javacc.ASTConditionalExpression;
import shadow.parser.javacc.ASTConditionalOrExpression;
import shadow.parser.javacc.ASTEqualityExpression;
import shadow.parser.javacc.ASTExtendsList;
import shadow.parser.javacc.ASTLocalVariableDeclaration;
import shadow.parser.javacc.ASTMethodDeclarator;
import shadow.parser.javacc.ASTMultiplicativeExpression;
import shadow.parser.javacc.ASTName;
import shadow.parser.javacc.ASTPrimaryExpression;
import shadow.parser.javacc.ASTRelationalExpression;
import shadow.parser.javacc.ASTRotateExpression;
import shadow.parser.javacc.ASTShiftExpression;
import shadow.parser.javacc.ASTUnaryExpression;
import shadow.parser.javacc.ASTUnaryExpressionNotPlusMinus;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.SimpleNode;
import shadow.typecheck.type.ClassInterfaceBaseType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.Type;


//no automatic promotion for bitwise operators

public class ClassChecker extends BaseChecker {
	protected LinkedList<HashMap<String, Type>> symbolTable; /** List of scopes with a hash of symbols & types for each scope */
	protected ClassInterfaceBaseType curClass; 
	protected MethodSignature curMethod;
	protected HashMap<String, Type> typeTable; 
	
	public ClassChecker(HashMap<String, Type> typeTable, boolean debug) {
		super(debug);
		this.typeTable = typeTable;
		symbolTable = new LinkedList<HashMap<String, Type>>();
	}
	
	public Object visit(ASTClassOrInterfaceDeclaration node, Object secondVisit) throws ShadowException {
		if(!(Boolean)secondVisit) // set the current type
			curClass = (ClassInterfaceBaseType)typeTable.get(node.getImage());
		else { // set back when returning from an inner class
			DEBUG("PARENT: " + curClass.getParent().getTypeName());
			curClass = (ClassInterfaceBaseType)curClass.getParent();
		}

		return WalkType.POST_CHILDREN;
	}

	public Object visit(ASTExtendsList node, Object secondVisit) throws ShadowException {
		
		int numChildren = node.jjtGetNumChildren();
		
		if(numChildren == 1) { // class
			Type type = typeTable.get(node.jjtGetChild(0).getImage());
			
			if(type == null) {
				addError(node.jjtGetChild(0), Error.UNDEF_TYP, node.jjtGetChild(0).getImage());
				return WalkType.PRE_CHILDREN;
			} else if(!(type instanceof ClassType)) {
				addError(node.jjtGetChild(0), Error.TYPE_MIS, node.jjtGetChild(0).getImage() + " is not a class");
				return WalkType.PRE_CHILDREN;
			}
			
			((ClassType)curClass).setExtendType((ClassType)type);
		} else { // interface
			for(int i=0; i < node.jjtGetNumChildren(); ++i) {
				Type type = typeTable.get(node.jjtGetChild(i).getImage());
				
				if(type == null) {
					addError(node.jjtGetChild(i), Error.UNDEF_TYP, node.jjtGetChild(i).getImage());
					return WalkType.PRE_CHILDREN;
				} else if(!(type instanceof InterfaceType)) {
					addError(node.jjtGetChild(i), Error.TYPE_MIS, node.jjtGetChild(i).getImage() + " is not an interface");
					return WalkType.PRE_CHILDREN;
				}
				
				((InterfaceType)curClass).addExtendType((InterfaceType)type);
			}
		}
			
		return WalkType.PRE_CHILDREN;
	}
	
	public Object visit(ASTBlock node, Object secondVisit) throws ShadowException {
		// we have a new scope, so we need a new HashMap in the linked list
		if((Boolean)secondVisit)
			symbolTable.removeFirst();
		else
			symbolTable.addFirst(new HashMap<String, Type>());
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTMethodDeclarator node, Object secondVisit) throws ShadowException {
		String methodName = node.getImage();
		
		curMethod = curClass.getMethod(methodName);
		
		return WalkType.PRE_CHILDREN;	// don't need to come back here
	}

	public Object visit(ASTLocalVariableDeclaration node, Object secondVisit) throws ShadowException {		

		// TODO: implement...
		
		return WalkType.PRE_CHILDREN;
	}
	
	/**
	 * TODO: DOUBLE CHECK THIS... I'M NOT REALLY SURE WHAT TO DO HERE
	 */
	public Object visit(ASTName node, Object data) throws ShadowException {
		String name = node.getImage();
		Type nameType = null;
		
		// go through the scopes trying to find the name
		for(HashMap<String, Type> curSymTable:symbolTable) {
			if(curSymTable.containsKey(name)) {
				nameType = curSymTable.get(name);
				break;
			}
		}
		
		// found it in the scopes
		if(nameType != null) {
			node.setType(nameType);
			return WalkType.PRE_CHILDREN;
		}
			
		// now check the parameters of the method
		if(curMethod.containsParam(name)) {
			node.setType(curMethod.getParameterType(name));
			return WalkType.PRE_CHILDREN;
		}
			
		// check to see if it's a field
		if(curClass.containsField(name)) {
			node.setType(curClass.getField(name));
			return WalkType.PRE_CHILDREN;
		}
		
		// by the time we get here, we haven't found this name anywhere
		addError(node, Error.UNDEC_VAR, name);
		return WalkType.NO_CHILDREN;
	}
	
	public Object visit(ASTPrimaryExpression node, Object secondVisit) throws ShadowException {
		if((Boolean)secondVisit)
			node.setType(node.jjtGetChild(0).getType());
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTAssignmentOperator node, Object secondVisit) throws ShadowException {
		if(node.jjtGetNumChildren() != 2) {
			addError(node, Error.TYPE_MIS, "Too many arguments");
			return WalkType.NO_CHILDREN;
		}
		
		// get the two types
		Type t1 = node.jjtGetChild(0).getType();
		Type t2 = node.jjtGetChild(1).getType();
		
		// TODO: Add in all the types that we can compare here
		if( !t2.isSubtype(t1) ) {
			addError(node.jjtGetChild(0), Error.TYPE_MIS, "Found type " + t2 + ", type " + t1 + " required");
			return WalkType.NO_CHILDREN;
		}
				
		node.setType(t1);
		
		return WalkType.PRE_CHILDREN;
	}
	
	public Object visit(ASTRelationalExpression node, Object secondVisit) throws ShadowException {
		if(node.jjtGetNumChildren() != 2) {
			addError(node, Error.TYPE_MIS, "Too many arguments");
			return WalkType.NO_CHILDREN;
		}
		
		// get the two types
		Type t1 = node.jjtGetChild(0).getType();
		Type t2 = node.jjtGetChild(1).getType();
		
		// TODO: Add in all the types that we can compare here
		if( !t1.isNumerical() ) {
			addError(node.jjtGetChild(0), Error.INVL_TYP, t1 + " used in relation");
			return WalkType.NO_CHILDREN;
		}
		
		if( !t2.isNumerical() ) {
			addError(node.jjtGetChild(1), Error.INVL_TYP, t2 + " used in relation");
			return WalkType.NO_CHILDREN;
		}
		
		node.setType(Type.BOOLEAN);	// relations are always booleans
		
		return WalkType.PRE_CHILDREN;
	}
	
	
	public Object visit(ASTEqualityExpression node, Object secondVisit) throws ShadowException {
		if(node.jjtGetNumChildren() != 2) {
			addError(node, Error.TYPE_MIS, "Too many arguments");
			return WalkType.NO_CHILDREN;
		}
		
		// get the two types
		Type t1 = node.jjtGetChild(0).getType();
		Type t2 = node.jjtGetChild(1).getType();
		
		// TODO: Add in subtyping
		if(!t1.isSubtype(t2) && !t2.isSubtype(t1)) {  //works either way
			addError(node.jjtGetChild(0), Error.TYPE_MIS, t1 + " and " + t2 + " are not comparable");
			return WalkType.NO_CHILDREN;
		}
		
		node.setType(Type.BOOLEAN);	// relations are always booleans
		
		return WalkType.PRE_CHILDREN;
	}
	
	public Object visitShiftRotate(SimpleNode node ) throws ShadowException {
		Type t1, t2;
		
		if(node.jjtGetNumChildren() > 3) {
			addError(node, Error.TYPE_MIS, "Too many arguments");
			return WalkType.NO_CHILDREN;
		}
		
		t1 = node.jjtGetChild(0).getType();
		
		//strange hack because RightRotate() and RightShift() have their own productions
		if(node.jjtGetNumChildren()  == 2 )			
			t2 = node.jjtGetChild(1).getType();
		else
			t2 = node.jjtGetChild(2).getType();
				
		if(!t1.isIntegral() ) {
			addError(node.jjtGetChild(1), Error.INVL_TYP, "Found type " + t1 + ", but integral type required for shift and rotate operations");
			return WalkType.NO_CHILDREN;
		}
		
		if(!t2.isIntegral() ) {
			addError(node.jjtGetChild(2), Error.INVL_TYP, "Found type " + t2 + ", but integral type required for shift and rotate operations");
			return WalkType.NO_CHILDREN;
		}
		
		node.setType(t1);	// assume that result has the same type as the first argument
		
		return WalkType.PRE_CHILDREN;
	}
	
	public Object visit(ASTShiftExpression node, Object secondVisit) throws ShadowException {
		if(!(Boolean)secondVisit)
			return WalkType.POST_CHILDREN;

		return visitShiftRotate( node );
	}
	
	public Object visit(ASTRotateExpression node, Object secondVisit) throws ShadowException {
		if(!(Boolean)secondVisit)
			return WalkType.POST_CHILDREN;

		return visitShiftRotate( node );
	}
	
	public Object visitArithmetic(SimpleNode node) throws ShadowException {
		// THIS IS WRONG... YOU CAN HAVE MULTIPLE NODES
		/*
		if(node.jjtGetNumChildren() != 2) {
			addError(node, Error.TYPE_MIS, "Too many arguments");
			return WalkType.NO_CHILDREN;
		}
		*/
		
		// get the two types
		Type t1 = node.jjtGetChild(0).getType();
		Type t2 = node.jjtGetChild(1).getType();
				
		if(!t1.isNumerical()) {
			addError(node.jjtGetChild(0), Error.INVL_TYP, "Found type " + t1 + ", but numerical type required for arithmetic operations");
			return WalkType.NO_CHILDREN;
		}
		
		if(!t2.isNumerical()) {
			addError(node.jjtGetChild(1), Error.INVL_TYP, "Found type " + t2 + ", but numerical type required for arithmetic operations");
			return WalkType.NO_CHILDREN;
		}
		
		if(!t1.equals(t2)) {
			addError(node, Error.TYPE_MIS, "Type " + t1 + " does not match " + t2 + " (strict typing)");
			return WalkType.NO_CHILDREN;
		}
		
		node.setType(t1); // for now assume that result has the same type as the first argument
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTAdditiveExpression node, Object secondVisit) throws ShadowException {
		if(!(Boolean)secondVisit)
			return WalkType.POST_CHILDREN;
		
		Type t1 = node.jjtGetChild(0).getType();
		Type t2 = node.jjtGetChild(1).getType();

		// see if we're dealing with strings or not
		if(t1.isString() && t2.isString()) {
			node.setType(t1);
			return WalkType.POST_CHILDREN;
		}
		else	// normal numeric addition
			return visitArithmetic( node );
	}
	
	public Object visit(ASTMultiplicativeExpression node, Object secondVisit) throws ShadowException {
		if(!(Boolean)secondVisit)
			return WalkType.POST_CHILDREN;

		return visitArithmetic( node );
	}
		
	public Object visit(ASTUnaryExpression node, Object secondVisit) throws ShadowException {
		if(node.jjtGetNumChildren() != 1) {
			addError(node, Error.TYPE_MIS, "Too many arguments");
			return WalkType.NO_CHILDREN;
		}
				
		Type t = node.jjtGetChild(0).getType();
		
		if( node.getImage().startsWith("+") || node.getImage().startsWith("-") )
			if(!t.isNumerical()) {
				addError(node, Error.INVL_TYP, "Found type " + t + ", but numerical type required for arithmetic operations");
				return WalkType.NO_CHILDREN;
			}
		
		node.setType(t);
		
		return WalkType.PRE_CHILDREN;
	}
		
	public Object visit(ASTUnaryExpressionNotPlusMinus node, Object secondVisit) throws ShadowException {
		if(node.jjtGetNumChildren() != 1) {
			addError(node, Error.TYPE_MIS, "Too many arguments");
			return WalkType.NO_CHILDREN;
		}
				
		Type t = node.jjtGetChild(0).getType();
		
		if( node.getImage().startsWith("~") )
			if(!t.isIntegral()) {
				addError(node, Error.INVL_TYP, "Found type " + t + ", but integral type required for bitwise operations");
				return WalkType.NO_CHILDREN;
			}
		
		if( node.getImage().startsWith("!") )
			if(!t.equals(Type.BOOLEAN)) {
				addError(node, Error.INVL_TYP, "Found type " + t + ", but boolean type required for logical operations");
				return WalkType.NO_CHILDREN;
			}
		
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
			
			if( !t1.equals(Type.BOOLEAN) ) {			
				addError(node.jjtGetChild(0), Error.INVL_TYP, "Found type" + t1 + ", but boolean type required for conditional operations");
				return WalkType.NO_CHILDREN;
			}
			
			if( t2.isSubtype(t3) )
				node.setType(t3);
			else if( t3.isSubtype(t2) )
				node.setType(t2);
			else {
				addError(node, Error.TYPE_MIS, "Type " + t2 + " must match " + t3 + " in ternary operator");
				return WalkType.NO_CHILDREN;
			}
		}
		else {
			addError(node, Error.TYPE_MIS, "Too many arguments");
			return WalkType.NO_CHILDREN;
		}
		
		
		return WalkType.PRE_CHILDREN;
	}
	
	public Object visitConditional(SimpleNode node ) throws ShadowException {	
		if(node.jjtGetNumChildren() != 2) {
			addError(node, Error.TYPE_MIS, "Too many arguments");
			return WalkType.NO_CHILDREN;
		}
		
		// get the two types
		Type t1 = node.jjtGetChild(0).getType();
		Type t2 = node.jjtGetChild(1).getType();
				
		if(!t1.equals(Type.BOOLEAN) ) {
			addError(node.jjtGetChild(0), Error.INVL_TYP, "Found type " + t1 + ", but boolean type required for conditional operations");
			return WalkType.NO_CHILDREN;
		}
		
		if(!t2.equals(Type.BOOLEAN) ) {
			addError(node.jjtGetChild(1), Error.INVL_TYP, "Found type " + t2 + ", but boolean type required for conditional operations");
			return WalkType.NO_CHILDREN;
		}
		
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
			addError(node, Error.TYPE_MIS, "Too many arguments");
			return WalkType.NO_CHILDREN;
		}
		
		// get the two types
		Type t1 = node.jjtGetChild(0).getType();
		Type t2 = node.jjtGetChild(1).getType();
		
		if(!t1.isIntegral() ) {
			addError(node.jjtGetChild(0), Error.INVL_TYP, "Found type " + t1 + ", but integral type required for shift and rotate operations");
			return WalkType.NO_CHILDREN;
		}
		
		if(!t2.isIntegral() ) {
			addError(node.jjtGetChild(1), Error.INVL_TYP, "Found type " + t2 + ", but integral type required for shift and rotate operations");
			return WalkType.NO_CHILDREN;
		}
	
		if(!t1.equals(t2)) {
			addError(node, Error.TYPE_MIS, "Type " + t1 + " does not match " + t2 + " (strict typing)");
			return WalkType.NO_CHILDREN;
		}
		
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
