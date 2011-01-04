package shadow.typecheck;

import java.util.List;
import java.util.Map;

import shadow.AST.ASTUtils;
import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.ASTClassOrInterfaceType;
import shadow.parser.javacc.ASTConstructorDeclaration;
import shadow.parser.javacc.ASTDestructorDeclaration;
import shadow.parser.javacc.ASTFieldDeclaration;
import shadow.parser.javacc.ASTFunctionType;
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
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.Type;

public class FieldAndMethodChecker extends BaseChecker {	
	
	public FieldAndMethodChecker(boolean debug, Map<String, Type> typeTable, List<String> importList ) {
		super(debug, typeTable, importList);
	}
	
	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit) throws ShadowException {
		//
		// TODO: Fix this as it could be a class, exception or interface
		//       We'll also need to add that to Type so it knows what it is
		//		

		if( secondVisit )		
		{
			if( currentType instanceof ClassType ) //may need to add a default constructor
			{
				ClassType classType = (ClassType)currentType;
	
				//
				// We don't want to do this here... we'll do it in the TAC code
				//
//				if( classType.getMethods("constructor") ==  null )
//					classType.addMethod("constructor", new MethodSignature("constructor", 0, -1)); //negative indicates "magically created"
			}
			
			currentType = (ClassInterfaceBaseType)currentType.getOuter();
		}
		else
		{
			currentType = (ClassInterfaceBaseType)lookupType(node.getImage());
			node.setType(currentType);
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	public boolean checkMemberModifiers( Node node, int modifiers )
	{
		int visibilityModifiers = 0;
		
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
			return false;
		}
		else if( visibilityModifiers == 0 )
		{			
			addError(node, Error.INVL_MOD, "Every method and field must be specified as public, private, or protected" );
			return false;
		}
		
		if( ModifierSet.isWeak(modifiers) && node instanceof ASTMethodDeclaration )
		{			
			addError(node, Error.INVL_MOD, "Methods cannot be declared with the weak modifier" );
			return false;
		}
		
		
		return true;
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
		
		//type.setASTNode(node);	// set the node to the type  //NO! Set the node to the field, not the type
		
		if( currentType instanceof ClassInterfaceBaseType )
		{
			ClassInterfaceBaseType currentClass = (ClassInterfaceBaseType)currentType;
			
			// go through inserting all the idents
			for(int i=1; i < node.jjtGetNumChildren(); ++i) {
				Node child = node.jjtGetChild(i);
				child.setType(type);
				child.setModifiers(node.getModifiers());
				String symbol = child.jjtGetChild(0).getImage();
				
				// make sure we don't already have this symbol
				if(currentClass.containsField(symbol) || currentClass.getMethods(symbol) != null || currentClass.containsInnerClass(symbol) )
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
	
	public Object visit(ASTClassOrInterfaceType node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;

		String typeName = node.getImage();
		Type type = lookupType(typeName);
		
		if(type == null) {
			addError(node, Error.UNDEF_TYP, typeName);
			return WalkType.NO_CHILDREN;
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
		MethodSignature signature = new MethodSignature(declaration.getImage(), node.getModifiers(), node.getLine(), node);
				
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
			// make sure we don't already have this method
			if( currentClass.containsMethod(signature) )
			{				
				// get the first signature
				MethodSignature firstMethod = currentClass.getMethodSignature(signature);				
				addError(declaration, Error.MULT_MTH, "First declared on line " + firstMethod.getLineNumber());
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
			
			ASTUtils.DEBUG("ADDED METHOD: " + signature.toString());			
			
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
	
	//
	// Everything below here are just visitors to push up the type
	//
	public Object visit(ASTResultType node, Boolean secondVisit) throws ShadowException { return pushUpType(node, secondVisit); }
	public Object visit(ASTType node, Boolean secondVisit) throws ShadowException { return pushUpType(node, secondVisit); }
	public Object visit(ASTVariableInitializer node, Boolean secondVisit) throws ShadowException { return pushUpType(node, secondVisit); }
	public Object visit(ASTVariableDeclarator node, Boolean secondVisit) throws ShadowException { return pushUpType(node, secondVisit); }
}
