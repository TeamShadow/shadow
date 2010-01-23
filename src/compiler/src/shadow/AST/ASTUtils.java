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
}
