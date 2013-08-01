package shadow.typecheck;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import shadow.TypeCheckException;
import shadow.TypeCheckException.Error;
import shadow.AST.ASTWalker;
import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.ASTBlock;
import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.ASTClassOrInterfaceType;
import shadow.parser.javacc.ASTCompilationUnit;
import shadow.parser.javacc.ASTCreateBlock;
import shadow.parser.javacc.ASTCreateDeclaration;
import shadow.parser.javacc.ASTCreateDeclarator;
import shadow.parser.javacc.ASTDestroyDeclaration;
import shadow.parser.javacc.ASTEnumDeclaration;
import shadow.parser.javacc.ASTExtendsList;
import shadow.parser.javacc.ASTFieldDeclaration;
import shadow.parser.javacc.ASTFormalParameter;
import shadow.parser.javacc.ASTFormalParameters;
import shadow.parser.javacc.ASTFunctionType;
import shadow.parser.javacc.ASTImplementsList;
import shadow.parser.javacc.ASTLiteral;
import shadow.parser.javacc.ASTMethodDeclaration;
import shadow.parser.javacc.ASTMethodDeclarator;
import shadow.parser.javacc.ASTPrimitiveType;
import shadow.parser.javacc.ASTReferenceType;
import shadow.parser.javacc.ASTResultType;
import shadow.parser.javacc.ASTResultTypes;
import shadow.parser.javacc.ASTType;
import shadow.parser.javacc.ASTTypeArguments;
import shadow.parser.javacc.ASTTypeBound;
import shadow.parser.javacc.ASTTypeDeclaration;
import shadow.parser.javacc.ASTTypeParameter;
import shadow.parser.javacc.ASTTypeParameters;
import shadow.parser.javacc.ASTVariableDeclaratorId;
import shadow.parser.javacc.ASTVariableInitializer;
import shadow.parser.javacc.ASTViewDeclaration;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.SignatureNode;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.EnumType;
import shadow.typecheck.type.ErrorType;
import shadow.typecheck.type.ExceptionType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;

public class TypeUpdater extends BaseChecker
{
	public TypeUpdater(boolean debug,
			HashMap<Package, HashMap<String, Type>> typeTable,
			List<String> importList, Package packageTree) {
		super(debug, typeTable, importList, packageTree);
	}	
	
	
	public void updateTypes(Map<String, Node> files) throws ShadowException, TypeCheckException
	{	
		ASTWalker walker = new ASTWalker( this );
		for(Node declarationNode : files.values() )
			walker.walk(declarationNode);
		
		addConstructorsAndProperties();
		
		checkForCircularClassHierarchies(files);
		
		if( errorList.size() > 0 )
		{
			printErrors();
			throw errorList.get(0);
		}
	}
	
	private void addConstructorsAndProperties()
	{		
		for( Package p : typeTable.keySet() )
		{
			for( Type type : typeTable.get(p).values() ) 
			{
				if( type instanceof ClassType )
				{
					ClassType classType = (ClassType)type;
					if (classType.getMethods("create").isEmpty())
					{
						//if no creates, add the default one
						ASTCreateDeclaration createNode = new ASTCreateDeclaration(-1);
						createNode.setModifiers(Modifiers.PUBLIC);
						MethodSignature createSignature = new MethodSignature(classType, "create", createNode.getModifiers(), createNode);
						createNode.setMethodSignature(createSignature);
						classType.addMethod("create", createSignature);
						//note that the node is null for the default create, because nothing was made
					}
					
					
					//add default getters and setters
					for( Map.Entry<String, ? extends ModifiedType> field : classType.getFields().entrySet() )
					{
						Modifiers fieldModifiers = field.getValue().getModifiers();
						
						if( fieldModifiers.isGet() || fieldModifiers.isSet() )
						{
							List<MethodSignature> methods = classType.getMethods(field.getKey());
							int getterCount = 0;
							int setterCount = 0;
							int getterCollision = 0;
							int setterCollision = 0;
							
							for( MethodSignature signature : methods)
							{
								if( signature.getModifiers().isGet() )
									getterCount++;
								else if( signature.getModifiers().isSet() )
									setterCount++;
								else if( signature.getParameterTypes().isEmpty() )
									getterCollision++;
								else if( signature.getParameterTypes().size() == 1 && field.getValue().getType().equals(signature.getParameterTypes().get(0).getType())  )
									setterCollision++;
							}
							
							if( fieldModifiers.isGet() && getterCount == 0 )
							{
								if( getterCollision > 0 )								
									addError(Error.INVALID_MODIFIER, "Default get property " +  field.getKey() + " cannot replace a method with an indistinguishable signature" );
								else
								{
									ASTMethodDeclaration methodNode = new ASTMethodDeclaration(-1);
									methodNode.setModifiers(Modifiers.PUBLIC | Modifiers.GET );
									methodNode.setImage(field.getKey());
									methodNode.setType(field.getValue().getType());
									MethodType methodType = new MethodType(classType, methodNode.getModifiers() );
									Modifiers modifiers = new Modifiers(field.getValue().getModifiers());									
									modifiers.removeModifier(Modifiers.GET);
									modifiers.removeModifier(Modifiers.FIELD);
									if( modifiers.isSet() )
										modifiers.removeModifier(Modifiers.SET);
									if( modifiers.isWeak() )
										modifiers.removeModifier(Modifiers.WEAK);
									SimpleModifiedType modifiedType = new SimpleModifiedType(field.getValue().getType(), modifiers); 
									methodType.addReturn(modifiedType);									
									classType.addMethod(field.getKey(), new MethodSignature(methodType, field.getKey(), null));
								}								
							}
							
							if( fieldModifiers.isSet() && setterCount == 0 )
							{
								if( setterCollision > 0 )								
									addError(Error.INVALID_MODIFIER, "Default set property " +  field.getKey() + " cannot replace a method with an indistinguishable signature" );
								else
								{
									ASTMethodDeclaration methodNode = new ASTMethodDeclaration(-1);
									methodNode.setModifiers(Modifiers.PUBLIC | Modifiers.SET );
									methodNode.setImage(field.getKey());
									methodNode.setType(field.getValue().getType());
									MethodType methodType = new MethodType(classType, methodNode.getModifiers());
									Modifiers modifiers = new Modifiers(field.getValue().getModifiers());
									modifiers.removeModifier(Modifiers.SET);
									modifiers.removeModifier(Modifiers.FIELD);									
									modifiers.addModifier(Modifiers.ASSIGNABLE);
									if( modifiers.isGet() )
										modifiers.removeModifier(Modifiers.GET);
									if( modifiers.isWeak() )
										modifiers.removeModifier(Modifiers.WEAK);
									SimpleModifiedType modifiedType = new SimpleModifiedType(field.getValue().getType(), modifiers);									
									methodType.addParameter("value", modifiedType );									
									classType.addMethod(field.getKey(), new MethodSignature(methodType, field.getKey(), null));
								}								
							}
						}						
					}
				}
			}
		}
	}
	
	private void checkForCircularClassHierarchies(Map<String, Node> files)
	{
		for(Node declarationNode : files.values() )
		{			
			if( declarationNode.getType() instanceof ClassType )
			{				
				ClassType classType = (ClassType) declarationNode.getType();
				
				//check for circular class hierarchy				
				ClassType parent = classType.getExtendType();
				HashSet<Type> hierarchy = new HashSet<Type>();
				hierarchy.add(classType.getTypeWithoutTypeArguments());
				
				boolean circular = false;
				
				while( parent != null && !circular )
				{
					if( hierarchy.contains(parent.getTypeWithoutTypeArguments()) )	
					{
						addError(Error.INVALID_HIERARCHY, "Class " + classType + " contains a circular extends or implements definition");
						circular = true;
					}
					else
						hierarchy.add(parent.getTypeWithoutTypeArguments());
					parent = parent.getExtendType();
				}
				
				//check for circular interface issues
				for( InterfaceType interfaceType : classType.getInterfaces() )
				{
					if( interfaceType.isCircular() )
					{
						addError(Error.INVALID_HIERARCHY, "Interface " + interfaceType + " has a circular extends definition" );
						circular = true;
					}
				}				
			}			
		}
	}
	
	@Override
	public Type lookupType( String name ) //only addition to base checker is resolving type parameters
	{		
		if( declarationType != null &&  currentType != declarationType ) //in declaration header, check type parameters of current class declaration
		{
			if( declarationType.isParameterized() )
			{
				for( ModifiedType modifiedParameter : declarationType.getTypeParameters() )
				{
					
					Type parameter = modifiedParameter.getType();
					
					if( parameter instanceof TypeParameter )
					{
						TypeParameter typeParameter = (TypeParameter) parameter;
						if( typeParameter.getTypeName().equals(name) )
							return typeParameter;
					}					
				}				
			}
		}		
		
		return super.lookupType(name);
	}
			
		
	/**
	 * Checks method and field modifiers to see if they are legal
	 * 
	 * @param node
	 * @param modifiers
	 * @return
	 */
	
	public boolean checkMethodModifiers( Node node, Modifiers modifiers )
	{
		int visibilityModifiers = 0;
		boolean success = true;
		
		//modifiers are set, but we have to check them
		if( modifiers.isPublic())
			visibilityModifiers++;
		if( modifiers.isPrivate())
			visibilityModifiers++;
		if( modifiers.isProtected())
			visibilityModifiers++;
		
		if( visibilityModifiers > 1 )
		{
			addError(Error.INVALID_MODIFIER, "Only one public, private, or protected modifier can be used at once" );
			success = false;
		}
		
		if( currentType instanceof InterfaceType )
		{
			if( visibilityModifiers > 0 )
			{			
				addError(Error.INVALID_MODIFIER, "Interface methods cannot be marked public, private, or protected since they are all public by definition" );
				success = false;
			}			
			
			node.addModifier(Modifiers.PUBLIC);
			node.getType().addModifier(Modifiers.PUBLIC);
		}
		else if( visibilityModifiers == 0 )
		{			
			addError(Error.INVALID_MODIFIER, "Every class method must be specified as public, private, or protected" );
			success = false;
		}
		
		return success;
	}
	
	//should never be visited in the type updater
	/*
	public Object visit(ASTLocalMethodDeclaration node, Boolean secondVisit) throws ShadowException
	{
		if( !secondVisit )
		{			
			Node declaration = node.jjtGetChild(0);
			MethodSignature signature = new MethodSignature( currentType, declaration.getImage(), node.getModifiers(), node);
			node.setMethodSignature(signature);
			MethodType methodType = signature.getMethodType();
			node.setType(methodType);
			node.setEnclosingType(currentType);
			
			if( currentMethod.getFirst().getModifiers().isImmutable())
				node.addModifier(Modifiers.IMMUTABLE);
			
			if( currentMethod.getFirst().getModifiers().isReadonly())
				node.addModifier(Modifiers.READONLY);
		}
		
		return WalkType.POST_CHILDREN;
	}
	*/
	
	private Object visitMethod( String name, SignatureNode node, Boolean secondVisit )
	{	
		MethodSignature signature;
		
		if( secondVisit )
		{
			signature = node.getMethodSignature();			
			
			// make sure we don't already have an indistinguishable method
			if( currentType.containsIndistinguishableMethod(signature) )
			{				
				// get the first signature
				MethodSignature method = currentType.getIndistinguishableMethod(signature);				
				addError(Error.MULTIPLY_DEFINED_SYMBOL, "Indistinguishable method already declared on line " + method.getNode().getLine());
				return false;
			}	
			
			
			//check fields
			ModifiedType field = currentType.getField(signature.getSymbol());				
			if( field != null )
			{	
				if( !(field.getModifiers().isGet() && signature.isGet()) && !(field.getModifiers().isSet() && signature.isSet())  )
				{
					addError(Error.MULTIPLY_DEFINED_SYMBOL, "Method name " + signature.getSymbol() + " already declared as field on line " + currentType.getField(signature.getSymbol()).getLine() );
					return false;				
				}
			}
			
			if( currentType instanceof ClassType && ((ClassType)currentType).containsInnerClass(signature.getSymbol() ) )
			{
				addError(Error.MULTIPLY_DEFINED_SYMBOL, "Method name " + signature.getSymbol() + " already declared as inner class" );
				return false;
			}
			
			// add the method to the current type
			currentType.addMethod(name, signature);
			currentMethod.removeFirst();
		}
		else
		{
			signature = new MethodSignature( currentType, name, node.getModifiers(), node);
			node.setMethodSignature(signature);
			MethodType methodType = signature.getMethodType();
			node.setType(methodType);
			node.setEnclosingType(currentType);
			checkMethodModifiers( node, node.getModifiers() );
			currentMethod.addFirst(node);
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
	
	public Object visit(ASTCreateDeclarator node, Boolean secondVisit) throws ShadowException
	{
		if( secondVisit )
		{
			ASTCreateDeclaration parent = (ASTCreateDeclaration) node.jjtGetParent();
			MethodSignature signature = parent.getMethodSignature();
			visitDeclarator( node, signature  );			
			
			if( (currentType instanceof SingletonType) && signature.getParameterTypes().size() > 0 )
					addError(Error.INVALID_SINGLETON_CREATE, "Singleton type " + currentType + " can only specify a default create");
		}		
		
		return WalkType.POST_CHILDREN;	
	}
	
	public Object visit(ASTMethodDeclarator node, Boolean secondVisit) throws ShadowException
	{
		if( secondVisit )
		{
			ASTMethodDeclaration parent = (ASTMethodDeclaration) node.jjtGetParent();
			visitDeclarator( node, parent.getMethodSignature() );			
		}		
		
		return WalkType.POST_CHILDREN;
	}
	
	private void visitDeclarator( Node node, MethodSignature signature )
	{	
		//ASTCreateDeclarator will never have type parameters 
		int index = 0;		
		if( node.jjtGetChild(index) instanceof ASTTypeParameters )
		{
			Type methodType = signature.getMethodType();
			methodType.setParameterized(true);
			index++;
		}
		
		// add parameters to the signature
		ASTFormalParameters parameters = (ASTFormalParameters) node.jjtGetChild(index);
		List<String> parameterNames = parameters.getParameterNames();
		SequenceType parameterTypes = parameters.getType();		
		
		for( int i = 0; i < parameterNames.size(); ++i )				
			signature.addParameter(parameterNames.get(i), parameterTypes.get(i));
		
		if( signature.getModifiers().isSet() )
		{				
			if( parameterTypes.size() != 1 )
				addError(Error.INVALID_MODIFIER, "Methods marked with set must have exactly one parameter");
			else
				signature.getParameterTypes().get(0).getModifiers().addModifier(Modifiers.ASSIGNABLE);
		}			
		else if( signature.getModifiers().isGet() )
		{
			if( parameterTypes.size() != 0 )
				addError(Error.INVALID_MODIFIER, "Methods marked with get cannot have any parameters");
		}		

		//add return types
		index++;
		if( node.jjtGetNumChildren() > index ) //creates have no results
		{		
			ASTResultTypes results = (ASTResultTypes) node.jjtGetChild(index);
					
			for( ModifiedType modifiedType : results.getType() ) 
				signature.addReturn(modifiedType);
		
			if( signature.getModifiers().isSet() )
			{				
				if( signature.getReturnTypes().size() != 0 )
					addError(Error.INVALID_MODIFIER, "Methods marked with set cannot have return values");				
			}			
			else if( signature.getModifiers().isGet() )
			{
				if( signature.getReturnTypes().size() != 1 )
					addError(Error.INVALID_MODIFIER, "Methods marked with get must have exactly one return value");
			}
		}		
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
		}		
	
		return WalkType.POST_CHILDREN;
	}	
	
	public Object visit(ASTFormalParameters node, Boolean secondVisit) throws ShadowException
	{
		if(secondVisit)
		{		
			// go through all the formal parameters
			for(int i = 0; i < node.jjtGetNumChildren(); ++i) 
			{
				Node parameter = node.jjtGetChild(i);
				String parameterName = parameter.getImage();
				if( node.getParameterNames().contains( parameterName ) )
					addError(Error.MULTIPLY_DEFINED_SYMBOL, "Symbol " + parameterName + " already defined as a parameter name");
				node.addParameter(parameterName, parameter);
			}	
		}
		
		return WalkType.POST_CHILDREN;			
	}	
	

	@Override
	public Object visit(ASTCompilationUnit node, Boolean secondVisit) throws ShadowException
	{
		if( !secondVisit )		
			currentPackage = packageTree;		
				
		return WalkType.POST_CHILDREN;			
	}
	
	@Override
	public Object visit(ASTTypeDeclaration node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit )
		{
			Node child = node.jjtGetChild(1); //child 0 is modifiers
			node.jjtGetParent().setType(child.getType());
			node.jjtGetParent().setModifiers(child.getModifiers());
		}	
				
		return WalkType.POST_CHILDREN;			
	}
	
	
	private void updateImports( Node node )
	{
		//changing these items updates the correct imports inside the type
		List<Object> importedItems = node.getType().getImportedItems();
		for( int i = 0; i < importedItems.size(); i++ )
		{
			String item = (String) importedItems.get(i);
			if( item.contains("@") ) //specific class
			{
				Type type = lookupType(item);
				if( type == null )
					addError(Error.INVALID_IMPORT, "Undefined type " + item + " cannot be imported");
				else
					importedItems.set(i, type);	
			}
			else
			{
				Package p = packageTree.getChild(item);
				if( p == null )
					addError(Error.INVALID_IMPORT, "Undefined package " + item + " cannot be imported");
				else
					importedItems.set(i, p);					
			}			
		}
	}
	
	
	
	@Override
	public Object visit(ASTLiteral node, Boolean secondVisit) throws ShadowException
	{							
		node.setType(literalToType(node.getLiteral()));
		return WalkType.NO_CHILDREN;			
	}

	
	public Object visit(ASTResultType node, Boolean secondVisit) throws ShadowException
	{
		if( secondVisit )			
		{
			Node child = node.jjtGetChild(1); //child 0 is always modifiers 
			node.setType(child.getType());			
		}
		
		return WalkType.POST_CHILDREN;	
	}
	
	//Visitors below this point	
	
	private Object visitDeclaration( Node node, Boolean secondVisit ) throws ShadowException
	{		
		if( secondVisit )
		{
			if( declarationType instanceof ClassType )
			{
				ClassType classType = (ClassType) declarationType;
				
				if( classType.getExtendType() == null )
				{					
					if( declarationType instanceof EnumType )
						classType.setExtendType(Type.ENUM);
					else if( declarationType instanceof ErrorType )
					{
						if( declarationType == Type.ERROR )
							classType.setExtendType(Type.EXCEPTION);
						else
							classType.setExtendType(Type.ERROR);
					}
					else if( declarationType instanceof ExceptionType )
					{
						if( declarationType == Type.EXCEPTION )
							classType.setExtendType(Type.OBJECT);
						else
							classType.setExtendType(Type.EXCEPTION);
					}
					else if( declarationType instanceof ArrayType )													
						classType.setExtendType(Type.ARRAY);		
					else if( classType != Type.OBJECT )
						classType.setExtendType(Type.OBJECT);
				}
				else
				{
					if( declarationType == Type.ERROR )
						classType.setExtendType(Type.EXCEPTION);
					else if ( declarationType == Type.EXCEPTION )
						classType.setExtendType(Type.OBJECT);
				}				
			}
			
			if( declarationType.getOuter() != null )			
				declarationType.addTypeParameterDependency(declarationType.getOuter());
			
			declarationType = declarationType.getOuter();
		}
		else
		{
			declarationType = node.getType();
			currentPackage = declarationType.getPackage();
			updateImports( node );
		}
			
		return WalkType.POST_CHILDREN;
		
		
	}
	
	
	//But we still need to know what type we're in to sort out type parameters in the extends list
	//declarationType will differ from current type only before the body (extends list, implements list)
	//if no extends added, fix those too	
	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit) throws ShadowException
	{			
		return visitDeclaration( node, secondVisit );
	}
	
	@Override
	public Object visit(ASTEnumDeclaration node, Boolean secondVisit) throws ShadowException
	{			
		return visitDeclaration( node, secondVisit );
	}
	
	@Override
	public Object visit(ASTViewDeclaration node, Boolean secondVisit) throws ShadowException
	{			
		return visitDeclaration( node, secondVisit );
	}
	
	@Override
	public Object visit(ASTCreateDeclaration node, Boolean secondVisit) throws ShadowException 
	{
		Node methodDeclarator = node.jjtGetChild(0); //probably unnecessary
		return visitMethod( methodDeclarator.getImage(), node, secondVisit );
	}
	
	@Override
	public Object visit(ASTDestroyDeclaration node, Boolean secondVisit) throws ShadowException
	{	
		//destroy uses the same node for modifiers and signature
		return visitMethod( node.getImage(), node, secondVisit );		
	}

	
	/**
	 * Adds a method to the current type.
	 */
	public Object visit(ASTMethodDeclaration node, Boolean secondVisit) throws ShadowException {
		//different nodes used for modifiers and signature
		Node methodDeclarator = node.jjtGetChild(0);
		if( !secondVisit && currentType != null && currentType.getModifiers().isImmutable() ) 
		{
			node.addModifier(Modifiers.IMMUTABLE);
			methodDeclarator.addModifier(Modifiers.IMMUTABLE);
		}
		return visitMethod( methodDeclarator.getImage(), node, secondVisit );
	}
	
	/**
	 * Add the field declarations.
	 */
	public Object visit(ASTFieldDeclaration node, Boolean secondVisit) throws ShadowException
	{
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		// a field declaration has a type followed by an identifier (or a sequence of them)
		Type type = node.jjtGetChild(0).getType();
		
		// make sure we have this type
		if(type == null)
		{
			addError(Error.UNDEFINED_TYPE, "Type " + node.jjtGetChild(0).jjtGetChild(0).getImage() + " not defined in this context");
			return WalkType.NO_CHILDREN;
		}		
		
		node.setType(type);
		node.setEnclosingType(currentType);

		node.addModifier(Modifiers.FIELD);
		
		if( currentType.getModifiers().isImmutable() )
			node.addModifier(Modifiers.IMMUTABLE);
		
		if( currentType.getModifiers().isReadonly() )
			node.addModifier(Modifiers.READONLY);

		
		if( currentType instanceof InterfaceType )
		{
			//all interface fields are implicitly constant
			node.addModifier(Modifiers.CONSTANT);							
		}
		
		if( type.isParameterized() && node.getModifiers().isConstant() )
			addError(Error.INVALID_TYPE_PARAMETERS, "Fields marked constant cannot have parameterized types");
		
		if( type.isPrimitive() && node.getModifiers().isNullable() )		
			addError(Error.INVALID_MODIFIER, "Modifier nullable cannot be applied to primitive type " + type);
				
		// go through inserting all the identifiers
		for(int i = 1; i < node.jjtGetNumChildren(); ++i)
		{
			Node declarator = node.jjtGetChild(i);			
						
			declarator.setType(type);
			declarator.setModifiers(node.getModifiers());
			declarator.setEnclosingType(currentType);
			String symbol = declarator.getImage();
			
			// make sure we don't already have this symbol
			if(currentType.containsField(symbol) || currentType.containsMethod(symbol) || (currentType instanceof ClassType && ((ClassType)currentType).containsInnerClass(symbol)) )			
				addError(Error.MULTIPLY_DEFINED_SYMBOL, "Field " + symbol + " cannot be redefined in this context");				
			else
				currentType.addField(symbol, declarator);			
		}		
			
		return WalkType.POST_CHILDREN;
	}
		
	//type parameters will only be visited here on type declarations and class methods 
	@Override
	public Object visit(ASTTypeParameters node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit )
		{			
			SequenceType typeParameters = node.getType();			
			for( int i = 0; i < typeParameters.size(); ++i )
			{
				TypeParameter firstParameter = (TypeParameter) typeParameters.get(i).getType();
				
				for( int j = i + 1; j < typeParameters.size(); ++j )
				{
					TypeParameter secondParameter = (TypeParameter) typeParameters.get(j).getType();
					if( firstParameter.getTypeName().equals(secondParameter.getTypeName()) )
						addError(Error.MULTIPLY_DEFINED_SYMBOL, "Type parameter " + firstParameter.getTypeName() + " cannot be redefined in this context" );
				}
			}
		}
			
		
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTTypeParameter node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit )
		{	
			TypeParameter typeParameter = (TypeParameter) node.getType();
			
			if( node.jjtGetNumChildren() > 0 )
			{
				ASTTypeBound bound = (ASTTypeBound)(node.jjtGetChild(0));				
				for( int i = 0; i < bound.jjtGetNumChildren(); i++ )								
					typeParameter.addBound(bound.jjtGetChild(i).getType());				
			}
		}
		else
		{	
			//type parameters are created on the first visit so that bounds dependent on them can look up the right type
			String symbol = node.getImage();
			TypeParameter typeParameter = new TypeParameter(symbol);					
			
			Type type;
						
			if( currentMethod.size() == 1 )						
				type = currentMethod.getFirst().getMethodSignature().getMethodType();  //add type parameters to method
			else
				type = declarationType; //add parameters to current class
			
			if( type instanceof SingletonType )			
				addError(Error.INVALID_TYPE_PARAMETERS, "Singleton type " + declarationType + " cannot be parameterized");
			else if( type instanceof ExceptionType )			
				addError(Error.INVALID_TYPE_PARAMETERS, "Exception type " + declarationType + " cannot be parameterized");
			else if( type instanceof ErrorType )
				addError(Error.INVALID_TYPE_PARAMETERS, "Error type " + declarationType + " cannot be parameterized");
			
			node.setType(typeParameter);
			type.addTypeParameter(node);
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	
	@Override
	public Object visit(ASTClassOrInterfaceType node, Boolean secondVisit) throws ShadowException
	{
		return deferredTypeResolution(node, secondVisit);	
	}
	
	
	//add extends list
	public Object visit(ASTExtendsList node, Boolean secondVisit) throws ShadowException
	{
		if(secondVisit)
		{
			if( declarationType instanceof ClassType ) //includes error, exception, and enum (for now)
			{		
				ClassType classType = (ClassType)declarationType;			
				Node child = node.jjtGetChild(0); //only one thing in extends lists for classes
				Type extendType = child.getType();
				if( declarationType.getClass() == ClassType.class )
				{						
					if( extendType.getClass() == ClassType.class )
						classType.setExtendType((ClassType) extendType);
					else
						addError(Error.INVALID_EXTEND, "Class type " + declarationType + " cannot extend non-class type " + extendType);
				}
				else if( declarationType.getClass() == ExceptionType.class )
				{
					if( extendType.getClass() == ExceptionType.class )
						classType.setExtendType((ClassType) extendType);
					else
						addError(Error.INVALID_EXTEND, "Exception type " + declarationType + " cannot extend non-exception type " + extendType);
				}
			}
			else if( declarationType instanceof InterfaceType ) 
			{
				InterfaceType interfaceType = (InterfaceType)declarationType;
				for( int i = 0; i < node.jjtGetNumChildren(); i++ )
				{					
					Type type = node.jjtGetChild(i).getType();
					if( type instanceof InterfaceType )
						interfaceType.addInterface((InterfaceType)type);
					else				
						addError(Error.INVALID_EXTEND, "Interface type " + interfaceType + " cannot extend non-interface type " + type);
				}					
			}
		}
		
		return WalkType.POST_CHILDREN;
	}	
	
	//add implements list
	public Object visit(ASTImplementsList node, Boolean secondVisit) throws ShadowException
	{
		if(secondVisit)
		{
			ClassType classType = (ClassType)declarationType;
			for( int i = 0; i < node.jjtGetNumChildren(); i++ )
			{
				Type type = node.jjtGetChild(i).getType();
				if( type instanceof InterfaceType )
					classType.addInterface((InterfaceType)type);
				else				
					addError(Error.INVALID_IMPLEMENT, "Class type " + classType + " cannot implement non-interface type" + type);
			}				
		}
		
		return WalkType.POST_CHILDREN;
		
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

	
	public Object visit(ASTReferenceType node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit )
		{
			Node child = node.jjtGetChild(0);
			Type type = child.getType();
			node.setModifiers(new Modifiers(child.getModifiers()));
			
			List<Integer> dimensions = node.getArrayDimensions();
			
			if( dimensions.size() == 0 )
				node.setType(type);
			else
				node.setType(new ArrayType(type, dimensions));
		}
	
		return WalkType.POST_CHILDREN;
	}
	

	@Override
	public Object visit(ASTPrimitiveType node, Boolean secondVisit) throws ShadowException
	{
		node.setType(nameToPrimitiveType(node.getImage()));		
		return WalkType.NO_CHILDREN;			
	}
	
	
	/**
	 * Given an ASTFunctionType node recursively builds the corresponding MethodType type.
	 * @param node
	 * @return
	 */
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
	
	public Object visit(ASTType node, Boolean secondVisit) throws ShadowException { 
		return pushUpType(node, secondVisit); 
	}
	
	
	public Object visit(ASTBlock node, Boolean secondVisit) throws ShadowException {
		return WalkType.NO_CHILDREN; //skip all blocks
	}
	
	public Object visit(ASTCreateBlock node, Boolean secondVisit) throws ShadowException {
		return WalkType.NO_CHILDREN; //skip all blocks
	}
	
	public Object visit(ASTVariableInitializer node, Boolean secondVisit) throws ShadowException { return pushUpType(node, secondVisit); }

}
