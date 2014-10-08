package shadow.typecheck.type;



public class SimpleModifiedType implements ModifiedType
{
	Type type;
	Modifiers modifiers;	
	
	public SimpleModifiedType( Type type, Modifiers modifiers)
	{
		this.type = type;
		this.modifiers = modifiers;		
	}	
		
	public SimpleModifiedType( Type type)
	{
		this( type, new Modifiers() );
	}
	
	@Override
	public Type getType() {
		return type;
	}

	@Override
	public Modifiers getModifiers() {
		return modifiers;
	}
	
	
	@Override
	public boolean equals(Object o) {
		if( o instanceof ModifiedType  )
		{
			ModifiedType modifiedType = (ModifiedType) o;
			return type.equals(modifiedType.getType()) && modifiers.equals(modifiedType.getModifiers());				
		}
		
		return false;
	}
	

	public SimpleModifiedType replace(SequenceType values, SequenceType replacements ) throws InstantiationException
	{	
		return new SimpleModifiedType( type.replace(values, replacements), modifiers );		
	}

	@Override
	public void setType(Type type) {
		this.type = type;
	}
	
	@Override
	public String toString()
	{
		return modifiers.toString() + " " + type.toString();
	}
}
