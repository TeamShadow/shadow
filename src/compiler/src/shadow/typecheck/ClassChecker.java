package shadow.typecheck;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import shadow.AST.ASTUtils;
import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.ASTAdditiveExpression;
import shadow.parser.javacc.ASTAllocationExpression;
import shadow.parser.javacc.ASTArgumentList;
import shadow.parser.javacc.ASTArguments;
import shadow.parser.javacc.ASTArrayDimsAndInits;
import shadow.parser.javacc.ASTAssignmentOperator;
import shadow.parser.javacc.ASTAssignmentOperator.AssignmentType;
import shadow.parser.javacc.ASTBitwiseAndExpression;
import shadow.parser.javacc.ASTBitwiseExclusiveOrExpression;
import shadow.parser.javacc.ASTBitwiseOrExpression;
import shadow.parser.javacc.ASTBlock;
import shadow.parser.javacc.ASTCastExpression;
import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.ASTClassOrInterfaceType;
import shadow.parser.javacc.ASTConditionalAndExpression;
import shadow.parser.javacc.ASTConditionalExclusiveOrExpression;
import shadow.parser.javacc.ASTConditionalExpression;
import shadow.parser.javacc.ASTConditionalOrExpression;
import shadow.parser.javacc.ASTConstructorDeclaration;
import shadow.parser.javacc.ASTDestructorDeclaration;
import shadow.parser.javacc.ASTDoStatement;
import shadow.parser.javacc.ASTEqualityExpression;
import shadow.parser.javacc.ASTExpression;
import shadow.parser.javacc.ASTFieldDeclaration;
import shadow.parser.javacc.ASTForInit;
import shadow.parser.javacc.ASTForStatement;
import shadow.parser.javacc.ASTForeachStatement;
import shadow.parser.javacc.ASTIfStatement;
import shadow.parser.javacc.ASTIsExpression;
import shadow.parser.javacc.ASTLocalVariableDeclaration;
import shadow.parser.javacc.ASTMemberSelector;
import shadow.parser.javacc.ASTMethodDeclaration;
import shadow.parser.javacc.ASTMultiplicativeExpression;
import shadow.parser.javacc.ASTName;
import shadow.parser.javacc.ASTPrimaryExpression;
import shadow.parser.javacc.ASTPrimaryPrefix;
import shadow.parser.javacc.ASTPrimarySuffix;
import shadow.parser.javacc.ASTPrimitiveType;
import shadow.parser.javacc.ASTReferenceType;
import shadow.parser.javacc.ASTRelationalExpression;
import shadow.parser.javacc.ASTResultType;
import shadow.parser.javacc.ASTReturnStatement;
import shadow.parser.javacc.ASTRotateExpression;
import shadow.parser.javacc.ASTSequence;
import shadow.parser.javacc.ASTShiftExpression;
import shadow.parser.javacc.ASTStatementExpression;
import shadow.parser.javacc.ASTSwitchLabel;
import shadow.parser.javacc.ASTSwitchStatement;
import shadow.parser.javacc.ASTType;
import shadow.parser.javacc.ASTTypeArguments;
import shadow.parser.javacc.ASTUnaryExpression;
import shadow.parser.javacc.ASTUnaryExpressionNotPlusMinus;
import shadow.parser.javacc.ASTVariableInitializer;
import shadow.parser.javacc.ASTWhileStatement;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.SimpleNode;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassInterfaceBaseType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.UnboundMethodType;


//no automatic promotion for bitwise operators

public class ClassChecker extends BaseChecker {
	protected LinkedList<HashMap<String, Type>> symbolTable; /** List of scopes with a hash of symbols & types for each scope */
	protected MethodType curMethod;
	protected LinkedList<Type> curPrefix; 	/** Stack for current prefix (needed for arbitrarily long chains of expressions) */
	
	public ClassChecker(boolean debug, Map<String, Type> typeTable, List<String> importList ) {
		super(debug, typeTable, importList);		
		symbolTable = new LinkedList<HashMap<String, Type>>();
		curPrefix = new LinkedList<Type>();
	}
	
	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit) {// set the current type
			if( currentType == null )
				currentType = (ClassInterfaceBaseType)lookupType(node.getImage());
			else
				currentType = (ClassInterfaceBaseType)lookupType(currentType + "." + node.getImage());			
		}
		else // set back when returning from an inner class			
			currentType = (ClassInterfaceBaseType)currentType.getOuter();
		

		return WalkType.POST_CHILDREN;
	}
	
	private void createScope(Boolean secondVisit) {
		// we have a new scope, so we need a new HashMap in the linked list
		if(secondVisit) {
			System.out.println("\nSYMBOL TABLE:");
			for(String s:symbolTable.getFirst().keySet())
				System.out.println(s + ": " + symbolTable.getFirst().get(s));
			
			symbolTable.removeFirst();
		}
		else
			symbolTable.addFirst(new HashMap<String, Type>());
	}
	
	public Object visit(ASTSwitchStatement node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		Type type = node.jjtGetChild(0).getType();
		if(!type.isIntegral() && !type.isString())//TODO allow enum types
			addError(node,Error.INVL_TYP, "Found type " + type + ", but integral or string type required for switch.");
		for(int i=1;i<node.jjtGetNumChildren();++i) {
			Node childNode = node.jjtGetChild(i);
			if(childNode.getClass() == ASTSwitchLabel.class) {
				if(childNode.getType() != null){ //default label should have null type 
					if(!childNode.getType().isSubtype(type)) {
						addError(childNode,Error.TYPE_MIS,"Label type " + childNode.getType() + " does not match switch type " + type + ".");
					}
				}
			}
		}
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTSwitchLabel node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		if(node.jjtGetNumChildren() == 1)
			node.setType(node.jjtGetChild(0).getType());

		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTBlock node, Boolean secondVisit) throws ShadowException {
		createScope(secondVisit);
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTMethodDeclaration node, Boolean secondVisit) throws ShadowException {
		updateCurMethod((MethodType)node.getType());
		return WalkType.PRE_CHILDREN;	// don't need to come back here
	}
	
	public Object visit(ASTConstructorDeclaration node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			updateCurMethod((MethodType)node.getType());
		
		createScope(secondVisit); // constructors don't have Block()s so new scope needed
	
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTDestructorDeclaration node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			updateCurMethod(null);  //no params in destructor
		
		createScope(secondVisit); // destructors don't have Block()s so new scope needed
		
		return WalkType.POST_CHILDREN;
	}
	
	private void updateCurMethod(MethodType method) {
		/*String methodName = node.getImage();
		
		if( currentType instanceof ClassInterfaceBaseType ) {
			ClassInterfaceBaseType currentClass = (ClassInterfaceBaseType)currentType;
			curMethod = currentClass.getMethod(methodName);
		}
		else		
			addError(node, "Method declarations only allowed in class, interface, enum, error, and exception types");
		*/
		curMethod = method;
	}
	
	public Object visit(ASTReferenceType node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		Type type = node.jjtGetChild(0).getType();
		
		List<Integer> dimensions = node.getArrayDimensions();
		
		if( dimensions.size() == 0 )
			node.setType(type);
		else
			node.setType(new ArrayType(type, dimensions));
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTLocalVariableDeclaration node, Boolean secondVisit) throws ShadowException {		
		if(!secondVisit)
			return WalkType.POST_CHILDREN;

		// 	get the var's type
		Type type = node.jjtGetChild(0).getType();
		
		if(type == null) {
			addError(node.jjtGetChild(0), Error.UNDEF_TYP, node.jjtGetChild(0).jjtGetChild(0).getImage());
			return WalkType.NO_CHILDREN;
		}

		// go through and add the vars
		for(int i=1; i < node.jjtGetNumChildren(); ++i) {
			Node curNode = node.jjtGetChild(i);
			String varName = curNode.jjtGetChild(0).getImage();
			
			if(symbolTable.getFirst().get(varName) != null) {
				addError(curNode, Error.MULT_SYM, varName);
				continue;
			}
			
			// check to see if we have any kind of init here
			if(curNode.jjtGetNumChildren() == 2) {
				Type initType = curNode.jjtGetChild(1).getType();
				
				// we had an error below
				if(initType == null)
					continue;

				if(!initType.isSubtype(type)) {
					addError(curNode.jjtGetChild(1), Error.TYPE_MIS, "Cannot assign " + initType + " to " + type);
					symbolTable.getFirst().put(varName, type); // 100% fake so we can continue later
					continue;
				}
			}
			
			// add the symbol to the table
			symbolTable.getFirst().put(varName, type);
		}

		return WalkType.POST_CHILDREN;
	}

	public Object visit(ASTFieldDeclaration node, Boolean secondVisit) throws ShadowException {		
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		Type type = node.getType();	// this is set in the FieldAndMethodChecker
		
		for(int i=1; i < node.jjtGetNumChildren(); ++i) {
			Node curVarDec = node.jjtGetChild(i);
			
			if(curVarDec.jjtGetNumChildren() == 2) {
				Node curVarInit = curVarDec.jjtGetChild(1);
				Type initType = curVarInit.getType();
				
				// we had an error below us
				if(initType == null)
					continue;
				
				if(!initType.isSubtype(type))
					addError(curVarInit, Error.TYPE_MIS, "Cannot assign " + initType + " to " + type);
			}
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	
	/**
	 * TODO: DOUBLE CHECK THIS... I'M NOT REALLY SURE WHAT TO DO HERE
	 */
	public Object visit(ASTName node, Boolean secondVisit) throws ShadowException {
		String name = node.getImage();
 		Type type = lookupType(name);

		// first we check to see if this names a type
		if(type != null) {
			node.setType(type);
			return WalkType.PRE_CHILDREN;
		}
		
		// now go through the scopes trying to find the variable
		for(HashMap<String, Type> curSymTable:symbolTable) {
			if(curSymTable.containsKey(name)) {
				type = curSymTable.get(name);
				break;
			}
		}
		
		// found it in the scopes
		if(type != null) {
			node.setType(type);
			return WalkType.PRE_CHILDREN;
		}
			
		// now check the parameters of the method
		if(curMethod != null && curMethod.containsParam(name)) {
			node.setType(curMethod.getParameterType(name));
			return WalkType.PRE_CHILDREN;
		}
			
		// check to see if it's a field or a method
		if( currentType instanceof ClassInterfaceBaseType )
		{
			ClassInterfaceBaseType currentClass = (ClassInterfaceBaseType)currentType;
			if(currentClass.containsField(name)) {
				node.setType(currentClass.getField(name));
				return WalkType.PRE_CHILDREN;
			}			
		
			List<MethodSignature> methods = currentClass.getMethods(name);
			
			//unbound method (it gets bound when you supply args
			if( methods != null && methods.size() > 0 )
			{
				node.setType( new UnboundMethodType( name, currentClass ) );
				//node.setType(methods.get(0).getMethodType());
				return WalkType.PRE_CHILDREN;
			}
		}
		
		
		// by the time we get here, we haven't found this name anywhere
		addError(node, Error.UNDEC_VAR, name);
		ASTUtils.DEBUG(node, "DIDN'T FIND: " + name);

		return WalkType.NO_CHILDREN;
	}
	
	public Object visit(ASTAssignmentOperator node, Boolean secondVisit) throws ShadowException {
		return WalkType.PRE_CHILDREN;	// I don't think we do anything here
	}
	
	public Object visit(ASTRelationalExpression node, Boolean secondVisit) throws ShadowException {
		if( !secondVisit )
			return WalkType.POST_CHILDREN;
		
		if(node.jjtGetNumChildren() != 2) {
			addError(node, Error.TYPE_MIS, "Too many arguments");
			node.setType(Type.BOOLEAN);	// 100% fake to keep things going
			return WalkType.NO_CHILDREN;
		} else {		
			// get the two types
			Type t1 = node.jjtGetChild(0).getType();
			Type t2 = node.jjtGetChild(1).getType();
			
			// TODO: Add in all the types that we can compare here
			if( !t1.isNumerical() ) {
				addError(node.jjtGetChild(0), Error.INVL_TYP, t1 + " used in relation");
				node.setType(Type.BOOLEAN);	// 100% fake to keep things going
				return WalkType.NO_CHILDREN;
			}
			
			if( !t2.isNumerical() ) {
				addError(node.jjtGetChild(1), Error.INVL_TYP, t2 + " used in relation");
				node.setType(Type.BOOLEAN);	// 100% fake to keep things going
				return WalkType.NO_CHILDREN;
			}
			
			node.setType(Type.BOOLEAN);	// relations are always booleans
		}
		
		return WalkType.PRE_CHILDREN;
	}
	
	
	public Object visit(ASTEqualityExpression node, Boolean secondVisit) throws ShadowException {
		if( !secondVisit )
			return WalkType.POST_CHILDREN;
		
		if(node.jjtGetNumChildren() != 2) {
			addError(node, Error.TYPE_MIS, "Too many arguments");
			node.setType(Type.BOOLEAN);	// 100% fake to keep things going
			return WalkType.NO_CHILDREN;
		} else {			
			// get the two types
			Type t1 = node.jjtGetChild(0).getType();
			Type t2 = node.jjtGetChild(1).getType();
			
			// TODO: Add in subtyping
			if(!t1.isSubtype(t2) && !t2.isSubtype(t1)) {  //works either way
				addError(node.jjtGetChild(0), Error.TYPE_MIS, t1 + " and " + t2 + " are not comparable");
				node.setType(Type.BOOLEAN);	// 100% fake to keep things going
				return WalkType.NO_CHILDREN;
			}
			
			node.setType(Type.BOOLEAN);	// relations are always booleans
		}
			
		return WalkType.POST_CHILDREN;
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
	
	public Object visit(ASTShiftExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;

		return visitShiftRotate( node );
	}
	
	public Object visit(ASTRotateExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;

		return visitShiftRotate( node );
	}
	
	public Object visitArithmetic(SimpleNode node) throws ShadowException {
		// get the two types
		Type t1 = node.jjtGetChild(0).getType();
		Type t2 = node.jjtGetChild(1).getType();
		
		//
		// TODO: There can be MORE than 2 types!!!!
		//
				
		if(!t1.isNumerical()) {
			addError(node.jjtGetChild(0), Error.INVL_TYP, "Found type " + t1 + ", but numerical type required for arithmetic operations");
			node.setType(t1);	// 100% fake this so we can keep moving
			return WalkType.NO_CHILDREN;
		}
		
		if(!t2.isNumerical()) {
			addError(node.jjtGetChild(1), Error.INVL_TYP, "Found type " + t2 + ", but numerical type required for arithmetic operations");
			node.setType(t1);	// 100% fake this so we can keep moving
			return WalkType.NO_CHILDREN;
		}
		
		if(!t1.equals(t2)) {
			addError(node, Error.TYPE_MIS, "Type " + t1 + " does not match " + t2 + " (strict typing)");
			node.setType(t1);	// 100% fake this so we can keep moving
			return WalkType.NO_CHILDREN;
		}
		
		node.setType(t1); // for now assume that result has the same type as the first argument
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTAdditiveExpression node, Boolean secondVisit) throws ShadowException {
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
	
	public Object visit(ASTMultiplicativeExpression node, Boolean secondVisit) throws ShadowException {
		if(!(Boolean)secondVisit)
			return WalkType.POST_CHILDREN;

		return visitArithmetic( node );
	}
		
	public Object visit(ASTUnaryExpression node, Boolean secondVisit) throws ShadowException {
		if(!(Boolean)secondVisit)
			return WalkType.POST_CHILDREN;
		
		if(node.jjtGetNumChildren() != 1)
		{
			addError(node, Error.TYPE_MIS, "Too many arguments");
			return WalkType.NO_CHILDREN;
		}
				
		Type t = node.jjtGetChild(0).getType();
		String symbol = node.getImage();
		
		if((symbol.startsWith("+") || symbol.startsWith("-")) && !t.isNumerical()) {
				addError(node, Error.INVL_TYP, "Found type " + t + ", but numerical type required for arithmetic operations");
				return WalkType.NO_CHILDREN;
		}
		
		if(symbol.startsWith("-"))
			node.setType(Type.makeSigned((ClassType)t));
		else
			node.setType(t);
		
		return WalkType.POST_CHILDREN;
	}
		
	public Object visit(ASTUnaryExpressionNotPlusMinus node, Boolean secondVisit) throws ShadowException {
		if(node.jjtGetNumChildren() != 1) {
			addError(node, Error.TYPE_MIS, "Too many arguments");
			return WalkType.NO_CHILDREN;
		}
				
		Type t = node.jjtGetChild(0).getType();
		
		if(node.getImage().startsWith("~") && !t.isIntegral()) {
				addError(node, Error.INVL_TYP, "Found type " + t + ", but integral type required for bitwise operations");
				return WalkType.NO_CHILDREN;
		}
		
		if(node.getImage().startsWith("!") && !t.equals(Type.BOOLEAN)) {
				addError(node, Error.INVL_TYP, "Found type " + t + ", but boolean type required for logical operations");
				return WalkType.NO_CHILDREN;
		}
		
		node.setType(t);
		
		return WalkType.PRE_CHILDREN;
	}
	
	
	public Object visit(ASTConditionalExpression node, Boolean secondVisit) throws ShadowException {
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
		
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visitConditional(SimpleNode node ) throws ShadowException {	
		if(node.jjtGetNumChildren() != 2) {
			addError(node, Error.TYPE_MIS, "Too many arguments");
			node.setType(Type.BOOLEAN);	// 100% fake to keep things going
			return WalkType.NO_CHILDREN;
		}
		
		// get the two types
		Type t1 = node.jjtGetChild(0).getType();
		Type t2 = node.jjtGetChild(1).getType();
		
		if(!t1.equals(Type.BOOLEAN) ) {
			addError(node.jjtGetChild(0), Error.INVL_TYP, "Found type " + t1 + ", but boolean type required for conditional operations");
			node.setType(Type.BOOLEAN);	// 100% fake to keep things going
			return WalkType.NO_CHILDREN;
		}
		
		if(!t2.equals(Type.BOOLEAN) ) {
			addError(node.jjtGetChild(1), Error.INVL_TYP, "Found type " + t2 + ", but boolean type required for conditional operations");
			node.setType(Type.BOOLEAN);	// 100% fake to keep things going
			return WalkType.NO_CHILDREN;
		}
		
		node.setType(Type.BOOLEAN);
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTConditionalOrExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;

		return visitConditional( node );
	}
	
	public Object visit(ASTConditionalExclusiveOrExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;

		return visitConditional( node );
	}
	
	public Object visit(ASTConditionalAndExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;

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
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTBitwiseOrExpression node, Boolean secondVisit) throws ShadowException {
		return visitBitwise( node );
	}	
	
	public Object visit(ASTBitwiseExclusiveOrExpression node, Boolean secondVisit) throws ShadowException {
		return visitBitwise( node );
	}	
	
	public Object visit(ASTBitwiseAndExpression node, Boolean secondVisit) throws ShadowException {
		return visitBitwise( node );
	}	

	public Object visit(ASTExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		// if we only have 1 child, we just get the type from that child
		if(node.jjtGetNumChildren() == 1)
			pushUpType(node, secondVisit);
		
		// we have 3 children so it's an assignment
		else if(node.jjtGetNumChildren() == 3) {
			// get the two types, we have to go up to the parent to get them
			Type t1 = node.jjtGetChild(0).getType();
			ASTAssignmentOperator op = (ASTAssignmentOperator)node.jjtGetChild(1);
			Type t2 = node.jjtGetChild(2).getType();
			
			// SHOULD DO SOMETHING WITH THIS!!!
			AssignmentType assType = op.getAssignmentType();
			
			// we had an error some other place as one of the types is unknown
			//if(t1 == null || t2 == null)
			//		return WalkType.NO_CHILDREN;
			
			// TODO: Add in all the types that we can compare here
			if( !t2.isSubtype(t1) ) {
				addError(node.jjtGetChild(0), Error.TYPE_MIS, "Found type " + t2 + ", type " + t1 + " required");
				return WalkType.NO_CHILDREN;
			}
					
			node.setType(t1);	// set this node's type
		}
		
		else {
			// something went terribly wrong here... should NEVER get to this state or parser is broken
			throw new ShadowException("Wrong number of args to an assignment!!!" + node.getLine() + node.jjtGetNumChildren());
		}

		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTAllocationExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		//check curPrefix at some point
		
		Node child = node.jjtGetChild(0);
		
		if( child instanceof ASTPrimitiveType ) //array allocation
		{
			//array dims and inits
			List<Integer> dimensions = ((ASTArrayDimsAndInits)(node.jjtGetChild(1))).getArrayDimensions();
			node.setType(new ArrayType(child.getType(), dimensions));			 
		}		
		else if( child instanceof ASTClassOrInterfaceType ) //object allocation 
		{
			int counter = 1;
			
			if( node.jjtGetChild(counter) instanceof ASTTypeArguments )
			{
				//for now
				addError(node.jjtGetChild(counter), Error.INVL_TYP, "Generics are not yet handled");
				counter++;				
			}
			
			if( node.jjtGetChild(counter) instanceof ASTArrayDimsAndInits)
			{
				//array dims and inits
				List<Integer> dimensions = ((ASTArrayDimsAndInits)(node.jjtGetChild(counter))).getArrayDimensions();
				node.setType(new ArrayType(child.getType(), dimensions));
			}
			
			else if( node.jjtGetChild(counter) instanceof ASTArguments )
			{
				if( child.getType() instanceof InterfaceType ) {
					addError(child, Error.INVL_TYP, "Interfaces cannot be instantiated");
					return WalkType.POST_CHILDREN;
				}
				
				ClassInterfaceBaseType type = (ClassInterfaceBaseType)child.getType();
				List<Type> typeList = ((ASTArguments)(node.jjtGetChild(counter))).getTypeList();
				List<MethodSignature> candidateConstructors = type.getMethods("constructor");
				
				// we don't have implicit constructors, so need to check if the default constructor is OK
				if(typeList.size() == 0 && candidateConstructors == null) {
					node.setType(child.getType());
					return WalkType.POST_CHILDREN;
				}
				
				// we have no constructors, but they are calling with params
				else if(typeList.size() > 0 && candidateConstructors == null){
					addError(child, Error.TYPE_MIS, "No constructor found with signature " + typeList);
					return WalkType.POST_CHILDREN;
				}

				else {
					// by the time we get here, we have a constructor list
					List<MethodSignature> acceptableConstructors = new LinkedList<MethodSignature>();
					
					for( MethodSignature signature : candidateConstructors ) {
						if( signature.matches( typeList )) {
							node.setType(child.getType());
							return WalkType.POST_CHILDREN;
						} else if( signature.canAccept(typeList))
							acceptableConstructors.add(signature);
					}
					
					if( acceptableConstructors.size() == 0 ) {
						addError(child, Error.TYPE_MIS, "No constructor found with signature " + typeList);
						node.setType(child.getType());	// 100% fake the type so we can continue
					}
					
					else if( acceptableConstructors.size() > 1 ) {
						addError(child, Error.TYPE_MIS, "Ambiguous constructor call with signature " + typeList);
						node.setType(child.getType());	// 100% fake the type so we can continue
					}
					
					else
						node.setType(child.getType());
				}
			}
		} 
		
		return WalkType.POST_CHILDREN;
	}
	
	
	public Object visit(ASTArgumentList node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		for( int i = 0; i < node.jjtGetNumChildren(); i++ )
			node.addType(node.jjtGetChild(i).getType());
		
		return WalkType.POST_CHILDREN;		
	}
	
	public Object visit(ASTArguments node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		if( node.jjtGetNumChildren() == 0 )
			node.setTypeList(new LinkedList<Type>());
		else
			node.setTypeList(((ASTArgumentList)(node.jjtGetChild(0))).getTypeList());
		
		return WalkType.POST_CHILDREN; 
	}
	
	public Object visit(ASTStatementExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		Node child = node.jjtGetChild(0);
		
		if( child instanceof ASTSequence )
		{	
			SequenceType sequence = (SequenceType)child.getType();
			Type t2 = node.jjtGetChild(1).getType();
			
			if( t2 instanceof SequenceType ) //from method call
			{
				SequenceType sequenceType = (SequenceType)t2;
				
				if( !sequence.canAccept( sequenceType.getTypes() ) )
					addError(child, Error.TYPE_MIS, "Sequence " + sequenceType + " does not match " + sequence);
			}
			else
				addError(child, Error.TYPE_MIS, "Only method return values can be assigned to sequences");
		}
		else //primary expression
		{
			if( node.jjtGetNumChildren() == 3 ) //only need to proceed if there is assignment
			{
				ASTAssignmentOperator op = (ASTAssignmentOperator)node.jjtGetChild(1);
				Type t1 = child.getType();
				Type t2 = node.jjtGetChild(2).getType();
				
				if( t2 == null )
				{
					addError(child, Error.TYPE_MIS, "Null type on RHS of " + t1);
					return WalkType.NO_CHILDREN;
				}
				
				// SHOULD DO SOMETHING WITH THIS!!!
				AssignmentType assType = op.getAssignmentType();
				
				if( t2 instanceof MethodType ) //could this be done with a more complex subtype relationship below?
				{
					MethodType methodType = (MethodType)t2;
					List<Type> type = new LinkedList<Type>();
					type.add(t1);
					if( !methodType.canReturn( type ) )
					{
						addError(node.jjtGetChild(0), Error.TYPE_MIS, "Method with signature " + methodType + " cannot return " + t1);
						return WalkType.NO_CHILDREN;
					}					
				}
				else if( !t2.isSubtype(t1) ) {
					addError(node.jjtGetChild(0), Error.TYPE_MIS, "Found type " + t2 + ", type " + t1 + " required");
					return WalkType.NO_CHILDREN;
				}
				
				//node.setType(t1);	// no need to set a statement's type
			}
		}
		
		return WalkType.POST_CHILDREN;	
	}

	
	public Object visit(ASTIsExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		//RelationalExpression() [ "is" Type() ]
		
		if( node.jjtGetNumChildren() == 1 )
			pushUpType(node, secondVisit);
		else
		{
			Type t1 = node.jjtGetChild(0).getType();
			Type t2 = node.jjtGetChild(1).getType();
			
			if( t1.isSubtype(t2) || t2.isSubtype(t1) )
				node.setType(Type.BOOLEAN);
			else
				addError(node, Error.TYPE_MIS, "Type " + t1 + " uncomparable with type " + t2);
		}			

		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTCastExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		
		if( node.jjtGetNumChildren() == 1 )
			pushUpType(node, secondVisit);
		else
		{
			Type t1 = node.jjtGetChild(0).getType();  //type
			Type t2 = node.jjtGetChild(1).getType();  //expression
			
			if( t1.isSubtype(t2) || t2.isSubtype(t1) )
				node.setType(t1);
			else
				addError(node, Error.TYPE_MIS, "Type " + t2 + " cannot be cast to " + t1);
		}			

		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTSequence node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;		
		
		if( node.jjtGetNumChildren() == 1 ) //maybe, or should it be treated like a sequence with one thing in it?
			pushUpType(node, secondVisit);
		else
		{
			SequenceType sequence = new SequenceType();
			
			for( int i = 0; i < node.jjtGetNumChildren(); i++ )
				sequence.addType(node.jjtGetChild(i).getType());
			
			node.setType( sequence );
		}			

		return WalkType.POST_CHILDREN;
	}
	
	

	public Object visit(ASTIfStatement node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		Type t = node.jjtGetChild(0).getType(); 
		
		if( !t.equals( Type.BOOLEAN ) )
			addError(node, Error.TYPE_MIS, "conditional of if statement must be boolean, found: " + t);
				
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTWhileStatement node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		Type t = node.jjtGetChild(0).getType(); 
		
		if( !t.equals( Type.BOOLEAN ) )
			addError(node, Error.TYPE_MIS, "conditional of while statement must be boolean, found: " + t);
				
		return WalkType.POST_CHILDREN;
	}

	public Object visit(ASTDoStatement node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		Type t = node.jjtGetChild(1).getType(); //second child, not first like if and while 
		
		if( !t.equals( Type.BOOLEAN ) )
			addError(node, Error.TYPE_MIS, "conditional of do statement must be boolean, found: " + t);
				
		return WalkType.POST_CHILDREN;
	}
	
	
	public Object visit(ASTForeachStatement node, Boolean secondVisit) throws ShadowException {
		createScope(secondVisit); //for variables declared in header, right?  Pretty sure that works
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
				
		Type t1 = node.jjtGetChild(0).getType(); //Type declaration
		Type t2 = node.jjtGetChild(1).getType(); //Collection
		
		//
		// TODO: Eventually we'll want the notion of a collection and need to check that here
		//
		if( t2.getKind() == Type.Kind.ARRAY )
		{
			ArrayType array = (ArrayType)t2;
			if( !array.getBaseType().isSubtype(t1) )
				addError(node, Error.TYPE_MIS, "incompatible foreach variable, found: " + t1 + " expected: " + array.getBaseType());
		}
		else
			addError(node, Error.TYPE_MIS, "foreach loop only works on arrays in this typechecker build, found: " + t2);
			
				
		return WalkType.POST_CHILDREN;
	}

	public Object visit(ASTForStatement node, Boolean secondVisit) throws ShadowException {
		boolean hasInit = false;
		
		if(node.jjtGetChild(0) instanceof ASTForInit) {
			createScope(secondVisit);	// only need the scope if we've created new vars
			hasInit = true;
		}
		
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		// the conditional type might come first or second depending upon if there is an init or not
		Type conditionalType = null;
		
		if(hasInit)
			conditionalType = node.jjtGetChild(1).getType();
			
		else
			conditionalType = node.jjtGetChild(0).getType();
		
		ASTUtils.DEBUG("TYPE: " + conditionalType);
		
		if(conditionalType == null || !conditionalType.equals( Type.BOOLEAN ) )
			addError(node, Error.TYPE_MIS, "conditional of for statement must be boolean, found: " + conditionalType);
		
		return WalkType.POST_CHILDREN;
	}	
	
	
	public Object visit(ASTReturnStatement node, Boolean secondVisit) throws ShadowException 
	{		
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
	
		//make sure matches method return types
		List<Type> types = new LinkedList<Type>();
		
		String returnTypes = "(";
		
		for( int i = 0; i < node.jjtGetNumChildren(); i++ )
		{
			returnTypes += node.jjtGetChild(i).getType();
			if( i < node.jjtGetNumChildren() - 1  )
				returnTypes += ",";
				
			types.add( node.jjtGetChild(i).getType()  );
		}
		
		returnTypes += ")";
		
		if( !curMethod.canReturn(types))
		{
			addError(node, Error.TYPE_MIS, "Method with signature " + curMethod + " cannot return " + returnTypes);
			return WalkType.NO_CHILDREN;
		}
		
		return WalkType.POST_CHILDREN;
	}	
	
	public Object visit(ASTPrimaryExpression node, Boolean secondVisit) throws ShadowException 
	{
		if(!secondVisit)
		{
			curPrefix.addFirst(null);
			return WalkType.POST_CHILDREN;
		}
		
		
		if( node.jjtGetNumChildren() > 1 ) 	//has suffixes, pull type from last suffix
			node.setType(node.jjtGetChild(node.jjtGetNumChildren() - 1).getType());
		else								//just prefix
			node.setType(node.jjtGetChild(0).getType());	
		
		curPrefix.removeFirst();  //pop prefix type off stack
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTPrimaryPrefix node, Boolean secondVisit) throws ShadowException 
	{
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		int children = node.jjtGetNumChildren();
		
		if(  children == 0 )
		{
			if( node.getImage().equals("this") )
			{	
				if( currentType instanceof InterfaceType )
					addError(node, Error.INVL_TYP, "\"this\" reference invalid for interfaces");
				else
					node.setType(currentType);
			}
			else //super case
			{
				if( currentType instanceof InterfaceType )
					addError(node, Error.INVL_TYP, "\"super\" reference invalid for interfaces"); //may need other cases
				else if( currentType instanceof ClassType )
				{
					ClassType parentType = ((ClassType)currentType).getExtendType();
					
					if( parentType.containsField(node.getImage() ))					
						node.setType(parentType.getField(node.getImage()));
					else
					{
						List<MethodSignature> methods = parentType.getMethods(node.getImage());
						
						//unbound method (it gets bound when you supply args
						if( methods != null && methods.size() > 0 )
							node.setType( new UnboundMethodType( node.getImage(), parentType ) );						
						else
							addError(node, Error.UNDEC_VAR, "Member " + node.getImage() + " not found");						
					}	
				}				
			}
		}
		else if( children == 1 )
		{
			Node child = node.jjtGetChild(0); 
			
			if( child instanceof ASTResultType ) //ResultType() "." "class"
			{
				if( child.getType() instanceof ClassType )
					node.setType( Type.CLASS );
			}
			else
				node.setType( child.getType() ); 	//literal, conditionalexpression, allocation expression, name
													//ack, methods will be a problem
		}
		
		curPrefix.set(0, node.getType()); //so that the suffix can figure out where it's at
		
		/*
		  Literal()
		  | "this" { jjtThis.setImage("this"); }
		  | "super" "." t = <IDENTIFIER> { jjtThis.setImage(t.image); }
		  | LOOKAHEAD( "(" ConditionalExpression() ")" ) "(" ConditionalExpression() ")"
		  | AllocationExpression()
		  | LOOKAHEAD( ResultType() "." "class" ) ResultType() "." "class"
		  | Name()
		*/

		
		return WalkType.POST_CHILDREN;
	}
	
	
	public Object visit(ASTPrimarySuffix node, Boolean secondVisit) throws ShadowException 
	{
		/*
		  LOOKAHEAD(2) "." "this"
		  | LOOKAHEAD(2) "." AllocationExpression()
		  | LOOKAHEAD(3) MemberSelector()
		  | "[" ConditionalExpression() ("," ConditionalExpression())* "]"
		  | "." t = <IDENTIFIER> { jjtThis.setImage(t.image); }
		  | Arguments()
		  }		
		*/	
				
		Type prefix = curPrefix.getFirst();
		
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		int children = node.jjtGetNumChildren();
		
		if(  children == 0 )
		{
			if( node.getImage().equals("this") )
			{	
				node.setType(prefix);
			}
			else 
			{
				if( prefix instanceof ClassInterfaceBaseType )
				{
					ClassInterfaceBaseType currentClass = (ClassInterfaceBaseType)prefix;
					if( currentClass.containsField(node.getImage() ) )
					{
						node.setType( currentClass.getField(node.getImage()));
					}
					else
					{
						List<MethodSignature> methods = currentClass.getMethods(node.getImage());
						
						//unbound method (it gets bound when you supply args
						if( methods != null && methods.size() > 0 )
							node.setType( new UnboundMethodType( node.getImage(), currentClass ) );						
						else
							addError(node, Error.UNDEC_VAR, "Member " + node.getImage() + " not found");
					}
					
				}
				else
					addError(node, Error.INVL_TYP, prefix + " not valid class or interface"); //may need other cases
			}
		}
		else // >= 1
		{
			Node child = node.jjtGetChild(0); 
			
			if( child instanceof ASTAllocationExpression ) //"." AllocationExpression()
			{
				ASTAllocationExpression allocation = (ASTAllocationExpression)child;
				
				if( prefix instanceof ClassInterfaceBaseType )
				{
					ClassInterfaceBaseType currentClass = (ClassInterfaceBaseType)prefix;
					if( currentClass.containsInnerClass(allocation.getType().getTypeName()) )
						node.setType(currentClass.getInnerClass(allocation.getType().getTypeName()));
					else
						addError(node, Error.UNDEF_TYP, "Inner class " + allocation.getType().getTypeName() + " not found");
				}
				else
					addError(node, Error.INVL_TYP, prefix + " not valid class or interface");
			}
			else if( child instanceof ASTMemberSelector )
			{
				addError(node, Error.INVL_TYP, "Generics are not yet handled");				
			}
			else if( child instanceof ASTConditionalExpression ) //array index
			{
				if( prefix instanceof ArrayType )
				{
					ArrayType arrayType = (ArrayType)prefix;
					
					for( int i = 0; i < node.jjtGetNumChildren(); i++ )
					{
						Type childType = node.jjtGetChild(0).getType();
						
						if( !childType.isIntegral() )
							addError(node.jjtGetChild(i), Error.INVL_TYP, "Found type " + childType + ", but integral type required for array subscript");
					}
					
					if( node.jjtGetNumChildren() == arrayType.getDimensions() )
						node.setType( arrayType.getBaseType() );
					else
						addError(node, Error.TYPE_MIS, "Needed "  + arrayType.getDimensions() + " indexes into array but found " +  node.jjtGetNumChildren());
				}
				else
					addError(node, Error.INVL_TYP, "Cannot subscript into non-array type " + prefix);
				
			}
			else if( child instanceof ASTArguments ) //method call
			{
				if( prefix instanceof UnboundMethodType )
				{
					UnboundMethodType unboundMethod = (UnboundMethodType)prefix; 
					List<Type> typeList = ((ASTArguments)child).getTypeList();
					ClassInterfaceBaseType outerClass = (ClassInterfaceBaseType)unboundMethod.getOuter();					
					List<MethodSignature> methods = outerClass.getMethods(unboundMethod.getTypeName());
					List<MethodSignature> acceptableMethods = new LinkedList<MethodSignature>();
					
					boolean perfectMatch = false;
					
					for( MethodSignature signature : methods ) 
					{
						if( signature.matches( typeList ))
						{
							List<Type> returnTypes = signature.getMethodType().getReturnTypes();
							if( returnTypes.size() == 1 )
								node.setType(returnTypes.get(0));
							else
							{
								SequenceType sequenceType = new SequenceType();
								for( Type type : returnTypes )
									sequenceType.addType(type);
								node.setType( sequenceType );
							}
							perfectMatch = true;
						}
						else if( signature.canAccept(typeList))
							acceptableMethods.add(signature);
					}
					
					if( !perfectMatch )
					{
						if( acceptableMethods.size() == 0 )					
							addError(child, Error.TYPE_MIS, "No method found with signature " + typeList);
						else if( acceptableMethods.size() > 1 )
							addError(child, Error.TYPE_MIS, "Ambiguous method call with signature " + typeList);
						else
						{
							List<Type> returnTypes = acceptableMethods.get(0).getMethodType().getReturnTypes();
							if( returnTypes.size() == 1 )
								node.setType(returnTypes.get(0));
							else
							{
								SequenceType sequenceType = new SequenceType();
								for( Type type : returnTypes )
									sequenceType.addType(type);
								node.setType( sequenceType );
							}							
						}
					}					
				}
				
			}
		}
		
		curPrefix.set(0, node.getType()); //so that a future suffix can figure out where it's at
		
		/*	
		  | LOOKAHEAD(2) "." AllocationExpression()
		  | LOOKAHEAD(3) MemberSelector()
		  | "[" ConditionalExpression() ("," ConditionalExpression())* "]"		  
		  | Arguments()
		  }		
		*/
		
		return WalkType.POST_CHILDREN;
	}
	
	

	
	

	
	/*//Push up is wrong, the children of ResultTypes are many, we can't just push one up 
	public Object visit(ASTResultTypes node, Boolean secondVisit) throws ShadowException 
	{ return pushUpType(node, secondVisit); 
	
	}
	
	*/
	
	
	
	

	//
	// Everything below here are just visitors to push up the type
	//
	public Object visit(ASTType node, Boolean secondVisit) throws ShadowException { return pushUpType(node, secondVisit); }
	public Object visit(ASTVariableInitializer node, Boolean secondVisit) throws ShadowException { return pushUpType(node, secondVisit); }
	public Object visit(ASTResultType node, Boolean secondVisit) throws ShadowException { return pushUpType(node, secondVisit); }

	
	
}
