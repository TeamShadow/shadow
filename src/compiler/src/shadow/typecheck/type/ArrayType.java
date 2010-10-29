package shadow.typecheck.type;

import java.util.List;

public class ArrayType extends Type
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
	
	public int getDimensions()
	{
		return dimensions;
	}
	
	public Type getBaseType()
	{
		return baseType;
	}
	
	public ArrayType(Type baseType, List<Integer> arrayDimensions ) {
		this( baseType, arrayDimensions, 0 );
	}	
	
	public ArrayType(Type baseType, List<Integer> arrayDimensions, int index ) {
		super( makeName(baseType, arrayDimensions, index), baseType.getModifiers(), baseType.getOuter(), Kind.ARRAY );
		dimensions = arrayDimensions.get(index);		
		if( arrayDimensions.size() == index + 1 )
			this.baseType = baseType;
		else
			this.baseType = new ArrayType( baseType, arrayDimensions, index + 1);
	}	

}
