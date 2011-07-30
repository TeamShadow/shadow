package shadow.AST;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import shadow.parser.javacc.Node;

/**
 * Useful functions for working with AST nodes
 * @author wspeirs
 *
 */
public class ASTUtils {
	
	private static final Log logger = LogFactory.getLog("shadow");

	static public String getLineCol(Node node) {
		return node.getLine() + ":" + node.getColumn();
	}
	
	static public String getSymLineCol(Node node) {
		return node.getImage() + " " + getLineCol(node);
	}
	
	public static void DEBUG(String msg) {
		logger.debug(msg);
	}
	
	public static void DEBUG(Node node) {
		logger.debug(node.getClass().getSimpleName() + " @ " + ASTUtils.getLineCol(node));
	}
	
	public static void DEBUG(Node node, String msg) {
		logger.debug(node.getClass().getSimpleName() + " @ " + ASTUtils.getLineCol(node) + " " + msg);
	}

}
