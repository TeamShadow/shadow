package shadow.typecheck;

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
		// call accept on the node indicating it's the pre-visit
		Boolean postVisit = (Boolean)node.jjtAccept(visitor, false);
		
		// then go through the children in order
		int numChildren = node.jjtGetNumChildren();
		for(int i=0; i < numChildren; ++i) {
			walk(node.jjtGetChild(i));
		}
		
		// if asked, visit the node again after the children
		if(postVisit)
			node.jjtAccept(visitor, true);
	}

}
