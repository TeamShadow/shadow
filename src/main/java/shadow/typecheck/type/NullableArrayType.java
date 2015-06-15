package shadow.typecheck.type;

import java.util.Collections;
import java.util.List;

import shadow.typecheck.TypeCheckException;

public class NullableArrayType extends ArrayType {
	
	public NullableArrayType(Type baseType) {
		this(baseType, Collections.singletonList(1), 0);	
	}
	
	public NullableArrayType( Type baseType, int dimensions ) {
		this(baseType, Collections.singletonList(dimensions), 0);		
	}
	
	public NullableArrayType(Type baseType, List<Integer> arrayDimensions ) {
		this(baseType, arrayDimensions, 0);		
	}
	
	protected NullableArrayType(Type baseType, List<Integer> arrayDimensions, int index ) {
		super( convertToNullable(baseType), arrayDimensions, index );	
		setExtendType(Type.NULLABLE_ARRAY); // added
		if( arrayDimensions.size() != index + 1 )
			setBaseType(new NullableArrayType( baseType, arrayDimensions, index + 1));
	}
	
	private static Type convertToNullable(Type type) {
		if( type instanceof ArrayType && !(type instanceof NullableArrayType)) {
			ArrayType arrayType = (ArrayType) type;
			return new NullableArrayType(arrayType.getBaseType());
		}
		else
			return type;
	}
	
	@Override
	public boolean equals(Type type)
	{		
		if( type == Type.NULL )
			return false;
		
		if( type instanceof NullableArrayType )
		{
			NullableArrayType other = (NullableArrayType)type;
			if( getDimensions() == other.getDimensions() )
				return getBaseType().equals(other.getBaseType());			
		}
		else if( type instanceof ClassType )
		{
			ClassType other = (ClassType)type;
			if( other.getTypeWithoutTypeArguments() == Type.NULLABLE_ARRAY && other.getTypeParameters().size() == 1 )	
			{
				ModifiedType baseType = other.getTypeParameters().get(0);			
				return baseType != null && this.getBaseType().equals(baseType.getType()) && baseType.getModifiers().getModifiers() == 0;
			}
		}	
		
		return false;
	}
	
	@Override
	public boolean isSubtype(Type t) {		
		if( t == UNKNOWN )
			return false;
	
		if( equals(t) )
			return true;
			
		if( t == OBJECT )
			return true;
		
		if( t instanceof NullableArrayType ) {			
			NullableArrayType other = (NullableArrayType)t;
			//invariant subtyping on arrays
			if( getDimensions() == other.getDimensions() )
				return getBaseType().equals(other.getBaseType());
			else
				return false;
		}
		
		//check generic version
		return convertToGeneric().isSubtype(t);
	}
	
	@Override
	public ClassType convertToGeneric()
	{
		Type base = getBaseType();
		try
		{
			return Type.NULLABLE_ARRAY.replace(Type.NULLABLE_ARRAY.getTypeParameters(), new SequenceType(base));			
		}
		catch(InstantiationException e)
		{}		
				
		return null; //shouldn't happen
	}
	
	@Override
	public NullableArrayType replace(SequenceType values, SequenceType replacements ) throws InstantiationException
	{	
		return new NullableArrayType( getBaseType().replace(values, replacements), getDimensions() );		
	}
	
	@Override
	public MethodSignature getMatchingMethod(String methodName, SequenceType arguments, SequenceType typeArguments, List<TypeCheckException> errors )
	{
		try
		{
			ClassType arrayType = Type.NULLABLE_ARRAY.replace(Type.NULLABLE_ARRAY.getTypeParameters(), new SequenceType(getBaseType()));
			return arrayType.getMatchingMethod(methodName, arguments, typeArguments, errors);
		}
		catch(InstantiationException e)
		{}
		
		return null; //shouldn't happen
	}
}
