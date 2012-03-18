package shadow.tac;

import shadow.parser.javacc.*;

public abstract class AbstractASTToTACVisitor implements ShadowParserVisitor {
	private TACData data;
	public TACData walk(Node node) throws ShadowException
	{
		data = null;
		if (isBlock(node))
			visit(node);
		TACData[] childrenData = new TACData[node.jjtGetNumChildren()];
		for (int i = 0; i < childrenData.length; i++)
			childrenData[i] = walk(node.jjtGetChild(i));
		data = new TACData(childrenData);
		visit(node);
		return data.isEmpty() ? null : data;
	}
	private boolean isBlock(Node node)
	{
		return node instanceof ASTTryStatement;
	}

	public abstract void visit(ASTCompilationUnit node, TACData tac) throws ShadowException;
	public abstract void visit(ASTPackageDeclaration node, TACData tac) throws ShadowException;
	public abstract void visit(ASTImportDeclaration node, TACData tac) throws ShadowException;
	public abstract void visit(ASTModifiers node, TACData tac) throws ShadowException;
	public abstract void visit(ASTTypeDeclaration node, TACData tac) throws ShadowException;
	public abstract void visit(ASTViewDeclaration node, TACData tac) throws ShadowException;
	public abstract void visit(ASTClassOrInterfaceDeclaration node, TACData tac) throws ShadowException;
	public abstract void visit(ASTExtendsList node, TACData tac) throws ShadowException;
	public abstract void visit(ASTImplementsList node, TACData tac) throws ShadowException;
	public abstract void visit(ASTVersion node, TACData tac) throws ShadowException;
	public abstract void visit(ASTEnumDeclaration node, TACData tac) throws ShadowException;
	public abstract void visit(ASTEnumBody node, TACData tac) throws ShadowException;
	public abstract void visit(ASTEnumConstant node, TACData tac) throws ShadowException;
	public abstract void visit(ASTTypeParameters node, TACData tac) throws ShadowException;
	public abstract void visit(ASTTypeParameter node, TACData tac) throws ShadowException;
	public abstract void visit(ASTTypeBound node, TACData tac) throws ShadowException;
	public abstract void visit(ASTClassOrInterfaceBody node, TACData tac) throws ShadowException;
	public abstract void visit(ASTClassOrInterfaceBodyDeclaration node, TACData tac) throws ShadowException;
	public abstract void visit(ASTFieldDeclaration node, TACData tac) throws ShadowException;
	public abstract void visit(ASTVariableDeclarator node, TACData tac) throws ShadowException;
	public abstract void visit(ASTVariableDeclaratorId node, TACData tac) throws ShadowException;
	public abstract void visit(ASTVariableInitializer node, TACData tac) throws ShadowException;
	public abstract void visit(ASTArrayInitializer node, TACData tac) throws ShadowException;
	public abstract void visit(ASTMethodDeclaration node, TACData tac) throws ShadowException;
	public abstract void visit(ASTMethodDeclarator node, TACData tac) throws ShadowException;
	public abstract void visit(ASTFormalParameters node, TACData tac) throws ShadowException;
	public abstract void visit(ASTFormalParameter node, TACData tac) throws ShadowException;
	public abstract void visit(ASTConstructorDeclaration node, TACData tac) throws ShadowException;
	public abstract void visit(ASTDestructorDeclaration node, TACData tac) throws ShadowException;
	public abstract void visit(ASTExplicitConstructorInvocation node, TACData tac) throws ShadowException;
	public abstract void visit(ASTInitializer node, TACData tac) throws ShadowException;
	public abstract void visit(ASTType node, TACData tac) throws ShadowException;
	public abstract void visit(ASTReferenceType node, TACData tac) throws ShadowException;
	public abstract void visit(ASTStaticArrayType node, TACData tac) throws ShadowException;
	public abstract void visit(ASTFunctionType node, TACData tac) throws ShadowException;
	public abstract void visit(ASTClassOrInterfaceType node, TACData tac) throws ShadowException;
	public abstract void visit(ASTClassOrInterfaceTypeSuffix node, TACData tac) throws ShadowException;
	public abstract void visit(ASTTypeArguments node, TACData tac) throws ShadowException;
	public abstract void visit(ASTTypeArgument node, TACData tac) throws ShadowException;
	public abstract void visit(ASTWildcardBounds node, TACData tac) throws ShadowException;
	public abstract void visit(ASTPrimitiveType node, TACData tac) throws ShadowException;
	public abstract void visit(ASTResultType node, TACData tac) throws ShadowException;
	public abstract void visit(ASTResultTypes node, TACData tac) throws ShadowException;
	public abstract void visit(ASTName node, TACData tac) throws ShadowException;
	public abstract void visit(ASTUnqualifiedName node, TACData tac) throws ShadowException;
	public abstract void visit(ASTNameList node, TACData tac) throws ShadowException;
	public abstract void visit(ASTExpression node, TACData tac) throws ShadowException;
	public abstract void visit(ASTAssignmentOperator node, TACData tac) throws ShadowException;
	public abstract void visit(ASTConditionalExpression node, TACData tac) throws ShadowException;
	public abstract void visit(ASTConditionalOrExpression node, TACData tac) throws ShadowException;
	public abstract void visit(ASTConditionalExclusiveOrExpression node, TACData tac) throws ShadowException;
	public abstract void visit(ASTConditionalAndExpression node, TACData tac) throws ShadowException;
	public abstract void visit(ASTBitwiseOrExpression node, TACData tac) throws ShadowException;
	public abstract void visit(ASTBitwiseExclusiveOrExpression node, TACData tac) throws ShadowException;
	public abstract void visit(ASTBitwiseAndExpression node, TACData tac) throws ShadowException;
	public abstract void visit(ASTEqualityExpression node, TACData tac) throws ShadowException;
	public abstract void visit(ASTIsExpression node, TACData tac) throws ShadowException;
	public abstract void visit(ASTRelationalExpression node, TACData tac) throws ShadowException;
	public abstract void visit(ASTShiftExpression node, TACData tac) throws ShadowException;
	public abstract void visit(ASTRotateExpression node, TACData tac) throws ShadowException;
	public abstract void visit(ASTAdditiveExpression node, TACData tac) throws ShadowException;
	public abstract void visit(ASTMultiplicativeExpression node, TACData tac) throws ShadowException;
	public abstract void visit(ASTUnaryExpression node, TACData tac) throws ShadowException;
	public abstract void visit(ASTUnaryExpressionNotPlusMinus node, TACData tac) throws ShadowException;
	public abstract void visit(ASTCastExpression node, TACData tac) throws ShadowException;
	public abstract void visit(ASTCheckExpression node, TACData tac) throws ShadowException;
	public abstract void visit(ASTPrimaryExpression node, TACData tac) throws ShadowException;
	public abstract void visit(ASTSequence node, TACData tac) throws ShadowException;
	public abstract void visit(ASTMethodCall node, TACData tac) throws ShadowException;
	public abstract void visit(ASTPrimaryPrefix node, TACData tac) throws ShadowException;
	public abstract void visit(ASTPrimarySuffix node, TACData tac) throws ShadowException;
	public abstract void visit(ASTLiteral node, TACData tac) throws ShadowException;
	public abstract void visit(ASTIntegerLiteral node, TACData tac) throws ShadowException;
	public abstract void visit(ASTBooleanLiteral node, TACData tac) throws ShadowException;
	public abstract void visit(ASTNullLiteral node, TACData tac) throws ShadowException;
	public abstract void visit(ASTArguments node, TACData tac) throws ShadowException;
	public abstract void visit(ASTArgumentList node, TACData tac) throws ShadowException;
	public abstract void visit(ASTAllocationExpression node, TACData tac) throws ShadowException;
	public abstract void visit(ASTArrayAllocation node, TACData tac) throws ShadowException;
	public abstract void visit(ASTConstructorInvocation node, TACData tac) throws ShadowException;
	public abstract void visit(ASTArrayDimsAndInits node, TACData tac) throws ShadowException;
	public abstract void visit(ASTStatement node, TACData tac) throws ShadowException;
	public abstract void visit(ASTAssertStatement node, TACData tac) throws ShadowException;
	public abstract void visit(ASTLabeledStatement node, TACData tac) throws ShadowException;
	public abstract void visit(ASTBlock node, TACData tac) throws ShadowException;
	public abstract void visit(ASTBlockStatement node, TACData tac) throws ShadowException;
	public abstract void visit(ASTLocalVariableDeclaration node, TACData tac) throws ShadowException;
	public abstract void visit(ASTEmptyStatement node, TACData tac) throws ShadowException;
	public abstract void visit(ASTStatementExpression node, TACData tac) throws ShadowException;
	public abstract void visit(ASTSwitchStatement node, TACData tac) throws ShadowException;
	public abstract void visit(ASTSwitchLabel node, TACData tac) throws ShadowException;
	public abstract void visit(ASTIfStatement node, TACData tac) throws ShadowException;
	public abstract void visit(ASTWhileStatement node, TACData tac) throws ShadowException;
	public abstract void visit(ASTDoStatement node, TACData tac) throws ShadowException;
	public abstract void visit(ASTForeachStatement node, TACData tac) throws ShadowException;
	public abstract void visit(ASTForStatement node, TACData tac) throws ShadowException;
	public abstract void visit(ASTForInit node, TACData tac) throws ShadowException;
	public abstract void visit(ASTStatementExpressionList node, TACData tac) throws ShadowException;
	public abstract void visit(ASTForUpdate node, TACData tac) throws ShadowException;
	public abstract void visit(ASTBreakStatement node, TACData tac) throws ShadowException;
	public abstract void visit(ASTContinueStatement node, TACData tac) throws ShadowException;
	public abstract void visit(ASTReturnStatement node, TACData tac) throws ShadowException;
	public abstract void visit(ASTThrowStatement node, TACData tac) throws ShadowException;
	public abstract void visit(ASTSynchronizedStatement node, TACData tac) throws ShadowException;
	public abstract void visit(ASTTryStatement node, TACData tac) throws ShadowException;
	public abstract void visit(ASTRightRotate node, TACData tac) throws ShadowException;
	public abstract void visit(ASTRightShift node, TACData tac) throws ShadowException;
	public abstract void visit(ASTMemberValuePairs node, TACData tac) throws ShadowException;
	public abstract void visit(ASTMemberValuePair node, TACData tac) throws ShadowException;
	public abstract void visit(ASTMemberValue node, TACData tac) throws ShadowException;
	public abstract void visit(ASTMemberValueArrayInitializer node, TACData tac) throws ShadowException;
	public abstract void visit(ASTDefaultValue node, TACData tac) throws ShadowException;

	
	public void visit(Node node) throws ShadowException {
		node.jjtAccept(this, null);
	}
	
	@Override
	public Object visit(SimpleNode node, Boolean data) throws ShadowException {
		visit(node);
		return null;
	}

	@Override
	public Object visit(ASTCompilationUnit node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTPackageDeclaration node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTImportDeclaration node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTModifiers node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTTypeDeclaration node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTViewDeclaration node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTExtendsList node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTImplementsList node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTVersion node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTEnumDeclaration node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTEnumBody node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTEnumConstant node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTTypeParameters node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTTypeParameter node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTTypeBound node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTClassOrInterfaceBody node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTClassOrInterfaceBodyDeclaration node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTFieldDeclaration node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTVariableDeclarator node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTVariableDeclaratorId node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTVariableInitializer node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTArrayInitializer node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTMethodDeclaration node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTMethodDeclarator node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTFormalParameters node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTFormalParameter node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTConstructorDeclaration node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTDestructorDeclaration node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTExplicitConstructorInvocation node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTInitializer node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTType node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTReferenceType node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTStaticArrayType node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTFunctionType node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTClassOrInterfaceType node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTClassOrInterfaceTypeSuffix node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTTypeArguments node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTTypeArgument node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTWildcardBounds node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTPrimitiveType node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTResultType node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTResultTypes node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTName node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTUnqualifiedName node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTNameList node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTExpression node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTAssignmentOperator node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTConditionalExpression node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTConditionalOrExpression node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTConditionalExclusiveOrExpression node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTConditionalAndExpression node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTBitwiseOrExpression node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTBitwiseExclusiveOrExpression node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTBitwiseAndExpression node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTEqualityExpression node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTIsExpression node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTRelationalExpression node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTShiftExpression node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTRotateExpression node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTAdditiveExpression node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTMultiplicativeExpression node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTUnaryExpression node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTUnaryExpressionNotPlusMinus node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTCastExpression node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTCheckExpression node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTPrimaryExpression node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTSequence node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTMethodCall node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTPrimaryPrefix node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTPrimarySuffix node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTLiteral node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTIntegerLiteral node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTBooleanLiteral node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTNullLiteral node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTArguments node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTArgumentList node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTAllocationExpression node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTArrayAllocation node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTConstructorInvocation node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTArrayDimsAndInits node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTStatement node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTAssertStatement node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTLabeledStatement node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTBlock node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTBlockStatement node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTLocalVariableDeclaration node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTEmptyStatement node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTStatementExpression node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTSwitchStatement node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTSwitchLabel node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTIfStatement node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTWhileStatement node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTDoStatement node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTForeachStatement node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTForStatement node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTForInit node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTStatementExpressionList node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTForUpdate node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTBreakStatement node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTContinueStatement node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTReturnStatement node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTThrowStatement node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTSynchronizedStatement node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTTryStatement node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTRightRotate node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTRightShift node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTMemberValuePairs node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTMemberValuePair node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTMemberValue node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTMemberValueArrayInitializer node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

	@Override
	public Object visit(ASTDefaultValue node, Boolean data) throws ShadowException {
		visit(node, this.data);
		return null;
	}

}
