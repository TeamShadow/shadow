package shadow.typecheck.type;


public class PropertyType extends GetSetType
{	
	public PropertyType(MethodSignature getter, MethodSignature setter)
	{			
		this.getter = getter;
		this.setter = setter;	
	}	
	
	@Override
	//probably never gets used
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
			SequenceType replacements) throws InstantiationException {
		
		MethodSignature replacedGetter = null;
		MethodSignature replacedSetter = null;
		if( getter != null )
			replacedGetter = getter.replace(values, replacements);
		if( setter != null )
			replacedSetter = setter.replace(values, replacements);	
			
		return new PropertyType( replacedGetter, replacedSetter );
	}
	
	@Override
	public PropertyType partiallyReplace(SequenceType values,
			SequenceType replacements) {
		
		MethodSignature replacedGetter = null;
		MethodSignature replacedSetter = null;
		if( getter != null )
			replacedGetter = getter.partiallyReplace(values, replacements);
		if( setter != null )
			replacedSetter = setter.partiallyReplace(values, replacements);	
			
		return new PropertyType( replacedGetter, replacedSetter );
	}
	
	@Override
	public void updateFieldsAndMethods() throws InstantiationException
	{
		if( getter != null )
			getter.updateFieldsAndMethods();
		
		if( setter != null )
			setter.updateFieldsAndMethods();
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
