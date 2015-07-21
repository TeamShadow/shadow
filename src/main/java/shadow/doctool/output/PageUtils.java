package shadow.doctool.output;

import java.util.List;

import shadow.doctool.DocumentationException;
import shadow.doctool.output.Html5Writer.Attribute;
import shadow.doctool.tag.TagManager.InlineTag;
import shadow.doctool.tag.TagManager.InlineTagType;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.Type;

public class PageUtils 
{
	public static void writeTableRow(Html5Writer out, boolean header,
			String ... columns) throws ShadowException, DocumentationException
	{
		out.openTab("tr");
		
		for (String column : columns) {
			if (header)
				out.fullLine("th", column);
			else
				out.fullLine("td", column);
		}
		
		out.closeUntab();
	}
	
	public static void writeInlineTags(List<InlineTag> inlineTags, Html5Writer out)
			throws DocumentationException, ShadowException
	{
		if (inlineTags.size() > 0) {
			out.open("p");
			for (InlineTag tag : inlineTags) {
				switch ((InlineTagType) tag.getType()) {
					case PLAIN_TEXT:
						out.add(tag.getArg(0));
						break;
					case CODE:
						out.full("code", tag.getArg(0), new Attribute("class", "inline"));
						break;
				}
			}
			out.closeLine();
		}
	}
	
	public static void writeLink(String href, String text, Html5Writer out) 
			throws DocumentationException, ShadowException
	{
		out.full("a", text, new Attribute("href", href));
	}
}
