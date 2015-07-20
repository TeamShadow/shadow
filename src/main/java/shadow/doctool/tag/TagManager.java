package shadow.doctool.tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shadow.doctool.DocumentationException;
import shadow.doctool.tag.ParserManager.ArgDescriptionParser;
import shadow.doctool.tag.ParserManager.DelimitedParser;
import shadow.doctool.tag.ParserManager.TagParser;

/** 
 * Contains all recognized tag types and their associated properties
 */
public class TagManager 
{
	public static class InlineTag
	{
		private final InlineTagType type;
		private final List<String> arguments;
		
		public InlineTag(InlineTagType type, List<String> arguments)
		{
			this.type = type;
			this.arguments = new ArrayList<String>(arguments);
		}
		
		public InlineTagType getType()
		{
			return type;
		}
		
		public List<String> getArgs()
		{
			return Collections.unmodifiableList(arguments);
		}
		
		public String getArg(int index)
		{
			return arguments.get(index);
		}
	}
	
	public enum BlockTagType
	{
		AUTHOR("author", new DelimitedParser(',')),
		PARAM("param", new ArgDescriptionParser(1, true)),
		THROWS("throws", new ArgDescriptionParser(1, true)),
		
		/* End of tag list */ ;
		
		public static final Map<String, BlockTagType> tagNames;
		static
		{
			tagNames = new HashMap<String, BlockTagType>();
			for (BlockTagType type : BlockTagType.values())
				tagNames.put(type.getName(), type);
		}
		
		public static BlockTagType getType(String name)
		{
			if (tagNames.containsKey(name))
				return tagNames.get(name);
			else
				return null;
		}
		
		private String name = null;
		private TagParser parser = null; // No parser by default
		
		private BlockTagType() {}
		
		private BlockTagType(String name)
		{
			this.name = name;
		}
		
		private BlockTagType(String name, TagParser parser)
		{
			this.name = name;
			this.parser = parser;
		}
		
		public List<String> parse(String text) throws DocumentationException
		{
			if (parser == null) {
				List<String> list = new ArrayList<String>();
				list.add(text);
				return list;
			}
			else {
				return parser.parse(text);
			}
		}
		
		public String getName()
		{
			return name;
		}
	}
	
	public enum InlineTagType
	{
		// This is a special tag representing normal text
		PLAIN_TEXT,
		
		// Regular inline tags
		CODE("code"),
		
		/* End of tag list */ ;
		
		public static final Map<String, InlineTagType> tagNames;
		static
		{
			tagNames = new HashMap<String, InlineTagType>();
			for (InlineTagType type : InlineTagType.values())
				tagNames.put(type.getName(), type);
		}
		
		public static InlineTagType getType(String name)
		{
			if (tagNames.containsKey(name))
				return tagNames.get(name);
			else
				return null;
		}
		
		private String name = null;
		private TagParser parser = null; // No parser by default
		
		private InlineTagType() {}
		
		private InlineTagType(String name)
		{
			this.name = name;
		}
		
		private InlineTagType(String name, TagParser parser)
		{
			this.parser = parser;
			this.name = name;
		}
		
		public List<String> parse(String text) throws DocumentationException 
		{
			if (parser == null) {
				List<String> list = new ArrayList<String>();
				list.add(text);
				return list;
			}
			else {
				return parser.parse(text);
			}
		}
		
		public InlineTag build(String text) throws DocumentationException
		{
			return new InlineTag(this, parse(text));
		}
		
		public String getName()
		{
			return name;
		}
	}
}
