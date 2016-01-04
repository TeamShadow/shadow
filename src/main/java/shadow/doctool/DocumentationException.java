package shadow.doctool;

import shadow.parser.javacc.ParseException;

@SuppressWarnings("serial")
public class DocumentationException extends ParseException
{
	public DocumentationException(String message)
	{
		super(message);		
	}
}
