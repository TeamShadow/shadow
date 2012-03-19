package shadow.tac;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import shadow.parser.javacc.*;
import shadow.tac.nodes.TACAllocation;
import shadow.tac.nodes.TACAssign;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACPhiBranch;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACComparison;
import shadow.tac.nodes.TACGetLength;
import shadow.tac.nodes.TACIndexed;
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
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassInterfaceBaseType;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;

public class ASTToTACConverter extends AbstractASTToTACVisitor
{
	protected ClassInterfaceBaseType outerType;
	private Deque<TACLabel> recoverBlock = new ArrayDeque<TACLabel>();

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
		if (!(tac.appendAndGetChild(1) instanceof TACReturn))
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
		visitLoop(tac, 0, 1, false);
	}

	@Override
	public void visit(ASTDoStatement node, TACData tac) throws ShadowException
	{
		visitLoop(tac, 1, 0, true);
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
		visitLoop(tac, condition, body, false);
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
	public void visit(ASTTryStatement node, TACData tac) throws ShadowException
	{
		if (tac == null)
		{
			if (node.getCatches() != 0)
				throw new UnsupportedOperationException();
			if (node.hasRecover())
				recoverBlock.push(new TACLabel("recover"));
			if (node.hasFinally())
				throw new UnsupportedOperationException();
		}
		else
		{
			TACLabel endLabel = new TACLabel("end");
			int index = 0;
			tac.appendChild(index++);
			tac.append(new TACBranch(endLabel));
			for (int i = 0; i < node.getCatches(); i++)
			{
				index++;
				index++;
				tac.append(new TACBranch(endLabel));
			}
			if (node.hasRecover())
			{
				tac.append(recoverBlock.pop());
				tac.appendChild(index++);
				tac.append(new TACBranch(endLabel));
			}
			if (node.hasFinally())
			{
				index++;
				tac.append(new TACBranch(endLabel));
			}
			tac.append(endLabel);
		}
	}

	@Override
	public void visit(ASTThrowStatement node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTCheckExpression node, TACData tac) throws ShadowException
	{
		TACNode operand = tac.appendAndGetChild(0);
		TACLabel checked = new TACLabel("checked");
		tac.append(new TACComparison(operand, TACComparison.Operator.NOT_EQUAL, new TACLiteral(node.getType(), "null")));
		tac.append(new TACBranch(tac.getNode(), checked, recoverBlock.peek()));
		tac.append(checked);
		tac.append(new TACReference(operand));
	}

	@Override
	public void visit(ASTMethodCall node, TACData tac) throws ShadowException
	{
		MethodType type = (MethodType)node.getType();
		TACSequence args = new TACSequence();
		SequenceType argTypes = type.getParameterTypes();
		for (int i = 0; i < tac.getChildCount(); i++)
		{
			TACNode arg = tac.appendAndGetChild(i);
			Type expectedType = argTypes.get(i).getType();
			if (!arg.getType().equals(expectedType))
				tac.append(arg = new TACCast(expectedType, arg));
			args.addNode(arg);
		}
		tac.append(args);
		tac.append(new TACCall(node.getImage(), type, args));
	}

	@Override
	public void visit(ASTReturnStatement node, TACData tac) throws ShadowException
	{
		tac.appendChildren();
		if (tac.hasChild(0))
		{
			// TODO: figure out expected type
			tac.append(new TACReturn(tac.getChildNode(0)));
		}
		else
			tac.append(new TACReturn());
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
		if (node.isImageNull())
			tac.appendChildren();
		else
		{
			tac.appendChildren();
			TACNode result = tac.getChildNode(2);
			TACBinary.Operator operation = TACBinary.Operator.parse(
					node.jjtGetChild(1).getImage().charAt(0));
			if (operation != null)
				tac.append(result = new TACBinary(tac.getChildNode(0), operation, result));
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
					falseLabel = new TACLabel("false");
			TACPhi phi = new TACPhi(node.getType(), new TACLabel("phi"));
			tac.appendChild(0);
			tac.append(new TACBranch(tac.getChildNode(0), trueLabel, falseLabel));
			tac.append(trueLabel);
			tac.append(new TACPhiBranch(trueLabel, tac.appendAndGetChild(1), phi));
			tac.append(falseLabel);
			tac.append(new TACPhiBranch(falseLabel, tac.appendAndGetChild(2), phi));
			tac.append(phi.getLabel());
			tac.append(phi);
		}
	}

	@Override
	public void visit(ASTConditionalOrExpression node, TACData tac) throws ShadowException
	{
		if (node.isImageNull())
			tac.appendChildren();
		else
		{
			TACPhi phi = new TACPhi(Type.BOOLEAN, new TACLabel("phi"));
			TACLabel currentLabel = new TACLabel("start");
			tac.append(new TACBranch(currentLabel));
			int index;
			for (index = 0; index < tac.getChildCount() - 1; index++)
			{
				TACLabel nextLabel = new TACLabel("next" + index);
				tac.append(currentLabel);
				tac.append(new TACPhiBranch(currentLabel, tac.appendAndGetChild(index), phi, nextLabel));
				currentLabel = nextLabel;
			}
			tac.append(currentLabel);
			tac.append(new TACPhiBranch(currentLabel, tac.appendAndGetChild(index), phi));
			tac.append(phi.getLabel());
			tac.append(phi);
		}
	}

	@Override
	public void visit(ASTConditionalExclusiveOrExpression node, TACData tac) throws ShadowException
	{
		visitBinary(node.getImage(), tac);
	}

	@Override
	public void visit(ASTConditionalAndExpression node, TACData tac) throws ShadowException
	{
		if (node.isImageNull())
			tac.appendChildren();
		else
		{
			TACPhi phi = new TACPhi(Type.BOOLEAN, new TACLabel("phi"));
			TACLabel currentLabel = new TACLabel("start");
			tac.append(new TACBranch(currentLabel));
			int index;
			for (index = 0; index < tac.getChildCount() - 1; index++)
			{
				TACLabel nextLabel = new TACLabel("next" + index);
				tac.append(currentLabel);
				tac.append(new TACPhiBranch(currentLabel, tac.appendAndGetChild(index), nextLabel, phi));
				currentLabel = nextLabel;
			}
			tac.append(currentLabel);
			tac.append(new TACPhiBranch(currentLabel, tac.appendAndGetChild(index), phi));
			tac.append(phi.getLabel());
			tac.append(phi);
		}
	}

	@Override
	public void visit(ASTBitwiseOrExpression node, TACData tac) throws ShadowException
	{
		visitBinary(node.getImage(), tac);
	}

	@Override
	public void visit(ASTBitwiseExclusiveOrExpression node, TACData tac) throws ShadowException
	{
		visitBinary(node.getImage(), tac);
	}

	@Override
	public void visit(ASTBitwiseAndExpression node, TACData tac) throws ShadowException
	{
		visitBinary(node.getImage(), tac);
	}

	@Override
	public void visit(ASTEqualityExpression node, TACData tac) throws ShadowException
	{
		visitComparison(node.getImage(), tac);
	}

	@Override
	public void visit(ASTIsExpression node, TACData tac) throws ShadowException
	{
		visitComparison(node.getImage(), tac);
	}

	@Override
	public void visit(ASTRelationalExpression node, TACData tac) throws ShadowException
	{
		visitComparison(node.getImage(), tac);
	}

	@Override
	public void visit(ASTShiftExpression node, TACData tac) throws ShadowException
	{
		visitBinary(node.getImage(), tac);
	}

	@Override
	public void visit(ASTRightShift node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTRotateExpression node, TACData tac) throws ShadowException
	{
		visitBinary(node.getImage(), tac);
	}

	@Override
	public void visit(ASTRightRotate node, TACData tac) throws ShadowException
	{
		
	}

	@Override
	public void visit(ASTAdditiveExpression node, TACData tac) throws ShadowException
	{
		visitBinary(node.getImage(), tac);
	}

	@Override
	public void visit(ASTMultiplicativeExpression node, TACData tac) throws ShadowException
	{
		visitBinary(node.getImage(), tac);
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
		tac.append(new TACCast(node.getType(), tac.getChildNode(1)));
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
					Type expectedType = prefixed.expectedPrefixType();
					if (prefixed.getType() != null && expectedType != null)
						if (!prefixed.getType().equals(expectedType))
							tac.append(lastNode = new TACCast(expectedType, lastNode));
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
		TACSequence sizes = (TACSequence)tac.appendAndGetChild(1);
		tac.append(visitArrayAllocation(tac, (ArrayType)node.getType(), sizes.getNodes(), 0));
	}

	@Override
	public void visit(ASTArrayDimsAndInits node, TACData tac) throws ShadowException
	{
		tac.appendChildren();
		TACSequence sequence = new TACSequence();
		for (int i = 0; i < tac.getChildCount(); i++)
			sequence.addNode(tac.getChildNode(i));
		tac.append(sequence);
	}

	@Override
	public void visit(ASTConstructorInvocation node, TACData tac) throws ShadowException
	{
		tac.appendChildren();
		MethodType type = (MethodType)node.getType();
		TACAllocation allocation = new TACAllocation(type.getOuter(), null, true);
		TACSequence argSequence = (TACSequence)tac.getChildNode(1);
		tac.append(allocation);
		tac.append(new TACCall(allocation, "constructor", type, argSequence));
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
		tac.append(new TACLiteral(Type.BOOLEAN, node.isTrue() ? "true" : "false"));
	}

	@Override
	public void visit(ASTNullLiteral node, TACData tac) throws ShadowException
	{
		tac.append(new TACLiteral(Type.NULL, "null"));
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
		else if (node.getImage().charAt(0) == '[')
		{
			TACPrefixed[] prefixed = new TACPrefixed[tac.getChildCount() - 1];
			TACNode index = tac.appendAndGetChild(0);
			for (int i = 1; i < tac.getChildCount(); i++)
			{
				tac.append(prefixed[i - 1] = new TACGetLength(i));
				tac.append(new TACBinary(index, TACBinary.Operator.MULTIPLY, tac.getNode()));
				tac.append(index = new TACBinary(tac.appendAndGetChild(i), TACBinary.Operator.ADD, tac.getNode()));
			}
			tac.append(new TACIndexed(node.getType(), index, prefixed));
		}
		else
			tac.append(new TACVariable(node.getType(), node.getImage(), node.isField()));
	}

	private TACReference visitArrayAllocation(TACData tac, ArrayType type, List<TACNode> sizes, int sizeIndex)
	{
		int startIndex = sizeIndex;
		TACNode size = sizes.get(sizeIndex++);
		for (int i = 1; i < type.getDimensions(); i++)
			tac.append(size = new TACBinary(size, TACBinary.Operator.MULTIPLY, sizes.get(sizeIndex++)));
		TACAllocation alloc = new TACAllocation(type, size, sizes, startIndex);
		tac.append(alloc);
		if (sizeIndex < sizes.size())
		{
			TACVariable index = new TACVariable(Type.INT);
			tac.append(index);
			tac.append(new TACLiteral(Type.INT, 0));
			tac.append(new TACAssign(index, tac.getNode()));
			TACComparison condition = new TACComparison(index, TACComparison.Operator.NOT_EQUAL, size);
			TACLabel bodyLabel = new TACLabel("body"),
					conditionLabel = new TACLabel("condition"),
					endLabel = new TACLabel("end");
			tac.append(new TACBranch(conditionLabel));
			tac.append(bodyLabel);
			TACReference value = visitArrayAllocation(tac, (ArrayType)type.getBaseType(), sizes, sizeIndex);
			tac.append(new TACIndexed(type.getBaseType(), alloc, index));
			tac.append(new TACAssign(tac.getNode(), value));
			tac.append(new TACLiteral(Type.INT, 1));
			tac.append(new TACBinary(index, TACBinary.Operator.ADD, tac.getNode()));
			tac.append(new TACAssign(index, tac.getNode()));
			tac.append(new TACBranch(conditionLabel));
			tac.append(conditionLabel);
			tac.append(condition);
			tac.append(new TACBranch(condition, bodyLabel, endLabel));
			tac.append(endLabel);
		}
		return new TACReference(alloc);
	}

	private void visitLoop(TACData tac, int condition, int body, boolean atLeastOnce)
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

	private void visitBinary(String image, TACData tac)
	{
		TACNode first = tac.appendAndGetChild(0);
		for (int i = 0; i < image.length(); i++)
			tac.append(first = new TACBinary(first,
					TACBinary.Operator.parse(image.charAt(i)),
					tac.appendAndGetChild(i + 1)));
	}

	private void visitComparison(String image, TACData tac)
	{
		TACNode first = tac.appendAndGetChild(0);
		for (int i = 0; i < image.length(); i++)
			tac.append(first = new TACComparison(first,
					TACComparison.Operator.parse(image.charAt(i)),
					tac.appendAndGetChild(i + 1)));
	}
}
