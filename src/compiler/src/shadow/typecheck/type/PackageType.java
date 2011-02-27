package shadow.typecheck.type;

import java.util.HashMap;

public class PackageType extends Type
{	
	private HashMap<String, PackageType> children = new HashMap<String, PackageType>();
	private String folderName;
	private String fullName;
	private PackageType parent;

	public PackageType()
	{
		this("", "", null);
	}	

	private PackageType(String fullName, String folderName,  PackageType parent )
	{
		super(fullName, 0, null, Kind.PACKAGE );		
		this.folderName = folderName;
		this.parent = parent;
	}	
	
	/**
	 * Adds a new folder to a package and returns it
	 * If the folder with the given name already exists, it returns that instead
	 * @param folder
	 * @return
	 */
	public PackageType addPackage( String folder )
	{				
		if( children.containsKey(folder) )
			return children.get(folder);
		
		PackageType newPackage = new PackageType( fullName + "." + folder, folder, this );		
		children.put(folder, newPackage);		
		
		return newPackage;
	}
	
	/**
	 * Adds an entire package path to the package tree and returns the PackageType corresponding to the leaf 
	 * @param path
	 * @return
	 */
	public PackageType addPackagePath( String path  )
	{
		String[] folders = path.split("\\.");
		
		PackageType parent = this;
		
		for( int i = 0; i < folders.length; i++ )		
			parent = parent.addPackage( folders[i] );
		
		return parent;		
	}
	
	
	public HashMap<String, PackageType>  getChildren()
	{
		return children;
	}
	
	public String getFullName()
	{
		return getTypeName();
	}
	
	public PackageType getParent()
	{
		return parent;
	}
	
	public String getFolderName()
	{
		return folderName;
	}

}
