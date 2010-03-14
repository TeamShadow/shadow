package shadow.AST;

import shadow.parser.javacc.Node;

/**
 * Useful functions for working with AST nodes
 * @author wspeirs
 *
 */
public class ASTUtils {

	static public String getLineCol(Node node) {
		return node.getLine() + ":" + node.getColumn();
	}
	
	static public String getSymLineCol(Node node) {
		return node.getImage() + " " + getLineCol(node);
	}
	
	public static String getFileAndLine(int depth) {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		
		return stack[depth].getFileName() + ":" + stack[depth].getLineNumber() + " ";
	}
	
	public static void DEBUG(String msg) {
		System.out.println("DEBUG: " + getFileAndLine(3) + msg);
	}
	
	public static void DEBUG(Node node) {
		System.out.println("DEBUG: " + getFileAndLine(3) + node.getClass().getSimpleName() + " @ " + ASTUtils.getLineCol(node));
	}
	
	public static void DEBUG(Node node, String msg) {
		System.out.println("DEBUG: " + getFileAndLine(3) + node.getClass().getSimpleName() + " @ " + ASTUtils.getLineCol(node) + " " + msg);
	}

}
