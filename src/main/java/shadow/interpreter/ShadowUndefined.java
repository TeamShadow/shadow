package shadow.interpreter;

import shadow.ShadowException;
import shadow.typecheck.type.Type;

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
	public ShadowValue cast(Type type) throws ShadowException {
		return new ShadowUndefined(type);
	}

	@Override
	public ShadowValue copy() throws ShadowException {		
		return new ShadowUndefined(type);
	}
	

	@Override
	public String toString() {
		return "Undefined";
	}
}
