package shadow.typecheck;

import java.util.HashMap;

import shadow.typecheck.type.Type;
import shadow.typecheck.type.Type.Kind;

public class Package
{	
	private HashMap<String, Package> children = new HashMap<String, Package>();
	private HashMap<String, Type> types = new HashMap<String, Type>();
	private String folderName;
	private String fullName;
	private Package parent;
	
	//public static final Package DEFAULT_PACKAGE = new Package(); 

	public Package()
	{
		this("", "", null);
	}	

	private Package(String fullName, String folderName,  Package parent )
	{
		this.fullName = fullName;	
		this.folderName = folderName;
		this.parent = parent;
	}	
	
	/**
	 * Adds a new folder to a package and returns it
	 * If the folder with the given name already exists, it returns that instead
	 * @param folder
	 * @return
	 */
	public Package addPackage( String folder )
	{				
		if( children.containsKey(folder) )
			return children.get(folder);
		
		Package newPackage = new Package( fullName + "." + folder, folder, this );		
		children.put(folder, newPackage);		
		
		return newPackage;
	}
	
	/**
	 * Adds an entire package path to the package tree and returns the PackageType corresponding to the leaf 
	 * @param path
	 * @return
	 */
	public Package addPackagePath( String path  )
	{
		String[] folders = path.split("\\.");
		
		Package parent = this;
		
		for( int i = 0; i < folders.length; i++ )		
			parent = parent.addPackage( folders[i] );
		
		return parent;		
	}
	
	public boolean addType(Type type )
	{		
		types.put(type.getTypeName(), type);
		
		String[] path = type.getTypeName().split("\\.");
		
		Package parent = this;
		
		for( int i = 0; i < path.length - 1 && parent != null; i++ ) //last thing in path should be type name		
			parent = parent.getChild( path[i] );
		
		if( parent == null ) //couldn't find package
			return false;
		
		if( parent.getChild(path[path.length - 1]) != null ) //existing package already has type name
			return false;
					
		parent.types.put(path[path.length - 1], type);
		return true;
	}
	
	
	public HashMap<String, Package>  getChildren()
	{
		return children;
	}
	
	public String getFullName()
	{
		return fullName;
	}
	
	public Package getParent()
	{
		return parent;
	}
	
	public String getFolderName()
	{
		return folderName;
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

	/*
	public Type findType(String qualifiedName)
	{
		String[] names = qualifiedName.split("\\.");
		
		Type type = findType( names, 0 );
		
		
		if( type != null )
			return type;
		
		//if path is not fully qualified, we may have to skip past all package prefixes until we can start matching class names
		return findTypeSkippingPackages( names );
	}
	
	private Type findTypeSkippingPackages( String[] names )
	{
		Type type;
		
		//check types in package
		for( String className : types.keySet()  )
		{
			if( className.equals(names[0]) )
			{
				if( 0 == names.length - 1 )
					return types.get(className);
				
				type = types.get(className).findType(names, 1);
				if( type != null )
					return type;
			}			
		}
		
		for( Package _package : children.values() )
		{	
			type = _package.findTypeSkippingPackages(names);
			if( type != null )
					return type;					
		}
		
		return null;
	}
	
	protected Type findType(String[] names, int index )
	{
		Type type = null;		
		
		//check types in package
		for( String className : types.keySet()  )
		{
			if( className.equals(names[index]) )
			{
				if( index == names.length - 1 )
					return types.get(className);
				
				type = types.get(className).findType(names, index + 1);
				if( type != null )
					return type;
			}			
		}
		
		//check subpackages in package
		for( String packageName : children.keySet()  )
		{
			if( packageName.equals(names[index]) )
			{
				if( index == names.length - 1 )
					return children.get(packageName);
				
				type = children.get(packageName).findType(names, index + 1);
				if( type != null )
					return type;
			}			
		}
		
		return null;
	}
	
	*/
}
