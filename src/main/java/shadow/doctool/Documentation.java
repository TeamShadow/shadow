package shadow.doctool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Logger;

import shadow.Loggers;
import shadow.doctool.DirectiveParser.Directive;
import shadow.doctool.tag.CapturedTag;
import shadow.doctool.tag.TagManager.BlockTagType;
import shadow.doctool.tag.TagManager.InlineTagType;

public class Documentation 
{
	private static Logger logger = Loggers.DOC_TOOL;
	
	// TODO: Remove the old constructor and make these members final
	private String mainText;
	private String brief;
	private List<CapturedTag> inlineTags;
	private Map<BlockTagType, CapturedTag> blockTags;
	
	private static final Pattern blockTagPattern = Pattern.compile("(^|\n|\r\n?)(@)(\\w*)");
	private static final Pattern inlineTagPattern = Pattern.compile("\\{@(\\w+)");
	
	public Documentation(String mainText, String brief, List<Directive> directives)
	{
		this.mainText = mainText;
		this.brief = brief;
	}
	
	public Documentation(DocumentationBuilder builder) throws DocumentationException
	{
		inlineTags = new ArrayList<CapturedTag>();
		blockTags = new HashMap<BlockTagType, CapturedTag>();
		
		String text = builder.toString();
		parseBody(text, inlineTags);
		parseBlockSection(text, blockTags);
	}
	
	/** 
	 * Parses the inline tags and plain text content of a documentation
	 * comment, automatically adding them to inlineTags. If it exists, the
	 * block tag section is returned as raw text
	 */
	public static void parseBody(String text, List<CapturedTag> inlineTags) 
			throws DocumentationException
	{
		// Separate the inline/body section from any trailing block tags
		Matcher blockTagMatcher = blockTagPattern.matcher(text);
		if (blockTagMatcher.find())
			text = text.substring(blockTagMatcher.start());
		
		Matcher tagMatcher = inlineTagPattern.matcher(text);
		int nextTagStart = 0;
		
		while (tagMatcher.find()) {
			// Check if the discovered tag is recognized
			InlineTagType type = InlineTagType.getType(tagMatcher.group(1));
			if (type != null) {
				// Find the end of the tag
				int tagEnd = text.indexOf('}', tagMatcher.end());
				if (tagEnd < 0)
					throw new DocumentationException("No closing bracket for tag \""
							+ tagMatcher.group(1) + "\"");
				// Capture everything before that tag as a plain-text tag
				String plain = text.substring(nextTagStart, tagMatcher.start()).trim();
				if (!plain.isEmpty())
					inlineTags.add(InlineTagType.PLAIN_TEXT_INLINE.parse(plain));
				// Capture the discovered tag
				inlineTags.add(type.parse(text.substring(tagMatcher.end(), tagEnd).trim()));
				nextTagStart = tagEnd + 1;
			} else {
				logger.warn("Invalid inline tag\"" + tagMatcher.group(1) + "\", ignoring");
			}
		}
		
		// Capture any remaining text as a plain-text tag
		String leftover = text.substring(nextTagStart).trim();
		if (!leftover.isEmpty())
			inlineTags.add(InlineTagType.PLAIN_TEXT_INLINE.parse(leftover));
	}
	
	public static void parseBlockSection(String blockSection, 
			Map<BlockTagType, CapturedTag> blockTags) 
			throws DocumentationException
	{
		blockSection = blockSection.trim();
		Matcher tagMatcher = blockTagPattern.matcher(blockSection);
		
		int previousTagStart = 0;
		BlockTagType previousType = null;
		while (tagMatcher.find()) {
			// Now that we know where the current tag starts, we know where the
			// previous tag ended
			if (previousType != null) {
				String previousBody = blockSection.substring(previousTagStart, 
						tagMatcher.start());
				blockTags.put(previousType, previousType.parse(previousBody));
			}
			// Record the relevant info about the current tag
			previousType = BlockTagType.getType(tagMatcher.group(3));
			previousTagStart = tagMatcher.end();
			if (previousType == null)
				logger.warn("Invalid block tag\"" + tagMatcher.group(3) + "\", ignoring");
		}
		
		// If necessary, capture the last tag found
		if (previousType != null) {
			String previousBody = blockSection.substring(previousTagStart);
			blockTags.put(previousType, previousType.parse(previousBody));
		}
	}
	
	/** 
	 * Returns the main text (excluding directives) of the original
	 * documentation comment 
	 */
	public String getMainText()
	{
		return mainText;
	}
	
	/** @return The first sentence of the main documentation text */
	public String getSummary()
	{
		return brief;
	}
}
