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
import shadow.parser.javacc.ASTReferenceType;
import shadow.parser.javacc.ASTResultType;
import shadow.parser.javacc.ASTResultTypes;
import shadow.parser.javacc.ASTType;
import shadow.parser.javacc.ASTVariableDeclarator;
import shadow.parser.javacc.ASTVariableInitializer;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
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
		type.setASTNode(node);	// set the node to the type
		
		if( currentType instanceof ClassInterfaceBaseType )
		{
			ClassInterfaceBaseType currentClass = (ClassInterfaceBaseType)currentType;
			
			// go through inserting all the idents
			for(int i=1; i < node.jjtGetNumChildren(); ++i) {
				String symbol = node.jjtGetChild(i).jjtGetChild(0).getImage();
				
				// make sure we don't already have this symbol
				if(currentClass.containsField(symbol)) {
					addError(node.jjtGetChild(i).jjtGetChild(0), Error.MULT_SYM, symbol);
					return WalkType.NO_CHILDREN;
				}
				
				currentClass.addField(symbol, type);
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
		MethodSignature signature = new MethodSignature(methodDec.getImage(), node.getModifiers(), node.getLine());
		
		signature.setASTNode(node);	// set the node for this method
		
		// check the parameters
		if(!visitParameters(methodDec.jjtGetChild(0), signature))
			return WalkType.NO_CHILDREN;
		
		// check to see if we have return types
		if(methodDec.jjtGetNumChildren() == 2) {
			Node retTypes = methodDec.jjtGetChild(1);
			
			for(int i=0; i < retTypes.jjtGetNumChildren(); ++i) {
				Type type = retTypes.jjtGetChild(i).getType();
				
				// make sure the return type is in the type table
				if(type == null) {
					addError(retTypes.jjtGetChild(i), Error.UNDEF_TYP);
					return WalkType.NO_CHILDREN;
				}
					
				// add the return type to our signature
				signature.addReturn(type);
			}
		}
		
		node.setType(signature.getMethodType());
		
		if( currentType instanceof ClassInterfaceBaseType )
		{
			ClassInterfaceBaseType currentClass = (ClassInterfaceBaseType)currentType; 
			// make sure we don't already have this method
			if(currentClass.containsMethod(signature)) {
				
				// get the first signature
				MethodSignature firstMethod = currentClass.getMethodSignature(signature);
				
				addError(methodDec, Error.MULT_MTH, "First declared on line " + firstMethod.getLineNumber());
				return WalkType.NO_CHILDREN;
			}
			
			// add the method to the current type
			currentClass.addMethod(methodDec.getImage(), signature);
		}
		else
		{
			addError(node, "Cannot add method to a structure that is not a class, interface, error, enum, or exception");
			return WalkType.NO_CHILDREN;
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	
	public Object visit(ASTConstructorDeclaration node, Boolean secondVisit) throws ShadowException {		
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		MethodSignature signature = new MethodSignature("constructor", node.getModifiers(), node.getLine());		
		visitParameters(node.jjtGetChild(0), signature);

		ASTUtils.DEBUG("ADDED METHOD: " + signature.toString());
		
		node.setType(signature.getMethodType());

		// add the method to the current type
		ClassInterfaceBaseType currentClass = (ClassInterfaceBaseType)currentType;
		currentClass.addMethod("constructor", signature);

		return WalkType.POST_CHILDREN;
	}
	
	public Object visit(ASTDestructorDeclaration node, Boolean secondVisit) throws ShadowException {
		if(!secondVisit)
			return WalkType.POST_CHILDREN;
		
		MethodSignature signature = new MethodSignature("destructor", node.getModifiers(), node.getLine());

		ASTUtils.DEBUG("ADDED METHOD: " + signature.toString());

		// add the method to the current type
		ClassInterfaceBaseType currentClass = (ClassInterfaceBaseType)currentType;
		currentClass.addMethod("destructor", signature);

		return WalkType.POST_CHILDREN;
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
			Type type = param.jjtGetChild(0).getType();
			
			// make sure this type is in the type table
			if(type == null) {
				addError(param.jjtGetChild(0), Error.UNDEF_TYP);
				return false;
			}
				
			// add the parameter type to the signature
			signature.addParameter(paramSymbol, type);
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
			Type type = curNode.getType();
			
			// check to see if we've moved on to the result types
			if(curNode instanceof ASTResultTypes)
				break;
			
			if(type == null) {
				addError(curNode, Error.UNDEF_TYP, curNode.getImage());
				return ret;	// just return whatever, we should prob throw here
			}
				
			ret.addParameter(type);	// add the type as the parameter
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
