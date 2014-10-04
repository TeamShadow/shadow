package shadow.typecheck.type;

import shadow.typecheck.BaseChecker.SubstitutionKind;

public class UninstantiatedInterfaceType extends InterfaceType implements UninstantiatedType 
{	
	private InterfaceType type;
	private SequenceType typeArguments;
	
	public SequenceType getTypeArguments()
	{
		return typeArguments;
	}
	
	public InterfaceType getType()
	{
		return type;
	}
	
	public UninstantiatedInterfaceType(InterfaceType type, SequenceType typeArguments )
	{
		super(type.getTypeName(), type.getModifiers());		
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
	
	@Override
	public InterfaceType getTypeWithoutTypeArguments()
	{
		return type.getTypeWithoutTypeArguments();		
	}
}
