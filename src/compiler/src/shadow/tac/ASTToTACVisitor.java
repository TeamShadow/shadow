package shadow.tac;

import java.util.LinkedList;
import java.util.List;

import shadow.AST.ASTWalker.WalkType;
import shadow.parser.javacc.ASTAdditiveExpression;
import shadow.parser.javacc.ASTAllocationExpression;
import shadow.parser.javacc.ASTBitwiseAndExpression;
import shadow.parser.javacc.ASTBitwiseExclusiveOrExpression;
import shadow.parser.javacc.ASTBitwiseOrExpression;
import shadow.parser.javacc.ASTBlock;
import shadow.parser.javacc.ASTBlockStatement;
import shadow.parser.javacc.ASTBooleanLiteral;
import shadow.parser.javacc.ASTCastExpression;
import shadow.parser.javacc.ASTConditionalAndExpression;
import shadow.parser.javacc.ASTConditionalExclusiveOrExpression;
import shadow.parser.javacc.ASTConditionalExpression;
import shadow.parser.javacc.ASTConditionalOrExpression;
import shadow.parser.javacc.ASTConstructorDeclaration;
import shadow.parser.javacc.ASTConstructorInvocation;
import shadow.parser.javacc.ASTDestructorDeclaration;
import shadow.parser.javacc.ASTDoStatement;
import shadow.parser.javacc.ASTEqualityExpression;
import shadow.parser.javacc.ASTExpression;
import shadow.parser.javacc.ASTFieldDeclaration;
import shadow.parser.javacc.ASTForInit;
import shadow.parser.javacc.ASTForStatement;
import shadow.parser.javacc.ASTForUpdate;
import shadow.parser.javacc.ASTIfStatement;
import shadow.parser.javacc.ASTIntegerLiteral;
import shadow.parser.javacc.ASTIsExpression;
import shadow.parser.javacc.ASTLiteral;
import shadow.parser.javacc.ASTLocalVariableDeclaration;
import shadow.parser.javacc.ASTMethodCall;
import shadow.parser.javacc.ASTMethodDeclaration;
import shadow.parser.javacc.ASTMultiplicativeExpression;
import shadow.parser.javacc.ASTNullLiteral;
import shadow.parser.javacc.ASTPrimaryExpression;
import shadow.parser.javacc.ASTPrimaryPrefix;
import shadow.parser.javacc.ASTPrimarySuffix;
import shadow.parser.javacc.ASTRelationalExpression;
import shadow.parser.javacc.ASTReturnStatement;
import shadow.parser.javacc.ASTRotateExpression;
import shadow.parser.javacc.ASTSequence;
import shadow.parser.javacc.ASTShiftExpression;
import shadow.parser.javacc.ASTStatement;
import shadow.parser.javacc.ASTStatementExpression;
import shadow.parser.javacc.ASTStatementExpressionList;
import shadow.parser.javacc.ASTUnaryExpression;
import shadow.parser.javacc.ASTUnaryExpressionNotPlusMinus;
import shadow.parser.javacc.ASTVariableDeclarator;
import shadow.parser.javacc.ASTVariableInitializer;
import shadow.parser.javacc.ASTWhileStatement;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.SimpleNode;
import shadow.tac.nodes.TACAllocation;
import shadow.tac.nodes.TACAssign;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACComparison;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACPhi;
import shadow.tac.nodes.TACPhiBranch;
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

public class ASTToTACVisitor extends AbstractTACVisitor {
	private ClassInterfaceBaseType outerType;
	private List<TACVariable> variables;
	public ASTToTACVisitor(ClassInterfaceBaseType type)
	{
		outerType = type;
		variables = new LinkedList<TACVariable>();
	}
	
	@Override
	public void visit(ASTBlock node, TACDeclaration[] children) throws ShadowException
	{
		for (TACVariable var : variables.pop())
			node.appendNode(new TACAllocation(var));
		for (int i = 0; i < node.jjtGetNumChildren(); i++)
			node.appendNode((SimpleNode)node.jjtGetChild(i));
	}
	
	private void visitMethod(SimpleNode node, MethodSignature ms)
	{
		TACMethod method = new TACMethod(ms);
		for (int i = 1; i < node.jjtGetNumChildren(); i++)
			method.appendNode((SimpleNode)node.jjtGetChild(i));
		if (!(node.getExitNode() instanceof TACReturn))
			node.appendNode(new TACReturn());
	}
	
	@Override
	public void visit(ASTMethodDeclaration node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
			visitMethod(node);
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTConstructorDeclaration node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
			visitMethod(node);
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTDestructorDeclaration node, TACDeclaration[] children)
			throws ShadowException {
		if (secondVisit)
			visitMethod(node);
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTFieldDeclaration node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
		{
//			for (int i = 1; i < node.jjtGetNumChildren(); i++)
//				node.appendNode(new TACAllocation(node.getType(),
//						node.jjtGetChild(i).jjtGetChild(0).getImage()));
			for (int i = 1; i < node.jjtGetNumChildren(); i++)
				node.appendNode((SimpleNode)node.jjtGetChild(i));
//			for (int i = 1; i < node.jjtGetNumChildren(); i++)
//			{
//				SimpleNode varNode = (SimpleNode)node.jjtGetChild(i);
//				TACReference var = new TACReference(node.getType(), varNode.jjtGetChild(0).getImage());
//				node.appendNode(var);
////				variables.peekLast().add(var);
//				if (varNode.jjtGetNumChildren() == 2)
//				{
//					SimpleNode valueNode = (SimpleNode)varNode.jjtGetChild(1);
//					node.appendNode(valueNode);
//					node.appendNode(new TACAssign(var, valueNode.getNode()));
//				}
//				else
//					node.appendNode(new TACAssign(var, getDefault(node.getType())));
//			}
		}
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTLocalVariableDeclaration node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
			for (int i = 1; i < node.jjtGetNumChildren(); i++)
			{
				SimpleNode decNode = (SimpleNode)node.jjtGetChild(i);
				variables.peek().add((TACVariable)decNode.getEntryNode());
				node.appendNode(decNode);
			}
		return WalkType.POST_CHILDREN;
	}
	
	private TACLiteral getDefault(Type type) {
		if (type.isPrimitive())
			return new TACLiteral(type, "0");
		return new TACLiteral(type, "null");
	}
	
	@Override
	public void visit(ASTVariableDeclarator node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
		{
			Type type = node.getType();
			TACVariable var;
			if (node.isField())
			{
				TACVariable base = new TACVariable(outerType, "this", false);
				node.appendNode(base);
				var = new TACVariable(base, type, node.jjtGetChild(0).getImage(), node.isField());
			}
			else
				var = new TACVariable(type, node.jjtGetChild(0).getImage(), node.isField());
			node.appendNode(var);
	//		variables.peekLast().add(var);
			if (node.jjtGetNumChildren() == 2)
			{
				SimpleNode valueNode = (SimpleNode)node.jjtGetChild(1);
				node.appendNode(valueNode);
				node.appendNode(new TACAssign(var, valueNode.getNode()));
			}
			else
			{
				TACLiteral defaultValue = getDefault(type);
				node.appendNode(defaultValue);
				node.appendNode(new TACAssign(var, defaultValue));
			}
		}
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTExpression node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
			node.appendNode((SimpleNode)node.jjtGetChild(0));
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTStatement node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
			node.appendNode((SimpleNode)node.jjtGetChild(0));
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTBlockStatement node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
			node.appendNode((SimpleNode)node.jjtGetChild(0));
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTStatementExpressionList node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
			for (int i = 0; i < node.jjtGetNumChildren(); i++)
				node.appendNode((SimpleNode)node.jjtGetChild(i));
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTStatementExpression node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
		{
			if (node.isImageNull())
				node.appendNode((SimpleNode)node.jjtGetChild(0));
			else if (node.getImage().equals("="))
			{
				int index = node.jjtGetNumChildren() - 1;
				SimpleNode right = (SimpleNode)node.jjtGetChild(index);
				TACNode rightNode = right.getNode();
				node.appendNode(right);
				while (--index >= 0)
				{
					SimpleNode left = (SimpleNode)node.jjtGetChild(index);
					node.appendNode(left);
					rightNode = new TACAssign(left.getNode(), rightNode);
					node.appendNode(rightNode);
				}
			}
			else
			{
				SimpleNode firstOperand = (SimpleNode)node.jjtGetChild(0),
						secondOperand = (SimpleNode)node.jjtGetChild(2);
				TACBinary.Operator operation = TACBinary.Operator.parse(
						node.jjtGetChild(1).getImage().charAt(0));
				node.appendNode(firstOperand);
				node.appendNode(secondOperand);
				TACNode result;
				if (operation == null)
					result = secondOperand.getNode();
				else
					node.appendNode(result = new TACBinary(firstOperand.getNode(), operation, secondOperand.getNode()));
				node.appendNode(new TACAssign(firstOperand.getNode(), result));
			}
		}
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTVariableInitializer node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
			node.appendNode((SimpleNode)node.jjtGetChild(0));
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTPrimaryExpression node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
		{
			TACNode lastNode = null;
			for (int i = 0; i < node.jjtGetNumChildren(); i++)
			{
				SimpleNode part = (SimpleNode)node.jjtGetChild(i);
				TACNode partNode = part.getNode();
				if (partNode instanceof TACPrefixed)
				{
					TACPrefixed prefixed = (TACPrefixed)partNode;
					if (prefixed.expectsPrefix())
					{
						if (lastNode == null)
						{
							TACVariable thisPrefix = new TACVariable(outerType, "this", false);
							node.appendNode(thisPrefix);
							prefixed.setPrefix(thisPrefix);
						}
						else
							prefixed.setPrefix(lastNode);
					}
				}
				node.appendNode(part);
				lastNode = partNode;
			}
		}
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTConditionalExpression node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
		{
			if (node.isImageNull())
				node.appendNode((SimpleNode)node.jjtGetChild(0));
			else
			{
				SimpleNode conditionNode = (SimpleNode)node.jjtGetChild(0),
						trueNode = (SimpleNode)node.jjtGetChild(1),
						falseNode = (SimpleNode)node.jjtGetChild(2);
				TACPhi phi = new TACPhi(node.getType(), trueNode.getNode(), falseNode.getNode());
				node.appendNode(conditionNode);
				node.appendNode(new TACBranch(conditionNode.getNode(), trueNode.getEntryNode(), falseNode.getEntryNode()));
				node.appendNode(trueNode);
				node.appendNode(new TACPhiBranch(trueNode.getNode(), phi));
				node.appendNode(falseNode);
				node.appendNode(new TACPhiBranch(falseNode.getNode(), phi));
				node.appendNode(phi);
			}
		}
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTSequence node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
		{
			TACSequence seq = new TACSequence();
			for (int i = 0; i < node.jjtGetNumChildren(); i++)
			{
				SimpleNode itemNode = (SimpleNode)node.jjtGetChild(i);
				node.appendNode(itemNode);
				seq.addNode(itemNode.getNode());
			}
			node.appendNode(seq);
		}
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTIfStatement node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
		{
			SimpleNode conditionNode = (SimpleNode)node.jjtGetChild(0),
					trueNode = (SimpleNode)node.jjtGetChild(1), falseNode = null;
			if (node.jjtGetNumChildren() > 2)
				falseNode = (SimpleNode)node.jjtGetChild(2);
			TACNode target = new TACNode.Dangling();
			node.appendNode(conditionNode);
			if (falseNode == null)
				node.appendNode(new TACBranch(conditionNode.getNode(), trueNode.getEntryNode(), target));
			else
				node.appendNode(new TACBranch(conditionNode.getNode(), trueNode.getEntryNode(), falseNode.getEntryNode()));
			node.appendNode(trueNode);
			node.appendNode(new TACBranch(target));
			if (falseNode != null)
			{
				node.appendNode(falseNode);
				node.appendNode(new TACBranch(target));
			}
			node.appendNode(target);
		}
		return WalkType.POST_CHILDREN;
	}
	
	public void visitLoop(SimpleNode node, SimpleNode conditionNode, SimpleNode bodyNode, boolean loopAtLeastOnce)
	{
		TACNode bodyTarget = bodyNode.getEntryNode(),
				conditionTarget = conditionNode.getEntryNode(),
				doneTarget = new TACNode.Dangling();
		node.appendNode(new TACBranch(loopAtLeastOnce ? bodyTarget : conditionTarget));
		node.appendNode(bodyNode);
		node.appendNode(new TACBranch(conditionTarget));
		node.appendNode(conditionNode);
		node.appendNode(new TACBranch(conditionNode.getNode(), bodyTarget, doneTarget));
		node.appendNode(doneTarget);
	}
	
	@Override
	public void visit(ASTWhileStatement node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
			visitLoop((SimpleNode)node, (SimpleNode)node.jjtGetChild(0),
					(SimpleNode)node.jjtGetChild(1), false);
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTDoStatement node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
			visitLoop((SimpleNode)node, (SimpleNode)node.jjtGetChild(1),
					(SimpleNode)node.jjtGetChild(0), true);
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTForStatement node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
		{
			SimpleNode conditionNode = (SimpleNode)node.jjtGetChild(0);
			int index = 1;
			if (conditionNode instanceof ASTForInit)
			{
				SimpleNode initNode = (SimpleNode)conditionNode.jjtGetChild(0);
				conditionNode = (SimpleNode)node.jjtGetChild(1);
				index = 2;
				node.appendNode(initNode);
			}
			SimpleNode bodyNode = (SimpleNode)node.jjtGetChild(index);
			if (bodyNode instanceof ASTForUpdate)
			{
				SimpleNode updateNode = (SimpleNode)bodyNode.jjtGetChild(0);
				bodyNode = (SimpleNode)node.jjtGetChild(index + 1);
				bodyNode.appendNode(updateNode);
			}
			visitLoop(node, conditionNode, bodyNode, false);
		}
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTMethodCall node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
		{
			MethodSignature ms = new MethodSignature((MethodType)node.getType(), node.getImage(), node);
			TACSequence args = new TACSequence();
			for (int i = 0; i < node.jjtGetNumChildren(); i++)
			{
				SimpleNode argNode = (SimpleNode)node.jjtGetChild(i);
				node.appendNode(argNode);
				args.addNode(argNode.getNode());
			}
			node.appendNode(args);
//			if (!ModifierSet.isStatic(node.getType().getModifiers()))
//			{
//				SimpleNode parent = (SimpleNode)node.jjtGetParent(),
//						superParent = (SimpleNode)parent.jjtGetParent(),
//						prefix = (SimpleNode)superParent.jjtGetChild(0);
//				TACReference object = parent != prefix ? new TACReference(prefix.getNode()) :
//					new TACReference(outerType, "this");
//				node.appendNode(object);
//				node.appendNode(new TACCall(object, ms, args));
//			}
//			else
				node.appendNode(new TACCall(ms, args));
		}
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTReturnStatement node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
		{
			if (node.jjtGetNumChildren() == 0)
				node.appendNode(new TACReturn());
			else
			{
				SimpleNode returnValue = (SimpleNode)node.jjtGetChild(0);
				node.appendNode(returnValue);
				node.appendNode(new TACReturn(returnValue.getNode()));
			}
		}
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTConstructorInvocation node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
		{
			SimpleNode argsNode = (SimpleNode)node.jjtGetChild(1);
			if (argsNode.jjtGetNumChildren() != 0)
				argsNode = (SimpleNode)argsNode.jjtGetChild(0);
			MethodSignature ms = new MethodSignature((MethodType)node.getType(), "constructor", node);
			TACSequence args = new TACSequence();
			TACAllocation allocation = new TACAllocation(ms.getMethodType().getOuter(), null, true);
			for (int i = 0; i < argsNode.jjtGetNumChildren(); i++)
			{
				SimpleNode argNode = (SimpleNode)argsNode.jjtGetChild(i);
				node.appendNode(argNode);
				args.addNode(argNode.getNode());
			}
			node.appendNode(args);
			node.appendNode(allocation);
			node.appendNode(new TACCall(allocation, ms, args));
			node.appendNode(new TACReference(allocation));
		}
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTAllocationExpression node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
		{
			node.appendNode((SimpleNode)node.jjtGetChild(0));
		}
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTLiteral node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
		{
			if (node.isImageNull())
				node.appendNode((SimpleNode)node.jjtGetChild(0));
			else
				node.appendNode(new TACLiteral(node.getType(), node.getImage()));
		}
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTBooleanLiteral node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
		{
			if (node.isImageNull())
				node.appendNode((SimpleNode)node.jjtGetChild(0));
			else
				node.appendNode(new TACLiteral(node.getType(), node.getImage()));
		}
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTNullLiteral node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
		{
			if (node.isImageNull())
				node.appendNode((SimpleNode)node.jjtGetChild(0));
			else
				node.appendNode(new TACLiteral(node.getType(), node.getImage()));
		}
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTIntegerLiteral node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
		{
			if (node.isImageNull())
				node.appendNode((SimpleNode)node.jjtGetChild(0));
			else
				node.appendNode(new TACLiteral(node.getType(), node.getImage()));
		}
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTCastExpression node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
		{
			SimpleNode operand = (SimpleNode)node.jjtGetChild(1);
			node.appendNode(operand);
			node.appendNode(new TACCast(node.getType(), operand.getNode()));
		}
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTPrimaryPrefix node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
		{
			if (node.isImageNull())
				node.appendNode((SimpleNode)node.jjtGetChild(0));
			else
				node.appendNode(new TACVariable(node.getType(), node.getImage(), node.isField()));
		}
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTPrimarySuffix node, TACDeclaration[] children)
			throws ShadowException {
		if (secondVisit)
			if (node.isImageNull())
				node.appendNode((SimpleNode)node.jjtGetChild(0));
			else
				node.appendNode(new TACVariable(node.getType(), node.getImage(), node.isField()));
		return WalkType.POST_CHILDREN;
	}
	
	private void visit(SimpleNode node, TACUnary.Operator operation)
	{
		SimpleNode operand = (SimpleNode)node.jjtGetChild(0);
		node.appendNode(operand);
		node.appendNode(new TACUnary(operation, operand.getNode()));
	}
	
	@Override
	public void visit(ASTUnaryExpression node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
		{
			if (node.isImageNull())
				node.appendNode((SimpleNode)node.jjtGetChild(0));
			else
			{
				TACUnary.Operator op;
				switch (node.getImage().charAt(0))
				{
					case '+':
						op = TACUnary.Operator.PLUS;
						break;
					case '-':
						op = TACUnary.Operator.MINUS;
						break;
					default:
						op = null;
						break;
				}
				visit(node, op);
			}
		}
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTUnaryExpressionNotPlusMinus node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
		{
			if (node.isImageNull())
				node.appendNode((SimpleNode)node.jjtGetChild(0));
			else
			{
				TACUnary.Operator op;
				switch (node.getImage().charAt(0))
				{
					case '!':
						op = TACUnary.Operator.LOGICAL_NOT;
						break;
					case '~':
						op = TACUnary.Operator.BITWISE_NOT;
						break;
					default:
						op = null;
						break;
				}
				visit(node, op);
			}
		}
		return WalkType.POST_CHILDREN;
	}
	
	private void visitBinary(SimpleNode node)
	{
		SimpleNode firstOperand = (SimpleNode)node.jjtGetChild(0);
		TACNode firstNode = firstOperand.getNode();
		node.appendNode(firstOperand);
		for (int i = 0; i < node.getImage().length(); )
		{
			TACBinary.Operator operation = TACBinary.Operator.parse(node.getImage().charAt(i++));
			SimpleNode secondOperand = (SimpleNode)node.jjtGetChild(i);
			node.appendNode(secondOperand);
			firstNode = new TACBinary(firstNode, operation, secondOperand.getNode());
			node.appendNode(firstNode);
		}
	}
	
	@Override
	public void visit(ASTAdditiveExpression node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
			if (node.isImageNull())
				node.appendNode((SimpleNode)node.jjtGetChild(0));
			else
				visitBinary(node);
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTMultiplicativeExpression node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
			if (node.isImageNull())
				node.appendNode((SimpleNode)node.jjtGetChild(0));
			else
				visitBinary(node);
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTShiftExpression node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
			if (node.isImageNull())
				node.appendNode((SimpleNode)node.jjtGetChild(0));
			else
				visitBinary(node);
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTRotateExpression node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
			if (node.isImageNull())
				node.appendNode((SimpleNode)node.jjtGetChild(0));
			else
				visitBinary(node);
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTConditionalAndExpression node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
			if (node.isImageNull())
				node.appendNode((SimpleNode)node.jjtGetChild(0));
			else
				visitBinary(node);
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTConditionalOrExpression node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
			if (node.isImageNull())
				node.appendNode((SimpleNode)node.jjtGetChild(0));
			else
				visitBinary(node);
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTConditionalExclusiveOrExpression node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
			if (node.isImageNull())
				node.appendNode((SimpleNode)node.jjtGetChild(0));
			else
				visitBinary(node);
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTBitwiseAndExpression node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
			if (node.isImageNull())
				node.appendNode((SimpleNode)node.jjtGetChild(0));
			else
				visitBinary(node);
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTBitwiseOrExpression node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
			if (node.isImageNull())
				node.appendNode((SimpleNode)node.jjtGetChild(0));
			else
				visitBinary(node);
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTBitwiseExclusiveOrExpression node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
			if (node.isImageNull())
				node.appendNode((SimpleNode)node.jjtGetChild(0));
			else
				visitBinary(node);
		return WalkType.POST_CHILDREN;
	}
	
	private void visitComparison(SimpleNode node)
	{
		SimpleNode firstOperand = (SimpleNode)node.jjtGetChild(0);
		TACNode firstNode = firstOperand.getNode();
		node.appendNode(firstOperand);
		for (int i = 0; i < node.getImage().length(); )
		{
			TACComparison.Operator operation = TACComparison.Operator.parse(node.getImage().charAt(i++));
			SimpleNode secondOperand = (SimpleNode)node.jjtGetChild(i);
			node.appendNode(secondOperand);
			firstNode = new TACComparison(firstNode, operation, secondOperand.getNode());
			node.appendNode(firstNode);
		}
	}
	
	@Override
	public void visit(ASTEqualityExpression node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
			if (node.isImageNull())
				node.appendNode((SimpleNode)node.jjtGetChild(0));
			else
				visitComparison(node);
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTRelationalExpression node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
			if (node.isImageNull())
				node.appendNode((SimpleNode)node.jjtGetChild(0));
			else
				visitComparison(node);
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public void visit(ASTIsExpression node, TACDeclaration[] children) throws ShadowException
	{
		if (secondVisit)
			if (node.isImageNull())
				node.appendNode((SimpleNode)node.jjtGetChild(0));
			else
				visitComparison(node);
		return WalkType.POST_CHILDREN;
	}
}
