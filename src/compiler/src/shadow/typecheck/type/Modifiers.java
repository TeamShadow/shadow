package shadow.typecheck.type;

import shadow.parser.javacc.Node;
import shadow.parser.javacc.ParseException;

/**
 * Class to hold modifiers.
 */
public final class Modifiers
{
	/* Definitions of the bits in the modifiers field.  */
	/* Unused Java modifiers are commented out until modifiers are finalized */ 
	public static final int PUBLIC         	= 0x0001;
	public static final int PROTECTED      	= 0x0002;
	public static final int PRIVATE       	= 0x0004;
	public static final int ABSTRACT       	= 0x0008;	
     
	public static final int READONLY		= 0x0010;
	public static final int NATIVE			= 0x0020;     
	public static final int WEAK           	= 0x0040;
	public static final int IMMUTABLE      	= 0x0080;
	public static final int NULLABLE      	= 0x0100;
	public static final int GET				= 0x0200;
	public static final int SET				= 0x0400;
	public static final int CONSTANT		= 0x0800;

	public static final int ASSIGNABLE   	= 0x1000;
	public static final int TYPE_NAME   	= 0x2000;
	public static final int FIELD		   	= 0x4000;
	public static final int PROPERTY	   	= 0x8000;
	public static final int TEMPORARY_READONLY	= 0x10000;
	
	public static final Modifiers NO_MODIFIERS = new Modifiers();

	/* Type Modifiers:
  		public		(only for inner classes)
  		private		(only for inner classes)
  		protected 	(only for inner classes)		
		abstract		
		immutable	(abstract, readonly, and immutable are mutually exclusive)
		readonly	(abstract, readonly, and immutable are mutually exclusive)
	 */


	/* Declaration Modifiers:
		public		(only for methods)
  		private		(only for methods)
  		protected 	(only for methods)
		get			(only for fields)
		set			(only for fields)
		constant	(only for fields, public constant)
				
		weak		(reference will not stop GC)
		native      (used to map in C functions)
		nullable	(variables that can be null)
		immutable	(all references to this object are readonly)
		readonly	(no mutable methods can be called on this reference)
	 */

	/* Hidden Modifiers: (used in the compiler internally, but cannot be marked by the user)
		assignable 		(used to mark variables [lvalues])
		type name		(used to distinguish between types and values/variables with that type, e.g. int vs. 5 or String vs. "figs")
		field			(used to mark nodes that are fields, as opposed to local variables, to inform the TAC [methods are NOT marked as fields])
		return readonly	(returned object will be readonly)
		return immutable(returned object will be immutable)
	 */


	private int modifiers = 0;

	public Modifiers(int modifiers)
	{
		this.modifiers = modifiers;
	}
	
	public Modifiers(Modifiers modifiers) {
		this(modifiers.modifiers);
	}

	public Modifiers()
	{
		modifiers = 0;		
	}
	
	public boolean equals(Object o)
	{
		if( o instanceof Modifiers )
		{
			Modifiers other = (Modifiers)o;
			return modifiers == other.modifiers;
		}
		
		return false;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		if( isPublic() )
			sb.append("public ");
		if( isProtected() )
			sb.append("protected ");
		if( isPrivate() )
			sb.append("private ");
		if( isAbstract() )
			sb.append("abstract ");
		if( isReadonly() )
			sb.append("readonly ");
		if( isNative() )
			sb.append("native ");
		if( isWeak() )
			sb.append("weak ");
		if( isImmutable() )
			sb.append("immutable ");
		if( isNullable() )
			sb.append("nullable ");
		if( isGet() )
			sb.append("get ");
		if( isSet() )
			sb.append("set ");
		if( isConstant() )
			sb.append("constant ");
		
		return sb.toString();
	}
	
	public String toDebugString()
	{
		StringBuilder sb = new StringBuilder(toString());
		
		if( modifiers == 0 )
			sb.append("[none] ");		
		if( isAssignable() )
			sb.append("[assignable] ");
		if( isTypeName() )
			sb.append("[type name] ");
		if( isField())
			sb.append("[field] ");
		if( isTemporaryReadonly())
			sb.append("[temporary readonly] ");
		
		return sb.toString();
	}
	

	/** A set of accessors that indicate whether the specified modifier
      is in the set. */

	public boolean isPublic() { return (modifiers & PUBLIC) != 0; }
	public boolean isProtected() { return (modifiers & PROTECTED) != 0; }
	public boolean isPrivate() { return (modifiers & PRIVATE) != 0; }	
	public boolean isAbstract() { return (modifiers & ABSTRACT) != 0; }
	//public boolean isFinal() { return (modifiers & FINAL) != 0; }
	public boolean isReadonly() { return (modifiers & READONLY) != 0; }
	public boolean isNative() { return (modifiers & NATIVE) != 0; }

	public boolean isGet() { return (modifiers & GET) != 0; }
	public boolean isSet() { return (modifiers & SET) != 0; }

	public boolean isConstant() { return (modifiers & CONSTANT) != 0; }

	public boolean isWeak() { return (modifiers & WEAK) != 0; }
	public boolean isImmutable() { return (modifiers & IMMUTABLE) != 0; }
	public boolean isNullable() { return (modifiers & NULLABLE) != 0; }

	public boolean isAssignable() { return (modifiers & ASSIGNABLE) != 0; }
	public boolean isTypeName() { return (modifiers & TYPE_NAME) != 0; }   
	public boolean isField() { return (modifiers & FIELD) != 0; } 
	public boolean isTemporaryReadonly() { return (modifiers & TEMPORARY_READONLY) != 0; }	
		
	public boolean isMutable() {  return !isReadonly() && !isImmutable() && !isTemporaryReadonly() && !isConstant(); }

	/**
	 * Changes the given modifier.
	 */
	public void removeModifier(int mod) { modifiers = modifiers & ~mod; }
	public void addModifier(int mod) { modifiers = modifiers | mod; }
	public boolean hasModifier(int mod ) { return (modifiers & mod) != 0;     }
	public boolean hasModifier(Modifiers modifier) { return hasModifier(modifier.modifiers);     }
	public void checkAndAdd(int mod, Node node ) throws ParseException
	{
		if( hasModifier(mod ) )
			throw new ParseException("Repeated modifiers not allowed", node);

		addModifier(mod );
	}
	
	public void upgradeToTemporaryReadonly()
	{
		//if already immutable or readonly, don't change
		if( !isImmutable() && !isReadonly() )
			addModifier(Modifiers.TEMPORARY_READONLY);
	}

	public void checkModifiers( Modifiers legal, String name, Node node )  throws ParseException
	{	
		if( isPublic() && !legal.isPublic()  )
			throw new ParseException(name + " cannot be marked public", node);
		if( isProtected() && !legal.isProtected( )  )
			throw new ParseException(name + " cannot be marked protected", node);
		if( isPrivate() && !legal.isPrivate()  )
			throw new ParseException(name + " cannot be marked private", node);
		if( isAbstract() && !legal.isAbstract()  )
			throw new ParseException(name + " cannot be marked abstract", node);
		if( isReadonly() && !legal.isReadonly()  )
			throw new ParseException(name + " cannot be marked readonly", node);
		if( isNative() && !legal.isNative()  )
			throw new ParseException(name + " cannot be marked native", node);
		if( isGet() && !legal.isGet()  )
			throw new ParseException(name + " cannot be marked get", node);
		if( isSet() && !legal.isSet()  )
			throw new ParseException(name + " cannot be marked set", node);
		if( isConstant() && !legal.isConstant()  )
			throw new ParseException(name + " cannot be marked constant", node);
		if( isWeak() && !legal.isWeak()  )
			throw new ParseException(name + " cannot be marked weak", node);
		if( isImmutable() && !legal.isImmutable()  )
			throw new ParseException(name + " cannot be marked immutable", node);
		if( isNullable() && !legal.isNullable()  )
			throw new ParseException(name + " cannot be marked nullable", node);
	}

	public void checkClassModifiers(Node node) throws ParseException
	{
		checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE | ABSTRACT | READONLY | IMMUTABLE), "A class", node);
		if( isReadonly() && isImmutable() )
			throw new ParseException("A class cannot be marked both readonly and immutable", node);
	}


	public void checkSingletonModifiers(Node node) throws ParseException
	{		  
		checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE | READONLY | IMMUTABLE), "A singleton", node);
		if( isReadonly() && isImmutable() )
			throw new ParseException("A singleton cannot be marked both readonly and immutable", node);
	}

	public void checkExceptionModifiers(Node node) throws ParseException
	{		  
		checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE ), "An exception", node);
		if( isReadonly() && isImmutable() )
			throw new ParseException("An exception cannot be marked both readonly and immutable", node);
	}

	public void checkErrorModifiers(Node node) throws ParseException
	{
		checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE | READONLY | IMMUTABLE ), "An error", node);
		if( isReadonly() && isImmutable() )
			throw new ParseException("An error cannot be marked both readonly and immutable", node);
	}


	public void checkInterfaceModifiers(Node node) throws ParseException
	{
		checkModifiers( new Modifiers(), "An interface", node);
	}

	public void checkViewModifiers(Node node) throws ParseException
	{
		checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE), "A view", node);
	}

	public void checkEnumModifiers(Node node) throws ParseException
	{
		checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE), "An enum", node);
	}

	public void checkFieldModifiers(Node node) throws ParseException
	{
		checkModifiers( new Modifiers(READONLY | CONSTANT | IMMUTABLE | GET | SET | WEAK | NULLABLE), "A field", node);
		if( isReadonly() && isImmutable() )
			throw new ParseException("A field cannot be marked both readonly and immutable", node);
		if( isSet() && isImmutable() )
			throw new ParseException("A field cannot be marked both set and immutable", node);
		if( isSet() && isReadonly() )
			throw new ParseException("A field cannot be marked both set and readonly", node);
		if( isConstant() && isImmutable() )
			throw new ParseException("A field cannot be marked both constant and immutable", node);
		if( isConstant() && isReadonly() )
			throw new ParseException("A field cannot be marked both constant and readonly", node);
	}

	public void checkMethodModifiers(Node node) throws ParseException
	{
		checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE | ABSTRACT | READONLY | IMMUTABLE | GET | SET | NATIVE), "A method", node);
		if( isGet() &&  isSet() )
			throw new ParseException("A method cannot be marked both get and set", node);
		if( isReadonly() && isImmutable() )
			throw new ParseException("A method cannot be marked both readonly and immutable", node);
		if( isAbstract() && isImmutable() )
			throw new ParseException("A method cannot be marked both abstract and immutable", node);
	}

	public void checkLocalMethodModifiers(Node node) throws ParseException
	{
		checkModifiers( new Modifiers(IMMUTABLE), "A local method", node);
	}


	public void checkCreateModifiers(Node node) throws ParseException
	{
		checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE), "A create", node);
	}

	public void checkDestroyModifiers(Node node) throws ParseException
	{
		checkModifiers(new Modifiers(PUBLIC), "A destroy", node);
	}		

	public void checkLocalVariableModifiers(Node node) throws ParseException
	{
		checkModifiers( new Modifiers(READONLY | IMMUTABLE | WEAK | NULLABLE), "A local variable", node);
		if( isReadonly() && isImmutable() )
			throw new ParseException("A local variable cannot be marked both readonly and immutable", node);
	}

	public void checkParameterAndReturnModifiers(Node node) throws ParseException
	{
		checkModifiers( new Modifiers(READONLY | IMMUTABLE | NULLABLE), "Method parameter and return types", node);
		if( isReadonly() && isImmutable() )
			throw new ParseException("Method parameter and return types cannot be marked both readonly and immutable", node);
	}

	public void setModifiers(int modifiers) {
		this.modifiers = modifiers;		
	}
	
	public int getModifiers() {
		return modifiers;		
	}
}