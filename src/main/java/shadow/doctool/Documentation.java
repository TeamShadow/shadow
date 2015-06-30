package shadow.doctool;

import java.util.HashMap;
import java.util.List;

import shadow.doctool.DirectiveParser.Directive;

public class Documentation 
{
	private final String mainText;
	private final String brief;
	private final HashMap<String, String> exceptionDescriptions;
	private final HashMap<String, String> parameterDescriptions;
	
	public Documentation(String mainText, String brief, List<Directive> directives)
	{
		this.mainText = mainText;
		this.brief = brief;
		parameterDescriptions = new HashMap<String, String>();
		exceptionDescriptions = new HashMap<String, String>();
		
		for (Directive directive : directives) {
			switch (directive.getType()) {
				case PARAM:
					parameterDescriptions.put(directive.getArgument(0),
							directive.getDescription());
					break;
				case THROWS:
					exceptionDescriptions.put(directive.getArgument(0),
							directive.getDescription());
				default: break;
			}
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
	
	/** 
	 * Returns the descriptive text - if any - associated with a given 
	 * parameter
	 */
	// TODO: Determine how this should handle null cases
	public String getParameterDescription(String parameterName)
	{
		return parameterDescriptions.get(parameterName);
	}
	
	/** 
	 * Returns the descriptive text - if any - associated with a given 
	 * exception
	 */
	// TODO: Determine how this should handle null cases
	public String getExceptionDescription(String exceptionName)
	{
		return exceptionDescriptions.get(exceptionName);
	}
}