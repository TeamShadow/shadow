package shadow.typecheck.type;

import java.util.List;

public class ArrayType extends Type
{	
	private int dimensions;
	private Type baseType;
	
	private static String makeName(Type baseType, List<Integer> arrayDimensions)
	{
		String name = baseType.getTypeName();
		for( int i : arrayDimensions )
		{		
			name += "[";
			for( int j = 1; j < i; j++ ) //no extra comma for 1 dimension
				name += ",";
			name += "]";				
		}
		
		return name;		
	}
	
	public int getDimensions()
	{
		return dimensions;
	}
	
	public Type getBaseType()
	{
		return baseType;
	}
	
	public ArrayType(Type baseType, List<Integer> arrayDimensions) {
		super( makeName(baseType, arrayDimensions), baseType.getModifiers(), baseType.getOuter(), Kind.ARRAY );
		dimensions = arrayDimensions.get(0);
		arrayDimensions.remove(0);
		if( arrayDimensions.size() == 0 )
			this.baseType = baseType;
		else
			this.baseType = new ArrayType( baseType, arrayDimensions );
	}	

}
