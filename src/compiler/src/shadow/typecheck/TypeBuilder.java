package shadow.typecheck;

import java.util.HashMap;

import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.ASTConstructorDeclaration;
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
	protected HashMap<String, Type> typeTable;
	protected ClassInterfaceBaseType curType;
	
	
	public TypeBuilder(HashMap<String, Type> typeTable, boolean debug) {
		super(debug);
		this.typeTable = typeTable;
		
		// put all of our built-in types into the TypeTable
		typeTable.put(Type.OBJECT.getTypeName(),	Type.OBJECT);
		typeTable.put(Type.BOOLEAN.getTypeName(),	Type.BOOLEAN);
		typeTable.put(Type.BYTE.getTypeName(),		Type.BYTE);
		typeTable.put(Type.CODE.getTypeName(),		Type.CODE);
		typeTable.put(Type.SHORT.getTypeName(),		Type.SHORT);
		typeTable.put(Type.INT.getTypeName(),		Type.INT);
		typeTable.put(Type.LONG.getTypeName(),		Type.LONG);
		typeTable.put(Type.FLOAT.getTypeName(),		Type.FLOAT);
		typeTable.put(Type.DOUBLE.getTypeName(),	Type.DOUBLE);
		typeTable.put(Type.STRING.getTypeName(),	Type.STRING);
		typeTable.put(Type.UBYTE.getTypeName(),		Type.UBYTE);
		typeTable.put(Type.UINT.getTypeName(),		Type.UINT);
		typeTable.put(Type.ULONG.getTypeName(),		Type.ULONG);
		typeTable.put(Type.USHORT.getTypeName(),	Type.USHORT);
		typeTable.put(Type.NULL.getTypeName(),		Type.NULL);
	}
	
	public Object visit(ASTClassOrInterfaceDeclaration node, Object secondVisit) throws ShadowException {
		
		//
		// TODO: Fix this as it could be a class, exception or interface
		//       We'll also need to add that to Type so it knows what it is
		//
		
		// For now we punt and assume everything is a class
		curType = new ClassInterfaceBaseType(node.getImage(), node.getModifiers());
		
		// insert our new type into the table
		typeTable.put(node.getImage(), curType);
		
		return WalkType.PRE_CHILDREN;
	}

	/**
	 * Add the field declarations.
	 */
	public Object visit(ASTFieldDeclaration node, Object secondVisit) throws ShadowException {
		// a field dec has a type followed by 1 or more idents
		Type type = typeTable.get(node.jjtGetChild(0).jjtGetChild(0).getImage());
		
		// make sure we have this type
		if(type == null) {
			addError(node.jjtGetChild(0).jjtGetChild(0), Error.UNDEF_TYP, node.jjtGetChild(0).jjtGetChild(0).getImage());
			return WalkType.NO_CHILDREN;
		}
		
		// go through inserting all the idents
		for(int i=1; i < node.jjtGetNumChildren(); ++i) {
			String symbol = node.jjtGetChild(i).jjtGetChild(0).getImage();
			
			// make sure we don't already have this symbol
			if(curType.containsField(symbol)) {
				addError(node.jjtGetChild(i).jjtGetChild(0), Error.MULT_SYM, symbol);
				return WalkType.NO_CHILDREN;
			}
			
			DEBUG("ADDING: " + type + " " + symbol);
			
			curType.addField(symbol, type);
		}
		
		return WalkType.NO_CHILDREN;	// only need the symbols, no type-checking yet
	}

	/**
	 * Adds a method to the current type.
	 */
	public Object visit(ASTMethodDeclaration node, Object secondVisit) throws ShadowException {		
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
					Type retType = typeTable.get(retTypeName);

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
		
		// make sure we don't already have this method
		if(curType.containsMethod(signature)) {
			
			// get the first signature
			MethodSignature firstMethod = curType.getMethodSignature(signature);
			
			addError(methodDec, Error.MULT_MTH, "First declared on line " + firstMethod.getLineNumber());
			return WalkType.NO_CHILDREN;
		}
		
		DEBUG("ADDED METHOD: " + signature.toString());

		// add the method to the current type
		curType.addMethod(methodDec.getImage(), signature);
		
		return WalkType.NO_CHILDREN;	// don't want to type-check the whole method now
	}
	
	public Object visit(ASTConstructorDeclaration node, Object secondVisit) throws ShadowException {		
		MethodSignature signature = new MethodSignature("constructor", node.getModifiers(), node.getLine());
		visitParameters(node.jjtGetChild(0), signature);
		
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
				Type paramType = typeTable.get(typeNode.getImage());
				
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
				Type type = typeTable.get(curNode.getImage());
				
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
					Type type = typeTable.get(curNode.getImage());
					
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
