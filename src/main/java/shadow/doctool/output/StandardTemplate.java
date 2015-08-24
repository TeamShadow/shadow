package shadow.doctool.output;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.cli.ParseException;

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
	private static final String CSS_FILE = "stylesheet.css";
	
	private final String timestamp;
	
	// Page directories for interlinking
	private final OverviewPage overviewPage;
	private final Map<Package, PackagePage> packagePages;
	private final Map<Type, ClassOrInterfacePage> typePages;
	
	// Directories for name-based lookups
	private final Map<String, PackagePage> pkgNameToPage;
	private final Map<String, ClassOrInterfacePage> typeNameToPage;
	
	// External arguments
	private final Map<String, String> arguments;
	
	public StandardTemplate(Map<String, String> args,
			Set<Type> typesToDocument, Set<Package> packagesToDocument)
			throws DocumentationException, ParseException
	{
		this.arguments = args;
		readArguments();
		
		timestamp = new SimpleDateFormat("MMM dd, yyyy 'at' HH:mm:ss z")
				.format(new Date());
		
		// Create all pages and the data structures for interlinking them
		
		overviewPage = new OverviewPage(this, packagesToDocument);
		packagePages = new HashMap<Package, PackagePage>();
		typePages = new HashMap<Type, ClassOrInterfacePage>();
		
		pkgNameToPage = new HashMap<String, PackagePage>();
		typeNameToPage = new HashMap<String, ClassOrInterfacePage>();
		
		for (Package current : packagesToDocument) {
			PackagePage page = new PackagePage(this, current, 
					packagesToDocument, typesToDocument);
			packagePages.put(current, page);
			pkgNameToPage.put(page.qualifiedName, page);
		}
		
		for (Type current : typesToDocument) {
			ClassOrInterfacePage page = new ClassOrInterfacePage(this, current, 
					typesToDocument);
			typePages.put(current, page);
			typeNameToPage.put(page.qualifiedName, page);
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
	
	public OverviewPage getOverviewPage()
	{
		return overviewPage;
	}
	
	public PackagePage getPackagePage(Package given)
	{
		return packagePages.get(given);
	}
	
	public PackagePage getPackagePage(String packageName)
	{
		return pkgNameToPage.get(packageName);
	}
	
	public ClassOrInterfacePage getTypePage(Type type)
	{
		return typePages.get(type);
	}
	
	public ClassOrInterfacePage getTypePage(String typeName)
	{
		return typeNameToPage.get(typeName);
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
	
	/** 
	 * Attempts to create a relative path to another page based on the
	 * qualified name of its associated package or type
	 */
	public Path linkByName(Page from, String toName)
	{
		Page to = null;
		if (toName.contains("@"))
			to = getTypePage(toName);
		else
			to = getPackagePage(toName);
		
		if (to != null)
			return linkToPage(from, to);
		else
			return null;
	}
	
	/** Creates a relative link to the master stylesheet */
	public static Path linkToCss(Page from)
	{
		Path toPath = Paths.get(File.separator + CSS_FILE);
		
		// For some reason, Path.relativize() will only work from the directory
		// above the start file
		Path fromPath = from.getRelativePath().getParent();
		if (fromPath == null)
			fromPath = Paths.get("/");
		else
			fromPath = Paths.get("/").resolve(fromPath);
		
		return fromPath.relativize(toPath);
	}
	
	private static void exportResource(Path targetFile, String resource)
			throws DocumentationException, IOException
	{
		InputStream input =
				DocumentationTool.class.getResourceAsStream(resource);
		if (input == null)
			throw new DocumentationException("Could not load \"" + resource 
					+ "\" from JAR");
		
		Files.deleteIfExists(targetFile);
		Files.copy(input, targetFile);
	}
	
	// TODO: Include the compiler version (no current mechanism)
	public void writeTimestampComment(HtmlWriter out) 
			throws DocumentationException, ShadowException
	{
		out.commentLine("Generated by Shadox on " + timestamp);
	}
	
	/* ARGUMENT HANDLING */
	
	public final static String ARG_TITLE		= "title";
	public final static String ARG_HELP 		= "help";
	
	private static final Set<String> validArgs =  buildArguments();
	
	private static Set<String> buildArguments()
	{
		Set<String> validArgs = new HashSet<String>();
		
		validArgs.add(ARG_TITLE);
		validArgs.add(ARG_HELP);
		
		return validArgs;
	}
	
	// Default values for arguments
	private String docTitle = "Documentation";
	
	/** Returns true if documentation can safely continue 
	 * @throws DocumentationException */
	private void readArguments() throws DocumentationException
	{
		for (String arg : arguments.keySet())
			if (!validArgs.contains(arg))
				throw new DocumentationException("Unrecognized option: \"" + arg + "\"");
		
		if (arguments.containsKey(ARG_TITLE))
			docTitle = arguments.get(ARG_TITLE);
	}
	
	public String getTitle()
	{
		return docTitle;
	}
	
	public static void printHelp() 
	{
		System.out.println("StandardTemplate: The default documentation template. Produces interlinked HTML");
		System.out.println("pages describing Shadow source files.");
		System.out.println("  help - Prints out usage information");
		System.out.println("  title - Descriptor to appear at the top of each page");
		// TODO: Implement
	}
}
