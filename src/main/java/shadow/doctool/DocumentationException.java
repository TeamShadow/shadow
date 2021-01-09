package shadow.doctool;

import shadow.ShadowException;
import shadow.ShadowExceptionErrorKind;

@SuppressWarnings("serial")
public class DocumentationException extends ShadowException
{
	public DocumentationException(String message)
	{
		super(message, null);		
	}

	@Override
	public ShadowExceptionErrorKind getError() {
		return null;
	}
}
