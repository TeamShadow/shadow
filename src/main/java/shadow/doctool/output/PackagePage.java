package shadow.doctool.output;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import shadow.doctool.DocumentationException;
import shadow.doctool.output.HtmlWriter.Attribute;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.Package;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.EnumType;
import shadow.typecheck.type.ExceptionType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.Type;

public class PackagePage extends Page
{
	public static final String PAGE_NAME = "$package-summary";
	
	private final Package self;
	private final Path relativePath;
	public final String qualifiedName;
	private final Set<Package> linkablePackages;
	private final Set<Type> linkableTypes;
	
	private final List<ClassType> classes = new ArrayList<ClassType>();
	private final List<EnumType> enums = new ArrayList<EnumType>();
	private final List<ExceptionType> exceptions = new ArrayList<ExceptionType>();
	private final List<InterfaceType> interfaces = new ArrayList<InterfaceType>();
	private final List<SingletonType> singletons = new ArrayList<SingletonType>();
	
	public PackagePage(StandardTemplate master, Package self,
			Set<Package> linkablePackages, Set<Type> linkableTypes)
	{
		super(master);
		
		this.self = self;
		this.linkablePackages = linkablePackages;
		this.linkableTypes = linkableTypes;
		
		String qualifiedName = self.getQualifiedName();
		if (qualifiedName.isEmpty())
			qualifiedName = "default";
		this.qualifiedName = qualifiedName;
		
		sortTypes();
		
		relativePath = constructOutputPath().resolve(PAGE_NAME + EXTENSION);
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
		Path output = outputDirectory.resolve(PAGE_NAME + EXTENSION);
		FileWriter fileWriter = new FileWriter(output.toFile());
		HtmlWriter out = new HtmlWriter(fileWriter);
		
		out.openTab("html");
		writeHtmlHead(qualifiedName, out);
		out.openTab("body");
		
		writeNavBar(null, out);
		
		writeHeader(out);
		writeAllSummaries(out);
		
		out.closeUntab();
		out.closeUntab();
		
		fileWriter.close();
	}
	
	private void writeHeader(HtmlWriter out) 
			throws ShadowException, DocumentationException
	{	
		out.openTab("div", new Attribute("class", "header"));
		
		// Create a link to this package's parent
		String parentName = "";
		if (self.getParent() != null)
			parentName = self.getParent().getQualifiedName();
		if (!parentName.isEmpty()) {
			out.open("p");
			writeLink("../" + PAGE_NAME + EXTENSION, 
					parentName, out);
			out.closeLine();
		}
		
		out.fullLine("h2", "Package " + qualifiedName);
		
		out.closeUntab();
		out.voidLine("hr");
	}
	
	private void writeAllSummaries(HtmlWriter out) 
			throws ShadowException, DocumentationException
	{
		writeSummaryTable("Singleton", singletons, out);
		writeSummaryTable("Interface", interfaces, out);
		writeSummaryTable("Class", classes, out);
		writeSummaryTable("Enumeration", enums, out);
		writeSummaryTable("Exception", exceptions, out);
		writeSubpackageTable(out);
	}
	
	// TODO: Only list linkable types?
	private void writeSummaryTable(String typeKind, List<? extends Type> contents,
			HtmlWriter out) throws ShadowException, DocumentationException
	{
		if (!contents.isEmpty()) {
			out.openTab("div", new Attribute("class", "block"));		
			out.fullLine("h3", typeKind + " Summary");
			out.openTab("table", new Attribute("class", "summarytable"));
			
			writeTableRow(out, true, typeKind, "Description");
			for (Type type : contents) {
				out.openTab("tr");
					out.open("td");
						writeTypeName(type, out);
					out.closeLine();
					out.open("td");
						if (type.hasDocumentation())
							writeInlineTags(type.getDocumentation().getSummary(), out);
					out.closeLine();
				out.closeUntab();
			}
			
			out.closeUntab();
			out.closeUntab();
		}
	}
	
	// TODO: Make this only list "linkable" (explicitly requested for documentation) packages?
	private void writeSubpackageTable(HtmlWriter out) throws ShadowException, DocumentationException
	{
		Map<String, Package> children = self.getChildren();
		
		if (!children.isEmpty()) {
			out.openTab("div", new Attribute("class", "block"));		
			out.fullLine("h3", "Subpackage Summary");
			out.openTab("table", new Attribute("class", "summarytable"));
			
			writeTableRow(out, true, "Name", "Description");
			for (Package subpkg : children.values()) {
				out.openTab("tr");
					out.open("td");
						writePackageName(subpkg, out);
					out.closeLine();
					out.open("td");
						if (subpkg.hasDocumentation())
							writeInlineTags(subpkg.getDocumentation().getSummary(), out);
					out.closeLine();
				out.closeUntab();
			}
			
			out.closeUntab();
			out.closeUntab();
		}
	}
	
	public Path getRelativePath()
	{
		return relativePath;
	}
	
	/* Helper methods */

	/**
	 * Determines where this page should be output based on its package and
	 * a given root directory
	 */
	private Path constructOutputPath(Path root)
	{
		return root.resolve(constructOutputPath());
	}
	
	private Path constructOutputPath()
	{
		// Climb up the chain of packages
		ArrayDeque<String> packages = new ArrayDeque<String>();
		Package currentPackage = self;
		while (currentPackage != null && !currentPackage.getName().isEmpty()) {
			packages.addFirst(currentPackage.getName());
			currentPackage = currentPackage.getParent();
		}
		
		// Descend into corresponding directories
		Path outputPath = Paths.get("");
		for (String packageName : packages)
			outputPath = outputPath.resolve(packageName);
		
		return outputPath;
	}

	private void writeTypeName(Type type, HtmlWriter out) 
			throws DocumentationException, ShadowException
	{
		if (linkableTypes.contains(type))
			writeLink(type.getTypeName().replaceAll(":", "\\$")
					+ EXTENSION, type.getTypeName(), out);
		else
			out.add(type.getTypeName());
	}
	
	private void writePackageName(Package pkg, HtmlWriter out) 
			throws DocumentationException, ShadowException
	{
		if (linkablePackages.contains(pkg))
			writeLink(StandardTemplate.linkToPage(this,
					master.getPackagePage(pkg)).toString(), 
					pkg.getQualifiedName(), out);
		else
			out.add(pkg.getQualifiedName());
	}
}
