package shadow.interpreter;

import shadow.ShadowException;
import shadow.ShadowExceptionFactory;

public class InterpreterException extends ShadowException {

	public InterpreterException(String message) {
		super(message);		
	}

	@Override
	public ShadowExceptionFactory getError() {
		return null;
	}

}
