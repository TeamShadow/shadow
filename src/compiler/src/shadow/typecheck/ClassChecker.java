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
import shadow.parser.javacc.ASTRightRotate;
import shadow.parser.javacc.ASTRightShift;
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
import shadow.parser.javacc.ASTAssignmentOperator.AssignmentType;
import shadow.parser.javacc.ShadowParser.ModifierSet;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassInterfaceBaseType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeWithModifiers;
import shadow.typecheck.type.UnboundMethodType;


//no automatic promotion for bitwise operators

public class ClassChecker extends BaseChecker {
	protected LinkedList<HashMap<String, TypeWithModifiers>> symbolTable; /** List of scopes with a hash of symbols & types for each scope */
	protected MethodType curMethod;
	protected LinkedList<Type> curPrefix; 	/** Stack for current prefix (needed for arbitrarily long chains of expressions) */
	
	public ClassChecker(boolean debug, Map<String, Type> typeTable, List<String> importList ) {
		super(debug, typeTable, importList);		
		symbolTable = new LinkedList<HashMap<String, TypeWithModifiers>>();
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
				System.out.println(s + ": " + symbolTable.getFirst().get(s).getType());
			
			symbolTable.removeFirst();
		}
		else
			symbolTable.addFirst(new HashMap<String, TypeWithModifiers>());
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
					//symbolTable.getFirst().put(varName, type); // 100% fake so we can continue later
					continue;
				}
			}
			
			// add the symbol to the table
			symbolTable.getFirst().put(varName, new TypeWithModifiers(type, node.getModifiers()));
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
	public Object visit(ASTName node, Boolean secondVisit) throws ShadowException 
	{
		String name = node.getImage();
 		Type type = lookupType(name);
 		int modifiers = 0;

		// first we check to see if this names a type
		if(type != null)
		{
			node.setType(type);
			return WalkType.PRE_CHILDREN;
		}
		
		// now go through the scopes trying to find the variable
		for(HashMap<String, TypeWithModifiers> curSymTable:symbolTable)
		{
			if(curSymTable.containsKey(name))
			{
				type = curSymTable.get(name).getType();
				modifiers = curSymTable.get(name).getModifiers();
				break;
			}
		}
		
		// found it in the scopes
		if( type != null ) 
		{
			node.setType(type);
			node.setModifiers(modifiers);
			node.addModifier(ModifierSet.ASSIGNABLE);
			return WalkType.PRE_CHILDREN;
		}
			
		// now check the parameters of the method
		if(curMethod != null && curMethod.containsParam(name))
		{
			node.setType(curMethod.getParameterType(name).getType());
			node.setModifiers(curMethod.getParameterType(name).getModifiers());
			node.addModifier(ModifierSet.ASSIGNABLE);
			return WalkType.PRE_CHILDREN;
		}
			
		// check to see if it's a field or a method
		if( currentType instanceof ClassInterfaceBaseType )
		{
			ClassInterfaceBaseType currentClass = (ClassInterfaceBaseType)currentType;
			
			if(currentClass.containsField(name))
			{
				node.setType(currentClass.getField(name).getType());
				node.setModifiers(currentClass.getField(name).getModifiers());
				node.addModifier(ModifierSet.ASSIGNABLE);
				return WalkType.PRE_CHILDREN;			
			}
			
			if( currentClass instanceof ClassType ) //check parents
			{
				ClassType parent = ((ClassType)currentClass).getExtendType();
				
				while( parent != null )
				{				
					if(parent.containsField(name))
					{
						Node field = parent.getField(name);
						if( ModifierSet.isPrivate(field.getModifiers()))
						{
							addError(node, Error.INVL_MOD, "Cannot access private variable " + field.getImage());
							return WalkType.PRE_CHILDREN;			
						}
						node.setType(parent.getField(name).getType());
						node.setModifiers(parent.getField(name).getModifiers());
						node.addModifier(ModifierSet.ASSIGNABLE);
						return WalkType.PRE_CHILDREN;			
					}
					
					parent = parent.getExtendType();
				}
			}
			
			List<MethodSignature> methods = currentClass.getMethods(name);
			
			//unbound method (it gets bound when you supply args)
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
		return WalkType.NO_CHILDREN;	// I don't think we do anything here
	}
	
	public Object visit(ASTRelationalExpression node, Boolean secondVisit) throws ShadowException {
		if( !secondVisit )
			return WalkType.POST_CHILDREN;		

		Type result = node.jjtGetChild(0).getType();
					
		for( int i = 1; i < node.jjtGetNumChildren(); i++ )
		{
			Type current = node.jjtGetChild(i).getType(); 
			if( !result.isNumerical() || !current.isNumerical() )
			{
				addError(node, Error.INVL_TYP, "Relational operator not defined on types " + result + " and " + current);
				return WalkType.POST_CHILDREN;
			}	
			
			result = Type.BOOLEAN;  //boolean after one comparison
		
		}
		
		node.setType(result); //propagates type up if only one child
		pushUpModifiers(node); //can make ASSIGNABLE (if only one child)
	
		
		return WalkType.POST_CHILDREN;	
	}
	
	
	public Object visit(ASTEqualityExpression node, Boolean secondVisit) throws ShadowException {
		if( !secondVisit )
			return WalkType.POST_CHILDREN;

		Type result = node.jjtGetChild(0).getType();
					
		for( int i = 1; i < node.jjtGetNumChildren(); i++ )
		{
			Type current = node.jjtGetChild(i).getType(); 
			if( !result.isSubtype(current) && !current.isSubtype(result) )
			{
				addError(node, Error.INVL_TYP, "Equality operator not defined on types " + result + " and " + current);
				return WalkType.POST_CHILDREN;
			}	
			
			result = Type.BOOLEAN;  //boolean after one comparison			
		}
		
		node.setType(result); //propagates type up if only one child
		pushUpModifiers(node); //can overwrite ASSIGNABLE (if only one child)
			
		
		return WalkType.POST_CHILDREN;		
	}
	
	public void visitShiftRotate( SimpleNode node ) throws ShadowException
	{			
		Type result = node.jjtGetChild(0).getType();
			
		for( int i = 1; i < node.jjtGetNumChildren(); i++ ) //cycle through types, upgrading to broadest legal one, short => int => long is possible
		{
			Node child = node.jjtGetChild(i); 
	
			if( !(child  instanceof ASTRightShift) && !(child instanceof ASTRightRotate)  ) //RightRotate() and RightShift() have their own productions
			{					
				Type current = child.getType();										
				
				if( current.isIntegral() && result.isIntegral() )
				{							
					if( result.isSubtype( current ))  //upgrades type to broader type (e.g. int goes to long)
						result = current;
				}
				else		
				{
					addError(child, Error.INVL_TYP, "Shift and rotate operations not defined on types " + result + " and " + current);
					return;
				}
			}				
		}				
		node.setType(result); //propagates type up if only one child	
		pushUpModifiers(node);  //can add ASSIGNABLE (if only one child)
	}
	
	public Object visit(ASTShiftExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;

		visitShiftRotate( node );
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTRotateExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;

		visitShiftRotate( node );
		return WalkType.POST_CHILDREN;
	}
	
	public void visitArithmetic(SimpleNode node) throws ShadowException 
	{
		Type result = node.jjtGetChild(0).getType();
	
		for( int i = 1; i < node.jjtGetNumChildren(); i++ ) //cycle through types, upgrading to broadest legal one
		{
			Type current = node.jjtGetChild(i).getType();
		
			
			if( result.isString() || current.isString() )
			{
				if( node.getImage().charAt(i - 1) != '+' )
				{
					addError(node.jjtGetChild(0), Error.INVL_TYP, "Cannot apply operator " + node.getImage().charAt(i - 1) + " to String type");
					return;
				}
				else
					result = Type.STRING;
			}
			else if( result.isNumerical() && current.isNumerical() )
			{
				if( result.isSubtype( current ))  //upgrades type to broader type (e.g. int goes to double)
					result = current;
			}
			else		
			{
				addError(node.jjtGetChild(i), Error.INVL_TYP, "Cannot apply arithmetic operations to " + result + " and " + current);
				return;						
			}				
		}
			
		node.setType(result); //propagates type up if only one child
		pushUpModifiers(node); //can add ASSIGNABLE (if only one child)
	}
	
	public Object visit(ASTAdditiveExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;		
	
		visitArithmetic( node );		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTMultiplicativeExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;

		visitArithmetic( node );
		return WalkType.POST_CHILDREN;
	}
		
	public Object visit(ASTUnaryExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
				
		Type t = node.jjtGetChild(0).getType();
		String symbol = node.getImage();
		
		if( (symbol.equals("-") || symbol.equals("+")) ) 
		{
			if( !t.isNumerical() )
			{
				addError(node, Error.INVL_TYP, "Found type " + t + ", but numerical type required for arithmetic operations");
				return WalkType.POST_CHILDREN;
			}
		}
		else
			pushUpModifiers( node );
		
		if(symbol.startsWith("-"))
			node.setType(Type.makeSigned((ClassType)t));
		else
			node.setType(t);
		
		return WalkType.POST_CHILDREN;
	}
		
	public Object visit(ASTUnaryExpressionNotPlusMinus node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		Type t = node.jjtGetChild(0).getType();
		
		if(node.getImage().startsWith("~") )
		{
			if( !t.isIntegral() )
			{
				addError(node, Error.INVL_TYP, "Found type " + t + ", but integral type required for bitwise operations");
				return WalkType.POST_CHILDREN;
			}
		}		
		else if(node.getImage().startsWith("!") )
		{
			if( !t.equals(Type.BOOLEAN)) 
			{
				addError(node, Error.INVL_TYP, "Found type " + t + ", but boolean type required for logical operations");
				return WalkType.POST_CHILDREN;
			}
		}
		else
			pushUpModifiers( node ); //can add ASSIGNABLE (if only one child)
		
		node.setType(t);
		
		return WalkType.POST_CHILDREN;
	}
	
	
	public Object visit(ASTConditionalExpression node, Boolean secondVisit) throws ShadowException {
		if( !secondVisit )
			return WalkType.POST_CHILDREN;
		
		if(node.jjtGetNumChildren() == 1) 
		{			
			node.setType(node.jjtGetChild(0).getType()); //propagate type up
			pushUpModifiers( node ); // can add ASSIGNABLE (if only one child)
		}
		else if(node.jjtGetNumChildren() == 3)
		{			
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
			else 
			{
				addError(node, Error.TYPE_MIS, "Type " + t2 + " must match " + t3 + " in ternary operator");
				return WalkType.NO_CHILDREN;
			}
		}
		else
		{
			addError(node, Error.TYPE_MIS, "Too many arguments");
			return WalkType.NO_CHILDREN;
		}		
		
		return WalkType.POST_CHILDREN;
	}
	
	public void visitConditional(SimpleNode node ) throws ShadowException {
		if(node.jjtGetNumChildren() == 1) 
		{
			node.setType( node.jjtGetChild(0).getType() ); //propagate type up
			pushUpModifiers( node );
		}
		else if(node.jjtGetNumChildren() == 2)
		{			
			// get the two types
			Type t1 = node.jjtGetChild(0).getType();
			Type t2 = node.jjtGetChild(1).getType();
			
			if(!t1.equals(Type.BOOLEAN) ) {
				addError(node.jjtGetChild(0), Error.INVL_TYP, "Found type " + t1 + ", but boolean type required for conditional operations");
				//node.setType(Type.BOOLEAN);	// 100% fake to keep things going
				return;
			}
			
			if(!t2.equals(Type.BOOLEAN) ) {
				addError(node.jjtGetChild(1), Error.INVL_TYP, "Found type " + t2 + ", but boolean type required for conditional operations");
				//node.setType(Type.BOOLEAN);	// 100% fake to keep things going
				return;
			}
			
			node.setType(Type.BOOLEAN);
		}
		else
			addError(node, Error.TYPE_MIS, "Too many arguments");
			
	}
	
	public Object visit(ASTConditionalOrExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;

		visitConditional( node );
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTConditionalExclusiveOrExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;

		visitConditional( node );
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTConditionalAndExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;

		visitConditional( node );
		return WalkType.POST_CHILDREN;
	}	

	//FIX THIS!  Bitwise operations can be over more than 2 operands
	public void visitBitwise(SimpleNode node ) throws ShadowException {
		if(node.jjtGetNumChildren() == 1)
		{
			node.setType( node.jjtGetChild(0).getType() ); //propagate type up
			pushUpModifiers( node );
		}
		else if(node.jjtGetNumChildren() == 2)
		{
			// get the two types
			Type t1 = node.jjtGetChild(0).getType();
			Type t2 = node.jjtGetChild(1).getType();
			
			if(!t1.isIntegral() ) {
				addError(node.jjtGetChild(0), Error.INVL_TYP, "Found type " + t1 + ", but integral type required for shift and rotate operations");
				return;
			}
			
			if(!t2.isIntegral() ) {
				addError(node.jjtGetChild(1), Error.INVL_TYP, "Found type " + t2 + ", but integral type required for shift and rotate operations");
				return;
			}
		
			if(!t1.equals(t2)) {
				addError(node, Error.TYPE_MIS, "Type " + t1 + " does not match " + t2 + " (strict typing)");
				return;
			}
								
			node.setType(t1);	// assume that result has the same type as the first argument
		}	
		else
			addError(node, Error.TYPE_MIS, "Too many arguments");
	}
	
	public Object visit(ASTBitwiseOrExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
				
		visitBitwise( node );
		return WalkType.POST_CHILDREN;
	}	
	
	public Object visit(ASTBitwiseExclusiveOrExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
				
		visitBitwise( node );
		return WalkType.POST_CHILDREN;
	}	
	
	public Object visit(ASTBitwiseAndExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
				
		visitBitwise( node );
		return WalkType.POST_CHILDREN;
	}	

	public Object visit(ASTExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		// if we only have 1 child, we just get the type from that child
		if(node.jjtGetNumChildren() == 1)
			pushUpType(node, secondVisit); //takes care of modifiers
		
		// we have 3 children so it's an assignment
		else if(node.jjtGetNumChildren() == 3) 
		{
			// get the two types, we have to go up to the parent to get them
			Node child1 = node.jjtGetChild(0);
			Type t1 = child1.getType();
			if( !ModifierSet.isAssignable( child1.getModifiers() ))
			{
				addError(child1, Error.TYPE_MIS, "Cannot assign a value to expression: " + child1 );
				return WalkType.NO_CHILDREN;
			}
			
			ASTAssignmentOperator op = (ASTAssignmentOperator)node.jjtGetChild(1);
			
			Node child2 = node.jjtGetChild(2);
			Type t2 = child2.getType();
			
			// SHOULD DO SOMETHING WITH THIS!!!
			AssignmentType assType = op.getAssignmentType();
			
			// we had an error some other place as one of the types is unknown
			//if(t1 == null || t2 == null)
			//		return WalkType.NO_CHILDREN;
			
			// TODO: Add in all the types that we can compare here
			if( !t2.isSubtype(t1) )
			{
				addError(child1, Error.TYPE_MIS, "Found type " + t2 + ", type " + t1 + " required");
				return WalkType.NO_CHILDREN;
			}
					
			node.setType(t1);	// set this node's type
			node.setModifiers( child2.getModifiers() ); //is this meaningful?
		}		
		else 
		{
			// something went terribly wrong here... should NEVER get to this state or parser is broken
			throw new ShadowException("Wrong number of args to an assignment!!!" + node.getLine() + node.jjtGetNumChildren());
		}

		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTAllocationExpression node, Boolean secondVisit) throws ShadowException 
	{
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
			ASTSequence sequence = (ASTSequence)child;
			SequenceType t1 = (SequenceType)(child.getType());
			Type t2 = node.jjtGetChild(1).getType();
			
			if( !sequence.isAssignable() )
				addError(child, Error.TYPE_MIS, "Cannot assign a value to expression: " + child);						
			else if( t2 instanceof SequenceType ) //from method call
			{
				SequenceType sequenceType = (SequenceType)t2;
				
				if( !t1.canAccept( sequenceType.getTypes() ) )
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
				
				if( !ModifierSet.isAssignable(child.getModifiers()) )
					addError(child, Error.TYPE_MIS, "Cannot assign a value to expression: " + child);
				else if( ModifierSet.isFinal(child.getModifiers()) )
					addError(child, Error.INVL_TYP, "Cannot assign a value to variable marked final");
				else					
				{
//					if( t2 == null )
//					{
//						addError(child, Error.TYPE_MIS, "Null type on RHS of " + t1);
//						return WalkType.POST_CHILDREN;
//					}
					
					// SHOULD DO SOMETHING WITH THIS!!!
					AssignmentType assType = op.getAssignmentType();
					
					if( t2 instanceof MethodType ) //could this be done with a more complex subtype relationship below?
					{
						MethodType methodType = (MethodType)t2;
						List<Type> type = new LinkedList<Type>();
						type.add(t1);
						if( !methodType.canReturn( type ) )
							addError(child, Error.TYPE_MIS, "Method with signature " + methodType + " cannot return " + t1);
					}
					else if( !t2.isSubtype(t1) )
						addError(child, Error.TYPE_MIS, "Found type " + t2 + ", type " + t1 + " required");
				}	
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
		{
			node.setType(node.jjtGetChild(node.jjtGetNumChildren() - 1).getType());
			node.setModifiers(node.jjtGetChild(node.jjtGetNumChildren() - 1).getModifiers());
		}
		else								//just prefix
		{
			node.setType(node.jjtGetChild(0).getType());
			pushUpModifiers( node ); 			
		}
		
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
						node.setType(parentType.getField(node.getImage()).getType());
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
			{
				node.setType( child.getType() ); 	//literal, conditionalexpression, allocation expression, name
				pushUpModifiers( node ); 			//ack, methods will be a problem
			}
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
						Node field = currentClass.getField(node.getImage());
						
						if( fieldIsAccessible( field, currentType ))
						{
							node.setType( field.getType());
							node.setModifiers(field.getModifiers());
						}
						else
							addError(node, Error.INVL_MOD, "Field " + node.getImage() + " not accessible from current context");
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
					{
						if( classIsAccessible( allocation.getType(), currentType ))
							node.setType(currentClass.getInnerClass(allocation.getType().getTypeName()));
						else
							addError(node, Error.INVL_MOD, "Class " + allocation.getType() + " not accessible from current context");
					}
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
					{
						node.setType( arrayType.getBaseType() );
						node.addModifier(ModifierSet.ASSIGNABLE);
					}
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
							
							if( !methodIsAccessible( signature, currentType  ))
								addError(node, Error.INVL_MOD, "Method " + signature + " not accessible from current context");							
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
							MethodSignature signature = acceptableMethods.get(0); 
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
							
							if( !methodIsAccessible( signature, currentType  ))
								addError(node, Error.INVL_MOD, "Method " + signature + " not accessible from current context");
							
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
	
	public static boolean fieldIsAccessible( Node node, Type type )
	{
		if( ModifierSet.isPublic(node.getModifiers()) || node.getEnclosingType() == type )
			return true;		
		
		if( type instanceof ClassType )
		{
			ClassType parent = ((ClassType)type).getExtendType();
			
			while( parent != null )
			{
				if( node.getEnclosingType() == parent )
				{
					if( ModifierSet.isPrivate(node.getModifiers()))
						return false;
					else
						return true;
				}
				
				parent = parent.getExtendType();
			}
		}
		
		return false;
	}

	public static boolean classIsAccessible( Type classType, Type type )
	{
		if( ModifierSet.isPublic(classType.getModifiers()) || classType.getOuter() == type || classType.getOuter() == null )
			return true;
		
		Type outer = type.getOuter();
		
		while( outer != null )
		{
			if( outer == classType.getOuter() )
				return true;
			
			outer = outer.getOuter();		
		}
		
		
		if( type instanceof ClassType )
		{
			ClassType parent = ((ClassType)type).getExtendType();
			
			while( parent != null )
			{
				if( classType.getOuter() == parent )
				{
					if( ModifierSet.isPrivate(classType.getModifiers()))
						return false;
					else
						return true;
				}
				
				outer = parent.getOuter();
				
				while( outer != null )
				{
					if( outer == classType.getOuter() )
						return true;
					
					outer = outer.getOuter();		
				}
				
				parent = parent.getExtendType();
			}
		}
		
		return false;
	}
	
	public static boolean methodIsAccessible( MethodSignature signature, Type type )
	{
		return fieldIsAccessible(  signature.getASTNode(), type );
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
	public Object visit(ASTVariableInitializer node, Boolean secondVisit) throws ShadowException {
		return pushUpType(node, secondVisit); 
	}
	public Object visit(ASTResultType node, Boolean secondVisit) throws ShadowException { return pushUpType(node, secondVisit); }

	
	
}
