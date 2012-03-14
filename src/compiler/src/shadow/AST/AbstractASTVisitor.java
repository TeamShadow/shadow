package shadow.AST;

import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.*;

public abstract class AbstractASTVisitor implements ShadowParserVisitor {

	@Override
	public Object visit(SimpleNode node, Boolean secondVisit) throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTCompilationUnit node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTPackageDeclaration node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTImportDeclaration node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTModifiers node, Boolean secondVisit) throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTTypeDeclaration node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTViewDeclaration node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTExtendsList node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTImplementsList node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}
	
	@Override
	public Object visit(ASTVersion node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTEnumDeclaration node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTEnumBody node, Boolean secondVisit) throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTEnumConstant node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTTypeParameters node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTTypeParameter node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTTypeBound node, Boolean secondVisit) throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTClassOrInterfaceBody node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTClassOrInterfaceBodyDeclaration node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTFieldDeclaration node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTVariableDeclarator node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTVariableDeclaratorId node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTVariableInitializer node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTArrayInitializer node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTMethodDeclaration node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTMethodDeclarator node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTMethodCall node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTFormalParameters node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTFormalParameter node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTConstructorDeclaration node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTDestructorDeclaration node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTExplicitConstructorInvocation node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTInitializer node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTType node, Boolean secondVisit) throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTReferenceType node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTStaticArrayType node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTFunctionType node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTClassOrInterfaceType node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTTypeArguments node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTTypeArgument node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTWildcardBounds node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTPrimitiveType node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTResultType node, Boolean secondVisit) throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTResultTypes node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTName node, Boolean secondVisit) throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTNameList node, Boolean secondVisit) throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTExpression node, Boolean secondVisit) throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTAssignmentOperator node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTConditionalExpression node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTConditionalOrExpression node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}
	
	@Override
	public Object visit(ASTConditionalExclusiveOrExpression node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTConditionalAndExpression node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTBitwiseOrExpression node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTBitwiseExclusiveOrExpression node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTBitwiseAndExpression node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTEqualityExpression node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTIsExpression node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTRelationalExpression node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTShiftExpression node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTRotateExpression node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTAdditiveExpression node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTMultiplicativeExpression node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTUnaryExpression node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTUnaryExpressionNotPlusMinus node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTCastExpression node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}
	
	@Override
	public Object visit(ASTCheckExpression node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}


	@Override
	public Object visit(ASTPrimaryExpression node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTPrimaryPrefix node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTPrimarySuffix node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTLiteral node, Boolean secondVisit) throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTBooleanLiteral node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTNullLiteral node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTArguments node, Boolean secondVisit) throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTArgumentList node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTAllocationExpression node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTArrayDimsAndInits node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTStatement node, Boolean secondVisit) throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTAssertStatement node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTLabeledStatement node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTBlock node, Boolean secondVisit) throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTBlockStatement node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTLocalVariableDeclaration node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTEmptyStatement node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTStatementExpression node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTSwitchStatement node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTSwitchLabel node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTIfStatement node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTWhileStatement node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTDoStatement node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTForeachStatement node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTForStatement node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTForInit node, Boolean secondVisit) throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTStatementExpressionList node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTForUpdate node, Boolean secondVisit) throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTBreakStatement node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTContinueStatement node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTReturnStatement node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTThrowStatement node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTSynchronizedStatement node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTTryStatement node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTRightRotate node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTRightShift node, Boolean secondVisit) throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTMemberValuePairs node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTMemberValuePair node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTMemberValue node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTMemberValueArrayInitializer node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTDefaultValue node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}
	
	@Override
	public Object visit(ASTSequence node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}
	
	@Override
	public Object visit(ASTIntegerLiteral node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}
	
	@Override
	public Object visit(ASTUnqualifiedName node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}
	
	@Override
	public Object visit(ASTClassOrInterfaceTypeSuffix node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}
	
	@Override
	public Object visit(ASTArrayAllocation node, Boolean secondVisit)
		throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}
		
	@Override
	public Object visit(ASTConstructorInvocation node, Boolean secondVisit)
		throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}
}
