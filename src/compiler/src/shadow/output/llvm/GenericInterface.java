package shadow.output.llvm;

public class GenericInterface implements Comparable<GenericInterface>
{	
	private String name;
	private String mangledName;
	private String mangledGeneric;
	private int size;	

	public GenericInterface(String name, String mangledName, String mangledGeneric, int size)
	{
		this.name = name;
		this.mangledName = mangledName;
		this.mangledGeneric = mangledGeneric;
		this.size = size;		
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getMangledName()
	{
		return mangledName;
	}
	
	public String getMangledGeneric()
	{
		return mangledGeneric;
	}
	
	public int getSize()	
	{
		return size;
	}
	
	@Override
	public int hashCode()
	{
		return name.hashCode();		
	}
	
	@Override
	public int compareTo(GenericInterface other)
	{
		return name.compareTo(other.name);		
	}
	
	@Override
	public boolean equals(Object other)
	{
		if( other instanceof GenericInterface )		
			return name.equals(((GenericInterface)other).getName());
		else
			return false;
	}
}
