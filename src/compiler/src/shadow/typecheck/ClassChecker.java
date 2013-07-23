package shadow.typecheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import shadow.TypeCheckException;
import shadow.AST.ASTUtils;
import shadow.AST.ASTWalker;
import shadow.AST.ASTWalker.WalkType;
import shadow.TypeCheckException.Error;
import shadow.parser.javacc.*;
import shadow.parser.javacc.ASTAssignmentOperator.AssignmentType;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.EnumType;
import shadow.typecheck.type.ExceptionType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.PropertyType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;
import shadow.typecheck.type.UnboundMethodType;


//no automatic promotion for bitwise operators

public class ClassChecker extends BaseChecker 
{	

	protected LinkedList<Node> curPrefix = null; 	/** Stack for current prefix (needed for arbitrarily long chains of expressions) */
	protected LinkedList<Node> labels = null; 	/** Stack of labels for labeled break statements */
	protected LinkedList<ASTTryStatement> tryBlocks = null; /** Stack of try blocks currently nested inside */	
	protected LinkedList<HashMap<String, ModifiedType>> symbolTable; /** List of scopes with a hash of symbols & types for each scope */
	protected LinkedList<Node> scopeMethods; /** Keeps track of the method associated with each scope (sometimes null) */
	
	public ClassChecker(boolean debug, HashMap<Package, HashMap<String, Type>> typeTable, List<String> importList, Package packageTree ) {
		super(debug, typeTable, importList, packageTree );		
		symbolTable = new LinkedList<HashMap<String, ModifiedType>>();
		curPrefix = new LinkedList<Node>();			
		tryBlocks = new LinkedList<ASTTryStatement>();
		scopeMethods = new LinkedList<Node>();
	}
	
	public void checkClass(Node node) throws ShadowException, TypeCheckException
	{
		ASTWalker walker = new ASTWalker(this);
		
		// now go through and check the whole class
		walker.walk(node);
		
		if( errorList.size() > 0 )
		{
			printErrors();
			throw errorList.get(0);
		}	
	}
	
	//Important!  Set the current type on entering the body, not the declaration, otherwise extends and imports are improperly checked with the wrong outer class
	public Object visit(ASTClassOrInterfaceBody node, Boolean secondVisit) throws ShadowException {		
		if( secondVisit )
			currentType = currentType.getOuter();		
		else
			currentType = node.jjtGetParent().getType(); //get type from declaration
			
		return WalkType.POST_CHILDREN;
	}

	public Object visitMethod( SignatureNode node, Boolean secondVisit  )
	{
		if(!secondVisit)
		{			
			if( node instanceof ASTLocalMethodDeclaration || node instanceof ASTInlineMethodDeclaration || node instanceof ASTInlineMethodDefinition )
			{				
				MethodSignature signature;
				
				if( node instanceof ASTInlineMethodDefinition )
					signature = new MethodSignature( currentType, "", node.getModifiers(), node);
				else
					signature = new MethodSignature( currentType, node.jjtGetChild(0).getImage(), node.getModifiers(), node);
				node.setMethodSignature(signature);
				MethodType methodType = signature.getMethodType();

				if( node instanceof ASTInlineMethodDeclaration || node instanceof ASTInlineMethodDefinition  )
					methodType.setInline(true);				
				
				node.setType(methodType);
				node.setEnclosingType(currentType);
				//what modifiers (if any) are allowed for a local method declaration?
			}
			
			currentMethod.addFirst(node);
		}
		else
			currentMethod.removeFirst();
		
		createScope(secondVisit); 
		
		return WalkType.POST_CHILDREN;
	}
	
	private void createScope(Boolean secondVisit) {
		// we have a new scope, so we need a new HashMap in the linked list
		if(secondVisit)			
		{
			symbolTable.removeFirst();
			scopeMethods.removeFirst();
		}
		else
		{
			symbolTable.addFirst(new HashMap<String, ModifiedType>());
			
			if( currentMethod.isEmpty() )
				scopeMethods.addFirst(null);
			else
				scopeMethods.addFirst(currentMethod.getFirst());
		}
	}
	
	public Object visit(ASTSwitchStatement node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		Type type = node.jjtGetChild(0).getType();
		if(!type.isIntegral() && !type.isString() && !(type instanceof EnumType))
			addError(Error.INVALID_TYPE, "Supplied type " + type + " cannot be used in switch statement, only integral, String, and enum types allowed");
		for(int i=1;i<node.jjtGetNumChildren();++i) {
			Node childNode = node.jjtGetChild(i);
			if(childNode.getClass() == ASTSwitchLabel.class) {
				if(childNode.getType() != null){ //default label should have null type 
					if(!childNode.getType().isSubtype(type)) {
						addError(childNode,Error.MISMATCHED_TYPE,"Label type " + childNode.getType() + " does not match switch type " + type);
					}
				}
			}
		}
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTSwitchLabel node, Boolean secondVisit) throws ShadowException {
		pushUpType(node, secondVisit);
		
		if( secondVisit && node.jjtGetNumChildren() > 0 && !node.getModifiers().isConstant() )
			addError(Error.INVALID_TYPE, "Value supplied as label must be constant");			
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTBlock node, Boolean secondVisit) throws ShadowException {
		createScope(secondVisit);
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTMethodDeclaration node, Boolean secondVisit) throws ShadowException {
		return visitMethod( node, secondVisit );
	}
	
	public Object visit(ASTLocalMethodDeclaration node, Boolean secondVisit) throws ShadowException {
		if( !secondVisit )
		{	
			Node declaration = node.jjtGetChild(0);			
			addSymbol(declaration.getImage(), node);
		}		
		return visitMethod( node, secondVisit );
	}
	
	public Object visit(ASTInlineMethodDeclaration node, Boolean secondVisit) throws ShadowException {
		if( !secondVisit )
		{	
			Node declaration = node.jjtGetChild(0);			
			addSymbol(declaration.getImage(), node);
		}		
		return visitMethod( node, secondVisit );
	}	
	
	public Object visit(ASTFormalParameters node, Boolean secondVisit) throws ShadowException	
	{
		//class methods have their types constructed
		//but still need to have parameters added to the symbol table
		if( !node.isLocal() )
		{
			MethodType methodType = currentMethod.getFirst().getMethodSignature().getMethodType();
			for(String symbol : methodType.getParameterNames())
				addSymbol( symbol, methodType.getParameterType(symbol));
			
			return WalkType.NO_CHILDREN;
		}
		
		if(secondVisit)
		{			
			for(int i = 0; i < node.jjtGetNumChildren(); ++i)
			{
				Node parameter = node.jjtGetChild(i);
				node.addParameter(parameter.getImage(), parameter);
			}
		}	
		
		return WalkType.POST_CHILDREN;
	}
	
	public void addSymbol( String name, ModifiedType node )
	{			
		
		if( symbolTable.size() == 0 )
			addError(Error.INVALID_STRUCTURE, "Declaration is illegal outside of a defined scope");
		else
		{
			boolean found = false;
		
			for( HashMap<String, ModifiedType> scope : symbolTable )
			{			
				if( scope.containsKey( name ) ) //we look at all enclosing scopes
				{
					addError(Error.MULTIPLY_DEFINED_SYMBOL, "Symbol " + name + " cannot be redefined in this context");
					found = true;
					break;
				}
			}
			
			if( !found )			
				symbolTable.getFirst().put(name, node);  //uses node for modifiers
		}
	}
	
	
	public ModifiedType findSymbol( String name )
	{
		ModifiedType node = null;
		for( int i = 0; i < symbolTable.size(); i++ )
		{
			HashMap<String,ModifiedType> map = symbolTable.get(i);		
			if( (node = map.get(name)) != null )
			{
				Node method = scopeMethods.get(i);
				if( method != null && method != currentMethod.getFirst() )
				{
					//situation where we are pulling a variable from an outer method
					//it must be final!
					//local method declarations don't count
					
					//TODO: add a check to deal with this, even without final
					
					//if( !(node instanceof ASTLocalMethodDeclaration) && !node.getModifiers().isFinal() )
					//	addError(Error.INVL_TYP, "Variables accessed by local methods from outer methods must be marked final");
				}
				return node;
			}
		}
		
		
		return node;
	}
	
	public Object visit(ASTFormalParameter node, Boolean secondVisit) throws ShadowException
	{
		if(secondVisit)
		{	
			//child 0 is Modifiers
			Type type = node.jjtGetChild(1).getType();
			String symbol = node.jjtGetChild(2).getImage();
			node.setType( type );
			node.setImage(symbol);
			
			if( node.getModifiers().isNullable() && type.isPrimitive() )
				addError(Error.INVALID_MODIFIER, "Modifier nullable cannot be applied to primitive type " + type);
 
			addSymbol( symbol, node );
		}
	
		return WalkType.POST_CHILDREN;
	}
	
	
	public Object visit(ASTCreateDeclaration node, Boolean secondVisit) throws ShadowException {
		if( secondVisit )
		{
			if( currentType instanceof ClassType)
			{
				ClassType classType = (ClassType) currentType;
				ClassType parentType = classType.getExtendType();
				
				if( parentType != null )
				{	
					if( !node.hasExplicitInvocation() ) 
						//only worry if there is no explicit invocation
						//explicit invocations are handled separately
					{
						boolean foundDefault = false;
						SequenceType emptyParameters = new SequenceType();
						for( MethodSignature method : parentType.getMethods("create") )
						{
							if( method.matches(emptyParameters) )
							{
								foundDefault = true;
								break;
							}
						}
					
						if( !foundDefault )
							addError(Error.MISSING_CREATE, "Explicit create invocation is missing, and parent class " + parentType + " does not implement the default create");
					}					
				}
			}
		}		
		
		return visitMethod( node, secondVisit );
	}
	
	public Object visit(ASTCreateBlock node, Boolean secondVisit) throws ShadowException {
		createScope(secondVisit);
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTDestroyDeclaration node, Boolean secondVisit) throws ShadowException {
		return visitMethod( node, secondVisit );
	}
	
	public Object visit(ASTReferenceType node, Boolean secondVisit) throws ShadowException
	{
		if(secondVisit)			
		{
			Node child =  node.jjtGetChild(0);			
			Type type = child.getType();			
			List<Integer> dimensions = node.getArrayDimensions();
			
			if( dimensions.size() == 0 )
				node.setType(type);
			else
			{
				ArrayType arrayType = new ArrayType(type, dimensions);
				((ClassType)currentType).addReferencedType(arrayType);
				node.setType(arrayType);
			}
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	private void processDeclaration( Node node )
	{
		boolean isLocal = (node instanceof ASTLocalVariableDeclaration);		
		int start = 0;				
		
		if( node.jjtGetChild(start) instanceof ASTType ) //skip type declaration
			start++;	
		
		Type type = node.getType();
		//type is set for local declarations immediately previously and for fields in the field and method checker
		
		if(type == null)
		{			
			addError(node.jjtGetChild(start), Error.UNDEFINED_TYPE, "Type " + node.jjtGetChild(start).jjtGetChild(0).getImage() + " not defined in this context");
			return;
		}		
		
		if( type.isPrimitive() && node.getModifiers().isNullable() )		
			addError(node.jjtGetChild(start), Error.INVALID_MODIFIER, "Modifier nullable cannot be applied to primitive type " + type);				
			
		// go through and add the variables
		for(int i = start; i < node.jjtGetNumChildren(); ++i)
		{
			Node declaration = node.jjtGetChild(i);
			Node identifier = declaration.jjtGetChild(0);
			
			if(declaration.jjtGetNumChildren() == 2) // check for initializer
			{
				Node initializer = declaration.jjtGetChild(1);
				addErrors(declaration, isValidInitialization(node, initializer) );				
			}
			/* //leave this to the TAC 
			else
			{
				if( !node.getModifiers().isNullable() && !type.isPrimitive() )
					addError(declaration, Error.TYPE_MIS, "Non-nullable variable " + declaration + "does not have initializer");		
			}
			*/
			
			declaration.setType(type);
			declaration.setModifiers(node.getModifiers());
			identifier.setType(type);
			identifier.setModifiers(node.getModifiers());
			
			if( isLocal ) // add the symbol to the scope table				
				addSymbol( identifier.getImage(), node);
		}		
	}
	
	
	public Object visit(ASTLocalVariableDeclaration node, Boolean secondVisit) throws ShadowException
	{		
		if(secondVisit)
		{	
			Type type = null;
			Node child = node.jjtGetChild(0); 
			
			if( child instanceof ASTType )
			{
				type = child.getType();
			}
			else //auto type
			{	
				for( int i = 0; i < node.jjtGetNumChildren(); i++ )
				{
					Node declaration = node.jjtGetChild(i);
					if( declaration.jjtGetNumChildren() == 2 )
					{
						type = declaration.jjtGetChild(1).getType();
						break;
					}
				}
				
				if( type == null )
				{
					addError(Error.UNDEFINED_TYPE, "Variable declared with var has no initializer to infer type from");
					type = Type.UNKNOWN;
				}
			}			
			
			node.setType(type);			
			processDeclaration( node );
		}

		return WalkType.POST_CHILDREN;
	}

	public Object visit(ASTFieldDeclaration node, Boolean secondVisit) throws ShadowException {		
		if(secondVisit)
			processDeclaration( node );							  		

		return WalkType.POST_CHILDREN;
	}	
	
	public boolean setTypeFromContext( Node node, String name, Type context, boolean directAccess  ) //directAccess is true if there is no prefix and false if there is
	{
		if( context instanceof TypeParameter )
		{
			TypeParameter typeParameter = (TypeParameter) context;
			for( Type type : typeParameter.getBounds() )
				if( setTypeFromContext( node, name, type, directAccess ) )
					return true;
			
			return setTypeFromContext( node, name, Type.OBJECT, directAccess );			
		}		
		else if( context instanceof InterfaceType )
		{			
			InterfaceType interfaceType = (InterfaceType)context;
			
			if( interfaceType.recursivelyContainsMethod( name ) )
			{
				node.setType( new UnboundMethodType( name, interfaceType ) );				
				return true;
			}			
		}		
		else if( context instanceof ClassType  )
		{
			ClassType classType;
			
			if( context instanceof ArrayType )
				classType = Type.ARRAY;
			else
				classType = (ClassType)context;
					
			if(classType.recursivelyContainsField(name))
			{
				Node field = classType.recursivelyGetField(name);
				node.setType(field.getType());
				node.setModifiers(field.getModifiers());
				node.addModifier(Modifiers.ASSIGNABLE);
				node.addModifier(Modifiers.FIELD);
				
				if( field.getModifiers().isPrivate() && currentType != classType   )
					addError(Error.ILLEGAL_ACCESS, "Private variable " + field.getImage() + "not accessible from this context");						
				
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
				node.setModifiers(Modifiers.TYPE_NAME);
				return true;
			}
		}

		return false;
	}
	
	
	public boolean setTypeFromName( Node node, String name ) 
	{			
		// next go through the scopes trying to find the variable
		ModifiedType declaration = findSymbol( name );
		
		if( declaration != null ) 
		{
			node.setType(declaration.getType());
			node.setModifiers(declaration.getModifiers());
			node.addModifier(Modifiers.ASSIGNABLE);
			return true;
		}
			
		// now check the parameters of the methods
		MethodType methodType = null;
		
		for( Node method : currentMethod)
		{
			methodType = (MethodType)method.getType();
		
			if(methodType != null && methodType.containsParam(name))
			{	
				node.setType(methodType.getParameterType(name).getType());
				node.setModifiers(methodType.getParameterType(name).getModifiers());
				node.addModifier(Modifiers.ASSIGNABLE);	//is this right?  Shouldn't all method parameters be unassignable?		
				return true;
			}
		}
				
		// check to see if it's a field or a method			
		if( setTypeFromContext( node, name, currentType, true ) )
			return true;
				
		//is it a type?
		Type type = lookupType( name );		
				
		if(type != null)
		{
			((ClassType)currentType).addReferencedType(type);
			node.setType(type);
			node.addModifier(Modifiers.TYPE_NAME);
			return true;
		}
		
		return false;
	}
	
	public Object visit(ASTRelationalExpression node, Boolean secondVisit) throws ShadowException 
	{
		if( secondVisit )
		{
			Type result = node.jjtGetChild(0).getType();
						
			for( int i = 1; i < node.jjtGetNumChildren(); i++ )
			{
				Node currentNode = node.jjtGetChild(i);
				Type current = currentNode.getType();
				char operation = node.getImage().charAt(i - 1);
				String symbol = "";
				switch( operation )
				{
				case '<': symbol = "<"; break;
				case '>': symbol = ">"; break;
				case '{': symbol = "<="; break;
				case '}': symbol = ">="; break;	
				}
				
				if( result.hasInterface(Type.CAN_COMPARE) )
				{
					SequenceType argument = new SequenceType(currentNode);												
					 
					MethodSignature signature = setMethodType(node, result, "compare", argument );
					if( signature != null )
					{
						result = signature.getReturnTypes().getType(0);
						node.addOperation(signature);
					}
					else
					{
						addError(Error.INVALID_TYPE, "Operator " + symbol + " not defined on types " + result + " and " + current);
						result = Type.UNKNOWN;	
						break;
					}				
				}
				else		
				{
					addError(Error.INVALID_TYPE, "Operator " + symbol + " not defined on types " + result + " and " + current);
					result = Type.UNKNOWN;
					break;
				}
				
				result = Type.BOOLEAN;  //boolean after one comparison
			}
			
			node.setType(result); //propagates type up if only one child
			pushUpModifiers(node); //can make ASSIGNABLE (if only one child)
		}
		
		return WalkType.POST_CHILDREN;	
	}
	
	public Object visit(ASTConcatenationExpression node, Boolean secondVisit) throws ShadowException 
	{
		if( secondVisit )
		{		
			Type result = node.jjtGetChild(0).getType();			
			
			if( node.jjtGetNumChildren() > 1 )
				result = Type.STRING;
				
			node.setType(result); //propagates type up if only one child
			pushUpModifiers(node); //can add ASSIGNABLE (if only one child)
		}
		
		return WalkType.POST_CHILDREN;	
	}	
	
	public Object visit(ASTEqualityExpression node, Boolean secondVisit) throws ShadowException
	{
		if( secondVisit )
		{
			Type result = node.jjtGetChild(0).getType();
						
			for( int i = 1; i < node.jjtGetNumChildren(); i++ )
			{
				char operation = node.getImage().charAt(i - 1);
				String symbol = "";
				switch( operation )
				{
				case '=': symbol = "=="; break;
				case 'e': symbol = "==="; break;
				case '!': symbol = "!="; break;
				case 'n': symbol = "!=="; break;
				}
				
				Node currentNode = node.jjtGetChild(i);
				Type current = currentNode.getType();
				if( operation == '=' || operation == '!')
				{	
					if( result.hasInterface(Type.CAN_EQUAL) )
					{
						SequenceType argument = new SequenceType();
						argument.add(currentNode);							
						 
						MethodSignature signature = setMethodType(node, result, "equal", argument );
						if( signature != null )
						{
							result = signature.getReturnTypes().getType(0);
							node.addOperation(signature);
						}
						else
						{
							addError(Error.INVALID_TYPE, "Operator " + symbol + " not defined on types " + result + " and " + current);
							result = Type.UNKNOWN;	
							break;
						}				
					}
					else		
					{
						addError(Error.INVALID_TYPE, "Operator " + symbol + " not defined on types " + result + " and " + current);
						result = Type.UNKNOWN;
						break;
					}			
				}
				else if( !result.isSubtype(current) && !current.isSubtype(result) )
				{
					addError(Error.INVALID_TYPE, "Operator " + symbol + " not defined on types " + result + " and " + current);
					result = Type.UNKNOWN;	
					break;
				}	
				
				result = Type.BOOLEAN;  //boolean after one comparison			
			}
			
			node.setType(result); //propagates type up if only one child
			pushUpModifiers(node); //can overwrite ASSIGNABLE (if only one child)
		}
		
		return WalkType.POST_CHILDREN;		
	}
	
	public void visitShiftRotate( OperationNode node ) throws ShadowException
	{			
		Type result = node.jjtGetChild(0).getType();
		int symbolIndex = 0;
		
		for( int i = 1; i < node.jjtGetNumChildren(); i++ ) //cycle through types, upgrading to broadest legal one, short => int => long is possible
		{
			Node currentNode = node.jjtGetChild(i);
			Type current = currentNode.getType();
			char operator = node.getImage().charAt(symbolIndex);
			String methodName = "";
			String symbol = "";
			
			switch( operator )
			{
			case 'l':
				methodName = "bitShiftLeft";
				symbol = "<<";
				break;
			case 'L':
				methodName = "bitRotateLeft";
				symbol = "<<<";
				 break;
			case 'r':
				methodName = "bitShiftRight";
				symbol = ">>";
				break;
			case 'R':
				methodName = "bitRotateRight";
				symbol = ">>>";
				break;
			}
			
			if( !(currentNode instanceof ASTRightShift) && !(currentNode instanceof ASTRightRotate)  ) //RightRotate() and RightShift() have their own productions
			{
				if( result.hasInterface(Type.INTEGER) )
				{
					SequenceType argument = new SequenceType();
					argument.add(currentNode);							
					 
					MethodSignature signature = setMethodType(node, result, methodName, argument );
					if( signature != null )
					{
						result = signature.getReturnTypes().getType(0);
						node.addOperation(signature);
					}
					else
					{
						addError(Error.INVALID_TYPE, "Operator " + symbol + " not defined on types " + result + " and " + current);
						result = Type.UNKNOWN;	
						break;
					}				
				}
				else		
				{
					addError(Error.INVALID_TYPE, "Operator " + symbol + " not defined on types " + result + " and " + current);
					result = Type.UNKNOWN;
					break;
				}
				
				symbolIndex++;
			}
		}						
					
		node.setType(result); //propagates type up if only one child	
		pushUpModifiers(node);  //can add ASSIGNABLE (if only one child)
	}
	
	public Object visit(ASTShiftExpression node, Boolean secondVisit) throws ShadowException {
		if(secondVisit)
			visitShiftRotate( node );
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTRotateExpression node, Boolean secondVisit) throws ShadowException {
		if(secondVisit)
			visitShiftRotate( node );
		
		return WalkType.POST_CHILDREN;
	}
	
	public void visitArithmetic(OperationNode node) throws ShadowException 
	{
		Type result = node.jjtGetChild(0).getType();
		for( int i = 1; i < node.jjtGetNumChildren(); i++ ) //cycle through types, upgrading to broadest legal one
		{			
			char operator = node.getImage().charAt(i - 1);
			InterfaceType interfaceType = null;
			String methodName = "";
			switch( operator )
			{
			case '+':
				methodName = "add";
				interfaceType = Type.CAN_ADD;
				break;
			case '-':
				methodName = "subtract";
				interfaceType = Type.CAN_SUBTRACT;
				break;
			case '*':
				methodName = "multiply";
				interfaceType = Type.CAN_MULTIPLY;
				break;
			case '/':
				methodName = "divide";
				interfaceType = Type.CAN_DIVIDE;
				break;
			case '%':
				methodName = "modulus";
				interfaceType = Type.CAN_MODULUS;
				break;
			}
			
			Node currentNode = node.jjtGetChild(i);
			Type current = currentNode.getType();
			
			if( result.hasInterface(interfaceType) )
			{
				SequenceType argument = new SequenceType(currentNode);											
				 
				MethodSignature signature = setMethodType(node, result, methodName, argument );
				if( signature != null )
				{
					result = signature.getReturnTypes().getType(0);
					node.addOperation(signature);
				}
				else
				{
					addError(node.jjtGetChild(i), Error.INVALID_TYPE, "Cannot apply operator " + operator + " to " + result + " and " + current);
					node.setType(Type.UNKNOWN);
					return;
				}				
			}
			else		
			{
				addError(node.jjtGetChild(i), Error.INVALID_TYPE, "Cannot apply operator " + operator + " to " + result + " and " + current);
				node.setType(Type.UNKNOWN);
				return;						
			}				
		}
			
		node.setType(result); //propagates type up if only one child
		pushUpModifiers(node); //can add ASSIGNABLE (if only one child)
	}
	
	public Object visit(ASTAdditiveExpression node, Boolean secondVisit) throws ShadowException {
		if(secondVisit)
			visitArithmetic( node );
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTMultiplicativeExpression node, Boolean secondVisit) throws ShadowException {
		if(secondVisit)
			visitArithmetic( node );
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTUnaryToString node, Boolean secondVisit) throws ShadowException {
		if(secondVisit)
		{				
			Type type = node.jjtGetChild(0).getType();
			String symbol = node.getImage();
			
			if( symbol.equals("#")  )
				node.setType(Type.STRING);
			else
			{
				node.setType(type);			
				pushUpModifiers( node );
			}
		}
		
		return WalkType.POST_CHILDREN;
	}
		
	public Object visit(ASTUnaryExpression node, Boolean secondVisit) throws ShadowException
	{
		if(secondVisit)
		{				
			Type type = node.jjtGetChild(0).getType();
			String operator = node.getImage();
			
			if( (operator.equals("-") || operator.equals("+")) ) 
			{
				if( type.hasInterface(Type.CAN_NEGATE) )
				{
					MethodSignature signature = setMethodType(node, type, "negate", new SequenceType() );
					if( signature != null )
					{
						type = signature.getReturnTypes().getType(0);
						node.addOperation(signature);
					}
					else
					{
						addError(Error.INVALID_TYPE, "Cannot apply unary " + operator + " to type " + type + " which does not implement interface " + Type.CAN_NEGATE);
						type = Type.UNKNOWN;						
					}
				}
				else
				{
					addError(Error.INVALID_TYPE, "Cannot apply unary " + operator + " to type " + type + " which does not implement interface " + Type.CAN_NEGATE);
					type = Type.UNKNOWN;					
				}
			}
			else
				pushUpModifiers( node );
			
			//maybe unsigned types can't be negated
			/*
			if(symbol.contains("-"))
				node.setType(Type.makeSigned((ClassType)type));
			else
			*/
			node.setType(type);
		}
		
		return WalkType.POST_CHILDREN;
	}
		
	public Object visit(ASTUnaryExpressionNotPlusMinus node, Boolean secondVisit) throws ShadowException {
		if(secondVisit)
		{				
			Type type = node.jjtGetChild(0).getType();
			
			if(node.getImage().startsWith("~") )
			{
				if( type.hasInterface(Type.INTEGER) )
				{	 
					MethodSignature signature = setMethodType(node, type, "bitComplement", new SequenceType() );
					if( signature != null )
					{
						type = signature.getReturnTypes().getType(0);
						node.addOperation(signature);
					}
					else
					{
						addError(Error.INVALID_TYPE, "Cannot apply operator ~ to type " + type + " which does not implement " + Type.INTEGER);
						type = Type.UNKNOWN;						
					}
				}
				else					
				{
					addError(Error.INVALID_TYPE, "Cannot apply operator ~ to type " + type + " which does not implement " + Type.INTEGER);
					type = Type.UNKNOWN;				
				}
			}		
			else if(node.getImage().startsWith("!") )
			{
				if( !type.equals(Type.BOOLEAN)) 
				{
					addError(Error.INVALID_TYPE, "Cannot apply operator ! to type " + type + " which is not boolean");
					type = Type.UNKNOWN;				
				}
			}
			else
				pushUpModifiers( node ); //can add ASSIGNABLE (if only one child)
			
			node.setType(type);
		}
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
				addError(node.jjtGetChild(0), Error.INVALID_TYPE, "Supplied type " + t1 + " cannot be used in the condition of a ternary operator, boolean type required");
				node.setType(Type.UNKNOWN);
			}
			else if( t2.isSubtype(t3) )
				node.setType(t3);
			else if( t3.isSubtype(t2) )
				node.setType(t2);
			else 
			{
				addError(Error.MISMATCHED_TYPE, "Supplied type " + t2 + " must match " + t3 + " in execution of ternary operator");
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
			
			for( int i = 0; i < node.jjtGetNumChildren(); i++ )
			{
				result = node.jjtGetChild(i).getType();
			
				if( result != Type.BOOLEAN )
				{
					addError(node.jjtGetChild(i), Error.INVALID_TYPE, "Supplied type " + result + " cannot be used with a logical operator, boolean type required");			
					node.setType(Type.UNKNOWN);
					return;
				}					
			}
			
			node.setType(result);
		}
	}
	
	public Object visit(ASTCoalesceExpression node, Boolean secondVisit) throws ShadowException {
		if( secondVisit )
		{
			if( node.jjtGetNumChildren() == 1 )
				pushUpType(node, secondVisit); //includes modifier push up
			else
			{	
				Type result = null;
				boolean isNullable = true;
				
				for( int i = 0; i < node.jjtGetNumChildren(); i++ ) //cycle through types, upgrading to broadest legal one
				{
					Node child = node.jjtGetChild(i); 
					Type type = child.getType();
					Modifiers modifiers = child.getModifiers();
					
					if( !modifiers.isNullable() )
					{
						isNullable = false;
						if( i < node.jjtGetNumChildren() - 1 )  //only last child can be nullable
						{	
							addError(child, Error.INVALID_TYPE, "Only the last term in a coalesce expression can be non-nullable");
							result = Type.UNKNOWN;
							break;
						}
					}
					
					if( result == null )
						result = type;				
					else if( result.isSubtype(type) )
						result = type;
					else if( !type.isSubtype(result) ) //neither is subtype of other, panic!
					{
						addError(Error.MISMATCHED_TYPE, "Supplied type " + type + " does not match type " + result + " in coalesce expression");
						result = Type.UNKNOWN;
						break;
					}
				}
				
				node.setType(result);
				if( isNullable )
					node.addModifier(Modifiers.NULLABLE);
			}			
		}	
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTConditionalOrExpression node, Boolean secondVisit) throws ShadowException {
		if(secondVisit)
			visitConditional( node );		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTConditionalExclusiveOrExpression node, Boolean secondVisit) throws ShadowException {
		if(secondVisit)
			visitConditional( node );		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTConditionalAndExpression node, Boolean secondVisit) throws ShadowException {
		if(secondVisit)
			visitConditional( node );
		return WalkType.POST_CHILDREN;
	}	


	public void visitBitwise(OperationNode node ) throws ShadowException {
				
		if( node.jjtGetNumChildren() == 1 )
			pushUpType(node, true); //includes modifier push up
		else
		{	
			Type result = node.jjtGetChild(0).getType();
			
			for( int i = 1; i < node.jjtGetNumChildren(); i++ )
			{
				Node currentNode = node.jjtGetChild(i);
				Type current = currentNode.getType();
				char operator = node.getImage().charAt(i - 1);
				String methodName = "";
				switch( operator )
				{
				case '|': methodName = "bitOr"; break;				
				case '&': methodName = "bitAnd"; break;
				case '^': methodName = "bitXor"; break;
				}
				
				if( result.hasInterface(Type.INTEGER) )
				{
					SequenceType argument = new SequenceType();
					argument.add(currentNode);							
					 
					MethodSignature signature = setMethodType(node, result, methodName, argument );
					if( signature != null )
					{
						result = signature.getReturnTypes().getType(0);
						node.addOperation(signature);
					}
					else
					{
						addError(Error.INVALID_TYPE, "Operator " + operator + " not defined on types " + result + " and " + current);
						node.setType(Type.UNKNOWN);	
						break;
					}				
				}
				else		
				{
					addError(Error.INVALID_TYPE, "Operator " + operator + " not defined on types " + result + " and " + current);
					node.setType(Type.UNKNOWN);
					break;
				}
			}
			
			node.setType(result);
		}
	}
	
	public Object visit(ASTBitwiseOrExpression node, Boolean secondVisit) throws ShadowException {
		if(secondVisit)
			visitBitwise( node );
		return WalkType.POST_CHILDREN;
	}	
	
	public Object visit(ASTBitwiseExclusiveOrExpression node, Boolean secondVisit) throws ShadowException {
		if(secondVisit)
			visitBitwise( node );
		return WalkType.POST_CHILDREN;
	}	
	
	public Object visit(ASTBitwiseAndExpression node, Boolean secondVisit) throws ShadowException {
		if(secondVisit)
			visitBitwise( node );
		return WalkType.POST_CHILDREN;
	}
	
	
	

	public Object visit(ASTClassOrInterfaceType node, Boolean secondVisit) throws ShadowException {
		return typeResolution(node, secondVisit);
	}
	
	public Object visit(ASTArguments node, Boolean secondVisit) throws ShadowException
	{
		if(secondVisit)
		{		
			for( int i = 0; i < node.jjtGetNumChildren(); i++ )
				node.addType(node.jjtGetChild(i));
		}
		
		return WalkType.POST_CHILDREN; 
	}
	
	public Object visit(ASTStatementExpression node, Boolean secondVisit) throws ShadowException
	{
			return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTSequenceAssignment node, Boolean secondVisit) throws ShadowException
	{
		if( !secondVisit )
			return WalkType.POST_CHILDREN;
		
		ASTSequenceLeftSide left = (ASTSequenceLeftSide) node.jjtGetChild(0);
		SequenceType leftSequence = left.getType();
		Node right = node.jjtGetChild(1);
		Type rightType = right.getType();
		ModifiedType rightElement = right;
		
		//check lengths first
		if( rightType instanceof SequenceType && leftSequence.size() != ((SequenceType)rightType).size() ) 
		{				
			addError(Error.INVALID_ASSIGNMENT, "Right hand side " + ((SequenceType)rightType) + " cannot be assigned to left hand side " + leftSequence + " because their lengths do not match");
			return WalkType.POST_CHILDREN;				
		}

		for( int i = 0; i < leftSequence.size(); ++i )
		{	
			ModifiedType leftElement = leftSequence.get(i);
			
			if( leftElement != null ) //can be skipped
			{		
				if( rightType instanceof SequenceType )
					rightElement = ((SequenceType)rightType).get(i);
				
				if( leftElement.getType().equals( Type.VAR ) )
				{
					if( rightElement.getType() instanceof PropertyType )
					{
						PropertyType propertyType = (PropertyType)rightElement.getType();
						if( propertyType.isGettable() )						
							leftElement.setType(propertyType.getGetType().getType());
						else
							addError(Error.ILLEGAL_ACCESS, "Property " + right + "does not have get access");
					}
					else
						leftElement.setType(rightElement.getType());
				}
							
				if( leftElement instanceof ASTSequenceVariable ) //declaration									
					addErrors(isValidInitialization(leftElement, rightElement));				
				else //otherwise simple assignment
					addErrors(isValidAssignment(leftElement, rightElement, AssignmentType.EQUAL));
			}
		}

		return WalkType.POST_CHILDREN;	
	}

	public Object visit(ASTExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		if( node.jjtGetNumChildren() == 3 ) //if there is assignment
		{
			ASTPrimaryExpression left = (ASTPrimaryExpression) node.jjtGetChild(0);
			ASTAssignmentOperator assignment = (ASTAssignmentOperator) node.jjtGetChild(1);			
			ASTConditionalExpression right = (ASTConditionalExpression) node.jjtGetChild(2);
			List<TypeCheckException> errors = isValidAssignment(left, right, assignment.getAssignmentType()); 
			
			if( errors.isEmpty() )
			{
				Type leftType = left.getType();
				Type rightType = right.getType();
				
				if( leftType instanceof PropertyType )
					leftType = ((PropertyType)leftType).getSetType().getType();
				
				if( rightType instanceof PropertyType )
					rightType = ((PropertyType)rightType).getGetType().getType();
						
				node.addOperation(leftType.getMatchingMethod(assignment.getAssignmentType().getMethod(), new SequenceType(rightType)));
			}
			else				
				addErrors(errors);			
					
			//will issue appropriate errors
			//since this is an Expression (with nothing to the left), there is no type to set
		}
		else //did something actually happen?
		{
			ASTPrimaryExpression child = (ASTPrimaryExpression) node.jjtGetChild(0);
			if( !child.isAction() )
				addError(Error.NO_ACTION, "Statement does not perform an action");
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
				addError(Error.MISMATCHED_TYPE, "Supplied type " + t1 + " cannot be compared with type " + t2 + " in an is statement");
				node.setType(Type.UNKNOWN);
			}
		}			

		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTTypeParameters node, Boolean secondVisit) throws ShadowException
	{		
		//skip class declarations (already done in type updater)
		//this will only be for local methods at this stage
		//declarators for class methods have already stopped walking
		if( node.jjtGetParent() instanceof ASTClassOrInterfaceDeclaration )
			return WalkType.NO_CHILDREN;
		
		if( secondVisit )
		{		
			for( int i = 0; i < node.jjtGetNumChildren(); ++i )
				node.addType(node.jjtGetChild(i));
		}
		
		return WalkType.POST_CHILDREN;		
	}	
	
	@Override
	public Object visit(ASTTypeParameter node, Boolean secondVisit)	throws ShadowException
	{
		if( secondVisit )
		{					
			//add bounds
			if( node.jjtGetNumChildren() > 0 )
			{
				TypeParameter typeParameter = (TypeParameter) node.getType();
				ASTTypeBound bound = (ASTTypeBound)(node.jjtGetChild(0));				
				for( int i = 0; i < bound.jjtGetNumChildren(); i++ )								
					typeParameter.addBound(bound.jjtGetChild(i).getType());				
			}		
		}
		else
		{
			//symbol is added on first visit because bounds may be dependent on it
			//e.g. A is CanEquate<A>
			String symbol = node.getImage();
			TypeParameter typeParameter = new TypeParameter(symbol);
			addSymbol( symbol, node );
			node.setType(typeParameter);
		}
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTFunctionType node, Boolean secondVisit) throws ShadowException
	{
		if( secondVisit )
		{		
			MethodType methodType = new MethodType();		
			Node parameters = node.jjtGetChild(0);
			SequenceType parameterTypes = (SequenceType) parameters.getType();
			
			Node returns = node.jjtGetChild(1);
			SequenceType returnTypes = (SequenceType) returns.getType();
							
			for( ModifiedType parameter : parameterTypes  )
				methodType.addParameter(parameter);			
				
			for( ModifiedType type : returnTypes )
				methodType.addReturn(type);
			
			node.setType(methodType);
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTResultTypes node, Boolean secondVisit) throws ShadowException 
	{
		if(secondVisit)
		{	
			for( int i = 0; i < node.jjtGetNumChildren(); i++ )
				node.addType(node.jjtGetChild(i));
		}
	
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTInlineResults node, Boolean secondVisit) throws ShadowException 
	{
		if(secondVisit)
		{	
			for( int i = 0; i < node.jjtGetNumChildren(); i++ )
				node.addType(node.jjtGetChild(i));
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
			
			node.setModifiers(node.jjtGetChild(1).getModifiers());
			
			if( t1 instanceof MethodType && t2 instanceof UnboundMethodType ) //casting methods
			{
				MethodType method = (MethodType)t1;
				UnboundMethodType unboundMethod = (UnboundMethodType)t2;				
				
				boolean found = false;
				
								
				Type outer = unboundMethod.getOuter();				
				for( MethodSignature signature : outer.getAllMethods(unboundMethod.getTypeName()) )
					if( signature.getMethodType().matches(method.getParameterTypes()) && signature.getMethodType().canReturn(method.getReturnTypes()))
					{
						node.setType(signature.getMethodType());
						found = true;
						break;
					}
					
				if( !found )
				{
					addError(Error.MISMATCHED_TYPE, "No method " + unboundMethod.getTypeName() + " matches signature " + method);
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
				addError(Error.MISMATCHED_TYPE, "Supplied type " + t2 + " cannot be cast to type " + t1);
				node.setType(Type.UNKNOWN);
			}
		}			

		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTSequenceRightSide node, Boolean secondVisit) throws ShadowException 
	{
		if(secondVisit)
		{
			for( int i = 0; i < node.jjtGetNumChildren(); i++ )							
				node.addType(node.jjtGetChild(i));
		}

		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTSequenceVariable node, Boolean secondVisit) throws ShadowException
	{
		if( secondVisit )
		{
			if( node.jjtGetNumChildren() > 1 ) //type specified
				node.setType(node.jjtGetChild(1).getType());
			else //var used
				node.setType(Type.VAR);
			
			addSymbol(node.getImage(), node);
		}		
		
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTSequenceLeftSide node, Boolean secondVisit) throws ShadowException
	{
		if(secondVisit)
		{	
			LinkedList<Boolean> usedItems = node.getUsedItems();
			int child = 0;		
			
			for( int i = 0; i < usedItems.size(); i++ )
			{
				if( usedItems.get(i))
				{					
					node.addType(node.jjtGetChild(child));
					child++;
				}
				else
					node.addType(null);
			}
		}			

		return WalkType.POST_CHILDREN;
	}
	
	

	public Object visit(ASTIfStatement node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		Type t = node.jjtGetChild(0).getType(); 
		
		if( !t.equals( Type.BOOLEAN ) )
			addError(Error.INVALID_TYPE, "Condition of if statement cannot accept non-boolean type " + t);
				
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTWhileStatement node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		Type t = node.jjtGetChild(0).getType(); 
		
		if( !t.equals( Type.BOOLEAN ) )
			addError(Error.INVALID_TYPE, "Condition of while statement cannot accept non-boolean type " + t);
				
		return WalkType.POST_CHILDREN;
	}

	public Object visit(ASTDoStatement node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		Type t = node.jjtGetChild(1).getType(); //second child, not first like if and while 
		
		if( !t.equals( Type.BOOLEAN ) )
			addError(Error.INVALID_TYPE, "Condition of do statement cannot accept non-boolean type " + t);
				
		return WalkType.POST_CHILDREN;
	}
	
	
	public Object visit(ASTForeachStatement node, Boolean secondVisit) throws ShadowException {
		createScope(secondVisit); //for variables declared in header
		return WalkType.POST_CHILDREN;
	}
		
	public Object visit(ASTForeachInit node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)		
			return WalkType.POST_CHILDREN;	
				
		//child 0 is Modifiers		
		Type collectionType = null;
		ModifiedType element = null;
		boolean isVar = false;
		
		if( node.jjtGetNumChildren() == 2 ) //var type
		{
			collectionType = node.jjtGetChild(1).getType();
			node.setType(Type.UNKNOWN);
			isVar = true;
		}
		else
		{				
			node.setType(node.jjtGetChild(1).getType());
			collectionType =  node.jjtGetChild(2).getType();
		}	
		
						
		if( collectionType instanceof ArrayType )
		{
			ArrayType array = (ArrayType)collectionType;
			element = new SimpleModifiedType( array.getBaseType(), new Modifiers() );
		}
		else if( collectionType.hasInterface(Type.CAN_ITERATE) )
		{			
			for(InterfaceType _interface : collectionType.getAllInterfaces() )				
				if( _interface.getTypeWithoutTypeArguments().equals(Type.CAN_ITERATE))
				{
					element = _interface.getTypeParameters().get(0);
					break;
				}
		}
		else
			addError(Error.INVALID_TYPE, "Supplied type " + collectionType + " does not implement CanIterate and cannot be the target of a foreach statement");
		
		if( isVar && element != null && element.getType() != null )
			node.setType(element.getType());
				
		List<TypeCheckException> errors = isValidInitialization( node, element);
		if( errors.isEmpty() )
			addSymbol( node.getImage(), node );
		else
			addErrors(errors );
				
		return WalkType.POST_CHILDREN;
	}


	public Object visit(ASTForStatement node, Boolean secondVisit) throws ShadowException {
		createScope(secondVisit);
		
		if(secondVisit)
		{
			int start = 0;
			if(node.jjtGetChild(start) instanceof ASTForInit)
				start = 1;	
			
			// the conditional type might come first or second depending upon if there is an init or not
			Type conditionalType = node.jjtGetChild(start).getType();			
			
			ASTUtils.DEBUG("TYPE: " + conditionalType);
			
			if(conditionalType == null || !conditionalType.equals( Type.BOOLEAN ) )
				addError(Error.INVALID_TYPE, "Supplied type " + conditionalType + " cannot be used in the condition of a for statement, boolean type required");
		}
			
		return WalkType.POST_CHILDREN;
	}	
	
	@Override
	public Object visit(ASTCatchStatements node, Boolean secondVisit) throws ShadowException 
	{	
		if( secondVisit )
		{
			List<Type> types = new ArrayList<Type>();
			
			for( int i = 0; i < node.getCatches(); i++ )
			{	
				Node child = node.jjtGetChild(i + 1); //catches after the try
				Type type = child.getType();				
				
				for( Type catchParameter : types )				
					if( type.isSubtype(catchParameter) )
					{
						addError( child, Error.UNREACHABLE_CODE, "Catch block for exception " + type + " cannot be reached" );
						break;
					}
				
				types.add(type); //adds to node's permanent list			
			}
		}
				
		return WalkType.POST_CHILDREN;	
	}
	
	@Override
	public Object visit(ASTCatchStatement node, Boolean secondVisit) throws ShadowException	
	{	
		if( secondVisit )
		{		
			Node child = node.jjtGetChild(0);
			Type type = child.getType();
			
			if( !(type instanceof ExceptionType) )
				addError( child, Error.INVALID_TYPE, "Supplied type " + type + " cannot be used as a catch parameter, exception types required");
		
			if( child.getModifiers().getModifiers() != 0 )
				addError( child, Error.INVALID_MODIFIER, "Modifiers cannot be applied to a catch parameter");
		}
		
		createScope(secondVisit); //for catch parameter
		
		return WalkType.POST_CHILDREN;	
		
	}
	
	public Object visit(ASTTryStatement node, Boolean secondVisit) throws ShadowException
	{
		if( secondVisit )
		{
			if( node.getBlocks() <= 0 )
				addError(Error.INVALID_STRUCTURE, "At least one catch, recover, or finally block must be associated with a try statement" );
			tryBlocks.removeLast();
		}
		else
			tryBlocks.add(node);		
		
		return WalkType.POST_CHILDREN;	
	}
	
	public Object visit(ASTCheckExpression node, Boolean secondVisit) throws ShadowException 
	{
		if( secondVisit )
		{
			ModifiedType child = node.jjtGetChild(0);
			
			//only gets make sense for a check expression
			if( child.getType() instanceof PropertyType )
			{
				PropertyType propertyType = (PropertyType) child.getType();
				child = propertyType.getGetType(); 
			}
			
			
			if( child.getModifiers().isNullable() )
			{
				node.setModifiers( child.getModifiers() );
				node.removeModifier(Modifiers.NULLABLE );
			}
			else
				addError(Error.INVALID_TYPE, "Non-nullable expression cannot be used in a check statement");
			
			node.setType(child.getType());
		}
		else
		{
			boolean found = false;
			for( ASTTryStatement statement : tryBlocks )
			{	
				if( statement.getRecoverStatement().hasRecover() )
				{
					found = true;
					break;
				}				
			}
			
			if( !found )
				addError(Error.INVALID_STRUCTURE, "No recover block supplied for check statement");
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
			
			Node last = node.jjtGetChild(node.jjtGetNumChildren() - 1);
						
			if(node.jjtGetParent() instanceof ASTExpression ) //this primary expression is the left side of an assignment
			{
				Type type = last.getType(); //if PropertyType, preserve that
				node.setModifiers(last.getModifiers());
				node.setType(type);
			}
			else
			{
				ModifiedType modifiedType = resolveType( last ); //otherwise, strip away the property
				node.setModifiers(modifiedType.getModifiers());
				node.setType(modifiedType.getType());
			}
		}
		else								//just prefix
		{
			Node child = node.jjtGetChild(0);
			node.setType(child.getType());
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
			if( node.getImage().equals("this")  ) //this
			{	
				if( currentType instanceof InterfaceType )
				{
					addError(Error.INVALID_SELF_REFERENCE, "Reference this invalid for interfaces");
					node.setType(Type.UNKNOWN);
				}
				else				
					node.setType(currentType);				
			}
			else if( node.getImage().startsWith("super")) //super case
			{
				if( currentType instanceof InterfaceType )
				{
					addError(Error.INVALID_SELF_REFERENCE, "Reference super invalid for interfaces");
					node.setType(Type.UNKNOWN);
				}
				else		
				{
					ClassType classType = (ClassType) currentType;					
					node.setType(classType.getExtendType());
				}
			}
			else //just a name
			{
				String name = node.getImage();
				if( !setTypeFromName( node, name )) //automatically sets type if can
				{
					addError(Error.UNDEFINED_TYPE, "Type " + name + " not defined in this context");
					node.setType(Type.UNKNOWN);											
				}
			}
		}
		else if( children == 1 )
		{
			Node child = node.jjtGetChild(0); 
			
			if( child instanceof ASTUnqualifiedName )
			{
				node.setImage(child.getImage() + "@" + node.getImage());
				String name = node.getImage();
				if( !setTypeFromName( node, name )) //automatically sets type if can
				{
					addError(Error.UNDEFINED_TYPE, "Type " + name + " not defined in this context");
					node.setType(Type.UNKNOWN);											
				}
			}
			else
			{
				node.setType( child.getType() ); 	//literal, conditional expression, check expression, cast expression, primitive and function types
				pushUpModifiers( node ); 			
			}
		}
		
		curPrefix.set(0, node); //so that the suffix can figure out where it's at
		
		/*   
		  Literal()
		| "this" { jjtThis.setImage("this"); }
		| "super" { jjtThis.setImage("super"); }
		| CheckExpression()
		| "(" ConditionalExpression() ")"
		| PrimitiveType()
		| FunctionType()
		| [LOOKAHEAD(UnqualifiedName() "@") UnqualifiedName() "@" ] t = <IDENTIFIER> { jjtThis.setImage(t.image); debugPrint(t.image); }
		 */
				
		return WalkType.POST_CHILDREN;
	}
	
	
	public Object visit(ASTClassOrInterfaceTypeSuffix node, Boolean secondVisit) throws ShadowException
	{
		return WalkType.PRE_CHILDREN;		
	}
	
	public Object visit(ASTTypeArguments node, Boolean secondVisit) throws ShadowException
	{
		if( secondVisit )
		{
			SequenceType sequenceType = new SequenceType();
			
			for( int i = 0; i < node.jjtGetNumChildren(); i++ )
				sequenceType.add(node.jjtGetChild(i));
			
			
			node.setType(sequenceType);
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	
	public Object visit(ASTPrimarySuffix node, Boolean secondVisit) throws ShadowException 
	{
		/*
		  QualifiedKeyword()
		| Brackets()
		| Subscript()
		| Method()
		| Allocation()
		| ScopeSpecifier()
		| Property()
		| MethodCall()
		*/
		
		if(secondVisit)
		{
			Node prefixNode = curPrefix.getFirst();		
			
			if( prefixNode.getModifiers().isNullable() )
				addError(Error.INVALID_DEREFERENCE, "Nullable reference cannot be dereferenced");
			
			Node child = node.jjtGetChild(0);
			if( (child instanceof ASTProperty) || (child instanceof ASTMethodCall) ||  (child instanceof ASTAllocation))
			{
				ASTPrimaryExpression parent = (ASTPrimaryExpression) node.jjtGetParent();
				parent.setAction(true);
			}			
			
			if( child instanceof ASTMethodCall )
			{
				Type childType = child.getType();
							
				if( childType instanceof MethodType )
				{
					MethodType methodType = (MethodType) childType;
					SequenceType returnTypes = methodType.getReturnTypes();
					returnTypes.setNodeType( node ); //used instead of setType
				}
				else
					node.setType(Type.UNKNOWN);
			}				
			else
				pushUpType(node, secondVisit);
			
			
			curPrefix.set(0, node); //so that a future suffix can figure out where it's at		
		}
		
		return WalkType.POST_CHILDREN;
		
	}

	public Object visit(ASTQualifiedKeyword node, Boolean secondVisit) throws ShadowException 
	{
		if( secondVisit )					
		{	
			node.setType(Type.UNKNOWN); //start with unknown, set if found
			String kind = node.getImage();
						
			if( curPrefix.getFirst().getModifiers().isTypeName())
			{	
				Type prefixType = curPrefix.getFirst().getType();
				
				if( kind.equals("this") )
				{					
					if( currentType.encloses( prefixType )  )				
						node.setType(prefixType);
					else				
						addError(Error.INVALID_SELF_REFERENCE, "Prefix of :" + node.getImage() + " is not the current class or an enclosing class");
				}
				else if( kind.equals("super") )
				{
					if( currentType.encloses( prefixType )  )
					{
						
						if( (prefixType instanceof ClassType) && ((ClassType)prefixType).getExtendType() != null )
							node.setType(((ClassType)prefixType).getExtendType());
						else
							addError(Error.INVALID_SELF_REFERENCE, "Type " + prefixType + " does not have a super class");
					}
					else				
						addError(Error.INVALID_SELF_REFERENCE, "Prefix of qualified super is not the current class or an enclosing class");
				}
				else if( kind.equals("class"))
				{
					if( (prefixType instanceof ClassType) || (prefixType instanceof TypeParameter)  )
						node.setType( Type.CLASS );
					else					
						addError(Error.INVALID_TYPE, ":class constant only accessible on class, enum, error, exception, singleton types and type parameters"); //may need other cases
				}
			}
			else
				addError(Error.NOT_TYPE, "Prefix of :" + kind + " is not a type name");
		}
		return WalkType.POST_CHILDREN;
	}
	
	
	public Object visit(ASTBrackets node, Boolean secondVisit) throws ShadowException 
	{
		if( secondVisit )
		{	
			ModifiedType prefixNode = curPrefix.getFirst();
			prefixNode = resolveType( prefixNode );
			Type prefixType = prefixNode.getType();
													
			if( prefixNode.getModifiers().isTypeName() && !(prefixType instanceof ArrayType) )
			{
				node.setType(new ArrayType( prefixType, node.getArrayDimensions() ) );				
			}
			else
			{
				node.setType(Type.UNKNOWN);
				addError(Error.NOT_TYPE, "Can only apply brackets to a type");
			}
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTSubscript node, Boolean secondVisit) throws ShadowException 
	{
		if( secondVisit )
		{			
			ModifiedType prefixNode = curPrefix.getFirst();
			prefixNode = resolveType( prefixNode );
			Type prefixType = prefixNode.getType();
			
			if( prefixType instanceof ArrayType )
			{
				ArrayType arrayType = (ArrayType)prefixType;
				
				for( int i = 0; i < node.jjtGetNumChildren(); i++ )
				{
					Type childType = node.jjtGetChild(i).getType();
					
					if( !childType.isIntegral() )
					{
						addError(node.jjtGetChild(i), Error.INVALID_SUBSCRIPT, "Subscript type " + childType + " is invalid, integral type required");
						node.setType(Type.UNKNOWN);
						return WalkType.POST_CHILDREN;
					}
				}
				
			    //only goes one level deep because of nullability
				int dimension = node.getArrayDimensions().get(0);				
				if( dimension == arrayType.getDimensions() )
				{
					node.setType( arrayType.getBaseType() );
					node.addModifier(Modifiers.ASSIGNABLE);
					
					//primitive arrays are initialized to default values
					//non-primitive array elements could be null
					if( !arrayType.getBaseType().isPrimitive() )
						node.addModifier(Modifiers.NULLABLE);						
				}				
				else
				{
					node.setType(Type.UNKNOWN);
					addError(Error.INVALID_SUBSCRIPT, "Subscript gives " + node.jjtGetNumChildren() + " indexes but "  + arrayType.getDimensions() + " are required");
				}
				
			}			
			else if( prefixType.hasInterface(Type.CAN_INDEX) )
			{
				if( node.jjtGetNumChildren() == 1)
				{
					SequenceType arguments = new SequenceType();
					Node child = node.jjtGetChild(0);
					arguments.add(child);					
									
					TreeMap<MethodSignature, MethodType> acceptableIndexes = new TreeMap<MethodSignature, MethodType>();
					
					for( MethodSignature signature : prefixType.getAllMethods("index") ) 
					{		
						MethodType methodType = signature.getMethodType();
						
						if( signature.matches( arguments )) 
						{
							//found it, don't look further!
							acceptableIndexes.clear();
							acceptableIndexes.put(signature, methodType);
							break;
						}
						else if( signature.canAccept( arguments ))
							acceptableIndexes.put(signature, methodType);
					}
					
					if( acceptableIndexes.size() == 0 ) 
					{
						addError(Error.INVALID_SUBSCRIPT, "Subscript type " + prefixType + " cannot index into type " + child.getType());
						node.setType(Type.UNKNOWN);
					}					
					else if( acceptableIndexes.size() > 1 )
					{
						addError(Error.INVALID_SUBSCRIPT, "Subscript type " + prefixType + " gives an ambiguous index into type " + child.getType());
						node.setType(Type.UNKNOWN);
					}					
					else
					{
						Entry<MethodSignature, MethodType> entry = acceptableIndexes.firstEntry();
						MethodSignature signature = entry.getKey(); 
						MethodType methodType = entry.getValue();				 
						
						if( !methodIsAccessible( signature, currentType  ))
						{
							node.setType(Type.UNKNOWN);
							addError(Error.ILLEGAL_ACCESS, "Index not accessible from this context");
						}
						else
						{
							node.setType(methodType.getReturnTypes().getType(0));
							node.setModifiers(methodType.getReturnTypes().get(0).getModifiers());
							node.addModifier(Modifiers.ASSIGNABLE);
						}
					}	
				}
				else
				{
					node.setType(Type.UNKNOWN);
					addError(Error.INVALID_SUBSCRIPT, "Subscript supplies multiple indexes into non-array type " + prefixType);
				}				
			}
			else
			{
				node.setType(Type.UNKNOWN);
				addError(Error.INVALID_SUBSCRIPT, "Subscript is not permitted for type " + prefixType + " because it does not implement " + Type.CAN_INDEX);
			}	
			
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTDestroy node, Boolean secondVisit) throws ShadowException 
	{
		if( secondVisit )
		{	
			Node prefixNode = curPrefix.getFirst();
			Type prefixType = prefixNode.getType();			
			
			if( prefixNode.getModifiers().isTypeName()  )
				addError(Error.INVALID_DESTROY, "Type name cannot be destroyed");
			else if( prefixType instanceof UnboundMethodType )
				addError(Error.INVALID_DESTROY, "Method cannot be destroyed");

			else if( prefixType instanceof PropertyType )			
				addError(Error.INVALID_DESTROY, "Property cannot be destroyed");
			
			node.setType(Type.UNKNOWN); //destruction has no type
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTArrayCreate node, Boolean secondVisit) throws ShadowException 
	{
		if( secondVisit )
		{			
			Node prefixNode = curPrefix.getFirst();
			Type prefixType = prefixNode.getType();			
			SequenceType typeArguments = null;
			int start = 0;
			
			if( node.jjtGetChild(0) instanceof ASTTypeArguments )
			{
				typeArguments = (SequenceType) node.jjtGetChild(0).getType();
				start = 1;				
			}
			
			
			for( int i = start; i < node.jjtGetNumChildren(); i++ )
			{
				Type childType = node.jjtGetChild(i).getType();
				
				if( !childType.isIntegral() )
				{
					addError(node.jjtGetChild(i), Error.INVALID_SUBSCRIPT, "Supplied type " +  childType + " cannot be used in this subscript, integral type required");
					node.setType(Type.UNKNOWN);
					return WalkType.POST_CHILDREN;
				}
			}			
			
			//create array
			if( prefixNode.getModifiers().isTypeName() )
			{
				if( typeArguments != null  )
				{
					if( prefixType.isParameterized() )
					{
						if( prefixType.getTypeParameters().canAccept(typeArguments, SubstitutionType.TYPE_PARAMETER) )
						{					
							prefixType = prefixType.replace(prefixType.getTypeParameters(), typeArguments);
							node.setType(new ArrayType( prefixType, node.getArrayDimensions() ) );
						}
						else
						{
							addError(Error.INVALID_TYPE_ARGUMENTS, "Supplied type arguments " + typeArguments + " do not match type parameters " + prefixType.getTypeParameters() );
							node.setType(Type.UNKNOWN);
						}						
					}
					else
					{
						addError(Error.UNNECESSARY_TYPE_ARGUMENTS, "Type arguments " + typeArguments + " supplied for non-parameterized type " + prefixType);						
						node.setType(Type.UNKNOWN);
					}
					
				}
				else
				{
					if( !prefixType.isParameterized() )
						node.setType(new ArrayType( prefixType, node.getArrayDimensions() ) );
					else
					{
						addError(Error.MISSING_TYPE_ARGUMENTS, "Type arguments not supplied for paramterized type " + prefixType);
						node.setType(Type.UNKNOWN);	
					}					
				}				
			}
			else
			{
				node.setType(Type.UNKNOWN);
				addError(Error.NOT_TYPE, "Type name must be used to create an array");
			}	
			
		}
		
		return WalkType.POST_CHILDREN;
	}

	
	
	public Object visit(ASTMethod node, Boolean secondVisit) throws ShadowException 
	{
		if( secondVisit )
		{			
			//always part of a suffix, thus always has a prefix
			ModifiedType prefixNode = curPrefix.getFirst();
			prefixNode = resolveType( prefixNode );
			Type prefixType = prefixNode.getType();
			String methodName = node.getImage();
			
			if( prefixNode.getModifiers().isTypeName() )
			{
				addError(Error.NOT_OBJECT, "Type name cannot be used to call method");				
			}
			else
			{				
				List<MethodSignature> methods = prefixType.getAllMethods(methodName);
				
				//unbound method (it gets bound when you supply arguments)
				if( methods != null && methods.size() > 0 )			
					node.setType( new UnboundMethodType( methodName, prefixType ) );
				else
					addError(Error.UNDEFINED_SYMBOL, "Method " + methodName + " not defined in this context");
			}
			
			
			if( node.getType() == null )
				node.setType( Type.UNKNOWN );
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTAllocation node, Boolean secondVisit)	throws ShadowException
	{
		if( secondVisit )
		{
			pushUpModifiers( node );
			Node child = node.jjtGetChild(0);			
			
			//create sets differently
			if(  !(child instanceof ASTCreate) )			
				node.setType(child.getType());			
		}
		
		return WalkType.POST_CHILDREN;
		
	}
	
	
	@Override
	public Object visit(ASTCreate node, Boolean secondVisit)	throws ShadowException
	{
		if( secondVisit )
		{
			Node prefixNode = curPrefix.getFirst();
			Type prefixType = prefixNode.getType();
			node.setType(Type.UNKNOWN);
			Node parent = node.jjtGetParent();
			
			if( prefixType instanceof InterfaceType )
			{
				addError(Error.INVALID_CREATE, "Interfaces cannot be created");
							
			}
			else if( prefixType instanceof SingletonType )
			{
				addError(Error.INVALID_CREATE, "Singletons cannot be created");
							
			}		
			else if(!( prefixType instanceof ClassType) )
			{
				addError(Error.INVALID_CREATE, "Type " + prefixType + " cannot be created");
				
			}
			else if( !prefixNode.getModifiers().isTypeName() )				
			{
				addError(Error.INVALID_CREATE, "Only a type can be created");
								
			}
			else
			{
				SequenceType typeArguments = null;
			
				
				int start = 0;
				if( node.jjtGetNumChildren() > 0 && (node.jjtGetChild(0) instanceof ASTTypeArguments) )
				{
					typeArguments = (SequenceType) node.jjtGetChild(0).getType();
					start = 1;
				}
				
				SequenceType arguments = new SequenceType();
				
				
				for( int i = start; i < node.jjtGetNumChildren(); i++ )
					arguments.add(node.jjtGetChild(i));

				MethodSignature signature;
				
				if( typeArguments != null )
				{					
					if( prefixType.isParameterized() && prefixType.getTypeParameters().canAccept(typeArguments, SubstitutionType.TYPE_PARAMETER) )
					{
						prefixType = prefixType.replace(prefixType.getTypeParameters(), typeArguments);
						signature = setCreateType( node, prefixType, arguments);
						node.setMethodSignature(signature);
						parent.setType(prefixType);
						//TODO: see if something similar can be done for ASTMethodCall
					}
					else
					{
						addError(Error.INVALID_TYPE_ARGUMENTS, "Supplied type arguments " + typeArguments + " do match type parameters " + prefixType.getTypeParameters());
					}					
				}
				else
				{
					if( !prefixType.isParameterized() )
					{
						signature = setCreateType( node, prefixType, arguments);
						node.setMethodSignature(signature);
						parent.setType(prefixType);
						//TODO: see if something similar can be done for ASTMethodCall
					}
					else
						addError(Error.MISSING_TYPE_ARGUMENTS, "Type arguments not supplied for paramterized type " + prefixType);
				}
				
			}
			
			if( node.getType() == Type.UNKNOWN )
				parent.setType(Type.UNKNOWN);			
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	
	
	@Override
	public Object visit(ASTInstance node, Boolean secondVisit)	throws ShadowException
	{
		if(secondVisit)
		{
			Node prefixNode = curPrefix.getFirst();
			Type prefixType = prefixNode.getType();
			
			if( !prefixNode.getModifiers().isTypeName() )
			{
				addError(Error.INVALID_INSTANCE, "A type is needed to get an instance");
				node.setType(Type.UNKNOWN);
			}
			else if( !(prefixType instanceof SingletonType) )
			{
				addError(Error.INVALID_INSTANCE, "Non-singleton type " + prefixType + " cannot be instanced");
				node.setType(Type.UNKNOWN);
			}
			else
				node.setType(prefixType);
			
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	
	public Object visit(ASTScopeSpecifier node, Boolean secondVisit) throws ShadowException	
	{
		if( secondVisit )
		{	
			//always part of a suffix, thus always has a prefix
			ModifiedType prefixNode = curPrefix.getFirst();
			prefixNode = resolveType( prefixNode );
			Type prefixType = prefixNode.getType(); 
			String name = node.getImage();
			boolean isTypeName = prefixNode.getModifiers().isTypeName();
			
			if( prefixType.containsField( name ) )
			{
				Node field = prefixType.getField(name);
				
				if( !fieldIsAccessible( field, currentType ))
				{
					addError(Error.ILLEGAL_ACCESS, "Field " + name + " not accessible from this context");
				}					
				else if( field.getModifiers().isConstant() && !isTypeName )
				{
					addError(Error.ILLEGAL_ACCESS, "Constant " + name + " requires class name for access");
				}
				else
				{
					node.setType( field.getType());
					node.setModifiers(field.getModifiers());						
					if( !field.getModifiers().isConstant() )
						node.addModifier(Modifiers.ASSIGNABLE);					
				}
			}
			else if( prefixType instanceof ClassType && ((ClassType)prefixType).containsInnerClass(name) )
			{
				ClassType classType = (ClassType) prefixType;
				ClassType innerClass = classType.getInnerClass(name);
				
				if( !classIsAccessible( innerClass, currentType ) )
					addError(Error.ILLEGAL_ACCESS, "Class " + innerClass + " is not accessible from this context");
				else
				{
					node.setType( innerClass );						
					node.addModifier(Modifiers.TYPE_NAME);					
				}					
			}
			else
				addError(Error.UNDEFINED_SYMBOL, "Field or inner class " + name + " not defined in this context");
	
			
			if( node.getType() == null )
				node.setType( Type.UNKNOWN );
		}
		
		return WalkType.POST_CHILDREN;
	}
	
		
	public Object visit(ASTProperty node, Boolean secondVisit) throws ShadowException	
	{
		if( secondVisit )
		{		
			//always part of a suffix, thus always has a prefix
			ModifiedType prefixNode = curPrefix.getFirst();
			prefixNode = resolveType( prefixNode );
			Type prefixType = prefixNode.getType();
			String propertyName = node.getImage();
			boolean isTypeName = prefixNode.getModifiers().isTypeName();
						
						
			if( isTypeName )
			{
				addError(Error.NOT_OBJECT, "Object reference must be used to access property " + propertyName);
				node.setType( Type.UNKNOWN ); //if got here, some error
			}	
			else
			{				
				List<MethodSignature> methods = prefixType.getAllMethods(propertyName);				

				if( methods != null && methods.size() > 0 )
				{
					MethodSignature getter = null;
					MethodSignature setter = null;
					
					for( MethodSignature signature : methods )
					{
						if( signature.getModifiers().isGet() )
							getter = signature;
						else if( signature.getModifiers().isSet() )
						{
							setter = signature;
							node.addModifier(Modifiers.ASSIGNABLE);
						}
					}
					
					node.setType( new PropertyType( getter, setter ) );
				}
				else
				{
					addError(Error.UNDEFINED_SYMBOL, "Property " + propertyName + " not defined in this context");
					node.setType( Type.UNKNOWN ); //if got here, some error
				}				
			}
			
		}
	
		return WalkType.POST_CHILDREN;
	}
	
	private ModifiedType resolveType( ModifiedType node ) //dereferences into PropertyType for getter, if needed
	{
		Type type = node.getType();
		
		if( type instanceof PropertyType )
		{
			PropertyType propertyType = (PropertyType) type;
			if( propertyType.isGettable() )
				return propertyType.getGetType();
			else
			{
				addError(Error.ILLEGAL_ACCESS, "Property " + node + " does not have get access");
				return node;
			}				
		}
		else
			return node;
	}
	
	protected MethodSignature setCreateType( ASTCreate node, Type prefixType, SequenceType arguments)
	{			
		return setMethodType( node, prefixType, "create", arguments, null);		
	}
	
	protected MethodSignature setMethodType( Node node, Type type, String method, SequenceType arguments)
	{
		return setMethodType( node, type, method, arguments, null );
	}

	protected MethodSignature setMethodType( Node node, Type type, String method, SequenceType arguments, SequenceType typeArguments )
	{	
		List<TypeCheckException> errors = new ArrayList<TypeCheckException>();
		MethodSignature signature = type.getMatchingMethod(method, arguments, typeArguments, errors);
		
		if( signature == null )
			addErrors(errors);	
		else
		{
			if( !methodIsAccessible( signature, currentType  ))					
				addError(Error.ILLEGAL_ACCESS, signature + " is not accessible from this context");						
		
			node.setType(signature.getMethodType());		
		}		
		
		return signature;
	}
	
	public Object visit(ASTMethodCall node, Boolean secondVisit) throws ShadowException	
	{		
		if( secondVisit )
		{			
			//always part of a suffix, thus always has a prefix
			ModifiedType prefixNode = curPrefix.getFirst();
			prefixNode = resolveType( prefixNode );
			Type prefixType = prefixNode.getType();
			
			int start = 0;			
			SequenceType typeArguments = null;
			
			if( node.jjtGetNumChildren() > 0 && (node.jjtGetChild(0) instanceof ASTTypeArguments) )
			{
				start = 1;			
				typeArguments = (SequenceType) node.jjtGetChild(0).getType();
			}
			
			SequenceType arguments = new SequenceType();
			
			for( int i = start; i < node.jjtGetNumChildren(); i++ )
				arguments.add(node.jjtGetChild(i));
			
		
			if( prefixType instanceof UnboundMethodType )
			{
				UnboundMethodType unboundMethod = (UnboundMethodType)(prefixType);				
				Type outer = prefixType.getOuter();
				MethodSignature signature = setMethodType(node, outer, unboundMethod.getTypeName(), arguments, typeArguments); //type set inside
				node.setMethodSignature(signature);
				
				if( signature != null &&  prefixNode.getModifiers().isImmutable() && !signature.getModifiers().isImmutable() && !signature.getModifiers().isReadonly()  )
					addError(Error.ILLEGAL_ACCESS, "Mutable method " + signature + " cannot be called from an immutable reference");
				
				if( signature != null &&  prefixNode.getModifiers().isReadonly() && !signature.getModifiers().isImmutable() && !signature.getModifiers().isReadonly()  )
					addError(Error.ILLEGAL_ACCESS, "Mutable method " + signature + " cannot be called from a readonly reference");				
			}			
			else if( prefixType instanceof MethodType ) //only happens with method pointers and local methods
			{
				MethodType methodType = (MethodType)prefixType;
				
				if( methodType.isParameterized() )
				{
					if( typeArguments != null )
					{						
						SequenceType parameters = methodType.getTypeParameters();
						if( parameters.canAccept(typeArguments, SubstitutionType.TYPE_PARAMETER))
							methodType = methodType.replace(parameters, typeArguments);
						else
						{
							addError(Error.INVALID_TYPE_ARGUMENTS, "Supplied type arguments " + typeArguments + " do not match method type arguments " + typeArguments);
							node.setType(Type.UNKNOWN);
						}							
					}
				}
				
				if( methodType.isInline() )
				{
					for( SignatureNode signatureNode : currentMethod )
						if( signatureNode.getMethodSignature().getMethodType() == methodType ) //if literally the same MethodType, we have illegal recursion						
							addError(Error.INVALID_STRUCTURE, "Inline method cannot be called recursively");
				}
				
				if( methodType.canAccept( arguments ) )
					node.setType(methodType);
				else 
				{
					addError(Error.INVALID_ARGUMENTS, "Supplied method arguments " + arguments + " do not match method parameters " + methodType.getParameterTypes());
					node.setType(Type.UNKNOWN);
				}
								
				if( prefixNode.getModifiers().isImmutable() && !methodType.getModifiers().isImmutable() && !methodType.getModifiers().isReadonly()  )
					addError(Error.ILLEGAL_ACCESS, "Mutable method cannot be called from an immutable reference");
				
				if( prefixNode.getModifiers().isReadonly() && !methodType.getModifiers().isImmutable() && !methodType.getModifiers().isReadonly()  )
					addError(Error.ILLEGAL_ACCESS, "Mutable method cannot be called from a readonly reference");
			}			
			else
			{									
				addError(Error.INVALID_METHOD, "Cannot apply arguments to non-method type " + prefixType);
				node.setType(Type.UNKNOWN);			
			}
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	public static boolean fieldIsAccessible( Node field, Type type )
	{
		//inside class or constant
		if ( field.getModifiers().isConstant() ) 
			return true;		
		
		type = type.getTypeWithoutTypeArguments();
		Type enclosing = field.getEnclosingType().getTypeWithoutTypeArguments();
		
		while( type != null )
		{
			if( enclosing.equals(type) )
				return true;
			
			type = type.getOuter();
		}

		return false;
	}

	
	public Object visit(ASTBreakStatement node, Boolean secondVisit) throws ShadowException 
	{
		//TODO: Check if break is in loop or switch
		
		return WalkType.PRE_CHILDREN;
	}	
	
	private boolean visitArrayInitializer(Node node)
	{
		Node child = node.jjtGetChild(0);
		Type result = child.getType();
		
		for( int i = 1; i < node.jjtGetNumChildren(); i++ ) //cycle through types, upgrading to broadest legal type
		{
			child = node.jjtGetChild(i);
			Type type = child.getType();
							
			if( result.isSubtype(type) )					
				result = type;				
			else if( !type.isSubtype(result) ) //neither is subtype of other, panic!
			{
				addError(Error.MISMATCHED_TYPE, "Types in array initializer list do not match");
				node.setType(Type.UNKNOWN);
				return false;
			}
		}

			
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
		ArrayType arrayType = new ArrayType( baseType, dimensions );
		node.setType(arrayType);
		((ClassType)currentType).addReferencedType(arrayType);
		
		return true;		
	}
	
	public Object visit(ASTArrayInitializer node, Boolean secondVisit) throws ShadowException 
	{		
		if( secondVisit )
			visitArrayInitializer(node);		
	
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTAssertStatement node, Boolean secondVisit) throws ShadowException 
	{
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
	
		Type assertType = node.jjtGetChild(0).getType();
		
		if( !assertType.equals(Type.BOOLEAN))
			addError(Error.INVALID_TYPE, "Supplied type " + assertType + " cannot be used in the condition of an assert, boolean required");
		
		if( node.jjtGetNumChildren() > 1 )
		{
			Node child = node.jjtGetChild(1);
			Type type = child.getType();
			if( type == null )
				addError(Error.NOT_OBJECT, "Object type required for assert information and no type found");
			else if( child.getModifiers().isTypeName() )
				addError(Error.NOT_OBJECT, "Object type required for assert information but type name used");
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTLiteral node, Boolean secondVisit) throws ShadowException {						
		node.setType(literalToType(node.getLiteral()));		
		return WalkType.NO_CHILDREN;			
	}
	
	@Override
	public Object visit(ASTPrimitiveType node, Boolean secondVisit) throws ShadowException {		
		node.setType(nameToPrimitiveType(node.getImage()));		
		return WalkType.NO_CHILDREN;			
	}	
		
	@Override
	public Object visit(ASTReturnStatement node, Boolean secondVisit)	throws ShadowException
	{
		if( secondVisit )
		{
			if( currentMethod.isEmpty() ) //should never happen			
				addError(Error.INVALID_STRUCTURE, "Return statement cannot be outside of method body");
			else
			{
				MethodType methodType = (MethodType)(currentMethod.getFirst().getType());
				SequenceType returnTypes  = methodType.getReturnTypes();
				node.setType(returnTypes);
				
				if( returnTypes.size() == 0 )
				{
					if( node.jjtGetNumChildren() > 0 )
						addError(Error.INVALID_RETURNS, "Cannot return values from a method that returns nothing");
				}
				else
				{
					Node child = node.jjtGetChild(0);
					Type type = child.getType();
					
					
					SequenceType sequenceType;
					
					if( type instanceof SequenceType )										
						sequenceType = (SequenceType)type;
					else
					{
						sequenceType = new SequenceType();
						sequenceType.add(child);						
					}
					
					if( !sequenceType.isSubtype(returnTypes) )						
						addError(Error.INVALID_RETURNS, "Cannot return " + sequenceType + " when " + returnTypes + (returnTypes.size() == 1 ? " is" : " are") + " expected" );
					
					for( ModifiedType modifiedType : sequenceType )
						if( modifiedType.getModifiers().isTypeName() )
							addError(Error.INVALID_RETURNS, "Cannot return type name from a method" );
				}				
				
				node.setType(returnTypes);
			}
		}	
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTResultType node, Boolean secondVisit) throws ShadowException
	{
		if( secondVisit )			
		{
			Type type = node.jjtGetChild(1).getType(); //child 0 is always Modifiers 
			node.setType(type);
			
			if( node.getModifiers().isNullable() && type.isPrimitive() )
				addError(Error.INVALID_MODIFIER, "Modifier nullable cannot be applied to primitive type " + type);				
		}
		
		return WalkType.POST_CHILDREN;	
	}
	
	public Object visit(ASTExplicitCreateInvocation node, Boolean secondVisit) throws ShadowException
	{	
		if( secondVisit )
		{
			SequenceType arguments = new SequenceType();
			for( int i = 0; i < node.jjtGetNumChildren(); i++ )
				arguments.add(node.jjtGetChild(i));			
			
			if( currentType instanceof ClassType )
			{
				ClassType type = (ClassType) currentType; //assumes "this" 
				if( node.getImage().equals("super") )
				{
					if( type.getExtendType() != null )
						type = type.getExtendType();
					else
						addError(Error.INVALID_CREATE, "Class type " + type + " cannot invoke a parent create because it does not extend another type");
				}
								
				boolean found = false;
				for( MethodSignature signature : type.getMethods("create") )
				{
					if( signature.matches(arguments) )
					{
						found = true;
						node.setMethodSignature(signature);
						break;						
					}
				}
				
				if( !found )
					addError(Error.INVALID_METHOD, "Create with arguments " + arguments + " is not defined in this context");
				
			}	
			else
				addError(Error.INVALID_CREATE, "Non-class type " + currentType + " cannot be created");
			
			ASTCreateDeclaration grandparent = (ASTCreateDeclaration) node.jjtGetParent().jjtGetParent();
			grandparent.setExplicitInvocation(true);
		}		
		
		return WalkType.POST_CHILDREN; 
	}
	

	// Everything below here are visitors to push up the type

	public Object visit(ASTType node, Boolean secondVisit) throws ShadowException { return pushUpType(node, secondVisit); }
	
	public Object visit(ASTVariableInitializer node, Boolean secondVisit) throws ShadowException {
		if( !secondVisit )
			return WalkType.POST_CHILDREN;
		
		Node child = node.jjtGetChild(0);
		Type childType = child.getType(); 
		if( childType instanceof PropertyType )
		{
			PropertyType propertyType = (PropertyType) childType;
			if( propertyType.isGettable() )
			{
				node.setType(propertyType.getGetType().getType());
				node.setModifiers(propertyType.getGetType().getModifiers());					
			}
			else
			{
				node.setType(Type.UNKNOWN);
				addError(Error.ILLEGAL_ACCESS, "Property " + child + "does not have get access.");
			}				
		}
		else
		{
			node.setType(child.getType());
			node.setModifiers(child.getModifiers());
		}
		
		return WalkType.POST_CHILDREN; 
	}
	
	public Object visit(ASTExtendsList node, Boolean secondVisit) throws ShadowException { 
		return WalkType.NO_CHILDREN;
	}
	
	public Object visit(ASTImplementsList node, Boolean secondVisit) throws ShadowException { 
		return WalkType.NO_CHILDREN;
	}
	
	public Object visit(ASTInlineMethodDefinition node, Boolean secondVisit) throws ShadowException 
	{	
		Node child = node.jjtGetChild(0);
		
		if( child instanceof ASTFormalParameters ) //inline method definition
		{
			if( secondVisit )
			{				
				MethodType methodType = node.getMethodSignature().getMethodType();
				ASTFormalParameters parameters = (ASTFormalParameters) child;
				List<String> parameterNames = parameters.getParameterNames();
				SequenceType parameterTypes = parameters.getType();		
				
				for( int i = 0; i < parameterNames.size(); ++i )				
					methodType.addParameter(parameterNames.get(i), parameterTypes.get(i));
				
				//add return types				
				ASTInlineResults results = (ASTInlineResults) node.jjtGetChild(1);
							
				for( ModifiedType modifiedType : results.getType() ) 
					methodType.addReturn(modifiedType);				
				
				node.setType(methodType);
			}
			
			visitMethod(node, secondVisit);
			
		}
		else //primary expression
		{
			if( secondVisit )
			{
				node.setType(child.getType());
				pushUpModifiers( node );
			}
		}
		
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTInlineMethodDeclarator node, Boolean secondVisit) throws ShadowException
	{
		if( secondVisit )		
			visitDeclarator( node );		
		
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTMethodDeclarator node, Boolean secondVisit) throws ShadowException 
	{ 
		if( node.isLocal() ) 
		{
			if( secondVisit )			
				visitDeclarator( node );			
			
			return WalkType.POST_CHILDREN;
		}							
		else //non-local already handled
			return WalkType.NO_CHILDREN;					
	}
	
	private void visitDeclarator( Node node )
	{
		SignatureNode parent = (SignatureNode) node.jjtGetParent();
		MethodSignature signature = parent.getMethodSignature();
		int index = 0;
		
		//add type parameters
		if( node.jjtGetChild(index) instanceof ASTTypeParameters )
		{
			MethodType methodType = signature.getMethodType();
			ASTTypeParameters typeParameters = (ASTTypeParameters) node.jjtGetChild(index);
			for( ModifiedType modifiedType : typeParameters.getType() )
				methodType.addTypeParameter(modifiedType);
			
			index++;
		}
		
		//add parameters			
		ASTFormalParameters parameters = (ASTFormalParameters) node.jjtGetChild(index);
		List<String> parameterNames = parameters.getParameterNames();
		SequenceType parameterTypes = parameters.getType();		
		
		for( int i = 0; i < parameterNames.size(); ++i )				
			signature.addParameter(parameterNames.get(i), parameterTypes.get(i));
		
		//add return types
		index++;
		SequenceNode results = (SequenceNode) node.jjtGetChild(index);
					
		for( ModifiedType modifiedType : results.getType() ) 
			signature.addReturn(new SimpleModifiedType(modifiedType.getType()));		
	}
}
