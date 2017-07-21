package shadow.typecheck.type;

import shadow.parse.ShadowParser.TypeArgumentsContext;

@SuppressWarnings("serial")
public class InstantiationException extends Exception 
{	
	private final TypeArgumentsContext context;	
	
	public InstantiationException(String message, TypeArgumentsContext context)
	{
		super(message);		
		this.context = context;
	}
	
	
	public TypeArgumentsContext getContext() {
		return context;
	}
}
