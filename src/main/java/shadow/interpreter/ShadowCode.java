package shadow.interpreter;

import java.math.BigInteger;

import shadow.parser.ShadowException;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class ShadowCode extends ShadowValue
{
	private int value;
	public ShadowCode(int value)
	{
		super(Modifiers.IMMUTABLE);
		this.value = value;
	}
	@Override
	public Type getType()
	{
		return Type.CODE;
	}

	public int getValue()
	{
		return value;
	}

	@Override
	protected ShadowValue cast(Type type) throws ShadowException
	{
		if (type.equals(Type.BYTE))
			return new ShadowInteger(BigInteger.valueOf(getValue()), 1, true);
		if (type.equals(Type.SHORT))
			return new ShadowInteger(BigInteger.valueOf(getValue()), 2, true);
		if (type.equals(Type.INT))
			return new ShadowInteger(BigInteger.valueOf(getValue()), 4, true);
		if (type.equals(Type.LONG))
			return new ShadowInteger(BigInteger.valueOf(getValue()), 8, true);
		if (type.equals(Type.UBYTE))
			return new ShadowInteger(BigInteger.valueOf(getValue()), 1, false);
		if (type.equals(Type.USHORT))
			return new ShadowInteger(BigInteger.valueOf(getValue()), 2, false);
		if (type.equals(Type.UINT))
			return new ShadowInteger(BigInteger.valueOf(getValue()), 4, false);
		if (type.equals(Type.ULONG))
			return new ShadowInteger(BigInteger.valueOf(getValue()), 8, false);
		throw new UnsupportedOperationException("Cannot cast " + getType() + " to " + type);
	}
	@Override
	public ShadowValue copy() throws ShadowException
	{
		return new ShadowCode(getValue());
	}
	@Override
	public String toString()
	{
		return new StringBuilder(1).appendCodePoint(getValue()).toString();
	}
}
