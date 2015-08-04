package shadow.doctool;

import java.util.ArrayDeque;

import shadow.parser.javacc.ShadowException;

/**
 * Represents the contents of a Shadow documentation comment. Should be
 * associated with a class/interface/exception/singleton declaration or
 * a field/method declaration. Note that whitespace in documentation
 * comments is not guaranteed to be preserved
 */
public class DocumentationBuilder 
{
	private ArrayDeque<String> lines;
	
	public DocumentationBuilder()
	{
		lines = new ArrayDeque<String>();
	}
	
	/** Parses a single line comment, removing leading/trailing whitespace */
	public void appendLine(String line)
	{
		lines.add(clean(line));
	}
	
	/** Parses a single line comment, removing leading/trailing whitespace */
	public void prependLine(String line)
	{
		line = line.trim();
		if (!line.isEmpty())
			lines.addFirst(clean(line));
	}
	
	/**
	 * Parses and splits a block comment. Leading asterisks and 
	 * leading/trailing whitespace are also removed
	 */
	public void addBlock(String block)
	{
		String[] split = block.split("[\r\n]+");
		
		// This loop runs backwards so that the block can be added to the
		// front of the deque, but that the individual lines remain in correct
		// order
		for (int i = split.length - 1; i >= 0; --i) {
			String line = split[i].trim();
			
			// Remove the leading asterisk, if it exists. The first line of a
			// multi-line comment (following /**) should be excluded from this
			if (i != 0 && line.indexOf('*') == 0)
				line = line.substring(1).trim();
			
			// Only keep non-empty lines
			if (!line.equals(""))
				lines.addFirst(clean(line));
		}
	}
	
	/** Converts all whitespace chunks into single spaces */
	static String clean(String value)
	{
		return value.replaceAll("\\s+", " ");
	}
	
	/** Determines if documentation text has actually been added */
	public boolean hasContent()
	{
		return (lines.size() > 0);
	}
	
	/** 
	 * Parses and processes the directives present in the documentation text,
	 * returning a Documentation object containing the results
	 */
	public Documentation process() throws ShadowException, DocumentationException
	{
		return new Documentation(this);
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		int i = 0;
		for (String line : lines)
		{
			builder.append(line);
			
			if (i < lines.size() - 1)
				builder.append("\n");
			
			i++;
		}
		
		return builder.toString();
	}
}
