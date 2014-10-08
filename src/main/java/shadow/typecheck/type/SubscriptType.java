package shadow.typecheck.type;

public class SubscriptType extends GetSetType
{	
	private ModifiedType index;
	private UnboundMethodType method;		
	
	public SubscriptType(MethodSignature getter, ModifiedType index, UnboundMethodType method)
	{	
		this.index = index;			
		this.method = method;
		this.getter = getter;
	}
	
	public boolean canAccept(ModifiedType input)
	{		
		Type outer = method.getOuter();
		String name = method.getTypeName();
		SequenceType arguments = new SequenceType();
		arguments.add(index);
		arguments.add(input);			
		
		if( outer.getMatchingMethod(name, arguments) != null )
			return true;		
		
		return false;
	}
	
	public boolean applyInput(ModifiedType input)
	{		
		Type outer = method.getOuter();
		String name = method.getTypeName();
		SequenceType arguments = new SequenceType();
		arguments.add(index);
		arguments.add(input);			
		
		MethodSignature signature = outer.getMatchingMethod(name, arguments); 
		if( signature != null )
		{			
			setter = signature;
			return true;
		}		
		
		return false;
	}
	
	//this will probably never be used
	@Override
	public boolean isSubtype(Type other) 
	{
		if( other instanceof SubscriptType )
		{
			SubscriptType otherIndex = (SubscriptType)other;
			//contravariant on index type			
			if( !otherIndex.index.getType().isSubtype(index.getType()))
				return false;
			
			//covariant on get
			if( !getGetType().getType().isSubtype(otherIndex.getGetType().getType()) )
				return false;
			
			if( otherIndex.getSetType() != null )
			{
				if( getSetType() == null )
					return false;
				//contravariant on store
				if( !otherIndex.getSetType().getType().isSubtype(getSetType().getType()) )
					return false;
			}			
			
			return true;
		}
		
		return false;
	}

	@Override
	public SubscriptType replace(SequenceType values,
			SequenceType replacements) throws InstantiationException {		
		
		ModifiedType replacedIndex = new SimpleModifiedType(index.getType().replace(values, replacements), index.getModifiers());
		UnboundMethodType replacedMethod = method.replace(values, replacements);
		MethodSignature replacedGetter = null;		
		if( getter != null )
			replacedGetter = getter.replace(values, replacements);
		
		SubscriptType replacement = new SubscriptType( replacedGetter, replacedIndex,  replacedMethod );
			
		if( setter != null )					
			replacement.setter = setter.replace(values, replacements);
			
		return replacement;
	}
	
	@Override
	public SubscriptType partiallyReplace(SequenceType values,
			SequenceType replacements) {		
		
		ModifiedType replacedIndex = new SimpleModifiedType(index.getType().partiallyReplace(values, replacements), index.getModifiers());
		UnboundMethodType replacedMethod = method.partiallyReplace(values, replacements);
		MethodSignature replacedGetter = null;		
		if( getter != null )
			replacedGetter = getter.partiallyReplace(values, replacements);
		
		SubscriptType replacement = new SubscriptType( replacedGetter, replacedIndex,  replacedMethod );
			
		if( setter != null )					
			replacement.setter = setter.partiallyReplace(values, replacements);
			
		return replacement;
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
		StringBuilder sb = new StringBuilder(getGetType().getType().toString(withBounds));
					
		sb.append(" <= [");
		sb.append(index.getType().toString(withBounds));
		sb.append("]");
				
		if( isSettable() )
		{
			sb.append(" <= ");
			sb.append(getSetType().getType().toString(withBounds));
		}		
		return sb.toString();		
	}
}
