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
import shadow.output.TabbedLineWriter;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.Package;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.EnumType;
import shadow.typecheck.type.ExceptionType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodSignature;
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
	}
	
	public void make(Path root) throws IOException, ShadowException
	{
		// Find/create the directory chain where the document will reside
		Path outputDirectory = constructOutputPath(root);
		outputDirectory.toFile().mkdirs();
		
		// Begin writing to the document itself
		Path output = outputDirectory.resolve(type.getTypeName() + extension);
		FileWriter fileWriter = new FileWriter(output.toFile());
		TabbedLineWriter out = new TabbedLineWriter(fileWriter);
		
		out.write("<!DOCTYPE html>");
		out.write("<html>"); out.indent();
		
		makeHtmlHead(out);
		
		out.write("<body>"); out.indent();
		
		makeHeader(out);
		makeMethodSummaryTable(out);
		makeMethodDetailSection(out);
		
		out.outdent(); out.write("</body>");
		out.outdent(); out.write("</html>");
		
		fileWriter.close();
	}
	
	private void makeHtmlHead(TabbedLineWriter out) throws ShadowException
	{
		out.write("<head>"); out.indent();
		
		out.write("<title>" + type.getTypeName() + "</title>");
		out.write("<link rel=\"stylesheet\" href=\"" + upDir(packageDepth)
				+ "stylesheet.css\">");
		
		out.outdent(); out.write("</head>");
	}
	
	private void makeHeader(TabbedLineWriter out) throws ShadowException
	{
		String packageName = type.getPackage().getQualifiedName();
		if (!packageName.isEmpty())
			out.write("<p>" + type.getPackage().getQualifiedName() + "</p>");
		
		out.write("<h2>" + typeKind + " " + type.getTypeName() + "</h2>");
		
		makeInheritanceSection(out);

		out.write("<hr>");
	}
	
	private void makeMethodSummaryTable(TabbedLineWriter out) throws ShadowException
	{
		out.write("<div class=\"block\">"); out.indent();
		out.write("<h3>Method Summary</h3>");
		out.write("<table class=\"summarytable\">"); out.indent();
		
		makeTableRow(out, true, "Modifiers", "Return Type", "Method and Description");
		for (List<MethodSignature> overloadList : type.getMethodMap().values())
			for (MethodSignature method : overloadList)
				makeTableRow(out, false, method.getModifiers().toString().trim(),
						method.getReturnTypes().toString(), method.getSymbol()
						+ getMethodParameterList(method, true, true));
		
		out.outdent(); out.write("</table>");
		out.outdent(); out.write("</div>");
	}
	
	private void makeMethodDetailSection(TabbedLineWriter out) throws ShadowException
	{
		out.write("<div class=\"block\">"); out.indent();
		out.write("<h3>Method Detail</h3>");
		
		for (List<MethodSignature> overloadList : type.getMethodMap().values())
			for (MethodSignature method : overloadList)
				makeMethodDetail(method, out);
		
		out.outdent(); out.write("</div>");
	}
	
	private void makeMethodDetail(MethodSignature method, 
			TabbedLineWriter out) throws ShadowException
	{
		out.write("<div class=\"methoddetail\">"); out.indent();
		
		out.write("<h4>" + method.getSymbol() + "</h4>");
		out.write("<p>" + method.getModifiers().toString().trim() + " "
				+ method.getSymbol() + getMethodParameterList(method, true, true)
				+ " => " + method.getReturnTypes() + "</p>");
		
		out.outdent(); out.write("</div>");
	}
	
	private String getMethodParameterList(MethodSignature method, 
			boolean parameterNames, boolean link)
	{
		StringBuilder builder = new StringBuilder();
		builder.append('(');
		int parameterCount = method.getParameterNames().size();
		for (int i = 0; i < parameterCount; ++i)
		{
			Type parameter = method.getParameterTypes().get(i).getType();
			
			if (link)
				builder.append(linkToType(parameter, 
						parameter.getQualifiedName()));
			else
				builder.append(parameter.getQualifiedName());
			
			
			builder.append(' ');
			builder.append(method.getParameterNames().get(i));
			
			if (i < parameterCount - 1)
				builder.append(", ");
		}
		builder.append(')');
		return builder.toString();
	}
	
	private void makeInheritanceSection(TabbedLineWriter out) throws ShadowException
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
			StringBuilder builder = new StringBuilder();
			out.write("<h4>Extends</h4>");
			builder.append("<p>");
			for (int i = 0; i < extendsList.size(); ++i) {
				if (i > 0)
					builder.append(", ");
				
				// List the name, optionally attempting a link
				Type current = extendsList.get(i);
				if (makeLinks)
					builder.append(linkToType(current, 
							current.getQualifiedName()));
				else
					builder.append(current.getQualifiedName());
			}
			builder.append("</p>");
			out.write(builder.toString());
		}
		
		if (implementsList.size() > 0) {
			StringBuilder builder = new StringBuilder();
			out.write("<h4>Implements</h4>");
			builder.append("<p>");
			for (int i = 0; i < implementsList.size(); ++i) {
				if (i > 0)
					builder.append(", ");
				
				// List the name, optionally attempting a link
				Type current = implementsList.get(i);
				if (makeLinks)
					builder.append(linkToType(current, 
							current.getQualifiedName()));
				else
					builder.append(current.getQualifiedName());
			}
			builder.append("</p>");
			out.write(builder.toString());
		}
	}
	
	/* Helper methods */
	
	private String linkToType(Type to, String text)
	{
		if (linkableTypes.contains(to))
			return makeLink(getRelativePath(type, to) + "/"
					+ to.getTypeName() + extension, text);
		else
			return text;
	}
	
	private static String makeLink(String href, String text)
	{
		return "<a href=\"" + href + "\">" + text + "</a>";
	}
	
	private static void makeTableRow(TabbedLineWriter out, boolean header,
			String ... columns) throws ShadowException
	{
		out.write("<tr>"); out.indent();
		
		for (String column : columns) {
			if (header)
				out.write("<th>" + column + "</th>");
			else
				out.write("<td>" + column + "</td>");
		}
		
		out.outdent(); out.write("</tr>");
	}

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
}
