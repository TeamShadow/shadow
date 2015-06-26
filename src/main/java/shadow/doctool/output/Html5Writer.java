package shadow.doctool.output;

import java.io.Writer;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import shadow.doctool.DocumentationException;
import shadow.output.TabbedLineWriter;
import shadow.parser.javacc.ShadowException;

/** 
 * Provides some basic assistance in creating HTML-style documents. Constraints
 * are based upon the syntax defined within the W3C HTML5 Recommendation found
 * here: http://www.w3.org/TR/html5/syntax.html
 * 
 * Some arbitrary formatting choices have been made in the name of cleanliness
 */
public class Html5Writer
{
	/** A standard HTML attribute with its value in double quotes */
	public static class Attribute
	{
		private static final Pattern illegal = Pattern.compile("[\\p{Cc}|\0|\"|\'|>|/|=]");
		
		private final String name;
		private final String value;
		
		public Attribute(String name, String value) throws DocumentationException
		{
			Matcher matcher = illegal.matcher(name);
			if (matcher.find())
				throw new DocumentationException("Illegal character present in attribute name");
			
			this.name = name;
			this.value = processValue(value);
		}
		
		private static String processValue(String value)
		{
			StringBuilder builder = new StringBuilder();
			
			for (int i = 0; i < value.length(); ++i) {
				switch (value.charAt(i)) {
					case '"':
						builder.append("&quot;");
						break;
					case '&':
						builder.append("&amp;");
						break;
					default:
						builder.append(value.charAt(i));
				}
			}
			
			return builder.toString();
		}
		
		@Override
		public String toString()
		{
			return name + "=\"" + value + "\"";
		}
	}
	
	private TabbedLineWriter out;
	private Stack<String> openTags = new Stack<String>();
	
	public Html5Writer(Writer writer) 
			throws ShadowException, DocumentationException
	{
		out = new TabbedLineWriter(writer);
		writeDoctype("html");
	}
	
	/* Output methods */
	
	public void full(String tagName, String text, Attribute ... attributes) 
			throws DocumentationException, ShadowException
	{
		String tag = processTag(tagName);
		String content = processContent(text);
		
		out.writeNoLine("<" + tag);
		for (Attribute attribute : attributes)
			out.writeNoLine(" " + attribute);
		out.writeNoLine(">" + content + "</" + tag + ">");
	}
	
	public void fullLine(String tagName, String text, Attribute ... attributes) 
			throws DocumentationException, ShadowException
	{
		full(tagName, text, attributes);
		out.write();
	}
	
	public void voidTag(String tagName, Attribute ...attributes)
		throws DocumentationException, ShadowException
	{
		String tag = processTag(tagName);
		
		out.writeNoLine("<" + tag);
		for (Attribute attribute : attributes)
			out.writeNoLine(" " + attribute);
		out.writeNoLine('>');
	}
	
	public void voidLine(String tagName, Attribute ... attributes)
			throws DocumentationException, ShadowException
	{
		voidTag(tagName, attributes);
		out.write();
	}
	
	public void open(String tagName, Attribute ... attributes) 
			throws DocumentationException, ShadowException
	{
		String tag = processTag(tagName);
		
		out.writeNoLine("<" + tag);
		for (Attribute attribute : attributes)
			out.writeNoLine(" " + attribute);
		out.writeNoLine('>');
		
		openTags.push(tag);
	}
	
	public void openLine(String tagName, Attribute ... attributes) 
			throws DocumentationException, ShadowException
	{
		open(tagName, attributes);
		out.write();
	}
	
	public void openLineTab(String tagName, Attribute ... attributes) 
			throws DocumentationException, ShadowException
	{
		openLine(tagName, attributes);
		out.indent();
	}
	
	public void close()
			throws DocumentationException, ShadowException
	{
		if(openTags.isEmpty())
			throw new DocumentationException("Cannot perform close - no currently open tags");
		
		String tag = openTags.pop();
		out.writeNoLine("</" + tag + ">");
	}
	
	public void closeLine()
			throws DocumentationException, ShadowException
	{
		close();
		out.write();
	}
	
	public void closeLineUntab() 
			throws DocumentationException, ShadowException
	{
		// Just to make sure outdent() doesn't happen without close
		if(openTags.isEmpty())
			throw new DocumentationException("Cannot perform close - no currently open tags");
		
		out.outdent();
		closeLine();
	}

	public void add(String text) 
			throws DocumentationException, ShadowException
	{
		if(openTags.isEmpty())
			throw new DocumentationException("Cannot add content - no currently open tags");
		
		String content = processContent(text);
		out.writeNoLine(content);
	}
	
	public void addLine(String text) 
			throws DocumentationException, ShadowException
	{
		add(text);
		out.write();
	}
	
	public void commentLine(String text)
			throws DocumentationException, ShadowException
	{
		String content = processContent(text);
		out.write("<!-- " + content + "-->");
	}
	
	/* Proxy methods for convenience */
	
	public void full(String tagName, String text) 
			throws DocumentationException, ShadowException
	{
		full(tagName, text, new Attribute[0]);
	}
	
	public void full(String tagName) 
			throws DocumentationException, ShadowException
	{
		full(tagName, "", new Attribute[0]);
	}
	
	public void full(String tagName, Attribute ... attributes) 
			throws DocumentationException, ShadowException
	{
		full(tagName, "", attributes);
	}
	
	public void fullLine(String tagName, String text) 
			throws DocumentationException, ShadowException
	{
		fullLine(tagName, text, new Attribute[0]);
	}
	
	public void fullLine(String tagName) 
			throws DocumentationException, ShadowException
	{
		fullLine(tagName, "", new Attribute[0]);
	}
	
	public void fullLine(String tagName, Attribute ... attributes) 
			throws DocumentationException, ShadowException
	{
		fullLine(tagName, "", attributes);
	}
	
	public void voidTag(String tagName)
			throws DocumentationException, ShadowException
	{
		voidTag(tagName, new Attribute[0]);
	}
	
	public void voidLine(String tagName) 
			throws DocumentationException, ShadowException
	{
		voidLine(tagName, new Attribute[0]);
	}
	
	public void open(String tagName)
			throws DocumentationException, ShadowException
	{
		open(tagName, new Attribute[0]);
	}
	
	public void openLine(String tagName)
			throws DocumentationException, ShadowException
	{
		openLine(tagName, new Attribute[0]);
	}
	
	/* Helper methods */
	
	private void writeDoctype(String doctype)
			throws ShadowException, DocumentationException
	{
		out.write("<!DOCTYPE " + processTag(doctype) + ">");
	}
	
	/* Text processing methods and classes */
	
	/** Replaces illegal content characters with legal escape text */
	private static String processContent(String text)
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < text.length(); ++i) {
			switch (text.charAt(i)) {
				case '<':
					builder.append("&lt;");
					break;
				case '>': // Not strictly illegal, but arguably confusing
					builder.append("&gt;");
					break;
				case '&':
					builder.append("&amp;");
					break;
				default:
					builder.append(text.charAt(i));
			}
		}
		
		return builder.toString();
	}
	
	private static final Pattern illegalTag = Pattern.compile("[^\\w]");
	
	/** Replaces illegal content characters with legal escape text */
	private static String processTag(String name) throws DocumentationException
	{
		Matcher matcher = illegalTag.matcher(name);
		if (matcher.find())
			throw new DocumentationException("Non-alphabetic characters present in tag name");
		
		return name;
	}
}
