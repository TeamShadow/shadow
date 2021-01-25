package shadow.typecheck.type;

import java.util.ArrayList;
import java.util.List;

import shadow.ShadowException;
import shadow.parse.Context;
import shadow.parse.ParseException;
import shadow.parse.ParseException.Error;
import shadow.parse.ShadowParser;

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
	private static int bits = 0;
	
	/* Definitions of the bits in the modifiers field.  */
	/* Unused Java modifiers are commented out until modifiers are finalized */ 
	public static final int PUBLIC         	= 1 << bits++;
	public static final int PROTECTED      	= 1 << bits++;
	public static final int PRIVATE       	= 1 << bits++;
	public static final int ABSTRACT       	= 1 << bits++;	
     
	public static final int READONLY		= 1 << bits++;
	public static final int WEAK           	= 1 << bits++;
	public static final int IMMUTABLE      	= 1 << bits++;
	public static final int NULLABLE      	= 1 << bits++;
	public static final int GET				= 1 << bits++;
	public static final int SET				= 1 << bits++;
	public static final int CONSTANT		= 1 << bits++;
	public static final int LOCKED			= 1 << bits++;

	public static final int ASSIGNABLE   	= 1 << bits++;
	public static final int TYPE_NAME   	= 1 << bits++;
	public static final int FIELD		   	= 1 << bits++;
	public static final int PROPERTY	   	= 1 << bits++;
	public static final int TEMPORARY_READONLY	= 1 << bits++;
	
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

	private List<ShadowException> checkModifiers( Modifiers legal, String name, Context ctx)
	{	
		List<ShadowException> exceptions = new ArrayList<ShadowException>();
		
		if( isPublic() && !legal.isPublic()  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked public", ctx));
		if( isProtected() && !legal.isProtected( )  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked protected", ctx));
		if( isPrivate() && !legal.isPrivate()  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked private", ctx));
		if( isAbstract() && !legal.isAbstract()  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked abstract", ctx));
		if( isReadonly() && !legal.isReadonly()  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked readonly", ctx));
		if( isGet() && !legal.isGet()  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked get", ctx));
		if( isSet() && !legal.isSet()  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked set", ctx));
		if( isConstant() && !legal.isConstant()  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked constant", ctx));
		if( isWeak() && !legal.isWeak()  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked weak", ctx));
		if( isImmutable() && !legal.isImmutable()  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked immutable", ctx));
		if( isNullable() && !legal.isNullable()  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked nullable", ctx));
		if( isLocked() && !legal.isLocked()  )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, name + " cannot be marked locked", ctx));
		
		return exceptions;
	}

	public List<ShadowException> checkClassModifiers(Context ctx)
	{
		return checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE | ABSTRACT | IMMUTABLE | LOCKED), "A class", ctx);		
	}


	public List<ShadowException> checkSingletonModifiers(Context ctx) 
	{		  
		return checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE | IMMUTABLE), "A singleton", ctx);
	}

	public List<ShadowException> checkExceptionModifiers(Context ctx) 
	{		  
		List<ShadowException> exceptions = checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE ), "An exception", ctx);
		if( isReadonly() && isImmutable() )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "An exception cannot be marked both readonly and immutable", ctx));
		return exceptions;
	}

	public List<ShadowException> checkInterfaceModifiers(Context ctx) 
	{
		return checkModifiers( new Modifiers(), "An interface", ctx);
	}

	public List<ShadowException> checkEnumModifiers(Context ctx) 
	{
		return checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE), "An enum", ctx);
	}

	public List<ShadowException> checkAttributeModifiers(Context ctx) {
		List<ShadowException> exceptions = new ArrayList<>();

		if (!equals(NO_MODIFIERS)) {
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "An attribute declaration cannot have modifiers", ctx));
		}

		return exceptions;
	}

	public List<ShadowException> checkFieldModifiers(Context ctx) 
	{
		if (ctx.getParent() instanceof ShadowParser.AttributeBodyDeclarationContext && !equals(NO_MODIFIERS)) {
			List<ShadowException> exceptions = new ArrayList<>();
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "A field within an attribute cannot have modifiers", ctx));
			return exceptions;
		}

		List<ShadowException> exceptions = checkModifiers( new Modifiers(READONLY | CONSTANT | IMMUTABLE | GET | SET | WEAK | NULLABLE | PUBLIC | PRIVATE | PROTECTED | LOCKED ), "A field", ctx);
		if( isReadonly() && isImmutable() )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "A field cannot be marked both readonly and immutable", ctx));
		if( isSet() && isImmutable() )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "A field cannot be marked both set and immutable", ctx));
		if( isSet() && isReadonly() )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "A field cannot be marked both set and readonly", ctx));
		if( isConstant() && isImmutable() )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "A field cannot be marked both constant and immutable", ctx));
		if( isConstant() && isReadonly() )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "A field cannot be marked both constant and readonly", ctx));
		if( !isConstant() ) {
			if( isPublic() )
				exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "Only a constant field can be marked public", ctx));
			else if( isProtected() )
				exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "Only a constant field can be marked protected", ctx));
			else if( isPrivate() )
				exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "Only a constant field can be marked private", ctx));
		}		
		if( isLocked() && !isSet() && !isGet() )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "A field can only be marked locked if it is also marked get or set", ctx));
		
		return exceptions;
	}

	public List<ShadowException> checkMethodModifiers(Context ctx) 
	{
		List<ShadowException> exceptions = checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE | ABSTRACT | READONLY | GET | SET | LOCKED), "A method", ctx);
		if( isGet() &&  isSet() )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "A method cannot be marked both get and set", ctx));		
		if( isReadonly() && isImmutable() )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "A method cannot be marked both readonly and immutable", ctx));
		return exceptions;
	}

	public List<ShadowException> checkLocalMethodModifiers(Context ctx) 
	{
		return checkModifiers( new Modifiers(READONLY), "A local method", ctx);
	}


	public List<ShadowException> checkCreateModifiers(Context ctx) 
	{
		return checkModifiers( new Modifiers(PUBLIC | PROTECTED | PRIVATE), "A create", ctx);
	}

	public List<ShadowException> checkDestroyModifiers(Context ctx) 
	{
		return checkModifiers(new Modifiers(PUBLIC), "A destroy", ctx);
	}		

	public List<ShadowException> checkLocalVariableModifiers(Context ctx) 
	{
		List<ShadowException> exceptions = checkModifiers( new Modifiers(READONLY | IMMUTABLE | WEAK | NULLABLE), "A local variable", ctx);
		if( isReadonly() && isImmutable() )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "A local variable cannot be marked both readonly and immutable", ctx));
		return exceptions;
	}

	public List<ShadowException> checkParameterAndReturnModifiers(Context ctx) 
	{
		List<ShadowException> exceptions = checkModifiers( new Modifiers(READONLY | IMMUTABLE | NULLABLE), "Method parameter and return types", ctx);
		if( isReadonly() && isImmutable() )
			exceptions.add(new ParseException(Error.ILLEGAL_MODIFIER, "Method parameter and return types cannot be marked both readonly and immutable", ctx));
		return exceptions;
	}

	public void setModifiers(int modifiers) {
		this.modifiers = modifiers;		
	}
	
	public int getModifiers() {
		return modifiers;		
	}
}