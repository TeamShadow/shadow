package shadow.typecheck.type;

import java.util.Collections;
import java.util.List;

public class ArrayType extends ClassType
{	
	private int dimensions;
	private Type baseType;
	
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
	
	
	private static String makeName(Type baseType, int dimensions )
	{
		StringBuilder name = new StringBuilder(baseType.getTypeName());
		name.append("[");
		for( int j = 1; j < dimensions; j++ ) //no extra comma for 1 dimension
			name.append(",");
		name.append("]");				
		
		return name.toString();		
	}
	
	public int getDimensions()
	{
		return dimensions;
	}
	
	@Override
	public int getWidth()
	{
		return OBJECT.getWidth() + getDimensions() * INT.getWidth();
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
	
	public ArrayType(Type baseType)
	{
		this(baseType, Collections.singletonList(1), 0);
	}
	
	public ArrayType( Type baseType, int dimensions )
	{
		super(makeName(baseType, dimensions), baseType.getModifiers(), baseType.getOuter());
		
		this.baseType = baseType;
		this.dimensions = dimensions;
		
		if( baseType.isParameterized() )
			setParameterized(true);
	}
	
	public ArrayType(Type baseType, List<Integer> arrayDimensions ) {
		this( baseType, arrayDimensions, 0 );
	}	
	
	protected ArrayType(Type baseType, List<Integer> arrayDimensions, int index ) {
		super( makeName(baseType, arrayDimensions, index), baseType.getModifiers(), null );	
		setExtendType(Type.ARRAY); // added
		dimensions = arrayDimensions.get(index);		
		if( arrayDimensions.size() == index + 1 )
			this.baseType = baseType;
		else
			this.baseType = new ArrayType( baseType, arrayDimensions, index + 1);
		
		if( baseType.isParameterized() )
			setParameterized(true);
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
			return true;
		
		if( type instanceof ArrayType )
		{
			ArrayType other = (ArrayType)type;
			if( dimensions == other.dimensions )
				return baseType.equals(other.baseType);			
		}
		else if( type instanceof ClassType )
		{
			ClassType other = (ClassType)type;
			if( other.getTypeWithoutTypeArguments() == Type.ARRAY && other.getTypeParameters().size() == 1 )	
			{
				ModifiedType baseType = other.getTypeParameters().get(0);			
				return baseType != null && this.getBaseType().equals(baseType.getType()) && baseType.getModifiers().getModifiers() == 0;
			}
		}	
		
		return false;
	}
	
	public boolean isSubtype(Type t)
	{		
		if( t == UNKNOWN )
			return false;
	
		if( equals(t) )
			return true;
			
		if( t == OBJECT || t == ARRAY )
			return true;
		if( t instanceof ArrayType )
		{
			ArrayType type = (ArrayType)this;
			ArrayType other = (ArrayType)t;
			//invariant subtyping on arrays
			if( type.getDimensions() == other.getDimensions() )
				return type.getBaseType().equals(other.getBaseType());
			else
				return false;
		}
		else
			return false;
	}
	
	public ArrayType replace(SequenceType values, SequenceType replacements )
	{	
		return new ArrayType( baseType.replace(values, replacements), dimensions  );		
	}
}
