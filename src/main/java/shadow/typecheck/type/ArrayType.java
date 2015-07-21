package shadow.typecheck.type;

import java.util.Collections;
import java.util.List;

import shadow.typecheck.Package;
import shadow.typecheck.TypeCheckException;

public class ArrayType extends ClassType
{	
	private final int dimensions;
	private final Type baseType;
	private final boolean nullable;
	
	private static String makeName(Type baseType, List<Integer> arrayDimensions, int index )
	{
		StringBuilder name = new StringBuilder(baseType.getTypeName());
		int i;
		for( ; index < arrayDimensions.size(); index++ )
		{		
			i = arrayDimensions.get(index);
			name.append("[");
			for( int j = 1; j < i; j++ ) //no extra comma for 1 dimension
				name.append(",");
			name.append("]");				
		}
		
		return name.toString();		
	}
	
	@Override
	public String getQualifiedName(boolean withBounds) {
		if( getSuperBaseType().isPrimitive() )
			return toString(withBounds);
		
		Package _package = getSuperBaseType().getPackage();		
		if( _package == null || _package.getQualifiedName().isEmpty())
			return "default@" + toString(withBounds);
		else
			return _package.getQualifiedName() + '@' + toString(withBounds);		
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
	
	public Type getSuperBaseType()
	{
		if (baseType instanceof ArrayType)
			return ((ArrayType)baseType).getSuperBaseType();
		
		return baseType;
	}
	
	public Type getBaseType()
	{
		return baseType;
	}
	
	@Override
	public String getMangledName()
	{
		return getBaseType().getMangledName() + "_A" + dimensions;
	}
	
	@Override
	public String getMangledNameWithGenerics(boolean convertArrays)
	{
		return getBaseType().getMangledNameWithGenerics(false) + "_A" + dimensions;
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
	
	protected ArrayType(Type baseType, List<Integer> arrayDimensions, int index, boolean nullable ) {
		super(makeName(baseType, arrayDimensions, index), new Modifiers(baseType.getModifiers().getModifiers() & ~Modifiers.IMMUTABLE), baseType.getDocumentation(), baseType.getOuter() );	
		if( nullable )		
			setExtendType(Type.ARRAY_NULLABLE);
		else
			setExtendType(Type.ARRAY);
		dimensions = arrayDimensions.get(index);		
		if( arrayDimensions.size() == index + 1 )
			this.baseType = baseType;
		else
			this.baseType = new ArrayType( baseType, arrayDimensions, index + 1, nullable);		
		
		if( baseType.isParameterized() )
			setParameterized(true);
		
		this.nullable = nullable;
	}
	
	public String toString(boolean withBounds) {		
		//peels off last set of brackets, important for arrays of arrays
		String brackets = getTypeName().substring(getTypeName().lastIndexOf('['));		
		
		return baseType.toString(withBounds) + brackets;
	}
	
	
	@Override
	public SequenceType getTypeParameters()
	{
		return baseType.getTypeParameters();		
	}
	
	@Override
	public boolean equals(Type type)
	{		
		if( type == Type.NULL )
			return false;
		
		if( type instanceof ArrayType )
		{
			ArrayType other = (ArrayType)type;
			if( dimensions == other.dimensions && nullable == other.nullable )
				return baseType.equals(other.baseType);			
		}
		/*  //this allows generic arrays to be assigned directly
		else if( type instanceof ClassType )
		{
			ClassType other = (ClassType)type;
			if( other.getTypeWithoutTypeArguments() == Type.ARRAY && other.getTypeParameters().size() == 1 )	
			{
				ModifiedType baseType = other.getTypeParameters().get(0);			
				return baseType != null && this.getBaseType().equals(baseType.getType()) && baseType.getModifiers().getModifiers() == 0;
			}
		}	*/
		
		return false;
	}
	
	@Override
	public MethodSignature getMatchingMethod(String methodName, SequenceType arguments, SequenceType typeArguments, List<TypeCheckException> errors ) {		
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
	public ArrayType replace(SequenceType values, SequenceType replacements ) throws InstantiationException
	{	
		return new ArrayType( baseType.replace(values, replacements), dimensions, nullable);		
	}
		
	public ClassType convertToGeneric()
	{
		Type base = baseType;
				
		//if( base instanceof ArrayType )
		//	base = ((ArrayType)base).convertToGeneric();
		
		try
		{
			if( nullable )
				return Type.ARRAY_NULLABLE.replace(Type.ARRAY_NULLABLE.getTypeParameters(), new SequenceType(base));
			else
				return Type.ARRAY.replace(Type.ARRAY.getTypeParameters(), new SequenceType(base));
		}
		catch(InstantiationException e)
		{}		
				
		return null; //shouldn't happen
	}
	
	public ArrayType convertToNullable()
	{
		if( nullable )
			return this;
		else		
			return new ArrayType( baseType, dimensions, true);
	}
	
	public boolean isNullable() {
		return nullable;
	}

	@Override
	public boolean isRecursivelyParameterized()
	{
		return baseType.isRecursivelyParameterized();
	}
	
	@Override
	public boolean isFullyInstantiated()
	{
		return baseType.isFullyInstantiated();
	}	
	
	public boolean containsUnboundTypeParameters()
	{
		if( baseType instanceof TypeParameter )
			return true;
		
		if( baseType.isParameterizedIncludingOuterClasses() && !baseType.isFullyInstantiated() )
			return true;
		
		if( baseType instanceof ArrayType )
			return ((ArrayType)baseType).containsUnboundTypeParameters();		
		
		return false;		
	}
}
