package shadow.interpreter;

import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class ShadowBoolean extends ShadowValue
{
	private boolean value;
	public ShadowBoolean(boolean value)
	{
		super(Modifiers.IMMUTABLE);
		this.value = value;
	}
	@Override
	public Type getType()
	{
		return Type.BOOLEAN;
	}
	public boolean getValue()
	{
		return value;
	}

	@Override
	protected ShadowValue cast(Type type) throws ShadowException
	{
		return this;
	}
	@Override
	public ShadowValue copy() throws ShadowException
	{
		return new ShadowBoolean(value);
	}
}
