package shadow.tac;

import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class TACVariable implements ModifiedType
{
	private ModifiedType type;
	private final String name;
	private int suffix;

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
	@Override
	public Modifiers getModifiers()
	{
		return type.getModifiers();
	}
	@Override
	public Type getType()
	{
		return type.getType();
	}
	@Override
	public void setType(Type newType)
	{
		type.setType(newType);
	}
		
	public ModifiedType getModifiedType() {
		return type;
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
	
	@Override
	public int hashCode() {
		return getName().hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if( other == null || !(other instanceof TACVariable) )
			return false;
		
		if( other == this )
			return true;
		
		TACVariable var = (TACVariable) other;
		
		return getName().equals(var.getName());
	}
}
