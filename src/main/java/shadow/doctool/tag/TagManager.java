package shadow.doctool.tag;

import shadow.doctool.DocumentationException;
import shadow.doctool.tag.ParserManager.TagParser;

public class TagManager 
{
	public interface TagType
	{
		public CapturedTag parse(String text) throws DocumentationException;
	}
	
	public enum BlockTagType implements TagType
	{
		// Special tags (no name String, null parser)
		PLAIN_TEXT_BLOCK,
		INVALID_BLOCK_TAG,
		
		// Regular block tags
		PARAM,
		THROWS,
		
		/* End of tag list */ ;
		
		private TagParser parser = null; // No parser by default
		
		private BlockTagType() {}
		
		private BlockTagType(TagParser parser)
		{
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
	}
	
	public enum InlineTagType implements TagType
	{
		// Special tags (no name String, null parser)
		PLAIN_TEXT_INLINE,
		INVALID_INLINE_TAG,
		
		// Regular inline tags
		SEE,
		
		/* End of tag list */ ;
		
		private TagParser parser = null; // No parser by default
		
		private InlineTagType() {}
		
		private InlineTagType(TagParser parser)
		{
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
	}
}
