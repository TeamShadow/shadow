package shadow.TAC;

import shadow.AST.ASTWalker;
import shadow.TAC.nodes.TACNode;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.SimpleNode;

/**
 * Converts the AST that describes an ASTMethodDeclaration into the associated TAC
 */
public class ASTToTAC {
	private Node astRoot = null;	/** The root of the ASTMethodDeclaration AST */
	private ASTToTACVisitor astVisitor = null;	/** The visitor that converts AST -> TAC */
	private ASTWalker walker = null;	/** The walker for the AST */
	
	/**
	 * Converts an AST to a TAC
	 * @param node An ASTMethodDeclaration node of an AST to be converted
	 */
	public ASTToTAC(Node node) {
		this.astRoot = node;
		astVisitor = new ASTToTACVisitor();
		walker = new ASTWalker(astVisitor);
		
	}
	
	/**
	 * Actually walks the tree and does the conversion
	 */
	public void convert() throws ShadowException {
		walker.walk(astRoot);	// walk the AST converting to TAC
	}
	
	/**
	 * Return the entry node of the method.
	 * @return The entry node of the method.
	 */
	public TACNode getEntry() {
		return ((SimpleNode)astRoot).getEntryNode();
	}
	
	/**
	 * Return the exit node of the method.
	 * @return The exit node of the method.
	 */
	public TACNode getExit() {
		return ((SimpleNode)astRoot).getExitNode();
	}
}
