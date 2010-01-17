package shadow.typecheck;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import shadow.parser.javacc.ASTFieldDeclaration;
import shadow.parser.javacc.ASTMethodDeclaration;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.ASTWalker.WalkType;

public class MethodAndFieldChecker extends BaseChecker {

	public MethodAndFieldChecker(LinkedList<HashMap<String, String>> symbolTable, HashSet<MethodSignature> methodTable) {
		super(symbolTable, methodTable);
	}

	/**
	 * Type checks a field declaration.
	 */
	public Object visit(ASTFieldDeclaration node, Object secondVisit) throws ShadowException {
		if((Boolean)secondVisit)
			addVarDec(node);
		
		return WalkType.POST_CHILDREN;	// type check the children first
	}

	/**
	 * Type checks a method declaration.
	 */
	public Object visit(ASTMethodDeclaration node, Object secondVisit) throws ShadowException {		
		Node methodDec = node.jjtGetChild(0);
		Node params = methodDec.jjtGetChild(0);
		MethodSignature signature = new MethodSignature(methodDec.getImage(), node.getModifiers(), node.getLine());
		
		HashSet<String> paramNames = new HashSet<String>();
		
		// go through all the formal parameters
		for(int i=0; i < params.jjtGetNumChildren(); ++i) {
			Node param = params.jjtGetChild(i);
			
			// add the parameter type to the signature
			signature.addParameter(param.jjtGetChild(0).jjtGetChild(0).getType());
			
			// get the name of the parameter
			String paramSymbol = param.jjtGetChild(1).getImage();
			
			// check if it's already in the set of parameter names
			if(paramNames.contains(paramSymbol))
				throw new ShadowException("MULTIPLY DEFINED PARAMETER NAMES: " + param.jjtGetChild(1).getLine() + ":" + param.jjtGetChild(1).getColumn());
			
			// add the name to the set
			paramNames.add(paramSymbol);
		}
		
		// check to see if we have a return type
		if(methodDec.jjtGetNumChildren() == 2) {
			Node retTypes = methodDec.jjtGetChild(1);
			
			for(int i=0; i < retTypes.jjtGetNumChildren(); ++i) {
				signature.addReturn(retTypes.jjtGetChild(i).jjtGetChild(0).getType());
			}
		}
		
		if(methodTable.contains(signature)) {
			// walk through the table and find the other signature
			MethodSignature firstMethod = null;
			for(MethodSignature ms:methodTable) {
				if(ms.equals(signature)) {
					firstMethod = ms;
					break;
				}
			}
			
			throw new ShadowException("MULTIPLY DEFINED METHODS, FIRST DECLARED ON LINE: " + firstMethod.getLineNumber() + " SECOND HERE: " + methodDec.getLine() + ":" + methodDec.getColumn());
		}
		
		methodTable.add(signature);
//		System.out.println("ADDED METHOD: " + signature.getSymbol());
		
		return WalkType.NO_CHILDREN;	// don't want to type-check the whole method now
	}
}
