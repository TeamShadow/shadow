package shadow.typecheck.type;

import java.util.HashMap;

public class Type {
	//types should not change after construction
	protected final String typeName;	/** A string the represents the type */
	protected final int modifiers; //do we need modifiers for types or just for references?  private inner classes, perhaps?
	protected final Type enclosing; //outer class
	protected Type parent;  //super type
	protected String packageName; //package

	public static final Type OBJECT = new ClassType( "Object", 0, null ); 
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
		this( typeName, modifiers, null );
	}
	
	public Type(String typeName, int modifiers, Type enclosing ) {
		this( typeName, modifiers, enclosing, OBJECT );
	}	
	
	public Type(String typeName, int modifiers, Type enclosing, Type parent ) {
		this.typeName = typeName;
		this.modifiers = modifiers;
		this.enclosing = enclosing;
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
	
	public void setParent(Type parent) {
		this.parent = parent;
	}

	public boolean equals(Object o) {
		Type t = (Type)o;
		
		// if either type is null or the type names are the same, then we're good
		return  typeName.equals(t.typeName);
	}
	
	public boolean isSubtype(Type t) {				
		//
		// Put in sub-typing logic here
		//
	
		//null is the subtype of everything
		return equals(NULL) || equals( t );
	}
	
	// TODO: Will this work? 
	public int hashCode() {
		return typeName.hashCode();
	}
	
	public boolean isString() {
		return this.equals(STRING);
	}
	
	/**
	 * Given an unsigned type, returns the signed version or the same type otherwise.
	 * @param type The type to convert.
	 * @return The signed version of the type.
	 */
	public static Type makeSigned(Type type) {
		if(type.equals(UBYTE))
			return BYTE;
		
		if(type.equals(USHORT))
			return SHORT;
		
		if(type.equals(UINT))
			return INT;
		
		if(type.equals(ULONG))
			return LONG;
		
		return type;
	}
	
	//for math
	public boolean isNumerical()
	{
		return
		this.equals(BYTE) ||
		this.equals(CODE) ||	// ??? REALLY ???	
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
	
	//for cases where integers are required (bitwise operations, array bounds, switch statements, etc.)
	public boolean isIntegral()
	{
		return
		this.equals(BYTE) ||
		this.equals(CODE) ||	
		this.equals(SHORT) ||
		this.equals(INT) ||
		this.equals(LONG) ||	  
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
