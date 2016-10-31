package shadow.tac.nodes;

import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class TACGlobalRef implements TACReference
{
	private Type type;
	private String name;

	public TACGlobalRef(Type type, String name)
	{
		this.type = type;
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}

	@Override
	public Modifiers getModifiers()
	{
		return new Modifiers();
	}
	@Override
	public Type getType()
	{
		return type;
	}
	@Override
	public String toString()
	{
		return name;
	}

	@Override
	public void setType(Type type)
	{
		throw new UnsupportedOperationException();		
	}
}
