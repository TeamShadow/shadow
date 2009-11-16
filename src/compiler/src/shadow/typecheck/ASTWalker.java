package shadow.typecheck;

import shadow.parser.AbstractASTVisitor;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;

public class ASTWalker {
	
	public AbstractASTVisitor visitor;

	/**
	 * Constructs a walker given a visitor
	 * @param visitor
	 */
	public ASTWalker(AbstractASTVisitor visitor) {
		this.visitor = visitor;
	}
	
	/**
	 * Given a node, calls accept on the node with the visitor, then walks the children.
	 * @param node The node to call accept on.
	 */
	public void walk(Node node) throws ShadowException {
		// first call accept on the node
		node.jjtAccept(visitor, visitor.getData());
		
		// then go through the children in order
		int numChildren = node.jjtGetNumChildren();
		for(int i=0; i < numChildren; ++i) {
			walk(node.jjtGetChild(i));
		}
	}

}
