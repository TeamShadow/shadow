package shadow.typecheck;

public class Type {
	//types should not change after construction
	protected final String typeName;	/** A string the represents the type */
	protected final int modifiers;
	protected final Type parent;
	
	public static final Type OBJECT = new Type( "Object", 0, null ); 
	public static final Type BOOLEAN = new Type( "boolean" );
	public static final Type BYTE = new Type( "byte" );
	public static final Type CODE = new Type( "code" );	
	public static final Type SHORT = new Type( "short" );
	public static final Type INT = new Type( "int" );
	public static final Type LONG = new Type( "long" );	  
	public static final Type FLOAT = new Type( "float" );
	public static final Type DOUBLE = new Type( "double" );
	public static final Type STRING = new Type( "String" );
	public static final Type UBYTE = new Type( "ubyte" );
	public static final Type UINT = new Type( "uint" );
	public static final Type ULONG = new Type( "ulong" );
	public static final Type USHORT = new Type( "ushort" );
	public static final Type NULL = new Type( "null", 0, null );
	
	public Type(String typeName) {
		this( typeName, 0 );
	}
	
	public Type(String typeName, int modifiers) {
		this( typeName, modifiers, OBJECT );
	}
	
	public Type(String typeName, int modifiers, Type parent ) {
		this.typeName = typeName;
		this.modifiers = modifiers;
		this.parent = parent;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public int getModifiers() {
		return modifiers;
	}
	
	public String toString() {
		return typeName;
	}
	
	public Type getParent() {
		return parent;
	}
	
	/**
	 * Need to override equals as we're doing special things
	 * @param ms The method signature to compare to
	 * @return True if the methods can co-exist, False otherwise
	 */
	public boolean equals(Object o) {
		Type t = (Type)o;
		
		// if either type is null or the type names are the same, then we're good
		return typeName.equals(NULL.typeName) || t.typeName.equals(NULL.typeName) || typeName.equals(t.typeName);
	}
	
	
	public boolean isSubtype(Object o) {				
		//
		// Put in sub-typing logic here
		//
		
		return equals( o );
	}
	
	
	
	// TODO: Will this work?
	// Sure... we don't even have to have a hashCode for the type since it is the value, not the key
	public int hashCode() {
		return typeName.hashCode();
	}
	
	public boolean isNumerical()
	{
		return
		this.equals(BYTE) ||
		this.equals(CODE) ||	
		this.equals(SHORT) ||
		this.equals(INT) ||
		this.equals(LONG) ||	  
		this.equals(FLOAT) ||
		this.equals(DOUBLE) ||		
		this.equals(UBYTE) ||
		this.equals(UINT) ||
		this.equals(ULONG) ||
		this.equals(USHORT);
	}
	
	public boolean isBuiltIn()
	{
		return
		this.equals(OBJECT) ||
		this.equals(BOOLEAN) ||
		this.equals(BYTE) ||
		this.equals(CODE) ||	
		this.equals(SHORT) ||
		this.equals(INT) ||
		this.equals(LONG) ||	  
		this.equals(FLOAT) ||
		this.equals(DOUBLE) ||
		this.equals(STRING) ||
		this.equals(UBYTE) ||
		this.equals(UINT) ||
		this.equals(ULONG) ||
		this.equals(USHORT);
	}
	
	public boolean isPrimitive()
	{
		return
		this.equals(BYTE) ||
		this.equals(CODE) ||	
		this.equals(SHORT) ||
		this.equals(INT) ||
		this.equals(LONG) ||	  
		this.equals(FLOAT) ||
		this.equals(DOUBLE) ||
		this.equals(UBYTE) ||
		this.equals(UINT) ||
		this.equals(ULONG) ||
		this.equals(USHORT);
	}
}
