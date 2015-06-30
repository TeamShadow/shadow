package shadow.typecheck.type;

import shadow.typecheck.BaseChecker.SubstitutionKind;

public class UninstantiatedInterfaceType extends InterfaceType implements UninstantiatedType 
{	
	private final InterfaceType type;
	private final SequenceType typeArguments;
	
	public SequenceType getTypeArguments()
	{
		return typeArguments;
	}
	
	public InterfaceType getType()
	{
		return type;
	}
	
	public UninstantiatedInterfaceType(InterfaceType type, SequenceType typeArguments)
	{
		super(type.getTypeName(), type.getModifiers(), type.getDocumentation());		
		this.type = type;
		this.typeArguments = typeArguments;
	}
	
	@Override
	public String toString(boolean withBounds)
	{
		return type.toString() + typeArguments.toString(" [", "]", withBounds);
	}
	
	@Override
	public boolean hasInterface(InterfaceType type)
	{	
		try {
			return instantiate().hasInterface(type);
		} 
		catch (InstantiationException e)
		{}		
		
		return false;
	}

	

	@Override
	public InterfaceType instantiate() throws InstantiationException
	{
		for( int i = 0; i < typeArguments.size(); i++ )
		{
			ModifiedType argument = typeArguments.get(i);
			if( argument.getType() instanceof UninstantiatedType )
			{
				UninstantiatedType uninstantiatedArgument = (UninstantiatedType) argument.getType();
				argument.setType(uninstantiatedArgument.instantiate());
			}
		}		
		
		if( !type.getTypeParameters().canAccept(typeArguments, SubstitutionKind.TYPE_PARAMETER) )
			throw new InstantiationException( "Supplied type arguments " + typeArguments + " do not match type parameters " + type.getTypeParameters());
		
		return type.replace(type.getTypeParameters(), typeArguments);
	}
	
	//doesn't update members and methods
	@Override
	public InterfaceType partiallyInstantiate() throws InstantiationException
	{		
		for( int i = 0; i < typeArguments.size(); i++ )
		{
			ModifiedType argument = typeArguments.get(i);
			if( argument.getType() instanceof UninstantiatedType )
			{
				UninstantiatedType uninstantiatedArgument = (UninstantiatedType) argument.getType();
				argument.setType(uninstantiatedArgument.partiallyInstantiate());
			}
		}
		
		if( !type.getTypeParameters().canAccept(typeArguments, SubstitutionKind.TYPE_PARAMETER) )
			throw new InstantiationException( "Supplied type arguments " + typeArguments + " do not match type parameters " + type.getTypeParameters());
		
		return type.partiallyReplace(type.getTypeParameters(), typeArguments);
	}
	
	@Override
	public UninstantiatedInterfaceType partiallyReplace(SequenceType values, SequenceType replacements )
	{
		return new UninstantiatedInterfaceType( type, typeArguments.partiallyReplace(values, replacements) );
	}
	
	@Override
	public InterfaceType replace(SequenceType values, SequenceType replacements ) throws InstantiationException
	{
		return new UninstantiatedInterfaceType( type, typeArguments.replace(values, replacements) ).instantiate();
	}
	
	@Override
	public InterfaceType getTypeWithoutTypeArguments()
	{
		return type.getTypeWithoutTypeArguments();		
	}
}
