package shadow.interpreter;

import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class ShadowFloat extends ShadowValue
{
	private float value;
	public ShadowFloat(float value)
	{
		super(Modifiers.IMMUTABLE);
		this.value = value;
	}
	@Override
	public Type getType()
	{
		return Type.FLOAT;
	}
	public float getValue()
	{
		return value;
	}

	@Override
	protected ShadowValue cast(Type type) throws ShadowException
	{
		if (type.typeEquals(Type.FLOAT))
			return new ShadowFloat((float)getValue());
		if (type.typeEquals(Type.DOUBLE))
			return new ShadowDouble((double)getValue());
		return this;
	}
	@Override
	public ShadowValue copy() throws ShadowException
	{
		return new ShadowFloat(getValue());
	}
	@Override
	public String toString()
	{
		return Float.toString(getValue());
	}
}
