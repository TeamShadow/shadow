package shadow.typecheck.type;

public class IndexType extends Type
{	
	private ModifiedType index;
	private UnboundMethodType method;
	private ModifiedType readType;
	private ModifiedType storeType;
	private MethodSignature storeSignature;
	private MethodSignature readSignature;

	
	public IndexType(ModifiedType readType, MethodSignature readSignature, ModifiedType index, UnboundMethodType method) {	
		super(null);			
		this.readType = readType;
		this.index = index;			
		this.method = method;
		this.readSignature = readSignature;
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
			storeType = new SimpleModifiedType(signature.getParameterTypes().get(1).getType()); //0 is index, 1 is thing to store
			storeType.getModifiers().addModifier(Modifiers.ASSIGNABLE);
			storeSignature = signature;
			return true;
		}		
		
		return false;
	}	
		
	public ModifiedType getReadType()
	{					
		return readType;
	}
	
	public ModifiedType getStoreType()
	{			
		return storeType;
	}
	
	public MethodSignature getStoreSignature()
	{			
		return storeSignature;
	}
	
	public MethodSignature getReadSignature()
	{			
		return readSignature;
	}
	
	public boolean isStorable()
	{
		return storeType != null;
	}	
	
	//this will probably never be used
	@Override
	public boolean isSubtype(Type other) 
	{
		if( other instanceof IndexType )
		{
			IndexType otherIndex = (IndexType)other;
			//contravariant on index type			
			if( !otherIndex.index.getType().isSubtype(index.getType()))
				return false;
			
			//covariant on get
			if( !getReadType().getType().isSubtype(otherIndex.getReadType().getType()) )
				return false;
			
			if( otherIndex.storeType != null )
			{
				if( storeType == null )
					return false;
				//contravariant on store
				if( !otherIndex.storeType.getType().isSubtype(storeType.getType()) )
					return false;
			}			
			
			return true;
		}
		
		return false;
	}

	@Override
	public IndexType replace(SequenceType values,
			SequenceType replacements) {		
		
		ModifiedType replacedIndex;		
		ModifiedType replacedGetType;
		UnboundMethodType replacedMethod;
		MethodSignature replacedReadSignature;
		IndexType replacement;
		
		replacedGetType = new SimpleModifiedType(readType.getType().replace(values, replacements), readType.getModifiers());		
		replacedIndex = new SimpleModifiedType(index.getType().replace(values, replacements), index.getModifiers());		
		replacedMethod = method.replace(values, replacements);
		replacedReadSignature = readSignature.replace(values, replacements);
		replacement = new IndexType( replacedGetType, replacedReadSignature, replacedIndex,  replacedMethod );
			
		if( storeType != null )
		{
			replacement.storeType = new SimpleModifiedType(storeType.getType().replace(values, replacements), storeType.getModifiers());
			replacement.storeSignature = storeSignature.replace(values, replacements);
		}
			
		return replacement;
	}
	
	@Override
	public String toString()
	{
		return toString(false);
	}
	
	@Override
	public String toString(boolean withBounds)
	{
		StringBuilder sb = new StringBuilder(readType.getType().toString(withBounds));
					
		sb.append(" <= [");
		sb.append(index.getType().toString(withBounds));
		sb.append("]");
				
		if( isStorable() )
		{
			sb.append(" <= ");
			sb.append(getStoreType().getType().toString(withBounds));
		}		
		return sb.toString();		
	}
}
