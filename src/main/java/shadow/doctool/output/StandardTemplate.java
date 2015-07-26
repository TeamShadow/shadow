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
		overviewPage = new OverviewPage(packagesToDocument);
		packagePages = new HashMap<Package, PackagePage>();
		typePages = new HashMap<Type, ClassOrInterfacePage>();
		
		for (Package current : packagesToDocument) {
			PackagePage page = new PackagePage(current, packagesToDocument, typesToDocument);
			packagePages.put(current, page);
		}
		
		for (Type current : typesToDocument) {
			ClassOrInterfacePage page = new ClassOrInterfacePage(current, typesToDocument);
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
	
	/** 
	 * Creates a navbar for use at the top of a documentation page. Fields
	 * that are not relevant for a given page should be left as null.
	 */
	public static void writeNavBar(Page overview, Page packagePage, Page type)
			throws ShadowException, DocumentationException
	{
		// TODO: Implement
	}
	
	/** Creates a relative path from one Page to another */
	public static Path linkToPage(Page to, Page from)
	{
		Path toPath = Paths.get("/").resolve(to.getRelativePath());
		Path fromPath = Paths.get("/").resolve(from.getRelativePath());
		
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
