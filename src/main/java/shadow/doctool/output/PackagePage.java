package shadow.doctool.output;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import shadow.doctool.DocumentationException;
import shadow.doctool.output.Html5Writer.Attribute;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.Package;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.EnumType;
import shadow.typecheck.type.ExceptionType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.Type;

public class PackagePage 
{
	private static final String extension = ".html";

	private final Package self;
	private String qualifiedName;
	private final Set<Package> linkablePackages;
	private final Set<Type> linkableTypes;
	
	private final List<ClassType> classes = new ArrayList<ClassType>();
	private final List<EnumType> enums = new ArrayList<EnumType>();
	private final List<ExceptionType> exceptions = new ArrayList<ExceptionType>();
	private final List<InterfaceType> interfaces = new ArrayList<InterfaceType>();
	private final List<SingletonType> singletons = new ArrayList<SingletonType>();
	
	public PackagePage(Package self, Set<Package> linkablePackages,
			Set<Type> linkableTypes)
	{
		this.self = self;
		this.linkablePackages = linkablePackages;
		this.linkableTypes = linkableTypes;
		
		qualifiedName = self.getQualifiedName();
		if (qualifiedName.isEmpty())
			qualifiedName = "default";
		
		sortTypes();
	}
	
	private void sortTypes()
	{
		Collection<Type> types = self.getTypes();
		
		for (Type type : types) {
			if (type instanceof EnumType)
				enums.add((EnumType)type);
			else if (type instanceof ExceptionType)
				exceptions.add((ExceptionType)type);
			else if (type instanceof SingletonType)
				singletons.add((SingletonType)type);
			else if (type instanceof ClassType)
				classes.add((ClassType)type);
			else if (type instanceof InterfaceType)
				interfaces.add((InterfaceType)type);
		}
	}
	
	public void write(Path root) throws IOException, ShadowException, DocumentationException
	{
		// Find/create the directory chain where the document will reside
		Path outputDirectory = constructOutputPath(root);
		outputDirectory.toFile().mkdirs();
		
		// Begin writing to the document itself
		Path output = outputDirectory.resolve("package-summary" + extension);
		FileWriter fileWriter = new FileWriter(output.toFile());
		Html5Writer out = new Html5Writer(fileWriter);
		
		out.openTab("html");
		writeHtmlHead(out);
		out.openTab("body");
		
		writeHeader(out);
		writeAllSummaries(out);
		
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
	
	private void writeHeader(Html5Writer out) 
			throws ShadowException, DocumentationException
	{	
		out.openTab("div", new Attribute("class", "header"));
		
		
		// Create a link to this package's parent
		String parentName = "";
		if (self.getParent() != null)
			parentName = self.getParent().getQualifiedName();
		if (!parentName.isEmpty()) {
			out.open("p");
			PageUtils.writeLink("../package-summary" + extension, 
					parentName, out);
			out.closeLine();
		}
		
		out.fullLine("h2", "Package " + qualifiedName);
		
		out.closeUntab();
		out.voidLine("hr");
	}
	
	private void writeAllSummaries(Html5Writer out) 
			throws ShadowException, DocumentationException
	{
		writeSummaryTable("Singleton", singletons, out);
		writeSummaryTable("Interface", interfaces, out);
		writeSummaryTable("Class", classes, out);
		writeSummaryTable("Enumeration", enums, out);
		writeSummaryTable("Exception", exceptions, out);
	}
	
	private void writeSummaryTable(String typeKind, List<? extends Type> contents,
			Html5Writer out) throws ShadowException, DocumentationException
	{
		if (!contents.isEmpty()) {
			out.openTab("div", new Attribute("class", "block"));		
			out.fullLine("h3", typeKind + " Summary");
			out.openTab("table", new Attribute("class", "summarytable"));
			
			PageUtils.writeTableRow(out, true, typeKind, "Description");
			for (Type type : contents) {
				out.openTab("tr");
					out.open("td");
						writeTypeName(type, out);
					out.closeLine();
					out.open("td");
						PageUtils.writeInlineTags(type.getDocumentation().getSummary(), out);
					out.closeLine();
				out.closeUntab();
			}
			
			out.closeUntab();
			out.closeUntab();
		}
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

	private void writeTypeName(Type type, Html5Writer out) 
			throws DocumentationException, ShadowException
	{
		if (linkableTypes.contains(type))
			PageUtils.writeLink(type.getTypeName().replaceAll(":", "\\$")
					+ extension, type.getTypeName(), out);
		else
			out.add(type.getTypeName());
	}
}
