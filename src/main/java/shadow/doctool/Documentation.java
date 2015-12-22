package shadow.doctool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Logger;

import shadow.Loggers;
import shadow.doctool.tag.TagManager.BlockTagType;
import shadow.doctool.tag.TagManager.InlineTag;
import shadow.doctool.tag.TagManager.InlineTagType;

/**
 * The processed contents of a documentation comment. Provides access to the
 * parsed contents of inline and block tags, alongside a brief summary of the
 * original comment.
 */
public class Documentation
{
	private static Logger logger = Loggers.DOC_TOOL;
	
	private final List<InlineTag> inlineTags;
	private final Map<BlockTagType, List<List<String>>> blockTags;
	private final List<InlineTag> summaryTags; // A subset of inlineTags
	
	private static final Pattern blockTagPattern = Pattern.compile("(^|\n|\r\n?)(@)(\\w*)");
	private static final Pattern inlineTagPattern = Pattern.compile("\\{@(\\w+)");
	private static final Pattern sentenceEnd = Pattern.compile("\\.($|\\s)");
	
	public Documentation(DocumentationBuilder builder) throws DocumentationException
	{
		inlineTags = new ArrayList<InlineTag>();
		blockTags = new HashMap<BlockTagType, List<List<String>>>();
		
		String text = builder.toString();
		parseInlineSection(text, inlineTags);
		parseBlockSection(text, blockTags);
		
		// Build the documentation summary out of inlineTags
		summaryTags = new ArrayList<InlineTag>();
		for (InlineTag tag : inlineTags) {
			if (tag.getType() == InlineTagType.PLAIN_TEXT) {
				String plain = tag.getArg(0);
				Matcher matcher = sentenceEnd.matcher(plain);
				if (matcher.find()) {
					plain = plain.substring(0, Math.min(matcher.start() + 1, plain.length())); // to keep the period
					if (!plain.isEmpty())
						summaryTags.add(InlineTagType.PLAIN_TEXT.build(plain));
					break; // Leave the loop once the end of the summary is found
				} else {
					summaryTags.add(tag);
				}
			} else {
				summaryTags.add(tag);
			}
		}
	}
	
	/** 
	 * Parses the inline tags and plain text content of a documentation
	 * comment, adding them to inlineTags. If it exists, the
	 * block tag section is returned as raw text
	 */
	public static void parseInlineSection(String text, List<InlineTag> inlineTags) throws DocumentationException
	{
		// Separate the inline/body section from any trailing block tags
		Matcher blockTagMatcher = blockTagPattern.matcher(text);
		if (blockTagMatcher.find())
			text = text.substring(0, blockTagMatcher.start());
		
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
				String plain = DocumentationBuilder.clean(text.substring(nextTagStart, tagMatcher.start()));
				if (!plain.isEmpty())
					inlineTags.add(InlineTagType.PLAIN_TEXT.build(plain));
				// Capture the discovered tag
				inlineTags.add(type.build(DocumentationBuilder.clean(
						text.substring(tagMatcher.end(), tagEnd).trim())));
				nextTagStart = tagEnd + 1;
			} else {
				logger.warn("Invalid inline tag\"" + tagMatcher.group(1) + "\", ignoring");
			}
		}
		
		// Capture any remaining text as a plain-text tag
		// First convert all whitespace to single spaces, then remove any trailing spaces
		// (a leading space may be part of a sentence, etc. and should be preserved)
		String leftover = DocumentationBuilder.clean(text.substring(nextTagStart));
		leftover = leftover.replaceFirst("\\s+$", "");
		if (!leftover.isEmpty())
			inlineTags.add(InlineTagType.PLAIN_TEXT.build(leftover));
	}
	
	/**
	 * Parses and stores all block tags that follow the body of a documentation 
	 * comment
	 */
	public static void parseBlockSection(String blockSection, Map<BlockTagType, List<List<String>>> blockTags) 
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
				addBlockTag(previousType, previousType.parse(previousBody), blockTags);
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
			addBlockTag(previousType, previousType.parse(previousBody), blockTags);
		}
	}
	
	/** Safely handles the addition of block tags to the central map */
	public static void addBlockTag(BlockTagType type, List<String> arguments, Map<BlockTagType, List<List<String>>> blockTags)
	{
		if (!blockTags.containsKey(type))
			blockTags.put(type, new ArrayList<List<String>>());
		
		blockTags.get(type).add(arguments);
	}
	
	public List<InlineTag> getInlineTags()
	{
		return Collections.unmodifiableList(inlineTags);
	}
	
	public List<InlineTag> getSummary()
	{
		return Collections.unmodifiableList(summaryTags);
	}
	
	public boolean hasBlockTags(BlockTagType type)
	{
		return blockTags.containsKey(type);
	}
	
	public List<List<String>> getBlockTags(BlockTagType type)
	{
		if (blockTags.containsKey(type))
			return Collections.unmodifiableList(blockTags.get(type));
		else
			return Collections.unmodifiableList(new ArrayList<List<String>>());
	}
	
	public void clear() {
		inlineTags.clear();
		blockTags.clear();
		summaryTags.clear();
	}
}
