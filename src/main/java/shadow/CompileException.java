package shadow;

@SuppressWarnings("serial")
public class CompileException extends ShadowException
{
	public CompileException(String message) 
	{
		super(message, null);
	}

	@Override
	public ShadowExceptionErrorKind getError() {
		return null;
	}
}
