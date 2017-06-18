package shadow.typecheck.type;

import java.util.List;

import shadow.typecheck.BaseChecker.SubstitutionKind;

public class UninstantiatedClassType extends ClassType implements UninstantiatedType {
	private final ClassType type;
	private final SequenceType typeArguments;	
	
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
		super(type.getTypeName(), type.getModifiers(), type.getDocumentation(), outer);		
		this.type = type;
		this.typeArguments = typeArguments;
	}
	
	public ClassType getType()
	{
		return type;
	}
	
	

	//doesn't update members and methods
	@Override
	public ClassType partiallyInstantiate() throws InstantiationException
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
	public ClassType instantiate() throws InstantiationException {		
		for( ModifiedType argument : typeArguments ) {			
			if( argument.getType() instanceof UninstantiatedType ) {
				UninstantiatedType uninstantiatedArgument = (UninstantiatedType) argument.getType();
				argument.setType(uninstantiatedArgument.instantiate());
			}
			else if( argument.getType() instanceof ArrayType ) {
				ArrayType arrayArgument = (ArrayType) argument.getType();
				argument.setType(arrayArgument.instantiate());
			}
		}
		
		if( !type.getTypeParameters().canAccept(typeArguments, SubstitutionKind.TYPE_PARAMETER) )
			throw new InstantiationException( "Supplied type arguments " + typeArguments + " do not match type parameters " + type.getTypeParameters());
		
		return type.replace(type.getTypeParameters(), typeArguments);
	}
	
	@Override
	public UninstantiatedClassType partiallyReplace(List<ModifiedType> values, List<ModifiedType> replacements ) throws InstantiationException
	{
		return new UninstantiatedClassType( type, typeArguments.partiallyReplace(values, replacements) );
	}
	
	@Override
	public ClassType replace(List<ModifiedType> values, List<ModifiedType> replacements ) throws InstantiationException
	{
		return new UninstantiatedClassType( type, typeArguments.replace(values, replacements) ).instantiate();
	}
	
	
	@Override
	public String toString(int options) {
		return type.toString() + typeArguments.toString(" [", "]", options);
	}
	
	@Override
	public ClassType getTypeWithoutTypeArguments() {
		return type.getTypeWithoutTypeArguments();		
	}
	
}
