package shadow.typecheck.type;

import shadow.typecheck.BaseChecker.SubstitutionKind;

public class UninstantiatedClassType extends ClassType implements UninstantiatedType {
	private ClassType type;
	private SequenceType typeArguments;	
	
	public SequenceType getTypeArguments()
	{
		return typeArguments;
	}
	
	public UninstantiatedClassType(ClassType type, SequenceType typeArguments )
	{
		this(type, typeArguments, type.getOuter() );		
	}
	
	public UninstantiatedClassType(ClassType type, SequenceType typeArguments, Type outer )
	{
		super(type.getTypeName(), type.getModifiers(), outer );		
		this.type = type;
		this.typeArguments = typeArguments;
	}
	
	public ClassType getType()
	{
		return type;
	}
	
	@Override
	public ClassType instantiate() throws InstantiationException
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
	public String toString(boolean withBounds)
	{
		return type.toString() + typeArguments.toString(" [", "]", withBounds);
	}
	
	@Override
	public ClassType getTypeWithoutTypeArguments()
	{
		return type.getTypeWithoutTypeArguments();		
	}
	
}
