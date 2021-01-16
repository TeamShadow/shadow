package shadow.interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import shadow.ShadowException;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Type;
import shadow.interpreter.InterpreterException.Error;

public class ShadowObject extends ShadowValue {
	private Type type;

	public ShadowObject(Type type) throws ShadowException {
		if (type.isPrimitive())
			throw new InterpreterException(Error.INVALID_TYPE, "Cannot create an object with a " +
					"primitive type");
		this.type = type;
	}
	@Override
	public Type getType() {
		return type;
	}

	@Override
	public ShadowValue copy() throws ShadowException {
		return new ShadowObject(getType());
	}

	@Override
	public String toLiteral() {
		throw new UnsupportedOperationException("Cannot convert an arbitrary object to a literal");
	}

	@Override
	public ShadowValue cast(Type type) throws ShadowException {
		return new ShadowObject(type);
	}
}
