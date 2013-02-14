package shadow.typecheck;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import shadow.AST.ASTWalker;
import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.ASTBlock;
import shadow.parser.javacc.ASTClassOrInterfaceBody;
import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.ASTClassOrInterfaceType;
import shadow.parser.javacc.ASTCompilationUnit;
import shadow.parser.javacc.ASTCreateDeclaration;
import shadow.parser.javacc.ASTDestroyDeclaration;
import shadow.parser.javacc.ASTExtendsList;
import shadow.parser.javacc.ASTFieldDeclaration;
import shadow.parser.javacc.ASTFormalParameters;
import shadow.parser.javacc.ASTFunctionType;
import shadow.parser.javacc.ASTImplementsList;
import shadow.parser.javacc.ASTImportDeclaration;
import shadow.parser.javacc.ASTLiteral;
import shadow.parser.javacc.ASTLocalMethodDeclaration;
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
import shadow.parser.javacc.ASTUnqualifiedName;
import shadow.parser.javacc.ASTVariableDeclarator;
import shadow.parser.javacc.ASTVariableInitializer;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.SignatureNode;
import shadow.typecheck.BaseChecker.Error;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassInterfaceBaseType;
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
import shadow.typecheck.type.UninstantiatedClassType;
import shadow.typecheck.type.UninstantiatedInterfaceType;

public class FieldAndMethodChecker extends BaseChecker {
	
	private Map<Type,Node> nodeTable = null;
		
	public FieldAndMethodChecker(boolean debug, HashMap<Package, HashMap<String, ClassInterfaceBaseType>> typeTable, List<String> importList, Package packageTree,  Map<Type,Node> nodeTable  ) {
		super(debug, typeTable, importList, packageTree );
		this.nodeTable = nodeTable;
	}
	

	
	public void buildTypes( Map<String, Node> files ) throws ShadowException
	{
		ASTWalker walker = new ASTWalker(this);
		
		//walk over all the files
		for( Node node : files.values() )								
			walker.walk(node);
		
		//deal with class problems
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
							int other = 0;
							
							for( MethodSignature signature : methods)
							{
								if( signature.getModifiers().isGet() )
									getterCount++;
								else if( signature.getModifiers().isSet() )
									setterCount++;
								else
									other++;
							}
							
							if( fieldModifiers.isGet() && getterCount == 0 )
							{
								if( other > 0 )								
									addError(Error.INVL_TYP, "Cannot create default get property " +  field.getKey() + " when there is already a method of the same name" );
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
									classType.addMethod(field.getKey(), new MethodSignature(methodType, field.getKey(), methodNode));
								}								
							}
							
							if( fieldModifiers.isSet() && setterCount == 0 )
							{
								if( other > 0 )								
									addError(Error.INVL_TYP, "Cannot create default set property " +  field.getKey() + " when there is already a method of the same name" );
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
									classType.addMethod(field.getKey(), new MethodSignature(methodType, field.getKey(), methodNode));
								}								
							}
						}						
					}
				}
			}
		}
	}
	
	
	//But we still need to know what type we're in to sort out type parameters in the extends list
	//declarationType will differ from current type only before the body (extends list, implements list)
	//if no extends added, fix those too	
	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit) throws ShadowException
	{			
		if( secondVisit )	
			currentType = currentType.getOuter();
		else
			currentType = (ClassInterfaceBaseType)node.getType();
			
		return WalkType.POST_CHILDREN;
	}
	
	
	//type parameters will only be visited here on methods 
	@Override
	public Object visit(ASTTypeParameters node, Boolean secondVisit) throws ShadowException
	{		
		if( !secondVisit )
		{	
			Node parent = node.jjtGetParent();
		
			if( parent instanceof ASTMethodDeclarator )		
			{
				Type methodType = parent.jjtGetParent().getType(); //declaration is one up from declarator
				methodType.setParameterized(true);
			}
		}
		
		return WalkType.POST_CHILDREN;
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
			addError(node, Error.INVL_MOD, "Only one public, private, or protected modifier can be used" );
			success = false;
		}
		
		if( currentType instanceof InterfaceType )
		{
			if( visibilityModifiers > 0 )
			{			
				addError(node, Error.INVL_MOD, "Interface methods cannot be marked public, private, or protected since they are all public by definition" );
				success = false;
			}			
			
			node.addModifier(Modifiers.PUBLIC);
			node.getType().addModifier(Modifiers.PUBLIC);
		}
		else if( visibilityModifiers == 0 )
		{			
			addError(node, Error.INVL_MOD, "Every method must be specified as public, private, or protected" );
			success = false;
		}
		
		return success;
	}

	/**
	 * Add the field declarations.
	 */
	public Object visit(ASTFieldDeclaration node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		// a field dec has a type followed by 1 or more idents
		Type type = node.jjtGetChild(0).getType();
		
		// make sure we have this type
		if(type == null)
		{
			addError(node.jjtGetChild(0).jjtGetChild(0), Error.UNDEF_TYP, node.jjtGetChild(0).jjtGetChild(0).getImage());
			return WalkType.NO_CHILDREN;
		}
		
		node.setType(type);		// set the type to the node
		node.setEnclosingType(currentType);

		node.addModifier(Modifiers.FIELD);
		
		if( currentType.getModifiers().isImmutable() ) {
			node.addModifier(Modifiers.IMMUTABLE);
			//node.addModifier(Modifiers.FINAL);
			/*
			if( type.isPrimitive() )
				node.addModifier(Modifiers.FINAL);
			*/
		}
		
		if( currentType instanceof ClassInterfaceBaseType )
		{
			ClassInterfaceBaseType currentClass = (ClassInterfaceBaseType)currentType;
			
			if( currentType instanceof InterfaceType )
			{
				//all interface fields are implicitly constant
				node.addModifier(Modifiers.CONSTANT);
				//type.addModifier(Modifiers.CONSTANT);				
			}
			
			if( type.isParameterized() && node.getModifiers().isConstant() )
				addError(node, Error.INVL_TYP, "Fields marked constant cannot have parameterized types");	
			
			
			// go through inserting all the idents
			for(int i=1; i < node.jjtGetNumChildren(); ++i)
			{
				ASTVariableDeclarator child = (ASTVariableDeclarator) node.jjtGetChild(i);
				child.setType(type);
				child.setModifiers(node.getModifiers());
				child.setEnclosingType(currentType);
				String symbol = child.jjtGetChild(0).getImage();
				
				// make sure we don't already have this symbol
				if(currentClass.containsField(symbol) || currentClass.containsMethod(symbol) || currentClass.containsInnerClass(symbol) )
				{
					addError(child.jjtGetChild(0), Error.MULT_SYM, symbol);
					return WalkType.NO_CHILDREN;
				}			

				currentClass.addField(symbol, child);
			}
		}
		else
		{
			addError(node, "Cannot add field to a structure that is not a class, interface, error, enum, or exception");
			return WalkType.NO_CHILDREN;
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
		return visitMethod( methodDeclarator, node, secondVisit );
	}
	
	public Object visit(ASTCreateDeclaration node, Boolean secondVisit) throws ShadowException {		
		//create uses the same node for modifiers and signature
		
		if( secondVisit && (currentType instanceof SingletonType) ) {
			Node parameters = node.jjtGetChild(0); //formal parameters
			if( parameters.jjtGetNumChildren() > 0 )
				addError( node, Error.INVL_TYP, "Singleton type can only specify a default create");
		}
		
		return visitMethod( node, node, secondVisit );
	}
	
	public Object visit(ASTDestroyDeclaration node, Boolean secondVisit) throws ShadowException {	
		//destroy uses the same node for modifiers and signature
		return visitMethod( node, node, secondVisit );		
	}
	
	public Object visit(ASTLocalMethodDeclaration node, Boolean secondVisit) throws ShadowException {
		if( !secondVisit )
		{			
			Node declaration = node.jjtGetChild(0);
			MethodSignature signature = new MethodSignature( currentType, declaration.getImage(), node.getModifiers(), node);
			node.setMethodSignature(signature);
			MethodType methodType = signature.getMethodType();
			node.setType(methodType);
			node.setEnclosingType(currentType);
			
			//if( Modifiers.isStatic(currentMethod.getFirst().getModifiers()))
			//	node.addModifier(Modifiers.STATIC);
			
			if( currentMethod.getFirst().getModifiers().isImmutable())
				node.addModifier(Modifiers.IMMUTABLE);
			
			if( currentMethod.getFirst().getModifiers().isReadonly())
				node.addModifier(Modifiers.READONLY);
			
			//node.addModifier(Modifiers.FINAL);
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTBlock node, Boolean secondVisit) throws ShadowException {
		return WalkType.NO_CHILDREN; //skip all blocks
	}
	
	
	private Object visitMethod( Node declaration, SignatureNode node, Boolean secondVisit )
	{	
		MethodSignature signature;
		
		if( secondVisit )
		{
			signature = node.getMethodSignature();
			if( currentType instanceof ClassInterfaceBaseType )
			{
				ClassInterfaceBaseType currentClass = (ClassInterfaceBaseType)currentType;

				// make sure we don't already have an indistinguishable method
				if( currentClass.containsIndistinguishableMethod(signature) )
				{				
					// get the first signature
					MethodSignature method = currentClass.getIndistinguishableMethod(signature);				
					addError(declaration, Error.MULT_MTH, "Indistinguishable method already declared on line " + method.getNode().getLine());
					return false;
				}	
				
				
				//check fields
				ModifiedType field = currentClass.getField(signature.getSymbol());				
				if( field != null )
				{	
					if( !(field.getModifiers().isGet() && signature.isGet()) && !(field.getModifiers().isSet() && signature.isSet())  )
					{
						addError(declaration, Error.MULT_SYM, "First declared on line " + currentClass.getField(signature.getSymbol()).getLine() );
						return false;				
					}
				}
				
				if( currentClass.containsInnerClass(signature.getSymbol() ) )
				{
					addError(declaration, Error.MULT_SYM );
					return false;
				}
				
				// add the method to the current type
				currentClass.addMethod(declaration.getImage(), signature);
			}
			else			
				addError(node, "Cannot add method to a structure that is not a class, interface, error, enum, or exception");
			
			currentMethod.removeFirst();
		}
		else
		{
			signature = new MethodSignature( currentType, declaration.getImage(), node.getModifiers(), node);
			node.setMethodSignature(signature);
			MethodType methodType = signature.getMethodType();
			node.setType(methodType);
			node.setEnclosingType(currentType);
			checkMethodModifiers( node, node.getModifiers() );
			currentMethod.addFirst(node);
		}		
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTResultTypes node, Boolean secondVisit) throws ShadowException {
		if(secondVisit)
		{
			
			Node parent = node.jjtGetParent();
			if( parent instanceof ASTMethodDeclarator )
			//ASTFunctionType adds result types differently
			{
				parent = parent.jjtGetParent();
				MethodSignature signature = ((SignatureNode)parent).getMethodSignature();
				
				if( signature.getModifiers().isSet() )
				{				
					if( node.jjtGetNumChildren() != 0 )
						addError(node, Error.INVL_TYP, "Methods marked with set cannot have any return values");				
				}			
				else if( signature.getModifiers().isGet() )
				{
					if( node.jjtGetNumChildren() != 1 )
						addError(node, Error.INVL_TYP, "Methods marked with get must have exactly one return value");
				}
		
				for(int i=0; i < node.jjtGetNumChildren(); ++i) {
					Type type = node.jjtGetChild(i).getType();
					
					// make sure the return type is in the type table
					if(type == null)					
						addError(node.jjtGetChild(i), Error.UNDEF_TYP);
					else						
					// add the return type to our signature
						signature.addReturn(node.jjtGetChild(i));
				}
			}
		}
		
		return WalkType.POST_CHILDREN;			
	}
	
	
	public Object visit(ASTFormalParameters node, Boolean secondVisit) throws ShadowException {
		if(secondVisit)
		{			
			Node parent = node.jjtGetParent();			
			if( parent instanceof ASTMethodDeclarator )
				parent = parent.jjtGetParent();  //signature is kept in ASTMethodDeclaration
			
			MethodSignature signature = ((SignatureNode)parent).getMethodSignature();
			
			if( signature.getModifiers().isSet() )
			{				
				if( node.jjtGetNumChildren() != 1 )
					addError(node, Error.INVL_TYP, "Methods marked with set must have exactly one parameter");				
			}			
			else if( signature.getModifiers().isGet() )
			{
				if( node.jjtGetNumChildren() != 0 )
					addError(node, Error.INVL_TYP, "Methods marked with get cannot have any parameters");
			}
			
			
			// go through all the formal parameters
			for(int i=0; i < node.jjtGetNumChildren(); ++i) 
			{
				Node parameter = node.jjtGetChild(i);
				
				//child 0 is Modifiers
				
				// get the name of the parameter
				String paramSymbol = parameter.jjtGetChild(2).getImage();
				
				// check if it's already in the set of parameter names
				if(signature.containsParam(paramSymbol)) {
					addError(parameter.jjtGetChild(1), Error.MULT_SYM, "In parameter names");
					return false;	// we're done with this node
				}				
				
				//child 1 is type
				Node child = parameter.jjtGetChild(1);			
				
				// get the type of the parameter
				parameter.setType(child.getType());
				
				if( signature.getModifiers().isSet() )
					parameter.addModifier(Modifiers.ASSIGNABLE);
				
				// make sure this type is in the type table
				if(parameter.getType() == null)
				{
					addError(child, Error.UNDEF_TYP);
					return false;
				}
					
				// add the parameter type to the signature
				signature.addParameter(paramSymbol, parameter);
			}	
		}
		
		return WalkType.POST_CHILDREN;			
	}	
	
	
	/**
	 * Given an ASTFunctionType node recursively builds the corresponding MethodType type.
	 * @param node
	 * @return
	 */
	public Object visit(ASTFunctionType node, Boolean secondVisit) throws ShadowException
	{
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		MethodType methodType = new MethodType(); // it has no name
		
		// add all the parameters to this method
		int i;
		for(i=0; i < node.jjtGetNumChildren(); ++i) {
			Node child = node.jjtGetChild(i);
			
			// check to see if we've moved on to the result types
			if( child instanceof ASTResultTypes )
				break;
			
			if( child.getType() == null ) 
			{
				addError(child, Error.UNDEF_TYP, child.getImage());
				node.setType(Type.UNKNOWN);
				return WalkType.POST_CHILDREN;
			}
				
			methodType.addParameter(child);	// add the type as the parameter
		}
		
		// check to see if we have result types
		if(i < node.jjtGetNumChildren())
		{
			Node resultsNode = node.jjtGetChild(i);
			
			for(int j = 0; j < resultsNode.jjtGetNumChildren(); ++j)
			{
				Node child = resultsNode.jjtGetChild(j);
				Type type = child.getType();
				
				if(type == null)
				{
					addError(child.jjtGetChild(0), Error.UNDEF_TYP, child.jjtGetChild(0).getImage());
					node.setType(Type.UNKNOWN);
					return WalkType.POST_CHILDREN;
				}
					
				methodType.addReturn(child);
			}
		}
		
		node.setType(methodType);
		
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
	public Object visit(ASTTypeDeclaration node, Boolean secondVisit) throws ShadowException {
		if( secondVisit )
		{
			Node child = node.jjtGetChild(1); //child 0 is modifiers
			node.jjtGetParent().setType(child.getType());
			node.jjtGetParent().setModifiers(child.getModifiers());
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
	public Object visit(ASTTypeParameter node, Boolean secondVisit)	throws ShadowException
	{
		if( secondVisit )
		{
			String symbol = node.getImage();
			TypeParameter typeParameter = new TypeParameter(symbol);					
			Node grandparent = node.jjtGetParent().jjtGetParent(); //parent is always TypeParameters
			Type type;
			
			
			if( grandparent instanceof ASTMethodDeclarator ) //skip class declarations (already done in type updater)
			{
				//if( grandparent instanceof ASTClassOrInterfaceDeclaration )										
				//	type = grandparent.getType();
				//else // grandparent instanceof ASTMethodDeclarator				
					type = grandparent.jjtGetParent().getType(); //method declaration is  three levels up
				
				for( ModifiedType existing : type.getTypeParameters() )
					if( existing.getType().getTypeName().equals( symbol ) )
						addError( node, Error.MULT_SYM, "Multiply defined type parameter " + typeParameter.getTypeName() );
				
				if( node.jjtGetNumChildren() > 0 )
				{
					ASTTypeBound bound = (ASTTypeBound)(node.jjtGetChild(0));				
					for( int i = 0; i < bound.jjtGetNumChildren(); i++ )								
						typeParameter.addBound((ClassInterfaceBaseType)(bound.jjtGetChild(i).getType()));				
				}			
				
				node.setType(typeParameter);
				type.addTypeParameter(node);
			}			
		}
		return WalkType.POST_CHILDREN;
	}
	
	
	@Override
	public Object visit(ASTTypeBound node, Boolean secondVisit)	throws ShadowException
	{
		if( secondVisit )
		{
			for( int i = 0; i < node.jjtGetNumChildren(); i++ )
			{
				Node child = node.jjtGetChild(i); //must be ASTClassOrInterfaceType
				Type type = lookupType( child.getImage() );
				
				if( type == null )
				{
					addError( node, Error.UNDEF_TYP, "Undefined type: " + child.getImage() );
					type = Type.UNKNOWN;
				}
				
				child.setType(type);
			}
		}
		
		return WalkType.POST_CHILDREN;
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
	
	@Override
	public Object visit(ASTClassOrInterfaceType node, Boolean secondVisit) throws ShadowException
	{		
		if( secondVisit )
		{	
			if( node.getType() == null )
			{					
				Node child = node.jjtGetChild(0); 
				String typeName = child.getImage();
				int start = 1;
				
				if( child instanceof ASTUnqualifiedName )
				{					
					child = node.jjtGetChild(start);
					typeName += "@" + child.getImage();
					start++;					
				}
				
				ClassInterfaceBaseType type = lookupType(typeName);
				ClassInterfaceBaseType instantiatedType;
				
				if(type == null)
				{
					addError(node, Error.UNDEF_TYP, typeName);			
					node.setType(Type.UNKNOWN);					
				}
				else
				{						
					if( child.jjtGetNumChildren() == 1 ) //contains arguments
					{
						SequenceType arguments = (SequenceType) child.jjtGetChild(0).getType();
						if( type instanceof ClassType )						
							instantiatedType = new UninstantiatedClassType((ClassType)type, arguments);							
						else
							instantiatedType = new UninstantiatedInterfaceType((InterfaceType)type, arguments);
						//the only dependencies that matter for type parameters are classes which themselves have
						currentType.addTypeParameterDependency(type);						
					}
					else if( type.isParameterized() ) //parameterized but no parameters!	
					{
						addError(node, Error.INVL_TYP, child.getImage() + " requires type parameters but none were supplied");
						instantiatedType = Type.UNKNOWN;
					}
					else
						instantiatedType = type;
					
					
					//Container<T, List<String>, String, Thing<K>>:Stuff<U>
					for( int i = start; i < node.jjtGetNumChildren(); i++ )
					{
						child = node.jjtGetChild(i);
						type = type.getInnerClass(child.getImage());
						
						if( type == null )
						{
							addError(node, Error.UNDEF_TYP, child.getImage());
							node.setType(Type.UNKNOWN);
							return WalkType.POST_CHILDREN;
						}
						
						if( child.jjtGetNumChildren() == 1 ) //contains arguments
						{
							SequenceType arguments = (SequenceType) child.jjtGetChild(0).getType();
							if( type instanceof ClassType )						
								instantiatedType = new UninstantiatedClassType((ClassType)type, arguments, instantiatedType);							
							else
								instantiatedType = new UninstantiatedInterfaceType((InterfaceType)type, arguments, instantiatedType);
							
							//the only dependencies that matter for type parameters are classes which themselves have
							currentType.addTypeParameterDependency(type);
						}
						else if( type.isParameterized() ) //parameterized but no parameters!
						{
							addError(node, Error.INVL_TYP, child.getImage() + " requires type parameters but none were supplied");
							instantiatedType = Type.UNKNOWN;
						}
						else
							instantiatedType = type;
					}
					
					//set the type now that it has type parameters 
					node.setType(instantiatedType);	
				}
			}
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	
	public Object visit(ASTExtendsList node, Boolean secondVisit) throws ShadowException { 
		return WalkType.NO_CHILDREN;
	}
	
	public Object visit(ASTImplementsList node, Boolean secondVisit) throws ShadowException { 
		return WalkType.NO_CHILDREN;
	}
	
	

	// Everything below here are just visitors to push up the type
		
	public Object visit(ASTType node, Boolean secondVisit) throws ShadowException { 
		return pushUpType(node, secondVisit); 
	}
	public Object visit(ASTVariableInitializer node, Boolean secondVisit) throws ShadowException { return pushUpType(node, secondVisit); }
}
