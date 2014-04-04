package shadow.interpreter;

import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class ShadowUShort extends ShadowValue
{
	private short value;
	public ShadowUShort(short value)
	{
		super(Modifiers.IMMUTABLE);
		this.value = value;
	}
	@Override
	public Type getType()
	{
		return Type.USHORT;
	}
	public short getValue()
	{
		return this.value;
	}

	@Override
	protected ShadowValue cast(Type type) throws ShadowException
	{
		if (type.typeEquals(Type.BYTE))
			return new ShadowByte((byte)(getValue() & 0xffff));
		if (type.typeEquals(Type.SHORT))
			return new ShadowShort((short)(getValue() & 0xffff));
		if (type.typeEquals(Type.INT))
			return new ShadowInt((int)(getValue() & 0xffff));
		if (type.typeEquals(Type.LONG))
			return new ShadowLong((long)(getValue() & 0xffff));
		if (type.typeEquals(Type.UBYTE))
			return new ShadowUShort((byte)(getValue() & 0xffff));
		if (type.typeEquals(Type.USHORT))
			return new ShadowUShort((short)(getValue() & 0xffff));
		if (type.typeEquals(Type.UINT))
			return new ShadowUInt((int)(getValue() & 0xffff));
		if (type.typeEquals(Type.ULONG))
			return new ShadowULong((long)(getValue() & 0xffff));
		return this;
	}
	@Override
	public ShadowValue copy() throws ShadowException
	{
		return new ShadowUShort(getValue());
	}
	@Override
	public String toString()
	{
		return Integer.toString(getValue() & 0xffff);
	}
}
