package shadow.typecheck.type;


public class PropertyType extends Type {
	
	private MethodSignature getter;
	private MethodSignature setter;
	
	public PropertyType(MethodSignature getter, MethodSignature setter) {	
		super(null);			
		this.getter = getter;
		this.setter = setter;	
	}	

	public MethodSignature getGetter()
	{
		return getter;
	}
	
	public MethodSignature getSetter()
	{
		return setter;
	}
	
	public ModifiedType getGetType()
	{
		if( getter == null )
			return null;
		
		return getter.getReturnTypes().get(0);
	}
	
	public ModifiedType getSetType()
	{
		if( setter == null )
			return null;
		
		return setter.getParameterTypes().get(0);
	}
	
	public boolean isGettable()
	{
		return getter != null;		
	}
	
	public boolean isSettable()
	{
		return setter != null;
	}	
	
	@Override
	public boolean isSubtype(Type other) {

		if( other instanceof PropertyType )
		{
			PropertyType otherProperty = (PropertyType)other;
			if( otherProperty.getter != null )
			{
				if( getter == null )
					return false;
				//covariant on get
				if( !getGetType().getType().isSubtype(otherProperty.getGetType().getType()) )
					return false;
			}
			
			if( otherProperty.setter != null )
			{
				if( setter == null )
					return false;
				//contravariant on set
				if( !otherProperty.getGetType().getType().isSubtype(getGetType().getType()) )
					return false;
			}			
			
			return true;
		}
		
		return false;
	}

	@Override
	public PropertyType replace(SequenceType values,
			SequenceType replacements) {
		
		MethodSignature replacedGetter = null;
		MethodSignature replacedSetter = null;
		if( getter != null )
			replacedGetter = getter.replace(values, replacements);
		if( setter != null )
			replacedSetter = setter.replace(values, replacements);	
			
		return new PropertyType( replacedGetter, replacedSetter );
	}
	
	@Override
	public String toString()
	{
		return toString(false);
	}
	
	@Override
	public String toString(boolean withBounds)
	{
		StringBuilder sb = new StringBuilder("[");
		
		if( isGettable() )
		{
			sb.append("get: ");
			sb.append(getGetType().getType().toString(withBounds));
		}
		
		if( isGettable() && isSettable() )
		{
			sb.append(", ");
		}
		
		if( isSettable() )
		{
			sb.append("set: ");
			sb.append(getSetType().getType().toString(withBounds));
		}
		sb.append("]");
		return sb.toString();		
	}
}
