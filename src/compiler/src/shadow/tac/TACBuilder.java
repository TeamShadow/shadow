package shadow.tac;

import static shadow.AST.ASTWalker.WalkType.NO_CHILDREN;
import static shadow.AST.ASTWalker.WalkType.POST_CHILDREN;
import static shadow.AST.ASTWalker.WalkType.PRE_CHILDREN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import shadow.parser.javacc.*;
import shadow.tac.nodes.TACArrayRef;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACBlock;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACCatch;
import shadow.tac.nodes.TACClass;
import shadow.tac.nodes.TACFieldRef;
import shadow.tac.nodes.TACInit;
import shadow.tac.nodes.TACLabelRef;
import shadow.tac.nodes.TACLandingpad;
import shadow.tac.nodes.TACLength;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACLoad;
import shadow.tac.nodes.TACMethodRef;
import shadow.tac.nodes.TACNewArray;
import shadow.tac.nodes.TACNewObject;
import shadow.tac.nodes.TACNodeRef;
import shadow.tac.nodes.TACNot;
import shadow.tac.nodes.TACOperand;
import shadow.tac.nodes.TACPropertyRef;
import shadow.tac.nodes.TACReference;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACSame;
import shadow.tac.nodes.TACSequence;
import shadow.tac.nodes.TACSequenceRef;
import shadow.tac.nodes.TACSingletonRef;
import shadow.tac.nodes.TACStore;
import shadow.tac.nodes.TACUnary;
import shadow.tac.nodes.TACVariableRef;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.ExceptionType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.PropertyType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.UnboundMethodType;

public class TACBuilder implements ShadowParserVisitor
{
	private Collection<TACModule> modules;
	private TACTree tree;
	private TACModule module;
	private TACMethod method;
	private TACOperand prefix;
	private boolean explicitSuper, implicitCreate;
	private TACVariable identifier;
	private TACBlock block;
	public Collection<TACModule> build(Node node) throws ShadowException
	{
		modules = null;
		tree = new TACTree();
		module = null;
		method = null;
		prefix = null;
		explicitSuper = false;
		implicitCreate = false;
		identifier = null;
		block = null;
		walk(node);
		return modules;
	}
	public void walk(Node node) throws ShadowException
	{
		Object type = visit(node, false);
		if (type != NO_CHILDREN)
		{
			tree = tree.next(node.jjtGetNumChildren());
			for (int i = 0; i < node.jjtGetNumChildren(); i++)
				walk(node.jjtGetChild(i));
			if (type == POST_CHILDREN)
				visit(node, true);
		}
		tree = tree.done();
	}

	public Object visit(Node node, Boolean secondVisit) throws ShadowException
	{
		return node.jjtAccept(this, secondVisit);
	}
	@Override
	public Object visit(SimpleNode node, Boolean secondVisit)
			throws ShadowException
	{
		return node.jjtAccept(this, secondVisit);
	}

	@Override
	public Object visit(ASTCompilationUnit node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTImportDeclaration node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTModifiers node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTTypeDeclaration node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTViewDeclaration node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node,
			Boolean secondVisit) throws ShadowException
	{
		Type type = node.getType();
		module = new TACModule(type);

		for (List<MethodSignature> methodList : type.getMethodMap().values())
			for (MethodSignature method : methodList)
				visitMethod(new TACMethod(method), method.getNode());
		if (!type.getMethodMap().containsKey("create") &&
				type instanceof ClassType)
			visitMethod(new TACMethod("create",
					new MethodType(type, new Modifiers())), null);

		List<TACModule> saveModules = new ArrayList<TACModule>();
		saveModules.add(module);
		TACTree saveTree = tree;

		Node body = node.jjtGetChild(node.jjtGetNumChildren() - 1);
		for (int i = 0; i < body.jjtGetNumChildren(); i++)
		{
			SimpleNode child = (SimpleNode)body.jjtGetChild(i);
			if (child.jjtGetNumChildren() > 1 && child.jjtGetChild(1) instanceof
					ASTClassOrInterfaceDeclaration)
				saveModules.addAll(build(child));
		}

		modules = saveModules;
		tree = saveTree;
		return NO_CHILDREN;
	}

	@Override
	public Object visit(ASTExtendsList node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTImplementsList node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTVersion node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTEnumDeclaration node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTEnumBody node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTEnumConstant node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTTypeParameters node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTTypeParameter node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTTypeBound node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTClassOrInterfaceBody node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTClassOrInterfaceBodyDeclaration node,
			Boolean secondVisit) throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTFieldDeclaration node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTVariableDeclarator node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			String name = node.jjtGetChild(0).getImage();
			TACReference ref;
			if (node.isField())
				ref = new TACFieldRef(tree,
						new TACVariableRef(tree, method.getThis()), name);
			else
				ref = new TACVariableRef(tree, method.addLocal(node, name));
			if (node.jjtGetNumChildren() == 1)
				new TACStore(tree, ref, getDefaultValue(node));
			else
				new TACStore(tree, ref, tree.appendChild(1));
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTVariableDeclaratorId node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTVariableInitializer node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTArrayInitializer node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTMethodDeclaration node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			if (node.jjtGetNumChildren() == 0)
			{
				TACFieldRef field = new TACFieldRef(tree, new TACVariableRef(
						tree, method.getThis()), node.getImage());
				if (node.getModifiers().isGet())
					new TACReturn(tree, method.getReturnTypes(),
							new TACLoad(tree, field));
				else if (node.getModifiers().isSet())
				{
					TACVariable value = null;
					for (TACVariable parameter : method.getParameters())
						value = parameter;
					new TACStore(tree, field, new TACVariableRef(tree, value));
					new TACReturn(tree, method.getReturnTypes());
				}
			}
			else if (!(tree.prependAllChildren() instanceof TACReturn))
			{
				SequenceType retTypes = method.getReturnTypes();
				if (retTypes.isEmpty())
					new TACReturn(tree, retTypes, null);
				else if (retTypes.size() == 1)
					new TACReturn(tree, retTypes,
							getDefaultValue(retTypes.get(0)));
				else
				{
					List<TACOperand> seq = new ArrayList<TACOperand>();
					for (ModifiedType type : retTypes)
						seq.add(getDefaultValue(type));
					new TACReturn(tree, retTypes, new TACSequence(tree, seq));
				}
			}
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTMethodDeclarator node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTFormalParameters node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTFormalParameter node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTCreateDeclaration node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			new TACReturn(tree, method.getType().getReturnTypes());
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTDestroyDeclaration node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit && !(tree.prependAllChildren() instanceof TACReturn))
			new TACReturn(tree, method.getReturnTypes());
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTExplicitCreateInvocation node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			implicitCreate = false;
			ClassType thisType = (ClassType)method.getPrefixType(),
					superType = thisType.getExtendType();
			MethodType type = new MethodType("this".equals(node.getImage()) ?
					thisType : superType, new Modifiers());
			TACSequence sequence = tree.appendChildRemoveSequence(0);
			List<TACOperand> params = new ArrayList<TACOperand>(
					sequence.size());
			params.add(new TACVariableRef(tree, method.getThis()));
			for (TACOperand param : sequence)
			{
				params.add(param);
				type.addParameter(param);
			}
			new TACCall(tree, block, new TACMethodRef(tree, type, "create"),
					params);
			if (node.getImage().equals("super"))
				new TACInit(tree, thisType);
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTInitializer node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTType node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTReferenceType node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTStaticArrayType node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTFunctionType node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTClassOrInterfaceType node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTClassOrInterfaceTypeSuffix node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTTypeArguments node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTPrimitiveType node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTResultType node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTResultTypes node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTName node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTUnqualifiedName node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTNameList node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			visitExpression(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTAssignmentOperator node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTConditionalExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			TACLabelRef trueLabel = new TACLabelRef(tree),
					falseLabel = new TACLabelRef(tree),
					doneLabel = new TACLabelRef(tree);
			TACReference var = new TACVariableRef(tree,
					method.addTempLocal(node));
			new TACBranch(tree, tree.appendChild(0), trueLabel, falseLabel);
			trueLabel.new TACLabel(tree);
			new TACStore(tree, var, tree.appendChild(1));
			new TACBranch(tree, doneLabel);
			falseLabel.new TACLabel(tree);
			new TACStore(tree, var, tree.appendChild(2));
			new TACBranch(tree, doneLabel);
			doneLabel.new TACLabel(tree);
			new TACLoad(tree, var);
		}
		return node.jjtGetNumChildren() == 1 ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTCoalesceExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			TACLabelRef nonnullLabel = new TACLabelRef(tree),
					nullLabel = new TACLabelRef(tree),
					doneLabel = new TACLabelRef(tree);
			TACReference var = new TACVariableRef(tree,
					method.addTempLocal(node));
			TACOperand value = tree.appendChild(0);
			new TACBranch(tree, new TACSame(tree, value, new TACLiteral(tree,
					"null")), nullLabel, nonnullLabel);
			nonnullLabel.new TACLabel(tree);
			new TACStore(tree, var, value);
			new TACBranch(tree, doneLabel);
			nullLabel.new TACLabel(tree);
			new TACStore(tree, var, tree.appendChild(1));
			new TACBranch(tree, doneLabel);
			doneLabel.new TACLabel(tree);
			new TACLoad(tree, var);
		}
		return node.jjtGetNumChildren() == 1 ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTConditionalOrExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			TACLabelRef doneLabel = new TACLabelRef(tree);
			TACReference var = new TACVariableRef(tree,
					method.addTempLocal(node));
			TACOperand value = tree.appendChild(0);
			new TACStore(tree, var, value);
			for (int i = 1; i < tree.getNumChildren(); i++)
			{
				TACLabelRef nextLabel = new TACLabelRef(tree);
				new TACBranch(tree, value, doneLabel, nextLabel);
				nextLabel.new TACLabel(tree);
				new TACStore(tree, var, value = tree.appendChild(i));
			}
			new TACBranch(tree, doneLabel);
			doneLabel.new TACLabel(tree);
			new TACLoad(tree, var);
		}
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTConditionalExclusiveOrExpression node,
			Boolean secondVisit) throws ShadowException
	{
		if (secondVisit)
			visitBinaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTConditionalAndExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			TACLabelRef doneLabel = new TACLabelRef(tree);
			TACReference var = new TACVariableRef(tree,
					method.addTempLocal(node));
			TACOperand value = tree.appendChild(0);
			new TACStore(tree, var, value);
			for (int i = 1; i < tree.getNumChildren(); i++)
			{
				TACLabelRef nextLabel = new TACLabelRef(tree);
				new TACBranch(tree, value, nextLabel, doneLabel);
				nextLabel.new TACLabel(tree);
				new TACStore(tree, var, value = tree.appendChild(i));
			}
			new TACBranch(tree, doneLabel);
			doneLabel.new TACLabel(tree);
			new TACLoad(tree, var);
		}
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTBitwiseOrExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			visitBinaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTBitwiseExclusiveOrExpression node,
			Boolean secondVisit) throws ShadowException
	{
		if (secondVisit)
			visitBinaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTBitwiseAndExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			visitBinaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTEqualityExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			int index = 0;
			String image = node.getImage();
			TACOperand value = tree.appendChild(index);
			while (index < image.length())
			{
				char c = image.charAt(index);
				if (c == '=' || c == '!')
				{
					TACOperand other = tree.appendChild(++index);
					if (node.jjtGetChild(index - 1).getType().isPrimitive() &&
							node.jjtGetChild(index).getType().isPrimitive())
					{
//						TACOperand parent = value;
//						if (value.getType().isSubtype(other.getType()))
//							parent = other;
//						value = new TACCall(tree, block, new TACMethodRef(tree,
//								parent, Type.OBJECT.getMethods("equals").
//								get(0)), value, other);
						value = new TACSame(tree, value, other);
					}
					else
					{
						TACLabelRef nullLabel = new TACLabelRef(tree),
								nonnullLabel = new TACLabelRef(tree),
								doneLabel = new TACLabelRef(tree);
						TACVariableRef var = new TACVariableRef(tree,
								method.addTempLocal(node));
						TACLiteral nullLiteral = new TACLiteral(tree, "null");
						new TACBranch(tree, new TACSame(tree, value,
								nullLiteral), nullLabel, nonnullLabel);
						nullLabel.new TACLabel(tree);
						new TACStore(tree, var, new TACSame(tree, other,
								nullLiteral));
						new TACBranch(tree, doneLabel);
						nonnullLabel.new TACLabel(tree);
						new TACStore(tree, var, new TACCall(tree, block,
								new TACMethodRef(tree, value, Type.OBJECT.
										getMethods("equals").get(0)),
								value, other));
						new TACBranch(tree, doneLabel);
						doneLabel.new TACLabel(tree);
						value = new TACLoad(tree, var);
					}
				}
				else
					value = new TACSame(tree, value, tree.appendChild(++index));
				if (c == '!' || c == 'n')
					value = new TACNot(tree, value);
			}
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTIsExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			visitBinaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTRelationalExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			visitBinaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTConcatenationExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			TACOperand last = null;
			for (int i = 0; i < tree.getNumChildren(); i++)
			{
				TACOperand operand = tree.appendChild(i);
				if (!operand.getType().isPrimitive() &&
						!(operand.getType() instanceof ArrayType))
				{ // TODO: actually check nullable
					TACLabelRef nullLabel = new TACLabelRef(tree),
							nonnullLabel = new TACLabelRef(tree),
							doneLabel = new TACLabelRef(tree);
					TACReference var = new TACVariableRef(tree,
							method.addTempLocal(node));
					new TACBranch(tree, new TACSame(tree, operand,
							new TACLiteral(tree, "null")), nullLabel,
							nonnullLabel);
					nullLabel.new TACLabel(tree);
					new TACStore(tree, var, new TACLiteral(tree, "\"null\""));
					new TACBranch(tree, doneLabel);
					nonnullLabel.new TACLabel(tree);
					new TACStore(tree, var, new TACCall(tree, block,
							new TACMethodRef(tree, operand,
									Type.OBJECT.getMethods("toString").get(0)),
							Collections.singletonList(operand)));
					new TACBranch(tree, doneLabel);
					doneLabel.new TACLabel(tree);
					operand = new TACLoad(tree, var);
				}
				else
					operand = new TACCall(tree, block,
							new TACMethodRef(tree, operand,
									Type.OBJECT.getMethods("toString").get(0)),
							Collections.singletonList(operand));
				last = i == 0 ? operand : new TACCall(tree, block,
						new TACMethodRef(tree,
								Type.STRING.getMethods("concatenate").get(0)),
						Arrays.asList(last, operand));
			}
		}
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTShiftExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			visitBinaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTRotateExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			visitBinaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTAdditiveExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			visitBinaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTMultiplicativeExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			visitBinaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTUnaryToString node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			TACOperand operand = tree.appendChild(0);
			TACLabelRef nullLabel = new TACLabelRef(tree),
					nonnullLabel = new TACLabelRef(tree),
					doneLabel = new TACLabelRef(tree);
			TACReference var = new TACVariableRef(tree,
					method.addTempLocal(node));
			new TACBranch(tree, new TACSame(tree, operand, new TACLiteral(tree,
					"null")), nullLabel, nonnullLabel);
			nullLabel.new TACLabel(tree);
			new TACStore(tree, var, new TACLiteral(tree, "\"null\""));
			new TACBranch(tree, doneLabel);
			nonnullLabel.new TACLabel(tree);
			new TACStore(tree, var, new TACCall(tree, block,
					new TACMethodRef(tree, operand,
							Type.OBJECT.getMethods("toString").get(0)),
					Collections.singletonList(operand)));
			new TACBranch(tree, doneLabel);
			doneLabel.new TACLabel(tree);
			new TACLoad(tree, var);
		}
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTUnaryExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			visitUnaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTUnaryExpressionNotPlusMinus node,
			Boolean secondVisit) throws ShadowException
	{
		if (secondVisit)
			visitUnaryOperation(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTCastExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			new TACCast(tree, node, tree.appendChild(1));
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTCheckExpression node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			TACLabelRef continueLabel = new TACLabelRef(tree);
			TACOperand operand = tree.appendChild(0);
			new TACBranch(tree, new TACSame(tree, operand, new TACLiteral(tree,
					"null")), block.getRecover(), continueLabel);
			continueLabel.new TACLabel(tree);
			prefix = new TACNodeRef(tree, operand);
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTPrimaryExpression node, Boolean secondVisit)
			throws ShadowException
	{
		TACOperand savePrefix = prefix;
		TACVariable saveIdentifier = identifier;
		prefix = null;
		identifier = null;

		tree = tree.next(node.jjtGetNumChildren());
		for (int i = 0; i < node.jjtGetNumChildren(); i++)
			walk(node.jjtGetChild(i));

		prefix = savePrefix;
		identifier = saveIdentifier;
		return NO_CHILDREN;
	}

	@Override
	public Object visit(ASTSequence node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			List<TACOperand> sequence =
					new ArrayList<TACOperand>(node.getUsedItems().size());
			int index = 0;
			for (boolean used : node.getUsedItems())
				if (used)
					sequence.add(tree.appendChild(index++));
				else
					sequence.add(new TACVariableRef(tree,
							method.addTempLocal(new SimpleModifiedType(
									Type.OBJECT,
									new Modifiers(Modifiers.NULLABLE)))));
			new TACSequence(tree, sequence);
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTPrimaryPrefix node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			identifier = new TACVariable(node, node.getImage());
			if (node.isImageNull())
				prefix = tree.appendChild(0);
			else
			{
				explicitSuper = node.getImage().equals("super");
				if (!(explicitSuper || node.getModifiers().isTypeName() ||
						node.getType() instanceof UnboundMethodType))
				{
					TACVariable local = method.getLocal(node.getImage());
					if (local != null)
						prefix = new TACVariableRef(tree, local);
					else
					{
						TACReference thisRef =
								new TACVariableRef(tree, method.getThis());
						while (!thisRef.getType().
								containsField(node.getImage()))
							thisRef = new TACFieldRef(tree, thisRef,
									new SimpleModifiedType(thisRef.getType().
											getOuter()), "this");
						prefix = new TACFieldRef(tree, thisRef,
								node.getImage());
					}
				}
			}
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTAllocation node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTPrimarySuffix node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTQualifiedKeyword node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			if (node.getImage().equals("class"))
				new TACClass(tree, identifier.getType(), method);
			else // TODO: Make this work
				throw new UnsupportedOperationException();
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTSubscript node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			List<TACOperand> indicies =
					new ArrayList<TACOperand>(tree.getNumChildren());
			for (int i = 0; i < tree.getNumChildren(); i++)
				indicies.add(tree.appendChild(i));
			prefix = new TACArrayRef(tree, prefix, indicies);
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTScopeSpecifier node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			prefix = new TACFieldRef(tree, prefix, node.getImage());
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTMethod node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			identifier = new TACVariable(node, node.getImage());
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTProperty node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			if (prefix.getType() instanceof ArrayType &&
					node.getImage().equals("length"))
			{
				ArrayType arrayType = (ArrayType)prefix.getType();
				TACOperand length = new TACLength(tree, prefix, 0);
				for (int i = 1; i < arrayType.getDimensions(); i++)
					length = new TACBinary(tree, length, '*',
							new TACLength(tree, prefix, i));
				prefix = length;
			}
			else
				prefix = new TACPropertyRef(tree, block, prefix,
						(PropertyType)node.getType(), node.getImage());
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTMethodCall node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			if (prefix == null)
				prefix = new TACVariableRef(tree, method.getThis());
			TACMethodRef methodRef = new TACMethodRef(tree,
					explicitSuper ? null : prefix,
					((MethodType)node.getType()).getTypeWithoutTypeArguments(),
					identifier.getName());
			List<TACOperand> params = new ArrayList<TACOperand>();
			params.add(prefix);
			for (int i = 0; i < tree.getNumChildren(); i++)
				if (node.jjtGetChild(i) instanceof ASTTypeArguments)
					for (ModifiedType type :
							(SequenceType)node.jjtGetChild(i).getType())
						params.add(new TACClass(tree, type.getType(), method));
				else
					params.add(tree.appendChild(i));
			prefix = new TACCall(tree, block, methodRef, params);
			explicitSuper = false;
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTLiteral node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			visitLiteral(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTIntegerLiteral node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			visitLiteral(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTBooleanLiteral node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			visitLiteral(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTNullLiteral node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			visitLiteral(node);
		return node.isImageNull() ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTArguments node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			new TACSequence(tree);
		return node.jjtGetNumChildren() != 0 ? PRE_CHILDREN : POST_CHILDREN;
	}

	@Override
	public Object visit(ASTArgumentList node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			List<TACOperand> params = new ArrayList<TACOperand>();
			for (int i = 0; i < tree.getNumChildren(); i++)
			{
				TACOperand param = tree.appendChild(i);
				if (param != null)
					params.add(param);
			}
			new TACSequence(tree, params);
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTArrayCreate node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			List<TACOperand> sizes = new ArrayList<TACOperand>(
					tree.getNumChildren());
			for (int i = 0; i < tree.getNumChildren(); i++)
				sizes.add(tree.appendChild(i));
			prefix = visitArrayAllocation((ArrayType)node.getType(), sizes);
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTCreate node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			TACMethodRef methodRef = new TACMethodRef(tree,
					(MethodType)node.getType(), node.getImage());
			TACOperand object = new TACNewObject(tree,
					(ClassType)methodRef.getPrefixType());
			List<TACOperand> params = new ArrayList<TACOperand>();
			params.add(object);
			if (methodRef.getPrefixType().hasOuter())
			{
				Type outerType = methodRef.getPrefixType().getOuter();
				TACVariable thisRef = method.getThis();
				if (thisRef.getType().equals(outerType))
					params.add(new TACVariableRef(tree, thisRef));
				else
					throw new UnsupportedOperationException();
			}
			for (int i = 0; i < tree.getNumChildren(); i++)
				if (node.jjtGetChild(i) instanceof ASTTypeArguments)
					for (ModifiedType type : (SequenceType)
							node.jjtGetChild(i).getType())
						params.add(new TACClass(tree, type.getType(), method));
				else
					params.add(tree.appendChild(i));
			new TACCall(tree, block, methodRef, params);
			prefix = new TACNodeRef(tree, object);
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTInstance node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			SingletonType type = (SingletonType)node.getType();
			TACLabelRef initLabel = new TACLabelRef(tree),
					doneLabel = new TACLabelRef(tree);
			TACReference instance = new TACSingletonRef(tree, type);
			new TACBranch(tree, new TACSame(tree, instance, new TACLiteral(tree,
					"null")), initLabel, doneLabel);
			initLabel.new TACLabel(tree);
			new TACStore(tree, instance, new TACCall(tree, block,
					new TACMethodRef(tree, type.getMethods("create").get(0)),
					Collections.singletonList(new TACNewObject(tree, type))));
			new TACBranch(tree, doneLabel);
			doneLabel.new TACLabel(tree);
			prefix = new TACLoad(tree, instance);
		}
		return POST_CHILDREN;
	}

	/*
	@Override
	public Object visit(ASTArrayDimsAndInits node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			List<ModifiedType> types = new ArrayList<ModifiedType>();
			List<TACOperand> seq = new ArrayList<TACOperand>();
			for (int i = 0; i < tree.getNumChildren(); i++)
			{
				TACOperand child = tree.appendChild(i);
				types.add(new SimpleModifiedType(child.getType()));
				seq.add(child);
			}
			new TACSequence(tree, seq);
		}
		return POST_CHILDREN;
	}
	
	*/

	@Override
	public Object visit(ASTStatement node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTAssertStatement node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTBlock node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			method.exitScope();
		else
			method.enterScope();
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTBlockStatement node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTLocalDeclaration node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTLocalMethodDeclaration node, Boolean secondVisit)
			throws ShadowException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Object visit(ASTLocalVariableDeclaration node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTEmptyStatement node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTStatementExpression node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTSequenceAssignment node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			new TACStore(tree, new TACSequenceRef(tree,
					tree.appendChildRemoveSequence(0)), tree.appendChild(1));
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTSwitchStatement node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTSwitchLabel node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTIfStatement node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			TACLabelRef trueLabel = new TACLabelRef(tree),
					falseLabel = new TACLabelRef(tree),
					endLabel = new TACLabelRef(tree);
			new TACBranch(tree, tree.appendChild(0), trueLabel, falseLabel);
			trueLabel.new TACLabel(tree);
			tree.appendChild(1);
			new TACBranch(tree, endLabel);
			falseLabel.new TACLabel(tree);
			tree.appendChild(2);
			new TACBranch(tree, endLabel);
			endLabel.new TACLabel(tree);
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTWhileStatement node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			visitLoop(0, 1, false);
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTDoStatement node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			visitLoop(1, 0, true);
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTForeachStatement node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTForStatement node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			int condition = 0;
			if (node.jjtGetChild(condition) instanceof ASTForInit)
				tree.appendChild(condition++);
			int body = condition + 1;
			if (node.jjtGetChild(body) instanceof ASTForUpdate)
				tree.getChild(++body).append(tree.getChild(body - 1));
			visitLoop(condition, body, false);
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTForInit node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTStatementExpressionList node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTForUpdate node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTBreakStatement node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTContinueStatement node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTReturnStatement node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
			new TACReturn(tree, (SequenceType)node.getType(),
					tree.appendChild(0));
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTThrowStatement node, Boolean secondVisit)
			throws ShadowException
	{
		if (secondVisit)
		{
			TACOperand exception = tree.appendChild(0);
			new TACCall(tree, block, new TACMethodRef(tree, exception,
					Type.EXCEPTION.getMethods("throw_").get(0)),
					Collections.singletonList(exception));
		}
		return POST_CHILDREN;
	}

	@Override
	public Object visit(ASTTryStatement node, Boolean secondVisit)
			throws ShadowException
	{
		tree = tree.next();
		TACLabelRef doneLabel = new TACLabelRef(tree);
		int index;

		TACBlock saveBlock = block;

		TACBlock outerBlock = new TACBlock(tree, saveBlock);
		if (node.hasFinally())
			outerBlock.addCleanup();

		TACBlock innerBlock = new TACBlock(tree, outerBlock);
		if (node.hasRecover())
			innerBlock.addRecover();
		innerBlock.addCatches(node.getCatches());
		if (node.hasCatches())
			innerBlock.addLandingpad();

		index = 0;
		block = innerBlock;
		walk(node.jjtGetChild(index));
		block = outerBlock;
		for (int i = 0; i < node.getCatches(); i++)
			walk(node.jjtGetChild(++index));
		if (node.hasRecover())
			walk(node.jjtGetChild(++index));
		block = saveBlock;
		if (node.hasFinally())
			walk(node.jjtGetChild(++index));

		index = 0;
		tree.appendChild(index);
		new TACBranch(tree, doneLabel);
		if (node.hasCatches())
		{
			innerBlock.getLandingpad().new TACLabel(tree);
			new TACLandingpad(tree, innerBlock);
			for (int i = 0; i < node.getCatches(); i++)
			{
				innerBlock.getCatch(i).new TACLabel(tree);
				new TACCatch(tree, (ExceptionType)node.jjtGetChild(i + 1).
						getType());
				tree.appendChild(++index);
				new TACBranch(tree, doneLabel);
			}
		}
		if (node.hasRecover())
		{
			innerBlock.getRecover().new TACLabel(tree);
			tree.appendChild(++index);
			new TACBranch(tree, doneLabel);
		}
		doneLabel.new TACLabel(tree);
		if (node.hasFinally())
		{
			doneLabel = new TACLabelRef(tree);
			outerBlock.getCleanup().new TACLabel(tree);
			tree.appendChild(++index);
			new TACBranch(tree, doneLabel);
			doneLabel.new TACLabel(tree);
		}

		return NO_CHILDREN;

//		tree.appendChild(index++);
//		new TACBranch(tree, endLabel);
//		for (int i = 0; i < node.getCatches(); i++)
//		{
//			index++;
//			index++;
//			new TACBranch(tree, endLabel);
//		}
//		if (node.hasRecover())
//		{
//			block.getRecover().new TACLabel(tree);
//			tree.appendChild(index++);
//			new TACBranch(tree, endLabel);
//		}
//		if (node.hasFinally())
//		{
//			block.getCleanup().new TACLabel(tree);
//			index++;
//		}
//		else
//			endLabel.new TACLabel(tree);
//		block = block.getParent();
//
//		block = block.getParent();
//		return NO_CHILDREN;
//
//		if (secondVisit)
//		{
//			int index = 0;
//			TACLabelRef endLabel = null;
//			if (node.hasRecover())
//				tree.append(block.getRecover());
//			if (node.hasFinally())
//				tree.append(endLabel = block.getCleanup());
//			if (endLabel == null)
//				endLabel = new TACLabelRef(tree);
//			tree.appendChild(index++);
//			new TACBranch(tree, endLabel);
//			for (int i = 0; i < node.getCatches(); i++)
//			{
//				index++;
//				index++;
//				new TACBranch(tree, endLabel);
//			}
//			if (node.hasRecover())
//			{
//				block.getRecover().new TACLabel(tree);
//				tree.appendChild(index++);
//				new TACBranch(tree, endLabel);
//			}
//			if (node.hasFinally())
//			{
//				block.getCleanup().new TACLabel(tree);
//				index++;
//			}
//			else
//				endLabel.new TACLabel(tree);
//			block = block.getParent();
//		}
//		else
//		{
//			if (node.getCatches() != 0)
//				throw new UnsupportedOperationException();
//			block = new TACBlock(block);
//			if (node.hasRecover())
//				block.addRecover();
//			if (node.hasFinally())
//				block.addCleanup();
//		}
//		return POST_CHILDREN;
	}
	@Override
	public Object visit(ASTCatchStatement node, Boolean data)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTRightRotate node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	@Override
	public Object visit(ASTRightShift node, Boolean secondVisit)
			throws ShadowException
	{
		return PRE_CHILDREN;
	}

	private void visitMethod(TACMethod methodRef, Node methodNode)
			throws ShadowException
	{
		method = methodRef;
		block = new TACBlock();
		if (module.isClass() && !methodRef.isNative())
		{
			tree = tree.next();
			if (implicitCreate = methodRef.isCreate())
			{
				Type type = methodRef.getPrefixType();
				tree = tree.next();
				if (type.hasOuter())
					new TACStore(tree,
							new TACFieldRef(tree, new TACVariableRef(tree,
									methodRef.getThis()),
									new SimpleModifiedType(type.getOuter()),
									"this"),
							new TACVariableRef(tree,
									methodRef.getParameter("outer")));
				if (type.isParameterized())
					for (ModifiedType typeParam : type.getTypeParameters())
						new TACStore(tree,
								new TACFieldRef(tree, new TACVariableRef(tree,
										methodRef.getThis()),
										new SimpleModifiedType(Type.CLASS),
										typeParam.getType().getTypeName()),
								new TACVariableRef(tree,
										methodRef.getParameter(typeParam.
												getType().getTypeName())));
				tree = tree.done();
				for (Node field : type.getFields().values())
					if (!field.getModifiers().isConstant())
						walk(field);
			}
			if (methodNode == null)
				new TACReturn(tree, methodRef.getReturnTypes());
			else
				walk(methodNode);
			tree = tree.done();
			if (implicitCreate)
			{
				ClassType thisType = (ClassType)methodRef.getPrefixType(),
						superType = thisType.getExtendType();
				if (superType != null)
					new TACCall(methodRef, block, new TACMethodRef(methodRef,
							new MethodType(superType, new Modifiers()),
							"create"), new TACVariableRef(methodRef,
							methodRef.getThis()));
				new TACInit(methodRef, thisType);
			}
			methodRef.append(tree.current());
		}
		module.addMethod(methodRef);
		block = null;
		method = null;
	}

	private void visitLiteral(SimpleNode node)
	{
		prefix = new TACLiteral(tree, node.getImage());
	}

	private void visitUnaryOperation(SimpleNode node)
	{
		new TACUnary(tree, node.getImage().charAt(0), tree.appendChild(0));
	}

	private void visitBinaryOperation(SimpleNode node)
	{
		int child = 0;
		TACOperand current = null;
		while (current == null)
			current = tree.appendChild(child++);
		String operations = node.getImage();
		for (int i = 0; i < operations.length(); i++)
		{
			char operation = operations.charAt(i);
			TACOperand next = null;
			while (next == null)
				next = tree.appendChild(child++);
			current = new TACBinary(tree, current, operation, next);
		}
	}

	private void visitExpression(SimpleNode node)
	{
		TACOperand value = tree.appendChild(2);
		TACReference var = (TACReference)tree.appendChild(0);
		char operation = node.jjtGetChild(1).getImage().charAt(0);
		if (operation != '=')
			value = new TACBinary(tree, var, operation, value);
		new TACStore(tree, var, value);
	}

	private void visitLoop(int condition, int body, boolean atLeastOnce)
	{
		TACLabelRef bodyLabel = new TACLabelRef(tree),
				conditionLabel = new TACLabelRef(tree),
				endLabel = new TACLabelRef(tree);
		new TACBranch(tree, atLeastOnce ? bodyLabel : conditionLabel);
		bodyLabel.new TACLabel(tree);
		tree.appendChild(body);
		new TACBranch(tree, conditionLabel);
		conditionLabel.new TACLabel(tree);
		new TACBranch(tree, tree.appendChild(condition), bodyLabel, endLabel);
		endLabel.new TACLabel(tree);
	}

	private TACOperand visitArrayAllocation(ArrayType type,
			List<TACOperand> sizes)
	{
		TACOperand baseClass = new TACClass(tree, type.getBaseType(), method);
		TACNewArray alloc = new TACNewArray(tree, type, baseClass,
				sizes.subList(0, type.getDimensions()));
		sizes = sizes.subList(type.getDimensions(), sizes.size());
		if (!sizes.isEmpty())
		{
			TACReference index = new TACVariableRef(tree,
					method.addTempLocal(new SimpleModifiedType(Type.INT)));
			new TACStore(tree, index, new TACLiteral(tree, "0"));
			TACLabelRef bodyLabel = new TACLabelRef(tree),
					condLabel = new TACLabelRef(tree),
					endLabel = new TACLabelRef(tree);
			new TACBranch(tree, condLabel);
			bodyLabel.new TACLabel(tree);
			new TACStore(tree, new TACArrayRef(tree, alloc, index),
					visitArrayAllocation((ArrayType)type.getBaseType(), sizes));
			new TACStore(tree, index, new TACBinary(tree, index, '+',
					new TACLiteral(tree, "1")));
			new TACBranch(tree, condLabel);
			condLabel.new TACLabel(tree);
			new TACBranch(tree, new TACSame(tree, index, alloc.getTotalSize()),
					endLabel, bodyLabel);
			endLabel.new TACLabel(tree);
		}
		return new TACNodeRef(tree, alloc);
	}

//	private TACReference visitArrayAllocation(TACData tac, ArrayType type, List<TACNode> sizes, int sizeIndex)
//	{
//		int startIndex = sizeIndex;
//		TACNode size = sizes.get(sizeIndex++);
//		for (int i = 1; i < type.getDimensions(); i++)
//			tac.append(size = new TACBinary(size, TACBinary.Operator.MULTIPLY, sizes.get(sizeIndex++)));
//		TACAllocation alloc = new TACAllocation(type, size, sizes, startIndex);
//		tac.append(alloc);
//		if (sizeIndex < sizes.size())
//		{
//			TACOldVariable index = new TACOldVariable(Type.INT);
//			tac.append(new TACAllocation(index));
//			tac.append(new TACLiteral(Type.INT, 0));
//			tac.append(new TACAssign(index, tac.getNode()));
//			TACComparison condition = new TACComparison(index, TACComparison.Operator.NOT_EQUAL, size);
//			TACLabel bodyLabel = new TACLabel("body"),
//					conditionLabel = new TACLabel("condition"),
//					endLabel = new TACLabel("end");
//			tac.append(new TACBranch(conditionLabel));
//			tac.append(bodyLabel);
//			TACReference value = visitArrayAllocation(tac, (ArrayType)type.getBaseType(), sizes, sizeIndex);
//			tac.append(new TACIndexed(type.getBaseType(), alloc, new TACReference(index)));
//			tac.append(new TACAssign(tac.getNode(), new TACReference(value)));
//			tac.append(new TACAssign(
//					tac.append(new TACReference(index)),
//					tac.append(new TACBinary(
//							tac.append(new TACReference(index)),
//							TACBinary.Operator.ADD,
//							tac.append(new TACLiteral(Type.INT, 1))))));
//			tac.append(new TACBranch(conditionLabel));
//			tac.append(conditionLabel);
//			tac.append(condition);
//			tac.append(new TACBranch(condition, bodyLabel, endLabel));
//			tac.append(endLabel);
//		}
//		return new TACReference(alloc);
//	}

	private TACOperand getDefaultValue(ModifiedType type)
	{
		if (type.getType().equals(Type.BOOLEAN))
			return new TACLiteral(tree, "false");
		if (type.getType().equals(Type.CODE))
			return new TACLiteral(tree, "'\0'");
		if (type.getType().equals(Type.UBYTE))
			return new TACLiteral(tree, "0uy");
		if (type.getType().equals(Type.BYTE))
			return new TACLiteral(tree, "0y");
		if (type.getType().equals(Type.USHORT))
			return new TACLiteral(tree, "0us");
		if (type.getType().equals(Type.SHORT))
			return new TACLiteral(tree, "0s");
		if (type.getType().equals(Type.UINT))
			return new TACLiteral(tree, "0ui");
		if (type.getType().equals(Type.INT))
			return new TACLiteral(tree, "0i");
		if (type.getType().equals(Type.ULONG))
			return new TACLiteral(tree, "0ul");
		if (type.getType().equals(Type.LONG))
			return new TACLiteral(tree, "0l");
//		if (!type.getModifiers().isNullable())
//			throw new IllegalArgumentException();
		return new TACCast(tree, type, new TACLiteral(tree, "null"));
	}

	@Override
	public Object visit(ASTBrackets node, Boolean secondVisit)
			throws ShadowException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Object visit(ASTDestroy node, Boolean secondVisit)
			throws ShadowException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Object visit(ASTForeachInit node, Boolean data)
			throws ShadowException
	{
		throw new UnsupportedOperationException();
	}
}
