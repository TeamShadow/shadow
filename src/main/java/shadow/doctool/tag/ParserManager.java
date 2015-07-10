package shadow.doctool.tag;

import java.util.List;

import shadow.doctool.DocumentationException;

public class ParserManager
{
	public interface TagParser
	{
		public List<String> parse(String text) throws DocumentationException;
	}
	
	public static class ArgDescriptionParser
	{
		
	}
	
	public static class DelimitedParser
	{
		
	}
}

