package shadow.doctool.tag;

import java.util.HashMap;
import java.util.Map;

import shadow.doctool.DocumentationException;
import shadow.doctool.tag.ParserManager.ArgDescriptionParser;
import shadow.doctool.tag.ParserManager.DelimitedParser;
import shadow.doctool.tag.ParserManager.TagParser;

public class TagManager 
{
	public interface TagType
	{
		public CapturedTag parse(String text) throws DocumentationException;
		public String getName();
	}
	
	public enum BlockTagType implements TagType
	{
		// Special tags (no name String, null parser)
		PLAIN_TEXT_BLOCK,
		INVALID_BLOCK_TAG,
		
		// Regular block tags
		PARAM("param", new ArgDescriptionParser(1, true)),
		THROWS("throws", new ArgDescriptionParser(1, true)),
		AUTHOR("author", new DelimitedParser(',')),
		
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
				return INVALID_BLOCK_TAG;
		}
		
		private String name = null;
		private TagParser parser = null; // No parser by default
		
		private BlockTagType() {}
		
		private BlockTagType(String name, TagParser parser)
		{
			this.name = name;
			this.parser = parser;
		}
		
		@Override
		public CapturedTag parse(String text) throws DocumentationException
		{
			if (parser == null)
				return new CapturedTag(this);
			
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getName()
		{
			return name;
		}
	}
	
	public enum InlineTagType implements TagType
	{
		// Special tags (no name String, null parser)
		PLAIN_TEXT_INLINE,
		INVALID_INLINE_TAG,
		
		// Regular inline tags
		SEE("see", new ArgDescriptionParser(1, false)),
		
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
				return INVALID_INLINE_TAG;
		}
		
		private String name = null;
		private TagParser parser = null; // No parser by default
		
		private InlineTagType() {}
		
		private InlineTagType(String name, TagParser parser)
		{
			this.parser = parser;
			this.name = name;
		}
		
		@Override
		public CapturedTag parse(String text) throws DocumentationException 
		{
			if (parser == null)
				return new CapturedTag(this);
			
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getName()
		{
			return name;
		}
	}
}
