package shadow.typecheck.type;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.naming.OperationNotSupportedException;

import shadow.parser.javacc.ASTAssignmentOperator;
import shadow.parser.javacc.Node;
import shadow.typecheck.Package;


public abstract class Type {
	//types should not change after construction
	protected final String typeName;	/** A string that represents the type */
	private Modifiers modifiers;
	private Type outer; //outer class or interface
	protected Package _package;
	private SequenceType typeParameters = null;	
	private boolean parameterized = false;
	protected Type typeWithoutTypeArguments = this;
	
	private ArrayList<InterfaceType> interfaces = new ArrayList<InterfaceType>();

	private Map<String, Node> fieldTable;
	protected HashMap<String, List<MethodSignature> > methodTable; // TODO: change this to private	
	private Set<Type> referencedTypes = new HashSet<Type>();
	private List<Type> typeParameterDependencies = new ArrayList<Type>();
	
	
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
	
	//used to clear out types between runs of the JUnit tests
	//otherwise, types can become mixed between two different runs of the type checker
	public static void clearTypes()				
	{
		OBJECT = null;
		CLASS = null;
		ARRAY = null;
		ENUM = null;
		EXCEPTION = null;	
		ERROR = null;			
		BOOLEAN = null;
		BYTE = null;
		CODE = null;
		DOUBLE = null;
		FLOAT = null;	
		INT = null;
		LONG = null;
		SHORT = null;
		UBYTE = null;
		UINT = null;
		ULONG = null;
		USHORT = null;
		STRING = null;
		CAN_COMPARE = null;
		CAN_INDEX = null;
		CAN_ITERATE = null;
		NUMBER = null;
	}
	
	public Type(String typeName) {
		this( typeName, new Modifiers() );
	}
	
	public Type(String typeName, Modifiers modifiers) {
		this( typeName, modifiers, null );
	}	

	public Type(String typeName, Modifiers modifiers, Type outer) {
		this( typeName, modifiers, outer, (outer == null ? null : outer._package ) );
	}
	
	public Type(String typeName, Modifiers modifiers, Type outer, Package _package ) {
		this.typeName = typeName;
		this.modifiers = modifiers;
		this.outer = outer;		
		this._package = _package;
		
		fieldTable = new HashMap<String, Node>();
		methodTable = new HashMap<String, List<MethodSignature>>();
		
	}
	
	public String getTypeName() 
	{
		return typeName;
	}
	
	public String getMangledName()
	{
		StringBuilder sb = new StringBuilder("_C");
		String[] parts = getTypeName().split(":");
		for ( String part : parts )
			mangle(sb, part).append("_I");
		return sb.delete(sb.length() - 2, sb.length()).toString();
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
		return getQualifiedName(false);			
	}
	
	public String getQualifiedName(boolean withBounds) 
	{		
		if( isPrimitive() )
			return toString(withBounds);
		else if( _package == null || _package.getQualifiedName().isEmpty())
			return "default@" + toString(withBounds);
		else
			return _package.getQualifiedName() + '@' + toString(withBounds);			
	}
	
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean withBounds) {		
		String className = typeName.substring(typeName.lastIndexOf(':') + 1);		
		StringBuilder builder;
		
		if( getOuter() == null )		
			builder = new StringBuilder(className);
		else
			builder = new StringBuilder(getOuter().toString(withBounds) + ":" + className );
			
		if( isParameterized() )		
			builder.append(getTypeParameters().toString("<",">", withBounds));
		
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
					return type.typeParameters.equals(typeParameters);
				else
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
	
	public boolean hasOuter() {
		return outer != null;
	}
	
	public Type getOuter()
	{
		return outer;
	}
	
	public void setOuter(Type outer)
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
				for (int shift = 12; shift >= 0; shift -= 4)
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
		if( value && typeParameters == null )
			typeParameters = new SequenceType();
		
		if( !value )
			typeParameters = null;			
			
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
	
	
	public void addTypeParameterDependency( Type type )
	{
		typeParameterDependencies.add(type);	
	}
	
	public List<Type> getTypeParameterDependencies()
	{
		return typeParameterDependencies;
	}
	
	public boolean containsField(String fieldName) {
		return fieldTable.containsKey(fieldName);
	}	
	
	public void addField(String fieldName, Node node) {
		fieldTable.put(fieldName, node);
	}
	
	public Node getField(String fieldName) {
		return fieldTable.get(fieldName);
	}
		
	public Map<String, Node> getFields() {
		return fieldTable;
	}	
	
	public boolean containsMethod(String symbol)
	{
		return methodTable.get(symbol) != null;		
	}	
	
	public boolean containsMethod(MethodSignature signature)
	{
		return containsMethod( signature, Modifiers.NO_MODIFIERS );		
	}
	
	
	public boolean containsMethod(MethodSignature signature, Modifiers modifiers ) //must have certain modifiers (usually public)
	{
		List<MethodSignature> list = methodTable.get(signature.getSymbol());
		
		if( list != null )
			for(MethodSignature existing : list )
				if( existing.equals(signature) && (existing.getMethodType().getModifiers().hasModifier(modifiers) )) 
					return true;
		
		return false;
	}	
	
	public boolean containsIndistinguishableMethod(MethodSignature signature) //not identical, but indistinguishable at call time
	{
		List<MethodSignature> list = methodTable.get(signature.getSymbol());
		
		if( list != null )
			for(MethodSignature existing : list )
				if( existing.isIndistinguishable(signature))
					return true;
		
		return false;
	}
	
	public void addMethod(String name, MethodSignature signature) {
		if( methodTable.containsKey(name) )		
			methodTable.get(name).add(signature);
		else
		{
			List<MethodSignature> list = new LinkedList<MethodSignature>();
			list.add(signature);
			methodTable.put(name, list);
		}
	}
	
	public Map<String, List<MethodSignature>> getMethodMap() {
		return methodTable;
	}

	
	public List<MethodSignature> getMethods(String methodName)
	{
		List<MethodSignature> signatures = methodTable.get(methodName);
		if( signatures == null )
			return new ArrayList<MethodSignature>();
		else
			return methodTable.get(methodName);
	}	
	
	/**
	 * Simple way to get a known method.
	 * Example:
	 *     Type.ARRAY.getMethod("index", 1);
	 */
	public MethodSignature getMethod(String methodName, int parameters)
	{
		MethodSignature method = null;
		List<MethodSignature> methods = getMethods(methodName);
		for (MethodSignature candidate : methods)
			if (candidate.getParameterTypes().size() == parameters)
				if (method == null)
					method = candidate;
				else
					throw new IllegalArgumentException("Multiple matching methods found");
		if (method == null)
			throw new IllegalArgumentException("No matching method found");
		return method;
	}
	
	protected void includeMethods( String methodName, List<MethodSignature> list )
	{		
		for( MethodSignature signature : getMethods(methodName) )
			if( !list.contains( signature ) )
				list.add(signature);		
	}
	
	private Map<MethodSignature, Integer> methodIndexCache;
	public int getMethodIndex( MethodSignature method )
	{
		// Lazily load cache
		if ( methodIndexCache == null )
		{
			Map<MethodSignature, Integer> cache =
					new HashMap<MethodSignature, Integer>();
			List<MethodSignature> methods = orderAllMethods();
			for ( int i = 0; i < methods.size(); i++ )
				cache.put(methods.get(i), i);
			methodIndexCache = cache;
		}

		Integer index = methodIndexCache.get(method);
		return index == null ? -1 : index;
	}

	public List<MethodSignature> orderAllMethods()
	{
		List<MethodSignature> methodList = new ArrayList<MethodSignature>();

		recursivelyOrderAllMethods(methodList);

		return methodList;
	}
	
	protected void orderMethods( List<MethodSignature> methodList )
	{
		TreeMap<String, List<MethodSignature>> sortedMethods =
				new TreeMap<String, List<MethodSignature>>(methodTable);

		for ( List<MethodSignature> methods : sortedMethods.values() )
			for ( MethodSignature method : methods )
				if ( method.getModifiers().isPublic() )
		{
			int index;
			for ( index = 0; index < methodList.size(); index++ )
				if ( methodList.get(index).isIndistinguishable(method) )
			{
				methodList.set(index, method);
				break;
			}
			if ( index == methodList.size() )
				methodList.add(method);
		}
	}

	/**
	 * This function is only used for error reporting as it finds an indistinguishable signature.
	 * @param signature
	 * @return
	 */
	public MethodSignature getIndistinguishableMethod(MethodSignature signature)
	{		
		for(MethodSignature ms : methodTable.get(signature.getSymbol()))
		{
			if(ms.isIndistinguishable(signature))
				return ms;			
		}
		
		return null;
	}	
	
	public boolean encloses(Type type) {
		if( equals(this) )
			return true;
		
		Type outer = type.getOuter();
		if( outer == null )
			return false;		
		
		return encloses(outer);
	}
	
	public void addReferencedType(Type type)
	{
		if (!equals(type) && !(type instanceof TypeParameter ) && !referencedTypes.contains(type) && !isDescendentOf(type))
			referencedTypes.add(type);
	}
	public Set<Type> getReferencedTypes()
	{
		return referencedTypes;
	}

	public boolean hasInterface(InterfaceType type)
	{
		return false;
	}
	
	public void addInterface(InterfaceType implementType) {
		interfaces.add(implementType);
	}
	
	public ArrayList<InterfaceType> getInterfaces()
	{
		return interfaces;
	}
	
	public ArrayList<InterfaceType> getAllInterfaces()
	{		
		ArrayList<InterfaceType> list = new ArrayList<InterfaceType>();
						
		for( InterfaceType interfaceType : getInterfaces() )
			list.addAll( interfaceType.getAllInterfaces() );
		
		return list;
	}	
	
	public boolean isDescendentOf(Type type)
	{
		return false;
	}
	
	public boolean isRecursivelyParameterized()
	{
		return isParameterized();
	}
	
	protected void recursivelyOrderAllMethods( List<MethodSignature> methodList )
	{
		throw new UnsupportedOperationException();
	}
		
	public void printMetaFile(PrintWriter out, String linePrefix) {
		throw new UnsupportedOperationException();
	}
	
	public List<MethodSignature> getAllMethods(String methodName)
	{
		throw new UnsupportedOperationException();
	}
	
	public abstract boolean isSubtype(Type other);
	public abstract Type replace(SequenceType values, SequenceType replacements );
	
}
