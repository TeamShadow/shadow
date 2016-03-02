package shadow.doctool.output;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import shadow.Loggers;
import shadow.doctool.Documentation;
import shadow.doctool.DocumentationException;
import shadow.doctool.output.HtmlWriter.Attribute;
import shadow.doctool.tag.TagManager.BlockTagType;
import shadow.doctool.tag.TagManager.InlineTag;
import shadow.doctool.tag.TagManager.InlineTagType;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.Package;
import shadow.typecheck.type.Type;

public abstract class Page 
{
	protected final Logger logger = Loggers.DOC_TOOL;
	
	// Interface to which all Pages must conform
	public abstract Path getRelativePath();
	public abstract void write(Path root) throws IOException, ShadowException, DocumentationException;
	
	protected static final String EXTENSION = ".html";
	protected final StandardTemplate master;
	
	public Page(StandardTemplate master)
	{
		this.master = master;
	}
	
	/* Helper methods */
	
	protected void writeHtmlHead(String title, HtmlWriter out) 
			throws ShadowException, DocumentationException
	{
		master.writeTimestampComment(out);
		out.openTab("head");
		
		out.fullLine("title", title);
		out.voidLine("link", new Attribute("rel", "stylesheet"),
				new Attribute("href", StandardTemplate.linkToCss(this)
						.toString()));
		
		out.closeUntab();
	}
	
	/** 
	 * Creates a navbar for use at the top of a documentation page. Fields
	 * that are not relevant for a given page should be left as null.
	 */
	public void writeNavBar(PackagePage packagePage, HtmlWriter out) 
			throws DocumentationException, ShadowException
	{
		out.openTab("div", new Attribute("class", "navlist"));
		out.openTab("ul");
		
		out.open("li");
		if (this instanceof OverviewPage)
			out.full("b", "Overview");
		else
			writeLink(StandardTemplate.linkToPage(this, 
					master.getOverviewPage()).toString(), "Overview", out);
		out.closeLine();
		
		out.open("li");
		if (this instanceof PackagePage)
			out.full("b", "Package");
		else if (this instanceof OverviewPage || packagePage == null)
			out.add("Package");
		else
			writeLink(StandardTemplate.linkToPage(this, 
					packagePage).toString(), "Package", out);
		out.closeLine();
		
		out.open("li");
		if (this instanceof ClassOrInterfacePage)
			out.full("b", "Type");
		else
			out.add("Type");
		out.closeLine();
		
		out.closeUntab();
		out.fullLine("span", master.getTitle());
		
		// Makes the outer div size itself properly
		out.fullLine("div", new Attribute("style", "clear:both;"));
		out.closeUntab();
	}
	
	/** 
	 * All inline tags should be available in any context (i.e. for all pages
	 * and all types)
	 */
	protected final void writeInlineTags(List<InlineTag> inlineTags,
			HtmlWriter out) throws DocumentationException, ShadowException
	{
		
		if (inlineTags.size() > 0) {
			out.open("p");
			for (InlineTag tag : inlineTags) {
				switch ((InlineTagType) tag.getType()) {
					case CODE:
						out.full("code", tag.getArg(0));
						break;
					case BOLD:
						out.full("b", tag.getArg(0));
						break;
					case ITALICS:
						out.full("i", tag.getArg(0));
						break;
					case LINK_DOC:
						Path link = master.linkByName(this, tag.getArg(0));
						if (link != null) {
							out.full("a", tag.getArg(1), new Attribute("href", 
									link.toString()));
						} else {
							logger.warn("On page " + getRelativePath() + " - "
									+ "Could not link to type or package " +
									tag.getArg(0));
							out.add(tag.getArg(1));
						}
						break;
					case LINK_URL:
						out.full("a", tag.getArg(1), new Attribute("href", tag.getArg(0)));
						break;
					case PLAIN_TEXT:
						out.add(tag.getArg(0));
						break;
				}
			}
			out.closeLine();
		}
	}
	
	protected static void writeTableRow(HtmlWriter out, boolean header,
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
	
	protected static void writeLink(String href, String text, Attribute attribute, HtmlWriter out) 
			throws DocumentationException, ShadowException
	{
		out.full("a", text, new Attribute("href", href), attribute);
	}
	
	protected static void writeLink(String href, String text, HtmlWriter out) 
			throws DocumentationException, ShadowException
	{
		out.full("a", text, new Attribute("href", href));
	}
	
	protected void writeUniversalBlockTags(Documentation documentation,
			HtmlWriter out) throws DocumentationException, ShadowException
	{
		if (documentation.hasBlockTags(BlockTagType.AUTHOR))
			writeAuthorSection(documentation.getBlockTags(BlockTagType.AUTHOR), out);
		if (documentation.hasBlockTags(BlockTagType.SEE_DOC) 
				|| documentation.hasBlockTags(BlockTagType.SEE_URL))
			writeSeeSection(documentation.getBlockTags(BlockTagType.SEE_DOC),
					documentation.getBlockTags(BlockTagType.SEE_URL), out);
	}
	
	protected void writeAuthorSection(List<List<String>> authorTags, 
			HtmlWriter out) throws DocumentationException, ShadowException
	{
		if (authorTags.size() > 0) {
			out.fullLine("h5", "Authors");
			out.openTab("div", new Attribute("class", "blocktagcontent"));
			for (List<String> tag : authorTags) {
				for (String author : tag) {					
					List<InlineTag> inlineTags = new ArrayList<InlineTag>();
					Documentation.parseInlineSection(author, inlineTags);
					writeInlineTags(inlineTags, out);					
				}
			}
			out.closeUntab();
		}
	}
	
	protected void writeSeeSection(List<List<String>> seeDocTags, 
			List<List<String>> seeUrlTags, HtmlWriter out) 
			throws DocumentationException, ShadowException
	{
		if (seeUrlTags.size() > 0 || seeDocTags.size() > 0) {
			out.fullLine("h5", "See Also");
			out.openTab("div", new Attribute("class", "blocktagcontent"));
			for (List<String> tag : seeDocTags) {
				out.open("p");
				Path link = master.linkByName(this, tag.get(0));
				if (link != null) {
					if( tag.size() > 1 )
						out.full("a", tag.get(1), new Attribute("href", 
							link.toString()));
					else
						out.full("a", tag.get(0), new Attribute("href", 
								link.toString()));						
				} else {
					logger.warn("On page " + getRelativePath() + " - "
							+ "Could not link to type or package " +
							tag.get(0));
					if( tag.size() > 1 )
						out.add(tag.get(1));
				}
				out.closeLine();
			}
			for (List<String> tag : seeUrlTags) {
				out.open("p");
				if( tag.size() > 1 )
					out.full("a", tag.get(1), new Attribute("href", tag.get(0)));
				else
					out.full("a", tag.get(0), new Attribute("href", tag.get(0)));
				out.closeLine();
			}
			out.closeUntab();
		}
	}

	protected static String getRelativePath(Package from, Package to, String file)
	{
		Path start = Paths.get(from.getPath());
		Path target = Paths.get(to.getPath());
		
		Path result = start.relativize(target);
		
		return result.resolve(file).toString();
	}
	
	protected static String getRelativePath(Package from, Package to)
	{
		return getRelativePath(from, to, PackagePage.PAGE_NAME + EXTENSION);
	}
	
	protected static String getRelativePath(Package from, Type to)
	{
		return getRelativePath(from, to.getPackage(), to.toString(Type.NO_OPTIONS).replaceAll(":", "\\$") + EXTENSION);
	}
	
	protected static String getRelativePath(Type from, Package to)
	{
		return getRelativePath(from.getPackage(), to, PackagePage.PAGE_NAME + EXTENSION);
	}
	
	protected static String getRelativePath(Type from, Type to)
	{
		return getRelativePath(from.getPackage(), to.getPackage(), to.toString(Type.NO_OPTIONS).replaceAll(":", "\\$") + EXTENSION);
	}
}