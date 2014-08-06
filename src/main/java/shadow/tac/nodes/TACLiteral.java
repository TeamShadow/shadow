package shadow.tac.nodes;

import java.math.BigInteger;

import shadow.interpreter.ShadowBoolean;
import shadow.interpreter.ShadowCode;
import shadow.interpreter.ShadowDouble;
import shadow.interpreter.ShadowFloat;
import shadow.interpreter.ShadowInteger;
import shadow.interpreter.ShadowString;
import shadow.interpreter.ShadowValue;
import shadow.parser.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class TACLiteral extends TACOperand
{
	private ShadowValue value;

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
			this.value = parseNumber(literal, 2, 8, false);
		else if (lower.endsWith("y"))
			this.value = parseNumber(literal, 1, 7, true);
		else if (lower.endsWith("us"))
			this.value = parseNumber(literal, 2, 16, false);
		else if (lower.endsWith("s"))
			this.value = parseNumber(literal, 1, 15, true);
		else if (lower.endsWith("ui"))
			this.value = parseNumber(literal, 2, 32, false);
		else if (lower.endsWith("i"))
			this.value = parseNumber(literal, 1, 31, true);
		else if (lower.endsWith("ul"))
			this.value = parseNumber(literal, 2, 64, false);
		else if (lower.endsWith("l"))
			this.value = parseNumber(literal, 1, 63, true);
		else if (lower.endsWith("u"))
			this.value = parseNumber(literal, 1, 32, false);
		else if (lower.endsWith("f") && !lower.startsWith("0x") && !lower.startsWith("0c") && !lower.startsWith("0b") )
			this.value = parseFloat(literal, 1);
		else if (lower.endsWith("d") && !lower.startsWith("0x") && !lower.startsWith("0c") && !lower.startsWith("0b") )
			this.value = parseDouble(literal, 1);
		else if (literal.indexOf('.') != -1 || lower.indexOf('e') != -1)
			this.value = parseDouble(literal, 0);
		else
			this.value = parseNumber(literal, 0, 31, true);
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
	private static ShadowDouble parseDouble(String string, int suffix)
	{
		string = string.substring(0, string.length() - suffix);
		return new ShadowDouble(Double.parseDouble(string));
	}
	
	private static ShadowFloat parseFloat(String string, int suffix)
	{
		string = string.substring(0, string.length() - suffix);
		return new ShadowFloat(Float.parseFloat(string));
	}
	
	private static ShadowInteger parseNumber(String string, int suffix, int bits, boolean signed)
	{
		int base = 10;
		int check = 2;
		int start = 0;
		
		string = string.substring(0, string.length() - suffix);
		
		if( string.length() > check && string.charAt(0) == '-' )
		{
			check = 3;
			start = 1;			
		}
				
		if (string.length() > check && string.charAt(start) == '0')
		{
			switch (string.charAt(start + 1))
			{
				case 'b':
				case 'B':
					base = 2;
					string = string.substring(0, start) + string.substring(check);
					break;
				case 'c':
				case 'C':
					base = 8;
					string = string.substring(0, start) + string.substring(check);
					break;
				case 'x':
				case 'X':
					base = 16;
					string = string.substring(0, start) + string.substring(check);
					break;
			}
		}
		
		BigInteger integer = new BigInteger(string, base);
		int length = integer.bitLength(); 
		if( length > bits )		
			throw new NumberFormatException("Number too big");
		
		if( integer.compareTo(BigInteger.ZERO) < 0 && !signed) //negative	
			throw new NumberFormatException("Cannot store a negative value in an unsigned type");
		
		
		int bytes = (int)Math.ceil(bits / 8.0);
		
		return new ShadowInteger( integer, bytes, signed);
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
		throw new IndexOutOfBoundsException("" + num);
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
