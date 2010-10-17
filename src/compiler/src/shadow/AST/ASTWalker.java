package shadow.AST;

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
	
	public enum WalkType {
		PRE_CHILDREN,	// just visit the node
		NO_CHILDREN,	// don't visit the children, just return
		POST_CHILDREN	// visit the node again after the children
	}
	
	/**
	 * Given a node, calls accept on the node with the visitor, then walks the children.
	 * @param node The node to call accept on.
	 */
	public void walk(Node node) throws ShadowException {
		// call accept on the node indicating it's the pre-visit
		WalkType wt = (WalkType)node.jjtAccept(visitor, false);
		
		// we don't want to go down this branch
		if(wt == WalkType.NO_CHILDREN)
			return;
		
		// go through the children in order
		int numChildren = node.jjtGetNumChildren();
		for(int i=0; i < numChildren; ++i) {
			walk(node.jjtGetChild(i));
		}
		
		// if asked, visit the node again after the children
		if(wt == WalkType.POST_CHILDREN)
			node.jjtAccept(visitor, true);
	}
	
	/**
	 * Given a node, calls accept on that node before calling accept on its children
	 * @param node
	 * @throws ShadowException
	 */
	public void preorderWalk(Node node) throws ShadowException {
		// call accept on the node indicating it's the pre-visit
		node.jjtAccept(visitor, false);
		
		// go through the children in order
		int numChildren = node.jjtGetNumChildren();
		
		for(int i=0; i < numChildren; ++i) {
			walk(node.jjtGetChild(i));
		}
	}

	/**
	 * Given a node, calls accept on that node's children before calling accept on the node.
	 * @param node
	 * @throws ShadowException
	 */
	public void postorderWalk(Node node) throws ShadowException {
		// go through the children in order
		int numChildren = node.jjtGetNumChildren();
		
		for(int i=0; i < numChildren; ++i) {
			walk(node.jjtGetChild(i));
		}

		node.jjtAccept(visitor, true);
	}

}
