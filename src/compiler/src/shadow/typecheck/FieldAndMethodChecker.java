package shadow.typecheck;

import java.io.File;
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
import shadow.parser.javacc.ASTClassOrInterfaceTypeSuffix;
import shadow.parser.javacc.ASTCompilationUnit;
import shadow.parser.javacc.ASTConstructorDeclaration;
import shadow.parser.javacc.ASTDestructorDeclaration;
import shadow.parser.javacc.ASTFieldDeclaration;
import shadow.parser.javacc.ASTFormalParameters;
import shadow.parser.javacc.ASTFunctionType;
import shadow.parser.javacc.ASTImportDeclaration;
import shadow.parser.javacc.ASTLiteral;
import shadow.parser.javacc.ASTMethodDeclaration;
import shadow.parser.javacc.ASTMethodDeclarator;
import shadow.parser.javacc.ASTReferenceType;
import shadow.parser.javacc.ASTResultType;
import shadow.parser.javacc.ASTResultTypes;
import shadow.parser.javacc.ASTType;
import shadow.parser.javacc.ASTTypeArgument;
import shadow.parser.javacc.ASTTypeArguments;
import shadow.parser.javacc.ASTTypeBound;
import shadow.parser.javacc.ASTTypeParameter;
import shadow.parser.javacc.ASTTypeParameters;
import shadow.parser.javacc.ASTVariableInitializer;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.ShadowParser.ModifierSet;
import shadow.parser.javacc.SignatureNode;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassInterfaceBaseType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InstantiatedType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;

public class FieldAndMethodChecker extends BaseChecker {
		
	public FieldAndMethodChecker(boolean debug, HashMap<Package, HashMap<String, Type>> typeTable, List<File> importList, Package packageTree ) {
		super(debug, typeTable, importList, packageTree );		
	}	
	
	public void buildTypes( Map<File, Node> files ) throws ShadowException
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
					if (classType.getMethods("constructor").isEmpty())
					{
						//if no constructors, add the default one
						ASTConstructorDeclaration constructorNode = new ASTConstructorDeclaration(-1);
						constructorNode.setModifiers(ModifierSet.PUBLIC);
						classType.addMethod("constructor", new MethodSignature( classType, "constructor", 0, constructorNode ) );
						//note that the node is null for the default constructor, because nothing was made
						// - in that case, modifiers ought to be moved to MethodSignature
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
						for( InterfaceType _interface : classType.getInterfaces() )
						{
							//check for circular interface issues first
							if( _interface.isCircular() )
								addError(Error.INVL_TYP, "Interface " + _interface + " has a circular extends hierarchy" );
							else if( !classType.satisfiesInterface(_interface) )
								addError(Error.INVL_TYP, "Type " + classType + " does not implement interface " + _interface );					
						}
											
						/* Check overridden methods to make sure:
						 * 1. All overrides match exactly  (if it matches everything but return type.... trouble!)
						 * 2. No final or immutable methods have been overridden
						 * 3. No changes from static to regular or vice versa
						 * 4. No overridden methods have been narrowed in access 
						 */			
						parent = classType.getExtendType();
						if( parent != null)
						{
							for( List<MethodSignature> signatures : classType.getMethodMap().values() )					
								for( MethodSignature signature : signatures )
								{
									if( parent.recursivelyContainsIndistinguishableMethod(signature) )
									{
										MethodSignature parentSignature = parent.recursivelyGetIndistinguishableMethod(signature);
										Node parentNode = parentSignature.getASTNode();
										Node node = signature.getASTNode();
										int parentModifiers;
										int modifiers;
										
										if( parentNode == null )
											parentModifiers = 0;
										else
											parentModifiers = parentNode.getModifiers();
										
										if( node == null )
											modifiers = 0;
										else
											modifiers = node.getModifiers();									
										
										if( !parentSignature.equals( signature ) )
											addError( parentNode, "Overriding method " + signature + " differs only by return type from " + parentSignature );
										else if( ModifierSet.isFinal(parentModifiers) || ModifierSet.isImmutable(parentModifiers) )
											addError( parentNode, "Method " + signature + " cannot override  final or immutable methods" );
										else if( ModifierSet.isStatic(parentModifiers) && !ModifierSet.isStatic(modifiers) )
											addError( parentNode, "Non-static method " + signature + " cannot override static method " + parentSignature );
										else if( !ModifierSet.isStatic(parentModifiers) && ModifierSet.isStatic(modifiers) )
											addError( parentNode, "Static method " + signature + " cannot override non-static method " + parentSignature );
										else if( ModifierSet.isPublic(parentModifiers) && (ModifierSet.isPrivate(modifiers) || ModifierSet.isProtected(modifiers)) )
											addError( parentNode, "Overriding method " + signature + " cannot reduce visibility of public method " + parentSignature );
										else if( ModifierSet.isProtected(parentModifiers) && ModifierSet.isPrivate(modifiers)  )
											addError( parentNode, "Overriding method " + signature + " cannot reduce visibility of protected method " + parentSignature );									
									}
								}
						}
					}				
				}
			}
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
	
	
	/**
	 * Checks method and field modifiers to see if they are legal
	 * 
	 * @param node
	 * @param modifiers
	 * @return
	 */
	
	public boolean checkMemberModifiers( Node node, int modifiers )
	{
		int visibilityModifiers = 0;
		boolean success = true;
		
		//modifiers are set, but we have to check them
		if( ModifierSet.isPublic( modifiers ))
			visibilityModifiers++;
		if( ModifierSet.isPrivate( modifiers ))
			visibilityModifiers++;
		if( ModifierSet.isProtected( modifiers ))
			visibilityModifiers++;
		
		if( visibilityModifiers > 1 )
		{
			addError(node, Error.INVL_MOD, "Only one public, private, or protected modifier can be used" );
			success = false;
		}		
		
		if( node instanceof ASTMethodDeclaration ) //methods
		{
			if( visibilityModifiers == 0 )
			{			
				addError(node, Error.INVL_MOD, "Every method must be specified as public, private, or protected" );
				success = false;
			}
			
			if( ModifierSet.isWeak(modifiers) ) 
			{			
				addError(node, Error.INVL_MOD, "Methods cannot be declared with the weak modifier" );
				success = false;
			}
			
			if( ModifierSet.isNullable(modifiers) ) 
			{			
				addError(node, Error.INVL_MOD, "Methods cannot be declared with the nullable modifier" );
				success = false;
			}
		}		
		else //fields
		{			
			if( visibilityModifiers == 0 )
			{			
				addError(node, Error.INVL_MOD, "Every field must be specified as public or private" );
				success = false;
			}
			
			if( ModifierSet.isProtected(modifiers) ) 
			{			
				addError(node, Error.INVL_MOD, "Fields cannot be declared with the protected modifier" );
				success = false;
			}
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

		if( !checkMemberModifiers( node, node.getModifiers() ))
			return WalkType.NO_CHILDREN;
		
		node.addModifier(ModifierSet.FIELD);	
		
		if( currentType instanceof ClassInterfaceBaseType )
		{
			ClassInterfaceBaseType currentClass = (ClassInterfaceBaseType)currentType;
			
			// go through inserting all the idents
			for(int i=1; i < node.jjtGetNumChildren(); ++i)
			{
				Node child = node.jjtGetChild(i);
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

				currentClass.addField(symbol, node);
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
	
	public Object visit(ASTClassOrInterfaceType node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;

		String typeName = node.getImage();		
		
		//Type type = findTypeParameter(typeName); 
				
		//if( type == null )
		Type type = lookupType(typeName);
		
		if(type == null)
		{
			addError(node, Error.UNDEF_TYP, typeName);
			type = Type.UNKNOWN;
		}
		else
		{
			if (currentType instanceof ClassType)
				((ClassType)currentType).addReferencedType(type);
			
			Type current;
			Type next = null;
			
			//walk backwards up the type, snapping up parameters
			for( int i = node.jjtGetNumChildren() - 1; i >= 0; i-- )
			{
				Node child = node.jjtGetChild(i);
				current = child.getType();
				if( child instanceof ASTClassOrInterfaceTypeSuffix  )
				{
					if( child.jjtGetNumChildren() > 0 ) //has type parameters
					{
						//COME BACK HERE
						if( current.isParameterized() )
						{
							SequenceType arguments = (SequenceType)(child.jjtGetChild(0).getType());
							List<TypeParameter> parameters = current.getParameters();
							if( checkTypeArguments( parameters, arguments ) )
							{
								InstantiatedType instantiatedType = new InstantiatedType(current, arguments);
								child.setType(instantiatedType);
								if( next != null )							
									next.setOuter(instantiatedType); //should only happen if next is an instantiated type too
							}
							else
								addError( child, Error.TYPE_MIS, "Type arguments " + arguments + " do not match type parameters " + parameters );
						}
						else
							addError( child, Error.TYPE_MIS, "Cannot instantiate type parameters for non-parameterized type: " + current);
					}
				}				
			}
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
		return visitMethod( methodDeclarator, node, secondVisit );
	}
	
	public Object visit(ASTConstructorDeclaration node, Boolean secondVisit) throws ShadowException {		
		//constructor uses the same node for modifiers and signature
		return visitMethod( node, node, secondVisit );
	}
	
	public Object visit(ASTDestructorDeclaration node, Boolean secondVisit) throws ShadowException {	
		//destructor uses the same node for modifiers and signature
		return visitMethod( node, node, secondVisit );		
	}
	
	public Object visit(ASTBlock node, Boolean secondVisit) throws ShadowException {
		return WalkType.NO_CHILDREN;
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
					addError(declaration, Error.MULT_MTH, "Indistinguishable method already declared on line " + method.getLineNumber());
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
			
			currentMethod = null;
		}
		else
		{
			signature = new MethodSignature( currentType, declaration.getImage(), node.getModifiers(), node);
			node.setMethodSignature(signature);
			MethodType methodType = signature.getMethodType();
			node.setType(methodType);
			node.setEnclosingType(currentType);
			checkMemberModifiers( node, node.getModifiers() );
			currentMethod = node;
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
			
			// go through all the formal parameters
			for(int i=0; i < node.jjtGetNumChildren(); ++i) {
				Node parameter = node.jjtGetChild(i);
				
				// get the name of the parameter
				String paramSymbol = parameter.jjtGetChild(1).getImage();
				
				// check if it's already in the set of parameter names
				if(signature.containsParam(paramSymbol)) {
					addError(parameter.jjtGetChild(1), Error.MULT_SYM, "In parameter names");
					return false;	// we're done with this node
				}				
				
				Node child = parameter.jjtGetChild(0);			
				
				// get the type of the parameter
				parameter.setType(child.getType());
				
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
	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit) throws ShadowException {
		createTypeParameterScope(secondVisit); //scope is created purely to hold type parameters	
		return WalkType.POST_CHILDREN;			
	}
	
	@Override
	public Object visit(ASTTypeParameters node, Boolean secondVisit)	throws ShadowException
	{
		//type parameters occur only in class/interface declarations and method declarations		
		if( secondVisit )
		{				
			Type parentType = node.jjtGetParent().getType();
			
			for( int i = 0; i < node.jjtGetNumChildren(); i++ )
			{
				TypeParameter parameter = (TypeParameter)(node.jjtGetChild(i).getType());
				for( TypeParameter existing : parentType.getParameters() )
					if( existing.getTypeName().equals( parameter.getTypeName() ) )
						addError( node, Error.MULT_SYM, "Multiply defined type parameter " + existing.getTypeName() );
				
				parentType.addParameter(parameter);
			}
			
			parentType.setParameterized(true);
		}
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTClassOrInterfaceTypeSuffix node, Boolean secondVisit)	throws ShadowException
	{

		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTTypeParameter node, Boolean secondVisit)	throws ShadowException
	{
		//type parameters occur only in class/interface declarations and method declarations
		//in either case, a new scope is created that only holds type parameters
		//(and perhaps method arguments)
		TypeParameter typeParameter;
		if( secondVisit )
		{			
			typeParameter = (TypeParameter)(node.getType());
			if( node.jjtGetNumChildren() > 0 )
			{
				ASTTypeBound bound = (ASTTypeBound)(node.jjtGetChild(0));				
				for( int i = 0; i < bound.jjtGetNumChildren(); i++ )								
					typeParameter.addBound(bound.jjtGetChild(i).getType());				
			}
		}
		else
		{
			String symbol = node.getImage();
			typeParameter = new TypeParameter(symbol);
			//addTypeParameter( symbol, node );
			node.setType(typeParameter);
		}
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTResultType node, Boolean secondVisit) throws ShadowException
	{
		if( secondVisit )
			if( node.jjtGetNumChildren() > 0 )
			{
				Node child = node.jjtGetChild(0); 
				node.setType(child.getType());			
			}
		
		return WalkType.POST_CHILDREN;	
	}
	
	
	
	//ASTTypeArguments Appears in: 
	//ConstructorInvocation()
	//ClassOrInterfaceTypeSuffix()
	//ArrayAllocation()
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
	
	
	//
	// Everything below here are just visitors to push up the type
	//	
	public Object visit(ASTTypeArgument node, Boolean secondVisit) throws ShadowException { return pushUpType(node, secondVisit); }
	public Object visit(ASTType node, Boolean secondVisit) throws ShadowException { return pushUpType(node, secondVisit); }
	public Object visit(ASTVariableInitializer node, Boolean secondVisit) throws ShadowException { return pushUpType(node, secondVisit); }
	//public Object visit(ASTVariableDeclarator node, Boolean secondVisit) throws ShadowException { return pushUpType(node, secondVisit); }
}
