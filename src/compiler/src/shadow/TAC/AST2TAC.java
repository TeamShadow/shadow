package shadow.TAC;

import shadow.AST.ASTWalker;
import shadow.TAC.nodes.TACNode;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;

public class AST2TAC {
	private Node astRoot;	/** The root of the AST */
	private AST2TACWalker astWalker;
	/**
	 * Converts an AST to a TAC
	 * @param node The root node of the branch of the AST to be converted
	 */
	public AST2TAC(Node node) {
		this.astRoot = node;
	}
	
	/**
	 * Actually walks the tree and does the conversion
	 */
	public void convert() throws ShadowException {
		astWalker = new AST2TACWalker();
		ASTWalker walker = new ASTWalker(astWalker);
		
		walker.walk(astRoot);
	}
	
	public TACNode getEntry() {
		return astWalker.getEntry();
	}
	
	public TACNode getExit() {
		return astWalker.getExit();
	}
}
