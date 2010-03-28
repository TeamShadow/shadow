package shadow.typecheck.type;

import shadow.parser.javacc.Node;


public class Type {
	//types should not change after construction
	protected final String typeName;	/** A string the represents the type */
	private final int modifiers; //do we need modifiers for types or just for references?  private inner classes, perhaps?
	private final Type outer; //outer class	
	private Kind kind;
	private int arrayDimension;
	private Node astNode;	/** This is to link back to the AST, usually not used */
	
	public static enum Kind { ARRAY, CLASS, ENUM, ERROR, EXCEPTION, INTERFACE, METHOD, VIEW};
	
	public static final ClassType OBJECT = new ClassType( "Object", 0, null ); 
	public static final ClassType BOOLEAN = new ClassType( "boolean" );
	public static final ClassType BYTE = new ClassType( "byte" );
	public static final ClassType CODE = new ClassType( "code" );	
	public static final ClassType SHORT = new ClassType( "short" );
	public static final ClassType INT = new ClassType( "int" );
	public static final ClassType LONG = new ClassType( "long" );	  
	public static final ClassType FLOAT = new ClassType( "float" );
	public static final ClassType DOUBLE = new ClassType( "double" );
	public static final ClassType STRING = new ClassType( "String" );
	public static final ClassType UBYTE = new ClassType( "ubyte" );
	public static final ClassType UINT = new ClassType( "uint" );
	public static final ClassType ULONG = new ClassType( "ulong" );
	public static final ClassType USHORT = new ClassType( "ushort" );
	
	public static final EnumType ENUM = new EnumType( "Enum", 0, null, OBJECT );
	public static final ErrorType ERROR = new ErrorType( "Error", 0, null, null );	
	public static final ExceptionType EXCEPTION = new ExceptionType( "Exception", 0, null, null );
	
	public static final Type NULL = new Type( "null" );
	
	public Type(String typeName) {
		this( typeName, 0 );
	}
	
	public Type(String typeName, int modifiers) {
		this( typeName, modifiers, null );
	}
	
	public Type(String typeName, int modifiers, Type outer ) {
		this( typeName, modifiers, outer, Kind.CLASS );
	}	
	
	public Type(String typeName, int modifiers, Type outer, Kind kind ) {
		this.typeName = typeName;
		this.modifiers = modifiers;
		this.outer = outer;
		this.kind = kind;
		this.arrayDimension = 0;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public int getModifiers() {
		return modifiers;
	}
	
	public void setASTNode(Node node) {
		this.astNode = node;
	}
	
	public Node getASTNode() {
		return astNode;
	}
	
	public String toString() {
		if(arrayDimension == 0)
			return typeName;
		
		StringBuilder sb = new StringBuilder(typeName + "[");
		
		for(int i=1; i < arrayDimension; ++i)
			sb.append(",");
		
		sb.append("]");
		
		return sb.toString();
	}
	
	public int getArrayDimension() {
		return arrayDimension;
	}
	
	public void setArrayDimension(int dimension) {
		arrayDimension = dimension;
	}
	
	public boolean isArray() {
		return arrayDimension == 0;
	}

	public boolean equals(Object o) {
		if( o != null && o instanceof Type )
		{
			Type t = (Type)o;
			//null matches everything... could this ever be a problem?
			return  typeName.equals(NULL.typeName) || typeName.equals(t.typeName);
		}
		else
			return false;
	}
	
	public boolean isSubtype(Type t) {				

		// This subtyping code does not handle generics	
		
		if( equals(t) )
			return true;
		
		switch( kind  )
		{
		case ARRAY:			
			if( t.equals(OBJECT) )
				return true;
			if( t.getKind() == Kind.ARRAY )
			{
				ArrayType type = (ArrayType)this;
				ArrayType other = (ArrayType)this;
				if( type.getArrayDimension() == other.getArrayDimension() )
					return type.getBaseType().isSubtype(other.getBaseType());
				else
					return false;
			}
			else
				return false;			
		case CLASS:
			if( t.isNumerical() && this.isNumerical() )
				return isNumericalSubtype(t);
			else if( t.getKind() == Kind.CLASS )			
				return ((ClassType)this).isDescendentOf(t);
			else if( t.getKind() == Kind.INTERFACE )
				return ((ClassType)this).isImplementerOf(t);
			else
				return false;
		case ENUM:
			if( t.equals(OBJECT) || t.equals(ENUM) )
				return true;			
			else if( t.getKind() == Kind.INTERFACE )
				return ((EnumType)this).isImplementerOf(t);
			else
				return false;
		case ERROR:
			if( t.getKind() == Kind.ERROR )			
				return ((ErrorType)this).isDescendentOf(t);
			else
				return false;			
		case EXCEPTION:
			if( t.getKind() == Kind.EXCEPTION )			
				return ((ExceptionType)this).isDescendentOf(t);
			else
				return false;
		case INTERFACE:
			if( t.getKind() == Kind.INTERFACE )			
				return ((ExceptionType)this).isDescendentOf(t);
			else
				return false;			
		case METHOD:
		case VIEW:
		default:
			return false;		
		}
	}
	
	protected boolean isNumericalSubtype(Type t)
	{
		if( this.equals(BYTE) )
		{
			return t.equals(SHORT) || t.equals(INT) || t.equals(LONG) || t.equals(FLOAT) || t.equals(DOUBLE);
		}
		else if( this.equals(CODE) )
		{
			return t.equals(UINT) || t.equals(LONG) || t.equals(ULONG) || t.equals(FLOAT) || t.equals(DOUBLE);
		}
		else if( this.equals(SHORT) )
		{
			return t.equals(INT) || t.equals(LONG) || t.equals(FLOAT) || t.equals(DOUBLE);
		}
		else if( this.equals(INT) )
		{
			return t.equals(LONG) || t.equals(FLOAT) || t.equals(DOUBLE);
		}
		else if( this.equals(LONG))
		{
			return t.equals(FLOAT) || t.equals(DOUBLE);
		}
		else if( this.equals(FLOAT))
		{
			return t.equals(DOUBLE);
		}
		else if( this.equals(UBYTE))
		{
			return t.equals(USHORT) || t.equals(UINT) || t.equals(ULONG)  || t.equals(SHORT) || t.equals(INT) || t.equals(LONG) || t.equals(FLOAT) || t.equals(DOUBLE);
		}		
		else if( this.equals(UINT))
		{
			return t.equals(ULONG) || t.equals(LONG) || t.equals(FLOAT) || t.equals(DOUBLE);
		}
		else if( this.equals(ULONG) )
		{
			return t.equals(FLOAT) || t.equals(DOUBLE);
		}
		else if( this.equals(USHORT) )
		{
			return t.equals(UINT) || t.equals(ULONG)  || t.equals(INT) || t.equals(LONG) || t.equals(FLOAT) || t.equals(DOUBLE);
		}
		else		
			return false;
	}
		
	public int hashCode() {
		return typeName.hashCode();
	}
	
	public boolean isString() {
		return this.equals(Type.STRING);
	}
	
	public Type getOuter()
	{
		return outer;
	}
	
	public void setKind(Kind kind ) {
		  this.kind = kind;
	  }
	  
	  public Kind getKind() {
		  return this.kind;
	  }
	
	
	/**
	 * Given an unsigned type, returns the signed version or the same type otherwise.
	 * @param type The type to convert.
	 * @return The signed version of the type.
	 */
	public static ClassType makeSigned(ClassType type) {
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
		this.equals(USHORT) ||
		
		this.equals(ENUM) ||
		this.equals(ERROR) ||
		this.equals(EXCEPTION);
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
