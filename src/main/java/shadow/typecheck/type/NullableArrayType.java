package shadow.typecheck.type;

import java.util.List;

public class NullableArrayType extends ArrayType {
	
	public NullableArrayType(Type baseType) {
		super(baseType);
	}
	
	public NullableArrayType( Type baseType, int dimensions ) {
		super(baseType, dimensions);
	}
	
	public NullableArrayType(Type baseType, List<Integer> arrayDimensions ) {
		super(baseType, arrayDimensions);
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
	

}
