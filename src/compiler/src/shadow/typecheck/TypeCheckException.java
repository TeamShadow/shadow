package shadow.typecheck;

import shadow.typecheck.BaseChecker.Error;

@SuppressWarnings("serial")
public class TypeCheckException extends Exception
{
	private Error error;	
	
	public TypeCheckException(Error error, String message)
	{
		super( message  );
		this.error = error;				
	}
	
	public Error getError()
	{	
		return error;
	}
}
