package shadow.typecheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import shadow.AST.ASTWalker;
import shadow.AST.ASTWalker.WalkType;
import shadow.interpreter.ShadowBoolean;
import shadow.interpreter.ShadowCode;
import shadow.interpreter.ShadowDouble;
import shadow.interpreter.ShadowFloat;
import shadow.interpreter.ShadowInteger;
import shadow.interpreter.ShadowString;
import shadow.interpreter.ShadowValue;
import shadow.parser.javacc.*;
import shadow.parser.javacc.ASTAssignmentOperator.AssignmentType;
import shadow.typecheck.TypeCheckException.Error;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.EnumType;
import shadow.typecheck.type.ExceptionType;
import shadow.typecheck.type.InstantiationException;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.PropertyType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.SubscriptType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;
import shadow.typecheck.type.UnboundMethodType;

public class StatementChecker extends BaseChecker {
	protected LinkedList<Node> curPrefix = null; 	/** Stack for current prefix (needed for arbitrarily long chains of expressions) */
	protected LinkedList<Node> labels = null; 	/** Stack of labels for labeled break statements */
	protected LinkedList<ASTTryStatement> tryBlocks = null; /** Stack of try blocks currently nested inside */	
	protected LinkedList<HashMap<String, ModifiedType>> symbolTable; /** List of scopes with a hash of symbols & types for each scope */
	protected LinkedList<Node> scopeMethods; /** Keeps track of the method associated with each scope (sometimes null) */
	
	public StatementChecker(HashMap<Package, HashMap<String, Type>> typeTable, List<String> importList, Package packageTree ) {
		super(typeTable, importList, packageTree );		
		symbolTable = new LinkedList<HashMap<String, ModifiedType>>();
		curPrefix = new LinkedList<Node>();			
		tryBlocks = new LinkedList<ASTTryStatement>();
		scopeMethods = new LinkedList<Node>();
	}
	
	public void check(Node node) throws ShadowException, TypeCheckException
	{
		ASTWalker walker = new ASTWalker(this);
		
		// now go through and check the whole class
		walker.walk(node);
		
		if( errorList.size() > 0 )
		{
			printErrors();
			printWarnings();
			throw errorList.get(0);
		}	
		
		printWarnings();
		warningList.clear();
	}
	
	//Important!  Set the current type on entering the body, not the declaration, otherwise extends and imports are improperly checked with the wrong outer class
	public Object visit(ASTClassOrInterfaceBody node, Boolean secondVisit) throws ShadowException {		
		if( secondVisit )
			currentType = currentType.getOuter();		
		else
		{
			currentType = node.jjtGetParent().getType(); //get type from declaration
			
			for( InterfaceType interfaceType : currentType.getInterfaces() )
				currentType.addReferencedType(interfaceType);
						
			if( currentType instanceof ClassType )
			{
				ClassType classType = (ClassType) currentType;
				currentType.addReferencedType(classType.getExtendType());
			}
		}
			
		return WalkType.POST_CHILDREN;
	}

	public Object visitMethod( SignatureNode node, Boolean secondVisit  )
	{
		if(!secondVisit)
		{		
			MethodSignature signature;
			
			if( node instanceof ASTLocalMethodDeclaration || /*node instanceof ASTInlineMethodDeclaration ||*/ node instanceof ASTInlineMethodDefinition )
			{	
				if( node instanceof ASTInlineMethodDefinition )
					signature = new MethodSignature( currentType, "", node.getModifiers(), node);
				else
					signature = new MethodSignature( currentType, node.jjtGetChild(0).getImage(), node.getModifiers(), node);
				node.setMethodSignature(signature);
				MethodType methodType = signature.getMethodType();

				if( node instanceof ASTInlineMethodDefinition  )
					methodType.setInline(true);				
				
				node.setType(methodType);
				node.setEnclosingType(currentType);
				//what modifiers (if any) are allowed for a local method declaration?
			}
			else
				signature = node.getMethodSignature();
			
			for( ModifiedType modifiedType : signature.getParameterTypes() )
				currentType.addReferencedType(modifiedType.getType());
			
			for( ModifiedType modifiedType : signature.getReturnTypes() )
				currentType.addReferencedType(modifiedType.getType());			
			
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
		if(secondVisit)
		{			
			int defaultCounter = 0;
			Type type = node.jjtGetChild(0).getType();
			if(!type.isIntegral() && !type.isString() && !(type instanceof EnumType))
				addError(Error.INVALID_TYPE, "Supplied type " + type + " cannot be used in switch statement, only integral, String, and enum types allowed", type);
			for(int i=1; i<node.jjtGetNumChildren(); ++i) {
				Node childNode = node.jjtGetChild(i);
				if(childNode.getClass() == ASTSwitchLabel.class) {
					if(childNode.getType() != null){ //default label should have null type 
						if(!childNode.getType().isSubtype(type))
							addError(childNode,Error.INVALID_LABEL,"Label type " + childNode.getType() + " does not match switch type " + type, childNode.getType(), type);
					}
					else
						defaultCounter++;
				}	
			}
			
			if( defaultCounter == 1 )
				node.setDefault(true);
			else if( defaultCounter > 1 )
				addError(node, Error.INVALID_LABEL, "Switch cannot have multiple default labels");			
		}
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTSwitchLabel node, Boolean secondVisit) throws ShadowException
	{	
		if( secondVisit && node.jjtGetNumChildren() > 0)
		{	
			Type result = null; 
		
			for( int i = 0; i < node.jjtGetNumChildren(); i++  )
			{
				Node child = node.jjtGetChild(0);
				Type type = child.getType();
	
				if( result == null )
					result = type;				
				else if( result.isSubtype(type) )
					result = type;
				else if( !type.isSubtype(result) ) //neither is subtype of other, panic!
				{
					addError(Error.MISMATCHED_TYPE, "Supplied type " + type + " does not match type " + result + " in switch label", type, result);
					result = Type.UNKNOWN;
					break;
				}
				
				if( !child.getModifiers().isConstant() )
					addError(Error.INVALID_TYPE, "Value supplied as label must be constant");
			}
			
			node.setType(result);
			node.addModifier(Modifiers.CONSTANT);
		}
		
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
	
	/*
	public Object visit(ASTInlineMethodDeclaration node, Boolean secondVisit) throws ShadowException {
		if( !secondVisit )
		{	
			Node declaration = node.jjtGetChild(0);			
			addSymbol(declaration.getImage(), node);
		}		
		return visitMethod( node, secondVisit );
	}
	*/	
	
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
				
				if( parameter.getType() instanceof SingletonType )
					addError(parameter, Error.INVALID_PARAMETERS, "Cannot define method with singleton parameter");
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
			node.setType( type );			
			
			if( node.getModifiers().isNullable() && type.isPrimitive() )
				addError(Error.INVALID_MODIFIER, "Modifier nullable cannot be applied to primitive type " + type);
		
			addSymbol( node.getImage(), node );
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
					if( !node.hasExplicitInvocation() && !node.getModifiers().isNative() ) 
						//only worry if there is no explicit invocation
						//explicit invocations are handled separately
						//for native creates, we have to trust the author of the native code
					{
						boolean foundDefault = false;						
						for( MethodSignature method : parentType.getMethods("create") )
						{
							if( method.getParameterTypes().isEmpty() )
							{
								foundDefault = true;
								break;
							}
						}
					
						if( !foundDefault )
							addError(Error.MISSING_CREATE, "Explicit create invocation is missing, and parent class " + parentType + " does not implement the default create", parentType);
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
				ArrayType arrayType = new ArrayType(type, dimensions, node.getModifiers().isNullable());
				((ClassType)currentType).addReferencedType(arrayType);
				node.setType(arrayType);
			}
			
			node.addModifier(Modifiers.TYPE_NAME);
		}
		
		return WalkType.POST_CHILDREN;
	}
		
	public Object visit(ASTLocalVariableDeclaration node, Boolean secondVisit) throws ShadowException
	{		
		if(secondVisit)
		{	
			Type type = null;			
			Node child = node.jjtGetChild(0);			
			boolean isVar = false;
			
			if( child instanceof ASTType )			
				type = child.getType();
			else //var type
			{
				type = Type.VAR;
				isVar = true;
			}
			
			node.setType(type);		
			
			//add variables
			for( int i = isVar ? 0 : 1; i < node.jjtGetNumChildren(); ++i )
			{
				Node declarator = node.jjtGetChild(i);
				
				if( isVar )
				{
					if( declarator.jjtGetNumChildren() > 0 ) //has initializer						
						type = declarator.jjtGetChild(0).getType();					
					else
					{
						type = Type.UNKNOWN;
						addError(declarator, Error.UNDEFINED_TYPE, "Variable declared with var has no initializer to infer type from");
					}
				}
				
				if( node.getModifiers().isNullable() && type instanceof ArrayType  ) {
					ArrayType arrayType = (ArrayType) type;
					type = arrayType.convertToNullable();
				}
				
				//we're going to allow nullable primitives
				//if( node.getModifiers().isNullable() && type.isPrimitive() )
				//	addError(Error.INVALID_MODIFIER, "Primitive type " + type + " cannot be marked nullable");
				
				declarator.setType(type);										
				declarator.setModifiers(node.getModifiers());					
				addSymbol( declarator.getImage(), declarator ); //add to local scope
			}
						
			checkInitializers( node );
		}

		return WalkType.POST_CHILDREN;
	}
	
	private void checkInitializers(Node node)
	{
		int start = 0;
		Node declarator = node.jjtGetChild(start);  //could be either VariableDeclarator or SequenceDeclarator
		if( declarator instanceof ASTType ) //if type, skip to first declarator
			start++;
		
		for( int i = start; i < node.jjtGetNumChildren(); ++i )
		{
			declarator = node.jjtGetChild(i);
			if( declarator.jjtGetNumChildren() > 0 ) //has initializer
				addErrors(declarator, isValidInitialization(declarator, declarator.jjtGetChild(0)));
			else if( declarator.getModifiers().isConstant() ) //only fields are ever constant
				addError( declarator, Error.INVALID_MODIFIER, "Field declared with modifier constant must have an initializer");
		}			
	}

	public Object visit(ASTFieldDeclaration node, Boolean secondVisit) throws ShadowException
	{		
		if(secondVisit)
			checkInitializers(node); //check all initializers
		else
			currentType.addReferencedType(node.getType());

		return WalkType.POST_CHILDREN;
	}	
	
	public boolean setTypeFromContext( Node node, String name, Type context )
	{
		if( context instanceof TypeParameter )
		{
			TypeParameter typeParameter = (TypeParameter) context;
			for( Type type : typeParameter.getBounds() )
				if( setTypeFromContext( node, name, type ) )
					return true;
			
			return setTypeFromContext( node, name, Type.OBJECT );			
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
			Modifiers methodModifiers = Modifiers.NO_MODIFIERS;
			if( !currentMethod.isEmpty() )
				methodModifiers = currentMethod.getFirst().getModifiers();
			
			/*//maybe not necessary?  since arrays "extend" Array or NullableArray
			if( context instanceof NullableArrayType )
				classType = Type.NULLABLE_ARRAY;
			else if( context instanceof ArrayType )
				classType = Type.ARRAY;
			else
			*/
				classType = (ClassType)context;
					
			if(classType.recursivelyContainsField(name))
			{
				Node field = classType.recursivelyGetField(name);
				node.setType(field.getType());
				node.setModifiers(field.getModifiers());
				
				if( !fieldIsAccessible( field, currentType ) )
					addError(Error.ILLEGAL_ACCESS, "Field " + name + " not accessible from this context");
				else
				{						
					if( field.getModifiers().isConstant() )
					{
						if( currentMethod.isEmpty() ) //constants are only assignable in declarations
							node.addModifier(Modifiers.ASSIGNABLE);
					}
					//creates and declarations are not marked immutable or readonly
					else if( methodModifiers.isImmutable() || methodModifiers.isReadonly() )						
						node.getModifiers().upgradeToTemporaryReadonly();
					else
						node.addModifier(Modifiers.ASSIGNABLE);
				}
				
				return true;			
			}
				
			if( classType.recursivelyContainsMethod(name))
			{
				node.setType( new UnboundMethodType( name, classType ) );	
				if( methodModifiers != null && methodModifiers.isImmutable() )
					node.addModifier(Modifiers.IMMUTABLE);
				else if( methodModifiers != null && methodModifiers.isReadonly() )
					node.addModifier(Modifiers.READONLY);
				return true;
			}
			
			/*
			if( classType.recursivelyContainsInnerClass(name))
			{
				Type innerClass = classType.recursivelyGetInnerClass(name);
				node.setType(innerClass);
				node.setModifiers(Modifiers.TYPE_NAME);
				return true;
			}
			*/
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
		if( setTypeFromContext( node, name, currentType ) )
			return true;
				
		//is it a type?
		Type type = lookupType( name );		
				
		if(type != null)
		{
			currentType.addReferencedType(type);			
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
				
				if( result.hasUninstantiatedInterface(Type.CAN_COMPARE) )
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
						addError(Error.INVALID_TYPE, "Operator " + symbol + " not defined on types " + result + " and " + current, result, current);
						result = Type.UNKNOWN;	
						break;
					}				
				}
				else		
				{
					addError(Error.INVALID_TYPE, "Operator " + symbol + " not defined on types " + result + " and " + current, result, current);
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
			{
				result = Type.STRING;
				for( int i = 0; i < node.jjtGetNumChildren() && result != Type.UNKNOWN; ++i )
				{
					Type childType = node.jjtGetChild(i).getType(); 
					if( childType instanceof SequenceType )
					{
						addError(Error.INVALID_TYPE, "Cannot apply operator # to sequence type " + childType, childType);
						result = Type.UNKNOWN;						
					}
				}
			}
				
			node.setType(result); //propagates type up if only one child
			pushUpModifiers(node); //can add ASSIGNABLE (if only one child)
		}
		
		return WalkType.POST_CHILDREN;	
	}	
	
	public Object visit(ASTEqualityExpression node, Boolean secondVisit) throws ShadowException
	{
		if( secondVisit )
		{
			Node result = node.jjtGetChild(0);
			Type resultType = result.getType();
						
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
					if( result.getModifiers().isNullable() )
					{
						addError(Error.INVALID_TYPE, "Cannot apply operator " + symbol + " to statement with nullable type");
						resultType = Type.UNKNOWN;	
						break;
					}						
					
					if( resultType.hasUninstantiatedInterface(Type.CAN_EQUAL) )
					{
						SequenceType argument = new SequenceType();
						argument.add(currentNode);							
						 
						MethodSignature signature = setMethodType(node, resultType, "equal", argument );
						if( signature != null )
						{
							resultType = signature.getReturnTypes().getType(0);
							node.addOperation(signature);
						}
						else
						{
							addError(Error.INVALID_TYPE, "Cannot apply operator " + symbol + " to types " + resultType + " and " + current, resultType, current);
							resultType = Type.UNKNOWN;	
							break;
						}				
					}
					else		
					{
						addError(Error.INVALID_TYPE, "Cannot apply operator " + symbol + " to types " + resultType + " and " + current, resultType, current);
						resultType = Type.UNKNOWN;
						break;
					}			
				}
				else if( !resultType.isSubtype(current) && !current.isSubtype(resultType) )
				{
					addError(Error.INVALID_TYPE, "Cannot apply operator " + symbol + " to types " + resultType + " and " + current, resultType, current);
					resultType = Type.UNKNOWN;	
					break;
				}	
				
				resultType = Type.BOOLEAN;  //boolean after one comparison			
			}
			
			node.setType(resultType); //propagates type up if only one child
			pushUpModifiers(node); //can overwrite ASSIGNABLE (if only one child)
		}
		
		return WalkType.POST_CHILDREN;		
	}
	
	public void visitBinary( OperationNode node ) throws ShadowException
	{
		Type result = node.jjtGetChild(0).getType();
		for( int i = 1; i < node.jjtGetNumChildren(); i++ ) //cycle through types, upgrading to broadest legal one
		{			
			char operator = node.getImage().charAt(i - 1);
			InterfaceType interfaceType = null;
			String methodName = "";
			String symbol = String.valueOf(operator);
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
			case 'l':
				methodName = "bitShiftLeft";
				symbol = "<<";
				interfaceType = Type.INTEGER;
				break;
			case 'L':
				methodName = "bitRotateLeft";
				symbol = "<<<";
				interfaceType = Type.INTEGER;
				 break;
			case 'r':
				methodName = "bitShiftRight";
				symbol = ">>";
				interfaceType = Type.INTEGER;
				break;
			case 'R':
				methodName = "bitRotateRight";
				symbol = ">>>";
				interfaceType = Type.INTEGER;
				break;
			}
			
			Node currentNode = node.jjtGetChild(i);
			Type current = currentNode.getType();
			
			if( result.hasUninstantiatedInterface(interfaceType) )
			//we can't know which one will work
			//so we go with an uninstantiated version and then find an appropriate signature
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
					addError(node.jjtGetChild(i), Error.INVALID_TYPE, "Cannot apply operator " + symbol + " to types " + result + " and " + current, result, current);
					node.setType(Type.UNKNOWN);
					return;
				}				
			}
			else		
			{
				addError(node.jjtGetChild(i), Error.INVALID_TYPE, "Cannot apply operator " + symbol + " to types " + result + " and " + current, result, current);
				node.setType(Type.UNKNOWN);
				return;						
			}				
		}
			
		node.setType(result); //propagates type up if only one child
		pushUpModifiers(node); //can add ASSIGNABLE (if only one child)		
	}
	
	public Object visit(ASTShiftExpression node, Boolean secondVisit) throws ShadowException {
		if(secondVisit)
			visitBinary( node );
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTRotateExpression node, Boolean secondVisit) throws ShadowException {
		if(secondVisit)
			visitBinary( node );
		
		return WalkType.POST_CHILDREN;
	}
	
	
	public Object visit(ASTAdditiveExpression node, Boolean secondVisit) throws ShadowException {
		if(secondVisit)
			visitBinary( node );
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTMultiplicativeExpression node, Boolean secondVisit) throws ShadowException {
		if(secondVisit)
			visitBinary( node );
		
		return WalkType.POST_CHILDREN;
	}
		
	private Type visitUnary( ASTUnaryExpression node, String method, String operator, InterfaceType interfaceType )
	{
		Type type = node.jjtGetChild(0).getType();
		
		if( type.hasUninstantiatedInterface(interfaceType) )
		{
			MethodSignature signature = setMethodType(node, type, method, new SequenceType() );
			if( signature != null )
			{
				type = signature.getReturnTypes().getType(0);
				node.addOperation(signature);
			}
			else
			{
				addError(Error.INVALID_TYPE, "Cannot apply " + operator + " to type " + type + " which does not implement interface " + interfaceType, type);
				type = Type.UNKNOWN;						
			}
		}
		else
		{
			addError(Error.INVALID_TYPE, "Cannot apply " + operator + " to type " + type + " which does not implement interface " + interfaceType, type);
			type = Type.UNKNOWN;					
		}
		
		return type;
	}
		
	public Object visit(ASTUnaryExpression node, Boolean secondVisit) throws ShadowException
	{
		if(secondVisit)
		{	
			String operator = node.getImage();
			Type type = node.jjtGetChild(0).getType();
			
			if( operator.equals("-") )
				type = visitUnary( node, "negate", "unary -", Type.CAN_NEGATE);
			else if( operator.equals("~") )
				type = visitUnary( node, "bitComplement", "operator ~", Type.INTEGER);
			else if( operator.equals("#") )
			{
				if( type instanceof SequenceType )
				{
					addError(Error.INVALID_TYPE, "Cannot apply operator # to sequence type " + type);
					type = Type.UNKNOWN;
				}
				else
				{
					MethodSignature signature = setMethodType(node, type, "toString", new SequenceType() );
					node.addOperation(signature); //should never be null
					type = Type.STRING;
				}
			}
			else if( operator.equals("!") )
			{
				if( !type.equals(Type.BOOLEAN)) 
				{
					addError(Error.INVALID_TYPE, "Cannot apply operator ! to type " + type + " which is not boolean", type);
					type = Type.UNKNOWN;				
				}				
			}
			else
				pushUpModifiers( node );
			
	
			node.setType(type);
		}
		
		return WalkType.POST_CHILDREN;
	}
		
	/*
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
	*/
	
	
	public Object visit(ASTConditionalExpression node, Boolean secondVisit) throws ShadowException {
		if( !secondVisit )
			return WalkType.POST_CHILDREN;
		
		if(node.jjtGetNumChildren() == 1)
			pushUpType( node, secondVisit ); //propagate type and modifiers up
		else if(node.jjtGetNumChildren() == 3)
		{			
			Type t1 = node.jjtGetChild(0).getType();
			
			Node first = node.jjtGetChild(1); 
			Type t2 = first.getType();
			Node second = node.jjtGetChild(2);
			Type t3 = second.getType();
						
			if( first.getModifiers().isNullable() || second.getModifiers().isNullable() )
				node.addModifier(Modifiers.NULLABLE);
			
			if( first.getModifiers().isNullable() != second.getModifiers().isNullable() && (t2 instanceof ArrayType || t3 instanceof ArrayType )  )
				addError(node, Error.INVALID_MODIFIER, "Cannot mix nullable and non-nullable arrays", t2, t3);
			
			if( first.getModifiers().isReadonly() || second.getModifiers().isReadonly() )
				node.addModifier(Modifiers.READONLY);
			else if( first.getModifiers().isTemporaryReadonly() || second.getModifiers().isTemporaryReadonly() )
				node.addModifier(Modifiers.TEMPORARY_READONLY);			
			
			if( first.getModifiers().isImmutable() && second.getModifiers().isImmutable() )			
				node.addModifier(Modifiers.IMMUTABLE);
			else if( first.getModifiers().isImmutable() != second.getModifiers().isImmutable()  && !t1.getModifiers().isImmutable() && !t2.getModifiers().isImmutable() )
				addError(node, Error.INVALID_MODIFIER, "Expressions " + first + " and " + second + " have incompatible levels of mutability", t1, t2);
			
			if( !t1.equals(Type.BOOLEAN) ) 
			{			
				addError(node.jjtGetChild(0), Error.INVALID_TYPE, "Supplied type " + t1 + " cannot be used in the condition of a ternary operator, boolean type required", t1);
				node.setType(Type.UNKNOWN);
			}
			else if( t2.isSubtype(t3) )
				node.setType(t3);
			else if( t3.isSubtype(t2) )
				node.setType(t2);
			else 
			{
				addError(node, Error.MISMATCHED_TYPE, "Supplied type " + t2 + " must match " + t3 + " in execution of ternary operator", t2, t3);
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
					addError(node.jjtGetChild(i), Error.INVALID_TYPE, "Supplied type " + result + " cannot be used with a logical operator, boolean type required", result);			
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
					
					if( type instanceof ArrayType )
						addError(child, Error.INVALID_TYPE, "Array type cannot be used in a coalesce expression");
					
					if( result == null )
						result = type;				
					else if( result.isSubtype(type) )
						result = type;
					else if( !type.isSubtype(result) ) //neither is subtype of other, panic!
					{
						addError(Error.MISMATCHED_TYPE, "Supplied type " + type + " does not match type " + result + " in coalesce expression", type, result);
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
				
				if( result.hasUninstantiatedInterface(Type.INTEGER) )
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
						addError(Error.INVALID_TYPE, "Operator " + operator + " not defined on types " + result + " and " + current, result, current);
						node.setType(Type.UNKNOWN);	
						break;
					}				
				}
				else		
				{
					addError(Error.INVALID_TYPE, "Operator " + operator + " not defined on types " + result + " and " + current, result, current);
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
					Type type = resolveType( rightElement).getType();
					if( leftElement.getModifiers().isNullable() && type instanceof ArrayType ) {
						ArrayType arrayType = (ArrayType) type;
						type = arrayType.convertToNullable();
					}
					
					leftElement.setType(type);
				}
							
				if( leftElement instanceof ASTSequenceVariable ) //declaration	
				{
					if( leftElement.getModifiers().isNullable() ) {					
						/*if( leftElement.getType().isPrimitive() )							
							addError(Error.INVALID_MODIFIER, "Primitive type " + leftElement.getType() + " cannot be marked nullable");
						else*/ if( leftElement.getType() instanceof ArrayType && ((ArrayType)leftElement.getType()).recursivelyGetBaseType().isPrimitive() )
							addError(Error.INVALID_MODIFIER, "Primitive array type " + leftElement.getType() + " cannot be marked nullable");
					}					
					
					addErrors(isValidInitialization(leftElement, rightElement));
				}
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
				{
					PropertyType getSetType = (PropertyType) leftType;
					//here we have a chance to tell the type whether it will only be storing or doing both
					if( assignment.getAssignmentType() == AssignmentType.EQUAL )
						getSetType.setStoreOnly();
					else
						getSetType.setLoadStore();					
					
					leftType = getSetType.getSetType().getType();
				}
				
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
				addError(Error.MISMATCHED_TYPE, "Supplied type " + t1 + " cannot be compared with type " + t2 + " in an is statement", t1, t2);
				node.setType(Type.UNKNOWN);
			}
			
			if( t1 instanceof SingletonType || t2 instanceof SingletonType )
				addError(Error.MISMATCHED_TYPE, "Cannot use singleton types in an is statement");
			else
			{			
				if( node.jjtGetChild(0).getModifiers().isTypeName() )
					addError(Error.NOT_OBJECT, "Left hand side of is statement cannot be a type name");
			
				if( !node.jjtGetChild(1).getModifiers().isTypeName() )
					addError(Error.NOT_TYPE, "Right hand side of is statement must be a type name");
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
			TypeParameter typeParameter = new TypeParameter(symbol, declarationType);
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
			node.addModifier(Modifiers.TYPE_NAME);
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
	
	public Object visit(ASTInlineResults node, Boolean secondVisit) throws ShadowException {
		if(secondVisit)
			for( int i = 0; i < node.jjtGetNumChildren(); i++ )
				node.addType(node.jjtGetChild(i));
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTCastExpression node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;		
		
		if( node.jjtGetNumChildren() == 1 )
			pushUpType(node, secondVisit);
		else {
			Type t1 = node.jjtGetChild(0).getType();  //type
			Type t2 = node.jjtGetChild(1).getType();  //expression
			
			node.setModifiers(node.jjtGetChild(1).getModifiers());
			node.removeModifier(Modifiers.ASSIGNABLE);			
			
			//special case because there is no way to cast to nullable Object[]
			if( t1 instanceof ArrayType && t2.getTypeWithoutTypeArguments().equals(Type.ARRAY_NULLABLE) ) {
				t1 = ((ArrayType)t1).convertToNullable();
				node.addModifier(Modifiers.NULLABLE);
			}
			
			if( t1 instanceof MethodType && t2 instanceof UnboundMethodType ) { //casting methods
				MethodType method = (MethodType)t1;
				UnboundMethodType unboundMethod = (UnboundMethodType)t2;				
				
							
				MethodSignature candidate = null;
				Type outer = unboundMethod.getOuter();			
				
				for( MethodSignature signature : outer.getAllMethods(unboundMethod.getTypeName()) ) {				
					MethodType methodType = signature.getMethodType();			
					//no type arguments for method pointers?
					/*
					if( methodType.isParameterized() )
					{
						if( hasTypeArguments )
						{	
							SequenceType parameters = methodType.getTypeParameters();							
							if( parameters.canAccept(typeArguments, SubstitutionKind.TYPE_PARAMETER))
							{
								methodType = methodType.replace(parameters, typeArguments);
								signature = signature.replace(parameters, typeArguments);
							}
							else
								continue;
						}
					}
					*/				
					
					//the list of method signatures starts with the closest (current class) and then adds parents and outer classes
					//always stick with the current if you can
					//(only replace if signature is a subtype of candidate but candidate is not a subtype of signature)					
					if( methodType.isSubtype(method)) {	
						if( candidate == null || (candidate.getMethodType().isSubtype(methodType) )) //take the broadest method possible that matches the cast target
							candidate = signature;
						else if( !methodType.isSubtype(candidate.getMethodType()) ) { //then two acceptable signatures are not subtypes of each other					
							addError(Error.INVALID_ARGUMENTS, "Ambiguous cast from " + unboundMethod.getTypeName() + " to " + method);
							break;
						}				
					}			
				}			
			
				if( candidate == null )			
					addError(Error.INVALID_METHOD, "No definition of " + unboundMethod.getTypeName() + " matches type " + method);
				else
					node.setType(candidate.getMethodType());
			}			
			else if( t1.isNumerical() && t2.isNumerical() ) //some numerical types (int and uint) are not superclasses or subclasses of each other
															//for convenience, all numerical types should be castable
				node.setType(t1);
			else if( t1.isSubtype(t2) || t2.isSubtype(t1) )
				node.setType(t1);			
			else {
				addError(Error.MISMATCHED_TYPE, "Supplied type " + t2 + " cannot be cast to type " + t1, t1, t2);
				node.setType(Type.UNKNOWN);
			}
		}			

		return WalkType.POST_CHILDREN;
	}
	

	public Object visit(ASTRightSide node, Boolean secondVisit) throws ShadowException 
	{
		return pushUpType(node, secondVisit);		
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
			addError(Error.INVALID_TYPE, "Condition of if statement cannot accept non-boolean type " + t, t);
				
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTWhileStatement node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		Type t = node.jjtGetChild(0).getType(); 
		
		if( !t.equals( Type.BOOLEAN ) )
			addError(Error.INVALID_TYPE, "Condition of while statement cannot accept non-boolean type " + t, t);
				
		return WalkType.POST_CHILDREN;
	}

	public Object visit(ASTDoStatement node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		Type t = node.jjtGetChild(1).getType(); //second child, not first like if and while 
		
		if( !t.equals( Type.BOOLEAN ) )
			addError(Error.INVALID_TYPE, "Condition of do statement cannot accept non-boolean type " + t, t);
				
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
		boolean iterable = true;
		
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
		
		node.setCollectionType(collectionType);		
						
		if( collectionType instanceof ArrayType )
		{
			ArrayType array = (ArrayType)collectionType;			
			element = new SimpleModifiedType( array.getBaseType() );
		}
		else if( collectionType.hasUninstantiatedInterface(Type.CAN_ITERATE)  )
		{			
			for(InterfaceType _interface : collectionType.getAllInterfaces() )				
				if( _interface.getTypeWithoutTypeArguments().equals(Type.CAN_ITERATE))
				{
					element = _interface.getTypeParameters().get(0);
					break;
				}
		}
		else if( collectionType.hasUninstantiatedInterface(Type.CAN_ITERATE_NULLABLE)  )
		{			
			for(InterfaceType _interface : collectionType.getAllInterfaces() )				
				if( _interface.getTypeWithoutTypeArguments().equals(Type.CAN_ITERATE_NULLABLE))
				{
					element = _interface.getTypeParameters().get(0);
					break;
				}
		}
		else
		{
			addError(Error.INVALID_TYPE, "Supplied type " + collectionType + " does not implement " + Type.CAN_ITERATE + " or " + Type.CAN_ITERATE_NULLABLE + " and cannot be the target of a foreach statement", collectionType);
			iterable = false;
		}		
		
		if( iterable )
		{
			if( isVar && element != null && element.getType() != null ) {
				Type elementType = element.getType();				
				node.setType(elementType);
				if( node.getModifiers().isNullable() ) {
					/*if( elementType.isPrimitive() )
						addError(Error.INVALID_MODIFIER, "Primitive type " + elementType + " cannot be marked nullable");
					else*/ if( elementType instanceof ArrayType ) {
						ArrayType arrayType = (ArrayType) elementType;
						if( arrayType.recursivelyGetBaseType().isPrimitive() )
							addError(Error.INVALID_MODIFIER, "Primitive array type " + elementType + " cannot be marked nullable");
					}
				}
			}
					
			List<TypeCheckException> errors = isValidInitialization( node, element);
			if( errors.isEmpty() )
				addSymbol( node.getImage(), node );
			else
				addErrors(errors );
		}
				
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
			
			if(conditionalType == null || !conditionalType.equals( Type.BOOLEAN ) )
				addError(Error.INVALID_TYPE, "Condition of for statement cannot accept non-boolean type " + conditionalType, conditionalType);
		}
			
		return WalkType.POST_CHILDREN;
	}	
	
	@Override
	public Object visit(ASTThrowStatement node, Boolean secondVisit) throws ShadowException
	{
		if( secondVisit )
		{
			Node child = node.jjtGetChild(0);
			if( !(child.getType() instanceof ExceptionType) )
				addError(Error.INVALID_TYPE, "Supplied type " + child.getType() + " cannot be used in a throw clause, exception or error type required", child.getType());
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
						addError( child, Error.UNREACHABLE_CODE, "Catch block for exception " + type + " cannot be reached", type );
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
			{
				addError( child, Error.INVALID_TYPE, "Supplied type " + type + " cannot be used as a catch parameter, exception types required", type);
				node.setType(Type.UNKNOWN);				
			}
			else
				node.setType(type);
			
					
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
	
	public Object visit(ASTCopyExpression node, Boolean secondVisit) throws ShadowException 
	{
		if( secondVisit )
		{
			ModifiedType child = node.jjtGetChild(0);
			child = resolveType( child );
			node.setModifiers(child.getModifiers());			
			if( node.getImage().equals("freeze"))
				node.addModifier(Modifiers.IMMUTABLE);
			node.setType(child.getType());
		}	
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTCheckExpression node, Boolean secondVisit) throws ShadowException 
	{
		if( secondVisit )
		{
			ModifiedType child = node.jjtGetChild(0);
			child = resolveType( child );
			Type type = child.getType();
			
			/*
			if( type instanceof ArrayType && type.getModifiers().isNullable() ) {
				ArrayType arrayType = (ArrayType) type;
				type = new ArrayType(arrayType.getBaseType(), arrayType.getDimensions());
			}			
			else*/ if( child.getModifiers().isNullable() ) {
				node.setModifiers(child.getModifiers());
				node.removeModifier(Modifiers.NULLABLE);
			}
			else
				addError(Error.INVALID_TYPE, "Non-nullable expression cannot be used in a check statement");
			
			node.setType(type);
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
			
			node.setRecover(found);
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
			node.setSuffix(last);
						
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
			node.setSuffix(child);
			node.setType(child.getType());
			pushUpModifiers( node );
		}		
		
		curPrefix.removeFirst();  //pop prefix type off stack
		
		return WalkType.POST_CHILDREN;
	}
	
	private void checkForSingleton(Type type) {
		//if singleton, add to current method for initialization
		if( type instanceof SingletonType ) {
			SingletonType singletonType = (SingletonType)type;
			if( currentMethod.isEmpty() ) {
				//add to all creates (since it must be declared outside a method)
				for( MethodSignature signature : currentType.getAllMethods("create"))				
					signature.getNode().addSingleton(singletonType);
			}
			else {
				int i = 0;
				SignatureNode signatureNode;
				//find outer method, instead of adding to local or inline methods
				do {
					signatureNode = currentMethod.get(i);
					i++;
				} while( signatureNode instanceof ASTInlineMethodDefinition || signatureNode instanceof ASTLocalMethodDeclaration );
				
				signatureNode.addSingleton(singletonType);				
			}
		}
		else if( type instanceof SequenceType ) {
			SequenceType sequenceType = (SequenceType) type;
			for(ModifiedType modifiedType : sequenceType )
				checkForSingleton(modifiedType.getType());
		}
		else if( type instanceof ArrayType )		
			checkForSingleton(((ArrayType)type).getBaseType());
	}
	
	public Object visit(ASTPrimaryPrefix node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;	
		
		int children = node.jjtGetNumChildren();
		String image = node.getImage();
		
		if(  children == 0 ) {
			if( image.equals("this") || image.equals("super") ) { //this or super				
				if( currentType instanceof InterfaceType ) {
					addError(Error.INVALID_SELF_REFERENCE, "Reference " + image + " invalid for interfaces");
					node.setType(Type.UNKNOWN);
				}
				else {
					if( image.equals("this") )					
						node.setType(currentType);
					else											
						node.setType(((ClassType) currentType).getExtendType());
					
					if( currentType.getModifiers().isImmutable() )
						node.getModifiers().addModifier(Modifiers.IMMUTABLE);
					else if( currentType.getModifiers().isReadonly() )
						node.getModifiers().addModifier(Modifiers.READONLY);
					
					Modifiers methodModifiers = Modifiers.NO_MODIFIERS;
					if(!currentMethod.isEmpty() )					
						methodModifiers = currentMethod.getFirst().getModifiers();
					
					//upgrade if current method is non-mutable
					if( methodModifiers.isImmutable() || methodModifiers.isReadonly() )
						node.getModifiers().upgradeToTemporaryReadonly();					
				}
			}	
			else { //just a name				
				if( !setTypeFromName( node, image )) { //automatically sets type if can				
					addError(Error.UNDEFINED_SYMBOL, "Symbol " + image + " not defined in this context");
					node.setType(Type.UNKNOWN);											
				}
			}
		}
		else if( children == 1 ) {
			Node child = node.jjtGetChild(0); 
			
			if( child instanceof ASTUnqualifiedName ) {
				node.setImage(child.getImage() + "@" + node.getImage());				
				if( !setTypeFromName( node, node.getImage() )) { //automatically sets type if can				
					addError(Error.UNDEFINED_TYPE, "Type " + image + " not defined in this context");
					node.setType(Type.UNKNOWN);											
				}
			}
			else {
				node.setType( child.getType() ); 	//literal, check expression, copy expression, cast expression, (conditional expression), primitive and function types, and array initializer
				pushUpModifiers( node ); 			
			}
		}
		
		checkForSingleton(node.getType());		
		curPrefix.set(0, node); //so that the suffix can figure out where it's at
				
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTClassOrInterfaceTypeSuffix node, Boolean secondVisit) throws ShadowException {	
		return WalkType.PRE_CHILDREN;		
	}
	
	public Object visit(ASTTypeArguments node, Boolean secondVisit) throws ShadowException {
		if( secondVisit )			
			for( int i = 0; i < node.jjtGetNumChildren(); i++ )
				node.addType(node.jjtGetChild(i));
		
		return WalkType.POST_CHILDREN;
	}
	
	
	public Object visit(ASTPrimarySuffix node, Boolean secondVisit) throws ShadowException {
		/*
		  QualifiedKeyword()
		| Brackets()
		| Subscript()
		| Destroy()
		| Method() //method name, not the call
		| Allocation()
		| ScopeSpecifier()
		| Property()
		| MethodCall()
		*/
		
		if(secondVisit) {
			Node prefixNode = curPrefix.getFirst();		
			
			if( prefixNode.getModifiers().isNullable() && !(prefixNode.getType() instanceof ArrayType) )
				addError(Error.INVALID_DEREFERENCE, "Nullable reference cannot be dereferenced");
			
			Node child = node.jjtGetChild(0);
			if( (child instanceof ASTProperty) || (child instanceof ASTMethodCall) ||  (child instanceof ASTAllocation)) {
				ASTPrimaryExpression parent = (ASTPrimaryExpression) node.jjtGetParent();
				parent.setAction(true);
			}	
			
			if( child instanceof ASTQualifiedKeyword )
				node.setImage(child.getImage()); //"this", "super"
			
			if( child instanceof ASTScopeSpecifier && child.getImage().equals("class"))
				node.setImage(child.getImage()); //"class"
			
			if( child instanceof ASTMethodCall ) {
				Type childType = child.getType();
							
				if( childType instanceof MethodType ) {
					MethodType methodType = (MethodType) childType;
					SequenceType returnTypes = methodType.getReturnTypes();
					returnTypes.setNodeType( node ); //used instead of setType
				}
				else
					node.setType(Type.UNKNOWN);
			}				
			else
				pushUpType(node, secondVisit);
			
			checkForSingleton(node.getType());
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
					if( prefixType.encloses( currentType )  )
					{
						node.setType(prefixType);						
						if( prefixType.getModifiers().isImmutable() )
							node.getModifiers().addModifier(Modifiers.IMMUTABLE);
						else if( prefixType.getModifiers().isReadonly() )
							node.getModifiers().addModifier(Modifiers.READONLY);
					}
					else				
						addError(Error.INVALID_SELF_REFERENCE, "Prefix of :" + node.getImage() + " is not the current class or an enclosing class");
				}
				else if( kind.equals("super") )
				{
					if( currentType.encloses( prefixType )  )
					{						
						if( (prefixType instanceof ClassType) && ((ClassType)prefixType).getExtendType() != null )
						{
							node.setType(((ClassType)prefixType).getExtendType());
							if( prefixType.getModifiers().isImmutable() )
								node.getModifiers().addModifier(Modifiers.IMMUTABLE);
							else if( prefixType.getModifiers().isReadonly() )
								node.getModifiers().addModifier(Modifiers.READONLY);
						}
						else
							addError(Error.INVALID_SELF_REFERENCE, "Type " + prefixType + " does not have a super class", prefixType);
					}
					else				
						addError(Error.INVALID_SELF_REFERENCE, "Prefix of qualified super is not the current class or an enclosing class");
				}			
				
				Modifiers methodModifiers = null;
				if(!currentMethod.isEmpty() )
					methodModifiers = currentMethod.getFirst().getModifiers();
				
				//in this case, depends only on current method (creates are exempt)
				if( methodModifiers != null && (methodModifiers.isImmutable() || methodModifiers.isReadonly()) )
					node.getModifiers().upgradeToTemporaryReadonly();
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
				node.setType(new ArrayType( prefixType, node.getArrayDimensions(), false) );
				node.addModifier(Modifiers.TYPE_NAME);
			}
			else
			{				
				addError(Error.NOT_TYPE, "Can only apply brackets to a type name");
				node.setType(Type.UNKNOWN);
			}
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTSubscript node, Boolean secondVisit) throws ShadowException {
		if( secondVisit ) {			
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
						addError(node.jjtGetChild(i), Error.INVALID_SUBSCRIPT, "Subscript type " + childType + " is invalid, integral type required", childType);
						node.setType(Type.UNKNOWN);
						return WalkType.POST_CHILDREN;
					}
				}
				
			    //only goes one level deep
				int dimension = node.getArrayDimensions().get(0);				
				if( dimension == arrayType.getDimensions() )
				{
					node.setType( arrayType.getBaseType() );
					if( prefixNode.getModifiers().isImmutable() )
						node.addModifier(Modifiers.IMMUTABLE);
					else if( prefixNode.getModifiers().isReadonly() )
						node.addModifier(Modifiers.READONLY);
					else if( prefixNode.getModifiers().isTemporaryReadonly() )
						node.addModifier(Modifiers.TEMPORARY_READONLY);
					else
						node.addModifier(Modifiers.ASSIGNABLE);
					
					//backdoor for creates
					//immutable and readonly array should only be assignable in creates
					if( prefixNode.getModifiers().isAssignable() ) 
						node.addModifier(Modifiers.ASSIGNABLE);
					
					//primitive arrays are initialized to default values
					//non-primitive array elements could be null
					//however, arrays of arrays are not-null
					//instead, they will be filled with default values, including a length of zero
					//thus, we don't need to check nullability, but we do need a range check				
					
					//nullable array means what you get out is nullable, not the array itself
					if( arrayType.isNullable() ) 
						node.addModifier(Modifiers.NULLABLE);
				}				
				else
				{
					node.setType(Type.UNKNOWN);
					addError(Error.INVALID_SUBSCRIPT, "Subscript gives " + node.jjtGetNumChildren() + " indexes but "  + arrayType.getDimensions() + " are required");
				}				
			}						
			else if( prefixType.hasUninstantiatedInterface(Type.CAN_INDEX) || prefixType.hasUninstantiatedInterface(Type.CAN_INDEX_NULLABLE) ||
					 prefixType.hasUninstantiatedInterface(Type.CAN_INDEX_STORE) || prefixType.hasUninstantiatedInterface(Type.CAN_INDEX_STORE_NULLABLE)) {
				if( node.jjtGetNumChildren() == 1)
				{
					SequenceType arguments = new SequenceType();
					Node child = node.jjtGetChild(0);
					arguments.add(child);
					
					MethodSignature signature = setMethodType( node, prefixType, "index", arguments);
					
					if( signature != null && (prefixNode.getModifiers().isReadonly() || prefixNode.getModifiers().isTemporaryReadonly() || prefixNode.getModifiers().isImmutable()) && signature.getModifiers().isMutable() )
					{
						node.setType(Type.UNKNOWN);
						addError(Error.INVALID_SUBSCRIPT, "Cannot apply mutable subscript to immutable or readonly prefix");						
					}
					else
					{
						//if signature is null, then it is not a load
						SubscriptType subscriptType = new SubscriptType(signature, child, new UnboundMethodType("index", prefixType), prefixNode, currentType);
						node.setType(subscriptType);
						if( signature != null )
							node.setModifiers(subscriptType.getGetType().getModifiers());								
						
						if( prefixNode.getModifiers().isImmutable() )
							node.addModifier(Modifiers.IMMUTABLE);
						else if( prefixNode.getModifiers().isReadonly() )
							node.addModifier(Modifiers.READONLY);
						else if( prefixNode.getModifiers().isTemporaryReadonly() )
							node.addModifier(Modifiers.TEMPORARY_READONLY);
						else if( prefixType.hasUninstantiatedInterface(Type.CAN_INDEX_STORE) || prefixType.hasUninstantiatedInterface(Type.CAN_INDEX_STORE_NULLABLE)  ) 
							node.addModifier(Modifiers.ASSIGNABLE);
					}											
				}
				else
				{
					node.setType(Type.UNKNOWN);
					addError(Error.INVALID_SUBSCRIPT, "Subscript supplies multiple indexes into non-array type " + prefixType, prefixType);
				}				
			}			
			else
			{
				node.setType(Type.UNKNOWN);
				addError(Error.INVALID_SUBSCRIPT, "Subscript is not permitted for type " + prefixType + " because it does not implement " + Type.CAN_INDEX + " or " + Type.CAN_INDEX_NULLABLE, prefixType);
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
	
	public Object visit(ASTArrayCreate node, Boolean secondVisit) throws ShadowException  {
		if( secondVisit ) {			
			Node prefixNode = curPrefix.getFirst();
			Type prefixType = prefixNode.getType();			
			SequenceType typeArguments = null;
			int start = 0;
			boolean nullable = false;
			
			if( node.jjtGetChild(0) instanceof ASTTypeArguments ) {
				typeArguments = ((ASTTypeArguments) node.jjtGetChild(0)).getType();
				start = 1;				
			}
			
			ASTArrayDimensions dimensions = (ASTArrayDimensions) node.jjtGetChild(start);
			start++;
			
			if( node.getImage().equals("null")  ) {
				nullable = true;
				node.addModifier(Modifiers.NULLABLE);
				
				if( prefixType instanceof ArrayType ) {
					ArrayType arrayType = (ArrayType) prefixType;
					prefixType = arrayType.convertToNullable();
				}
			}
			
			//create array
			if( prefixNode.getModifiers().isTypeName() ) {
				ArrayType arrayType = null;
				if( typeArguments != null  ) {
					if( prefixType.isParameterized() ) {
						if( prefixType.getTypeParameters().canAccept(typeArguments, SubstitutionKind.TYPE_PARAMETER) ) {					
							try {
								prefixType = prefixType.replace(prefixType.getTypeParameters(), typeArguments);
								arrayType = new ArrayType( prefixType, dimensions.getArrayDimensions(), nullable );
								node.setType( arrayType );
							} 
							catch (InstantiationException e) {
								addError(Error.INVALID_TYPE_ARGUMENTS, "Supplied type arguments " + typeArguments + " do not match type parameters " + prefixType.getTypeParameters() );
								node.setType(Type.UNKNOWN);
							}							
						}
						else {
							addError(Error.INVALID_TYPE_ARGUMENTS, "Supplied type arguments " + typeArguments + " do not match type parameters " + prefixType.getTypeParameters() );
							node.setType(Type.UNKNOWN);
						}						
					}
					else {
						addError(Error.UNNECESSARY_TYPE_ARGUMENTS, "Type arguments " + typeArguments + " supplied for non-parameterized type " + prefixType, prefixType);						
						node.setType(Type.UNKNOWN);
					}					
				}
				else {
					if( !prefixType.isParameterized() ) {							
						arrayType = new ArrayType( prefixType, dimensions.getArrayDimensions(), nullable ); 
						node.setType( arrayType );
					}
					else {
						addError(Error.MISSING_TYPE_ARGUMENTS, "Type arguments not supplied for paramterized type " + prefixType);
						node.setType(Type.UNKNOWN);	
					}					
				}
								
				if( node.hasCreate() ) {
					SequenceType arguments = new SequenceType();
					for( int i = start; i < node.jjtGetNumChildren(); ++i )
						arguments.add(node.jjtGetChild(i));
					
					MethodSignature signature = prefixType.getMatchingMethod("create", arguments, typeArguments);					
					if( signature == null || !methodIsAccessible( signature, currentType) )
						addError(node, Error.INVALID_CREATE, "Cannot create array of type " + prefixType + " with specified create arguments", prefixType);
					else if( prefixType instanceof InterfaceType )
						addError(node, Error.INVALID_CREATE, "Cannot create non-nullable array of interface type " + prefixType, prefixType);
					else
						node.setMethodSignature(signature);					
				}
				else if( node.hasDefault() ) {
					Node child = node.jjtGetChild(start);				
					Type baseType = arrayType.getBaseType();
					if( child.getType().isSubtype(baseType) ) {
									
						if( child.getModifiers().isNullable() && !node.getModifiers().isNullable() )
							addError(child, Error.INVALID_MODIFIER, "Cannot apply nullable default value to non-nullable array", child.getType(), baseType);					
					}
					else
						addError(node, Error.INVALID_TYPE, "Cannot apply type " + child.getType() + " as default value in array of type " + arrayType, child.getType());						
				}
				else if( !node.getImage().equals("null") && !prefixType.isPrimitive() && !(prefixType instanceof ArrayType) ) {
					//try default constructor
					MethodSignature signature = prefixType.getMatchingMethod("create", new SequenceType(), typeArguments);					
					if( signature == null || !methodIsAccessible( signature, currentType) )
						addError(node, Error.INVALID_CREATE, "Cannot create array of type " + prefixType + " which does not implement a default create", prefixType);
					else if( prefixType instanceof InterfaceType )
						addError(node, Error.INVALID_CREATE, "Cannot create non-nullable array of interface type " + prefixType, prefixType);
					else
						node.setMethodSignature(signature);
				}
			}
			else {				
				addError(Error.NOT_TYPE, "Type name must be used to create an array");
				node.setType(Type.UNKNOWN);
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
			
			if( prefixNode.getModifiers().isTypeName() && !(prefixType instanceof SingletonType) )
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
			
			//these push the immutable or readonly modifier to the prefix of the call
			if( prefixNode.getModifiers().isImmutable() )
				node.addModifier(Modifiers.IMMUTABLE);			
			else if( prefixNode.getModifiers().isReadonly() )
				node.addModifier(Modifiers.READONLY);
			else if( prefixNode.getModifiers().isTemporaryReadonly() )
				node.addModifier(Modifiers.TEMPORARY_READONLY);				
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
	public Object visit(ASTCreate node, Boolean secondVisit) throws ShadowException {
		if( secondVisit ) {
			Node prefixNode = curPrefix.getFirst();
			Type prefixType = prefixNode.getType();
			node.setType(Type.UNKNOWN);
			Node parent = node.jjtGetParent();
			
			if( prefixType instanceof InterfaceType )			
				addError(Error.INVALID_CREATE, "Interfaces cannot be created");						
			else if( prefixType instanceof SingletonType )			
				addError(Error.INVALID_CREATE, "Singletons cannot be created");
			else if(!( prefixType instanceof ClassType) && !(prefixType instanceof TypeParameter && !prefixType.getAllMethods("create").isEmpty()))
				addError(Error.INVALID_CREATE, "Type " + prefixType + " cannot be created", prefixType);				
			else if( !prefixNode.getModifiers().isTypeName() )				
				addError(Error.INVALID_CREATE, "Only a type can be created");
			else {
				SequenceType typeArguments = null;
						
				int start = 0;
				if( node.jjtGetNumChildren() > 0 && (node.jjtGetChild(0) instanceof ASTTypeArguments) ) {
					typeArguments = ((ASTTypeArguments) node.jjtGetChild(0)).getType();
					start = 1;
				}
				
				SequenceType arguments = new SequenceType();
				
				for( int i = start; i < node.jjtGetNumChildren(); i++ )
					arguments.add(node.jjtGetChild(i));

				MethodSignature signature;				
				
				if( typeArguments != null ) {					
					if( prefixType.isParameterized() && prefixType.getTypeParameters().canAccept(typeArguments, SubstitutionKind.TYPE_PARAMETER) ) {
						try {
							prefixType = prefixType.replace(prefixType.getTypeParameters(), typeArguments);
							signature = setCreateType( node, prefixType, arguments);
							node.setMethodSignature(signature);
							parent.setType(prefixType);
							
							//occasionally this type is only seen here and must be added to the referenced types
							currentType.addReferencedType(prefixType);
						}
						catch (InstantiationException e) {
							addError(Error.INVALID_TYPE_ARGUMENTS, "Supplied type arguments " + typeArguments + " do match type parameters " + prefixType.getTypeParameters());					
						}
					}
					else
						addError(Error.INVALID_TYPE_ARGUMENTS, "Supplied type arguments " + typeArguments + " do match type parameters " + prefixType.getTypeParameters());										
				}
				else {
					if( !prefixType.isParameterized() ) {
						signature = setCreateType( node, prefixType, arguments);
						node.setMethodSignature(signature);
						parent.setType(prefixType);
						//TODO: see if something similar can be done for ASTMethodCall
					}
					else
						addError(Error.MISSING_TYPE_ARGUMENTS, "Type arguments not supplied for parameterized type " + prefixType);
				}
			}
			
			if( node.getType() == Type.UNKNOWN )
				parent.setType(Type.UNKNOWN);
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
			node.setPrefixType(prefixType); //mostly useful for constants
			String name = node.getImage();
			boolean isTypeName = prefixNode.getModifiers().isTypeName();
			
			if( name.equals("class") )
			{	
				if( isTypeName )
				{
					if( node.jjtGetNumChildren() > 0 ) //has type arguments
					{							
						ASTTypeArguments arguments = (ASTTypeArguments) node.jjtGetChild(0);
						SequenceType parameterTypes = prefixType.getTypeParameters();
						SequenceType argumentTypes = arguments.getType();
						
						if( !prefixType.isParameterized() )
							addError( Error.UNNECESSARY_TYPE_ARGUMENTS, "Type arguments " + argumentTypes + " supplied for non-parameterized type " + prefixType, prefixType);
						else if( !parameterTypes.canAccept(argumentTypes, SubstitutionKind.TYPE_PARAMETER) )
							addError( Error.INVALID_TYPE_ARGUMENTS, "Supplied type arguments " + argumentTypes + " do not match type parameters " + parameterTypes );
						else		
						{
							try
							{
								((ClassType)currentType).addReferencedType(prefixType.replace(parameterTypes, argumentTypes));
							}
							catch(InstantiationException e)
							{
								addError( Error.INVALID_TYPE_ARGUMENTS, "Supplied type arguments " + argumentTypes + " do not match type parameters " + parameterTypes );
							}
						}
					}
					//allow "raw" classes
					//else if( prefixType.isParameterized() )
						//addError( Error.MISSING_TYPE_ARGUMENTS, "Type arguments not supplied for parameterized type " + prefixType, prefixType);
				}
				else
					addError( Error.NOT_TYPE, "Constant class requires type name for access");
				
				node.setType( Type.CLASS );
				node.addModifier(Modifiers.IMMUTABLE);	
			}			
			else if( prefixType.containsField( name ) )
			{
				Node field = prefixType.getField(name);
				
				if( node.jjtGetNumChildren() > 0 ) //has type arguments
				{
					addError(Error.INVALID_TYPE_ARGUMENTS, "Type arguments should not be used for field access");
				}
				else if( !fieldIsAccessible( field, currentType ))
				{
					addError(Error.ILLEGAL_ACCESS, "Field " + name + " not accessible from this context");
				}					
				else if( field.getModifiers().isConstant() && !isTypeName )
				{
					addError(Error.ILLEGAL_ACCESS, "Constant " + name + " requires type name for access");
				}
				else if( !field.getModifiers().isConstant() && isTypeName )
				{
					addError(Error.ILLEGAL_ACCESS, "Field " + name + " is only accessible from an object reference");
				}
				else
				{
					node.setType(field.getType());
					node.setModifiers(field.getModifiers());
															
					if( !prefixNode.getModifiers().isMutable() )					
						node.getModifiers().upgradeToTemporaryReadonly();
					
					//only in a create if
					//prefix is "this"
					//current method is create
					//current method is inside prefix type
					boolean insideCreate = false;
					
					if( currentMethod.isEmpty() )
						insideCreate = true; //declaration
					else
					{
						MethodSignature signature = currentMethod.getFirst().getMethodSignature();												
						insideCreate = curPrefix.getFirst().getImage().equals("this") && signature.isCreate() && signature.getOuter().equals(prefixType);   
					}
										
					if( node.getModifiers().isMutable() || insideCreate )
						node.addModifier(Modifiers.ASSIGNABLE);					
				}
			}
			else if( prefixType instanceof ClassType && ((ClassType)prefixType).containsInnerClass(name) )
			{
				ClassType classType = (ClassType) prefixType;
				ClassType innerClass = classType.getInnerClass(name);
				
				if( !classIsAccessible( innerClass, currentType ) )
					addError(Error.ILLEGAL_ACCESS, "Class " + innerClass + " is not accessible from this context", innerClass);
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
			boolean isTypeName = prefixNode.getModifiers().isTypeName() && !(prefixType instanceof SingletonType);
						
			if( isTypeName )
			{
				addError(Error.NOT_OBJECT, "Object reference must be used to access property " + propertyName);
				node.setType( Type.UNKNOWN ); //if got here, some error
			}	
			else
			{				
				List<MethodSignature> methods = prefixType.getAllMethods(propertyName);	
				if( prefixNode.getModifiers().isImmutable() )
					node.addModifier(Modifiers.IMMUTABLE);
				else if( prefixNode.getModifiers().isReadonly() )
					node.addModifier(Modifiers.READONLY);
				else if( prefixNode.getModifiers().isTemporaryReadonly() )
					node.addModifier(Modifiers.TEMPORARY_READONLY);				

				if( methods != null && methods.size() > 0 )
				{
					MethodSignature getter = null;
					UnboundMethodType setterName = null;
					
					for( MethodSignature signature : methods )
					{
						if( signature.getModifiers().isGet() )
							getter = signature;
						else if( setterName == null && signature.getModifiers().isSet() )
							setterName = new UnboundMethodType( signature.getSymbol(), signature.getOuter());
					}					
					
					boolean mutableProblem = false;
					boolean accessibleProblem = false;
					
					if( getter != null && !methodIsAccessible(getter, currentType)) {
						accessibleProblem = true;
						getter = null;
					}
					
					if( getter != null && !prefixNode.getModifiers().isMutable() && getter.getModifiers().isMutable()  ) {
						mutableProblem = true;
						getter = null;
					}
				
					if( setterName == null  ) {
						node.setType( Type.UNKNOWN ); //assume bad						
						if( mutableProblem  )
							addError(node, Error.ILLEGAL_ACCESS, "Mutable property " + propertyName + " cannot be called from " + (prefixNode.getModifiers().isImmutable() ? "immutable" : "readonly") + " context");
						else if( accessibleProblem )
							addError(node, Error.ILLEGAL_ACCESS, "Property " + propertyName + " is not accessible in this context");
						else if( getter == null )
							addError(node, Error.INVALID_PROPERTY, "Property " + propertyName + " is not defined in this context");
						else //only case where it works out
							node.setType( new PropertyType( getter, setterName, prefixNode, currentType) );
					}
					else
						node.setType( new PropertyType( getter, setterName, prefixNode, currentType) );
				}
				else {
					addError(node, Error.INVALID_PROPERTY, "Property " + propertyName + " not defined in this context");
					node.setType( Type.UNKNOWN ); //if got here, some error
				}				
			}
			
		}
	
		return WalkType.POST_CHILDREN;
	}
	
	private ModifiedType resolveType( ModifiedType node ) { //dereferences into PropertyType or IndexType for getter, if needed	
		Type type = node.getType();		
		if( type instanceof PropertyType ) { //includes SubscriptType as well		
			PropertyType getSetType = (PropertyType) type;
			if( getSetType.isGettable() )						
				return getSetType.getGetType();			
			else {
				String kind = (type instanceof SubscriptType) ? "Subscript " : "Property ";
				addError(Error.ILLEGAL_ACCESS, kind + node + " does not have appropriate get access");
				return new SimpleModifiedType( Type.UNKNOWN );
			}				
		}
		else
			return node;
	}
	
	protected MethodSignature setCreateType( SignatureNode node, Type prefixType, SequenceType arguments)
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
				typeArguments = ((ASTTypeArguments) node.jjtGetChild(0)).getType();
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
				
				if( signature != null &&  prefixNode.getModifiers().isImmutable() && signature.getModifiers().isMutable()  )
					addError(Error.ILLEGAL_ACCESS, "Mutable method " + signature + " cannot be called from an immutable reference");
				
				if( signature != null &&  (prefixNode.getModifiers().isReadonly() || prefixNode.getModifiers().isTemporaryReadonly()) && signature.getModifiers().isMutable() )
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
						if( parameters.canAccept(typeArguments, SubstitutionKind.TYPE_PARAMETER))
						{
							try
							{
								methodType = methodType.replace(parameters, typeArguments);
							}
							catch(InstantiationException e)
							{
								addError(Error.INVALID_TYPE_ARGUMENTS, "Supplied type arguments " + typeArguments + " do not match method type arguments " + parameters, parameters, typeArguments);
								node.setType(Type.UNKNOWN);
							}
						}
						else
						{
							addError(Error.INVALID_TYPE_ARGUMENTS, "Supplied type arguments " + typeArguments + " do not match method type arguments " + parameters, parameters, typeArguments);
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
					addError(Error.INVALID_ARGUMENTS, "Supplied method arguments " + arguments + " do not match method parameters " + methodType.getParameterTypes(), arguments, methodType.getParameterTypes());
					node.setType(Type.UNKNOWN);
				}
								
				if( prefixNode.getModifiers().isImmutable() && !methodType.getModifiers().isImmutable() && !methodType.getModifiers().isReadonly()  )
					addError(Error.ILLEGAL_ACCESS, "Mutable method cannot be called from an immutable reference");
				
				if( prefixNode.getModifiers().isReadonly() && !methodType.getModifiers().isImmutable() && !methodType.getModifiers().isReadonly()  )
					addError(Error.ILLEGAL_ACCESS, "Mutable method cannot be called from a readonly reference");
			}			
			else
			{									
				addError(Error.INVALID_METHOD, "Cannot apply arguments to non-method type " + prefixType, prefixType);
				node.setType(Type.UNKNOWN);			
			}
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	public static boolean fieldIsAccessible( Node field, Type type ) {
		//constants are no longer all public
		if ( field.getModifiers().isPublic() ) 
			return true;		
		
		//if inside class		
		Type checkedType = type.getTypeWithoutTypeArguments();
		Type enclosing = field.getEnclosingType().getTypeWithoutTypeArguments();
		
		while( checkedType != null ) {
			if( enclosing.equals(checkedType) )
				return true;			
			checkedType = checkedType.getOuter();
		}
		
		checkedType = type.getTypeWithoutTypeArguments();
		if( field.getModifiers().isProtected() && checkedType instanceof ClassType ) {
			ClassType classType = ((ClassType) checkedType).getExtendType();
			while( classType != null ) {
				if( enclosing.equals(classType))
					return true;
				classType = classType.getExtendType();
			}			
		}		

		return false;
	}

	public Object visit(ASTBreakOrContinueStatement node, Boolean secondVisit) throws ShadowException {
		if( secondVisit ) {			
			Node parent = node.jjtGetParent();
			boolean found = false;
			
			while( parent != null && !found ) {
				if( parent instanceof ASTDoStatement || 
					parent instanceof ASTForeachStatement ||
					parent instanceof ASTForStatement ||
					parent instanceof ASTWhileStatement )
					found = true;
				else
					parent = parent.jjtGetParent();
			}
			
			if( !found )
				addError(node, Error.INVALID_STRUCTURE, node.getImage() + " statement cannot occur outside of a loop body");
		}		
		return WalkType.POST_CHILDREN;
	}	
	
	private boolean visitArrayInitializer(Node node)
	{
		Node child = node.jjtGetChild(0);
		Type result = child.getType();
		
		boolean nullable = false;
		
		for( int i = 1; i < node.jjtGetNumChildren(); i++ ) //cycle through types, upgrading to broadest legal type
		{
			child = node.jjtGetChild(i);
			Type type = child.getType();
			
			if( child.getModifiers().isNullable() )
				nullable = true;
							
			if( result.isSubtype(type) )					
				result = type;				
			else if( !type.isSubtype(result) ) //neither is subtype of other, panic!
			{
				addError(Error.MISMATCHED_TYPE, "Types in array initializer list do not match");
				node.setType(Type.UNKNOWN);
				return false;
			}
		}

		/*//old code assumes that ultimate type is multidimensional array	
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
		ArrayType arrayType = new ArrayType(baseType, dimensions);
		*/
		
		//new code assumes that result is array of arrays
		ArrayType arrayType;
		
		if( nullable )
		{
			node.addModifier(Modifiers.NULLABLE);
			arrayType = new ArrayType(result, 1, nullable);
		}
		else
			arrayType = new ArrayType(result, 1, nullable);
		
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
			addError(Error.INVALID_TYPE, "Supplied type " + assertType + " cannot be used in the condition of an assert, boolean required", assertType);
		
		if( node.jjtGetNumChildren() > 1 )
		{
			Node child = node.jjtGetChild(1);
			Type type = child.getType();
			if( type == null )
				addError(Error.NOT_OBJECT, "Object type required for assert information and no type found");
			else if( child.getModifiers().isTypeName() && !(type instanceof SingletonType) )
				addError(Error.NOT_OBJECT, "Object type required for assert information but type name used");
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTLiteral node, Boolean secondVisit) throws ShadowException {
		if( !secondVisit )
			return WalkType.POST_CHILDREN;
		
		Type type = literalToType(node.getLiteral());
		node.setType(type);
		if( type != Type.NULL )
			currentType.addReferencedType(type);
		
		//ugly but needed to find negation		
		Node greatGrandparent = node.jjtGetParent().jjtGetParent().jjtGetParent();
		boolean negated = false;

		if( greatGrandparent instanceof ASTUnaryExpression ) {
			Node greatGreat = greatGrandparent.jjtGetParent();
			if( greatGreat instanceof ASTUnaryExpression && greatGreat.getImage().equals("-") )
				negated = true;
		}
		
		String literal = node.getImage();
		String lower = literal.toLowerCase();
		int length = literal.length();
		
		try
		{		
			if (literal.equals("null"))
				node.setValue(ShadowValue.NULL);
			else if (literal.startsWith("\'") && literal.endsWith("\'"))				
				node.setValue(ShadowCode.parseCode(literal));			
			else if (literal.startsWith("\"") && literal.endsWith("\""))
				node.setValue(ShadowString.parseString(literal));
			else if (literal.equals("true"))
				node.setValue(new ShadowBoolean(true));
			else if (literal.equals("false"))
				node.setValue(new ShadowBoolean(false));
			else if (lower.endsWith("f") && !lower.startsWith("0x") && !lower.startsWith("0c") && !lower.startsWith("0b") )
				node.setValue(ShadowFloat.parseFloat(lower.substring(0,  length - 1)));
			else if (lower.endsWith("d") && !lower.startsWith("0x") && !lower.startsWith("0c") && !lower.startsWith("0b") )
				node.setValue(ShadowDouble.parseDouble(lower.substring(0, length - 1)));
			else if (literal.indexOf('.') != -1 || (lower.indexOf('e') != -1 && !lower.startsWith("0x") && !lower.startsWith("0c") && !lower.startsWith("0b") ))
				node.setValue(ShadowDouble.parseDouble(lower));
			else
				node.setValue(ShadowInteger.parseNumber(lower, negated));
		}
		catch(NumberFormatException e)
		{
			addError(node, Error.INVALID_LITERAL, "Value out of range");
		}
		catch(IllegalArgumentException e)
		{
			addError(node, Error.INVALID_LITERAL, e.getLocalizedMessage());
		}
		
		return WalkType.POST_CHILDREN;			
	}
	
	@Override
	public Object visit(ASTNullLiteral node, Boolean secondVisit) throws ShadowException {
		node.jjtGetParent().setImage(node.getImage());		
		return WalkType.NO_CHILDREN;
	}
	
	@Override
	public Object visit(ASTBooleanLiteral node, Boolean secondVisit) throws ShadowException {
		node.jjtGetParent().setImage(node.getImage());		
		return WalkType.NO_CHILDREN;
	}
	
	@Override
	public Object visit(ASTPrimitiveType node, Boolean secondVisit) throws ShadowException {		
		node.setType(nameToPrimitiveType(node.getImage()));
		currentType.addReferencedType(node.getType());
		node.addModifier(Modifiers.TYPE_NAME);
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
				node.setType(methodType.getReturnTypes()); //return statement set to exactly the types method returns
														   //implicit casts are added in TAC conversion if the values don't match exactly			
				
				if( node.getType().size() == 0 )
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
						sequenceType = (SequenceType) type;
					else
						sequenceType = new SequenceType( child );
					
					SequenceType updatedTypes = new SequenceType();
					
					//reconstitute full return types based on types with return modifiers
					//return modifiers can differ from regular modifiers because readonly and immutable methods
					//can enforce readonly constraints to keep object internals from changing during the method
					for( int i = 0; i < sequenceType.size(); ++i )
					{
						Modifiers modifiers = sequenceType.get(i).getModifiers();
						modifiers.removeModifier(Modifiers.TEMPORARY_READONLY);
						
						updatedTypes.add(new SimpleModifiedType(sequenceType.getType(i), modifiers ) );
					}
										
					if( !updatedTypes.isSubtype(node.getType()) )						
						addError(Error.INVALID_RETURNS, "Cannot return " + updatedTypes + " when " + node.getType() + (node.getType().size() == 1 ? " is" : " are") + " expected", node.getType(), updatedTypes);
					
					for( ModifiedType modifiedType : updatedTypes )
						if( modifiedType.getModifiers().isTypeName() )
							addError(Error.INVALID_RETURNS, "Cannot return type name from a method" );			
				}
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
				addError(Error.INVALID_MODIFIER, "Modifier nullable cannot be applied to primitive type " + type, type);				
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
						addError(Error.INVALID_CREATE, "Class type " + type + " cannot invoke a parent create because it does not extend another type", type);
				}								
								
				MethodSignature signature = setMethodType(node, type, "create", arguments, null); //type set inside
				node.setMethodSignature(signature);
			}	
			else
				addError(Error.INVALID_CREATE, "Non-class type " + currentType + " cannot be created", currentType);
			
			ASTCreateDeclaration grandparent = (ASTCreateDeclaration) node.jjtGetParent().jjtGetParent();
			grandparent.setExplicitInvocation(true);
		}		
		
		return WalkType.POST_CHILDREN; 
	}
	

	public Object visit(ASTType node, Boolean secondVisit) throws ShadowException { 
		if( secondVisit ) {
			boolean isNullable = node.getModifiers().isNullable();
			
			Node child = node.jjtGetChild(0);			
			node.setModifiers(child.getModifiers());
			
			Type type = child.getType();
			
			if( isNullable ) { 
				if( type instanceof ArrayType ) {
					ArrayType arrayType = (ArrayType) type;
					type = arrayType.convertToNullable();
					
					if( arrayType.recursivelyGetBaseType().isPrimitive() )
						addError(Error.INVALID_MODIFIER, "Primitive array type " + type + " cannot be marked nullable");
				}				
			}
			
			node.setType(type);
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
	
	public Object visit(ASTArrayDimensions node, Boolean secondVisit) throws ShadowException
	{
		if( secondVisit ) {
			for( int i = 0; i < node.jjtGetNumChildren(); i++ ) {
				Type childType = node.jjtGetChild(i).getType();
				
				if( !childType.isIntegral() ) {
					addError(node.jjtGetChild(i), Error.INVALID_SUBSCRIPT, "Supplied type " +  childType + " cannot be used in this array creation, integral type required", childType);
					node.setType(Type.UNKNOWN);
					return WalkType.POST_CHILDREN;
				}
			}
		}
		
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
		MethodType methodType = signature.getMethodType();
		
		//add type parameters
		if( node.jjtGetChild(index) instanceof ASTTypeParameters )
		{			
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
		
		currentType.addReferencedType(methodType);
	}
	
	
}
