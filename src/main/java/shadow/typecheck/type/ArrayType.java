package shadow.typecheck.type;

import java.util.Collections;
import java.util.List;

import shadow.ShadowException;

public class ArrayType extends ClassType
{	
	private final int dimensions;
	private final Type baseType;
	private final boolean nullable;
	private final String brackets;
	
	public static String makeBrackets(int dimensions ) {
		StringBuilder brackets = new StringBuilder("[");		
		for( int i = 1; i < dimensions; i++) //no extra comma for 1 dimension 
			brackets.append(",");
		brackets.append("]");
		return brackets.toString();		
	}

	public int getDimensions()
	{
		return dimensions;
	}
	
	@Override
	public int getWidth()
	{
		//return OBJECT.getWidth() + getDimensions() * INT.getWidth();
		return 5;  //not the actual width, just a value that helps sort the fields
		//references have a "width" of 6, which covers either 4 or 8 byte pointers
		//arrays go after the references but before 4 byte primitives
		//also, arrays are never the same width as objects or primitives for overriding purposes
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
		this(baseType, Collections.singletonList(1), 0, false);
	}

	public ArrayType(Type baseType, boolean nullable ) {
		this(baseType, Collections.singletonList(1), 0, nullable);
	}	

	public ArrayType( Type baseType, int dimensions, boolean nullable ) {
		this(baseType, Collections.singletonList(dimensions), 0, nullable);		
	}
	
	public ArrayType(Type baseType, List<Integer> arrayDimensions, boolean nullable ) {
		this( baseType, arrayDimensions, 0, nullable );
	}
	
	protected static Type getLowestBase(Type type) {
		if( type instanceof ArrayType )
			return ((ArrayType)type).recursivelyGetBaseType();
		return type;		
	}
	
	protected ArrayType(Type baseType, List<Integer> arrayDimensions, int index, boolean nullable ) {
		super( getLowestBase(baseType).getTypeName(), new Modifiers(baseType.getModifiers().getModifiers() & ~Modifiers.IMMUTABLE), baseType.getDocumentation(), baseType.getOuter() );		
		if( nullable )		
			setExtendType(Type.ARRAY_NULLABLE);
		else
			setExtendType(Type.ARRAY);
		dimensions = arrayDimensions.get(index);
		brackets = makeBrackets(dimensions);
		if( arrayDimensions.size() == index + 1 )
			this.baseType = baseType;
		else
			this.baseType = new ArrayType( baseType, arrayDimensions, index + 1, nullable);		
		
		if( baseType.isParameterized() )
			setParameterized(true);
		
		this.nullable = nullable;
	}
	
	@Override
	public String toString(int options) {
		if( (options & MANGLE) != 0 ) {
			if( baseType.isPrimitive() )
				return baseType.getTypeName() + "_A" + dimensions;
			else
				return baseType.toString(options & ~CONVERT_ARRAYS) + "_A" + dimensions;
		}
		
		return baseType.toString(options) + brackets;
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
			if( dimensions == other.dimensions && nullable == other.nullable )
				return baseType.equals(other.baseType);			
		}
		return false;
	}
	
	@Override
	public MethodSignature getMatchingMethod(String methodName, SequenceType arguments, SequenceType typeArguments, List<ShadowException> errors ) {		
		return convertToGeneric().getMatchingMethod(methodName, arguments, typeArguments, errors);		
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
			if( type.getDimensions() == other.getDimensions() && type.nullable == other.nullable )
				return type.getBaseType().equals(other.getBaseType());
			else
				return false;
		}
		
		//check generic version
		return convertToGeneric().isSubtype(t);
	}
	
	@Override
	public ArrayType replace(List<ModifiedType> values, List<ModifiedType> replacements ) throws InstantiationException
	{	
		return new ArrayType( baseType.replace(values, replacements), dimensions, nullable);		
	}
		
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
	
	public ArrayType convertToNullable() {
		if( nullable )
			return this;
		else		
			return new ArrayType( baseType, dimensions, true);
	}
	
	public boolean isNullable() {
		return nullable;
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
		
		if( baseType.isParameterizedIncludingOuterClasses() && !baseType.isFullyInstantiated() )
			return true;
		
		if( baseType instanceof ArrayType )
			return ((ArrayType)baseType).containsUnboundTypeParameters();		
		
		return false;		
	}
}
