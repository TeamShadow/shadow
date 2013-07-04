package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACUnary extends TACOperand
{
	public static enum Operation
	{
		PLUS(Type.NUMERICAL, '+'),
		MINUS(Type.NUMERICAL, '-'),
		COMPLEMENT(Type.INTEGRAL, '~'),
		NOT(Type.BOOLEAN, '!');

		private Type type;
		private char code;
		private String string;
		private Operation(Type t, char c)
		{
			this(t, c, String.valueOf(c));
		}
		private Operation(Type t, String s)
		{
			this(t, s.charAt(0), s);
		}
		private Operation(Type t, char c, String s)
		{
			type = t;
			code = c;
			string = s;
		}

		public Type getOperandType()
		{
			return type;
		}
		public Type getResultType()
		{
			return type;
		}

		public static Operation valueOf(char c)
		{
			for (Operation operation : values())
				if (operation.code == c)
					return operation;
			return null;
		}
		@Override
		public String toString()
		{
			return string;
		}

		public static enum Type { BOOLEAN, NUMERICAL, INTEGRAL }
	}

	private Operation operation;
	private TACOperand operand;
	public TACUnary(char op, TACOperand firstOperand)
	{
		this(null, Operation.valueOf(op), firstOperand);
	}
	public TACUnary(TACNode node, char op, TACOperand firstOperand)
	{
		this(node, Operation.valueOf(op), firstOperand);
	}
	public TACUnary(Operation op, TACOperand firstOperand)
	{
		this(null, op, firstOperand);
	}
	public TACUnary(TACNode node, Operation op, TACOperand first)
	{
		super(node);
		switch (op.getOperandType())
		{
			case BOOLEAN:
			case NUMERICAL:
			case INTEGRAL:
				break;
			default:
				throw new InternalError("Unknown operand type");
		}
		operation = op;
		operand = check(first, first);
	}

	public Operation getOperation()
	{
		return operation;
	}
	public TACOperand getOperand()
	{
		return operand;
	}

	@Override
	public Type getType()
	{
		switch (operation.getResultType())
		{
			case BOOLEAN:
				return Type.BOOLEAN;
			case NUMERICAL:
			case INTEGRAL:
				return operand.getType();
			default:
				throw new InternalError("Unknown result type");
		}
	}
	@Override
	public int getNumOperands()
	{
		return 1;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (num == 0)
			return operand;
		throw new IndexOutOfBoundsException();
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return "" + operation + operand;
	}
}
