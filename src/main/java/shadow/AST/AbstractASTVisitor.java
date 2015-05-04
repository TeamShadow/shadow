package shadow.AST;

import java.io.File;

import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.*;

public class AbstractASTVisitor implements ShadowParserVisitor {
	
	private File file;
	private int line;
	private int column;
	
	public void setLocation(Node node)
	{
		file = node.getFile();
		line = node.getLine();
		column = node.getColumn();
	}
	
	public void setFile(File file)
	{
		this.file = file;
	}
	
	public File getFile()
	{
		return file;
	}
	
	public void setLine(int line)
	{
		this.line = line;
	}
	
	public int getLine()
	{
		return line;
	}
	
	public void setColumn(int column)
	{
		this.column = column;
	}
	
	public int getColumn()
	{
		return column;
	}

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
	public Object visit(ASTImportDeclaration node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTModifiers node, Boolean secondVisit) throws ShadowException {
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
	public Object visit(ASTCreateDeclaration node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTDestroyDeclaration node, Boolean secondVisit)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTExplicitCreateInvocation node, Boolean secondVisit)
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
	public Object visit(ASTStatement node, Boolean secondVisit) throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTAssertStatement node, Boolean secondVisit)
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
	public Object visit(ASTTryStatement node, Boolean secondVisit)
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
	public Object visit(ASTCreate node, Boolean secondVisit)
		throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTConcatenationExpression node, Boolean data)
		throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTQualifiedKeyword node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTScopeSpecifier node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTMethod node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTProperty node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}
	
	@Override
	public Object visit(ASTCoalesceExpression node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTSequenceAssignment node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}


	@Override
	public Object visit(ASTLocalDeclaration node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTLocalMethodDeclaration node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTBrackets node, Boolean data) throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTSubscript node, Boolean data) throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTAllocation node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTArrayCreate node, Boolean data) throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTDestroy node, Boolean data) throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTForeachInit node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTCatchStatement node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTFinallyStatement node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTRecoverStatement node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTCatchStatements node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTCreateBlock node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTInlineResults node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTCreateDeclarator node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTInlineMethodDefinition node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTSequenceRightSide node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTSequenceLeftSide node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTSequenceVariable node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}


	@Override
	public Object visit(ASTRightSide node, Boolean data)	
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTCopyExpression node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTGenericDeclaration node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTArrayDimensions node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}
}
