package shadow.interpreter;

import shadow.ShadowException;
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
    public ShadowBoolean equal(ShadowValue other) throws ShadowException
	{
		if( other instanceof ShadowNull )
			return new ShadowBoolean(true);

		throw new InterpreterException("Type " + getType() + " does not match " + other.getType());
	}
	
	@Override
	public String toString() {
		return toLiteral();
	}

	@Override
	public String toLiteral() {
		return "null";
	}
}
