package shadow.doctool;

import java.util.ArrayDeque;

import shadow.parser.javacc.ShadowException;

/**
 * Represents the contents of a Shadow documentation comment. Should be
 * associated with a class/interface/exception/singleton declaration or
 * a field/method declaration.
 */
public class DocumentationBuilder 
{
	private ArrayDeque<String> lines;
	
	public DocumentationBuilder()
	{
		lines = new ArrayDeque<String>();
	}
	
	/** Parses a single line comment, removing leading/trailing whitespace */
	public void addLine(String line)
	{
		lines.addFirst(line.trim());
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
		for (int i = split.length - 1; i >= 0; --i)
		{
			split[i] = split[i].trim();
			
			// Remove the leading asterisk, if it exists. The first line of a
			// multi-line comment (following /**) should be excluded from this
			if (i != 0 && split[i].indexOf('*') == 0)
				split[i] = split[i].substring(1).trim();
			
			// Only keep non-empty lines
			if (!split[i].equals(""))
				lines.addFirst(split[i]);
		}
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
	public Documentation process() throws ShadowException
	{
		return DirectiveParser.process(this);
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
