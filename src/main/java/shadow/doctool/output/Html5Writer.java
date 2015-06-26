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
	/** The contents of an HTML element, containing only legal characters */
	public static class Element
	{
		private final String text;
		
		public Element(String text)
		{
			this.text = process(text);
		}
		
		/** Replaces illegal characters with legal escape text */
		private static String process(String text)
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
		
		@Override
		public String toString()
		{
			return text;
		}
	}
	
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
	
	public static class Tag
	{
		private static final Pattern illegal = Pattern.compile("[^A-Za-z]");
		
		private final String name;
		
		public Tag(String name) throws DocumentationException
		{
			Matcher matcher = illegal.matcher(name);
			if (matcher.find())
				throw new DocumentationException("Non-alphabetic characters present in tag name");
			
			this.name = name;
		}
		
		@Override
		public String toString()
		{
			return name;
		}
	}
	
	private TabbedLineWriter out;
	private Stack<Tag> openTags = new Stack<Tag>();
	private StringBuilder buffer = new StringBuilder();
	
	public Html5Writer(Writer writer) throws ShadowException
	{
		out = new TabbedLineWriter(writer);
		writeDoctype("html");
	}
	
	/** Opens a new tag, but does not append newlines or alter indentation */
	public void openTag(Tag tag, Attribute ...attributes)
	{
		openTags.push(tag);
		buffer = new StringBuilder();
		buffer.append("<" + tag);
		
		for (Attribute attribute : attributes)
			buffer.append(" " + attribute);
		
		buffer.append('>');
	}
	
	/** Adds textual content to the currently open tag */
	public void addContent(Element content) throws DocumentationException
	{
		if (openTags.isEmpty())
			throw new DocumentationException("No open tags present to write within");
		
		buffer.append(content);
	}
	
	/** Adds textual content to the currently open tag, followed by a newline */
	public void addContentLine(Element content)throws DocumentationException, ShadowException
	{
		if (openTags.isEmpty())
			throw new DocumentationException("No open tags present to write within");
		
		if (buffer.length() > 0) {
			buffer.append(content);
			out.write(buffer.toString());
			buffer = new StringBuilder();
		} else {
			out.write(content.toString());
		}
	}
	
	/** Closes the most recently opened tag, writing a newline */
	public void closeLastTag() throws DocumentationException, ShadowException
	{
		if (openTags.isEmpty())
			throw new DocumentationException("No closable tags present");
		
		Tag tag = openTags.pop();
		if (buffer.length() > 0) {
			out.write(buffer.toString() + "</" + tag + ">");
			buffer = new StringBuilder();
		} else {
			out.write("</" + tag + ">");
		}
	}
	
	/** 
	 * Creates an opening tag on its own line, followed by a newline.
	 * Indentation level is then incremented
	 */
	public void openBlockTag(Tag tag, Attribute ... attributes) throws ShadowException
	{
		StringBuilder builder = new StringBuilder();
		
		if (buffer.length() > 0) {
			out.write(buffer.toString());
			buffer = new StringBuilder();
		}
		
		builder.append("<" + tag);
		for (Attribute attribute : attributes)
			builder.append(" " + attribute);
		builder.append('>');
		
		out.write(builder.toString());
		out.indent();
		
		openTags.add(tag);
	}
	
	/** 
	 * Creates a closing tag on its own line, followed by a newline.
	 * Indentation level is then decremented
	 */
	public void closeBlockTag(String name) throws ShadowException
	{
		if (buffer.length() > 0) {
			out.write(buffer.toString());
			buffer = new StringBuilder();
		}
		
		out.write("</" + name + ">");
		out.outdent();
	}
	
	/** Creates a single, one-off tag which is considered closed */
	public void voidElement(Tag tag) throws ShadowException
	{
		buffer.append("<" + tag + ">");
	}
	
	/** Creates a void element on its own line */
	public void voidElementLine(Tag tag) throws ShadowException
	{
		if (buffer.length() > 0) {
			out.write(buffer.toString());
			buffer = new StringBuilder();
		}
		
		out.write("<" + tag + ">");
	}
	
	private void writeDoctype(String doctype) throws ShadowException
	{
		out.write("<!DOCTYPE " + doctype + ">");
	}
}
