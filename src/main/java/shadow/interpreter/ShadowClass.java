package shadow.interpreter;

import shadow.ShadowException;
import shadow.interpreter.InterpreterException.Error;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.Type;

public class ShadowClass extends ShadowObject {
	private Type type;

	public ShadowClass(Type type) throws ShadowException {
		super(type.isParameterized() ? Type.GENERIC_CLASS : Type.CLASS);
		this.type = type;
	}

	@Override
    public ShadowValue callMethod(String method, ShadowValue ... arguments) throws InterpreterException {
		try {		
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
		}
		catch(ShadowException e) {
		}
		
		return super.callMethod(method, arguments);
	}
	
	public ShadowString name() {
		return new ShadowString(type.toString());
	}
	
	public ShadowValue parent() throws ShadowException {
		if(type instanceof ClassType) {
			ClassType classType = (ClassType) type;
			if(classType.getExtendType() == null)
				return new ShadowNull(Type.CLASS);
			else
				return new ShadowClass(classType.getExtendType());
		}
		
		return new ShadowNull(Type.CLASS);
	}
	

	public ShadowBoolean isInterface() {		
		return new ShadowBoolean(type instanceof InterfaceType);
	}
	

	public ShadowBoolean isPrimitive() {
		return new ShadowBoolean(type.isPrimitive());
	}
	

	public ShadowBoolean isGeneric() {
		return new ShadowBoolean(type.isParameterized());
	}
	
	public ShadowBoolean isArray() {		
		return new ShadowBoolean(type instanceof ArrayType);
	}
	

	public ShadowBoolean isSingleton() {
		return new ShadowBoolean(type instanceof SingletonType);
	}
	

	public ShadowBoolean isMethod() {		
		return new ShadowBoolean(type instanceof MethodType);
	}


	 public ShadowBoolean equal(ShadowValue value) throws ShadowException {
	        if(value instanceof ShadowClass) {
	        	ShadowClass other = (ShadowClass) value;
	        	return new ShadowBoolean(type.equals(other.type));
	        }
	        
	        return new ShadowBoolean(false);
	 }

	public ShadowBoolean classIsSubtype( ShadowValue value ) {
		
		if(value instanceof ShadowClass) {
        	ShadowClass other = (ShadowClass) value;
        	return new ShadowBoolean(type.isSubtype(other.type));
        }
        
        return new ShadowBoolean(false);		
	}

	public String toString() {
		return type.toString();
	}	
	
	public ShadowInteger hash() throws ShadowException {
		return new ShadowString(toString()).hash();
	}
	
	@Override
	public String toLiteral() {
		return type.toString() + ":class";
	}
	
	@Override
	public ShadowValue cast(Type type) throws ShadowException {
		if(type.equals(getType()))
			return this;
		else if(type.isSubtype(getType()) || getType().isSubtype(type))
			return new ShadowObject((ClassType)type);

		throw new InterpreterException(Error.MISMATCHED_TYPE, "Cannot cast " + getType() + " to " + type);
	}
}
