package shadow.typecheck.type;

import java.util.ArrayList;
import java.util.List;

import shadow.ShadowException;
import shadow.parse.ParseException;
import shadow.parse.ParseException.Error;

/**
 * Class to hold modifiers.
 * 
 * <b>Type Modifiers</b>
 * <ol>
 * <li>public: only for inner classes</li>
 * <li>private: only for inner classes</li>
 * <li>protected: only for inner classes</li>        
 * <li>abstract: cannot instantiate object</li>        
 * <li>immutable: abstract, readonly, and immutable are mutually exclusive</li>
 * <li>readonly: abstract, readonly, and immutable are mutually exclusive</li>
 * <li>locked: cannot extend class</li>
 * </ol>
 * 
 * <b>Declaration Modifiers</b>
 * <ul>
 * <li>public: only for methods</li>
 * <li>private: only for methods</li>
 * <li>protected: only for methods</li>
 * <li>get: only for fields</li>
 * <li>set: only for fields</li>
 * <li>constant: only for fields, constant</li>
 * <li>weak: reference will not stop GC</li>
 * <li>native: used to map in C functions</li>
 * <li>nullable: variables that can be null</li>
 * <li>immutable: all references to this object are readonly</li>
 * <li>readonly: no mutable methods can be called on this reference</li>
 * <li>locked: method cannot be overridden</li>
 * </ul>
 * 
 * <b>Hidden Modifiers</b> <i>(Used in the compiler internally, but cannot be marked by the user)</i>
 * <ul>
 * <li>assignable: used to mark variables [lvalues]</li>
 * <li>type name: used to distinguish between types and values/variables with that type, e.g. int vs. 5 or String vs. "figs"</li>
 * <li>field: used to mark nodes that are fields, as opposed to local variables, to inform the TAC [methods are NOT marked as fields]</li>
 * <li>temporary readonly: returned object will be readonly</li> 
 * </ul>
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
	public static final int LOCKED			= 0x1000;
	public static final int EXTERN			= 0x40000;
	public static final int EXTERN_SHARABLE = 0x80000;

	public static final int ASSIGNABLE   	= 0x2000;
	public static final int TYPE_NAME   	= 0x4000;
	public static final int FIELD		   	= 0x8000;
	public static final int PROPERTY	   	= 0x10000;
	public static final int TEMPORARY_READONLY	= 0x20000;
	
	public static final Modifiers NO_MODIFIERS = new Modifiers();

	/* Type Modifiers:
  		public		(only for inner classes)
  		private		(only for inner classes)
  		protected 	(only for inner classes)		
		abstract		
		immutable	(abstract, readonly, and immutable are mutually exclusive)
		readonly	(abstract, readonly, and immutable are mutually exclusive)
		locked		(class cannot be extended)
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
		locked		(method cannot be overridden)
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
		if( this.isTemporaryReadonly() )
			sb.append("temporary readonly ");
		if( isReadonly() )
			sb.append("readonly ");
		if( isNative() )
			sb.append("native ");
		if( isExtern() )
			sb.append("extern ");
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
		if( isLocked() )
			sb.append("locked ");
		
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
	public boolean isExtern() { return (modifiers & EXTERN) != 0; }	
	public boolean isExternSharable() { return (modifiers & EXTERN_SHARABLE) != 0; }	
	public boolean isNativeOrExtern() { return (isNative() || isExtern()); }
	
	public boolean isGet() { return (modifiers & GET) != 0; }
	public boolean isSet() { return (modifiers & SET) != 0; }

	public boolean isConstant() { return (modifiers & CONSTANT) != 0; }
	public boolean isLocked() { return (modifiers & LOCKED) != 0; }

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
	
	/**
	 * Adds the given modifier. Returns true if not already present.
	 * @param mod
	 * @return true if modifiers not already present
	 */
	public boolean addModifier(int mod) { 
		boolean newValue = (modifiers & mod) == 0;
		modifiers = modifiers | mod; 
		
		return newValue;
	}
	public boolean hasModifier(int mod ) { return (modifiers & mod) != 0;     }
	public boolean hasModifier(Modifiers modifier) { return hasModifier(modifier.modifiers);     }
		
	public void upgradeToTemporaryReadonly()
	{
		//if already immutable or readonly, don't change
		if( !isImmutable() && !isReadonly() )
			addModifier(Modifiers.TEMPORARY_READONLY);
	}

	private List<ShadowException> checkModifiers( Modifiers legal, String name)
	{	
		List<ShadowException> exceptions = new ArrayList<ShadowException>();
		
		if( isPublic() && !legal.isPublic()  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked public"));
		if( isProtected() && !legal.isProtected( )  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked protected"));
		if( isPrivate() && !legal.isPrivate()  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked private"));
		if( isAbstract() && !legal.isAbstract()  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked abstract"));
		if( isReadonly() && !legal.isReadonly()  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked readonly"));
		if( isNative() && !legal.isNative()  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked native"));
		if( isExtern() && !legal.isExtern()  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked extern"));
		if( isGet() && !legal.isGet()  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked get"));
		if( isSet() && !legal.isSet()  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked set"));
		if( isConstant() && !legal.isConstant()  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked constant"));
		if( isWeak() && !legal.isWeak()  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked weak"));
		if( isImmutable() && !legal.isImmutable()  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked immutable"));
		if( isNullable() && !legal.isNullable()  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked nullable"));
		if( isLocked() && !legal.isLocked()  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked locked"));
		
		return exceptions;
	}

	public List<ShadowException> checkClassModifiers()
	{
		return checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE | ABSTRACT | IMMUTABLE | LOCKED), "A class");		
	}


	public List<ShadowException> checkSingletonModifiers() 
	{		  
		return checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE | IMMUTABLE), "A singleton");
	}

	public List<ShadowException> checkExceptionModifiers() 
	{		  
		List<ShadowException> exceptions = checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE ), "An exception");
		if( isReadonly() && isImmutable() )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "An exception cannot be marked both readonly and immutable"));
		return exceptions;
	}

	public List<ShadowException> checkInterfaceModifiers() 
	{
		return checkModifiers( new Modifiers(), "An interface");
	}

	public List<ShadowException> checkEnumModifiers() 
	{
		return checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE), "An enum");
	}

	public List<ShadowException> checkFieldModifiers() 
	{
		List<ShadowException> exceptions = checkModifiers( new Modifiers(READONLY | CONSTANT | IMMUTABLE | GET | SET | WEAK | NULLABLE | PUBLIC | PRIVATE | PROTECTED), "A field");
		if( isReadonly() && isImmutable() )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "A field cannot be marked both readonly and immutable"));
		if( isSet() && isImmutable() )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "A field cannot be marked both set and immutable"));
		if( isSet() && isReadonly() )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "A field cannot be marked both set and readonly"));
		if( isConstant() && isImmutable() )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "A field cannot be marked both constant and immutable"));
		if( isConstant() && isReadonly() )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "A field cannot be marked both constant and readonly"));
		if( !isConstant() ) {
			if( isPublic() )
				exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "Only a constant field can be marked public"));
			else if( isProtected() )
				exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "Only a constant field can be marked protected"));
			else if( isPrivate() )
				exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "Only a constant field can be marked private"));
		}
		return exceptions;
	}

	public List<ShadowException> checkMethodModifiers() 
	{
		List<ShadowException> exceptions = checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE | ABSTRACT | READONLY | GET | SET | NATIVE | EXTERN | LOCKED), "A method");
		if( isGet() &&  isSet() )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "A method cannot be marked both get and set"));		
		if( isReadonly() && isImmutable() )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "A method cannot be marked both readonly and immutable"));
		if(isNative() && isExtern()) {
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "A method cannot be marked both native and extern"));			
		}
		
		if(isExtern() && !isPrivate()) {
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "An extern method can only be private"));	
		}	
		if(isExtern() && isExternSharable()) {
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "A strictly sharable method cannot be marked extern"));
		}
		if(isExternSharable() && isPublic()) {
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "A strictly sharable method cannot be public"));
		}
		
		return exceptions;
	}

	public List<ShadowException> checkLocalMethodModifiers() 
	{
		return checkModifiers( new Modifiers(READONLY), "A local method");
	}


	public List<ShadowException> checkCreateModifiers() 
	{
		return checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE | NATIVE), "A create");
	}

	public List<ShadowException> checkDestroyModifiers() 
	{
		return checkModifiers(new Modifiers(PUBLIC), "A destroy");
	}		

	public List<ShadowException> checkLocalVariableModifiers() 
	{
		List<ShadowException> exceptions = checkModifiers( new Modifiers(READONLY | IMMUTABLE | WEAK | NULLABLE), "A local variable");
		if( isReadonly() && isImmutable() )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "A local variable cannot be marked both readonly and immutable"));
		return exceptions;
	}

	public List<ShadowException> checkParameterAndReturnModifiers() 
	{
		List<ShadowException> exceptions = checkModifiers( new Modifiers(READONLY | IMMUTABLE | NULLABLE), "Method parameter and return types");
		if( isReadonly() && isImmutable() )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "Method parameter and return types cannot be marked both readonly and immutable"));
		return exceptions;
	}

	public void setModifiers(int modifiers) {
		this.modifiers = modifiers;		
	}
	
	public int getModifiers() {
		return modifiers;		
	}
}