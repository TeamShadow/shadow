package shadow.typecheck.type;

public class UninstantiatedClassType extends ClassType implements UninstantiatedType {
	private ClassType type;
	private SequenceType typeArguments;	
	
	public UninstantiatedClassType(ClassType type, SequenceType typeArguments )
	{
		super(type.getTypeName(), type.getModifiers(), type.getOuter() );		
		this.type = type;
		this.typeArguments = typeArguments;
	}
	
	@Override
	public ClassType instantiate()
	{
		SequenceType instantiatedArguments = new SequenceType();
		
		for( int i = 0; i < typeArguments.size(); i++ )
		{
			ModifiedType argument = typeArguments.get(i);
			if( argument.getType() instanceof UninstantiatedType )
			{
				UninstantiatedType uninstantiatedArgument = (UninstantiatedType) argument.getType();
				instantiatedArguments.add(new SimpleModifiedType( uninstantiatedArgument.instantiate(), argument.getModifiers()  ) );
			}
			else
				instantiatedArguments.add( argument );
		}		
		
		return type.replace(type.getTypeParameters(), instantiatedArguments);
	}
	
}
