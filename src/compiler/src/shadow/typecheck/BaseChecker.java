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
	protected boolean debug;
	
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
	
	public BaseChecker(boolean debug) {
		errorList = new ArrayList<String>();
		this.debug = debug;
	}
	
	protected String getFileAndLine(int depth) {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		
		return stack[depth].getFileName() + ":" + stack[depth].getLineNumber() + " ";
	}
	
	/**
	 * Adds an error message to the list errors we keep until the end.
	 * @param node The node where the error occurred. This will be printed in the standard format.
	 * @param msg The message to communicate to the user.
	 */
	protected void addError(Node node, String msg) {
		String error = "[" + ASTUtils.getLineCol(node) + "] : " + msg;
		
		if(debug)
			errorList.add(getFileAndLine(3) + error);
		else
			errorList.add(error);
	}
	
	/**
	 * Adds an error messages to the list of errors.
	 * @param node The node where the error occurred. This will be printed in standard format.
	 * @param type One of the pre-defined types of errors.
	 * @param msg The message associated with the error.
	 */
	protected void addError(Node node, Error type, String msg) {
		String error = "[" + ASTUtils.getLineCol(node) + "] " + type.getStr() + ": " + msg; 
		
		if(debug)
			errorList.add(getFileAndLine(3) + error);
		else
			errorList.add(error);
	}
	
	/**
	 * Adds an error messages to the list of errors.
	 * @param node The node where the error occurred. This will be printed in standard format.
	 * @param type One of the pre-defined types of errors.
	 */
	protected void addError(Node node, Error type) {
		String error = "[" + ASTUtils.getLineCol(node) + "] " + type.getStr() + ": "; 
		
		if(debug)
			errorList.add(getFileAndLine(3) + error);
		else
			errorList.add(error);
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
	
	public void DEBUG(String msg) {
		if(debug)
			System.out.println("DEBUG: " + getFileAndLine(3) + msg);
	}
	
	public void DEBUG(Node node) {
		if(debug)
			System.out.println("DEBUG: " + getFileAndLine(3) + node.getClass().getSimpleName() + " @ " + ASTUtils.getLineCol(node));
	}
}
