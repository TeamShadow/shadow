package shadow.doctool.output;

import java.io.IOException;
import java.nio.file.Path;

import shadow.doctool.DocumentationException;
import shadow.doctool.output.HtmlWriter.Attribute;
import shadow.parser.javacc.ShadowException;

public abstract class Page 
{
	protected final StandardTemplate master;
	
	public Page(StandardTemplate master)
	{
		this.master = master;
	}
	
	public abstract Path getRelativePath();
	public abstract void write(Path root) throws IOException, ShadowException, DocumentationException;
	
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
			PageUtils.writeLink(StandardTemplate.linkToPage(this, 
					master.getOverviewPage()).toString(), "Overview", out);
		out.closeLine();
		
		out.open("li");
		if (this instanceof PackagePage)
			out.full("b", "Package");
		else if (this instanceof OverviewPage || packagePage == null)
			out.add("Package");
		else
			PageUtils.writeLink(StandardTemplate.linkToPage(this, 
					packagePage).toString(), "Package", out);
		out.closeLine();
		
		out.open("li");
		if (this instanceof ClassOrInterfacePage)
			out.full("b", "Type");
		else
			out.add("Type");
		out.closeLine();
		
		out.closeUntab();
		out.fullLine("span", StandardTemplate.docTitle);
		
		// Makes the outer div size itself properly
		out.fullLine("div", new Attribute("style", "clear:both;"));
		out.closeUntab();
	}
}
