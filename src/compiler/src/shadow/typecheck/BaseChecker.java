package shadow.typecheck;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import shadow.AST.ASTUtils;
import shadow.AST.AbstractASTVisitor;
import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.ASTAdditiveExpression;
import shadow.parser.javacc.ASTMultiplicativeExpression;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.SimpleNode;

public abstract class BaseChecker extends AbstractASTVisitor {

	protected ArrayList<String> errorList;
	
	// these are constants for our error messages to keep things consistent
	public static enum Error {
		INVL_TYP		{ String getStr() { return "INVALID TYPE"; } },
		MULT_SYM		{ String getStr() { return "MULTIPLY DEFINED SYMBOL"; } },
		MULT_MTH		{ String getStr() { return "MULTIPLY DEFINED METHODS"; } },
		UNDEC_VAR		{ String getStr() { return "UNDECLARED VARIABLE"; } },
		UNDEF_TYP		{ String getStr() { return "UNDEFINDED TYPE"; } },
		TYPE_MIS		{ String getStr() { return "TYPE MISMATCH"; } };
		
		abstract String getStr();
	}
	
	public BaseChecker() {
		errorList = new ArrayList<String>();
	}
	
	/**
	 * Adds an error message to the list errors we keep until the end.
	 * @param node The node where the error occurred. This will be printed in the standard format.
	 * @param msg The message to communicate to the user.
	 */
	protected void addError(Node node, String msg) {
		errorList.add("[" + ASTUtils.getLineCol(node) + "] : " + msg);
	}
	
	/**
	 * Adds an error messages to the list of errors.
	 * @param node The node where the error occurred. This will be printed in standard format.
	 * @param type One of the pre-defined types of errors.
	 * @param msg The message associated with the error.
	 */
	protected void addError(Node node, Error type, String msg) {
		errorList.add("[" + ASTUtils.getLineCol(node) + "] " + type.getStr() + ": " + msg);
	}
	
	/**
	 * Adds an error messages to the list of errors.
	 * @param node The node where the error occurred. This will be printed in standard format.
	 * @param type One of the pre-defined types of errors.
	 */
	protected void addError(Node node, Error type) {
		errorList.add("[" + ASTUtils.getLineCol(node) + "] " + type.getStr() + ": ");
	}
	
	/**
	 * Print out the list of errors to standard error.
	 */
	public void printErrors() {
		printErrors(System.err);
	}
	
	/**
	 * Print out the list of errors to the given stream.
	 * @param stream The stream to print the errors to.
	 */
	public void printErrors(PrintStream stream) {
		for(String msg:errorList) {
			stream.println(msg);
		}
	}
	
	public int getErrorCount() {
		return errorList.size();
	}
}
