package shadow.typecheck;

import java.util.HashMap;

import shadow.AST.ASTUtils;
import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.*;

public class MethodAndFieldChecker extends BaseChecker {
	protected HashMap<String, Type> fieldTable;
	protected HashMap<String, MethodSignature> methodTable;
	
	public MethodAndFieldChecker(HashMap<String, Type> fieldTable, HashMap<String, MethodSignature> methodTable) {
		this.fieldTable = fieldTable;
		this.methodTable = methodTable;
	}

	/**
	 * Type checks a field declaration.
	 */
	public Object visit(ASTFieldDeclaration node, Object secondVisit) throws ShadowException {
		if((Boolean)secondVisit)
			addVarDec(node, fieldTable);
		
		return WalkType.POST_CHILDREN;	// type check the children first
	}

	/**
	 * Type checks a method declaration.
	 */
	public Object visit(ASTMethodDeclaration node, Object secondVisit) throws ShadowException {		
		Node methodDec = node.jjtGetChild(0);
		Node params = methodDec.jjtGetChild(0);
		MethodSignature signature = new MethodSignature(methodDec.getImage(), node.getModifiers(), node.getLine());
		
		// go through all the formal parameters
		for(int i=0; i < params.jjtGetNumChildren(); ++i) {
			Node param = params.jjtGetChild(i);
			
			// get the name of the parameter
			String paramSymbol = param.jjtGetChild(1).getImage();
			
			// check if it's already in the set of parameter names
			if(signature.containsParam(paramSymbol))
				throw new ShadowException("MULTIPLY DEFINED PARAMETER NAMES: " + param.jjtGetChild(1).getLine() + ":" + param.jjtGetChild(1).getColumn());
			
			// get the type of the parameter
			Node typeNode = param.jjtGetChild(0).jjtGetChild(0);
			
			// we need to check if we have a ClassOrInterfaceType here that has . in it
			// if this is a primative type, the type will already be set
			if(typeNode.getType() == null) {
				if(typeNode.getImage().contains("."))
					throw new ShadowException("INVALID TYPE: " + ASTUtils.getSymLineCol(typeNode));
				else
					typeNode.setType(new Type(typeNode.getImage()));
			}

			// add the parameter type to the signature
			signature.addParameter(paramSymbol, typeNode.getType());
		}
		
		// check to see if we have a return type
		if(methodDec.jjtGetNumChildren() == 2) {
			Node retTypes = methodDec.jjtGetChild(1);
			
			for(int i=0; i < retTypes.jjtGetNumChildren(); ++i)
				signature.addReturn(retTypes.jjtGetChild(i).jjtGetChild(0).jjtGetChild(0).getType());
		}
		
		if(methodTable.containsValue(signature)) {
			// walk through the table and find the other signature
			MethodSignature firstMethod = null;
			for(MethodSignature ms:methodTable.values()) {
				if(ms.equals(signature)) {
					firstMethod = ms;
					break;
				}
			}
			
			throw new ShadowException("MULTIPLY DEFINED METHODS, FIRST DECLARED ON LINE: " + firstMethod.getLineNumber() + " SECOND HERE: " + methodDec.getLine() + ":" + methodDec.getColumn());
		}
		
		methodTable.put(methodDec.getImage(), signature);
//		System.out.println("ADDED METHOD: " + signature.getSymbol());
		
		return WalkType.NO_CHILDREN;	// don't want to type-check the whole method now
	}
}
