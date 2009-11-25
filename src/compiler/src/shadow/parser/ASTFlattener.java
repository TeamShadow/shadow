package shadow.parser;

import shadow.parser.javacc.*;

public class ASTFlattener implements ShadowParserVisitor {
	public SimpleNode root;

	/**
	 * Constructs a walker given a visitor
	 * @param visitor
	 */
	public ASTFlattener(SimpleNode root) {
		this.root = root;
	}
	
	public SimpleNode flatten() throws ShadowException {
		flatten(root);
		return root;
	}
	
	/**
	 * Given a node, calls accept on the node with the visitor, then walks the children.
	 * @param node The node to call accept on.
	 */
	public void flatten(SimpleNode node) throws ShadowException {
		// go through the children first
		int numChildren = node.jjtGetNumChildren();
		for(int i=0; i < numChildren; ++i) {
			flatten((SimpleNode)node.jjtGetChild(i));
		}
		
		// visit the node again after the children
		node.jjtAccept(this, null);
	}
	
	@Override
	public Object visit(SimpleNode node, Object data) throws ShadowException {
		Node myParent = node.jjtGetParent();
		
		// if we only have one child we can be removed
		if(node.jjtGetNumChildren() == 1 && myParent != null) {
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
		
		return null;
	}

	@Override
	public Object visit(ASTCompilationUnit node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTPackageDeclaration node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTImportDeclaration node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTModifiers node, Object data) throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTTypeDeclaration node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTViewDeclaration node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTExtendsList node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTImplementsList node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTEnumDeclaration node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTEnumBody node, Object data) throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTEnumConstant node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTTypeParameters node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTTypeParameter node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTTypeBound node, Object data) throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTClassOrInterfaceBody node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTClassOrInterfaceBodyDeclaration node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTFieldDeclaration node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTVariableDeclarator node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTVariableDeclaratorId node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTVariableInitializer node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTArrayInitializer node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTMethodDeclaration node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTMethodDeclarator node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTFormalParameters node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTFormalParameter node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTConstructorDeclaration node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTExplicitConstructorInvocation node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTInitializer node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTType node, Object data) throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTReferenceType node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTStaticArrayType node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTFunctionType node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTClassOrInterfaceType node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTTypeArguments node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTTypeArgument node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTWildcardBounds node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTPrimitiveType node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTResultType node, Object data) throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTResultTypes node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTName node, Object data) throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTNameList node, Object data) throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTExpression node, Object data) throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTAssignmentOperator node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTConditionalExpression node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTConditionalOrExpression node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTConditionalAndExpression node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTInclusiveOrExpression node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTExclusiveOrExpression node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTAndExpression node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTEqualityExpression node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTInstanceOfExpression node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTRelationalExpression node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTShiftExpression node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTRotateExpression node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTAdditiveExpression node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTMultiplicativeExpression node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTUnaryExpression node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTUnaryExpressionNotPlusMinus node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTCastExpression node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTPrimaryExpression node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTMemberSelector node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTPrimaryPrefix node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTPrimarySuffix node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTLiteral node, Object data) throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTBooleanLiteral node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTNullLiteral node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTArguments node, Object data) throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTArgumentList node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTAllocationExpression node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTArrayDimsAndInits node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTStatement node, Object data) throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTAssertStatement node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTLabeledStatement node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTBlock node, Object data) throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTBlockStatement node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTLocalVariableDeclaration node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTEmptyStatement node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTStatementExpression node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTSwitchStatement node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTSwitchLabel node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTIfStatement node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTWhileStatement node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTDoStatement node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTForeachStatement node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTForStatement node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTForInit node, Object data) throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTStatementExpressionList node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTForUpdate node, Object data) throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTBreakStatement node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTContinueStatement node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTReturnStatement node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTThrowStatement node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTSynchronizedStatement node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTTryStatement node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTRIGHTROTATE node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTRIGHTSHIFT node, Object data) throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTMemberValuePairs node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTMemberValuePair node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTMemberValue node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTMemberValueArrayInitializer node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

	@Override
	public Object visit(ASTDefaultValue node, Object data)
			throws ShadowException {
		return visit((SimpleNode)node, data);	}

}
