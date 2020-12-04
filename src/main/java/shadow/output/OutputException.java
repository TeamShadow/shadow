package shadow.output;

import shadow.ShadowException;
import shadow.ShadowExceptionErrorKind;

public class OutputException extends ShadowException {

	private static final long serialVersionUID = -6433760185046688067L;

	public OutputException(String message) {
		super(message, null);		
	}

	@Override
	public ShadowExceptionErrorKind getError() {
		return null;
	}

}
