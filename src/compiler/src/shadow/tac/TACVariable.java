package shadow.tac;

import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class TACVariable implements ModifiedType
{
	private ModifiedType type;
	private String name;
	private int suffix;
	public TACVariable(ModifiedType varType)
	{
		type = varType;
		name = null;
		suffix = 0;
	}
	public TACVariable(ModifiedType varType, String varName)
	{
		type = varType;
		name = varName;
		suffix = 0;
	}
	public boolean hasType()
	{
		return type != null;
	}
	public Modifiers getModifiers()
	{
		return type.getModifiers();
	}
	public Type getType()
	{
		return type.getType();
	}
	@Override
	public void setType(Type type)
	{
		throw new UnsupportedOperationException();
	}
	public String getOriginalName()
	{
		return name;
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
