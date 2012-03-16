package shadow.tac;

import shadow.parser.javacc.*;
import shadow.tac.nodes.TACAllocation;
import shadow.tac.nodes.TACAssign;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACBranchPhi;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACComparison;
import shadow.tac.nodes.TACLabel;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACPhi;
import shadow.tac.nodes.TACPrefixed;
import shadow.tac.nodes.TACReference;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACSequence;
import shadow.tac.nodes.TACUnary;
import shadow.tac.nodes.TACVariable;
import shadow.typecheck.MethodSignature;
import shadow.typecheck.type.ClassInterfaceBaseType;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.Type;

public class ASTToTACConverter extends AbstractASTToTACVisitor
{
	protected ClassInterfaceBaseType outerType;

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
		tac.appendChildren();
		if (!(tac.getNode() instanceof TACReturn))
			tac.append(new TACReturn());
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
	public void visit(ASTBlockStatement node, TACData tac) throws ShadowException
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
		tac.appendChild(0);
		TACNode value;
		if (!tac.hasChild(1))
		{
			value = getDefault(node.getType());
			tac.append(value);
		}
		else
		{
			tac.appendChild(1);
			value = tac.getChildNode(1);
		}
		tac.append(new TACAssign(tac.getChildNode(0), value));
	}

	@Override
	public void visit(ASTVariableDeclaratorId node, TACData tac) throws ShadowException
	{
		TACVariable var = new TACVariable(node.jjtGetParent().getType(), node.getImage(), node.jjtGetParent().isField());
		if (node.jjtGetParent().isField())
		{
			TACVariable implicitThis = new TACVariable(outerType, "this", false);
			tac.append(implicitThis);
			var.setPrefix(implicitThis);
		}
		else
			tac.append(new TACAllocation(var));
		tac.append(var);
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
	public void visit(ASTStatement node, TACData tac) throws ShadowException
	{
		tac.appendChildren();
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
		TACLabel trueLabel = new TACLabel("true"),
				falseLabel = new TACLabel("false"),
				endLabel = new TACLabel("end");
		tac.appendChild(0);
		tac.append(new TACBranch(tac.getChildNode(0), trueLabel, falseLabel));
		tac.append(trueLabel);
		tac.appendChild(1);
		tac.append(new TACBranch(endLabel));
		tac.append(falseLabel);
		tac.appendChild(2);
		tac.append(new TACBranch(endLabel));
		tac.append(endLabel);
	}

	@Override
	public void visit(ASTWhileStatement node, TACData tac) throws ShadowException
	{
		visitLoop(node, tac, 0, 1, false);
	}

	@Override
	public void visit(ASTDoStatement node, TACData tac) throws ShadowException
	{
		visitLoop(node, tac, 1, 0, true);
	}

	@Override
	public void visit(ASTForStatement node, TACData tac) throws ShadowException
	{
		int condition = 0;
		if (node.jjtGetChild(condition) instanceof ASTForInit)
			tac.appendChild(condition++);
		int body = condition + 1;
		if (node.jjtGetChild(body) instanceof ASTForUpdate)
			tac.getChild(++body).append(tac.getChild(body - 1));
		visitLoop(node, tac, condition, body, false);
	}

	@Override
	public void visit(ASTForeachStatement node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTForInit node, TACData tac) throws ShadowException
	{
		tac.appendChildren();
	}

	@Override
	public void visit(ASTForUpdate node, TACData tac) throws ShadowException
	{
		tac.appendChildren();
	}

	@Override
	public void visit(ASTStatementExpressionList node, TACData tac) throws ShadowException
	{
		tac.appendChildren();
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
	public void visit(ASTThrowStatement node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTMethodCall node, TACData tac) throws ShadowException
	{
		tac.appendChildren();
		MethodSignature ms = new MethodSignature((MethodType)node.getType(), node.getImage(), node);
		TACSequence args = new TACSequence();
		for (int i = 0; i < tac.getChildCount(); i++)
			args.addNode(tac.getChildNode(i));
		tac.append(args);
		tac.append(new TACCall(ms, args));
	}

	@Override
	public void visit(ASTReturnStatement node, TACData tac) throws ShadowException
	{
		tac.appendChildren();
		if (!tac.hasChild(0))
			tac.append(new TACReturn());
		else
			tac.append(new TACReturn(tac.getChildNode(0)));
	}

	@Override
	public void visit(ASTSequence node, TACData tac) throws ShadowException
	{
		TACSequence sequence = new TACSequence();
		tac.appendChildren();
		for (int i = 0; i < node.jjtGetNumChildren(); i++)
			sequence.addNode(tac.getChildNode(i));
		tac.append(sequence);
	}
	@Override
	public void visit(ASTExpression node, TACData tac) throws ShadowException
	{
		TACNode result;
		if (node.isImageNull())
			tac.appendChildren();
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
	public void visit(ASTStatementExpression node, TACData tac) throws ShadowException
	{
		TACNode result;
		if (node.isImageNull())
			tac.appendChildren();
		else if (node.getImage().length() == 1)
		{
			int index = tac.getChildCount() - 1;
			tac.appendChild(index);
			result = tac.getChildNode(index);
			while (--index >= 0)
			{
				tac.appendChild(index);
				result = new TACAssign(tac.getChildNode(index), result);
				tac.append(result);
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
	public void visit(ASTAssignmentOperator node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTConditionalExpression node, TACData tac) throws ShadowException
	{
		if (node.isImageNull())
			tac.appendChildren();
		else
		{
			TACLabel trueLabel = new TACLabel("true"),
					falseLabel = new TACLabel("false"),
					endLabel = new TACLabel("end");
			TACPhi phi = new TACPhi(node.getType(), tac.getChildNode(1), tac.getChildNode(2));
			tac.appendChild(0);
			tac.append(new TACBranch(tac.getChildNode(0), trueLabel, falseLabel));
			tac.append(trueLabel);
			tac.appendChild(1);
			tac.append(new TACBranchPhi(endLabel, tac.getChildNode(1), phi));
			tac.append(falseLabel);
			tac.appendChild(2);
			tac.append(new TACBranchPhi(endLabel, tac.getChildNode(2), phi));
			tac.append(endLabel);
			tac.append(phi);
		}
	}

	@Override
	public void visit(ASTConditionalOrExpression node, TACData tac) throws ShadowException
	{
		visitBinary(node, tac);
	}

	@Override
	public void visit(ASTConditionalExclusiveOrExpression node, TACData tac) throws ShadowException
	{
		visitBinary(node, tac);
	}

	@Override
	public void visit(ASTConditionalAndExpression node, TACData tac) throws ShadowException
	{
		visitBinary(node, tac);
	}

	@Override
	public void visit(ASTBitwiseOrExpression node, TACData tac) throws ShadowException
	{
		visitBinary(node, tac);
	}

	@Override
	public void visit(ASTBitwiseExclusiveOrExpression node, TACData tac) throws ShadowException
	{
		visitBinary(node, tac);
	}

	@Override
	public void visit(ASTBitwiseAndExpression node, TACData tac) throws ShadowException
	{
		visitBinary(node, tac);
	}

	@Override
	public void visit(ASTEqualityExpression node, TACData tac) throws ShadowException
	{
		visitComparison(node, tac);
	}

	@Override
	public void visit(ASTIsExpression node, TACData tac) throws ShadowException
	{
		visitComparison(node, tac);
	}

	@Override
	public void visit(ASTRelationalExpression node, TACData tac) throws ShadowException
	{
		visitComparison(node, tac);
	}

	@Override
	public void visit(ASTShiftExpression node, TACData tac) throws ShadowException
	{
		visitBinary(node, tac);
	}

	@Override
	public void visit(ASTRightShift node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTRotateExpression node, TACData tac) throws ShadowException
	{
		visitBinary(node, tac);
	}

	@Override
	public void visit(ASTRightRotate node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTAdditiveExpression node, TACData tac) throws ShadowException
	{
		visitBinary(node, tac);
	}

	@Override
	public void visit(ASTMultiplicativeExpression node, TACData tac) throws ShadowException
	{
		visitBinary(node, tac);
	}

	@Override
	public void visit(ASTUnaryExpression node, TACData tac) throws ShadowException
	{
		visitUnary(node, tac);
	}

	@Override
	public void visit(ASTUnaryExpressionNotPlusMinus node, TACData tac) throws ShadowException
	{
		visitUnary(node, tac);
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
		tac.appendChildren();
		tac.append(new TACCast(node.getType(), tac.getChildNode(0)));
	}

	@Override
	public void visit(ASTCheckExpression node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTPrimaryExpression node, TACData tac) throws ShadowException
	{
		TACNode lastNode = null;
		for (int i = 0; i < tac.getChildCount(); i++)
		{
			TACNode part = tac.getChildNode(i);
			if (part instanceof TACPrefixed)
			{
				TACPrefixed prefixed = (TACPrefixed)part;
				if (prefixed.expectsPrefix())
				{
					if (lastNode == null)
					{
						lastNode = new TACVariable(outerType, "this", false);
						tac.append(lastNode);
					}
					prefixed.setPrefix(lastNode);
				}
			}
			tac.appendChild(i);
			lastNode = part;
		}
	}

	@Override
	public void visit(ASTPrimaryPrefix node, TACData tac) throws ShadowException
	{
		visitPrimary(node, tac);
	}

	@Override
	public void visit(ASTPrimarySuffix node, TACData tac) throws ShadowException
	{
		visitPrimary(node, tac);
	}

	@Override
	public void visit(ASTAllocationExpression node, TACData tac) throws ShadowException
	{
		tac.appendChildren();
	}

	@Override
	public void visit(ASTArrayAllocation node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTConstructorInvocation node, TACData tac) throws ShadowException
	{
		tac.appendChildren();
		MethodSignature ms = new MethodSignature((MethodType)node.getType(), "constructor", node);
		TACAllocation allocation = new TACAllocation(ms.getMethodType().getOuter(), null, true);
		TACSequence argSequence = (TACSequence)tac.getChildNode(1);
		tac.append(allocation);
		tac.append(new TACCall(allocation, ms, argSequence));
		tac.append(new TACReference(allocation));
	}

	@Override
	public void visit(ASTArguments node, TACData tac) throws ShadowException
	{
		if (tac.hasChild(0))
			tac.appendChildren();
		else
			tac.append(new TACSequence());
	}

	@Override
	public void visit(ASTArgumentList node, TACData tac) throws ShadowException
	{
		tac.appendChildren();
		TACSequence sequence = new TACSequence();
		for (int i = 0; i < tac.getChildCount(); i++)
			sequence.addNode(tac.getChildNode(i));
		tac.append(sequence);
	}

	@Override
	public void visit(ASTLiteral node, TACData tac) throws ShadowException
	{
		if (tac.hasChild(0))
			tac.appendChildren();
		else
			tac.append(new TACLiteral(node.getType(), node.getImage()));
	}

	@Override
	public void visit(ASTIntegerLiteral node, TACData tac) throws ShadowException
	{
		tac.append(new TACLiteral(node.getType(), node.getImage()));
	}

	@Override
	public void visit(ASTBooleanLiteral node, TACData tac) throws ShadowException
	{
		tac.append(new TACLiteral(node.getType(), node.getImage()));
	}

	@Override
	public void visit(ASTNullLiteral node, TACData tac) throws ShadowException
	{
		tac.append(new TACLiteral(node.getType(), node.getImage()));
	}

	@Override
	public void visit(ASTArrayDimsAndInits node, TACData tac) throws ShadowException
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
	public void visit(ASTSynchronizedStatement node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTTryStatement node, TACData tac) throws ShadowException
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



	private TACLiteral getDefault(Type type)
	{
		if (type.isPrimitive())
			return new TACLiteral(type, "0");
		return new TACLiteral(type, "null");
	}

	private void visitPrimary(SimpleNode node, TACData tac)
	{
		if (node.isImageNull())
			tac.appendChildren();
		else
			tac.append(new TACVariable(node.getType(), node.getImage(), node.isField()));
	}

	private void visitLoop(SimpleNode node, TACData tac, int condition, int body, boolean atLeastOnce)
	{
		TACLabel bodyLabel = new TACLabel("body"),
				conditionLabel = new TACLabel("condition"),
				endLabel = new TACLabel("end");
		tac.append(new TACBranch(atLeastOnce ? bodyLabel : conditionLabel));
		tac.append(bodyLabel);
		tac.appendChild(body);
		tac.append(new TACBranch(conditionLabel));
		tac.append(conditionLabel);
		tac.appendChild(condition);
		tac.append(new TACBranch(tac.getChildNode(condition), bodyLabel, endLabel));
		tac.append(endLabel);
	}

	private void visitUnary(SimpleNode node, TACData tac)
	{
		tac.appendChildren();
		if (!node.isImageNull())
			tac.append(new TACUnary(TACUnary.Operator.parse(node.getImage().charAt(0)),
					tac.getChildNode(0)));
	}

	private void visitBinary(SimpleNode node, TACData tac)
	{
		if (node.isImageNull())
			tac.appendChildren();
		else
		{
			TACNode first = tac.getChildNode(0);
			tac.appendChild(0);
			for (int i = 0; i < node.getImage().length(); )
			{
				first = new TACBinary(first, TACBinary.Operator.parse(
						node.getImage().charAt(i)), tac.getChildNode(++i));
				tac.appendChild(i);
				tac.append(first);
			}
		}
	}

	private void visitComparison(SimpleNode node, TACData tac)
	{
		if (node.isImageNull())
			tac.appendChildren();
		else
		{
			TACNode first = tac.getChildNode(0);
			tac.appendChild(0);
			for (int i = 0; i < node.getImage().length(); )
			{
				first = new TACComparison(first, TACComparison.Operator.parse(
						node.getImage().charAt(i)), tac.getChildNode(++i));
				tac.appendChild(i);
				tac.append(first);
			}
		}
	}
}
