package shadow.interpreter;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import shadow.parser.ShadowException;
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
		return getValue();
	}
}
