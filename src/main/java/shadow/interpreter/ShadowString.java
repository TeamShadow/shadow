package shadow.interpreter;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.Type;

public class ShadowString extends ShadowValue
{
	private String value;
	public ShadowString(String value)
	{
		this.value = value;
	}
	@Override
	public Type getType()
	{
		return Type.STRING;
	}
	public String getValue()
	{
		return this.value;
	}

	@Override
	protected ShadowValue cast(Type type) throws ShadowException
	{
		if( !type.equals(Type.STRING) )
			throw new UnsupportedOperationException("Cannot convert type " + Type.STRING + " to " + type);
		
		return this;
	}
	
	public ShadowValue convert(Type type) throws ShadowException
	{
		if( type.equals(Type.STRING))
			return this;
		
		if( type.equals(Type.BYTE) )
			return new ShadowInteger(new BigInteger(value), 1, true);
		else if( type.equals(Type.SHORT) )
			return new ShadowInteger(new BigInteger(value), 2, true);
		else if( type.equals(Type.INT) )
			return new ShadowInteger(new BigInteger(value), 4, true);
		else if( type.equals(Type.LONG) )
			return new ShadowInteger(new BigInteger(value), 8, true);		
		else if( type.equals(Type.UBYTE) )
			return new ShadowInteger(new BigInteger(value), 1, false);
		else if( type.equals(Type.USHORT) )
			return new ShadowInteger(new BigInteger(value), 2, false);
		else if( type.equals(Type.UINT) )
			return new ShadowInteger(new BigInteger(value), 4, false);
		else if( type.equals(Type.ULONG) )
			return new ShadowInteger(new BigInteger(value), 8, false);
		else if( type.equals(Type.DOUBLE) )
			return new ShadowDouble( Double.parseDouble(value));
		else if( type.equals(Type.FLOAT) )
			return new ShadowFloat( Float.parseFloat(value));
		
		throw new UnsupportedOperationException("Cannot convert type " + Type.STRING + " to " + type);		
	}
	
	
	
	@Override
	public ShadowValue copy() throws ShadowException
	{
		return new ShadowString(getValue());
	}
	
	@Override
	public ShadowInteger hash() throws ShadowException
	{		
		int code = 0;		
		byte[] data = value.getBytes(StandardCharsets.UTF_8);
		
		for( int i = 0; i < data.length; i += 1 )
		{
			code *= 31;
			code += Math.abs(data[i]);
		}
		
		return new ShadowInteger( BigInteger.valueOf(code), 4, false);		
	}
	
	@Override
	public String toString()
	{
		return "\"" + getValue() + "\"";
	}
	
	public static ShadowString parseString(String string)
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
				case 'u':
					//add in high surrogates and full 32-bit values at some point
					char code = (char)Integer.parseInt(builder.substring(index + 2, index + 6), 16);
					builder.replace(index, index + 6, String.valueOf(code));
					break;
				default:
					throw new IllegalArgumentException("Unknown escape sequence \\" + builder.charAt(index + 1));
			}
			index++;
		}
		return new ShadowString(builder.toString());
	}	
}
