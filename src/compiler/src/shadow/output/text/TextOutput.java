package shadow.output.text;

import java.io.Writer;
import java.util.List;

import shadow.output.AbstractOutput;
import shadow.output.Cleanup;
import shadow.parser.javacc.ShadowException;
import shadow.tac.TACAbstractVisitor;
import shadow.tac.TACMethod;
import shadow.tac.TACModule;
import shadow.tac.TACNodeList;
import shadow.tac.TACVariable;
import shadow.tac.nodes.TACArrayRef;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACBlock;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACCatch;
import shadow.tac.nodes.TACClass;
import shadow.tac.nodes.TACDestinationPhiRef;
import shadow.tac.nodes.TACFieldRef;
import shadow.tac.nodes.TACLabelRef;
import shadow.tac.nodes.TACLabelRef.TACLabel;
import shadow.tac.nodes.TACLandingpad;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACLoad;
import shadow.tac.nodes.TACMethodRef;
import shadow.tac.nodes.TACNewArray;
import shadow.tac.nodes.TACNewObject;
import shadow.tac.nodes.TACNot;
import shadow.tac.nodes.TACOperand;
import shadow.tac.nodes.TACPhiRef;
import shadow.tac.nodes.TACPhiRef.TACPhi;
import shadow.tac.nodes.TACPropertyRef;
import shadow.tac.nodes.TACResume;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACSame;
import shadow.tac.nodes.TACSequence;
import shadow.tac.nodes.TACSequenceRef;
import shadow.tac.nodes.TACSimpleNode;
import shadow.tac.nodes.TACSingletonRef;
import shadow.tac.nodes.TACStore;
import shadow.tac.nodes.TACThrow;
import shadow.tac.nodes.TACTypeId;
import shadow.tac.nodes.TACUnary;
import shadow.tac.nodes.TACUnwind;
import shadow.tac.nodes.TACVariableRef;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Type;

public class TextOutput extends AbstractOutput
{
	private Inline inline = new Inline();
	private static TACLabelRef defaultBlock = new TACLabelRef();
	private TACLabelRef block = defaultBlock;
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
	public void walk(TACNodeList nodes) throws ShadowException
	{
		try
		{
			super.walk(nodes);
		}
		finally
		{
			Cleanup.getInstance().walk(nodes);
		}
	}
	@Override
	public void walk(TACSimpleNode nodes) throws ShadowException
	{
		try
		{
			super.walk(nodes);
		}
		finally
		{
			Cleanup.getInstance().walk(nodes);
		}
	}

	private void startBlock(TACLabelRef label) throws ShadowException
	{
		if (block != null)
			writer.writeLeft("// Missing terminator!!!");
		block = label;
	}
	private void endBlock(boolean terminate) throws ShadowException
	{
		if (block == null)
			writer.writeLeft("// Missing label!!!");
		if (terminate)
			block = null;
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
		tempCounter = 0;
		TACMethodRef methodRef = method.getMethod();
		StringBuilder sb = new StringBuilder(
				methodRef.getModifiers().toString()).
				append(methodRef.getName()).append('(');
		for (TACVariable param : method.getParameters())
			sb.append(param.getType().getQualifiedName()).append(' ').
					append(param.getName()).append(", ");
		if (!method.getParameters().isEmpty())
			sb.delete(sb.length() - 2, sb.length());
		sb.append(") => (");
		for (ModifiedType retType : methodRef.getReturnTypes())
			sb.append(retType.getType().getQualifiedName());
		sb.append(')');
		if (methodRef.isNative())
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
		startBlock(defaultBlock);
		if (!method.getMethod().isNative())
		{
			writer.outdent();
			writer.write('}');
		}
	}

	private String symbol(TACOperand node)
			throws ShadowException
	{
		return symbol(node, true);
	}
	private String symbol(TACOperand node, boolean shouldExist)
			throws ShadowException
	{
		if (node == null)
			return "null";
		Object symbol = node.getData();
		if (!(symbol instanceof String))
		{
			node.setData( symbol = "" + '_' + tempCounter++ + '_');
			if (shouldExist)
				writer.writeLeft("// Unknown reference: " + symbol + "!!!");
		}
		return (String)symbol;
	}

	@Override
	public void visit(TACLabel node) throws ShadowException
	{
		startBlock(node.getRef());
		writer.writeLeft(symbol(node.getRef()) + ':');
	}

	@Override
	public void visit(TACLabelRef node) throws ShadowException
	{
		endBlock(false);
		writer.write("// label " + symbol(node, false) + ';');
	}

	@Override
	public void visit(TACPhiRef node) throws ShadowException
	{
		writer.write("// " + node.getType() + ' ' + symbol(node, false) + ';');
	}

	@Override
	public void visit(TACDestinationPhiRef node) throws ShadowException
	{
		writer.write("// label " + symbol(node, false) + ';');
	}

	@Override
	public void visit(TACStore node) throws ShadowException
	{
		endBlock(false);
		writer.write(inline.visit(inline.visit(new StringBuilder(),
				node.getReference()).append(" = "), node.getValue()).
				append(';').toString());
	}

	@Override
	public void visit(TACBranch node) throws ShadowException
	{
		TACLabelRef blockRef = block;
		endBlock(true);
		StringBuilder sb = new StringBuilder("goto ");
		if (node.isConditional())
			inline.visit(sb, node.getCondition()).append(" ? ").
					append(symbol(node.getTrueLabel())).append(", ").
					append(symbol(node.getFalseLabel()));
		else if (node.isDirect())
		{
			sb.append(symbol(node.getLabel()));
			TACLabel label = node.getLabel().getLabel();
			if (label != null && label.getNext() instanceof TACPhi)
			{
				TACPhiRef phi = ((TACPhi)label.getNext()).getRef();
				writer.write(symbol(phi) + " = " + phi.getValue(blockRef) +
						';');
			}
			if (label == node.getNext())
				return; // skip fall-through branches
		}
		else if (node.isIndirect())
			sb.append(symbol(node.getOperand()));
		writer.write(sb.append(';').toString());
	}

	@Override
	public void visit(TACCall node) throws ShadowException
	{
		endBlock(false);
		if (node.getMethod().isVoid())
			writer.write(inline.visit(new StringBuilder(), node).append(';').
					toString());
	}

	@Override
	public void visit(TACReturn node) throws ShadowException
	{
		endBlock(true);
		StringBuilder sb = new StringBuilder("return");
		if (node.hasReturnValue())
			inline.visit(sb.append(' '), node.getReturnValue());
		writer.write(sb.append(';').toString());
	}

	@Override
	public void visit(TACThrow node) throws ShadowException
	{
		endBlock(true);
		writer.write(inline.visit(new StringBuilder("throw "),
				node.getException()).append(';').toString());
	}

	@Override
	public void visit(TACBlock node) throws ShadowException
	{
		endBlock(false);
		writer.write("// block " + symbol(node, false) + '(' +
				symbol(node.getParent()) + ");");
//		writer.write('{');
//		writer.indent();
	}

	@Override
	public void visit(TACLandingpad node) throws ShadowException
	{
		endBlock(false);
		writer.write("// landingpad;");
	}

	@Override
	public void visit(TACUnwind node) throws ShadowException
	{
		endBlock(false);
		writer.write("// unwind;");
	}

	@Override
	public void visit(TACResume node) throws ShadowException
	{
		endBlock(true);
		writer.write("// resume;");
	}

	private class Inline extends TACAbstractVisitor
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
			sb.append(method.getOuterType().getQualifiedName()).append(':').
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
			visit(sb.append("cast<").append(node.getType().getQualifiedName()).
					append(">("), node.getOperand()).append(')');
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
		public void visit(TACClass node) throws ShadowException
		{
			sb.append(node.getType()).append(":class");
		}

		@Override
		public void visit(TACTypeId node) throws ShadowException
		{
			visit(sb.append("typeid("), node.getOperand()).append(')');
		}

		@Override
		public void visit(TACUnwind node) throws ShadowException
		{
			sb.append("_exception");
		}

		@Override
		public void visit(TACLiteral node) throws ShadowException
		{
			sb.append(node);
		}

		@Override
		public void visit(TACCatch node) throws ShadowException
		{
			sb.append("catch ").append(node.getType().getQualifiedName());
		}
	}
}
