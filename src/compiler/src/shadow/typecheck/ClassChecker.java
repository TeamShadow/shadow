package shadow.typecheck;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;

import shadow.Loggers;
import shadow.AST.ASTUtils;
import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.ASTAdditiveExpression;
import shadow.parser.javacc.ASTAllocationExpression;
import shadow.parser.javacc.ASTArgumentList;
import shadow.parser.javacc.ASTArguments;
import shadow.parser.javacc.ASTArrayAllocation;
import shadow.parser.javacc.ASTArrayDimsAndInits;
import shadow.parser.javacc.ASTArrayInitializer;
import shadow.parser.javacc.ASTAssertStatement;
import shadow.parser.javacc.ASTAssignmentOperator;
import shadow.parser.javacc.ASTBitwiseAndExpression;
import shadow.parser.javacc.ASTBitwiseExclusiveOrExpression;
import shadow.parser.javacc.ASTBitwiseOrExpression;
import shadow.parser.javacc.ASTBlock;
import shadow.parser.javacc.ASTBreakStatement;
import shadow.parser.javacc.ASTCastExpression;
import shadow.parser.javacc.ASTCheckExpression;
import shadow.parser.javacc.ASTClassOrInterfaceBody;
import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.ASTClassOrInterfaceType;
import shadow.parser.javacc.ASTCompilationUnit;
import shadow.parser.javacc.ASTConditionalAndExpression;
import shadow.parser.javacc.ASTConditionalExclusiveOrExpression;
import shadow.parser.javacc.ASTConditionalExpression;
import shadow.parser.javacc.ASTConditionalOrExpression;
import shadow.parser.javacc.ASTConstructorDeclaration;
import shadow.parser.javacc.ASTConstructorInvocation;
import shadow.parser.javacc.ASTDestructorDeclaration;
import shadow.parser.javacc.ASTDoStatement;
import shadow.parser.javacc.ASTEqualityExpression;
import shadow.parser.javacc.ASTExpression;
import shadow.parser.javacc.ASTFieldDeclaration;
import shadow.parser.javacc.ASTForInit;
import shadow.parser.javacc.ASTForStatement;
import shadow.parser.javacc.ASTForeachStatement;
import shadow.parser.javacc.ASTFormalParameter;
import shadow.parser.javacc.ASTFormalParameters;
import shadow.parser.javacc.ASTFunctionType;
import shadow.parser.javacc.ASTIfStatement;
import shadow.parser.javacc.ASTImportDeclaration;
import shadow.parser.javacc.ASTIsExpression;
import shadow.parser.javacc.ASTLabeledStatement;
import shadow.parser.javacc.ASTLiteral;
import shadow.parser.javacc.ASTLocalVariableDeclaration;
import shadow.parser.javacc.ASTMethodCall;
import shadow.parser.javacc.ASTMethodDeclaration;
import shadow.parser.javacc.ASTMultiplicativeExpression;
import shadow.parser.javacc.ASTName;
import shadow.parser.javacc.ASTPrimaryExpression;
import shadow.parser.javacc.ASTPrimaryPrefix;
import shadow.parser.javacc.ASTPrimarySuffix;
import shadow.parser.javacc.ASTReferenceType;
import shadow.parser.javacc.ASTRelationalExpression;
import shadow.parser.javacc.ASTResultType;
import shadow.parser.javacc.ASTResultTypes;
import shadow.parser.javacc.ASTRightRotate;
import shadow.parser.javacc.ASTRightShift;
import shadow.parser.javacc.ASTRotateExpression;
import shadow.parser.javacc.ASTSequence;
import shadow.parser.javacc.ASTShiftExpression;
import shadow.parser.javacc.ASTStatementExpression;
import shadow.parser.javacc.ASTSwitchLabel;
import shadow.parser.javacc.ASTSwitchStatement;
import shadow.parser.javacc.ASTTryStatement;
import shadow.parser.javacc.ASTType;
import shadow.parser.javacc.ASTTypeArguments;
import shadow.parser.javacc.ASTUnaryExpression;
import shadow.parser.javacc.ASTUnaryExpressionNotPlusMinus;
import shadow.parser.javacc.ASTUnqualifiedName;
import shadow.parser.javacc.ASTVariableInitializer;
import shadow.parser.javacc.ASTWhileStatement;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.ShadowParser.ModifierSet;
import shadow.parser.javacc.SimpleNode;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassInterfaceBaseType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.Type.Kind;
import shadow.typecheck.type.UnboundMethodType;


//no automatic promotion for bitwise operators

public class ClassChecker extends BaseChecker {
	private static final Log logger = Loggers.TYPE_CHECKER;
	
	protected LinkedList<HashMap<String, Node>> symbolTable; /** List of scopes with a hash of symbols & types for each scope */
	protected Node curMethod = null;   /** Current method (only a single reference needed since Shadow does not allow methods to be defined inside of methods) */
	protected LinkedList<Node> curPrefix = null; 	/** Stack for current prefix (needed for arbitrarily long chains of expressions) */
	protected LinkedList<Node> labels = null; 	/** Stack of labels for labeled break statements */
	protected LinkedList<ASTTryStatement> tryBlocks = null; /** Stack of try blocks currently nested inside */
	
	public ClassChecker(boolean debug, HashMap<Package, HashMap<String, Type>> typeTable, List<File> importList, Package packageTree ) {
		super(debug, typeTable, importList, packageTree );		
		symbolTable = new LinkedList<HashMap<String, Node>>();
		curPrefix = new LinkedList<Node>();
		labels = new LinkedList<Node>();	
		tryBlocks = new LinkedList<ASTTryStatement>();
	}
	
	//Important!  Set the current type on entering the body, not the declaration, otherwise extends and imports are improperly checked with the wrong outer class
	public Object visit(ASTClassOrInterfaceBody node, Boolean secondVisit) throws ShadowException {		
		if( secondVisit )
			currentType = currentType.getOuter();		
		else
			currentType = node.jjtGetParent().getType(); //get type from declaration
			
		return WalkType.POST_CHILDREN;
	}
	
	private void createScope(Boolean secondVisit) {
		// we have a new scope, so we need a new HashMap in the linked list
		if(secondVisit)
		{
/*			logger.debug("\nSYMBOL TABLE:");
			for(String s:symbolTable.getFirst().keySet())
				logger.debug(s + ": " + symbolTable.getFirst().get(s).getType());
*/				
			
			symbolTable.removeFirst();
		}
		else
			symbolTable.addFirst(new HashMap<String, Node>());
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
		pushUpType(node, secondVisit);
		
		if( secondVisit && node.jjtGetNumChildren() > 0 && !ModifierSet.isFinal(node.getModifiers()) )
			addError(node, Error.INVL_MOD, "Label must have constant value");			
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTBlock node, Boolean secondVisit) throws ShadowException {
		createScope(secondVisit);
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTMethodDeclaration node, Boolean secondVisit) throws ShadowException {
		return visitMethod( node, secondVisit );
	}
	
	
	public Object visit(ASTFormalParameters node, Boolean secondVisit) throws ShadowException
	/* Note:  The ASTFormalParameters node can only be found in methods (including constructors)
	 * However, the ASTFormalParameter node can also be found in a try-catch block
	 * This is why we add parameters to the symbol table in ASTFormalParameters and not ASTFormalParameter
	 */
	{
		if(!secondVisit)
			return WalkType.POST_CHILDREN;	
	
		for(int i = 0; i < node.jjtGetNumChildren(); ++i)
		{
			Node child = node.jjtGetChild(i);
			String varName = child.jjtGetChild(1).getImage();
			
			addSymbol( varName, child );
		}		

		return WalkType.POST_CHILDREN;
	}
	
	public void addSymbol( String name, Node node )
	{
		if( symbolTable.get(0).containsKey( name ) ) //we only look at current scope
			addError(node, Error.MULT_SYM, name);
		else if( symbolTable.size() == 0 )
			addError(node, Error.INVL_TYP, "No valid scope for variable declaration");
		else
			symbolTable.getFirst().put(name, node);  //uses node for modifiers
	}
	
	
	public Node findSymbol( String name )
	{
		Node node = null;
		for( HashMap<String,Node> map : symbolTable )
			if( (node = map.get(name)) != null )
				return node;
		
		return node;
	}
	
	
	public Object visitMethod( SimpleNode node, Boolean secondVisit  )
	{
		if(!secondVisit)
			curMethod = node;
		else
			curMethod = null;
		
		createScope(secondVisit); 
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTFormalParameter node, Boolean secondVisit) throws ShadowException
	{
		if(!secondVisit)
			return WalkType.POST_CHILDREN; 
			
		node.setType( node.jjtGetChild(0).getType() );		
	
		return WalkType.POST_CHILDREN;
	}
	
	
	public Object visit(ASTConstructorDeclaration node, Boolean secondVisit) throws ShadowException {
		return visitMethod( node, secondVisit );
	}
	
	public Object visit(ASTDestructorDeclaration node, Boolean secondVisit) throws ShadowException {
		return visitMethod( node, secondVisit );
	}
	
	public Object visit(ASTReferenceType node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		Node child =  node.jjtGetChild(0);
		
		Type type = child.getType();
		
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
		for(int i = 1; i < node.jjtGetNumChildren(); ++i)
		{
			Node curNode = node.jjtGetChild(i);
			String varName = curNode.jjtGetChild(0).getImage();
			
			if(curNode.jjtGetNumChildren() == 2) // check to see if we have any kind of init here
			{
				Type initType = curNode.jjtGetChild(1).getType();
				
				if(!initType.isSubtype(type)) 
				{
					addError(curNode.jjtGetChild(1), Error.TYPE_MIS, "Cannot assign " + initType + " to " + type);
					type = Type.UNKNOWN; //overwrites old type, for error case
				}
			}
			
			// add the symbol to the table
			addSymbol( varName, node);
		}
		
		node.setType(type); //either declaration type or UNKNOWN  		

		return WalkType.POST_CHILDREN;
	}

	public Object visit(ASTFieldDeclaration node, Boolean secondVisit) throws ShadowException {		
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		Type type = node.getType();	// this is set in the FieldAndMethodChecker
		
		for(int i=1; i < node.jjtGetNumChildren(); ++i)
		{
			Node curVarDec = node.jjtGetChild(i);
			
			if(curVarDec.jjtGetNumChildren() == 2) 
			{
				Node curVarInit = curVarDec.jjtGetChild(1);
				Type initType = curVarInit.getType();				
		
				if(!initType.isSubtype(type))
					addError(curVarInit, Error.TYPE_MIS, "Cannot assign " + initType + " to " + type);
			}
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	
	private boolean checkField( Node node, String fieldName, Type prefixType, boolean isStatic )
	{
		if( prefixType instanceof ClassInterfaceBaseType )
		{
			ClassInterfaceBaseType currentClass = (ClassInterfaceBaseType)prefixType;
			if( currentClass.containsField( fieldName ) )
			{
				Node field = currentClass.getField(fieldName);
				
				if( !fieldIsAccessible( field, currentType ))				
					addError(node, Error.INVL_MOD, "Field " + fieldName + " not accessible from current context");
				else if( isStatic && !ModifierSet.isStatic(field.getModifiers())   )
					addError(node, Error.INVL_MOD, "Cannot access non-static field " + fieldName + " from static context");
				else
				{
					node.setType( field.getType());
					node.setModifiers(field.getModifiers());
					node.addModifier(ModifierSet.ASSIGNABLE);
					return true;
				}							
			}
			else
			{
				List<MethodSignature> methods = currentClass.getMethods(fieldName);
				
				//unbound method (it gets bound when you supply args
				if( methods != null && methods.size() > 0 )
				{
					node.setType( new UnboundMethodType( fieldName, currentClass ) );
					return true;					
				}
				else
					addError(node, Error.UNDEC_VAR, "Member " + fieldName + " not found");
			}			
		}
		else
			addError(node, Error.INVL_TYP, prefixType + " not valid class or interface");
		
		node.setType( Type.UNKNOWN ); //if got here, some error		
		return false;
	}
	
	public boolean setTypeFromContext( Node node, String name, Type context, boolean directAccess  ) //directAccess is true if there is no prefix and false if there is
	{
		if( context instanceof InterfaceType )
		{			
			InterfaceType interfaceType = (InterfaceType)context;
			
			if( interfaceType.recursivelyContainsMethod( name ) )
			{
				node.setType( new UnboundMethodType( name, interfaceType ) );				
				return true;
			}			
		}
		else if( context instanceof ClassType )
		{
			ClassType classType = (ClassType)context;
					
			if(classType.recursivelyContainsField(name))
			{
				Node field = classType.recursivelyGetField(name);
				node.setType(field.getType());
				node.setModifiers(field.getModifiers());
				node.addModifier(ModifierSet.ASSIGNABLE);
				
				if( ModifierSet.isPrivate(field.getModifiers()) && currentType != classType   )
					addError(node, Error.INVL_MOD, "Cannot access private variable " + field.getImage());						
				
				
				if( curMethod != null ) //curMethod is null for field initializations
				{
					if( directAccess && ModifierSet.isStatic(curMethod.getModifiers()) && !ModifierSet.isStatic(node.getModifiers()) )
						addError(node, Error.INVL_MOD, "Cannot access non-static member " + name + " from static method " + curMethod);
				}
				
				return true;			
			}
				
			if( classType.recursivelyContainsMethod(name))
			{
				node.setType( new UnboundMethodType( name, classType ) );				
				return true;
			}
			
			if( classType.recursivelyContainsInnerClass(name))
			{
				Type innerClass = classType.recursivelyGetInnerClass(name);
				node.setType(innerClass);
				node.setModifiers(ModifierSet.TYPE_NAME);
				return true;
			}
		}

		return false;
	}
	
	
	public boolean setTypeFromName( Node node, String name ) 
	{	
		//is it a type?
		Type type = lookupType( name );		
				
		if(type != null)
		{
			((ClassType)currentType).addReferencedType(type);
			node.setType(type);
			return true;
		}
		
		// next go through the scopes trying to find the variable
		Node declaration = findSymbol( name );
		
		if( declaration != null ) 
		{
			node.setType(declaration.getType());
			node.setModifiers(declaration.getModifiers());
			node.addModifier(ModifierSet.ASSIGNABLE);
			return true;
		}
			
		// now check the parameters of the method
		MethodType methodType = null;
		
		if( curMethod != null  )
			methodType = (MethodType)curMethod.getType();
		
		if(methodType != null && methodType.containsParam(name))
		{	
			node.setType(methodType.getParameterType(name).getType());
			node.setModifiers(methodType.getParameterType(name).getModifiers());
			node.addModifier(ModifierSet.ASSIGNABLE);
			return true;
		}
			
		// check to see if it's a field or a method			
		return setTypeFromContext( node, name, currentType, true );
	}
	
	
	
	public Object visit(ASTName node, Boolean secondVisit) throws ShadowException 
	{			
		String[] references = node.getImage().split("\\.");
								
		int i = 0;
 		
		Type type = lookupType( node.getImage() );
				
		if( type == null)
		{ 
			addError(node, Error.UNDEC_VAR, references[i]);
			node.setType(Type.UNKNOWN);
			ASTUtils.DEBUG(node, "DIDN'T FIND: " + references[i]);
		}
		else
			node.setType(type);

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
				node.setType(Type.UNKNOWN);
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
				node.setType(Type.UNKNOWN);
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
					node.setType(Type.UNKNOWN);
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
					node.setType(Type.UNKNOWN);
					return;
				}
				else
					result = Type.STRING;
			}
			else if( result.isNumerical() && current.isNumerical() )
			{
				if( result.isSubtype( current ))  //upgrades type to broader type (e.g. int goes to double)
					result = current;
				else if( !current.isSubtype(result) )
				{
					addError(node.jjtGetChild(i), Error.INVL_TYP, "Cannot apply arithmetic operations to " + result + " and " + current);
					node.setType(Type.UNKNOWN);
					return;					
				}					
			}
			else		
			{
				addError(node.jjtGetChild(i), Error.INVL_TYP, "Cannot apply arithmetic operations to " + result + " and " + current);
				node.setType(Type.UNKNOWN);
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
				
		Type type = node.jjtGetChild(0).getType();
		String symbol = node.getImage();
		
		if( (symbol.equals("-") || symbol.equals("+")) ) 
		{
			if( !type.isNumerical() )
			{
				addError(node, Error.INVL_TYP, "Found type " + type + ", but numerical type required for arithmetic operations");
				node.setType(Type.UNKNOWN);
				return WalkType.POST_CHILDREN;
			}
		}
		else
			pushUpModifiers( node );
		
		if(symbol.startsWith("-"))
			node.setType(Type.makeSigned((ClassType)type));
		else
			node.setType(type);
		
		return WalkType.POST_CHILDREN;
	}
		
	public Object visit(ASTUnaryExpressionNotPlusMinus node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		Type type = node.jjtGetChild(0).getType();
		
		if(node.getImage().startsWith("~") )
		{
			if( !type.isIntegral() )
			{
				addError(node, Error.INVL_TYP, "Found type " + type + ", but integral type required for bitwise operations");
				type = Type.UNKNOWN;				
			}
		}		
		else if(node.getImage().startsWith("!") )
		{
			if( !type.equals(Type.BOOLEAN)) 
			{
				addError(node, Error.INVL_TYP, "Found type " + type + ", but boolean type required for logical operations");
				type = Type.UNKNOWN;				
			}
		}
		else
			pushUpModifiers( node ); //can add ASSIGNABLE (if only one child)
		
		node.setType(type);		
		return WalkType.POST_CHILDREN;
	}
	
	
	public Object visit(ASTConditionalExpression node, Boolean secondVisit) throws ShadowException {
		if( !secondVisit )
			return WalkType.POST_CHILDREN;
		
		if(node.jjtGetNumChildren() == 1)
			pushUpType( node, secondVisit ); //propagate type and modifiers up
		else if(node.jjtGetNumChildren() == 3)
		{			
			Type t1 = node.jjtGetChild(0).getType();
			Type t2 = node.jjtGetChild(1).getType();
			Type t3 = node.jjtGetChild(2).getType();
			
			if( !t1.equals(Type.BOOLEAN) ) 
			{			
				addError(node.jjtGetChild(0), Error.INVL_TYP, "Found type" + t1 + ", but boolean type required for conditional operations");
				node.setType(Type.UNKNOWN);
			}
			else if( t2.isSubtype(t3) )
				node.setType(t3);
			else if( t3.isSubtype(t2) )
				node.setType(t2);
			else 
			{
				addError(node, Error.TYPE_MIS, "Type " + t2 + " must match " + t3 + " in ternary operator");
				node.setType(Type.UNKNOWN);
			}
		}		
		
		return WalkType.POST_CHILDREN;
	}
	
	public void visitConditional(SimpleNode node ) throws ShadowException {
		
		if( node.jjtGetNumChildren() == 1 )
			pushUpType(node, true); //includes modifier push up
		else
		{
			Type result = null;
			
			for( int i = 0; i < node.jjtGetNumChildren(); i++ ) //cycle through types, upgrading to broadest legal one
			{
				result = node.jjtGetChild(i).getType();
			
				if( result != Type.BOOLEAN )
				{
					addError(node.jjtGetChild(i), Error.INVL_TYP, "Found type " + result + ", but boolean type required for conditional operations");			
					node.setType(Type.UNKNOWN);
					return;
				}					
			}
			
			node.setType(result);
		}
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


	public void visitBitwise(SimpleNode node ) throws ShadowException {
				
		if( node.jjtGetNumChildren() == 1 )
			pushUpType(node, true); //includes modifier push up
		else
		{
			Type result = node.jjtGetChild(0).getType();
			if( !result.isIntegral() )
			{
				addError(node.jjtGetChild(0), Error.INVL_TYP, "Found type " + result + ", but integral type required for shift and rotate operations");
				node.setType(Type.UNKNOWN);
				return;
			}
			
			for( int i = 1; i < node.jjtGetNumChildren(); i++ ) //cycle through types, upgrading to broadest legal one
			{
				Type current = node.jjtGetChild(i).getType();
			
				if( !current.isIntegral() )
				{
					addError(node.jjtGetChild(i), Error.INVL_TYP, "Found type " + current + ", but integral type required for shift and rotate operations");
					node.setType(Type.UNKNOWN);
					return;
				}
				
				if( current.isSubtype(result))
					result = current;
			}
			
			node.setType(result);
		}
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
				node.setType(Type.UNKNOWN);
				return WalkType.NO_CHILDREN;
			}
			
			//ASTAssignmentOperator op = (ASTAssignmentOperator)node.jjtGetChild(1);  //leave it for the TAC?
			
			Node child2 = node.jjtGetChild(2);
			Type t2 = child2.getType();
			
			// SHOULD DO SOMETHING WITH THIS!!!
			//AssignmentType assType = op.getAssignmentType(); //leave it for the TAC?
			

			
			// TODO: Add in all the types that we can compare here
			if( !t2.isSubtype(t1) )
			{
				addError(child1, Error.TYPE_MIS, "Found type " + t2 + ", type " + t1 + " required");
				node.setType(Type.UNKNOWN);
				return WalkType.NO_CHILDREN;
			}
					
			node.setType(t1);	// set this node's type
			node.setModifiers( child2.getModifiers() ); //is this meaningful?
		}

		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTClassOrInterfaceType node, Boolean secondVisit) throws ShadowException {
		if( node.getType() != null ) //optimization if type already determined by FieldAndMethodChecker
			return WalkType.NO_CHILDREN;		
		
		if(!secondVisit)
			return WalkType.POST_CHILDREN;

		String typeName = node.getImage();
		Type type = lookupType(typeName);
		
		if(type == null)
		{
			addError(node, Error.UNDEF_TYP, typeName);
			type = Type.UNKNOWN;
		}
		else if (currentType instanceof ClassType)
			((ClassType)currentType).addReferencedType(type);
				
		node.setType(type);
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTAllocationExpression node, Boolean secondVisit) throws ShadowException 
	{
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		//check curPrefix at some point		
		Node child = node.jjtGetChild(0);
		
		if( child instanceof ASTArrayAllocation ) //array allocation		
			node.setType(child.getType());				
		else //constructor invocation
			node.setType(child.jjtGetChild(0).getType());  //set type of allocation expression to class type			
		
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
		
		if( child instanceof ASTSequence && child.getType() instanceof SequenceType ) //second check used for sequences containing a single item
																					  //(which are treated as sequences by the parser but should 
																					  //be regarded as single values by the typechecker
		{			
			Node current = child;			
		
			for( int i = 1; i < node.jjtGetNumChildren(); i++ )
			{	
				if( !(current instanceof ASTSequence) || !(((ASTSequence)current).isAssignable()) )
				{
					addError(child, Error.TYPE_MIS, "Cannot assign a value to expression: " + child);
					break;
				}
				
				SequenceType currentType = (SequenceType)(current.getType());
				Node next = node.jjtGetChild(i);
				if( next.getType() instanceof SequenceType )
				{
					SequenceType nextType = (SequenceType)(next.getType());
					if( currentType.canAccept( nextType.getTypes() ) )
						current = next;
					else
					{
						addError(current, Error.TYPE_MIS, "Sequence " + nextType + " does not match " + current);
						break;
					}	
				}
				else
					addError(current, Error.TYPE_MIS, next.getType() + "must be of sequence type");
			}
		}
		else //primary expression
		{
			if( node.jjtGetNumChildren() == 3 ) //only need to proceed if there is assignment
			{
				//ASTAssignmentOperator op = (ASTAssignmentOperator)node.jjtGetChild(1);
				//Leave it for the TAC?
				Type t1 = child.getType();
				Type t2 = node.jjtGetChild(2).getType();
				
				if( !ModifierSet.isAssignable(child.getModifiers()) )
					addError(child, Error.TYPE_MIS, "Cannot assign a value to expression: " + child);
				else if( ModifierSet.isFinal(child.getModifiers()) )
					addError(child, Error.INVL_TYP, "Cannot assign a value to variable marked final");
				else					
				{
					// SHOULD DO SOMETHING WITH THIS!!!
					//AssignmentType assType = op.getAssignmentType();
					//Leave it for the TAC?
					
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
			{
				addError(node, Error.TYPE_MIS, "Type " + t1 + " uncomparable with type " + t2);
				node.setType(Type.UNKNOWN);
			}
		}			

		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTFunctionType node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		MethodType methodType = new MethodType();		
		
		int children = node.jjtGetNumChildren(); 
		if( children > 0 ) //fill in parameters
		{		
			for( int i = 0; i < children - 1; i++ )
				methodType.addParameter(node.jjtGetChild(i));
			
			Node last = node.jjtGetChild(children - 1); 
			
			if(  last instanceof ASTResultTypes ) //if last child is results, add those
			{
				ASTResultTypes results = (ASTResultTypes)last;
				for( Type type : results.getTypes() )
					methodType.addReturn(type);
			}
			else //otherwise everything was a parameter
				methodType.addParameter(last);			
		}
		
		node.setType(methodType);
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTResultTypes node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
	
		for( int i = 0; i < node.jjtGetNumChildren(); i++ )
			node.addType(node.jjtGetChild(i).getType());

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
			
			
			if( t1 instanceof MethodType && t2 instanceof UnboundMethodType ) //casting methods
			{
				MethodType method = (MethodType)t1;
				UnboundMethodType unboundMethod = (UnboundMethodType)t2;				
				
				boolean found = false;
				
								
				ClassInterfaceBaseType outer = (ClassInterfaceBaseType)unboundMethod.getOuter();				
				for( MethodSignature signature : outer.getMethods(unboundMethod.getTypeName()) )
					if( signature.getMethodType().matchesModifiedTypes( method.getParameterTypes()) && signature.getMethodType().canReturn(method.getReturnTypes()))
					{
						node.setType(signature.getMethodType());
						found = true;
						break;
					}
					
				if( !found )
				{
					addError(node, Error.TYPE_MIS, "Cannot cast: No method " + unboundMethod.getTypeName() + " matches signature " + method);
					node.setType(Type.UNKNOWN);
				}				
			}			
			else if( t1.isNumerical() && t2.isNumerical() ) //some numerical types (int and uint) are not superclasses or subclasses of each other
															//for convenience, all numerical types should be castable
				node.setType(t1);
			else if( t1.isSubtype(t2) || t2.isSubtype(t1) )
				node.setType(t1);
			else
			{
				addError(node, Error.TYPE_MIS, "Type " + t2 + " cannot be cast to " + t1);
				node.setType(Type.UNKNOWN);
			}
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
	
	
	public Object visit(ASTTryStatement node, Boolean secondVisit) throws ShadowException 
	{		
		if(secondVisit)
		{
			if( node.getBlocks() == 0 )
				addError( node, Error.TYPE_MIS, "try statement must have at least one catch, recover, or finally block" );
			else
			{				
				Node child;
				List<Type> types = new LinkedList<Type>();
				
				for( int i = 0; i < node.getCatches(); i++ )				
				{
					//catch statement
					child = node.jjtGetChild(2*i); //formal parameter
					
					Type type = child.getType();
					if( type.getKind() == Kind.EXCEPTION )
					{
						for( Type existing : types )
							if( type.isSubtype(existing) )
							{
								addError( child, Error.TYPE_MIS, "unreachable catch: " + type );
								break;
							}						
					}
					else
						addError( child, Error.TYPE_MIS, "found " + type + "but only exception types allowed for catch parameters");
				}
				
				//no checking necessary for recover
				
				//no checking necessary for finally 
				
				tryBlocks.removeFirst();
			}						
		}	
		else
			tryBlocks.addFirst(node);
			
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTCheckExpression node, Boolean secondVisit) throws ShadowException 
	{
		if( secondVisit )
		{
			Node child = node.jjtGetChild(0);
			
			if( ModifierSet.isNullable(child.getModifiers()) )				
				node.setModifiers( ModifierSet.removeModifier(child.getModifiers(), ModifierSet.NULLABLE  ));			
			else
				addError( node, Error.TYPE_MIS, "check expression can only be used on an expression with a nullable type");
			
			node.setType(child.getType());
		}
		else
		{
			boolean found = false;
			for( ASTTryStatement statement : tryBlocks )
			{
				if( statement.hasRecover() )
				{
					found = true;
					break;
				}				
			}
			
			if( !found )
				addError( node, Error.TYPE_MIS, "check expression not inside of try statement with recover block");
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
		else								//allocation or just prefix
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
				{
					addError(node, Error.INVL_TYP, "Reference this invalid for interfaces");
					node.setType(Type.UNKNOWN);
				}
				else
				{				
					node.setType(currentType);				
				
					if( curMethod != null && ModifierSet.isStatic(curMethod.getModifiers())  )					
						addError(node, Error.INVL_MOD, "Cannot access non-static reference this from static method " + curMethod);
				}					
			}
			else if( node.getImage().startsWith("super.")) //super case
			{
				if( currentType instanceof ClassType )
				{
					String name = node.getImage().substring(6);  //removes "super."
					
					if( !setTypeFromContext( node, name, ((ClassType)currentType).getExtendType(), true )) //automatically sets type if can
					{
						addError(node, Error.UNDEC_VAR, name);
						node.setType(Type.UNKNOWN);
						ASTUtils.DEBUG(node, "DIDN'T FIND: " + name);						
					}
				}
				else
				{
					addError(node, Error.INVL_TYP, "Reference super must be used inside of class, enum, error, or exception"); //will this ever happen?
					node.setType(Type.UNKNOWN);
				}
			}
			else //just a name
			{
				String name = node.getImage();
				if( !setTypeFromName( node, name )) //automatically sets type if can
				{
					addError(node, Error.UNDEC_VAR, name);
					node.setType(Type.UNKNOWN);
					ASTUtils.DEBUG(node, "DIDN'T FIND: " + name);						
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
				else
				{
					addError(node, Error.INVL_TYP, ".class member only accessible on class, enum, error, or exception types"); //may need other cases
					node.setType(Type.UNKNOWN);
				}
			}
			else if( child instanceof ASTMethodCall && child.getType() != Type.UNKNOWN )
			{
				MethodType type = (MethodType)(child.getType());
				List<Type> returnTypes = type.getReturnTypes();
				if( returnTypes.size() == 1 )
					node.setType( returnTypes.get(0));
				else
					node.setType( new SequenceType( returnTypes ));
			}
			else if( child instanceof ASTUnqualifiedName )
			{
				node.setImage(child.getImage() + "@" + node.getImage());
				String name = node.getImage();
				if( !setTypeFromName( node, name )) //automatically sets type if can
				{
					addError(node, Error.UNDEC_VAR, name);
					node.setType(Type.UNKNOWN);
					ASTUtils.DEBUG(node, "DIDN'T FIND: " + name);						
				}
			}
			else
			{
				node.setType( child.getType() ); 	//literal, conditionalexpression, allocation expression, check expression
				pushUpModifiers( node ); 			
			}
		}
		
		curPrefix.set(0, node); //so that the suffix can figure out where it's at
		
		/* OLD
		  Literal()
		  | "this" { jjtThis.setImage("this"); }
		  | "super" "." t = <IDENTIFIER> { jjtThis.setImage(t.image); }
		  | LOOKAHEAD( "(" ConditionalExpression() ")" ) "(" ConditionalExpression() ")"
		  | AllocationExpression()
		  | LOOKAHEAD( ResultType() "." "class" ) ResultType() "." "class"
		  | Name()
		*/
		
		
		/* NEW
		  Literal()
			| "this" { jjtThis.setImage("this"); }
			| "super" "." t = <IDENTIFIER> { jjtThis.setImage(t.image); }
			| LOOKAHEAD( "(" ConditionalExpression() ")" ) "(" ConditionalExpression() ")"
			| AllocationExpression()
			| LOOKAHEAD( ResultType() "." "class" ) ResultType() "." "class"
			//| TypeArguments() t = <IDENTIFIER> { jjtThis.setImage(t.image); } MethodCall()
			| LOOKAHEAD(2) MethodCall()
			| t = <IDENTIFIER> { jjtThis.setImage(t.image); debugPrint(t.image); }
		 */
				
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTMethodCall node, Boolean secondVisit) throws ShadowException 
	{
		//t = <IDENTIFIER> { jjtThis.setImage(t.image); } "(" [ ConditionalExpression() ( "," ConditionalExpression() )* ] ")"
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		String name = node.getImage();
		Type context;
		boolean directAccess;
		
		if( node.jjtGetParent() instanceof ASTPrimaryPrefix ) //not part of some chain of references
		{
			context = currentType;
			directAccess = true;			
		}
		else //ASTPrimarySuffix: already in the middle of references
		{
			context = curPrefix.getFirst().getType();
			directAccess = false;
		}
		
		if( !setTypeFromContext( node, name, context, directAccess )) //automatically sets type if can
		{
			addError(node, Error.UNDEC_VAR, name);
			node.setType(Type.UNKNOWN);
			ASTUtils.DEBUG(node, "DIDN'T FIND: " + name);
		}
		else if( node.getType() instanceof UnboundMethodType )
		{
			UnboundMethodType unboundMethod = (UnboundMethodType)(node.getType());
			List<Type> typeList = new LinkedList<Type>();
			for( int i = 0; i < node.jjtGetNumChildren(); i++ )
				typeList.add(node.jjtGetChild(i).getType());
			
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
					else
						node.setType(signature.getMethodType());
				}
				else if( signature.canAccept(typeList))
					acceptableMethods.add(signature);
			}
			
			if( !perfectMatch )
			{
				if( acceptableMethods.size() == 0 )	
				{
					node.setType(Type.UNKNOWN);						
					addError(node, Error.TYPE_MIS, "No method found with signature " + typeList);
				}
				else if( acceptableMethods.size() > 1 )
				{
					node.setType(Type.UNKNOWN);						
					addError(node, Error.TYPE_MIS, "Ambiguous method call with signature " + typeList);
				}							
				else
				{
					MethodSignature signature = acceptableMethods.get(0); 
					List<Type> returnTypes = signature.getMethodType().getReturnTypes();
					if( returnTypes.size() == 1 )
						node.setType(returnTypes.get(0));
					else
					{
						SequenceType sequenceType = new SequenceType( returnTypes );						
						node.setType( sequenceType );
					}							
					
					if( !methodIsAccessible( signature, currentType  ))
						addError(node, Error.INVL_MOD, "Method " + signature + " not accessible from current context");
					else
						node.setType(signature.getMethodType());
				}
			}					
		}
		else
		{									
			addError(node, Error.TYPE_MIS, "Cannot apply arguments to non-method type " + node.getType());
			node.setType(Type.UNKNOWN);			
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	
	public Object visit(ASTPrimarySuffix node, Boolean secondVisit) throws ShadowException 
	{
		
		/* NEW
		   LOOKAHEAD(2) "." "this" { jjtThis.setImage("this"); } // when does this even happen?
			| "[" ConditionalExpression() ("," ConditionalExpression())* "]"
			//| "." TypeArguments() t = <IDENTIFIER> { jjtThis.setImage(t.image); } MethodCall()
			| LOOKAHEAD(3) "." MethodCall()
			| "." t = <IDENTIFIER> { jjtThis.setImage(t.image); debugPrint(t.image); }
		 */
		
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
				
		Node prefixNode = curPrefix.getFirst();
		Type prefixType = prefixNode.getType();
		
		int children = node.jjtGetNumChildren();
		
		if(  children == 0 )
		{
			if( node.getImage().equals("this") )				
				node.setType(prefixType);			
			else //field name
				checkField( node, node.getImage(), prefixType, ModifierSet.isTypeName(prefixNode.getModifiers()) );
		}
		else // >= 1
		{
			Node child = node.jjtGetChild(0); 
			
		
			/*else if( child instanceof ASTMemberSelector )
			{
				addError(node, Error.INVL_TYP, "Generics are not yet handled");	
				node.setType(Type.UNKNOWN);
			}*/
			if( child instanceof ASTConditionalExpression ) //array index
			{
				if( prefixType instanceof ArrayType )
				{
					ArrayType arrayType = (ArrayType)prefixType;
					
					for( int i = 0; i < node.jjtGetNumChildren(); i++ )
					{
						Type childType = node.jjtGetChild(0).getType();
						
						if( !childType.isIntegral() )
						{
							addError(node.jjtGetChild(i), Error.INVL_TYP, "Found type " + childType + ", but integral type required for array subscript");
							node.setType(Type.UNKNOWN);
						}
					}
					
					if( node.getType() != Type.UNKNOWN )
					{
						if( node.jjtGetNumChildren() == arrayType.getDimensions() )
						{
							node.setType( arrayType.getBaseType() );
							node.addModifier(ModifierSet.ASSIGNABLE);
						}
						else
						{
							node.setType(Type.UNKNOWN);
							addError(node, Error.TYPE_MIS, "Needed "  + arrayType.getDimensions() + " indexes into array but found " +  node.jjtGetNumChildren());
						}
					}
				}
				else
				{
					node.setType(Type.UNKNOWN);
					addError(node, Error.INVL_TYP, "Cannot subscript into non-array type " + prefixType);
				}
			}
			else //MethodCall
			{
				//can look like a method call but not have a real method connected to it
				if( child.getType() instanceof MethodType )
				{
					MethodType type = (MethodType)(child.getType());
					List<Type> returnTypes = type.getReturnTypes();
					if( returnTypes.size() == 1 )
						node.setType( returnTypes.get(0));
					else
						node.setType( new SequenceType( returnTypes ));
				}
				else
					node.setType(Type.UNKNOWN);
			}
			
		}
		
		curPrefix.set(0, node); //so that a future suffix can figure out where it's at
		
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
		return fieldIsAccessible( signature.getASTNode(), type );
	}

	
	/*//Push up is wrong, the children of ResultTypes are many, we can't just push one up 
	public Object visit(ASTResultTypes node, Boolean secondVisit) throws ShadowException 
	{ return pushUpType(node, secondVisit); 
	
	}
	
	*/
	
	public Object visit(ASTLabeledStatement node, Boolean secondVisit) throws ShadowException 
	{ 
		if(!secondVisit)
		{
			String label = node.getImage();
			if(findSymbol(label) != null || labels.contains(label))
				addError(node, Error.MULT_SYM, label);
			else
				labels.push(node);	
		}
		else
			labels.pop();
			
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTBreakStatement node, Boolean secondVisit) throws ShadowException 
	{ 
		if( !node.getImage().isEmpty() )
		{
			for( Node label : labels )			
				if( label.equals(node.getImage()) )
					return WalkType.PRE_CHILDREN;
				
			addError(node, Error.UNDEC_VAR, "No matching label for break statement");			
		}
			
		return WalkType.PRE_CHILDREN;
	}
	
	public Object visit(ASTArrayDimsAndInits node, Boolean secondVisit) throws ShadowException 
	{		
		if( secondVisit )
		{
			Node child = node.jjtGetChild(0);
			if( child instanceof ASTArrayInitializer )
			{
				//finish this!!!			
				
			}
			else
			{
				for( int i = 0; i < node.jjtGetNumChildren(); i++ )
				{
					child = node.jjtGetChild(i); 
					if( !child.getType().isNumerical() )
					{
						addError(child, Error.INVL_TYP, "Numerical type must be specified for array dimensions");				
						break;
					}
				}
			}
		}
			
	
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTArrayInitializer node, Boolean secondVisit) throws ShadowException 
	{		
		if( secondVisit )
		{
			Node child = node.jjtGetChild(0);
			Type result = child.getType();
			
			for( int i = 1; i < node.jjtGetNumChildren(); i++ ) //cycle through types, upgrading to broadest legal one
			{
				child = node.jjtGetChild(i);
				Type type = child.getType();
								
				if( type.isSubtype(result) )					
					result = type;				
				else if( !result.isSubtype(type) ) //neither is subtype of other, panic!
				{
					addError(node, Error.INVL_TYP, "Types in array initializer list do not match");
					result = Type.UNKNOWN;
					break;
				}
			}
			
			if( result == Type.UNKNOWN )
				node.setType(result);
			else
			{
				ArrayList<Integer> dimensions = new ArrayList<Integer>();
				Type baseType;
				if( result instanceof ArrayType )
				{
					ArrayType arrayType = (ArrayType)result;
					dimensions.add(arrayType.getDimensions() + 1 );
					baseType = arrayType.getBaseType();
				}
				else
				{
					dimensions.add(1);
					baseType = result;
				}				
				node.setType(new ArrayType( baseType, dimensions ));
			}			
		}		
	
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTAssertStatement node, Boolean secondVisit) throws ShadowException 
	{
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
	
		Type assertType = node.jjtGetChild(0).getType();
		
		if( !assertType.equals(Type.BOOLEAN))
			addError(node, Error.INVL_TYP, "Found type " + assertType + ", but boolean type required for assert condition");
		
		if( node.jjtGetNumChildren() > 1 )
		{
			Node child = node.jjtGetChild(1);
			Type type = child.getType();
			if( type == null )
				addError(node, Error.INVL_TYP, "Value type required for assert information and no type found");
			else if( type instanceof ClassInterfaceBaseType )
			{
				if( ModifierSet.isTypeName(child.getModifiers()) )
					addError(node, Error.INVL_TYP, "Value type required for assert information but type name used");				
			}
			else if( !(type instanceof ArrayType) )
				addError(node, Error.INVL_TYP, "Value type required for assert information and " + type + " found");			
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTImportDeclaration node, Boolean secondVisit) throws ShadowException {
		currentPackage = node.getPackage();		
		return WalkType.NO_CHILDREN;
	}
	
	@Override
	public Object visit(ASTCompilationUnit node, Boolean secondVisit) throws ShadowException {
		if( !secondVisit )
			currentPackage = packageTree;
		
		return WalkType.POST_CHILDREN;			
	}
	
	@Override
	public Object visit(ASTLiteral node, Boolean secondVisit) throws ShadowException {
		Type type = null;		
		switch( node.getLiteral() )
		{
		case BYTE: 		type = Type.BYTE; break;
		case CODE: 		type = Type.CODE; break;
		case SHORT: 	type = Type.SHORT; break;
		case INT: 		type = Type.INT; break;
		case LONG:		type = Type.LONG; break;
		case FLOAT: 	type = Type.FLOAT; break;
		case DOUBLE: 	type = Type.DOUBLE; break;
		case STRING:	type = Type.STRING; break;
		case UBYTE: 	type = Type.UBYTE; break;
		case USHORT: 	type = Type.USHORT; break;
		case UINT: 		type = Type.UINT; break;
		case ULONG: 	type = Type.ULONG; break;
		case BOOLEAN: 	type = Type.BOOLEAN; break;
		case NULL: 		type = Type.NULL; break;
		}
		
		node.setType(type);
		
		return WalkType.NO_CHILDREN;			
	}

	@Override
	public Object visit(ASTArrayAllocation node, Boolean secondVisit) throws ShadowException
	{
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		//check curPrefix at some point		
		Node child = node.jjtGetChild(0);
		
		int counter = 1;
		
		if( child instanceof ASTClassOrInterfaceType && node.jjtGetChild(counter) instanceof ASTTypeArguments ) //reference array might have type arguments
		{
			//for now
			addError(node.jjtGetChild(counter), Error.INVL_TYP, "Generics are not yet handled");
			node.setType(Type.UNKNOWN);
			counter++;				
		}
			
		//array dims and inits
		List<Integer> dimensions = ((ASTArrayDimsAndInits)(node.jjtGetChild(counter))).getArrayDimensions();
		node.setType(new ArrayType(child.getType(), dimensions));
		
		return WalkType.POST_CHILDREN;
	}

	@Override
	public Object visit(ASTConstructorInvocation node, Boolean secondVisit)	throws ShadowException
	{
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		//check curPrefix at some point		
		Node child = node.jjtGetChild(0);
						
		int counter = 1;
		
		if( node.jjtGetChild(counter) instanceof ASTTypeArguments ) //may have type arguments
		{
			//for now
			addError(node.jjtGetChild(counter), Error.INVL_TYP, "Generics are not yet handled");
			node.setType(Type.UNKNOWN);
			counter++;				
		}

		if( child.getType() instanceof InterfaceType )
		{
			addError(child, Error.INVL_TYP, "Interfaces cannot be instantiated");
			node.setType(Type.UNKNOWN);
			return WalkType.POST_CHILDREN;
		}
		
		//examine argument list to find constructor		
		ClassInterfaceBaseType type = (ClassInterfaceBaseType)child.getType();
		List<Type> typeList = ((ASTArguments)(node.jjtGetChild(counter))).getTypeList();
		List<MethodSignature> candidateConstructors = type.getMethods("constructor");
		
		// we don't have implicit constructors, so need to check if the default constructor is OK
		if(typeList.size() == 0 && candidateConstructors == null)
		{
			node.setType(child.getType());
			return WalkType.POST_CHILDREN;
		}		
		// we have no constructors, but they are calling with params
		else if(typeList.size() > 0 && candidateConstructors == null)
		{
			addError(child, Error.TYPE_MIS, "No constructor found with signature " + typeList);
			node.setType(Type.UNKNOWN);
			return WalkType.POST_CHILDREN;
		}
		else
		{
			// by the time we get here, we have a constructor list
			List<MethodSignature> acceptableConstructors = new LinkedList<MethodSignature>();
			
			for( MethodSignature signature : candidateConstructors ) 
			{
				if( signature.matches( typeList )) 
				{
					node.setType(child.getType());
					node.setType(signature.getMethodType());
					return WalkType.POST_CHILDREN;
				}
				else if( signature.canAccept(typeList))
					acceptableConstructors.add(signature);
			}
			
			if( acceptableConstructors.size() == 0 ) 
			{
				addError(child, Error.TYPE_MIS, "No constructor found with signature " + typeList);
				node.setType(Type.UNKNOWN);
			}					
			else if( acceptableConstructors.size() > 1 )
			{
				addError(child, Error.TYPE_MIS, "Ambiguous constructor call with signature " + typeList);
				node.setType(Type.UNKNOWN);
			}					
			else
			{
				MethodSignature signature = acceptableConstructors.get(0); 
				
				if( !methodIsAccessible( signature, currentType  ))
					addError(node, Error.INVL_MOD, "Constructor " + signature + " not accessible from current context");
				else
					node.setType(signature.getMethodType());
			}				
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit)	throws ShadowException
	{
		createScope(secondVisit); //scope is created purely to hold type parameters (other declarations are kept in the body scope)		
		return WalkType.POST_CHILDREN;
	}
	
	

	//
	// Everything below here are just visitors to push up the type
	//
	public Object visit(ASTType node, Boolean secondVisit) throws ShadowException { return pushUpType(node, secondVisit); }
	public Object visit(ASTVariableInitializer node, Boolean secondVisit) throws ShadowException {
		return pushUpType(node, secondVisit); 
	}
	public Object visit(ASTResultType node, Boolean secondVisit) throws ShadowException { return pushUpType(node, secondVisit); }
	
}
