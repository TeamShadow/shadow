package shadow.typecheck.type;

public class TypeWithModifiers
{
	private Type type;
	private int modifiers;
	
	public TypeWithModifiers(Type type, int modifiers)
	{
		this.type = type;
		this.modifiers = modifiers;
	}
	
	public Type getType()
	{
		return type;
	}
	
	public int getModifiers()
	{
		return modifiers;
	}
}
