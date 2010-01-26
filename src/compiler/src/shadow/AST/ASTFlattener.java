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
	
	public void removeNode(SimpleNode node) throws ShadowException {
		Node myParent = node.jjtGetParent();
		
		// if I'm my parent's only child, and I have only 1 child, then remove
		if(myParent != null && node.jjtGetNumChildren() == 1 && myParent.jjtGetNumChildren() == 1) {
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
	}

	public Object visit(ASTPrimaryPrefix node, Object data) throws ShadowException { removeNode((SimpleNode)node); return WalkType.PRE_CHILDREN; }

	public Object visit(ASTReferenceType node, Object data) throws ShadowException { removeNode((SimpleNode)node); return WalkType.PRE_CHILDREN; }

	public Object visit(ASTExpression node, Object data) throws ShadowException { removeNode((SimpleNode)node); return WalkType.PRE_CHILDREN; }

	public Object visit(ASTConditionalExpression node, Object data) throws ShadowException { removeNode((SimpleNode)node); return WalkType.PRE_CHILDREN; }

	public Object visit(ASTConditionalOrExpression node, Object data) throws ShadowException { removeNode((SimpleNode)node); return WalkType.PRE_CHILDREN; }
	
	public Object visit(ASTConditionalExclusiveOrExpression node, Object data) throws ShadowException { removeNode((SimpleNode)node); return WalkType.PRE_CHILDREN; }

	public Object visit(ASTConditionalAndExpression node, Object data) throws ShadowException { removeNode((SimpleNode)node);	return WalkType.PRE_CHILDREN; }

	public Object visit(ASTBitwiseOrExpression node, Object data) throws ShadowException { removeNode((SimpleNode)node);	return WalkType.PRE_CHILDREN; }

	public Object visit(ASTBitwiseExclusiveOrExpression node, Object data) throws ShadowException { removeNode((SimpleNode)node);	return WalkType.PRE_CHILDREN; }

	public Object visit(ASTBitwiseAndExpression node, Object data) throws ShadowException { removeNode((SimpleNode)node);	return WalkType.PRE_CHILDREN; }

	public Object visit(ASTEqualityExpression node, Object data) throws ShadowException { removeNode((SimpleNode)node);	return WalkType.PRE_CHILDREN; }

	public Object visit(ASTIsExpression node, Object data) throws ShadowException { removeNode((SimpleNode)node);	return WalkType.PRE_CHILDREN; }

	public Object visit(ASTRelationalExpression node, Object data) throws ShadowException { removeNode((SimpleNode)node);	return WalkType.PRE_CHILDREN; }

	public Object visit(ASTShiftExpression node, Object data) throws ShadowException { removeNode((SimpleNode)node);	return WalkType.PRE_CHILDREN; }

	public Object visit(ASTRotateExpression node, Object data) throws ShadowException { removeNode((SimpleNode)node);	return WalkType.PRE_CHILDREN; }

	public Object visit(ASTAdditiveExpression node, Object data) throws ShadowException { removeNode((SimpleNode)node);	return WalkType.PRE_CHILDREN; }

	public Object visit(ASTMultiplicativeExpression node, Object data) throws ShadowException { removeNode((SimpleNode)node);	return WalkType.PRE_CHILDREN; }

	public Object visit(ASTUnaryExpression node, Object data) throws ShadowException { removeNode((SimpleNode)node);	return WalkType.PRE_CHILDREN; }

	public Object visit(ASTUnaryExpressionNotPlusMinus node, Object data) throws ShadowException { removeNode((SimpleNode)node);	return WalkType.PRE_CHILDREN; }

	public Object visit(ASTCastExpression node, Object data) throws ShadowException { removeNode((SimpleNode)node);	return WalkType.PRE_CHILDREN; }

	public Object visit(ASTPrimaryExpression node, Object data) throws ShadowException { removeNode((SimpleNode)node);	return WalkType.PRE_CHILDREN; }

	public Object visit(ASTStatementExpression node, Object data) throws ShadowException { removeNode((SimpleNode)node);	return WalkType.PRE_CHILDREN; }

	public Object visit(ASTStatementExpressionList node, Object data) throws ShadowException { removeNode((SimpleNode)node);	return WalkType.PRE_CHILDREN; }
}
