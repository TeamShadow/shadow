package shadow.doctool.output;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.List;

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
	private int packageDepth = 0;
	
	public ClassOrInterfacePage(Type type, List<Type> linkableTypes) throws DocumentationException
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
	}
	
	public void make(Path root) throws IOException, ShadowException
	{
		// Find/create the subdirectory chain where the document will reside
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
						+ getMethodParameterList(method, true));
		
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
	
	private static void makeMethodDetail(MethodSignature method, 
			TabbedLineWriter out) throws ShadowException
	{
		out.write("<div class=\"methoddetail\">"); out.indent();
		
		out.write("<h4>" + method.getSymbol() + "</h4>");
		out.write("<p>" + method.getModifiers().toString().trim() + " "
				+ method.getSymbol() + getMethodParameterList(method, true)
				+ " => " + method.getReturnTypes() + "</p>");
		
		out.outdent(); out.write("</div>");
	}
	
	private static String getMethodParameterList(MethodSignature method, boolean parameterNames)
	{
		StringBuilder builder = new StringBuilder();
		builder.append('(');
		int parameterCount = method.getParameterNames().size();
		for (int i = 0; i < parameterCount; ++i)
		{
			builder.append(method.getParameterTypes().get(i).getType().getQualifiedName());
			builder.append(' ');
			builder.append(method.getParameterNames().get(i));
			
			if (i < parameterCount - 1)
				builder.append(", ");
		}
		builder.append(')');
		return builder.toString();
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
	
	private static String upDir(int count)
	{
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < count; ++i)
			builder.append("../");
		return builder.toString();
	}
}
