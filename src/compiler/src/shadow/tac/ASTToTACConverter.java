package shadow.tac;

import shadow.parser.javacc.*;
import shadow.tac.nodes.TACAssign;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACLabel;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACPhi;
import shadow.tac.nodes.TACPhiBranch;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACVariable;
import shadow.typecheck.type.Type;

public class ASTToTACConverter extends AbstractASTToTACVisitor
{

	@Override
	public void visit(ASTCompilationUnit node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTPackageDeclaration node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTImportDeclaration node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTTypeDeclaration node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTViewDeclaration node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTClassOrInterfaceDeclaration node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTClassOrInterfaceBody node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTClassOrInterfaceBodyDeclaration node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTFieldDeclaration node, TACData tac) throws ShadowException
	{
		tac.appendChildren();
	}

	@Override
	public void visit(ASTMethodDeclaration node, TACData tac) throws ShadowException
	{
		tac.appendChildren();
		if (!(tac.getNode() instanceof TACReturn))
			tac.append(new TACReturn());
	}

	@Override
	public void visit(ASTMethodDeclarator node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTConstructorDeclaration node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTDestructorDeclaration node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTBlock node, TACData tac) throws ShadowException
	{
		tac.appendChildren();
	}

	@Override
	public void visit(ASTLocalVariableDeclaration node, TACData tac) throws ShadowException
	{
		tac.appendChildren();
	}

	@Override
	public void visit(ASTVariableDeclarator node, TACData tac) throws ShadowException
	{
		tac.appendChildren();
		TACNode value;
		if (tac.getChildCount() == 1)
			tac.append(value = getDefault(node.getType()));
		else
			value = tac.getNode();
		tac.append(new TACAssign(tac.getChildNode(0), value));
	}

	@Override
	public void visit(ASTVariableDeclaratorId node, TACData tac) throws ShadowException
	{
		tac.append(new TACVariable(node.getType(), node.getImage(), node.isField()));
	}

	@Override
	public void visit(ASTVariableInitializer node, TACData tac) throws ShadowException
	{
		tac.appendChildren();
	}

	@Override
	public void visit(ASTArrayInitializer node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTStatementExpression node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTExpression node, TACData tac) throws ShadowException
	{
		tac.appendChildren();
	}

	@Override
	public void visit(ASTAssignmentOperator node, TACData tac) throws ShadowException
	{
		TACNode result;
		if (node.isImageNull())
			tac.appendChildren();
		else if (node.getImage().length() == 1)
		{
			int index = tac.getChildCount();
			tac.appendChild(index);
			result = tac.getChildNode(index);
			while (--index >= 0)
			{
				tac.appendChild(index);
				result = new TACAssign(tac.getChildNode(index), result);
			}
		}
		else
		{
			tac.appendChildren();
			TACBinary.Operator operation = TACBinary.Operator.parse(
					node.jjtGetChild(1).getImage().charAt(0));
			if (operation != null)
				tac.append(result = new TACBinary(tac.getChildNode(0), operation, tac.getChildNode(2)));
			else
				result = tac.getChildNode(2);
			tac.append(new TACAssign(tac.getChildNode(0), result));
		}
	}

	@Override
	public void visit(ASTConditionalExpression node, TACData tac) throws ShadowException
	{
		if (node.isImageNull())
			tac.appendChildren();
		else
		{
			TACLabel trueLabel = new TACLabel(), falseLabel = new TACLabel();
			TACPhi phi = new TACPhi(node.getType(), trueLabel, falseLabel);
			tac.appendChild(0);
			tac.append(new TACBranch(tac.getChildNode(0), trueLabel, falseLabel));
			tac.append(trueLabel);
			tac.appendChild(1);
			tac.append(new TACPhiBranch(trueLabel, phi));
			tac.append(falseLabel);
			tac.appendChild(2);
			tac.append(new TACPhiBranch(falseLabel, phi));
			tac.append(phi);
		}
	}

	@Override
	public void visit(ASTConditionalOrExpression node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTConditionalExclusiveOrExpression node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTConditionalAndExpression node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTBitwiseOrExpression node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTBitwiseExclusiveOrExpression node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTBitwiseAndExpression node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTEqualityExpression node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTIsExpression node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTRelationalExpression node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTShiftExpression node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTRotateExpression node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTAdditiveExpression node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTMultiplicativeExpression node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTUnaryExpression node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTUnaryExpressionNotPlusMinus node, TACData tac) throws ShadowException
	{
		
	}


	@Override
	public void visit(ASTModifiers node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTExtendsList node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTImplementsList node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTVersion node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTEnumDeclaration node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTEnumBody node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTEnumConstant node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTTypeParameters node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTTypeParameter node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTTypeBound node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTFormalParameters node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTFormalParameter node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTExplicitConstructorInvocation node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTInitializer node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTType node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTReferenceType node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTStaticArrayType node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTFunctionType node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTClassOrInterfaceType node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTClassOrInterfaceTypeSuffix node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTTypeArguments node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTTypeArgument node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTWildcardBounds node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTPrimitiveType node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTResultType node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTResultTypes node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTName node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTUnqualifiedName node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTNameList node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTCastExpression node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTCheckExpression node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTPrimaryExpression node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTSequence node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTMethodCall node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTPrimaryPrefix node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTPrimarySuffix node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTLiteral node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTIntegerLiteral node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTBooleanLiteral node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTNullLiteral node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTArguments node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTArgumentList node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTAllocationExpression node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTArrayAllocation node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTConstructorInvocation node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTArrayDimsAndInits node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTStatement node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTAssertStatement node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTLabeledStatement node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTBlockStatement node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTEmptyStatement node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTSwitchStatement node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTSwitchLabel node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTIfStatement node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTWhileStatement node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTDoStatement node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTForeachStatement node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTForStatement node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTForInit node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTStatementExpressionList node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTForUpdate node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTBreakStatement node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTContinueStatement node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTReturnStatement node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTThrowStatement node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTSynchronizedStatement node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTTryStatement node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTRightRotate node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTRightShift node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTMemberValuePairs node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTMemberValuePair node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTMemberValue node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTMemberValueArrayInitializer node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTDefaultValue node, TACData tac) throws ShadowException
	{
		
	}



	private TACLiteral getDefault(Type type) {
		if (type.isPrimitive())
			return new TACLiteral(type, "0");
		return new TACLiteral(type, "null");
	}

	private void visitBinary(SimpleNode node, TACData tac)
	{
		tac.appendChild(0);
		SimpleNode firstOperand = (SimpleNode)node.jjtGetChild(0);
		TACNode firstNode = firstOperand.getNode();
		node.appendNode(firstOperand);
		for (int i = 0; i < node.getImage().length(); i++)
		{
			TACBinary.Operator operation = TACBinary.Operator.parse(node.getImage().charAt(i));
			SimpleNode secondOperand = (SimpleNode)node.jjtGetChild(i + 1);
			node.appendNode(secondOperand);
			firstNode = new TACBinary(firstNode, operation, secondOperand.getNode());
			node.appendNode(firstNode);
		}
	}
}
