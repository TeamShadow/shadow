package shadow.output.llvm;

import java.util.ArrayList;

public class Generic implements Comparable<Generic>
{	
	private String name;
	private String mangledName;
	private String mangledGeneric;
	private int size;
	boolean isInterface;
	ArrayList<String> parameters = new ArrayList<String>();
	ArrayList<String> interfaces = new ArrayList<String>();
	String parent = null;

	public Generic(String name, String mangledName, String mangledGeneric, int size, boolean isInterface)
	{
		this.name = name;
		this.mangledName = mangledName;
		this.mangledGeneric = mangledGeneric;
		this.size = size;		
		this.isInterface = isInterface;
	}
	
	public void addParent(String parent)
	{
		this.parent = parent;
	}
	
	public String getParent()
	{
		return parent;
	}
	
	public boolean isInterface()
	{
		return isInterface;
	}
	
	public ArrayList<String> getParameters()
	{
		return parameters;
	}
	
	public void addParameter(String parameter)
	{
		parameters.add(parameter);		
	}
	
	public ArrayList<String> getInterfaces()
	{
		return interfaces;
	}
	
	public void addInterface(String _interface)
	{
		interfaces.add(_interface);		
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
	public int compareTo(Generic other)
	{
		return name.compareTo(other.name);		
	}
	
	@Override
	public boolean equals(Object other)
	{
		if( other instanceof Generic )		
			return name.equals(((Generic)other).getName());
		else
			return false;
	}
}
