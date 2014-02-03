package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.PropertyType;
import shadow.typecheck.type.Type;

/** 
 * TAC representation of binary operator
 * Example: x + y 
 * @author Jacob Young
 */

public class TACBinary extends TACOperand
{
	public static enum Operation
	{
		ADD(Type.NUMERICAL, '+'),
		SUBTRACT(Type.NUMERICAL, '-'),
		MULTIPLY(Type.NUMERICAL, '*'),
		DIVIDE(Type.NUMERICAL, '/'),
		MODULUS(Type.NUMERICAL, '%'),

		BITWISE_OR(Type.INTEGRAL, '|'),
		BITWISE_XOR(Type.INTEGRAL, '^'),
		BITWISE_AND(Type.INTEGRAL, '&'),

		SHIFT_LEFT(Type.INTEGRAL, 'l', "<<"),
		SHIFT_RIGHT(Type.INTEGRAL, 'r', ">>"),
		ROTATE_LEFT(Type.INTEGRAL, 'L', "<<<"),
		ROTATE_RIGHT(Type.INTEGRAL, 'R', ">>>"),

		EQUAL(Type.OBJECT, Type.BOOLEAN, "=="),
		NOT_EQUAL(Type.OBJECT, Type.BOOLEAN, "!="),
		LESS_THAN(Type.NUMERICAL, Type.BOOLEAN, '<'),
		GREATER_THAN(Type.NUMERICAL, Type.BOOLEAN, '>'),
		LESS_OR_EQUAL(Type.NUMERICAL, Type.BOOLEAN, '{', "<="),
		GREATER_OR_EQUAL(Type.NUMERICAL, Type.BOOLEAN, '}', ">="),

		OR(Type.BOOLEAN, "or"),
		XOR(Type.BOOLEAN, "xor"),
		AND(Type.BOOLEAN, "and");

//		CONCATENATION(Type.STRING, '#');

		private Type operand, result;
		private char code;
		private String string;
		private Operation(Type type, char c)
		{
			this(type, type, c);
		}
		private Operation(Type type, String s)
		{
			this(type, type, s);
		}
		private Operation(Type type, char c, String s)
		{
			this(type, type, c, s);
		}
		private Operation(Type operandType, Type resultType, char c)
		{
			this(operandType, resultType, c, String.valueOf(c));
		}
		private Operation(Type operandType, Type resultType, String s)
		{
			this(operandType, resultType, s.charAt(0), s);
		}
		private Operation(Type operandType, Type resultType, char c, String s)
		{
			operand = operandType;
			result = resultType;
			code = c;
			string = s;
		}

		public Type getOperandType()
		{
			return operand;
		}
		public Type getResultType()
		{
			return result;
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

		public static enum Type
		{ BOOLEAN, INTEGRAL, NUMERICAL, STRING, CLASS, OBJECT }
	}

	private Operation operation;
	private TACOperand first, second;
	public TACBinary(TACOperand firstOperand, char op,
			TACOperand secondOperand)
	{
		this(null, firstOperand, Operation.valueOf(op), secondOperand);
	}
	public TACBinary(TACNode node, TACOperand firstOperand, char op,
			TACOperand secondOperand)
	{
		this(node, firstOperand, Operation.valueOf(op), secondOperand);
	}
	public TACBinary(TACOperand firstOperand, Operation op,
			TACOperand secondOperand)
	{
		this(null, firstOperand, op, secondOperand);
	}
	public TACBinary(TACNode node, TACOperand firstOperand, Operation op,
			TACOperand secondOperand)
	{
		super(node);
		ModifiedType firstType = firstOperand, secondType = secondOperand;
		if (firstType.getType() instanceof PropertyType)
			firstType = ((PropertyType)firstType.getType()).getGetType();
		if (secondType.getType() instanceof PropertyType)
			secondType = ((PropertyType)secondType.getType()).getGetType();
		switch (op.getOperandType())
		{
			case BOOLEAN:
				firstType = secondType = this;
				break;
			case INTEGRAL:
			case NUMERICAL:
			case OBJECT:
				firstType = secondType = secondType.getType().
						isSubtype(firstType.getType()) ? firstType : secondType;
				break;
			default:
				throw new InternalError("Unknown operand type");
		}
		operation = op;
		first = check(firstOperand, firstType);
		second = check(secondOperand, firstType);
	}

	public TACOperand getFirst()
	{
		return first;
	}
	public Operation getOperation()
	{
		return operation;
	}
	public TACOperand getSecond()
	{
		return second;
	}

	@Override
	public Type getType()
	{
		switch (operation.getResultType())
		{
			case BOOLEAN:
				return Type.BOOLEAN;
			case INTEGRAL:
			case NUMERICAL:
				return first.getType();
			case STRING:
				return Type.STRING;
			default:
				throw new InternalError("Unknown result type");
		}
	}
	@Override
	public int getNumOperands()
	{
		return 2;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (num == 0)
			return first;
		if (num == 1)
			return second;
		throw new IndexOutOfBoundsException();
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	private static boolean paren = false;
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		boolean isParen = paren;
		if (isParen)
			sb.append('(');
		paren = true;
		sb.append(first).append(' ').append(operation).append(' ');
		paren = true;
		sb.append(second);
		if (isParen)
			sb.append(')');
		paren = false;
		return sb.toString();
	}
}
