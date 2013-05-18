package shadow.output.text;

import java.io.Writer;
import java.util.List;

import shadow.output.AbstractOutput;
import shadow.parser.javacc.ShadowException;
import shadow.tac.TACAbstractVisitor;
import shadow.tac.TACMethod;
import shadow.tac.TACModule;
import shadow.tac.TACVariable;
import shadow.tac.nodes.TACArrayRef;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACFieldRef;
import shadow.tac.nodes.TACLabelRef;
import shadow.tac.nodes.TACLabelRef.TACLabel;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACLoad;
import shadow.tac.nodes.TACMethodRef;
import shadow.tac.nodes.TACNewArray;
import shadow.tac.nodes.TACNewObject;
import shadow.tac.nodes.TACNot;
import shadow.tac.nodes.TACOperand;
import shadow.tac.nodes.TACPropertyRef;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACSame;
import shadow.tac.nodes.TACSequence;
import shadow.tac.nodes.TACSequenceRef;
import shadow.tac.nodes.TACSingletonRef;
import shadow.tac.nodes.TACStore;
import shadow.tac.nodes.TACUnary;
import shadow.tac.nodes.TACVariableRef;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Type;

public class TextOutput extends AbstractOutput
{
	private Inline inline = new Inline();
	private int tempCounter = 0;
	public TextOutput(Writer out) throws ShadowException
	{
		super(out);
	}
	public TextOutput(boolean mode) throws ShadowException
	{
		writer.setLineNumbers(mode);
	}

	@Override
	public void startFile(TACModule module) throws ShadowException
	{
		Type type = module.getType();
		StringBuilder sb = new StringBuilder(type.getModifiers().toString()).
				append("class ").append(type.getQualifiedName());
		if (type instanceof ClassType)
		{
			ClassType classType = module.getClassType();
			if (classType.getExtendType() != null)
				sb.append(" extends ").
						append(classType.getExtendType().getQualifiedName());
			List<InterfaceType> interfaceTypes = classType.getInterfaces();
			if (!interfaceTypes.isEmpty())
			{
				sb.append(" implements ");
				for (InterfaceType interfaceType : interfaceTypes)
					sb.append(interfaceType.getQualifiedName()).append(", ");
				sb.delete(sb.length() - 2, sb.length());
			}
		}
		writer.write(sb.append(" {").toString());
		writer.indent();
	}

	@Override
	public void endFile(TACModule module) throws ShadowException
	{
		writer.outdent();
		writer.write('}');
	}

	@Override
	public void startMethod(TACMethod method) throws ShadowException
	{
		StringBuilder sb = new StringBuilder(
				method.getType().getModifiers().toString()).
				append(method.getName()).append('(');
		for (TACVariable param : method.getParameters())
			sb.append(param.getType().getQualifiedName()).append(' ').
					append(param.getName()).append(", ");
		if (!method.getParameters().isEmpty())
			sb.delete(sb.length() - 2, sb.length());
		sb.append(") => (");
		for (ModifiedType retType : method.getReturnTypes())
			sb.append(retType.getType().getQualifiedName());
		sb.append(')');
		if (method.isNative())
			writer.write(sb.append(';').toString());
		else
		{
			writer.write(sb.append(" {").toString());
			writer.indent();
			for (TACVariable param : method.getLocals())
				if (!method.getParameters().contains(param))
					writer.write(param.getType().getQualifiedName() + ' ' +
							param.getName() + ';');
		}
	}

	@Override
	public void endMethod(TACMethod method) throws ShadowException
	{
		if (!method.isNative())
		{
			writer.outdent();
			writer.write('}');
		}
	}

	@Override
	public void visit(TACLabelRef node) throws ShadowException
	{
		node.setSymbol(String.format("_%d_", tempCounter++));
	}

	@Override
	public void visit(TACLabel node) throws ShadowException
	{
		writer.writeLeft(node.toString() + ':');
	}

	@Override
	public void visit(TACBranch node) throws ShadowException
	{
		StringBuilder sb = new StringBuilder("goto ");
		if (node.isConditional())
			inline.visit(sb, node.getCondition()).append(" ? ").
					append(node.getTrueLabel()).append(", ").
					append(node.getFalseLabel());
		else
			sb.append(node.getLabel());
		writer.write(sb.append(';').toString());
	}

	@Override
	public void visit(TACReturn node) throws ShadowException
	{
		StringBuilder sb = new StringBuilder("return");
		if (node.hasReturnValue())
			inline.visit(sb.append(' '), node.getReturnValue());
		writer.write(sb.append(';').toString());
	}

	@Override
	public void visit(TACCall node) throws ShadowException
	{
		if (!node.getMethod().hasReturn())
			writer.write(inline.visit(new StringBuilder(), node).append(';').
					toString());
	}

	@Override
	public void visit(TACStore node) throws ShadowException
	{
		writer.write(inline.visit(inline.visit(new StringBuilder(),
				node.getReference()).append(" = "), node.getValue()).
				append(';').toString());
	}

	class Inline extends TACAbstractVisitor
	{
		private boolean parentheses;
		private StringBuilder sb;
		public StringBuilder visit(StringBuilder builder, TACOperand node)
				throws ShadowException
		{
			sb = builder;
			node.accept(this);
			return builder;
		}

		@Override
		public void visit(TACNot node) throws ShadowException
		{
			sb.append('!');
			parentheses = true;
			visit(sb, node.getOperand());
			parentheses = false;
		}

		@Override
		public void visit(TACSame node) throws ShadowException
		{
			boolean paren = parentheses;
			if (paren) sb.append('(');
			parentheses = true;
			visit(sb, node.getFirst()).append(" === ");
			parentheses = true;
			visit(sb, node.getSecond());
			if (paren) sb.append(')');
			parentheses = false;
		}

		@Override
		public void visit(TACUnary node) throws ShadowException
		{
			sb.append(node.getOperation());
			parentheses = true;
			visit(sb, node.getOperand());
			parentheses = false;
		}

		@Override
		public void visit(TACBinary node) throws ShadowException
		{
			boolean paren = parentheses;
			if (paren) sb.append('(');
			parentheses = true;
			visit(sb, node.getFirst()).append(' ').append(node.getOperation()).
					append(' ');
			parentheses = true;
			visit(sb, node.getSecond());
			if (paren) sb.append(')');
			parentheses = false;
		}

		@Override
		public void visit(TACCall node) throws ShadowException
		{
			TACMethodRef method = node.getMethod();
			sb.append(method.getPrefixType().getQualifiedName()).append(':').
					append(method.getName()).append('(');
			for (TACOperand param : node.getParameters())
				visit(sb, param).append(", ");
			if (node.getNumParameters() != 0)
				sb.delete(sb.length() - 2, sb.length());
			sb.append(')');
		}

		@Override
		public void visit(TACCast node) throws ShadowException
		{
			sb.append("cast<").append(node.getType().getQualifiedName()).
					append('>');
			visit(sb, node.getOperand());
		}

		@Override
		public void visit(TACLoad node) throws ShadowException
		{
			visit(sb, node.getReference());
		}

		@Override
		public void visit(TACArrayRef node) throws ShadowException
		{
			visit(visit(sb, node.getArray()).append('['), node.getIndex(0));
			for (int i = 1; i < node.getNumIndicies(); i++)
				visit(sb.append(", "), node.getIndex(i));
			sb.append(']');
		}

		@Override
		public void visit(TACPropertyRef node) throws ShadowException
		{
			visit(sb, node.getPrefix()).append("->").append(node.getName());
		}

		@Override
		public void visit(TACFieldRef node) throws ShadowException
		{
			visit(sb, node.getPrefix()).append(':').append(node.getName());
		}

		@Override
		public void visit(TACVariableRef node) throws ShadowException
		{
			sb.append(node.getName());
		}

		@Override
		public void visit(TACSequenceRef node) throws ShadowException
		{
			sb.append('(');
			for (TACOperand operand : node)
				visit(sb, operand).append(", ");
			sb.delete(sb.length() - 2, sb.length()).append(')');
		}

		@Override
		public void visit(TACSequence node) throws ShadowException
		{
			sb.append('(');
			for (TACOperand operand : node)
				visit(sb, operand).append(", ");
			sb.delete(sb.length() - 2, sb.length()).append(')');
		}

		@Override
		public void visit(TACNewObject node) throws ShadowException
		{
			sb.append(node.getType().getQualifiedName()).append(":create");
		}

		@Override
		public void visit(TACNewArray node) throws ShadowException
		{
			sb.append(node.getType().getBaseType().getQualifiedName()).
					append(":create[").append(node.getDimension(0));
			for (int i = 1; i < node.getDimensions(); i++)
				visit(sb.append(", "), node.getDimension(i));
			sb.append(']');
		}

		@Override
		public void visit(TACSingletonRef node) throws ShadowException
		{
			sb.append(node.getType().getQualifiedName()).append(":instance");
		}

		@Override
		public void visit(TACLiteral node) throws ShadowException
		{
			sb.append(node);
		}
	}
}
