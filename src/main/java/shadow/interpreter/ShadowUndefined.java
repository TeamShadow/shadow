package shadow.interpreter;

import shadow.typecheck.type.Type;

/**
 * A value held by a variable before it's been defined.
 */
public class ShadowUndefined extends ShadowValue {
	
	private final Type type;
	
	public ShadowUndefined(Type type) {
		this.type = type;
	}	

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public ShadowValue cast(Type type) throws InterpreterException {
		return new ShadowUndefined(type);
	}

	@Override
	public ShadowValue copy() throws InterpreterException {
		return new ShadowUndefined(type);
	}
	

	@Override
	public String toString() {
		return "Undefined";
	}

	@Override
	public String toLiteral() {
		throw new UnsupportedOperationException("Undefined values cannot be represented as literals");
	}
}
