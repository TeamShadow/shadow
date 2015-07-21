package shadow.doctool.output;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import shadow.doctool.DocumentationException;
import shadow.doctool.output.Html5Writer.Attribute;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.Package;

/** Displays a list of all known packages and their descripitons */
public class OverviewPage
{
	public static String PAGE_NAME = "$overview";
	
	public static void write(Path root, Set<Package> knownPackages)
			throws DocumentationException, ShadowException, IOException
	{
		// Find/create the directory chain where the document will reside
		Path outputDirectory = root;
		outputDirectory.toFile().mkdirs();
		
		// Begin writing to the document itself
		Path output = outputDirectory.resolve(PAGE_NAME + PageUtils.EXTENSION);
		FileWriter fileWriter = new FileWriter(output.toFile());
		Html5Writer out = new Html5Writer(fileWriter);
		
		out.openTab("html");
		writeHtmlHead(out);
		out.openTab("body");
		
		writeHeader(out);
		writeTable(knownPackages, out);
		
		out.closeUntab();
		out.closeUntab();
		
		fileWriter.close();
	}
	
	private static void writeHtmlHead(Html5Writer out)
			throws ShadowException, DocumentationException
	{
		out.openTab("head");
		
		out.fullLine("title", "Overview");
		out.voidLine("link", new Attribute("rel", "stylesheet"),
				new Attribute("href", "stylesheet.css"));
		
		out.closeUntab();
	}
	
	private static void writeHeader(Html5Writer out) 
			throws ShadowException, DocumentationException
	{	
		out.openTab("div", new Attribute("class", "header"));
		
		out.fullLine("h2", "Overview");
		
		out.closeUntab();
		out.voidLine("hr");
	}
	
	private static void writeTable(Set<Package> knownPackages, 
			Html5Writer out) throws DocumentationException, ShadowException
	{
		if (!knownPackages.isEmpty()) {
			// List packages in alphabetical order, documenting only those that
			// have contents
			List<Package> fullList = new ArrayList<Package>(knownPackages);
			Collections.sort(fullList);
			List<Package> documentList = new ArrayList<Package>();
			for (Package current : fullList)
				if (!current.getTypes().isEmpty())
					documentList.add(current);
			
			out.openTab("div", new Attribute("class", "block"));		
			out.fullLine("h3", "Package Summary");
			out.openTab("table", new Attribute("class", "summarytable"));
			
			PageUtils.writeTableRow(out, true, "Package", "Description");
			for (Package current : documentList)
				writeTableEntry(current, knownPackages, out);
			
			out.closeUntab();
			out.closeUntab();
		}
	}
	
	private static void writeTableEntry(Package current, Set<Package> linkablePackages, 
			Html5Writer out) throws DocumentationException, ShadowException
	{
		out.openTab("tr");
			out.open("td");
				writePackageName(current, linkablePackages, out);
			out.closeLine();
			out.open("td");
				if (current.hasDocumentation())
					PageUtils.writeInlineTags(current.getDocumentation().getSummary(), out);
			out.closeLine();
		out.closeUntab();
	}
	
	private static void writePackageName(Package pkg, Set<Package> linkablePackages, 
			Html5Writer out) throws DocumentationException, ShadowException
	{
		String packageName = pkg.getQualifiedName();
		if (packageName.isEmpty())
			packageName = "default";
		
		if (linkablePackages.contains(pkg))
			if (pkg.getQualifiedName().isEmpty())
				PageUtils.writeLink(PackagePage.PAGE_NAME + PageUtils.EXTENSION,
						"default", out);
			else
				PageUtils.writeLink(pkg.getQualifiedName().replaceAll(":", "/") 
						+ "/" + PackagePage.PAGE_NAME + PageUtils.EXTENSION, 
						packageName, out);
		else
			out.add(packageName);
	}
}
