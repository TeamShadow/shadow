package shadow.typecheck;

@SuppressWarnings("serial")
public class CycleFoundException extends Exception {

	private Object cause;
	
	public CycleFoundException(Object cause)
	{
		this.cause = cause;
	}
	
	public Object getCycleCause()
	{
		return cause;		
	}

}
