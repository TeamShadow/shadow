package shadow.typecheck;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import shadow.AST.ASTWalker;
import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.ASTBlock;
import shadow.parser.javacc.ASTClassOrInterfaceBody;
import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.ASTClassOrInterfaceType;
import shadow.parser.javacc.ASTClassOrInterfaceTypeSuffix;
import shadow.parser.javacc.ASTCompilationUnit;
import shadow.parser.javacc.ASTCreateDeclaration;
import shadow.parser.javacc.ASTDestroyDeclaration;
import shadow.parser.javacc.ASTExtendsList;
import shadow.parser.javacc.ASTFieldDeclaration;
import shadow.parser.javacc.ASTFormalParameter;
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
import shadow.parser.javacc.ASTVariableDeclarator;
import shadow.parser.javacc.ASTVariableInitializer;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.SignatureNode;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassInterfaceBaseType;
import shadow.typecheck.type.ClassType;
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

public class FieldAndMethodChecker extends BaseChecker {
	
	private ClassInterfaceBaseType declarationType = null;
	private Map<Type,Node> nodeTable = null;
		
	public FieldAndMethodChecker(boolean debug, HashMap<Package, HashMap<String, ClassInterfaceBaseType>> typeTable, List<String> importList, Package packageTree,  Map<Type,Node> nodeTable  ) {
		super(debug, typeTable, importList, packageTree );
		this.nodeTable = nodeTable;
	}
	
	
	//process types in order, using topological sort
	private void updateTypeParameters()
	{	
		
		
		//sets the correct types for type parameters in declarations
		
		
		//first add all the type parameters in class and interface declarations
		/*
		for( Package p : getTypeTable().keySet() )
		{
			for( ClassInterfaceBaseType type : getTypeTable().get(p).values() ) //look through all types, updating their extends and implements
			{	
				TreeSet<String> missingTypes = new TreeSet<String>();				
				Node declarationNode = nodeTable.get(type);
				if( declarationNode != null )	
				{
					currentType = type;			
									
					for( int i = 0; i < declarationNode.jjtGetNumChildren(); i++ )
					{					
						if( declarationNode.jjtGetChild(i) instanceof ASTTypeParameters )
							processTypeParameters( type, (ASTTypeParameters)(declarationNode.jjtGetChild(i)), missingTypes );
					}
				}					
									
				
				if( missingTypes.size() > 0 )	
					addError( nodeTable.get(type), Error.UNDEF_TYP, "Cannot define type " + type + " because it depends on the following undefined types: " + missingTypes);		
			}
		}
		*/
		
		//then update the parameters in extends and implements list
		//has to be done after all the type declarations are processed,
		//otherwise we might update a superclass before the superclass parameters have been defined
		for( Package p : getTypeTable().keySet() )
		{
			for( ClassInterfaceBaseType type : getTypeTable().get(p).values() ) //look through all types, updating their extends and implements
			{	
				TreeSet<String> missingTypes = new TreeSet<String>();
				Node declarationNode = nodeTable.get(type);
				if( declarationNode != null )	
				{
					currentType = type;			
									
					for( int i = 0; i < declarationNode.jjtGetNumChildren(); i++ )
					{
						Node child = declarationNode.jjtGetChild(i);
						
						if( child instanceof ASTExtendsList )
						{
							if( currentType instanceof ClassType )
							{
								if( child.jjtGetNumChildren() > 0 )
								{
									updateTypeParameters( (ASTClassOrInterfaceType)(child.jjtGetChild(0)), missingTypes);
									Type childType = child.jjtGetChild(0).getType();
									
									((ClassType) currentType).setExtendType((ClassType) childType);
								}
							}
							else
							{
								InterfaceType interfaceType = (InterfaceType) currentType;
								ArrayList<InterfaceType> extendTypes = interfaceType.getExtendTypes();
								
								for( int j = 0; j < child.jjtGetNumChildren(); j++ )
								{
									updateTypeParameters( (ASTClassOrInterfaceType)(child.jjtGetChild(j)), missingTypes);
									Type childType = child.jjtGetChild(j).getType();
									
									extendTypes.set(j, (InterfaceType) childType );									
								}
							}						
						}
						else if( child instanceof ASTImplementsList )
						{					
							ClassType classType = (ClassType) currentType;
							ArrayList<InterfaceType> implementsTypes = classType.getInterfaces();							
							
							for( int j = 0; j < child.jjtGetNumChildren(); j++ )
							{
								updateTypeParameters( (ASTClassOrInterfaceType)(child.jjtGetChild(j)), missingTypes);
								Type childType = child.jjtGetChild(j).getType();
																
								implementsTypes.set(j, (InterfaceType) childType);									
							}							
						}
					}
					
					//update fields
					for( Node field : type.getFields().values() )
					{
						Node parent = field.jjtGetParent();
						ASTType typeNode = (ASTType) parent.jjtGetChild(0);
						updateTypeParameters(typeNode, missingTypes);
						field.setType(typeNode.getType());
					}
					
					//update methods					
					for( List<MethodSignature> signatures : type.getMethodMap().values() )
						for( MethodSignature signature : signatures )
							updateTypeParameters( signature, missingTypes );
				}					
									
				
				if( missingTypes.size() > 0 )	
					addError( nodeTable.get(type), Error.UNDEF_TYP, "Cannot define type " + type + " because it depends on the following undefined types: " + missingTypes);		
				
			}
		}
	}
	
	
	
	private void updateTypeParameters( MethodSignature signature, TreeSet<String> missingTypes  )
	{
		Node node = signature.getNode(); //MethodDeclaration, CreateDeclaration, or DestroyDeclaration
		
		if( node instanceof ASTMethodDeclaration )
			node = node.jjtGetChild(0);
		
		for( int i = 0; i < node.jjtGetNumChildren(); i++ )
		{
			Node child = node.jjtGetChild(i);
			
			if( child instanceof ASTFormalParameters )				
			{
				for( int j = 0; j < child.jjtGetNumChildren(); j++ )
					updateTypeParameters( (ASTFormalParameter) child.jjtGetChild(j), missingTypes  );				
			}
			else if( child instanceof ASTResultTypes )
			{
				for( int j = 0; j < child.jjtGetNumChildren(); j++ )
					updateTypeParameters( (ASTResultType) child.jjtGetChild(j), missingTypes  );				
			}			
		}		
	}
		
	private void updateTypeParameters(ASTType node, TreeSet<String> missingTypes)
	{
		Node child = node.jjtGetChild(0);
		
		if( child instanceof ASTReferenceType )
			updateTypeParameters( (ASTReferenceType)child, missingTypes );
		else if( child instanceof ASTFunctionType )
			updateTypeParameters( (ASTFunctionType)child, missingTypes );		
			
		//PrimitiveTypes are ignored
		//StaticArrayTypes... well, they may never exist
			
		node.setType(child.getType());
	}
	
	private void updateTypeParameters(ASTFunctionType node,
			TreeSet<String> missingTypes) {

		MethodType methodType = new MethodType(currentType, new Modifiers());
		
		for( int i = 0; i < node.jjtGetNumChildren(); i++ )
		{
			Node child = node.jjtGetChild(i);
			if( child instanceof ASTType )
			{
				updateTypeParameters( (ASTType) child, missingTypes  );
				methodType.addParameter(child);
			}
			else if( child instanceof ASTResultTypes )
			{	
				for( int j = 0; j < child.jjtGetNumChildren(); j++ )
				{
					updateTypeParameters( (ASTResultType) child.jjtGetChild(j), missingTypes  );
					methodType.addReturn(child.jjtGetChild(j));
				}
			}
		}	
		
		node.setType(methodType);		
	}

	private void updateTypeParameters(ASTResultType node, TreeSet<String> missingTypes) {
		
		Node child = node.jjtGetChild(1); //child 0 is always Modifiers
		
		updateTypeParameters( (ASTType)child , missingTypes  );
		node.setType(child.getType());
	}
	
	private void updateTypeParameters(ASTFormalParameter node, TreeSet<String> missingTypes) {
		
		Node child = node.jjtGetChild(1); //child 0 is always Modifiers
		
		updateTypeParameters( (ASTType)child, missingTypes  );
		node.setType(child.getType());
	}

	private void updateTypeParameters(ASTReferenceType node, TreeSet<String> missingTypes)
	{
		Node child = node.jjtGetChild(0);
		
		if( child instanceof ASTClassOrInterfaceType )
			updateTypeParameters( (ASTClassOrInterfaceType) child, missingTypes );
		
		List<Integer> dimensions = node.getArrayDimensions();
		
		if( dimensions.size() == 0 )
			node.setType(child.getType());
		else
			node.setType(new ArrayType(child.getType(), dimensions));	
	}
	
	
	// updates type parameters inside of extends and implements lists (and bounds lists)
	private void updateTypeParameters(ASTClassOrInterfaceType node, TreeSet<String> missingTypes)
	{
		String typeName = node.getImage();	
		ClassInterfaceBaseType type = lookupType(typeName); //retrieve the type without type parameters
		
		if(type == null)
		{
			addError(node, Error.UNDEF_TYP, typeName);			
			type = Type.UNKNOWN;
			missingTypes.add(typeName);
		}
		else
		{	
			//Container<T, List<String>, String, Thing<K>>.Stuff<U>		
			
			ClassInterfaceBaseType current = type;
			ClassInterfaceBaseType next = null;
			
			// walk backwards up the type, snapping up parameters
			// we go backwards because we need to set outer types
			for( int i = node.jjtGetNumChildren() - 1; i >= 0; i-- )
			{
				Node child = node.jjtGetChild(i);				
				if( child instanceof ASTClassOrInterfaceTypeSuffix  )
				{					
					if( child.jjtGetNumChildren() > 0 ) //has type parameters
					{						
						ASTTypeArguments typeArguments = (ASTTypeArguments) child.jjtGetChild(0);
						SequenceType arguments = new SequenceType();
						for( int j = 0; j < typeArguments.jjtGetNumChildren(); j++ )
						{
							ASTType argument = (ASTType) (typeArguments.jjtGetChild(j));
							//recursively update the type parameters of the type parameters...							
							updateTypeParameters( argument, missingTypes );
							
							arguments.add(argument);
						}						
						typeArguments.setType(arguments);					
						
						//InstantiatedType instantiatedType = new InstantiatedType(current, arguments);
						ClassInterfaceBaseType instantiatedType = current.replace(current.getTypeParameters(), arguments);
						//child.setType(instantiatedType.getInstantiatedType());
						child.setType(instantiatedType);
						if( i == node.jjtGetNumChildren() - 1 )
							type = instantiatedType;								
						
						if( next != null )							
							next.setOuter(instantiatedType); //should only happen if next is an instantiated type too
						
						next = instantiatedType;
						current = instantiatedType.getOuter();
					}
				}				
			}			
		}
		
		//reset the type now that it has type parameters 
		node.setType(type);		
	}

	

	/**
	 * Adds type parameters to declarations and checks that the same parameter is not added multiple times.
	 * It must be called before field and method checking (since other classes may be dependent on the information).
	 * It must be called after all the types have been collected, otherwise it may depend on unknown types.
	 * @param parentType
	 * @param parameters
	 * @param missingTypes
	 */
	

	public void processTypeParameter(Type parentType, ASTTypeParameter node, TreeSet<String> missingTypes)
	{		
		String symbol = node.getImage();
		TypeParameter typeParameter = new TypeParameter(symbol);		
		node.setType(typeParameter);
		
		
		if( parentType.isParameterized() )
			for( ModifiedType existing : parentType.getTypeParameters() )
				if( existing.getType().getTypeName().equals( typeParameter.getTypeName() ) )
					addError( node, Error.MULT_SYM, "Multiply defined type parameter " + existing.getType().getTypeName() );
		
		parentType.addTypeParameter(node);
		
		if( node.jjtGetNumChildren() > 0 )
		{
			ASTTypeBound bound = (ASTTypeBound)(node.jjtGetChild(0));				
			for( int i = 0; i < bound.jjtGetNumChildren(); i++ )
			{
				processTypeBound( bound, missingTypes );
				typeParameter.addBound((ClassInterfaceBaseType)bound.jjtGetChild(i).getType());
			}
		}
	}


	private void processTypeBound(ASTTypeBound bound, TreeSet<String> missingTypes)
	{	
		currentMethod.clear();		
		
		for( int i = 0; i < bound.jjtGetNumChildren(); i++ )
		{
			ASTClassOrInterfaceType child = (ASTClassOrInterfaceType) bound.jjtGetChild(i); //must be ASTClassOrInterfaceType			
			updateTypeParameters(child, missingTypes);
		}		
	}

	
	
	public void buildTypes( Map<String, Node> files ) throws ShadowException
	{
		ASTWalker walker = new ASTWalker(this);
		
		//walk over all the files
		for( Node node : files.values() )								
			walker.walk(node);
		
		updateTypeParameters();
		
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
					
					
					
					//check for circular class hierarchy				
					ClassType parent = classType.getExtendType();
					HashSet<ClassType> hierarchy = new HashSet<ClassType>();
					hierarchy.add(classType);
					
					boolean circular = false;
					
					while( parent != null && !circular )
					{
						if( hierarchy.contains(parent) )	
						{
							addError(Error.INVL_TYP, "Circular type hierarchy for class " + type );
							circular = true;
						}
						else
							hierarchy.add(parent);
						parent = parent.getExtendType();
					}
	
					if( !circular )
					{
						//check to see if all interfaces are satisfied
						for( ClassInterfaceBaseType _interface : classType.getInterfaces() )
						{
							InterfaceType interfaceType = (InterfaceType) _interface;
							
							//check for circular interface issues first
							if( interfaceType.isCircular() )
								addError(Error.INVL_TYP, "Interface " + _interface + " has a circular extends hierarchy" );
							else if( !classType.satisfiesInterface(interfaceType) )
								addError(Error.INVL_TYP, "Type " + classType + " does not implement interface " + _interface );					
						}
											
						/* Check overridden methods to make sure:
						 * 1. All overrides match exactly  (if it matches everything but return type.... trouble!)
						 * 2. No final methods have been overridden
						 * 3. Immutable methods cannot be overridden by mutable methods
						 * 4. No overridden methods have been narrowed in access 
						 */			
						parent = classType.getExtendType();
						if( parent != null)
						{
							for( List<MethodSignature> signatures : classType.getMethodMap().values() )					
								for( MethodSignature signature : signatures )
								{
									if( parent.recursivelyContainsIndistinguishableMethod(signature) && !signature.isCreate() )
									{
										MethodSignature parentSignature = parent.recursivelyGetIndistinguishableMethod(signature);
										Node parentNode = parentSignature.getNode();
										Node node = signature.getNode();
										Modifiers parentModifiers;
										Modifiers modifiers;
										
										if( parentNode == null )
											parentModifiers = new Modifiers();
										else
											parentModifiers = parentNode.getModifiers();
										
										if( node == null )
											modifiers = new Modifiers();
										else
											modifiers = node.getModifiers();									
										
										if( !parentSignature.getReturnTypes().canAccept(signature.getReturnTypes()) )
											addError( parentNode, "Overriding method " + signature + " differs only by return type from " + parentSignature );
										else if( parentModifiers.isFinal() )
											addError( parentNode, "Method " + signature + " cannot override final method" );
										else if( !modifiers.isImmutable() && parentModifiers.isImmutable()  )
											addError( parentNode, "Non-immutable method " + signature + " cannot override immutable method" );
										else if( parentModifiers.isPublic() && (modifiers.isPrivate() || modifiers.isProtected()) )
											addError( parentNode, "Overriding method " + signature + " cannot reduce visibility of public method " + parentSignature );
										else if( parentModifiers.isProtected() && modifiers.isPrivate()  )
											addError( parentNode, "Overriding method " + signature + " cannot reduce visibility of protected method " + parentSignature );									
									}
								}
						}
					}				
				}
			}
		}
		
		
		//produce meta files
		if( getErrorCount() == 0 ) // no errors
		{
			for( Map.Entry<String, Node> entry : files.entrySet() )
			{
				Node node = entry.getValue();
				String file = entry.getKey();
				try
				{					
					File shadowVersion = new File( file + ".shadow");
					File metaVersion = new File( file + ".meta");
					//add meta file if one doesn't exist
					if( !metaVersion.exists() || (shadowVersion.exists() && shadowVersion.lastModified() >= metaVersion.lastModified()) )  
					{	
						PrintWriter out = new PrintWriter(metaVersion);
						ClassInterfaceBaseType type = (ClassInterfaceBaseType) node.getType();
						type.printMetaFile(out, "");
						out.close();						
					}
				}
				catch(IOException e)
				{
					System.err.println("Failed to create meta file for " + node.getType() );					
				}
			}
		}
		
	}
	
	//Important!  Set the current type on entering the body, not the declaration, otherwise extends and imports are improperly checked with the wrong outer class
	public Object visit(ASTClassOrInterfaceBody node, Boolean secondVisit) throws ShadowException {		
		if( secondVisit )
			currentType = currentType.getOuter();		
		else
			currentType = declarationType; //get type from declaration
			
		return WalkType.POST_CHILDREN;
	}
	
	//But we still need to know what type we're in to sort out type parameters in the extends list
	//declarationType will differ from current type only before the body (extends list, implements list)
	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit) throws ShadowException {		
		if( secondVisit )
		{
			declarationType = declarationType.getOuter();
			
			
			
			
		}
		else
			declarationType = (ClassInterfaceBaseType)node.getType();
			
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
	
	@Override
	public ClassInterfaceBaseType lookupType( String name ) //only addition to base checker is resolving type parameters
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
	
	public Object visit(ASTClassOrInterfaceType node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;

		
		String typeName = node.getImage();
		ClassInterfaceBaseType type = lookupType(typeName);
		
		if(type == null)
		{
			addError(node, Error.UNDEF_TYP, typeName);
			type = Type.UNKNOWN;
		}
		else
		{
			if (currentType instanceof ClassType)
				((ClassType)currentType).addReferencedType(type);
			/*
			if( currentType == declarationType ) //doesn't happen in the extends list and implements list
			{
				ClassInterfaceBaseType current = type;
				ClassInterfaceBaseType next = null;
				
				//walk backwards up the type, snapping up parameters
				//we go backwards because we need to set outer types
				for( int i = node.jjtGetNumChildren() - 1; i >= 0; i-- )
				{
					Node child = node.jjtGetChild(i);				
					if( child instanceof ASTClassOrInterfaceTypeSuffix  )
					{					
						if( child.jjtGetNumChildren() > 0 ) //has type parameters
						{						
							if( current.isParameterized() )
							{
								SequenceType arguments = (SequenceType)(child.jjtGetChild(0).getType());
								SequenceType parameters = current.getTypeParameters();							
								if( parameters.canAccept(arguments) )
								{
									ClassInterfaceBaseType instantiatedType = current.replace(parameters, arguments);
									child.setType(instantiatedType);
									if( i == node.jjtGetNumChildren() - 1 )
										type = instantiatedType;								
									
									if( next != null )							
										next.setOuter(instantiatedType); //should only happen if next is an instantiated type too
									
									next = instantiatedType;
									current = instantiatedType.getOuter();
								}
								else
								{
									addError( child, Error.TYPE_MIS, "Type arguments " + arguments + " do not match type parameters " + parameters );
									break;
								}
							}
							else
							{
								addError( child, Error.TYPE_MIS, "Cannot instantiate type parameters for non-parameterized type: " + current);
								break;
							}
						}
					}				
				}	
			}
			*/
		}
				
		node.setType(type);
		
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
			
			node.addModifier(Modifiers.FINAL);
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTBlock node, Boolean secondVisit) throws ShadowException {
		return WalkType.POST_CHILDREN;
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
				
				
				if( currentClass.containsField(signature.getSymbol()) )
				{
					addError(declaration, Error.MULT_SYM, "First declared on line " + currentClass.getField(signature.getSymbol()).getLine() );
					return false;				
				}
				
				if( currentClass.containsInnerClass(signature.getSymbol() ) )
				{
					addError(declaration, Error.MULT_SYM );
					return false;
				}
				
				// add the method to the current type
				currentClass.addMethod(declaration.getImage(), signature);
				
//				ASTUtils.DEBUG("ADDED METHOD: " + signature.toString());
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
	public Object visit(ASTFunctionType node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		MethodType ret = new MethodType(); // it has no name
		
		// add all the parameters to this method
		int i;
		for(i=0; i < node.jjtGetNumChildren(); ++i) {
			Node curNode = node.jjtGetChild(i);
			
			// check to see if we've moved on to the result types
			if(curNode instanceof ASTResultTypes)
				break;
			
			if(curNode.getType() == null) {
				addError(curNode, Error.UNDEF_TYP, curNode.getImage());
				return ret;	// just return whatever, we should prob throw here
			}
				
			ret.addParameter(curNode);	// add the type as the parameter
		}
		
		// check to see if we have result types
		if(i < node.jjtGetNumChildren()) {
			Node resNode = node.jjtGetChild(i);
			
			for(int r=0; r < resNode.jjtGetNumChildren(); ++r) {
				Node curNode = resNode.jjtGetChild(r);
				Type type = curNode.getType();
				
				if(type == null) {
					addError(curNode.jjtGetChild(0), Error.UNDEF_TYP, curNode.jjtGetChild(0).getImage());
					return ret;	// just return whatever, we should prob throw here
				}
					
				ret.addReturn(curNode);
			}
		}
		
		node.setType(ret);
		
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
	public Object visit(ASTTypeParameters node, Boolean secondVisit)	throws ShadowException
	{		
		Node parent = node.jjtGetParent();
	
		//type parameters for class/interface declarations have already been found in the type collector
		//only add for method declarations
		if( !secondVisit && (parent instanceof ASTMethodDeclarator) )		
		{
			Type declarationType = parent.jjtGetParent().getType(); //declaration is one up from declarator
			declarationType.setParameterized(true);
		}
		
		return WalkType.POST_CHILDREN;
	}

	@Override
	public Object visit(ASTTypeParameter node, Boolean secondVisit)	throws ShadowException
	{
		if( secondVisit )
		{
			TypeParameter typeParameter;
			
			Node grandparent = node.jjtGetParent().jjtGetParent();
			
			//only add generics to method declarations
			if( grandparent instanceof ASTMethodDeclarator )
			{
				if( secondVisit )
				{			
					typeParameter = (TypeParameter)(node.getType());
					if( node.jjtGetNumChildren() > 0 )
					{
						ASTTypeBound bound = (ASTTypeBound)(node.jjtGetChild(0));				
						for( int i = 0; i < bound.jjtGetNumChildren(); i++ )								
							typeParameter.addBound((ClassInterfaceBaseType)(bound.jjtGetChild(i).getType()));				
					}
				}
				else
				{			
					Node declaration = node.jjtGetParent().jjtGetParent().jjtGetParent(); //method declaration is  three levels up
					Type declarationType  = declaration.getType();
					
					String symbol = node.getImage();
					typeParameter = new TypeParameter(symbol);
					
					for( ModifiedType existing : declarationType.getTypeParameters() )
						if( existing.getType().getTypeName().equals( symbol ) )
							addError( node, Error.MULT_SYM, "Multiply defined type parameter " + symbol );
					
					node.setType(typeParameter);
					declarationType.addTypeParameter(node);
				}
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
	

	// Everything below here are just visitors to push up the type
		
	public Object visit(ASTType node, Boolean secondVisit) throws ShadowException { 
		return pushUpType(node, secondVisit); 
	}
	public Object visit(ASTVariableInitializer node, Boolean secondVisit) throws ShadowException { return pushUpType(node, secondVisit); }
}
