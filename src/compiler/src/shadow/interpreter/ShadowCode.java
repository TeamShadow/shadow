package shadow.interpreter;

import shadow.parser.javacc.ShadowException;
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
			return new ShadowByte((byte)getValue());
		if (type.equals(Type.SHORT))
			return new ShadowShort((short)getValue());
		if (type.equals(Type.INT))
			return new ShadowInt((int)getValue());
		if (type.equals(Type.LONG))
			return new ShadowLong((long)getValue());
		if (type.equals(Type.UBYTE))
			return new ShadowUByte((byte)getValue());
		if (type.equals(Type.USHORT))
			return new ShadowUShort((short)getValue());
		if (type.equals(Type.UINT))
			return new ShadowUInt((int)getValue());
		if (type.equals(Type.ULONG))
			return new ShadowULong((long)getValue());
		return this;
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
