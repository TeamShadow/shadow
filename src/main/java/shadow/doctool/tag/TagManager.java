package shadow.doctool.tag;

import java.util.HashMap;
import java.util.Map;

import shadow.doctool.DocumentationException;
import shadow.doctool.tag.ParserManager.ArgDescriptionParser;
import shadow.doctool.tag.ParserManager.DelimitedParser;
import shadow.doctool.tag.ParserManager.TagParser;

/** A collection of tags for use within documentation comments */
public class TagManager 
{
	public interface TagType
	{
		public CapturedTag parse(String text) throws DocumentationException;
		public String getName();
	}
	
	public enum BlockTagType implements TagType
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
		
		@Override
		public CapturedTag parse(String text) throws DocumentationException
		{
			if (parser == null)
				return new CapturedTag(this, text);
			
			return new CapturedTag(this, parser.parse(text));
		}
		
		@Override
		public String getName()
		{
			return name;
		}
	}
	
	public enum InlineTagType implements TagType
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
		
		@Override
		public CapturedTag parse(String text) throws DocumentationException 
		{
			if (parser == null)
				return new CapturedTag(this, text);
			
			return new CapturedTag(this, parser.parse(text));
		}
		
		@Override
		public String getName()
		{
			return name;
		}
	}
}
