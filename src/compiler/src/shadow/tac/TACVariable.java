package shadow.tac;

import shadow.typecheck.type.Type;

public class TACVariable
{
	private Type type;
	private String name;
	public TACVariable(Type varType, String varName)
	{
		type = varType;
		name = varName;
	}
	public Type getType()
	{
		return type;
	}
	public String getName()
	{
		return name;
	}
	protected void rename()
	{
		name += '_';
	}

	@Override
	public String toString()
	{
		return getType().toString() + ' ' + getName();
	}
}
