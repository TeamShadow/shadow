package shadow.typecheck.type;

import java.util.List;

import shadow.ShadowException;

public class ArrayType extends ClassType
{	
	private final Type baseType;
	private final boolean nullable;
	
	@Override
	public int getWidth()
	{
		return 6;
		//not necessarily the actual width, just a value that helps sort the fields
		//references have a "width" of 6, which covers either 4 or 8 byte pointers
		// interfaces first
		// then longs and ulongs
		// then regular references and arrays
		// then smaller primitives
	}
	
	public Type recursivelyGetBaseType() {
		if (baseType instanceof ArrayType)
			return ((ArrayType)baseType).recursivelyGetBaseType();
		
		return baseType;
	}	

	public Type getBaseType() {
		return baseType;
	}
	
	public ArrayType(Type baseType) {
		this(baseType, false);
	}	
	
	protected static Type getLowestBase(Type type) {
		if( type instanceof ArrayType )
			return ((ArrayType)type).recursivelyGetBaseType();
		return type;		
	}
	
	public ArrayType(Type baseType, boolean nullable ) {
		this( baseType, 1, nullable );
	}
	
	public ArrayType(Type baseType, int dimensions, boolean nullable ) {
		super( getLowestBase(baseType).getTypeName(), new Modifiers(baseType.getModifiers().getModifiers() & ~Modifiers.IMMUTABLE), baseType.getDocumentation(), baseType.getOuter() );		
		if( nullable )		
			setExtendType(Type.ARRAY_NULLABLE);
		else
			setExtendType(Type.ARRAY);	
		
		if( dimensions == 1 )
			this.baseType = baseType;
		else
			this.baseType = new ArrayType( baseType, dimensions - 1, nullable);	
		
		if( baseType.isParameterized() )
			setParameterized(true);
		
		this.nullable = nullable;
	}
	
	@Override
	public String toString(int options) {
		if( (options & MANGLE) != 0 ) {
			if( baseType.isPrimitive() )
				return baseType.getTypeName() + "_A";
			else
				return baseType.toString(options & ~CONVERT_ARRAYS) + "_A";
		}
		
		return baseType.toString(options) + "[]";
	}
	
	@Override
	public SequenceType getTypeParameters() {
		return baseType.getTypeParameters();		
	}
	
	@Override
	public boolean equals(Type type) {		
		if( type == Type.NULL )
			return false;
		
		if( type instanceof ArrayType )
		{
			ArrayType other = (ArrayType)type;
			if( nullable == other.nullable )
				return baseType.equals(other.baseType);			
		}
		return false;
	}
	
	@Override
	public MethodSignature getMatchingMethod(String methodName, SequenceType arguments, SequenceType typeArguments, List<ShadowException> errors ) {		
		if( nullable )
			return Type.ARRAY_NULLABLE.getMatchingMethod(methodName, arguments, typeArguments, errors);
		else
			return Type.ARRAY.getMatchingMethod(methodName, arguments, typeArguments, errors);
	}
	
	@Override
	public boolean isSubtype(Type t) {		
		if( t == UNKNOWN )
			return false;
		
		if( t == OBJECT )
			return true;
	
		if( equals(t) )
			return true;
		
		if( t instanceof ArrayType ) {
			ArrayType type = (ArrayType)this;
			ArrayType other = (ArrayType)t;
			//invariant subtyping on arrays
			if( type.nullable == other.nullable )
				return type.getBaseType().equals(other.getBaseType());
			else
				return false;
		}
		
		//check generic version
		//return convertToGeneric().isSubtype(t);
		return false;
	}
	
	@Override
	public ArrayType replace(List<ModifiedType> values, List<ModifiedType> replacements ) throws InstantiationException
	{	
		return new ArrayType( baseType.replace(values, replacements), nullable);		
	}
		
	/*
	public ClassType convertToGeneric() {
		Type base = baseType;				
		
		try {
			if( nullable )
				return Type.ARRAY_NULLABLE.replace(Type.ARRAY_NULLABLE.getTypeParameters(), new SequenceType(base));
			else
				return Type.ARRAY.replace(Type.ARRAY.getTypeParameters(), new SequenceType(base));
		}
		catch(InstantiationException e)
		{}		
				
		return null; //shouldn't happen
	}
	*/
	
	public ArrayType convertToNullable() {
		if( nullable )
			return this;
		else		
			return new ArrayType( baseType, true);
	}
	
	public boolean isNullable() {
		return nullable;
	}
	
	public ArrayType instantiate() throws InstantiationException {
		if( recursivelyGetBaseType() instanceof UninstantiatedType ) {			
			if( baseType instanceof UninstantiatedType )
				return new ArrayType(((UninstantiatedType)baseType).instantiate(), nullable);
			//must be an array of arrays
			else
				return new ArrayType(((ArrayType)baseType).instantiate(), nullable);				
		}
		else
			return this;
	}
	
	@Override
	public boolean isRecursivelyParameterized() {
		return baseType.isRecursivelyParameterized();
	}
	

	@Override
	public boolean isFullyInstantiated() {
		return baseType.isFullyInstantiated();
	}	
	
	public boolean containsUnboundTypeParameters() {
		if( baseType instanceof TypeParameter )
			return true;
		
		if( baseType.isParameterized() && !baseType.isFullyInstantiated() )
			return true;
		
		if( baseType instanceof ArrayType )
			return ((ArrayType)baseType).containsUnboundTypeParameters();		
		
		return false;		
	}
}
