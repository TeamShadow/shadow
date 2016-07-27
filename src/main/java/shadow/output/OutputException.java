package shadow.output;

import shadow.ShadowException;
import shadow.ShadowExceptionFactory;

public class OutputException extends ShadowException {

	public OutputException(String message) {
		super(message);		
	}

	@Override
	public ShadowExceptionFactory getError() {		
		return null;
	}

}
