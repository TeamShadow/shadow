package shadow.doctool.output;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
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
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.Type;

public class ClassOrInterfacePage
{
	private static final String extension = ".html";
	
	private Type type;
	private String typeKind;
	private Set<Type> linkableTypes;
	private boolean makeLinks = true;
	private int packageDepth = 0;
	
	
	private List<MethodSignature> constructors = new ArrayList<MethodSignature>();
	private List<MethodSignature> destructors = new ArrayList<MethodSignature>();
	private List<MethodSignature> methods = new ArrayList<MethodSignature>();
	private List<MethodSignature> properties = new ArrayList<MethodSignature>();
	
	public ClassOrInterfacePage(Type type, Set<Type> linkableTypes) throws DocumentationException
	{
		if (type instanceof ClassType)
			typeKind = "Class";
		else if (type instanceof EnumType)
			typeKind = "Enum";
		else if (type instanceof ExceptionType)
			typeKind = "Exception";
		else if (type instanceof InterfaceType)
			typeKind = "Interface";
		else if (type instanceof SingletonType)
			typeKind = "Singleton";
		else
			throw new DocumentationException("Unexpected type: " + type.getQualifiedName());
		
		this.type = type;
		this.linkableTypes = linkableTypes;
		
		fillMethodLists();
	}
	
	private void fillMethodLists()
	{
		for (List<MethodSignature> overloadList : type.getMethodMap().values()) {
			for (MethodSignature method : overloadList) {
				Modifiers modifiers = method.getModifiers();
				if (modifiers.hasModifier(Modifiers.GET) 
						|| modifiers.hasModifier(Modifiers.SET))
					properties.add(method);
				else if (method.getSymbol() == "create")
					constructors.add(method);
				else if (method.getSymbol() == "destroy")
					destructors.add(method);
				else
					methods.add(method);
			}
		}
	}
	
	public void write(Path root) throws IOException, ShadowException, DocumentationException
	{
		// Find/create the directory chain where the document will reside
		Path outputDirectory = constructOutputPath(root);
		outputDirectory.toFile().mkdirs();
		
		// Begin writing to the document itself
		Path output = outputDirectory.resolve(type.getTypeName() + extension);
		FileWriter fileWriter = new FileWriter(output.toFile());
		Html5Writer out = new Html5Writer(fileWriter);
		
		out.openTab("html");
		writeHtmlHead(out);
		out.openTab("body");
		
		writeHeader(out);
		writeAllMethodTables(out);
		writeAllMethodDetails(out);
		
		out.closeUntab();
		out.closeUntab();
		
		fileWriter.close();
	}

	private void writeHtmlHead(Html5Writer out) throws ShadowException, DocumentationException
	{
		out.openTab("head");
		
		out.fullLine("title", type.getTypeName());
		out.voidLine("link", 
				new Attribute("rel", "stylesheet"),
				new Attribute("href", upDir(packageDepth) + "stylesheet.css"));
		
		out.closeUntab();
	}
	
	private void writeHeader(Html5Writer out) throws ShadowException, DocumentationException
	{
		String packageName = type.getPackage().getQualifiedName();
		if (!packageName.isEmpty())
			out.fullLine("p", type.getPackage().getQualifiedName());
		
		out.fullLine("h2", typeKind + " " + type.getTypeName());
		
		writeInheritanceSection(out);

		out.voidLine("hr");
	}
	
	private void writeAllMethodTables(Html5Writer out) 
			throws ShadowException, DocumentationException
	{
		if (!constructors.isEmpty())
			writeMethodTable("Constructor Summary", constructors, out);
		if (!destructors.isEmpty())
			writeMethodTable("Destructor Summary", destructors, out);
		if (!methods.isEmpty())
			writeMethodTable("Method Summary", methods, out);
		if (!properties.isEmpty())
			writeMethodTable("Property Summary", properties, out);
	}
	
	private void writeMethodTable(String name, 
			List<MethodSignature> methods, Html5Writer out) 
					throws ShadowException, DocumentationException
	{
		out.openTab("div", new Attribute("class", "block"));		
		out.fullLine("h3", name);
		out.openTab("table", new Attribute("class", "summarytable"));
		
		writeTableRow(out, true, "Modifiers", "Return Type", "Method and Description");
		for (MethodSignature method : methods) {
			out.openTab("tr");
				out.open("td");
					out.full("code", method.getModifiers().toString().trim());
				out.closeLine();
				out.open("td");
					out.full("code", method.getReturnTypes().toString());
				out.closeLine();
				out.open("td");
					out.open("code");
						writeMethodName(method, true, out);
						writeParameters(method, true, true, out);
					out.close();
				out.closeLine();
			out.closeUntab();
		}
		
		out.closeUntab();
		out.closeUntab();
	}
	
	private void writeAllMethodDetails(Html5Writer out)
			throws ShadowException, DocumentationException 
	{
		if (!constructors.isEmpty())
			writeMethodDetailSection("Constructor Details", constructors, out);
		if (!destructors.isEmpty())
			writeMethodDetailSection("Destructor Details", destructors, out);
		if (!methods.isEmpty())
			writeMethodDetailSection("Method Details", methods, out);
		if (!properties.isEmpty())
			writeMethodDetailSection("Property Details", properties, out);
	}
	
	private void writeMethodDetailSection(String name, 
			List<MethodSignature> methods, Html5Writer out) 
					throws ShadowException, DocumentationException
	{
		out.openTab("div", new Attribute("class", "block"));		
		out.fullLine("h3", name);
		
		for (MethodSignature method : methods)
			writeMethodDetail(method, out);
		
		out.closeUntab();
	}
	
	private void writeMethodDetail(MethodSignature method, 
			Html5Writer out) throws ShadowException, DocumentationException
	{
		out.openTab("div", new Attribute("class", "methoddetail"));
		
		out.fullLine("h4", method.getSymbol(),
				new Attribute("id", getUniqueID(method)));
		
		out.open("code");
		out.add(method.getModifiers().toString().trim() + " "
				+ method.getSymbol());
		writeParameters(method, true, true, out);
		out.add(" => " + method.getReturnTypes());
		out.closeLine();
		
		out.closeUntab();
	}
	
	private void writeMethodName(MethodSignature method, boolean linkToDetail,
			Html5Writer out) throws DocumentationException, ShadowException
	{
		if (linkToDetail)
			writeLink("#" + getUniqueID(method), method.getSymbol(), out);
		else
			out.add(method.getSymbol());
	}
	
	private void writeParameters(MethodSignature method, 
			boolean parameterNames, boolean link, Html5Writer out) 
					throws DocumentationException, ShadowException
	{
		out.add("(");
		int parameterCount = method.getParameterNames().size();
		for (int i = 0; i < parameterCount; ++i)
		{
			Type parameter = method.getParameterTypes().get(i).getType();
			
			if (link)
				writeCrossLink(parameter, parameter.getQualifiedName(), out);
			else
				out.add(parameter.getQualifiedName());
			
			
			out.add(" " + method.getParameterNames().get(i));
			
			if (i < parameterCount - 1)
				out.add(", ");
		}
		out.add(")");
	}
	
	private void writeInheritanceSection(Html5Writer out) throws ShadowException, DocumentationException
	{
		List<Type> extendsList = new ArrayList<Type>();
		List<Type> implementsList = new ArrayList<Type>();

		// TODO: Should getAllInterfaces() be used here?
		if (type instanceof InterfaceType) {
			extendsList.addAll(type.getInterfaces());
		} else {
			Type extendType = ((ClassType)type).getExtendType();
			if (extendType != null)
				extendsList.add(extendType);
			implementsList.addAll(type.getInterfaces());
		}
		
		if (extendsList.size() > 0) {
			out.fullLine("h4", "Extends");
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < extendsList.size(); ++i) {
				if (i > 0)
					builder.append(", ");
				
				// List the name, optionally attempting a link
				Type current = extendsList.get(i);
				if (makeLinks)
					writeCrossLink(current, current.getQualifiedName(), out);
				else
					builder.append(current.getQualifiedName());
			}
			out.fullLine("p", builder.toString());
		}
		
		if (implementsList.size() > 0) {
			out.fullLine("h4", "Implements");
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < implementsList.size(); ++i) {
				if (i > 0)
					builder.append(", ");
				
				// List the name, optionally attempting a link
				Type current = implementsList.get(i);
				if (makeLinks)
					writeCrossLink(current, current.getQualifiedName(), out);
				else
					builder.append(current.getQualifiedName());
			}
			out.fullLine("p", builder.toString());
		}
	}
	
	private void writeCrossLink(Type to, String text, Html5Writer out) 
			throws DocumentationException, ShadowException
	{
		if (linkableTypes.contains(to))
			writeLink(getRelativePath(type, to) + "/" 
					+ to.getTypeName() + extension, text, out);
		else
			out.add(text);
	}
	
	private static void writeLink(String href, String text, Html5Writer out) 
			throws DocumentationException, ShadowException
	{
		out.full("a", text, new Attribute("href", href));
	}
	
	private static void writeTableRow(Html5Writer out, boolean header,
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
	
	/* Helper methods */

	/**
	 * Determines where this page should be output based on its package and
	 * a given root directory
	 */
	private Path constructOutputPath(Path root)
	{
		ArrayDeque<String> packages = new ArrayDeque<String>();
		
		// Climb up the chain of packages
		Package currentPackage = type.getPackage();
		while (currentPackage != null && !currentPackage.getName().isEmpty())
		{
			packages.addFirst(currentPackage.getName());
			currentPackage = currentPackage.getParent();
			
			packageDepth++; // TODO: Put this somewhere more appropriate
		}
		
		// Descend into corresponding directories
		for (String packageName : packages)
			root = root.resolve(packageName);
		
		return root;
	}
	
	/** Creates the requested number of repetitions of '../' */
	private static String upDir(int count)
	{
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < count; ++i)
			builder.append("../");
		return builder.toString();
	}
	
	public static String getRelativePath(Type from, Type to)
	{
		Path start = Paths.get(from.getPackage().getPath());
		Path target = Paths.get(to.getPackage().getPath());
		
		return start.relativize(target).toString();
	}
	
	/** 
	 * Creates a unique ID based on a method's signature which can be used as
	 * an HTML bookmark
	 */
	private static String getUniqueID(MethodSignature method)
	{
		return method.getSymbol() 
				+ method.getParameterTypes().toString().replaceAll("\\s", "");
	}
}
