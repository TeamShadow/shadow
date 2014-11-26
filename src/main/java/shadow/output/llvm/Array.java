package shadow.output.llvm;

import shadow.typecheck.type.ArrayType;

public class Array implements Comparable<Array>
{
	int dimensions;
	String baseClass;
	String name;
	String type;
	
	public Array(ArrayType type)
	{
		name = type.toString();
		dimensions = type.getDimensions();
		baseClass = type.getBaseType().getMangledNameWithGenerics();
		this.type = LLVMOutput.type(type);
	}
	
	public String getType()
	{
		return type;
	}	
	
	public String getName()
	{
		return name;
	}
	
	public int getDimensions()
	{
		return dimensions;
	}
	
	public String getBaseClass()
	{
		return baseClass;
	}
	
	public String getMangledName()
	{
		return baseClass + "_A" + dimensions;
	}		
	
	@Override
	public int hashCode()
	{
		return getMangledName().hashCode();
	}
	
	@Override
	public int compareTo(Array other)
	{
		return getMangledName().compareTo(other.getMangledName());		
	}
	
	@Override
	public String toString()
	{
		return getMangledName();
	}
	
	@Override
	public boolean equals(Object other)
	{
		if( other instanceof Array )		
			return getMangledName().equals(((Array)other).getMangledName());
		else
			return false;
	}
	
}
