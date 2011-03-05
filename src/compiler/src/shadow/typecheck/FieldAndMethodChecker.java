package shadow.typecheck;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import shadow.AST.ASTWalker;
import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.ASTClassOrInterfaceBody;
import shadow.parser.javacc.ASTClassOrInterfaceType;
import shadow.parser.javacc.ASTCompilationUnit;
import shadow.parser.javacc.ASTConstructorDeclaration;
import shadow.parser.javacc.ASTDestructorDeclaration;
import shadow.parser.javacc.ASTFieldDeclaration;
import shadow.parser.javacc.ASTFunctionType;
import shadow.parser.javacc.ASTImportDeclaration;
import shadow.parser.javacc.ASTMethodDeclaration;
import shadow.parser.javacc.ASTMethodDeclarator;
import shadow.parser.javacc.ASTReferenceType;
import shadow.parser.javacc.ASTResultType;
import shadow.parser.javacc.ASTResultTypes;
import shadow.parser.javacc.ASTType;
import shadow.parser.javacc.ASTVariableDeclarator;
import shadow.parser.javacc.ASTVariableInitializer;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.ShadowParser.ModifierSet;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassInterfaceBaseType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.Type;

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
					//if no constructors, add the default one
					classType.addMethod("constructor", new MethodSignature( "constructor", 0, null ) );
					//note that the node is null for the default constructor, because nothing was made
					
					
					
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
											addError( parentNode, "Overrding method " + signature + " cannot reduce visibility of public method " + parentSignature );
										else if( ModifierSet.isProtected(parentModifiers) && ModifierSet.isPrivate(modifiers)  )
											addError( parentNode, "Overriding method " + signature + " cannot reduce visibility of protected method " + parentSignature );									
									}
								}
						}
					}				
				}
			}
		}
		
		
		/*  Don't think this is right.  We shouldn't lump all the parent fields and methods into the class. 
		 *  We can retrieve stuff when we need it and keep the class hierarchies unmixed. 
		for( Type type : typeTable.values() ) 
		{
			if( type instanceof ClassType ) //adds all parent fields and methods and check for compliance with all interfaces
			{
				ClassType classType = (ClassType) type;
				ClassType parent = classType.getExtendType();				
				
				while( parent != null && parent != classType )
				{	
					Map<String,Node> fields = parent.getFields();
					
					for( String name : fields.keySet() )
					{
						if( !classType.containsField(name) ) //add parent fields to current class if it doesn't have them already
						{
							Node node = fields.get(name);
							if( !ModifierSet.isPrivate(node.getModifiers() ) ) //private fields aren't added
								classType.addField(name, node);
						}
					}
					
					Map< String,List<MethodSignature> > methods = parent.getMethodMap();
					
					for( String name : methods.keySet() )
					{
						HashSet<MethodType> methodsInClass = new HashSet<MethodType>();
						List<MethodSignature> signatures = classType.getMethods(name);
						if( signatures != null )
							for( MethodSignature signature : signatures  )
								methodsInClass.add(signature.getMethodType());						
						
						signatures = methods.get(name);
						if( signatures != null )
							for( MethodSignature parentSignature : signatures ) //add parent methods to current class
							{
								MethodType parentMethod = parentSignature.getMethodType();
								if( !methodsInClass.contains(parentMethod) && !ModifierSet.isPrivate(parentMethod.getModifiers()) )
								//only if it doesn't have it already and it's not private
								{
									classType.addMethod(name, parentSignature);
									methodsInClass.add(parentMethod); //to avoid double adds, but should be unnecessary
								}
							}
					}
					
					parent = parent.getExtendType();
				}
				
				if( parent == classType )				
					addError(Error.INVL_TYP, "Circular type hierarchy for class " + type );
				
				//Meets all interface requirements?
				for( InterfaceType _interface : classType.getInterfaces() )
				{
					if( !classType.satisfiesInterface(_interface) )
						addError(Error.INVL_TYP, "Type " + classType + " does not implement interface " + _interface );					
				}
			}
		}	
		
		*/
	}
	
	
//	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit) throws ShadowException {
//		//
//		// TODO: Fix this as it could be a class, exception or interface
//		//       We'll also need to add that to Type so it knows what it is
//		//		
//
//		if( secondVisit )		
//		{
//			if( currentType instanceof ClassType ) //may need to add a default constructor
//			{
//				ClassType classType = (ClassType)currentType;
//	
//				//
//				// We don't want to do this here... we'll do it in the TAC code
//				//
////				if( classType.getMethods("constructor") ==  null )
////					classType.addMethod("constructor", new MethodSignature("constructor", 0, -1)); //negative indicates "magically created"
//			}
//			
//			currentType = (ClassInterfaceBaseType)currentType.getOuter();
//		}
//		else
//		{
//			currentType = node.getType();
//		}
//		
//		return WalkType.POST_CHILDREN;
//	}
	
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
		if(type == null) {
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
		Type type = lookupType(typeName);
		
		if(type == null)
		{
			addError(node, Error.UNDEF_TYP, typeName);
			type = Type.UNKNOWN;
		}
				
		node.setType(type);
		
		return WalkType.POST_CHILDREN;
	}
	
	/**
	 * Adds a method to the current type.
	 */
	public Object visit(ASTMethodDeclaration node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		Node methodDec = node.jjtGetChild(0);
		
		createMethod( methodDec, node  ); //different nodes used for modifiers and signature
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTConstructorDeclaration node, Boolean secondVisit) throws ShadowException {		
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		createMethod( node, node  ); //constructor uses the same node for modifiers and signature		
		
		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTDestructorDeclaration node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		createMethod( node, node  ); //destructor uses the same node for modifiers and signature

		return WalkType.POST_CHILDREN;
	}
	
	
	
	private boolean createMethod( Node declaration, Node node )
	{
		MethodSignature signature = new MethodSignature(declaration.getImage(), node.getModifiers(), node);
				
		if( !checkMemberModifiers( node, node.getModifiers() ))
			return false;
		
		if( declaration instanceof ASTMethodDeclarator || declaration instanceof ASTConstructorDeclaration )			
			// check the parameters
			if(!visitParameters(declaration.jjtGetChild(0), signature))
				return false;
		
		// check to see if we have return types
		if(declaration instanceof ASTMethodDeclarator && declaration.jjtGetNumChildren() == 2)
		{
			Node retTypes = declaration.jjtGetChild(1);
			
			for(int i=0; i < retTypes.jjtGetNumChildren(); ++i) {
				Type type = retTypes.jjtGetChild(i).getType();
				
				// make sure the return type is in the type table
				if(type == null)
				{
					addError(retTypes.jjtGetChild(i), Error.UNDEF_TYP);
					return false;
				}
					
				// add the return type to our signature
				signature.addReturn(type);
			}
		}
		
		node.setType(signature.getMethodType());
		node.setEnclosingType(currentType);
		
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
			
//			ASTUtils.DEBUG("ADDED METHOD: " + signature.toString());			
			
			return true;
		}
		else
		{
			addError(node, "Cannot add method to a structure that is not a class, interface, error, enum, or exception");
			return false;
		}
	}
	
	
	
	
	public boolean visitParameters(Node params, MethodSignature signature) {
		// go through all the formal parameters
		for(int i=0; i < params.jjtGetNumChildren(); ++i) {
			Node param = params.jjtGetChild(i);
			
			// get the name of the parameter
			String paramSymbol = param.jjtGetChild(1).getImage();
			
			// check if it's already in the set of parameter names
			if(signature.containsParam(paramSymbol)) {
				addError(param.jjtGetChild(1), Error.MULT_SYM, "In parameter names");
				return false;	// we're done with this node
			}
			
			// get the type of the parameter
			param.setType(param.jjtGetChild(0).getType());
			
			// make sure this type is in the type table
			if(param.getType() == null)
			{
				addError(param.jjtGetChild(0), Error.UNDEF_TYP);
				return false;
			}
				
			// add the parameter type to the signature
			signature.addParameter(paramSymbol, param);
		}
		
		return true;
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
				Type type = resNode.jjtGetChild(r).getType();
				
				if(type == null) {
					addError(curNode.jjtGetChild(0), Error.UNDEF_TYP, curNode.jjtGetChild(0).getImage());
					return ret;	// just return whatever, we should prob throw here
				}
					
				ret.addReturn(type);
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
	
	//
	// Everything below here are just visitors to push up the type
	//
	public Object visit(ASTResultType node, Boolean secondVisit) throws ShadowException { return pushUpType(node, secondVisit); }
	public Object visit(ASTType node, Boolean secondVisit) throws ShadowException { return pushUpType(node, secondVisit); }
	public Object visit(ASTVariableInitializer node, Boolean secondVisit) throws ShadowException { return pushUpType(node, secondVisit); }
	public Object visit(ASTVariableDeclarator node, Boolean secondVisit) throws ShadowException { return pushUpType(node, secondVisit); }
}
