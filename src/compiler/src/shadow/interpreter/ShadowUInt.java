package shadow.interpreter;

import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class ShadowUInt extends ShadowValue
{
	private int value;
	public ShadowUInt(int value)
	{
		super(Modifiers.IMMUTABLE);
		this.value = value;
	}
	@Override
	public Type getType()
	{
		return Type.UINT;
	}
	public int getValue()
	{
		return this.value;
	}

	@Override
	protected ShadowValue cast(Type type) throws ShadowException
	{
		if (type.equals(Type.BYTE))
			return new ShadowByte((byte)(getValue() & 0xffff));
		if (type.equals(Type.SHORT))
			return new ShadowShort((short)(getValue() & 0xffff));
		if (type.equals(Type.INT))
			return new ShadowInt((int)(getValue() & 0xffff));
		if (type.equals(Type.LONG))
			return new ShadowLong((long)(getValue() & 0xffff));
		if (type.equals(Type.UBYTE))
			return new ShadowUInt((byte)(getValue() & 0xffff));
		if (type.equals(Type.USHORT))
			return new ShadowUInt((short)(getValue() & 0xffff));
		if (type.equals(Type.UINT))
			return new ShadowUInt((int)(getValue() & 0xffff));
		if (type.equals(Type.ULONG))
			return new ShadowULong((long)(getValue() & 0xffff));
		return this;
	}
	@Override
	public ShadowValue copy() throws ShadowException
	{
		return new ShadowUInt(getValue());
	}
	@Override
	public String toString()
	{
		return Long.toString(getValue() & 0xffffffffl);
	}
}