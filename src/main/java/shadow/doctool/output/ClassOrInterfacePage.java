package shadow.doctool.output;

import java.util.List;

import shadow.doctool.DocumentationException;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.EnumType;
import shadow.typecheck.type.ExceptionType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.Type;

public class ClassOrInterfacePage extends Page
{
	private Type type;
	private String typeKind;
	
	public ClassOrInterfacePage(Type type) throws DocumentationException
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
	
	private String makeHeader()
	{
		StringBuilder builder = new StringBuilder();
		String packageName = type.getPackage().getQualifiedName();
		if (!packageName.isEmpty())
			builder.append("<p>" + type.getPackage().getQualifiedName() + "</p>\n");
		builder.append("<h2>" + typeKind + " " + type.getTypeName() + "</h2>\n");
		builder.append("<hr>\n");
		
		return builder.toString();
	}
	
	private String generateMethodSummaryTable()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("<h3>Method Summary</h3>\n");
		builder.append("<table class=\"summarytable\">\n<tr>\n");
		builder.append("<th>Modifiers</th>\n");
		builder.append("<th>Return Type</th>\n");
		builder.append("<th>Method and Description</th>\n");
		builder.append("</tr>");
		
		for (List<MethodSignature> overloadList : type.getMethodMap().values())
			for (MethodSignature method : overloadList)
				builder.append(makeMethodSummary(method));
		
		builder.append("</table>\n");
		return builder.toString();
	}
	
	private static String makeMethodSummary(MethodSignature method)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("<tr>\n");
		builder.append("<th>" + method.getModifiers().toString().trim() 
				+ "</th>\n");
		builder.append("<th>" + method.getReturnTypes() + "</th>\n");
		builder.append("<th>" + method.getSymbol() 
				+ getMethodParameterList(method, true) + "</th>\n");
		builder.append("</tr>\n");
		return builder.toString();
	}
	
	private static String getMethodParameterList(MethodSignature method, boolean parameterNames)
	{
		StringBuilder builder = new StringBuilder();
		builder.append('(');
		int parameterCount = method.getParameterNames().size();
		for (int i = 0; i < parameterCount; ++i)
		{
			builder.append(method.getParameterNames().get(i));
			builder.append(method.getParameterTypes().get(i).getType().getQualifiedName());
			
			if (i < parameterCount - 1)
				builder.append(", ");
		}
		builder.append(')');
		return builder.toString();
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("<!DOCTYPE html>\n<html>\n<head>\n");
		builder.append("<title>" + type.getTypeName() + "</title>\n");
		builder.append("</head>\n<body>\n");
		
		builder.append(makeHeader());
		builder.append(generateMethodSummaryTable());
		
		builder.append("</body>\n</html>");
		return builder.toString();
	}
}
