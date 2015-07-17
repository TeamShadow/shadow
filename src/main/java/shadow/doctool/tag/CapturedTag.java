package shadow.doctool.tag;

import java.util.ArrayList;
import java.util.List;

import shadow.doctool.tag.TagManager.TagType;

/** 
 * Contains the type and arguments from a specific invocation of a 
 * documentation tag
 */
// TODO: Split into a separate class for inline and block tags - the shared
// class seems more ambiguous than helpful
public class CapturedTag
{
	// TODO: Consider adding verification of the number of arguments for a given type
	
	private final TagType type;
	private final ArrayList<String> arguments = new ArrayList<String>();
	
	public CapturedTag(TagType type, List<String> arguments)
	{
		this.type = type;
		this.arguments.addAll(arguments);
	}
	
	public CapturedTag(TagType type, String text)
	{
		this.type = type;
		arguments.add(text);
	}
	
	public CapturedTag(TagType type)
	{
		this.type = type;
	}
	
	public TagType getType()
	{
		return type;
	}
	
	public String getArg(int index)
	{
		return arguments.get(index);
	}
	
	public List<String> getArgs()
	{
		return (List<String>) arguments.clone();
	}
	
	public int size()
	{
		return arguments.size();
	}
}
