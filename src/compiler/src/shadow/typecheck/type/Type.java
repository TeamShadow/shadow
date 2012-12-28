package shadow.typecheck.type;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import shadow.parser.javacc.ASTAssignmentOperator;
import shadow.typecheck.Package;


public abstract class Type {
	//types should not change after construction
	protected final String typeName;	/** A string that represents the type */
	private Modifiers modifiers;
	private ClassInterfaceBaseType outer; //outer class	
	//private final Kind kind;
	protected Package _package;
	private SequenceType typeParameters = null;	
	private boolean parameterized = false;
	protected Type typeWithoutTypeArguments = this;
	//private boolean instantiated = false;
	//private SequenceType typeArguments;
	
	private TypeArgumentCache instantiatedTypes = new TypeArgumentCache();
	
	private static class TypeArgumentCache
	{
		public ModifiedType argument;
		public Type instantiatedType;
		public List<TypeArgumentCache> children;
	}
	
	public Type getInstantiation( SequenceType typeArguments  )
	{
		return getInstantiation(instantiatedTypes, typeArguments, 0 );
	}
	
	public Type getTypeWithoutTypeArguments()
	{		
		return typeWithoutTypeArguments;
	}
	
	private static Type getInstantiation(TypeArgumentCache types, SequenceType typeArguments, int index  )
	{
		if( index == typeArguments.size() )
			return types.instantiatedType;
		
		if( types.children == null )
			return null;
		
		ModifiedType argument = typeArguments.get(index);
		for( TypeArgumentCache child : types.children )		
			if( child.argument != null && child.argument.getType().equals(argument.getType()) && child.argument.getModifiers().equals(argument.getModifiers()))
				return getInstantiation( child, typeArguments, index + 1 );

		return null;
	}
	
	public void addInstantiation( SequenceType typeArguments, Type type  )
	{
		addInstantiation(instantiatedTypes, typeArguments, 0, type );
	}
	
	private static void addInstantiation(TypeArgumentCache types, SequenceType typeArguments, int index, Type type  )
	{		
		if( index == typeArguments.size() )		
			types.instantiatedType = type;			
		else
		{		
			if( types.children == null )
				types.children = new ArrayList<TypeArgumentCache>();
			
			ModifiedType argument = typeArguments.get(index);
			for( TypeArgumentCache child : types.children )		
				if( child.argument != null && child.argument.getType().equals(argument.getType()) && child.argument.getModifiers().equals(argument.getModifiers()))
				{
					addInstantiation( child, typeArguments, index + 1, type );
					return;
				}
			
			TypeArgumentCache newChild = new TypeArgumentCache();
			newChild.argument = argument;
			types.children.add(newChild);
			addInstantiation( newChild, typeArguments, index + 1, type );
		}
	}


		
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
		UNKNOWN,
		NULL 
	};
	*/
	
	public static ClassType OBJECT = null;
	public static ClassType CLASS = null;  // meta class for holding .class variables
	public static ClassType ARRAY = null;  // class representation of all array types
	public static ClassType ENUM = null;  //weirdly, the base class for enum is not an enum
	public static ExceptionType EXCEPTION = null;	
	public static ErrorType ERROR = null;
		
	public static ClassType BOOLEAN = null;
	public static ClassType BYTE = null;
	public static ClassType CODE = null;
	public static ClassType DOUBLE = null;
	public static ClassType FLOAT = null;	
	public static ClassType INT = null; //new ClassType( "int", OBJECT );
	public static ClassType LONG = null;
	public static ClassType SHORT = null;
	
	public static ClassType UBYTE = null;
	public static ClassType UINT = null;
	public static ClassType ULONG = null;
	public static ClassType USHORT = null;
	
	public static ClassType STRING = null;
	
	//interfaces needed for language features
	public static InterfaceType CAN_COMPARE = null;
	public static InterfaceType CAN_INDEX = null;
	public static InterfaceType CAN_ITERATE = null;
	public static InterfaceType NUMBER = null;	
	
	public static final ClassType UNKNOWN = new ClassType( "Unknown Type", new Modifiers(), null); //UNKNOWN type used for placeholder when typechecking goes wrong
	public static final ClassType NULL = new ClassType("null", new Modifiers(), null);
	
	public Type(String typeName) {
		this( typeName, new Modifiers() );
	}
	
	public Type(String typeName, Modifiers modifiers) {
		this( typeName, modifiers, null );
	}	

	public Type(String typeName, Modifiers modifiers, ClassInterfaceBaseType outer) {
		this( typeName, modifiers, outer, (outer == null ? null : outer._package ) );
	}
	
	public Type(String typeName, Modifiers modifiers, ClassInterfaceBaseType outer, Package _package ) {
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
		return mangle(new StringBuilder("_C"), getTypeName()).toString();
	}
	
	public String getImportName() //does not include parameters
	{		
		if( isPrimitive() )
			return getTypeName();
		else if( _package == null || _package.getQualifiedName().isEmpty())
			return "default@" + getTypeName();
		else
			return _package.getQualifiedName() + '@' + getTypeName();			
	}
	
	public String getQualifiedName() 
	{		
		if( isPrimitive() )
			return toString();
		else if( _package == null || _package.getQualifiedName().isEmpty())
			return "default@" + toString();
		else
			return _package.getQualifiedName() + '@' + toString();			
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder(typeName);
			
		if( isParameterized() )		
			builder.append(getTypeParameters().toString("<",">"));
		
		return builder.toString();
	}
	
	public String getPath()
	{
		if( _package == null || _package.getPath().isEmpty() )
			return typeName;
		else
			return _package.getPath() + File.separator + typeName;	
	}
	
	public Modifiers getModifiers()
	{
		return modifiers;
	}
	
	public void setModifiers(Modifiers modifiers)
	{
		this.modifiers = modifiers;
	}
	
	public void addModifier( int modifier )
	{
		modifiers.addModifier(modifier);		
	}

	public boolean equals(Object o)
	{
		if( o != null && o instanceof Type )
		{
			if( this == Type.NULL || o == Type.NULL || o == this )
				return true;
			
			Type type = (Type) o;
			
			if( type.getPackage() == getPackage() && type.getTypeName().equals(getTypeName()) )
			{
				
				if( parameterized )
				{
					if( type.typeParameters.size() == typeParameters.size() )
					{
						for( int i = 0; i < typeParameters.size(); i++ )
						{
							if( !type.typeParameters.get(i).equals(typeParameters.get(i)) )
								return false;
						}
					}
					else
						return false;
				}
					
				return true;
			}	
			else
				return false;
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
		return 6;
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
	
	
	final public boolean isFloating()
	{
		return
		this.equals(FLOAT) ||
		this.equals(DOUBLE);
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

	final public boolean isUnsigned()
	{
		return
		this.equals(UBYTE) ||
		this.equals(USHORT) ||
		this.equals(UINT) ||
		this.equals(ULONG);
	}
	
	public boolean acceptsAssignment( Type rightType, ASTAssignmentOperator.AssignmentType assignmentType ) 
	{			
		switch( assignmentType  )
		{
		case EQUAL:
			return rightType.isSubtype(this);
			
		case PLUSASSIGN:			
		case MINUSASSIGN:
		case STARASSIGN:
		case SLASHASSIGN:
		case MODASSIGN:
			return isNumerical() && rightType.isSubtype(this);			

		case ANDASSIGN:
		case ORASSIGN:
		case XORASSIGN:
			return isIntegral() && rightType.isSubtype(this);
			
		case LEFTSHIFTASSIGN:
		case RIGHTSHIFTASSIGN:
		case RIGHTROTATEASSIGN:
		case LEFTROTATEASSIGN:
			return isIntegral() && rightType.isIntegral();

		case CATASSIGN:
			return isString();
		}
		
		return false;		
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
	
	public SequenceType getTypeParameters()
	{
		return typeParameters;
	}
	
	public void addTypeParameter(ModifiedType parameter)
	{
		if( typeParameters == null )
		{
			typeParameters = new SequenceType();
			parameterized = true;
		}
		typeParameters.add(parameter);		
	}	
	
	public static StringBuilder mangle(StringBuilder sb, String name)
	{
		for (int i = 0; i < name.length(); i++)
		{
			char c = name.charAt(i);
			if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
				sb.append(c);
			else if (c == '_')
				sb.append(c).append(c);
			else
			{
				sb.append("_U");
				for (int shift = 12; shift >= 0; shift -= 8)
					sb.append(Character.forDigit((c >> shift) & 0xf, 16));
			}
		}
		return sb;
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
				if (c == 'U')
				{
					c = (char)Integer.parseInt(name.substring(i, i + 4), 16);
					i += 3;
				} else if (c == '_')
					sb.append(c);
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
	abstract public Type replace(SequenceType values, SequenceType replacements );
}
