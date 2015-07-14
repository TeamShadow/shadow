package shadow.doctool.tag;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import shadow.doctool.DocumentationException;

/** A collection of tag parsers for documentation comments */
public class ParserManager
{
	public interface TagParser
	{
		public List<String> parse(String text) throws DocumentationException;
	}
	
	public static class ArgDescriptionParser implements TagParser
	{
		private final int argCount;
		private final boolean hasDescription;
		
		private static final Pattern argumentPattern = Pattern.compile("[^\\s]+");
		
		public ArgDescriptionParser(int argCount, boolean hasDescription)
		{
			this.argCount = argCount;
			this.hasDescription = hasDescription;
			
			if (argCount < 0)
				throw new IllegalArgumentException("Expected the number of arguments to be zero or greater");
		}
		
		public List<String> parse(String text) throws DocumentationException
		{
			text = text.trim();
			List<String> results = new ArrayList<String>();
			Matcher matcher = argumentPattern.matcher(text);
			int lastCaptured = 0;
			
			// Capture each argument
			for (int i = 0; i < argCount; ++i)
			{
				if (matcher.find())
				{
					results.add(matcher.group());
					lastCaptured = matcher.end();
				}
				else
					throw new DocumentationException("Found fewer arguments than expected");
			}
			
			// Capture any remaining text
			if (lastCaptured < text.length())
			{
				if (hasDescription)
					results.add(text.substring(lastCaptured).trim().replaceAll("\\s+", " "));
				else // TODO: Should this just be a warning?
					throw new DocumentationException("Found more arguments than expected");
			}
			
			return results;
		}
	}
	
	public static class DelimitedParser implements TagParser
	{
		private final char delimiter;
		
		public DelimitedParser(char delimiter)
		{
			this.delimiter = delimiter;
			
			if (Character.isWhitespace(delimiter))
				throw new IllegalArgumentException("Whitespace characters cannot be used as delimiters");
		}
		
		public List<String> parse(String text) throws DocumentationException
		{
			List<String> results = new ArrayList<String>();
			String[] tokens = text.split(Pattern.quote(Character.toString(delimiter)));
			
			for (String token : tokens)
			{
				String fixed = token.trim().replaceAll("\\s+", " ");
				if (!fixed.isEmpty())
					results.add(fixed);
			}
			
			return results;
		}
	}
}

