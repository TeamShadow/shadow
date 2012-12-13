package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACLiteral extends TACOperand
{
	private Type type;
	private Object value;
	public TACLiteral(String literal)
	{
		this(null, literal);
	}
	public TACLiteral(TACNode node, String literal)
	{
		super(node);
		if (literal.equals("null"))
		{
			type = Type.NULL;
			value = null;
		}
		else if (literal.equals("true"))
		{
			type = Type.BOOLEAN;
			value = true;
		}
		else if (literal.equals("false"))
		{
			type = Type.BOOLEAN;
			value = false;
		}
		else if (literal.startsWith("\'") && literal.endsWith("\'"))
		{
			type = Type.CODE;
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
						value = (char)Integer.parseInt(literal.substring(3,
								literal.length() - 1), 16);
						break;
				}
			}
			else
				value = literal.charAt(1);
		}
		else if (literal.startsWith("\"") && literal.endsWith("\""))
		{
			type = Type.STRING;
			value = parseString(literal.substring(1, literal.length() - 1));
		}
		else if (literal.endsWith("uy"))
		{
			type = Type.UBYTE;
			value = (byte)parseNumber(literal.substring(0, literal.length() -
					2), 8);
		}
		else if (literal.endsWith("y"))
		{
			type = Type.BYTE;
			value = (byte)parseNumber(literal.substring(0, literal.length() -
					1), 8);
		}
		else if (literal.endsWith("us"))
		{
			type = Type.USHORT;
			value = (short)parseNumber(literal.substring(0, literal.length() -
					2), 16);
		}
		else if (literal.endsWith("s"))
		{
			type = Type.SHORT;
			value = (short)parseNumber(literal.substring(0, literal.length() -
					1), 16);
		}
		else if (literal.endsWith("ui"))
		{
			type = Type.UINT;
			value = (int)parseNumber(literal.substring(0, literal.length() - 2),
					32);
		}
		else if (literal.endsWith("i"))
		{
			type = Type.INT;
			value = (int)parseNumber(literal.substring(0, literal.length() - 1),
					32);
		}
		else if (literal.endsWith("ul"))
		{
			type = Type.ULONG;
			value = (long)parseNumber(literal.substring(0, literal.length() -
					2), 64);
		}
		else if (literal.endsWith("l"))
		{
			type = Type.LONG;
			value = (long)parseNumber(literal.substring(0, literal.length() -
					1), 64);
		}
		else if (literal.endsWith("u"))
		{
			type = Type.UINT;
			value = (int)parseNumber(literal.substring(0, literal.length() - 1),
					32);
		}
		else if (literal.endsWith("f"))
		{
			type = Type.FLOAT;
			value = (float)Float.parseFloat(literal.substring(0,
					literal.length() - 1));
		}
		else if (literal.endsWith("d"))
		{
			type = Type.DOUBLE;
			value = (double)Double.parseDouble(literal.substring(0,
					literal.length() - 1));
		}
		else if (literal.indexOf('.') != -1 ||
				literal.indexOf('e') != -1 || literal.indexOf('E') != -1)
		{
			type = Type.DOUBLE;
			value = (double)Double.parseDouble(literal);
		}
		else
		{
			type = Type.INT;
			value = (int)parseNumber(literal, 32);
		}
		setName(toString());
	}

	private static String parseString(String string)
	{
		StringBuilder builder = new StringBuilder(string);
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
	private static long parseNumber(String string, int bits)
	{
		int base = 10;
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
		if ((value & ~(((1L << (bits >> 1)) << (bits >> 1)) - 1)) != 0)
			throw new NumberFormatException("too big");
		return value;
	}

	public Object getValue()
	{
		return value;
	}

	@Override
	public Type getType()
	{
		return type;
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
		return String.valueOf(value);
	}
}
