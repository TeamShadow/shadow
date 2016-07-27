package shadow;

@SuppressWarnings("serial")
public class CompileException extends ShadowException
{
	public CompileException(String message) 
	{
		super(message);
	}

	@Override
	public ShadowExceptionFactory getError() {	
		return null;
	}
}
