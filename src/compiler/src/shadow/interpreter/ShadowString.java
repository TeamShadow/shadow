package shadow.interpreter;

import shadow.parser.javacc.ShadowException;
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
		return this;
	}
	@Override
	public ShadowValue copy() throws ShadowException
	{
		return new ShadowString(getValue());
	}
	@Override
	public String toString()
	{
		return getValue();
	}
}
