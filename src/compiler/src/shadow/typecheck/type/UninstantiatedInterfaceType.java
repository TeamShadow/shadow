package shadow.typecheck.type;

public class UninstantiatedInterfaceType extends InterfaceType implements UninstantiatedType 
{	
	private InterfaceType type;
	private SequenceType typeArguments;
	
	public UninstantiatedInterfaceType(InterfaceType type, SequenceType typeArguments )
	{
		this(type, typeArguments, type.getOuter() );		
	}
	
	public UninstantiatedInterfaceType(InterfaceType type, SequenceType typeArguments, ClassInterfaceBaseType outer )
	{
		super(type.getTypeName(), type.getModifiers(), outer );		
		this.type = type;
		this.typeArguments = typeArguments;
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
		
		if( !type.getTypeParameters().canAccept(typeArguments) )
			throw new InstantiationException( "Type parameters " + type.getTypeParameters() + " cannot accept type arguments " + typeArguments );
		
		return type.replace(type.getTypeParameters(), typeArguments);
	}
}
