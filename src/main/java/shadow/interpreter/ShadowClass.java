package shadow.interpreter;

import shadow.interpreter.InterpreterException.Error;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.Type;

public class ShadowClass extends ShadowObject {
	private final Type representedType;

	public ShadowClass(Type representedType) throws InterpreterException {
		super(representedType.isParameterized() ? Type.GENERIC_CLASS : Type.CLASS);
		this.representedType = representedType;
	}

	@Override
    public ShadowValue callMethod(String method, ShadowValue ... arguments) throws InterpreterException {
		if(arguments.length == 0) {
			switch(method) {
			case "isArray": return isArray();
			case "isGeneric": return isGeneric();
			case "isInterface": return isInterface();
			case "isMethod": return isMethod();
			case "isPrimitive": return isPrimitive();
			case "isSingleton": return isSingleton();
			case "name": return name();
			case "parent": return parent();
			}
		}
		
		return super.callMethod(method, arguments);
	}
	
	public ShadowString name() {
		return new ShadowString(representedType.toString());
	}
	
	public ShadowValue parent() throws InterpreterException {
		if(representedType instanceof ClassType) {
			ClassType classType = (ClassType) representedType;
			if(classType.getExtendType() == null)
				return new ShadowNull(Type.CLASS);
			else
				return new ShadowClass(classType.getExtendType());
		}
		
		return new ShadowNull(Type.CLASS);
	}
	

	public ShadowBoolean isInterface() {		
		return new ShadowBoolean(representedType instanceof InterfaceType);
	}
	

	public ShadowBoolean isPrimitive() {
		return new ShadowBoolean(representedType.isPrimitive());
	}
	

	public ShadowBoolean isGeneric() {
		return new ShadowBoolean(representedType.isParameterized());
	}
	
	public ShadowBoolean isArray() {		
		return new ShadowBoolean(representedType instanceof ArrayType);
	}
	

	public ShadowBoolean isSingleton() {
		return new ShadowBoolean(representedType instanceof SingletonType);
	}
	

	public ShadowBoolean isMethod() {		
		return new ShadowBoolean(representedType instanceof MethodType);
	}


	 public ShadowBoolean equal(ShadowValue value) throws InterpreterException {
	        if(value instanceof ShadowClass) {
	        	ShadowClass other = (ShadowClass) value;
	        	return new ShadowBoolean(representedType.equals(other.representedType));
	        }
	        
	        return new ShadowBoolean(false);
	 }

	public ShadowBoolean classIsSubtype( ShadowValue value ) {
		
		if(value instanceof ShadowClass) {
        	ShadowClass other = (ShadowClass) value;
        	return new ShadowBoolean(representedType.isSubtype(other.representedType));
        }
        
        return new ShadowBoolean(false);		
	}

	public String toString() {
		return representedType.toString();
	}	
	
	public ShadowInteger hash() throws InterpreterException {
		return new ShadowString(toString()).hash();
	}

	/** Gets the type that this Class object was created from.
	 *
	 * E.g. returns {@code String} for a Class object created via {@code String:class}.
	 */
	public Type getRepresentedType() {
		return representedType;
	}
	
	@Override
	public String toLiteral() {
		return representedType.toString() + ":class";
	}
	
	@Override
	public ShadowValue cast(Type type) throws InterpreterException {
		if(type.equals(getType()))
			return this;
		else if(type.isSubtype(getType()) || getType().isSubtype(type))
			return new ShadowObject((ClassType)type);

		throw new InterpreterException(Error.MISMATCHED_TYPE, "Cannot cast " + getType() + " to " + type);
	}
}
