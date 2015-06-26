package shadow.doctool;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Logger;

import shadow.Loggers;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.Documentation;

/** Used to parse the '@' directives in documentation comments */
public class DirectiveParser 
{
	private static Logger logger = Loggers.DOC_TOOL;
	
	/** 
	 * Contains the information needed to parse all directives. If support must
	 * be added for additional directives, this is the place to make changes
	 */
	public enum DirectiveType
	{
		// Regular, user-placed directives
		PARAM(1, true),
		THROWS(1, true),
		
		// Special directives
		MAIN_CONTENT(0, false), // This is a "hidden" directive for main comment text
		INVALID_DIRECTIVE(0, false);
		
		// Text representations
		private static final String textParam = 		"param";
		private static final String textThrows =		"throws";
		
		private final int arguments;
		private final boolean endsWithDescription;
		
		private DirectiveType(int arguments, boolean endsWithDescription)
		{
			this.arguments = arguments;
			this.endsWithDescription = endsWithDescription;
		}
		
		public int getArgumentCount()
		{
			return arguments;
		}
		
		public boolean endsWithDescription()
		{
			return endsWithDescription;
		}
		
		/** Associates the text name of a parameter with its enum element */
		private static DirectiveType parse(String name)
		{
			switch (name) {
				case textParam: return DirectiveType.PARAM;
				case textThrows: return DirectiveType.THROWS;
				default: return DirectiveType.INVALID_DIRECTIVE;
			}
		}
		
		@Override
		public String toString()
		{
			switch (this) {
				case PARAM: return textParam;
				case THROWS: return textThrows;
				
				case MAIN_CONTENT: return null;
				case INVALID_DIRECTIVE: return null;
				default: return null;
			}
		}
	}
	
	/** Processes the directives in the documentation */
	public static ProcessedDocumentation process(Documentation documentation) throws ShadowException
	{
		String fullText = documentation.toString();
		List<String> contents = new ArrayList<String>();
		List<DirectiveType> directiveTypes = new ArrayList<DirectiveType>();
		splitOnDirectives(fullText, contents, directiveTypes);
		
		List<Directive> directives = new ArrayList<Directive>();
		String mainText = parseDirectives(directives, contents, directiveTypes);
		
		String brief = getFirstSentence(mainText);
		
		return new ProcessedDocumentation(mainText, brief, directives);
	}
	
	private static final Pattern sentenceEnd = Pattern.compile("\\.\\s");
	
	private static String getFirstSentence(String mainText) 
	{
		Matcher matcher = sentenceEnd.matcher(mainText);
		
		if (matcher.find())
			return mainText.substring(0, matcher.start() - 1);
		else
			return mainText;
	}

	/** 
	 * A generic directive wrapper, containing its type and any arguments or
	 * description it may have
	 */
	public static class Directive
	{
		private final DirectiveType type;
		private String[] arguments;
		private String description;
		
		public Directive(DirectiveType type)
		{
			this.type = type;
			arguments = new String[type.getArgumentCount()];
			description = "";
		}
		
		public void setArgument(int index, String argument)
		{
			arguments[index] = argument;
		}
		
		public String getArgument(int index)
		{
			return arguments[index];
		}
		
		public void setDescription(String description)
		{
			this.description = description;
		}
		
		public String getDescription()
		{
			return description;
		}
		
		public DirectiveType getType()
		{
			return type;
		}
	}
	
	// Regex for capturing a directive following a newline. This relies on all
	// non-newline whitespace having been removed
	private static Pattern directivePattern = Pattern.compile("(^|\n|\r\n?)(@)(\\w*)");
	
	/** 
	 * Splits a String along each occurrence of a valid directive. If the String
	 * does not begin with a directive, the first section is assumed to be of
	 * MAIN_CONTENT type.
	 * 
	 * @param 	contents 		A list used to return the text following each
	 * 							directive
	 * @param 	directiveTypes	A list used to return the type of directive 
	 * 							associated with each String in contents
	 */
	private static void splitOnDirectives(String fullText, 
			List<String> contents, List<DirectiveType> directiveTypes)
	{
		contents.clear();
		directiveTypes.clear();
		
		// Deal with the first "chunk"
		contents.add(fullText);
		directiveTypes.add(DirectiveType.MAIN_CONTENT);
		Matcher matcher = directivePattern.matcher(fullText);
		
		// Perform the splitting
		int i = 0;
		while (matcher.find()) {
			if (DirectiveType.parse(matcher.group(3)) != DirectiveType.INVALID_DIRECTIVE) {
				int directiveStart = matcher.start(2);
				
				// Upon splitting, move the latter piece to a new position in
				// the list
				String toSplit = contents.get(i);
				contents.set(i, toSplit.substring(0, directiveStart));
				contents.add(toSplit.substring(matcher.end()));
				directiveTypes.add(DirectiveType.parse(matcher.group(3)));
				
				// Match only against the newly generated piece
				i++;
				matcher = directivePattern.matcher(contents.get(i));
			} else {
				logger.warn("Unrecognized directive @" + matcher.group(3) + ", ignoring");
			}
		}
	}
	
	// Regex for capturing single-word arguments
	private static Pattern argumentPattern = Pattern.compile("\\w+");
	
	/** 
	 * Takes the resulting lists from splitOnDirectives() and parses the text
	 * associated with each directive for it's arguments and option description
	 * 
	 * @param directives 		An empty list used to return the finalized
	 * 							Directives
	 * @param text 				A list of Strings generated by 
	 * 							splitOnDirectives(), containing the text that 
	 * 							followed each associated directive
	 * @param directiveTypes	A list of DirectiveTypes generated by
	 * 						 	splitOnDirectives()
	 * 
	 * @return A String containing the main (non-directive-related) content of
	 * 			the original documentation comment
	 */
	private static String parseDirectives(List<Directive> directives, 
			List<String> text, List<DirectiveType> directiveTypes) 
			throws ShadowException
	{
		StringBuilder mainContent = new StringBuilder();
		
		// Handle all parsing for each previously-captured directive
		for (int i = 0; i < text.size(); ++i) {
			Matcher matcher = argumentPattern.matcher(text.get(i));
			DirectiveType currentType = directiveTypes.get(i);
			Directive currentDirective = new Directive(currentType);
			int endOfLastArgument = 0;
			
			// Capture each expected argument
			for (int j = 0; j < currentType.getArgumentCount(); ++j) {
				if (matcher.find()) {
					currentDirective.setArgument(j, matcher.group());
					endOfLastArgument = matcher.end();
				} else {
					throw new ShadowException("Expected " 
							+ currentType.getArgumentCount()
							+ " arguments for directive @"
							+ currentType.toString()
							+ ", found only " + j);
				}
			}
			
			// Retrieve and clean up the description
			String leftoverText = text.get(i).substring(endOfLastArgument).trim();
			leftoverText = leftoverText.replaceAll("\\n|\\r\\n?", " ");
			leftoverText.replaceAll("\\s+", " ");
			
			// Capture a description if desired. It is acceptable for the
			// description to be an empty String
			if (currentType.endsWithDescription())
				currentDirective.setDescription(leftoverText);
			else
				mainContent.append(leftoverText);
			
			if (currentType != DirectiveType.MAIN_CONTENT)
				directives.add(currentDirective);
		}
		
		return mainContent.toString();
	}
}
