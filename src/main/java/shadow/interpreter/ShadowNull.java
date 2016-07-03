package shadow.interpreter;

import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class ShadowNull extends ShadowValue {
	private Type type;
	public ShadowNull(Type type)
	{
		super(Modifiers.NULLABLE);
		this.type = type;
	}
	@Override
	public Type getType()
	{
		return type;
	}

	@Override
	public ShadowValue cast(Type type) throws ShadowException
	{
		return new ShadowNull(type);
	}
	@Override
	public ShadowValue copy() throws ShadowException
	{
		return new ShadowNull(type);
	}
	
	@Override
	public String toString() {
		return "null";
	}
}
