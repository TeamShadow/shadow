package shadow.tac;

import shadow.typecheck.type.Type;

public class TACVariable
{
	private Type type;
	private String name;
	private int suffix;
	public TACVariable(Type varType, String varName)
	{
		type = varType;
		name = varName;
		suffix = 0;
	}
	public boolean hasType()
	{
		return type != null;
	}
	public Type getType()
	{
		return type;
	}
	public String getName()
	{
		if (suffix == 0)
			return name;
		return name + suffix;
	}
	protected void rename()
	{
		suffix++;
	}

	@Override
	public String toString()
	{
		if (!hasType())
			return getName();
		return getType().toString() + ' ' + getName();
	}
}
