package shadow.interpreter;

import java.math.BigInteger;

import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class ShadowULong extends ShadowValue
{
	private long value;
	public ShadowULong(long value)
	{
		super(Modifiers.IMMUTABLE);
		this.value = value;
	}
	@Override
	public Type getType()
	{
		return Type.ULONG;
	}
	public long getValue()
	{
		return this.value;
	}

	@Override
	protected ShadowValue cast(Type type) throws ShadowException
	{
		if (type.equals(Type.BYTE))
			return new ShadowByte((byte)getValue());
		if (type.equals(Type.SHORT))
			return new ShadowShort((short)getValue());
		if (type.equals(Type.INT))
			return new ShadowInt((int)getValue());
		if (type.equals(Type.LONG))
			return new ShadowLong((long)getValue());
		if (type.equals(Type.UBYTE))
			return new ShadowULong((byte)getValue());
		if (type.equals(Type.USHORT))
			return new ShadowULong((short)getValue());
		if (type.equals(Type.UINT))
			return new ShadowULong((int)getValue());
		if (type.equals(Type.ULONG))
			return new ShadowULong((long)getValue());
		return this;
	}
	@Override
	public ShadowValue copy() throws ShadowException
	{
		return new ShadowULong(getValue());
	}
	@Override
	public String toString()
	{
		BigInteger temp = BigInteger.valueOf(getValue());
		if (temp.signum() < 0)
			temp = temp.abs().setBit(63);
		return temp.toString();
	}
}