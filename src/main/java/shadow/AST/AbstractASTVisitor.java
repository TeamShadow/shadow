package shadow.AST;

import java.io.File;
import java.util.List;

import shadow.Loggers;
import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.*;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.TypeCheckException;
import shadow.typecheck.TypeCheckException.Error;
import shadow.typecheck.type.Type;

public class AbstractASTVisitor extends ErrorReporter implements ShadowParserVisitor {
	
	private File file;
	private int lineStart = -1;
	private int lineEnd = -1;
	private int columnStart = -1;
	private int columnEnd = -1;
	
	public AbstractASTVisitor() {
		super(Loggers.TYPE_CHECKER);
	}
	
	public void setLocation(Node node) {
		file = node.getFile();
		lineStart = node.getLineStart();
		lineEnd = node.getLineEnd();
		columnStart = node.getColumnStart();
		columnEnd = node.getColumnEnd();
	}
	
	public void setFile(File file)
	{
		this.file = file;
		lineStart = -1;
		lineEnd = -1;
		columnStart = -1;
		columnEnd = -1;
	}
	
	public File getFile()
	{
		return file;
	}
	
	public void setLineStart(int line)
	{
		this.lineStart = line;
	}
	
	public int getLineStart()
	{
		return lineStart;
	}
	
	public void setLineEnd(int line)
	{
		this.lineEnd = line;
	}
	
	public int getLineEnd()
	{
		return lineEnd;
	}
	
	public void setColumnStart(int column)
	{
		this.columnStart = column;
	}
	
	public void setColumnEnd(int column)
	{
		this.columnEnd = column;
	}
	
	public int getColumnStart()
	{
		return columnStart;
	}
	
	public int getColumnEnd()
	{
		return columnEnd;
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
	public Object visit(ASTType node, Boolean secondVisit) throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTReferenceType node, Boolean secondVisit)
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
	public Object visit(ASTArrayDimensions node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTBreakOrContinueStatement node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTIsList node, Boolean data)
			throws ShadowException {
		return WalkType.PRE_CHILDREN;
	}
		
	/**
	 * Adds an error to the main list of errors.
	 * @param error				kind of error
	 * @param message			message explaining error
	 * @param errorTypes		types associated with error
	 */
	protected final void addError(Error error, String message, Type... errorTypes) {
		if( containsUnknown(errorTypes) )
			return; // Don't add error if it has an unknown type in it.
				
		message = makeMessage(error, message, getFile(), getLineStart(), getLineEnd(),
				getColumnStart(), getColumnEnd());		
		errorList.add(new TypeCheckException(error, message));
	}
	
	/**
	 * Adds a temporary list of errors to the main list of errors.
	 * @param errors			list of errors
	 */
	protected final void addErrors(List<TypeCheckException> errors) {		
		if( errors != null )
			for( TypeCheckException error : errors )
				addError( error.getError(), error.getMessage() );
	}
	
	/**
	 * Adds an error associated with a node to the main list of errors.
	 * @param node				node related to error	
	 * @param error				kind of error
	 * @param message			message explaining error
	 * @param errorTypes		types associated with error
	 */
	@Override
	public final void addError(Node node, Error error, String message, Type... errorTypes) {
		if( containsUnknown(errorTypes) )
			return; // Don't add error if it has an unknown type in it.
		
		if( node == null )
			addError(error, message, errorTypes);
		else {			
			message = makeMessage(error, message, node.getFile(), node.getLineStart(),
					node.getLineEnd(), node.getColumnStart(), node.getColumnEnd() );
			errorList.add(new TypeCheckException(error, message));
		}
	}
	
	/**
	 * Adds a warning to the main list of warnings.	
	 * @param warning			kind of warning
	 * @param message			message explaining warning
	 */
	protected final void addWarning(Error warning, String message) {
		message = makeMessage(warning, message, getFile(), getLineStart(), getLineEnd(),
				getColumnStart(), getColumnEnd());		
		warningList.add(new TypeCheckException(warning, message));
	}
	
	
	/**
	 * Adds a warning associated with a node to the main list of warnings.
	 * @param node				node related to warning	
	 * @param warning			kind of warning
	 * @param message			message explaining warning
	 */
	@Override
	public final void addWarning(Node node, Error warning, String message) {
		if( node == null )
			addWarning(warning, message);
		else {		
			message = makeMessage(warning, message, node.getFile(), node.getLineStart(),
					node.getLineEnd(), node.getColumnStart(), node.getColumnEnd() );
			warningList.add(new TypeCheckException(warning, message));
		}
	}
}
