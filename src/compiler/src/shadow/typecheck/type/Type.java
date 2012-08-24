package shadow.typecheck.type;

import java.util.ArrayList;
import java.util.List;

import shadow.parser.javacc.ShadowParser.ModifierSet;
import shadow.typecheck.Package;


public abstract class Type {
	//types should not change after construction
	protected final String typeName;	/** A string that represents the type */
	private int modifiers;
	private ClassInterfaceBaseType outer; //outer class	
	//private final Kind kind;
	protected Package _package;
	private List<TypeParameter> typeParameters = new ArrayList<TypeParameter>();	
	private boolean parameterized;	
		
	/*
	public static enum Kind {
		ARRAY,
		CLASS,
		ENUM,
		ERROR,
		EXCEPTION,
		INTERFACE,
		METHOD,		
		SEQUENCE,
		TYPE_PARAMETER,
		UNBOUND_METHOD,
		VIEW,
		INSTANTIATED,
		UNKNOWN,
		NULL 
	};
	*/
	
	public static ClassType OBJECT = null; 
	public static ClassType STRING = null; 
	public static ClassType CLASS = null;  // meta class for holding .class variables
	public static ClassType ARRAY = null;  // class representation of all array types
	
	public static final ClassType BOOLEAN = new ClassType( "boolean", OBJECT );
	public static final ClassType BYTE = new ClassType( "byte", OBJECT );
	public static final ClassType CODE = new ClassType( "code", OBJECT );	
	public static final ClassType SHORT = new ClassType( "short", OBJECT );
	public static final ClassType INT = new ClassType( "int", OBJECT );
	public static final ClassType LONG = new ClassType( "long", OBJECT );
	public static final ClassType FLOAT = new ClassType( "float", OBJECT );
	public static final ClassType DOUBLE = new ClassType( "double", OBJECT );
	
	public static final ClassType UBYTE = new ClassType( "ubyte", OBJECT );
	public static final ClassType UINT = new ClassType( "uint", OBJECT );
	public static final ClassType ULONG = new ClassType( "ulong", OBJECT );
	public static final ClassType USHORT = new ClassType( "ushort", OBJECT );
	
	public static final EnumType ENUM = new EnumType( "Enum", 0, null, OBJECT );
	public static final ErrorType ERROR = new ErrorType( "Error", 0, null, null );	
	public static final ExceptionType EXCEPTION = new ExceptionType( "Exception", 0, null, null );
	
	public static final ClassType UNKNOWN = new ClassType( "Unknown Type", 0, null); //UNKNOWN type used for placeholder when typechecking goes wrong
	
	public static final ClassType NULL = new ClassType("null", 0, null);
	
	public Type(String typeName) {
		this( typeName, 0 );
	}
	
	public Type(String typeName, int modifiers) {
		this( typeName, modifiers, null );
	}	

	public Type(String typeName, int modifiers, ClassInterfaceBaseType outer) {
		this( typeName, modifiers, outer, (outer == null ? null : outer._package ) );
	}
	
	public Type(String typeName, int modifiers, ClassInterfaceBaseType outer, Package _package ) {
		this.typeName = typeName;
		this.modifiers = modifiers;
		this.outer = outer;		
		this._package = _package;
	}
	
	public String getTypeName() 
	{
		return typeName;
	}

	public String getMangledName()
	{
		return typeName;
	}
	
	public String getFullName() {
		if( _package == null || _package.getFullyQualifiedName().length() == 0 )
			return typeName;
		else
			return _package.getFullyQualifiedName() + '@' + typeName;			
	}
	
	public String getPath()
	{
		if( _package == null || _package.getPath().length() == 0 )
			return typeName;
		else
			return _package.getPath() + '/' + typeName;	
	}
	
	public int getModifiers()
	{
		return modifiers;
	}
	
	public void setModifiers(int modifiers)
	{
		this.modifiers = modifiers;
	}
	
	public void addModifier( int modifier )
	{
		modifiers = ModifierSet.addModifier(modifiers, modifier);		
	}
	
	public String toString()
	{
		return getFullName();		
	}

	public boolean equals(Object o)
	{
		if( o != null && o instanceof Type )
		{
			//null type matches everything, could this ever be a problem?
			return this == Type.NULL || o == Type.NULL || o == this;
		}
		else
			return false;
	}
	
	protected boolean isNumericalSubtype(Type t)
	{
		if( this.equals(BYTE) )
		{
			return t.equals(SHORT) || t.equals(INT) || t.equals(LONG) || t.equals(FLOAT) || t.equals(DOUBLE);
		}
		else if( this.equals(CODE) )
		{
			return t.equals(INT) || t.equals(UINT) || t.equals(LONG) || t.equals(ULONG) || t.equals(FLOAT) || t.equals(DOUBLE);
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
	
	public int getWidth()
	{
		if( this == NULL )
			return -1;
		if( this.equals(BYTE) || this.equals(UBYTE) || this.equals(BOOLEAN) )
			return 1;
		else if( this.equals(SHORT) || this.equals(USHORT) )
			return 2;
		else if( this.equals(INT) || this.equals(UINT) || this.equals(CODE) || this.equals(FLOAT) )
			return 4;
		else if( this.equals(LONG) || this.equals(ULONG) || this.equals(DOUBLE) )
			return 8;
		return -1;
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	public boolean isString() {
		return this.equals(Type.STRING);
	}
	
	public ClassInterfaceBaseType getOuter()
	{
		return outer;
	}
	
	public void setOuter(ClassInterfaceBaseType outer)
	{
		this.outer = outer;
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
	final public boolean isNumerical()
	{
		return isPrimitive() && !this.equals(BOOLEAN); //includes CODE, is that right?
	}
	
	//for cases where integers are required (bitwise operations, array bounds, switch statements, etc.)
	final public boolean isIntegral()
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
	
	
	final public boolean isBuiltIn()
	{
		return
		isPrimitive() ||
		this.equals(CLASS) ||
		this.equals(OBJECT) ||		
		this.equals(STRING) ||		
		this.equals(ENUM) ||
		this.equals(ERROR) ||
		this.equals(EXCEPTION);
	}
	
	final public boolean isPrimitive()
	{
		return
		this.equals(BOOLEAN) ||
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

	final public boolean isSigned()
	{
		return
		this.equals(BOOLEAN) ||
		this.equals(BYTE) ||
		this.equals(CODE) ||
		this.equals(SHORT) ||
		this.equals(INT) ||
		this.equals(LONG);
	}

	//searches for inner classes that follow the name list starting at index i in names
	//overridden by ClassInterfaceBaseType
	protected Type findType(String[] names, int i)
	{
		return null;
	}
	
	public Package getPackage()
	{
		return _package;
	}
	
	public void setPackage(Package p)
	{
		_package = p;
	}
	
	public List<TypeParameter> getTypeParameters()
	{
		return typeParameters;
	}
	
	public void addTypeParameter(TypeParameter parameter)
	{
		typeParameters.add(parameter);
	}	
	
	public static void mangle(String name, StringBuilder sb)
	{
		for (int i = 0; i < name.length(); i++)
		{
			char c = name.charAt(i);
			if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
				sb.append(c);
			else if (c == '_')
				sb.append("_S");
			else
			{
				sb.append("_U");
				for (int shift = 12; shift >= 0; shift -= 8)
					sb.append(Character.forDigit((c >> shift) & 0xf, 16));
			}
		}
	}
	
	public static String unmangle(String name)
	{
		StringBuilder sb = new StringBuilder(name.length());
		
		for (int i = 0; i < name.length(); i++)
		{
			char c = name.charAt(i);
			if (c == '_')
			{
				c = name.charAt(++i);
				if (c == 'S')
					c = '_';
				else if (c == 'U')
				{
					c = (char)Integer.parseInt(name.substring(i, i + 4), 16);
					i += 3;
				} else
					sb.append('_');
			}
			sb.append(c);
		}
		
		return sb.toString();
	}
	public void setParameterized(boolean value)
	{
		parameterized = value;
	}
	
	public boolean isParameterized()
	{
		return parameterized; 
	}

	/**
	 * This method indicates whether a cast is necessary between two types.
	 * <p>
	 * Examples:
	 * <pre>
	 *   STRING.isStrictSubtype(OBJECT) == true
	 *   OBJECT.isStrictSubtype(OBJECT) == false
	 *   OBJECT.isStrictSubtype(NULL) == false
	 *   NULL.isStrictSubtype(OBJECT) == true
	 *   NULL.isStrictSubtype(NULL) == false
	 * </pre>
	 * 
	 * @param other another type
	 * @return {@literal true} if {@code this} can be cast to {@code other} and they are not equal
	 */
	public boolean isStrictSubtype(Type other) {
		if ( this == Type.NULL )
			return other != Type.NULL;
		if ( equals(other) )
			return false;
		return isSubtype(other);
	}

	/**
	 * @param other another type
	 * @return {@literal true} if {@code this} can be cast to {@code other}
	 */
	abstract public boolean isSubtype(Type other);
	abstract public Type replace(List<TypeParameter> values, List<ModifiedType> replacements );
}
