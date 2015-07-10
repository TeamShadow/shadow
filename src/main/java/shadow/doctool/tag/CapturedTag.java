package shadow.doctool.tag;

import java.util.ArrayList;
import java.util.List;

import shadow.doctool.tag.TagManager.TagType;

/** 
 * Contains the type and arguments from a specific invocation of a 
 * documentation tag
 */
public class CapturedTag
{
	// TODO: Consider adding verification of the number of arguments for a given type
	
	private final TagType type;
	private final List<String> arguments = new ArrayList<String>();
	
	public CapturedTag(TagType type, List<String> arguments)
	{
		this.type = type;
		this.arguments.addAll(arguments);
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
}
