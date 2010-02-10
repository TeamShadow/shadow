package shadow.typecheck;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.ASTConstructorDeclaration;
import shadow.parser.javacc.ASTDestructorDeclaration;
import shadow.parser.javacc.ASTFieldDeclaration;
import shadow.parser.javacc.ASTFunctionType;
import shadow.parser.javacc.ASTMethodDeclaration;
import shadow.parser.javacc.ASTResultType;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.ClassInterfaceBaseType;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.Type;

public class TypeBuilder extends BaseChecker {	
	
	public TypeBuilder(boolean debug, Map<String, Type> typeTable, List<String> importList ) {
		super(debug, typeTable, importList);
	}
	
	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit) throws ShadowException {
		//
		// TODO: Fix this as it could be a class, exception or interface
		//       We'll also need to add that to Type so it knows what it is
		//
		
		// For now we punt and assume everything is a class
		if( secondVisit )		
			currentType = (ClassInterfaceBaseType)currentType.getOuter();
		else					
			currentType = (ClassInterfaceBaseType)lookupType(node.getImage());
		
		// insert our new type into the table
		//typeTable.put(node.getImage(), currentType);
		
		return WalkType.POST_CHILDREN;
	}

	/**
	 * Add the field declarations.
	 */
	public Object visit(ASTFieldDeclaration node, Boolean secondVisit) throws ShadowException {
		// a field dec has a type followed by 1 or more idents
		Type type = lookupType(node.jjtGetChild(0).jjtGetChild(0).getImage());
		
		// make sure we have this type
		if(type == null) {
			addError(node.jjtGetChild(0).jjtGetChild(0), Error.UNDEF_TYP, node.jjtGetChild(0).jjtGetChild(0).getImage());
			return WalkType.NO_CHILDREN;
		}
		
		node.setType(type);	// set this node's type
		
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
				
				DEBUG("ADDING: " + type + " " + symbol);
				
				currentClass.addField(symbol, type);
			}
		}
		else
		{
			addError(node, "Cannot add field to a structure that is not a class, interface, error, enum, or exception");
			return WalkType.NO_CHILDREN;
		}
			
		return WalkType.NO_CHILDREN;	// only need the symbols, no type-checking yet
	}

	/**
	 * Adds a method to the current type.
	 */
	public Object visit(ASTMethodDeclaration node, Boolean secondVisit) throws ShadowException {		
		Node methodDec = node.jjtGetChild(0);
		MethodSignature signature = new MethodSignature(methodDec.getImage(), node.getModifiers(), node.getLine());
		
		// check the parameters
		if(!visitParameters(methodDec.jjtGetChild(0), signature))
			return WalkType.NO_CHILDREN;
		
		// check to see if we have return types
		if(methodDec.jjtGetNumChildren() == 2) {
			Node retTypes = methodDec.jjtGetChild(1);
			
			for(int i=0; i < retTypes.jjtGetNumChildren(); ++i) {
				Node retNode = retTypes.jjtGetChild(i).jjtGetChild(0).jjtGetChild(0);
				
				if(retNode instanceof ASTFunctionType) {
					signature.addReturn(createMethodType((ASTFunctionType)retNode));
				} else {
					String retTypeName = retNode.getImage();
					Type retType = lookupType(retTypeName);

					// make sure the return type is in the type table
					if(retType == null) {
						addError(retNode, Error.UNDEF_TYP, retTypeName);
						return WalkType.NO_CHILDREN;
					}
					
					// add the return type to our signature
					signature.addReturn(retType);
				}
			}
		}
		
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
			
			DEBUG("ADDED METHOD: " + signature.toString());
	
			// add the method to the current type
			currentClass.addMethod(methodDec.getImage(), signature);
		}
		else
		{
			addError(node, "Cannot add method to a structure that is not a class, interface, error, enum, or exception");
			return WalkType.NO_CHILDREN;
		}
		
		return WalkType.NO_CHILDREN;	// don't want to type-check the whole method now
	}
	
	public Object visit(ASTConstructorDeclaration node, Boolean secondVisit) throws ShadowException {		
		MethodSignature signature = new MethodSignature("constructor", node.getModifiers(), node.getLine());
		visitParameters(node.jjtGetChild(0), signature);

		DEBUG("ADDED METHOD: " + signature.toString());

		// add the method to the current type
		ClassInterfaceBaseType currentClass = (ClassInterfaceBaseType)currentType;
		currentClass.addMethod("constructor", signature);

		return WalkType.NO_CHILDREN;
	}
	
	public Object visit(ASTDestructorDeclaration node, Boolean secondVisit) throws ShadowException {		
		MethodSignature signature = new MethodSignature("destructor", node.getModifiers(), node.getLine());

		DEBUG("ADDED METHOD: " + signature.toString());

		// add the method to the current type
		ClassInterfaceBaseType currentClass = (ClassInterfaceBaseType)currentType;
		currentClass.addMethod("destructor", signature);

		return WalkType.NO_CHILDREN;
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
			Node typeNode = param.jjtGetChild(0).jjtGetChild(0);
			
			if(typeNode instanceof ASTFunctionType) {
				signature.addParameter(paramSymbol, createMethodType((ASTFunctionType)typeNode));
			} else {	// regular parameter
				Type paramType = lookupType(typeNode.getImage());
				
				// make sure this type is in the type table
				if(paramType == null) {
					addError(typeNode, Error.UNDEF_TYP, typeNode.getImage());
					return false;
				}
				
				// add the parameter type to the signature
				signature.addParameter(paramSymbol, paramType);
			}
		}
		
		return true;
	}
	
	/**
	 * Given an ASTFunctionType node recursively builds the corresponding MethodType type.
	 * @param node
	 * @return
	 */
	public MethodType createMethodType(ASTFunctionType node) {
		MethodType ret = new MethodType(null); // it has no name
		
		// add all the parameters to this method
		int i;
		for(i=0; i < node.jjtGetNumChildren(); ++i) {
			Node curNode = node.jjtGetChild(i).jjtGetChild(0);
			
			// check to see if we've moved on to the result types
			if(curNode instanceof ASTResultType)
				break;
			
			// need to recursively call this
			else if(curNode instanceof ASTFunctionType)
				ret.addParameter(createMethodType((ASTFunctionType)curNode));
			
			
			else {
				Type type = lookupType(curNode.getImage());
				
				if(type == null) {
					addError(curNode, Error.UNDEF_TYP, curNode.getImage());
					return ret;	// just return whatever, we should prob throw here
				}
				
				ret.addParameter(type);	// add the type as the parameter
			}
		}
		
		// check to see if we have result types
		if(i < node.jjtGetNumChildren()) {
			Node resNode = node.jjtGetChild(i);
			
			for(int r=0; r < resNode.jjtGetNumChildren(); ++r) {
				Node curNode = resNode.jjtGetChild(r).jjtGetChild(0).jjtGetChild(0);
				
				if(curNode instanceof ASTFunctionType) {
					ret.addReturn(createMethodType((ASTFunctionType)curNode));
				} else {
					Type type = lookupType(curNode.getImage());
					
					if(type == null) {
						addError(curNode.jjtGetChild(0), Error.UNDEF_TYP, curNode.jjtGetChild(0).getImage());
						return ret;	// just return whatever, we should prob throw here
					}
					
					ret.addReturn(type);
				}
			}
		}
		
		return ret;
	}
}
