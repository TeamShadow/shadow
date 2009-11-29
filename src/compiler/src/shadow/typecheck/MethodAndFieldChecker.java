package shadow.typecheck;

import java.util.HashMap;
import java.util.LinkedList;

import shadow.parser.javacc.ASTFieldDeclaration;
import shadow.parser.javacc.ASTMethodDeclaration;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.ASTWalker.WalkType;

public class MethodAndFieldChecker extends BaseChecker {

	public MethodAndFieldChecker(LinkedList<HashMap<String, String>> symbolTable, HashMap<String, MethodSignature> methodTable) {
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

	public Object visit(ASTMethodDeclaration node, Object secondVisit) throws ShadowException {		
		
		return WalkType.NO_CHILDREN;	// don't want to type-check the whole method now
	}
}