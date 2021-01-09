package shadow.interpreter;

import shadow.ShadowException;
import shadow.interpreter.InterpreterException.Error;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.UnboundMethodType;

public class ShadowUnboundMethod extends ShadowValue {
	
	private ShadowValue object;
	private UnboundMethodType type;
	
	public ShadowUnboundMethod(ShadowValue object, UnboundMethodType type) {
		this.object = object;
		this.type = type;		
	}

	@Override
	public UnboundMethodType getType() {
		return type;
	}
	
	public ShadowValue getObject() {
		return object;
	}

	@Override
	public ShadowValue cast(Type type) throws ShadowException {
		if(type instanceof UnboundMethodType)
			return this;		
		throw new InterpreterException(Error.MISMATCHED_TYPE, "Cannot cast " + getType() + " to " + type);
	}

	@Override
	public ShadowValue copy() throws ShadowException {
		return new ShadowUnboundMethod(object, type);
	}

	@Override
	public String toLiteral() {
		return null;
	}

}
