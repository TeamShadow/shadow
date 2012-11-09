package shadow.typecheck.type;


public class ViewType extends ClassInterfaceBaseType {

	public ViewType(String typeName) {
		super(typeName);
	}

	public ViewType(String typeName, Modifiers modifiers) {
		super(typeName, modifiers);
	}

	@Override
	public boolean isSubtype(Type t)
	{
		return equals(t);
	}

	@Override
	public ViewType replace(SequenceType values,	SequenceType replacements)
	{		
		return this;
	}
	
	@Override
	public String toString()
	{		
		return "#" + super.toString();
	}
}
