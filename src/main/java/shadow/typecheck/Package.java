package shadow.typecheck;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;

import shadow.doctool.Documentation;
import shadow.doctool.DocumentationBuilder;
import shadow.doctool.DocumentationException;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.Type;

/** 
 * A representation of a Shadow package, with awareness of both its parent and
 * child packages 
 */
public class Package implements Comparable<Package>
{	
	@SuppressWarnings("serial")
	public static class PackageException extends Exception
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
	private final String name;	
	private final Package parent;
	private final HashMap<String, Type> types = new HashMap<String, Type>();
	private Documentation documentation;
	
	public Package(HashMap<Package, HashMap<String, Type>> otherTypes)
	{
		this("", null, otherTypes );
	}	

	private Package(String name, Package parent,  HashMap<Package, HashMap<String, Type>> otherTypes)
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
	public Package addPackage( String name, HashMap<Package, HashMap<String, Type>> typeTable )
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
	public Package addQualifiedPackage( String path, HashMap<Package, HashMap<String, Type>> typeTable  )
	{
		if( path.length() > 0 && !path.equals("default") )
		{
			String[] folders = path.split(":");
			
			Package parent = this;
			
			for( int i = 0; i < folders.length; i++ )		
				parent = parent.addPackage( folders[i], typeTable );
			
			return parent;
		}
		else
			return this;
	}
	
	public void addType(Type type ) throws PackageException {	
		if(!types.containsKey(type.toString(Type.NO_OPTIONS))) { //no package name or type parameters		
			types.put(type.toString(Type.NO_OPTIONS), type);
			type.setPackage(this);
		}
		else
			throw new PackageException("Package " + this.toString() + " already contains type " + type);
	}
	

	public void addTypes(HashMap<String, Type> types) throws PackageException {
		for( Type type : types.values() )
			addType( type );
	}
	
	
	public HashMap<String, Package>  getChildren()
	{
		return children;
	}
	
	public String getQualifiedName()
	{
		if (parent == null || parent.getName().isEmpty())
			return getName();
		
		return parent.getQualifiedName() + ':' + getName();
	}
	
	public String getPath()
	{
		if (parent == null || parent.getName().isEmpty())
			return getName();
		
		return parent.getQualifiedName() + File.separator + getName();
	}
	
	public String getMangledName()
	{
		StringBuilder sb = new StringBuilder();
		
		if (parent != null && !parent.getName().isEmpty())
			sb.append(parent.getMangledName());
		return Type.mangle(sb.append("_P"), getName()).toString();
	}
	
	public Package getParent()
	{
		return parent;
	}
	
	/** The number of parents this package has */
	public int getDepth()
	{
		int depth = 0;
		Package current = this.parent;
		while (current != null) {
			depth++;
			current = current.getParent();
		}
		
		return depth;
	}
	
	public String getName()
	{
		return name;
	}
	
	public boolean hasChild(String name)
	{
		return (getChild(name) != null);
	}
	
	public Package getChild(String name)
	{
		if( name.contains(":"))
		{
			int separator = name.indexOf(":");
			String child = name.substring(0, separator);
			if( children.containsKey(child))
				return children.get(child).getChild(name.substring(separator + 1));
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
	
	public Collection<Type> getTypes() {
		return types.values();
	}

	public void setDocumentationBuilder(DocumentationBuilder builder)
			throws ShadowException, DocumentationException
	{
		this.documentation = builder.process();
	}
	
	public void setDocumentation(Documentation documentation)
	{
		this.documentation = documentation;
	}
	
	public Documentation getDocumentation()
	{
		return documentation;
	}
	
	public boolean hasDocumentation()
	{
		return (documentation != null);
	}

	@Override
	public int hashCode() {		
		return getQualifiedName().hashCode();
	}


	@Override
	public boolean equals(Object o) {
		if( o instanceof Package )
		{
			Package p = (Package)o;
			return getQualifiedName().equals(p.getQualifiedName());
		}

		return false;
	}


	@Override
	/** Alphabetically compares qualified names */
	public int compareTo(Package o) 
	{
		return this.getQualifiedName().compareTo(o.getQualifiedName());
	}	
	
	@Override
	public String toString() {
		if( parent == null )
			return "default";
		else		
			return getQualifiedName();
	}
}
