package shadow.doctool.output;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Set;

import shadow.doctool.DocumentationException;
import shadow.doctool.output.Html5Writer.Attribute;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.Package;

public class PackagePage 
{
	private static final String extension = ".html";

	private final Package self;
	private final Set<Package> linkablePackages;
	private String qualifiedName;
	
	public PackagePage(Package self, Set<Package> linkablePackages)
	{
		this.self = self;
		this.linkablePackages = linkablePackages;
		
		qualifiedName = self.getQualifiedName();
		if (qualifiedName.isEmpty())
			qualifiedName = "default";
	}
	
	public void write(Path root) throws IOException, ShadowException, DocumentationException
	{
		// Find/create the directory chain where the document will reside
		Path outputDirectory = constructOutputPath(root);
		outputDirectory.toFile().mkdirs();
		
		// Begin writing to the document itself
		// Note: All colons in class names are replaced with dashes to avoid
		// browser issues (i.e. relative paths being interpreted as protocols)
		// TODO: Solve conflicts with inner classes name package:summary
		Path output = outputDirectory
				.resolve("package-summary" + extension);
		FileWriter fileWriter = new FileWriter(output.toFile());
		Html5Writer out = new Html5Writer(fileWriter);
		
		out.openTab("html");
		writeHtmlHead(out);
		out.openTab("body");
		
		writeHeader(out);
		
		out.closeUntab();
		out.closeUntab();
		
		fileWriter.close();
	}
	
	private void writeHtmlHead(Html5Writer out) throws ShadowException, DocumentationException
	{
		out.openTab("head");
		
		out.fullLine("title", qualifiedName);
		out.voidLine("link", new Attribute("rel", "stylesheet"),
				new Attribute("href", ClassOrInterfacePage.upDir(self.getDepth()) + "stylesheet.css"));
		
		out.closeUntab();
	}
	
	private void writeHeader(Html5Writer out) throws ShadowException, DocumentationException
	{
		out.openTab("div", new Attribute("class", "header"));
		
		out.fullLine("h2", "Package " + qualifiedName);
		
		out.closeUntab();
		out.voidLine("hr");
	}
	
	/* Helper methods */

	/**
	 * Determines where this page should be output based on its package and
	 * a given root directory
	 */
	private Path constructOutputPath(Path root)
	{
		ArrayDeque<String> packages = new ArrayDeque<String>();
		
		// Climb up the chain of packages
		Package currentPackage = self;
		while (currentPackage != null && !currentPackage.getName().isEmpty()) {
			packages.addFirst(currentPackage.getName());
			currentPackage = currentPackage.getParent();
		}
		
		// Descend into corresponding directories
		for (String packageName : packages)
			root = root.resolve(packageName);
		
		return root;
	}
}
