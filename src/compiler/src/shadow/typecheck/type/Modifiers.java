package shadow.typecheck.type;

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
	//public static final int FINAL          	= 0x0010;     
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
	
	public static final Modifiers NO_MODIFIERS = new Modifiers();

	/* Type Modifiers:
  	public		(only for inner classes)
  	private		(only for inner classes)
  	protected 	(only for inner classes)
		final 		(can't be extended)
		abstract		
		immutable	(final, abstract, and immutable are mutually exclusive)
	 */


	/* Declaration Modifiers:
		public			(only for methods)
  	private			(only for methods)
  	protected 		(only for methods)		
		static			(only for members)
		get				(only for fields)
		set				(only for fields)
		constant		(only for fields, public constant)
		final 			(can't be changed)		
		weak			(reference will not stop GC)
		native          (used to map in C functions)
		nullable		(variables that can be null)
	 */

	/* Hidden Modifiers: (used in the compiler internally, but cannot be marked by the user)
		assignable 		(used to mark variables [lvalues])
		type name		(used to distinguish between types and values/variables with that type, e.g. int vs. 5 or String vs. "figs")
		field			(used to mark nodes that are fields, as opposed to local variables, to inform the TAC [methods are NOT marked as fields])
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
		/*if( isFinal() )
			sb.append("final ");*/
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

	/**
	 * Changes the given modifier.
	 */
	public void removeModifier(int mod) { modifiers = modifiers & ~mod; }
	public void addModifier(int mod) { modifiers = modifiers | mod; }
	public boolean hasModifier(int mod ) { return (modifiers & mod) != 0;     }
	public boolean hasModifier(Modifiers modifier) { return hasModifier(modifier.modifiers);     }
	public void checkAndAdd(int mod ) throws ParseException
	{
		if( hasModifier(mod ) )
			throw new ParseException( "Repeated modifiers not allowed" );

		addModifier(mod );
	}

	public void checkModifiers( Modifiers legal, String name )  throws ParseException
	{	
		if( isPublic() && !legal.isPublic()  )
			throw new ParseException(name + " cannot be marked public");
		if( isProtected() && !legal.isProtected( )  )
			throw new ParseException(name + " cannot be marked protected");
		if( isPrivate() && !legal.isPrivate()  )
			throw new ParseException(name + " cannot be marked private");		
		if( isAbstract() && !legal.isAbstract()  )
			throw new ParseException(name + " cannot be marked abstract");
		/*if( isFinal() && !legal.isFinal()  )
			throw new ParseException(name + " cannot be marked final"); */
		if( isReadonly() && !legal.isReadonly()  )
			throw new ParseException(name + " cannot be marked readonly");
		if( isNative() && !legal.isNative()  )
			throw new ParseException(name + " cannot be marked native");
		if( isGet() && !legal.isGet()  )
			throw new ParseException(name + " cannot be marked get");
		if( isSet() && !legal.isSet()  )
			throw new ParseException(name + " cannot be marked set");
		if( isConstant() && !legal.isConstant()  )
			throw new ParseException(name + " cannot be marked constant");
		if( isWeak() && !legal.isWeak()  )
			throw new ParseException(name + " cannot be marked weak");
		if( isImmutable() && !legal.isImmutable()  )
			throw new ParseException(name + " cannot be marked immutable");
		if( isNullable() && !legal.isNullable()  )
			throw new ParseException(name + " cannot be marked nullabe");
	}

	public void checkClassModifiers() throws ParseException
	{
		checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE | ABSTRACT | READONLY | IMMUTABLE), "A class");	
	}


	public void checkSingletonModifiers() throws ParseException
	{		  
		checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE | READONLY | IMMUTABLE), "A singleton");	
	}

	public void checkExceptionModifiers() throws ParseException
	{		  
		checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE ), "An exception");
	}

	public void checkErrorModifiers() throws ParseException
	{
		checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE | READONLY | IMMUTABLE ), "An error");
	}


	public void checkInterfaceModifiers() throws ParseException
	{
		checkModifiers( new Modifiers(), "An interface");
	}

	public void checkViewModifiers() throws ParseException
	{
		checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE), "A view");
	}

	public void checkEnumModifiers() throws ParseException
	{
		checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE), "An enum");
	}

	public void checkFieldModifiers() throws ParseException
	{
		checkModifiers( new Modifiers(READONLY | CONSTANT | IMMUTABLE | GET | SET | WEAK | NULLABLE), "A field");
	}

	public void checkMethodModifiers() throws ParseException
	{
		checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE | ABSTRACT | READONLY | IMMUTABLE | GET | SET | NATIVE), "A method");
		if( isGet() &&  isSet() )
			throw new ParseException("A method cannot be marked both get and set");			
	}

	public void checkLocalMethodModifiers() throws ParseException
	{
		checkModifiers( new Modifiers(IMMUTABLE), "A local method");
	}


	public void checkCreateModifiers() throws ParseException
	{
		checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE), "A create");
	}

	public void checkDestroyModifiers() throws ParseException
	{
		checkModifiers(new Modifiers(PUBLIC), "A destroy");
	}		

	public void checkLocalVariableModifiers() throws ParseException
	{
		checkModifiers( new Modifiers(READONLY | IMMUTABLE | WEAK | NULLABLE), "A local variable");
	}

	public void checkParameterAndReturnModifiers() throws ParseException
	{
		checkModifiers( new Modifiers(READONLY | IMMUTABLE | NULLABLE), "Method parameter and return types");		  

		//what does final mean for parameters and return types?
		//shouldn't all parameters be final?		
	}

	public void setModifiers(int modifiers) {
		this.modifiers = modifiers;		
	}
	
	public int getModifiers() {
		return modifiers;		
	}
}