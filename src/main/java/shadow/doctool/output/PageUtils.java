package shadow.doctool.output;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import shadow.doctool.DocumentationException;
import shadow.doctool.output.Html5Writer.Attribute;
import shadow.doctool.tag.TagManager.InlineTag;
import shadow.doctool.tag.TagManager.InlineTagType;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.Type;
import shadow.typecheck.Package;

public class PageUtils 
{
	public static String EXTENSION = ".html";
	
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

	public static String getRelativePath(Package from, Package to, String file)
	{
		Path start = Paths.get(from.getPath());
		Path target = Paths.get(to.getPath());
		
		Path result = start.relativize(target);
		
		return result.resolve(file).toString();
	}
	
	public static String getRelativePath(Package from, Package to)
	{
		return getRelativePath(from, to, PackagePage.PAGE_NAME + EXTENSION);
	}
	
	public static String getRelativePath(Package from, Type to)
	{
		return getRelativePath(from, to.getPackage(), to.getTypeName() + EXTENSION);
	}
	
	public static String getRelativePath(Type from, Package to)
	{
		return getRelativePath(from.getPackage(), to, PackagePage.PAGE_NAME + EXTENSION);
	}
	
	public static String getRelativePath(Type from, Type to)
	{
		return getRelativePath(from.getPackage(), to.getPackage(), to.getTypeName() + EXTENSION);
	}
}
