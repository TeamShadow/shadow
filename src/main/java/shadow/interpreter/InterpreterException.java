package shadow.interpreter;

import shadow.ShadowException;
import shadow.ShadowExceptionFactory;

public class InterpreterException extends ShadowException {

	private static final long serialVersionUID = 6292713104787662488L;

	public InterpreterException(String message) {
		super(message);		
	}

	@Override
	public ShadowExceptionFactory getError() {
		return null;
	}

}
