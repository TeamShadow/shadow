package shadow.interpreter;

import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class ShadowUByte extends ShadowValue
{
	private byte value;
	public ShadowUByte(byte value)
	{
		super(Modifiers.IMMUTABLE);
		this.value = value;
	}
	@Override
	public Type getType()
	{
		return Type.UBYTE;
	}
	public byte getValue()
	{
		return this.value;
	}

	@Override
	protected ShadowValue cast(Type type) throws ShadowException
	{
		if (type.equals(Type.BYTE))
			return new ShadowByte((byte)(getValue() & 0xff));
		if (type.equals(Type.SHORT))
			return new ShadowShort((short)(getValue() & 0xff));
		if (type.equals(Type.INT))
			return new ShadowInt((int)(getValue() & 0xff));
		if (type.equals(Type.LONG))
			return new ShadowLong((long)(getValue() & 0xff));
		if (type.equals(Type.UBYTE))
			return new ShadowUByte((byte)(getValue() & 0xff));
		if (type.equals(Type.USHORT))
			return new ShadowUShort((short)(getValue() & 0xff));
		if (type.equals(Type.UINT))
			return new ShadowUInt((int)(getValue() & 0xff));
		if (type.equals(Type.ULONG))
			return new ShadowULong((long)(getValue() & 0xff));
		return this;
	}
	@Override
	public ShadowValue copy() throws ShadowException
	{
		return new ShadowUByte(getValue());
	}
	@Override
	public String toString()
	{
		return Integer.toString(getValue() & 0xff);
	}
}
