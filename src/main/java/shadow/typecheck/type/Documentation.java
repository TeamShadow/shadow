package shadow.typecheck.type;

import java.util.ArrayList;

/**
 * Represents the contents of a Shadow documentation comment. Should be
 * associated with a class/interface/exception/singleton declaration or
 * a field/method declaration.
 */
public class Documentation 
{
	private ArrayList<String> lines;
	
	public Documentation()
	{
		lines = new ArrayList<String>();
	}
	
	public void addLine(String line)
	{
		lines.add(line);
	}
	
	/** Determines if documentation text has actually been added */
	public boolean hasContent()
	{
		return (lines.size() > 0);
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		for (String line : lines)
		{
			builder.append(line);
		}
		
		return builder.toString();
	}
}
