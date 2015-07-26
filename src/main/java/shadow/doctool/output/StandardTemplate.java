package shadow.doctool.output;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import shadow.doctool.DocumentationException;
import shadow.doctool.DocumentationTool;
import shadow.doctool.output.HtmlWriter.Attribute;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.Package;
import shadow.typecheck.type.Type;

/** 
 * A documentation template which generates HTML pages describing packages,
 * classes, and interfaces. This class is the stock template intended to ship
 * with Shadox.
 * 
 * @author Brian Stottler
 */
public class StandardTemplate implements DocumentationTemplate
{
	private final OverviewPage overviewPage;
	private final Map<Package, PackagePage> packagePages;
	private final Map<Type, ClassOrInterfacePage> typePages;
	
	public StandardTemplate(Set<Type> typesToDocument, Set<Package> packagesToDocument) 
			throws DocumentationException
	{
		overviewPage = new OverviewPage(this, packagesToDocument);
		packagePages = new HashMap<Package, PackagePage>();
		typePages = new HashMap<Type, ClassOrInterfacePage>();
		
		for (Package current : packagesToDocument) {
			PackagePage page = new PackagePage(this, current, 
					packagesToDocument, typesToDocument);
			packagePages.put(current, page);
		}
		
		for (Type current : typesToDocument) {
			ClassOrInterfacePage page = new ClassOrInterfacePage(this, current, 
					typesToDocument);
			typePages.put(current, page);
		}
	}
	
	public void write(Path outputDirectory) 
			throws IOException, ShadowException, DocumentationException
	{
		overviewPage.write(outputDirectory);
		
		for (PackagePage page : packagePages.values())
			page.write(outputDirectory);
		
		for (ClassOrInterfacePage page : typePages.values())
			page.write(outputDirectory);
		
		// Export the style-sheet
		exportResource(outputDirectory.resolve("stylesheet.css"), 
				"/doctool/stylesheet.css");
	}
	
	public PackagePage getPackagePage(Package given)
	{
		return packagePages.get(given);
	}
	
	public OverviewPage getOverviewPage()
	{
		return overviewPage;
	}
	
	public ClassOrInterfacePage getTypePage(Type type)
	{
		return typePages.get(type);
	}
	
	/** 
	 * Creates a navbar for use at the top of a documentation page. Fields
	 * that are not relevant for a given page should be left as null.
	 */
	public static void writeNavBar(Page current, Page overview, 
			Page packagePage, Page type, HtmlWriter out) 
			throws ShadowException, DocumentationException
	{
		out.openTab("div", new Attribute("class", "navlist"));
		out.openTab("ul");
		
		out.open("li");
		if (overview != null)
			PageUtils.writeLink(linkToPage(current, overview).toString(), "Overview", out);
		else
			out.add("Overview");
		out.closeLine();
		
		out.open("li");
		if (packagePage != null)
			PageUtils.writeLink(linkToPage(current, packagePage).toString(), "Package", out);
		else
			out.add("Package");
		out.closeLine();
		
		out.open("li");
		if (type != null)
			PageUtils.writeLink(linkToPage(current, type).toString(), "Type", out);
		else
			out.add("Type");
		out.closeLine();
		
		out.closeUntab();
		out.closeUntab();
	}
	
	/** Creates a relative path from one Page to another */
	public static Path linkToPage(Page from, Page to)
	{
		Path toPath = Paths.get("/").resolve(to.getRelativePath());
		
		// For some reason, Path.relativize() will only work from the directory
		// above the start file
		Path fromPath = from.getRelativePath().getParent();
		if (fromPath == null)
			fromPath = Paths.get("/");
		else
			fromPath = Paths.get("/").resolve(fromPath);
		
		return fromPath.relativize(toPath);
	}
	
	private static void exportResource(Path targetFile, String resource) throws DocumentationException, IOException
	{
		InputStream input = DocumentationTool.class.getResourceAsStream(resource);
		if (input == null)
			throw new DocumentationException("Could not load \"" + resource 
					+ "\" from JAR");
		
		Files.deleteIfExists(targetFile);
		Files.copy(input, targetFile);
	}
}
