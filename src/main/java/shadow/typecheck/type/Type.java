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

import shadow.parser.javacc.ASTAssignmentOperator;
import shadow.parser.javacc.ASTAssignmentOperator.AssignmentType;
import shadow.parser.javacc.Node;
import shadow.typecheck.BaseChecker;
import shadow.typecheck.TypeCheckException;
import shadow.typecheck.BaseChecker.SubstitutionKind;
import shadow.typecheck.TypeCheckException.Error;
import shadow.typecheck.Package;

/**
 * A representation of a Shadow type.
 */
public abstract class Type implements Comparable<Type>
{
	//types should not change after construction
	private final String typeName;	/** A string that represents the type */
	private Modifiers modifiers;
	private Type outer; //outer class or interface
	private Package _package;
	private SequenceType typeParameters = null;	
	private boolean parameterized = false;
	protected Type typeWithoutTypeArguments = this;
	
	private ArrayList<InterfaceType> interfaces = new ArrayList<InterfaceType>();	
		
	private Map<String, Node> fieldTable = new HashMap<String, Node>();
	private HashMap<String, List<MethodSignature> > methodTable = new HashMap<String, List<MethodSignature>>();	
	private Set<Type> referencedTypes = new HashSet<Type>();
	private List<Type> genericDeclarations = new ArrayList<Type>();
	private List<Type> typeParameterDependencies = new ArrayList<Type>();
	
	
	private TypeArgumentCache instantiatedTypes = new TypeArgumentCache();	
	private LinkedList<Object> importedItems = new LinkedList<Object>();
	
	private static boolean referenceRecursion = false;	
	private String hashName = null;
	
	
	/*
	 * Predefined system types needed for Shadow
	 */
	
	public static ClassType OBJECT = null;
	public static ClassType CLASS = null;  // meta class for holding normal :class variables
	public static ClassType GENERIC_CLASS = null;  // meta class for holding generic :class variables	
	public static ClassType ARRAY_CLASS = null;  // meta class for holding generic array :class variables
	public static ClassType ARRAY = null;  // object representation of all array types
	public static ClassType NULLABLE_ARRAY = null;  // object representation of nullable array types
	public static ClassType METHOD = null;  // object representation for references with function type
	public static ClassType UNBOUND_METHOD = null; //object representation for unbound methods (method name, but no parameters to bind it to a particular implementation)	

	public static ClassType ENUM = null;  //weirdly, the base class for enum is not an EnumType
	public static ExceptionType EXCEPTION = null;
	public static ExceptionType CAST_EXCEPTION = null;
	public static ExceptionType INDEX_OUT_OF_BOUNDS_EXCEPTION = null;
	public static ExceptionType ASSERT_EXCEPTION = null;
	public static ExceptionType UNEXPECTED_NULL_EXCEPTION = null;
			
	public static ClassType BOOLEAN = null;
	public static ClassType BYTE = null;
	public static ClassType CODE = null;
	public static ClassType DOUBLE = null;
	public static ClassType FLOAT = null;	
	public static ClassType INT = null;
	public static ClassType LONG = null;
	public static ClassType SHORT = null;
	
	public static ClassType UBYTE = null;
	public static ClassType UINT = null;
	public static ClassType ULONG = null;
	public static ClassType USHORT = null;
	
	public static ClassType STRING = null;
	public static ClassType ADDRESS_MAP = null; //used for copying
	
	public static final ClassType UNKNOWN = new ClassType( "Unknown Type", new Modifiers(), null); //UNKNOWN type used for placeholder when typechecking goes wrong
	public static final ClassType NULL = new ClassType("null", new Modifiers(Modifiers.IMMUTABLE), null);
	public static final VarType VAR = new VarType(); //VAR type used for placeholder for variables declared with var, until type is known
	
	
	/*
	 * Predefined interfaces needed for Shadow
	 */	

	public static InterfaceType CAN_COMPARE = null;
	public static InterfaceType CAN_EQUAL = null;
	public static InterfaceType CAN_INDEX = null;
	public static InterfaceType NULLABLE_CAN_INDEX = null;
	public static InterfaceType CAN_INDEX_STORE = null;
	public static InterfaceType NULLABLE_CAN_INDEX_STORE = null;
	public static InterfaceType CAN_ITERATE = null;
	public static InterfaceType NULLABLE_CAN_ITERATE = null;
	public static InterfaceType ITERATOR = null;
	public static InterfaceType NULLABLE_ITERATOR = null;
	public static InterfaceType NUMBER = null;
	public static InterfaceType INTEGER = null;
	public static InterfaceType CAN_ADD = null;
	public static InterfaceType CAN_SUBTRACT = null;
	public static InterfaceType CAN_MULTIPLY = null;
	public static InterfaceType CAN_DIVIDE = null;
	public static InterfaceType CAN_MODULUS = null;
	public static InterfaceType CAN_NEGATE = null;	
	
	
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
	
	//used to clear out types between runs of the JUnit tests
	//otherwise, types can become mixed between two different runs of the type checker
	public static void clearTypes()				
	{
		OBJECT = null;
		CAST_EXCEPTION = null;
		INDEX_OUT_OF_BOUNDS_EXCEPTION = null;
		UNEXPECTED_NULL_EXCEPTION = null;
		ASSERT_EXCEPTION = null;
		CLASS = null;		
		ARRAY = null;
		NULLABLE_ARRAY = null;
		ARRAY_CLASS = null;
		METHOD = null;				
		UNBOUND_METHOD = null;
		ENUM = null;
		EXCEPTION = null;		
		GENERIC_CLASS = null;		
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
		ADDRESS_MAP = null;
		CAN_COMPARE = null;
		CAN_EQUAL = null;		
		CAN_INDEX = null;
		NULLABLE_CAN_INDEX = null;
		CAN_INDEX_STORE = null;
		NULLABLE_CAN_INDEX_STORE = null;
		CAN_ITERATE = null;
		NULLABLE_CAN_ITERATE = null;
		ITERATOR = null;
		NULLABLE_ITERATOR = null;
		NUMBER = null;
		INTEGER = null;
		CAN_ADD = null;
		CAN_SUBTRACT = null;
		CAN_MULTIPLY = null;
		CAN_DIVIDE = null;
		CAN_MODULUS = null;
		CAN_NEGATE = null;
	}
	
	/*
	 * Constructors
	 */
	
	public Type(String typeName) {
		this( typeName, new Modifiers() );
	}
	
	public Type(String typeName, Modifiers modifiers) {
		this( typeName, modifiers, null );
	}	

	public Type(String typeName, Modifiers modifiers, Type outer) {
		this( typeName, modifiers, outer, (outer == null ? null : outer._package ) );
	}
	
	public Type(String typeName, Modifiers modifiers, Type outer, Package _package )
	{
		this.typeName = typeName;
		this.modifiers = modifiers;
		this.outer = outer;		
		this._package = _package;
	}
	
	public String getTypeName() 
	{
		return typeName;
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
	
	final public String getMangledNameWithGenerics() {
		if( this instanceof ArrayType || 
			getTypeWithoutTypeArguments().equals(Type.ARRAY) || 
			getTypeWithoutTypeArguments().equals(Type.NULLABLE_ARRAY) ||
			Type.ARRAY.recursivelyContainsInnerClass(getTypeWithoutTypeArguments()) ||
			Type.NULLABLE_ARRAY.recursivelyContainsInnerClass(getTypeWithoutTypeArguments()) )
			return getMangledNameWithGenerics(false);
		else
			return getMangledNameWithGenerics(true);		
	}

			
	protected String getMangledNameWithGenerics(boolean convertArrays) {		
		String className = typeName.substring(typeName.lastIndexOf(':') + 1);		
		StringBuilder builder;
		
		if( getOuter() == null )
		{
			Package _package = getPackage();			
			if( _package == null )
				builder = new StringBuilder("_Pdefault");
			else
				builder = new StringBuilder(_package.getMangledName());
			
			builder.append("_C").append(className);
		}
		else
			builder = new StringBuilder(getOuter().getMangledNameWithGenerics(convertArrays) + "_I" + className );
			
		if( isParameterized() )		
			builder.append(getTypeParameters().getMangledNameWithGenerics(convertArrays));
				
		return builder.toString();
	}	

	

	public String getMangledName()
	{
		String className = typeName.substring(typeName.lastIndexOf(':') + 1);		
		StringBuilder builder;
		
		if( getOuter() == null )
		{
			Package _package = getPackage();			
			if( _package == null )
				builder = new StringBuilder("_Pdefault");
			else
				builder = new StringBuilder(_package.getMangledName());
			
			builder.append("_C").append(className);
		}
		else
			builder = new StringBuilder(getOuter().getMangledName() + "_I" + className );
				
		return builder.toString();	
	}
	
	public final String getHashName()
	{
		/*
		if( _package == null || _package.getQualifiedName().isEmpty())
			return "default@" + toString();
		else
			return _package.getQualifiedName() + '@' + toString();
		*/
		
		//return getMangledNameWithGenerics();
		
		if( hashName == null )
			hashName = getQualifiedName();
		
		return hashName;
	}
	
	protected final void invalidateHashName() {
		hashName = null;
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
	
	public final boolean manglesTheSameAs(Type type)
	{
		return getMangledName().equals(type.getMangledName());		
	}
	
	@Override
	public boolean equals(Object object)
	{
		if( object instanceof Type )
		{
			Type type = (Type)object;
			return equals( type );			
		}
		else
			return false;
	}
	
	//separate from equals() because we need certain different types to be equivalent in hash tables
	public boolean equals(Type type)
	{
		if( type != null )
		{
			if( type == this )
				return true;
			
			// Prevent null pointer problem on getPackage.equals()
			//if (getPackage() == null && type.getPackage() != null)
			//	return false;
						
			//if( getPackage().equals(type.getPackage()) && type.getTypeName().equals(getTypeName()) )
			if( getPackage() == type.getPackage() && type.getTypeName().equals(getTypeName()) )
			{				
				if( isParameterizedIncludingOuterClasses() )
				{
					if( isParameterized() && !type.typeParameters.matches(typeParameters) )
						return false;
					
					if( hasOuter() )
						return getOuter().equals(type.getOuter());
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
		else if( this.equals(CODE) )  //just like uint?
		{
			return t.equals(UINT) || t.equals(ULONG) || t.equals(LONG) || t.equals(FLOAT) || t.equals(DOUBLE);
			//return t.equals(INT) || t.equals(UINT) || t.equals(LONG) || t.equals(ULONG) || t.equals(FLOAT) || t.equals(DOUBLE);
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
			return t.equals(CODE) || t.equals(ULONG) || t.equals(LONG) || t.equals(FLOAT) || t.equals(DOUBLE);
			//return t.equals(ULONG) || t.equals(LONG) || t.equals(FLOAT) || t.equals(DOUBLE);
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
	
	public static int getWidth(ModifiedType type)
	{
		if (type.getModifiers().isNullable())
			return OBJECT.getWidth(); // nullable makes a primitive type a reference
		return type.getType().getWidth();
	}
	public int getWidth()
	{
		if( this == NULL )
			return OBJECT.getWidth();
		if( this.equals(BYTE) || this.equals(UBYTE) || this.equals(BOOLEAN) )
			return 1;
		else if( this.equals(SHORT) || this.equals(USHORT) )
			return 2;
		else if( this.equals(INT) || this.equals(UINT) || this.equals(CODE) || this.equals(FLOAT) )
			return 4;
		else if( this.equals(LONG) || this.equals(ULONG) || this.equals(DOUBLE) )
			return 8;
		
		return 6; //for objects?  So that they're always considered between 4 and 8 bytes and not equal to any primitive?
	}
	public boolean isSimpleReference()
	{
		return getWidth() == OBJECT.getWidth();
	}
	
	@Override
	public final int hashCode() {		
		return getHashName().hashCode();
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
		//this.equals(CODE) ||
		this.equals(SHORT) ||
		this.equals(INT) ||
		this.equals(LONG);
	}

	final public boolean isUnsigned()
	{
		return
		this.equals(UBYTE) ||
		this.equals(USHORT) ||
		this.equals(CODE) || //right?
		this.equals(UINT) ||
		this.equals(ULONG);
	}

	
	public boolean canAccept( Type rightType, ASTAssignmentOperator.AssignmentType assignmentType, List<TypeCheckException> errors ) 
	{
		boolean accepts = false;
		
		//equal and cat are separate because they are not dependent on implementing a specific interface
		if( assignmentType.equals(AssignmentType.EQUAL) )
		{
			accepts = rightType.isSubtype(this);/* ||  //no! don't accept Array<int> inside int[] without explicit cast
			( this instanceof ArrayType && this.isSubtype(rightType) ); */
			
			if( !accepts )
				BaseChecker.addError(errors, Error.INVALID_ASSIGNMENT, "Type " + rightType + " is not a subtype of " + this, rightType, this);
		
			return accepts;
		}
		else if( assignmentType.equals(AssignmentType.CAT) )
		{
			accepts = isString();
			if( !accepts )
				BaseChecker.addError(errors, Error.INVALID_ASSIGNMENT, "Type " + this + " is not type " + Type.STRING, this);
			
			return accepts;
		}
		
		String methodName = assignmentType.getMethod();
		InterfaceType interfaceType = null;
		String operator = assignmentType.getOperator();
		
		switch( assignmentType  )
		{	
		case PLUS: interfaceType = Type.CAN_ADD; break;
		case MINUS: interfaceType = Type.CAN_SUBTRACT; break;
		case STAR: interfaceType = Type.CAN_MULTIPLY; break;
		case SLASH: interfaceType = Type.CAN_DIVIDE; break;
		case MOD: interfaceType = Type.CAN_MODULUS; break;
		case AND:
		case OR:
		case XOR:
		case LEFT_SHIFT:
		case RIGHT_SHIFT:
		case LEFT_ROTATE:
		case RIGHT_ROTATE:
			interfaceType = Type.INTEGER;
			break;
		default:
			return false;
		}
		
		if( hasUninstantiatedInterface(interfaceType) )
		{
			SequenceType argument = new SequenceType(rightType);
			MethodSignature signature = getMatchingMethod(methodName, argument, null, errors);
			if( signature != null )
			{
				Type result = signature.getReturnTypes().getType(0);
				accepts = result.isSubtype(this);
				if( !accepts )
					BaseChecker.addError(errors, Error.INVALID_ASSIGNMENT, "Type " + result + " is not a subtype of " + this, result, this);				
				return accepts;
			}
			else							
				return false;				
		}
		else		
		{
			BaseChecker.addError(errors, Error.INVALID_TYPE, "Cannot apply operator " + operator + " to type " + this + " which does not implement interface " + interfaceType, this);			
			return false;						
		}
	}
	
	public MethodSignature getMatchingMethod(String methodName, SequenceType arguments)
	{
		return getMatchingMethod(methodName, arguments, null );
	}	
	
	
	public MethodSignature getMatchingMethod(String methodName, SequenceType arguments, SequenceType typeArguments )
	{
		List<TypeCheckException> errors = new ArrayList<TypeCheckException>();
		return getMatchingMethod(methodName, arguments, typeArguments, errors );
	}
	
	public MethodSignature getMatchingMethod(String methodName, SequenceType arguments, SequenceType typeArguments, List<TypeCheckException> errors )
	{
		boolean hasTypeArguments = typeArguments != null;
		MethodSignature candidate = null;		
		
		for( MethodSignature signature : getAllMethods(methodName) ) 
		{				
			MethodType methodType = signature.getMethodType();			
			
			if( methodType.isParameterized() )
			{
				if( hasTypeArguments )
				{	
					SequenceType parameters = methodType.getTypeParameters();		
					try
					{
						if( parameters.canAccept(typeArguments, SubstitutionKind.TYPE_PARAMETER))
						{
							methodType = methodType.replace(parameters, typeArguments);
							signature = signature.replace(parameters, typeArguments);
						}
						else
							continue;
					}
					catch(InstantiationException e)
					{}
				}
			}				
			
			//the list of method signatures starts with the closest (current class) and then adds parents and outer classes
			//always stick with the current if you can
			//(only replace if signature is a subtype of candidate but candidate is not a subtype of signature)
			if( signature.canAccept(arguments ) )
			{	
				if( candidate == null || (signature.getParameterTypes().isSubtype(candidate.getParameterTypes()) && !candidate.getParameterTypes().isSubtype(signature.getParameterTypes()) ) )
					candidate = signature;
				else if( !candidate.getParameterTypes().isSubtype(signature.getParameterTypes()) )
				{					
					BaseChecker.addError(errors, Error.INVALID_ARGUMENTS, "Ambiguous call to " + methodName + " with arguments " + arguments, arguments);
					return null;
				}				
			}			
		}			
	
		if( candidate == null )			
			BaseChecker.addError(errors, Error.INVALID_METHOD, "No definition of " + methodName + " with arguments " + arguments + " in this context", arguments);
		
		return candidate;
	}	
	
	public Package getPackage()
	{
		return _package;
	}
	
	public void setPackage(Package p)
	{
		_package = p;
		invalidateHashName();
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
		invalidateHashName();
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
	
	public static String unmangle(String name) //never used?
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
		invalidateHashName();
	}
	
	public boolean isParameterized()
	{
		return parameterized; 
	}
	
	//Must have type parameters AND have them all filled in
	//or have none and have all outer types all filled in
	public boolean isFullyInstantiated()
	{
		if( !isParameterizedIncludingOuterClasses() )
			return false;
		
		if( parameterized )
			for( ModifiedType parameter : typeParameters ) {
				Type parameterType = parameter.getType(); 
				if( parameterType instanceof TypeParameter )
					return false;
				
				if( parameterType.isParameterizedIncludingOuterClasses() && !parameterType.isFullyInstantiated() )
					return false;
			}
		
		if( hasOuter() )			
			return getOuter().isFullyInstantiated();
				
		return true;		
	}
	
	public List<ModifiedType> getTypeParametersIncludingOuterClasses()
	{
		List<ModifiedType> list = new ArrayList<ModifiedType>();
		
		if( hasOuter() )
			list.addAll( outer.getTypeParametersIncludingOuterClasses() );
		
		if( isParameterized() )
			list.addAll( getTypeParameters() );
		
		return list;
	}
	
	public boolean isParameterizedIncludingOuterClasses()
	{
		if( isParameterized() )
			return true;
		
		if( hasOuter() )
			return getOuter().isParameterizedIncludingOuterClasses();
		
		return false;		
	}
	
	public boolean isRecursivelyParameterized()
	{
		return isParameterized();	
	}
	
	public boolean isUninstantiated()
	{
		return equals(getTypeWithoutTypeArguments());
		
		/*
		if( !isParameterizedIncludingOuterClasses() )
			return true;
		
		if( parameterized )
			for( ModifiedType parameter : typeParameters )		
				if( !(parameter.getType() instanceof TypeParameter) )
					return false;		

		if( hasOuter() )			
			return getOuter().isUninstantiated();
		
		return true;
		*/
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
		signature.getMethodType().setOuter(this);		
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
			return signatures;
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
		return recursivelyOrderAllMethods(new ArrayList<MethodSignature>());
	}

	public List<MethodSignature> orderMethods()
	{
		return recursivelyOrderMethods(new ArrayList<MethodSignature>());
	}

	protected List<MethodSignature> orderMethods( List<MethodSignature> methodList, boolean add )
	{
		int parentSize = methodList.size();
		List<MethodSignature> result = add ? methodList : new ArrayList<MethodSignature>();
		List<MethodSignature> original = new ArrayList<MethodSignature>(methodList);
		for ( List<MethodSignature> methods : new TreeMap<String, List<MethodSignature>>(getMethodMap()).values() )
			for ( MethodSignature method : methods )
				if ( !method.getModifiers().isPrivate() )
		{
			SequenceType parameters = method.getParameterTypes();
			SequenceType returns = method.getReturnTypes();
			boolean replaced = false;
			MethodSignature wrapper = method;
			for ( int i = 0; i < parentSize; i++ )
			{
				MethodSignature originalMethod = original.get(i);
				SequenceType originalParameters = originalMethod.getParameterTypes(), 
								rawParameters = originalMethod.getMethodType().getTypeWithoutTypeArguments().getParameterTypes();
				
				if ( (!method.isCreate() || originalMethod.getOuter() instanceof InterfaceType) &&
						method.getSymbol().equals(originalMethod.getSymbol()) &&
						parameters.size() == originalParameters.size() )
				{
					boolean replace = true, wrapped = false;
					if (!method.isCreate() && method.getOuter().isPrimitive())
						wrapped = true;
					for ( int j = 0; replace && j < parameters.size(); j++ )
					{
						ModifiedType parameter = parameters.get(j),								
								originalParameter = originalParameters.get(j),
								rawParameter = rawParameters.get(j);
						
						//can be broader than original types						
						if ( !originalParameter.getType().isSubtype(parameter.getType()) )
							replace = false;
						else if ( getWidth(parameter) != getWidth(rawParameter) )
							wrapped = true;
					}
										
					//adding wrapping for returns as well
					SequenceType originalReturns = originalMethod.getReturnTypes(),
						rawReturns = originalMethod.getMethodType().getTypeWithoutTypeArguments().getReturnTypes();
					for ( int j = 0; replace && j < returns.size(); j++ )
					{
						ModifiedType returnValue = returns.get(j),
								originalReturn = originalReturns.get(j),
								rawReturn = rawReturns.get(j);
						//can be narrower than original types
						if ( !returnValue.getType().isSubtype(originalReturn.getType()) )
						//if ( !parentReturn.getType().isSubtype(returnValue.getType()) )
							replace = false;
						else if ( getWidth(returnValue) != getWidth(rawReturn) )
							wrapped = true;
					}
					
					
					if ( replace )
					{
						//we've found a replacement method, but it has to be the tightest replacement possible
						MethodSignature currentMethod = methodList.get(i);
						if( currentMethod == originalMethod || currentMethod.getMethodType().isSubtype(method.getMethodType()) )
						{						
							replaced = true;
							if ( wrapped && wrapper == method )
								wrapper = originalMethod.wrap(method);
							methodList.set(i, wrapper);
						}
					}
				}
			}
			if ( wrapper != method )
			{
				if ( !add )
					result.add(wrapper);
				result.add(method);
			}
			else if ( !add || !replaced )
				if ( !method.isCreate() || method.getOuter() instanceof InterfaceType )
					result.add(method);
		}
		return result;
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
	
	public boolean encloses(Type type)
	{
		if( equals(type) )
			return true;
		
		Type outer = type.getOuter();
		if( outer == null )
			return false;		
		
		return encloses(outer);
	}

	//only used by TypeUpdater for meta files
	public void addReferencedTypeDirectly(Type type ) {
		referencedTypes.add( type );
	}	
	
	
	public void addReferencedType(Type type)
	{		
		if( type != null && !(type instanceof UninstantiatedType) && !(type instanceof TypeParameter) )
		{			
			if( type instanceof ArrayType )
			{				
				ArrayType arrayType = (ArrayType) type;
				Type baseType = arrayType.getBaseType();
				if( !equals(baseType) && baseType instanceof ArrayType && !((ArrayType)baseType).containsUnboundTypeParameters() )
					referencedTypes.add(baseType); //add in second-level and lower arrays because of Array<T> generic conversion issues
								
				addReferencedType(arrayType.convertToGeneric());
				//covers Type.ARRAY and all recursive base types
				//automatically does the right thing for NullableArray
			}
			else if( type instanceof MethodType )
			{
				MethodType methodType = (MethodType) type;
				for( ModifiedType parameter : methodType.getParameterTypes() )
					addReferencedType( parameter.getType() );
				
				for( ModifiedType _return : methodType.getReturnTypes() )
					addReferencedType( _return.getType() );	
				
				if( type.isParameterized() && type.isFullyInstantiated() )
					for( ModifiedType typeParameter : type.getTypeParameters() )						
						addReferencedType( typeParameter.getType() );
			}
			else if (!equals(type) &&  !(type instanceof UnboundMethodType) /*&& !isDescendentOf(type)*/)
			{		
				if( type.isParameterized() )
				{
					//if( type.isFullyInstantiated() )
					//{				
						referencedTypes.add(type);
						referencedTypes.add(type.typeWithoutTypeArguments);
						
						if( !referenceRecursion )
						{
							referenceRecursion = true; //prevents rabbit hole recursion on type parameters								
							for( ModifiedType typeParameter : type.getTypeParameters() )						
								addReferencedType( typeParameter.getType() );
							
							referenceRecursion = false;
						}						
					//}
					
					
					
				}
				else
				{
					referencedTypes.add(type);					
				}
			}
			
			//add inner types, since their instantiations must be recorded
			if( type instanceof ClassType )
			{
				ClassType classType = (ClassType) type;
				for( ClassType inner : classType.getInnerClasses().values() )
					addReferencedType( inner );				
			}
			
			//add reference to outer types					
			Type outer = getOuter();
			while( outer != null )
			{
				outer.addReferencedType(type);
				outer = outer.getOuter();
			}
			
			ArrayList<InterfaceType> interfaces = type.getInterfaces();
			
			//add interfaces
			for( InterfaceType interfaceType : interfaces )
				addReferencedType(interfaceType);
			
			/* 
			//add extend types (recursively) needed?
			if( type instanceof ClassType )
				addReferencedType( ((ClassType)type).getExtendType() );
			*/
		}
	}

	public Set<Type> getReferencedTypes()
	{
		return referencedTypes;
	}

	public boolean hasInterface(InterfaceType type)
	{
		return false;
	}
	
	public boolean hasUninstantiatedInterface(InterfaceType type)
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
	
	public void setInterfaces(ArrayList<InterfaceType> values)
	{
		interfaces = values;
	}
	
	//must return an ArrayList to preserve order
	//it is essentially that generic classes list their interfaces in the same order as each other
	//otherwise the corresponding blocks of methods won't match
	//the set is used to prevent duplicates
	public ArrayList<InterfaceType> getAllInterfaces()
	{		
		HashSet<InterfaceType> set = new HashSet<InterfaceType>();
		ArrayList<InterfaceType> list = new ArrayList<InterfaceType>();
		
		for( InterfaceType interfaceType : getInterfaces() ) {
			for( InterfaceType type : interfaceType.getAllInterfaces() ) {
				if( set.add(type) )
					list.add(type);
			}
		}
		
		return list;
	}	
	
	public boolean isDescendentOf(Type type)
	{
		return false;
	}
	
	protected List<MethodSignature> recursivelyOrderMethods( List<MethodSignature> methodList )
	{
		throw new UnsupportedOperationException();
	}
	protected List<MethodSignature> recursivelyOrderAllMethods( List<MethodSignature> methodList )
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
	public abstract Type replace(SequenceType values, SequenceType replacements ) throws InstantiationException;
	public abstract Type partiallyReplace(SequenceType values, SequenceType replacements );
	public abstract void updateFieldsAndMethods() throws InstantiationException;	
	
	public void addImportedItems( List<Object> items )
	{
		importedItems.addAll(items);		
	}
	
	public List<Object> getImportedItems()
	{
		return importedItems;		
	}
	
	@Override
	public final int compareTo(Type other)
	{
		return getHashName().compareTo(other.getHashName());		
	}
	
	protected final void printImports(PrintWriter out, String linePrefix )
	{		
		if( getOuter() == null )
		{
			HashSet<String> imports = new HashSet<String>();
			
			//imported items come from import statements and fully qualified classes
			for( Object importItem : getImportedItems() )
			{
				if( importItem instanceof Type )
				{
					Type importType = (Type)importItem;
					if( !importType.hasOuter() && getReferencedTypes().contains(importType) && !importType.isPrimitive())
						imports.add(importType.getImportName());
						
				}
				else if( importItem instanceof Package )
				{
					Package importPackage = (Package)importItem;
					for( Type referencedType : getReferencedTypes() )
						if( !referencedType.hasOuter() && !(referencedType instanceof ArrayType) &&  referencedType.getPackage().equals( importPackage ) && !referencedType.isPrimitive() )
							imports.add(referencedType.getImportName());					
				}
			}
			
			//also classes from the same package
			for( Type packageType : getPackage().getTypes() ) {
				if( packageType != this && !packageType.hasOuter() && getReferencedTypes().contains(packageType) && !packageType.isPrimitive())
					imports.add(packageType.getImportName());
			}			
			
			for( String importType : imports )			
				out.println(linePrefix + "import " + importType + ";");
		}
	}	
	
	protected final void printGenerics(PrintWriter out, String indent ) {
		out.println(indent + "// Generics");
		
		//fix referenced types so that they include everything
		
		for( Type type : getReferencedTypes() ) {		
			if( type.isParameterizedIncludingOuterClasses() ) {		
				if( type.isFullyInstantiated() )						
					out.println(indent + "import " + type.getQualifiedName() + ";");
			}			
			else if( type instanceof ArrayType )
				out.println(indent + "import " + type.getQualifiedName() + ";");
		}
	}
	
	public void clearInstantiatedTypes() {
		if( instantiatedTypes.children != null ) {
			instantiatedTypes.children.clear();
			instantiatedTypes.children = null;
		}
		instantiatedTypes.argument = null;
		instantiatedTypes.instantiatedType = null;
	}
	
	public void addGenericDeclaration(Type type) {
		genericDeclarations.add(type);
	}
	
	public List<Type> getGenericDeclarations() {
		return genericDeclarations;
	}
}
