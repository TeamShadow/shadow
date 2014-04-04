package shadow.interpreter;

import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class ShadowDouble extends ShadowValue
{
	private double value;
	public ShadowDouble(double value)
	{
		super(Modifiers.IMMUTABLE);
		this.value = value;
	}
	@Override
	public Type getType()
	{
		return Type.DOUBLE;
	}
	public double getValue()
	{
		return value;
	}

	@Override
	protected ShadowValue cast(Type type) throws ShadowException
	{
		if (type.equals(Type.FLOAT))
			return new ShadowFloat((float)getValue());
		if (type.equals(Type.DOUBLE))
			return new ShadowDouble((double)getValue());
		return this;
	}
	@Override
	public ShadowValue copy() throws ShadowException
	{
		return new ShadowDouble(getValue());
	}
	@Override
	public String toString()
	{
		return Double.toString(getValue());
	}
}
