package shadow.typecheck;

import java.util.HashMap;

import shadow.typecheck.type.ClassInterfaceBaseType;
import shadow.typecheck.type.Type;

public class Package
{	
	public class PackageException extends Exception
	{
		public PackageException()
		{
			super();
		}		

		public PackageException(String message)
		{
			super(message);
		}
	}

	
	
	private final HashMap<String, Package> children = new HashMap<String, Package>();
	private final HashMap<String, ClassInterfaceBaseType> types = new HashMap<String, ClassInterfaceBaseType>();
	private final String name;	
	private final Package parent;
	
	public Package(HashMap<Package, HashMap<String, ClassInterfaceBaseType>> otherTypes)
	{
		this("", null, otherTypes );
	}	

	private Package(String name, Package parent,  HashMap<Package, HashMap<String, ClassInterfaceBaseType>> otherTypes)
	{
		this.name = name;
		this.parent = parent;
		otherTypes.put(this, types);
	}	
	
	/**
	 * Adds a new folder to a package and returns it
	 * If the folder with the given name already exists, it returns that instead
	 * @param folder
	 * @return
	 */
	public Package addPackage( String name, HashMap<Package, HashMap<String, ClassInterfaceBaseType>> typeTable )
	{				
		if( children.containsKey(name) )
			return children.get(name);
		
		Package newPackage = new Package( name, this, typeTable );		
		children.put(name, newPackage);		
		
		return newPackage;
	}
	
	/**
	 * Adds an entire package path to the package tree and returns the PackageType corresponding to the leaf 
	 * @param path
	 * @return
	 */
	public Package addFullyQualifiedPackage( String path, HashMap<Package, HashMap<String, ClassInterfaceBaseType>> typeTable  )
	{
		if( path.length() > 0 && !path.equals("default") )
		{
			String[] folders = path.split("\\.");
			
			Package parent = this;
			
			for( int i = 0; i < folders.length; i++ )		
				parent = parent.addPackage( folders[i], typeTable );
			
			return parent;
		}
		else
			return this;
	}
	
	public void addType(ClassInterfaceBaseType type ) throws PackageException
	{	
		if(!types.containsKey(type.getTypeName()))
		{
			types.put(type.getTypeName(), type);
			type.setPackage(this);
		}
		else
			throw new PackageException("Package " + this.toString() + " already contains type " + type);
	}
	

	public void addTypes(HashMap<String, ClassInterfaceBaseType> types) throws PackageException {
		for( ClassInterfaceBaseType type : types.values() )
			addType( type );
	}
	
	
	public HashMap<String, Package>  getChildren()
	{
		return children;
	}
	
	public String getFullyQualifiedName()
	{
		if (parent == null || parent.getName().isEmpty())
			return getName();
		
		return parent.getFullyQualifiedName() + '.' + getName();
	}
	
	public String getPath()
	{
		if (parent == null || parent.getName().isEmpty())
			return getName();
		
		return parent.getFullyQualifiedName() + '/' + getName();
	}
	
	public String getMangledName()
	{
		StringBuilder sb = new StringBuilder();
		
		if (parent != null && !parent.getName().isEmpty())
			sb.append(parent.getMangledName());
		sb.append("_P");
		Type.mangle(getName(), sb);
		
		return sb.toString();
	}
	
	public Package getParent()
	{
		return parent;
	}
	
	public String getName()
	{
		return name;
	}
	
	public boolean hasChild(String name)
	{
		if( name.contains("."))
		{
			int period = name.indexOf(".");
			String child = name.substring(0, period);
			if( children.containsKey(child))
				return children.get(child).hasChild(name.substring(period + 1));
			else
				return false;
		}
			
		return children.containsKey(name);
	}
	
	public Package getChild(String name)
	{
		if( name.contains("."))
		{
			int period = name.indexOf(".");
			String child = name.substring(0, period);
			if( children.containsKey(child))
				return children.get(child).getChild(name.substring(period + 1));
			else
				return null;
		}
			
		return children.get(name);
	}
	
	public boolean hasType( String name )
	{
		return types.containsKey(name);
	}
	
	public Type getType( String name )
	{
		return types.get(name);
	}


	@Override
	public int hashCode() {		
		return getFullyQualifiedName().hashCode();
	}


	@Override
	public boolean equals(Object o) {
		if( o instanceof Package )
		{
			Package p = (Package)o;
			return getFullyQualifiedName().equals(p.getFullyQualifiedName());
		}

		return false;
	}
	
	@Override
	public String toString() {
		return getFullyQualifiedName();
	}	
}
