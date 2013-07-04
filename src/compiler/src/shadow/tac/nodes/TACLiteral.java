package shadow.tac.nodes;

import shadow.interpreter.ShadowBoolean;
import shadow.interpreter.ShadowByte;
import shadow.interpreter.ShadowCode;
import shadow.interpreter.ShadowDouble;
import shadow.interpreter.ShadowFloat;
import shadow.interpreter.ShadowInt;
import shadow.interpreter.ShadowLong;
import shadow.interpreter.ShadowShort;
import shadow.interpreter.ShadowString;
import shadow.interpreter.ShadowUByte;
import shadow.interpreter.ShadowUInt;
import shadow.interpreter.ShadowULong;
import shadow.interpreter.ShadowUShort;
import shadow.interpreter.ShadowValue;
import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class TACLiteral extends TACOperand
{
	private ShadowValue value;
	public TACLiteral(String literal)
	{
		this(null, literal);
	}
	public TACLiteral(TACNode node, String literal)
	{
		super(node);
		String lower = literal.toLowerCase();
		if (literal.equals("null"))
			this.value = ShadowValue.NULL;
		else if (literal.startsWith("\'") && literal.endsWith("\'"))
		{
			int value;
			if (literal.charAt(1) == '\\')
			{
				switch (literal.charAt(2))
				{
					case '\'':
						value = '\'';
						break;
					case '\"':
						value = '\"';
						break;
					case '\\':
						value = '\\';
						break;
					case 'b':
						value = '\b';
						break;
					case 'f':
						value = '\f';
						break;
					case 'n':
						value = '\n';
						break;
					case 'r':
						value = '\r';
						break;
					case 't':
						value = '\t';
						break;
					case 'u':
						value = (char)Integer.parseInt(
								literal.substring(3, literal.length() - 1), 16);
						break;
					default:
						value = (char)Integer.parseInt(
								literal.substring(3, literal.length() - 1), 8);
						break;
				}
			}
			else
				value = literal.charAt(1);
			this.value = new ShadowCode(value);
		}
		else if (literal.startsWith("\"") && literal.endsWith("\""))
			this.value = new ShadowString(parseString(literal));
		else if (literal.equals("true"))
			this.value = new ShadowBoolean(true);
		else if (literal.equals("false"))
			this.value = new ShadowBoolean(false);
		else if (lower.endsWith("uy"))
			this.value = new ShadowUByte((byte)parseNumber(literal, 2, 8));
		else if (lower.endsWith("y"))
			this.value = new ShadowByte((byte)parseNumber(literal, 1, 7));
		else if (lower.endsWith("us"))
			this.value = new ShadowUShort((short)parseNumber(literal, 2, 16));
		else if (lower.endsWith("s"))
			this.value = new ShadowShort((short)parseNumber(literal, 1, 15));
		else if (lower.endsWith("ui"))
			this.value = new ShadowUInt((int)parseNumber(literal, 2, 32));
		else if (lower.endsWith("i"))
			this.value = new ShadowInt((int)parseNumber(literal, 1, 31));
		else if (lower.endsWith("ul"))
			this.value = new ShadowULong((long)parseNumber(literal, 2, 64));
		else if (lower.endsWith("l"))
			this.value = new ShadowLong((long)parseNumber(literal, 1, 63));
		else if (lower.endsWith("u"))
			this.value = new ShadowUInt((int)parseNumber(literal, 1, 32));
		else if (lower.endsWith("f") && "xou".indexOf(lower.charAt(1)) == -1)
			this.value = new ShadowFloat((float)parseNumber(literal, 1));
		else if (lower.endsWith("d") && "xou".indexOf(lower.charAt(1)) == -1)
			this.value = new ShadowDouble((double)parseNumber(literal, 1));
		else if (literal.indexOf('.') != -1 || lower.indexOf('e') != -1)
			this.value = new ShadowDouble((double)parseNumber(literal, 0));
		else
			this.value = new ShadowInt((int)parseNumber(literal, 0, 31));
	}

	private static String parseString(String string)
	{
		StringBuilder builder = new StringBuilder(
				string.substring(1, string.length() - 1));
		int index = 0; while ((index = builder.indexOf("\\", index)) != -1)
		{
			switch (builder.charAt(index + 1))
			{
				case 'b':
					builder.replace(index, index + 2, "\b");
					break;
				case 't':
					builder.replace(index, index + 2, "\t");
					break;
				case 'n':
					builder.replace(index, index + 2, "\n");
					break;
				case 'f':
					builder.replace(index, index + 2, "\f");
					break;
				case 'r':
					builder.replace(index, index + 2, "\r");
					break;
				case '\"':
					builder.replace(index, index + 2, "\"");
					break;
				case '\'':
					builder.replace(index, index + 2, "\'");
					break;
				case '\\':
					builder.replace(index, index + 2, "\\");
					break;
				default:
					throw new IllegalArgumentException();
			}
			index++;
		}
		return builder.toString();
	}
	private static double parseNumber(String string, int suffix)
	{
		string = string.substring(0, string.length() - suffix);
		return Double.parseDouble(string);
	}
	private static long parseNumber(String string, int suffix, int bits)
	{
		int base = 10;
		string = string.substring(0, string.length() - suffix);
		if (string.length() > 2 && string.charAt(0) == '0')
		{
			switch (string.charAt(1))
			{
				case 'b':
				case 'B':
					base = 2;
					string = string.substring(2);
					break;
				case 'o':
				case 'O':
					base = 8;
					string = string.substring(2);
					break;
				case 'x':
				case 'X':
					base = 16;
					string = string.substring(2);
					break;
			}
		}
		long value = 0;
		for (int i = 0; i < string.length(); i++)
		{
			if (value < 0)
				throw new NumberFormatException("too big");
			int digit = Character.digit(string.charAt(i), base);
			if (digit == -1)
				throw new NumberFormatException("Invalid digit");
			value = value * base + digit;
		}
		if (value != 1L << bits &&
				(value & ~((1L << (bits >> 1) << (bits + 1 >> 1)) - 1)) != 0)
			throw new NumberFormatException("too big");
		return value;
	}

	public ShadowValue getValue()
	{
		return value;
	}
	@Override
	public Modifiers getModifiers()
	{
		return getValue().getModifiers();
	}
	@Override
	public Type getType()
	{
		return getValue().getType();
	}
	@Override
	public void setType(Type type)
	{
		getValue().setType(type);
	}
	@Override
	public int getNumOperands()
	{
		return 0;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		throw new IndexOutOfBoundsException("num");
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return value.toString();
	}
}
