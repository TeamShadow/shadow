package shadow.AST;

import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.*;

/**
 * This class removes unneeded nodes from the AST.
 * 
 * If you don't explicitly remove the node then nothing happens by default. If you want
 * to remove a node from the AST, simply add the appropriate visitor.
 * @author William R. Speirs
 */
public class ASTFlattener extends AbstractASTVisitor {

	/**
	 * Constructs a walker given a visitor
	 * @param visitor
	 */
	public ASTFlattener() {	}
	
	/**
	 * Removes a node <b>without</b> doing any checking!
	 * @param node The node to remove
	 * @throws ShadowException
	 */
	protected void removeNode(SimpleNode node) throws ShadowException {
		Node myParent = node.jjtGetParent();
		
		if(myParent == null)
			return;

		// set my child's parent to my parent
		node.jjtGetChild(0).jjtSetParent(node.jjtGetParent());
		
		// find myself in my parent
		int i=0;
		
		for( ; i < myParent.jjtGetNumChildren(); ++i) {
			if(myParent.jjtGetChild(i) == node) {
				// swap my child into where I was in my parent
				myParent.jjtSwapChild(node.jjtGetChild(0), i);
				break;
			}
		}
	}
	
	/**
	 * Removes a node iff the parent has 1 child and the node has 1 child.
	 * @param node The node that might be removed.
	 * @throws ShadowException
	 */
	protected void removeNode1P1C(SimpleNode node) throws ShadowException {
		if(!node.isImageNull())	// if we have an image, don't want to remove
			return;
		
		Node myParent = node.jjtGetParent();
		
		if(node instanceof ASTIsExpression) {
			((SimpleNode)myParent).dump("");
			System.out.println("PARENT: " + myParent.jjtGetNumChildren());
			System.out.println("ME: " + node.jjtGetNumChildren());
		}
		
		// if I'm my parent's only child, and I have only 1 child, then remove
		if(myParent != null && myParent.jjtGetNumChildren() == 1 && node.jjtGetNumChildren() == 1)
			removeNode(node);
	}
	
	/**
	 * Removes a node only if it has 1 child.
	 * @param node THe node that might be removed.
	 * @throws ShadowException
	 */
	protected void removeNode1C(SimpleNode node) throws ShadowException {
		if(!node.isImageNull())	// if we have an image, don't want to remove
			return;
		
		if(node.jjtGetNumChildren() == 1)
			removeNode(node);
	}
	

	//
	// These are nodes we want to remove IFF parent has 1 child and it has 1 child
	//
//	public Object visit(ASTReferenceType node, Boolean secondVisit) throws ShadowException { removeNode1P1C((SimpleNode)node); return WalkType.PRE_CHILDREN; }
	public Object visit(ASTStatementExpression node, Boolean secondVisit) throws ShadowException { removeNode1P1C((SimpleNode)node);	return WalkType.PRE_CHILDREN; }
	public Object visit(ASTStatementExpressionList node, Boolean secondVisit) throws ShadowException { removeNode1P1C((SimpleNode)node);	return WalkType.PRE_CHILDREN; }

	//
	// These are nodes we want to remove IF it has 1 child
	//
	public Object visit(ASTExpression node, Boolean secondVisit) throws ShadowException { removeNode1C((SimpleNode)node); return WalkType.PRE_CHILDREN; }
	public Object visit(ASTConditionalExpression node, Boolean secondVisit) throws ShadowException { removeNode1C((SimpleNode)node); return WalkType.PRE_CHILDREN; }
	public Object visit(ASTConditionalOrExpression node, Boolean secondVisit) throws ShadowException { removeNode1C((SimpleNode)node); return WalkType.PRE_CHILDREN; }
	public Object visit(ASTConditionalExclusiveOrExpression node, Boolean secondVisit) throws ShadowException { removeNode1C((SimpleNode)node); return WalkType.PRE_CHILDREN; }
	public Object visit(ASTConditionalAndExpression node, Boolean secondVisit) throws ShadowException { removeNode1C((SimpleNode)node);	return WalkType.PRE_CHILDREN; }
	public Object visit(ASTBitwiseOrExpression node, Boolean secondVisit) throws ShadowException { removeNode1C((SimpleNode)node);	return WalkType.PRE_CHILDREN; }
	public Object visit(ASTBitwiseExclusiveOrExpression node, Boolean secondVisit) throws ShadowException { removeNode1C((SimpleNode)node);	return WalkType.PRE_CHILDREN; }
	public Object visit(ASTBitwiseAndExpression node, Boolean secondVisit) throws ShadowException { removeNode1C((SimpleNode)node);	return WalkType.PRE_CHILDREN; }
	public Object visit(ASTShiftExpression node, Boolean secondVisit) throws ShadowException { removeNode1C((SimpleNode)node);	return WalkType.PRE_CHILDREN; }
	public Object visit(ASTRotateExpression node, Boolean secondVisit) throws ShadowException { removeNode1C((SimpleNode)node);	return WalkType.PRE_CHILDREN; }
	public Object visit(ASTAdditiveExpression node, Boolean secondVisit) throws ShadowException { removeNode1C((SimpleNode)node);	return WalkType.PRE_CHILDREN; }
	public Object visit(ASTMultiplicativeExpression node, Boolean secondVisit) throws ShadowException { removeNode1C((SimpleNode)node);	return WalkType.PRE_CHILDREN; }
	public Object visit(ASTUnaryExpression node, Boolean secondVisit) throws ShadowException { removeNode1C((SimpleNode)node);	return WalkType.PRE_CHILDREN; }
	public Object visit(ASTUnaryExpressionNotPlusMinus node, Boolean secondVisit) throws ShadowException { removeNode1C((SimpleNode)node);	return WalkType.PRE_CHILDREN; }
	public Object visit(ASTCastExpression node, Boolean secondVisit) throws ShadowException { removeNode1C((SimpleNode)node);	return WalkType.PRE_CHILDREN; }
	public Object visit(ASTEqualityExpression node, Boolean secondVisit) throws ShadowException { removeNode1C((SimpleNode)node);	return WalkType.PRE_CHILDREN; }
	public Object visit(ASTIsExpression node, Boolean secondVisit) throws ShadowException { removeNode1C((SimpleNode)node);	return WalkType.PRE_CHILDREN; }
	public Object visit(ASTRelationalExpression node, Boolean secondVisit) throws ShadowException { removeNode1C((SimpleNode)node);	return WalkType.PRE_CHILDREN; }
	public Object visit(ASTPrimaryExpression node, Boolean secondVisit) throws ShadowException { removeNode1C((SimpleNode)node);	return WalkType.PRE_CHILDREN; }
	public Object visit(ASTPrimaryPrefix node, Boolean secondVisit) throws ShadowException { removeNode1C((SimpleNode)node); return WalkType.PRE_CHILDREN; }
	
}
