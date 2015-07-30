package shadow.doctool.output;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import shadow.doctool.Documentation;
import shadow.doctool.DocumentationException;
import shadow.doctool.output.HtmlWriter.Attribute;
import shadow.doctool.tag.TagManager.BlockTagType;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.Package;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.EnumType;
import shadow.typecheck.type.ExceptionType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;

public class ClassOrInterfacePage extends Page
{
	private Type type;
	private String typeKind;
	private HashSet<Type> linkableTypes;
	private final Path relativePath;
	public final String qualifiedName;
	
	// Method categories
	private List<MethodSignature> constructors = new ArrayList<MethodSignature>();
	private List<MethodSignature> destructors = new ArrayList<MethodSignature>();
	private List<MethodSignature> methods = new ArrayList<MethodSignature>();
	private List<MethodSignature> properties = new ArrayList<MethodSignature>();
	
	// Public and protected constants
	private List<Node> visibleConstants = new ArrayList<Node>();
	
	public ClassOrInterfacePage(StandardTemplate master, Type type, 
			Collection<Type> linkableTypes) throws DocumentationException
	{
		super(master);
		
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
		this.linkableTypes = new HashSet<Type>(linkableTypes);
		this.relativePath = constructOutputPath().resolve(type.getTypeName().replaceAll(":", "\\$") + EXTENSION);
		
		// The qualified name should not contain type parameters
		String pkg = type.getPackage().getQualifiedName();
		if (pkg.isEmpty())
			pkg = "default";
		this.qualifiedName = pkg + "@" + type.getTypeName();
		
		getVisibleMethods();
		getVisibleConstants();
	}
	
	/** Sorts all methods into lists based upon their type and visibility */
	private void getVisibleMethods()
	{
		for (List<MethodSignature> overloadList : type.getMethodMap().values()) {
			for (MethodSignature method : overloadList) {
				if (!method.getModifiers().isPrivate()) {
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
		
		Collections.sort(constructors);
		Collections.sort(destructors);
		Collections.sort(methods);
		Collections.sort(properties);
	}
	
	private void getVisibleConstants()
	{
		for (Node field : type.getFields().values())
			if (field.getModifiers().isConstant() && !field.getModifiers().isPrivate())
				visibleConstants.add(field);
	}
	
	public void write(Path root) 
			throws IOException, ShadowException, DocumentationException
	{
		// Find/create the directory chain where the document will reside
		Path outputDirectory = constructOutputPath(root);
		outputDirectory.toFile().mkdirs();
		
		// Begin writing to the document itself
		// Note: All colons in class names are replaced with dashes to avoid
		// browser issues (i.e. relative paths being interpreted as protocols)
		Path output = outputDirectory
				.resolve(type.getTypeName().replaceAll(":", "\\$") + EXTENSION);
		FileWriter fileWriter = new FileWriter(output.toFile());
		HtmlWriter out = new HtmlWriter(fileWriter);
		
		out.openTab("html");
		writeHtmlHead(type.getTypeName(), out);
		out.openTab("body");
		
		writeNavBar(master.getPackagePage(type.getPackage()), out);
		
		writeHeader(out);
		writeConstantSummaries(out);
		writeMethodSummaries(out);
		
		writeConstantDetails(out);
		writeMethodDetails(out);
		
		out.closeUntab();
		out.closeUntab();
		
		fileWriter.close();
	}
	
	private void writeHeader(HtmlWriter out) 
			throws ShadowException, DocumentationException
	{
		out.openTab("div", new Attribute("class", "header"));
		
		String packageName = type.getPackage().getQualifiedName();
		
		// Create a link to this class/interface's package
		out.open("p");
		if (!packageName.isEmpty())
			writeLink(PackagePage.PAGE_NAME +  EXTENSION, 
					type.getPackage().getQualifiedName(), out);
		else
			writeLink(PackagePage.PAGE_NAME +  EXTENSION, 
					"default", out);
		out.closeLine();
		
		out.fullLine("h2", typeKind + " " + type.getTypeName());
		
		writeInnerClassSection(out);
		writeInheritanceSection(out);
		
		out.voidLine("hr");
		
		// Full, "in code" declaration
		out.open("code");
		out.add(type.getModifiers().toString() + typeKind.toLowerCase()
				+ " " + type.getTypeName());
		writeTypeParameters(type, true, out);
		out.closeLine();
		
		// Documentation text
		if (type.hasDocumentation()) {
			writeInlineTags(type.getDocumentation().getInlineTags(), out);
			writeBlockTags(type.getDocumentation(), out);
		}
		
		out.closeUntab();
	}
	
	// TODO: Support recursion of type constraints?
	private void writeTypeParameters(Type parent, boolean link, 
			HtmlWriter out) throws ShadowException, DocumentationException
	{
		SequenceType typeParams = parent.getTypeParameters();
		if (typeParams != null && typeParams.size() > 0) {
			out.add("<");
			int i = 0;
			for (ModifiedType modifiedParam : typeParams) {
				if (i != 0)
					out.add(", ");
				TypeParameter param = (TypeParameter)modifiedParam.getType();
				out.add(param.getTypeName());
				Set<Type> bounds = param.getBounds();
				int j = 0;
				for (Type constraint : bounds) {
					if (j == 0) {
						out.add(" is ");
					} else {
						out.add(" and ");
					}
					if (link) {
						writeCrossLink(constraint, constraint.getTypeName(), out);
					} else {
						out.add(constraint.getTypeName());
					}
					j++;
				}
				i++;
			}
			out.add(">");
		}
	}
	
	private void writeInnerClassSection(HtmlWriter out)
			throws ShadowException, DocumentationException
	{
		// Link to outer class, if any
		if (type.hasOuter()) {
			out.fullLine("h4", "Outer class");
			out.open("p");
			writeCrossLink(type.getOuter(), type.getOuter().getQualifiedName(), out);
			out.closeLine();
		}
		
		if (type instanceof ClassType) {
			Collection<ClassType> innerClasses 
					= ((ClassType)type).getInnerClasses().values();
			if (!innerClasses.isEmpty())
				out.fullLine("h4", "Visible inner classes");
			out.open("p");
			int i = 0;
			for (Type current : innerClasses) {
				if (i > 0)
					out.add(", ");
				
				// List the name, optionally attempting a link
				writeCrossLink(current, current.getQualifiedName(), out);
				i++;
			}
			out.closeLine();
		}
	}

	private void writeInheritanceSection(HtmlWriter out) 
			throws ShadowException, DocumentationException
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
			out.open("p");
			for (int i = 0; i < extendsList.size(); ++i) {
				if (i > 0)
					out.add(", ");
				
				// List the name, optionally attempting a link
				Type current = extendsList.get(i);
				writeCrossLink(current, current.getQualifiedName(), out);
			}
			out.closeLine();
		}
		
		if (implementsList.size() > 0) {
			out.fullLine("h4", "Implements");
			out.open("p");
			for (int i = 0; i < implementsList.size(); ++i) {
				if (i > 0)
					out.add(", ");
				
				// List the name, optionally attempting a link
				Type current = implementsList.get(i);
				writeCrossLink(current, current.getQualifiedName(), out);
			}
			out.closeLine();
		}
	}
	
	private void writeConstantSummaries(HtmlWriter out)
			throws ShadowException, DocumentationException
	{
		if (!visibleConstants.isEmpty()) {
			out.openTab("div", new Attribute("class", "block"));		
			out.fullLine("h3", "Constant Summary");
			out.openTab("table", new Attribute("class", "summarytable"));
			
			writeTableRow(out, true, "Modifiers", "Type", 
					"Name and Description");
			for (Node constant : visibleConstants) {
				out.openTab("tr");
					out.open("td");
						out.full("code", constant.getModifiers().toString().trim());
					out.closeLine();
					out.open("td");
						out.full("code", constant.getType().getTypeName());
					out.closeLine();
					out.open("td");
						out.open("code");
							writeNodeName(constant, true, out);
						out.close();
						if (constant.hasDocumentation())
							writeInlineTags(constant.getDocumentation().getSummary(), out);
					out.closeLine();
				out.closeUntab();
			}
			
			out.closeUntab();
			out.closeUntab();
		}
	}
	
	private void writeMethodSummaries(HtmlWriter out) 
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
			List<MethodSignature> methods, HtmlWriter out) 
					throws ShadowException, DocumentationException
	{
		out.openTab("div", new Attribute("class", "block"));		
		out.fullLine("h3", name);
		out.openTab("table", new Attribute("class", "summarytable"));
		
		writeTableRow(out, true, "Modifiers", "Return Type", 
				"Method and Description");
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
					if (method.hasDocumentation())
						writeInlineTags(method.getDocumentation().getSummary(), out);
				out.closeLine();
			out.closeUntab();
		}
		
		out.closeUntab();
		out.closeUntab();
	}
	
	private void writeConstantDetails(HtmlWriter out)
			throws ShadowException, DocumentationException
	{
		if (!visibleConstants.isEmpty()) {
			out.openTab("div", new Attribute("class", "block"));		
			out.fullLine("h3", "Constant Detail");
			
			for (Node constant : visibleConstants)
				writeConstantDetail(constant, out);
			
			out.closeUntab();
		}
	}
	
	private void writeConstantDetail(Node constant, HtmlWriter out)
			throws ShadowException, DocumentationException
	{
		out.openTab("div", new Attribute("class", "methoddetail"));
		
		out.fullLine("h4", constant.toString(),
				new Attribute("id", constant.toString()));
		
		out.open("code");
		out.add(constant.getModifiers().toString().trim() + " "
				+ constant.toString());
		out.closeLine();
		
		// Documentation text
		if (constant.hasDocumentation()) {
			writeInlineTags(constant.getDocumentation().getInlineTags(), out);
			writeBlockTags(constant.getDocumentation(), out);
		}
		
		out.closeUntab();
	}
	
	private void writeMethodDetails(HtmlWriter out)
			throws ShadowException, DocumentationException 
	{
		if (!constructors.isEmpty())
			writeMethodDetailSection("Constructor Detail", constructors, out);
		if (!destructors.isEmpty())
			writeMethodDetailSection("Destructor Detail", destructors, out);
		if (!methods.isEmpty())
			writeMethodDetailSection("Method Detail", methods, out);
		if (!properties.isEmpty())
			writeMethodDetailSection("Property Detail", properties, out);
	}
	
	private void writeMethodDetailSection(String name, 
			List<MethodSignature> methods, HtmlWriter out) 
					throws ShadowException, DocumentationException
	{
		out.openTab("div", new Attribute("class", "block"));		
		out.fullLine("h3", name);
		
		for (MethodSignature method : methods)
			writeMethodDetail(method, out);
		
		out.closeUntab();
	}
	
	private void writeMethodDetail(MethodSignature method, 
			HtmlWriter out) throws ShadowException, DocumentationException
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
		
		// Documentation text
		if (method.hasDocumentation()) {
			writeInlineTags(method.getDocumentation().getInlineTags(), out);
			writeBlockTags(method.getDocumentation(), out);
		}
		
		out.closeUntab();
	}
	
	private void writeNodeName(Node node, boolean linkToDetail,
			HtmlWriter out) throws DocumentationException, ShadowException
	{
		if (linkToDetail)
			writeLink("#" + node.toString(), node.toString(), out);
		else
			out.add(node.toString());
	}
	
	private void writeMethodName(MethodSignature method, boolean linkToDetail,
			HtmlWriter out) throws DocumentationException, ShadowException
	{
		if (linkToDetail)
			writeLink("#" + getUniqueID(method), method.getSymbol(), out);
		else
			out.add(method.getSymbol());
	}
	
	private void writeParameters(MethodSignature method, 
			boolean parameterNames, boolean link, HtmlWriter out) 
					throws DocumentationException, ShadowException
	{
		out.add("(");
		int parameterCount = method.getParameterNames().size();
		for (int i = 0; i < parameterCount; ++i)
		{
			Type parameter = method.getParameterTypes().get(i).getType();
			
			// TODO: Limit which modifiers appear? (i.e. not 'immutable locked')
			out.add(parameter.getModifiers().toString().trim() + " ");
			
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
	
	private void writeBlockTags(Documentation documentation,
			HtmlWriter out) throws DocumentationException, ShadowException
	{
		if (documentation.hasBlockTags(BlockTagType.AUTHOR))
			writeAuthorSection(documentation.getBlockTags(BlockTagType.AUTHOR), out);
		if (documentation.hasBlockTags(BlockTagType.PARAM))
			writeParamSection(documentation.getBlockTags(BlockTagType.PARAM), out);
		if (documentation.hasBlockTags(BlockTagType.SEE_DOC) 
				|| documentation.hasBlockTags(BlockTagType.SEE_URL))
			writeSeeSection(documentation.getBlockTags(BlockTagType.SEE_DOC),
					documentation.getBlockTags(BlockTagType.SEE_URL), out);
		if (documentation.hasBlockTags(BlockTagType.THROWS))
			writeThrowsSection(documentation.getBlockTags(BlockTagType.THROWS), out);
		if (documentation.hasBlockTags(BlockTagType.RETURN))
			writeReturnSection(documentation.getBlockTags(BlockTagType.RETURN), out);
	}
	
	private void writeAuthorSection(List<List<String>> authorTags, 
			HtmlWriter out) throws DocumentationException, ShadowException
	{
		if (authorTags.size() > 0) {
			out.fullLine("h5", "Authors");
			out.openTab("div", new Attribute("class", "blocktagcontent"));
			for (List<String> tag : authorTags) {
				for (String author : tag) {
					out.fullLine("p", author);
				}
			}
			out.closeUntab();
		}
	}
	
	private void writeParamSection(List<List<String>> paramTags,
			HtmlWriter out) throws DocumentationException, ShadowException
	{
		if (paramTags.size() > 0) {
			out.fullLine("h5", "Parameters");
			out.openTab("div", new Attribute("class", "blocktagcontent"));
			for (List<String> tag : paramTags) {
				out.open("p");
				out.full("code", tag.get(0), new Attribute("class", "inline"));
				if (tag.size() > 1)
					out.add(" - " + tag.get(1));
				out.closeLine();
			}
			out.closeUntab();
		}
	}
	
	private void writeSeeSection(List<List<String>> seeDocTags, 
			List<List<String>> seeUrlTags, HtmlWriter out) 
			throws DocumentationException, ShadowException
	{
		if (seeUrlTags.size() > 0 || seeDocTags.size() > 0) {
			out.fullLine("h5", "See Also");
			out.openTab("div", new Attribute("class", "blocktagcontent"));
			for (List<String> tag : seeDocTags) {
				out.open("p");
				// TODO: Move the linking code into a method?
				Path link = master.linkByName(this, tag.get(0));
				if (link != null) {
					out.full("a", tag.get(1), new Attribute("href", 
							link.toString()));
				} else {
					logger.warn("On page " + getRelativePath() + " - "
							+ "Could not link to type or package " +
							tag.get(0));
					out.add(tag.get(1));
				}
				out.closeLine();
			}
			for (List<String> tag : seeUrlTags) {
				out.open("p");
				out.full("a", tag.get(1), new Attribute("href", tag.get(0)));
				out.closeLine();
			}
			out.closeUntab();
		}
	}
	
	private void writeThrowsSection(List<List<String>> throwsTags,
			HtmlWriter out) throws DocumentationException, ShadowException
	{
		if (throwsTags.size() > 0) {
			out.fullLine("h5", "Throws");
			out.openTab("div", new Attribute("class", "blocktagcontent"));
			for (List<String> tag : throwsTags) {
				out.open("p");
				out.full("code", tag.get(0), new Attribute("class", "inline"));
				if (tag.size() > 1)
					out.add(" - " + tag.get(1));
				out.closeLine();
			}
			out.closeUntab();
		}
	}
	
	private void writeReturnSection(List<List<String>> throwsTags,
			HtmlWriter out) throws DocumentationException, ShadowException
	{
		if (throwsTags.size() > 0) {
			out.fullLine("h5", "Returns");
			out.openTab("div", new Attribute("class", "blocktagcontent"));
			for (List<String> tag : throwsTags) {
				out.open("p");
				out.full("code", tag.get(0), new Attribute("class", "inline"));
				if (tag.size() > 1)
					out.add(" - " + tag.get(1));
				out.closeLine();
			}
			out.closeUntab();
		}
	}
	
	private void writeCrossLink(Type to, String text, HtmlWriter out) 
			throws DocumentationException, ShadowException
	{
		if (linkableTypes.contains(to))
			// Replace colons in class names with dashes
			writeLink(getRelativePath(type, to)
					.replaceAll(":", "\\$"), text, out);
		else
			out.add(text);
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
		Package currentPackage = type.getPackage();
		while (currentPackage != null && !currentPackage.getName().isEmpty()) {
			packages.addFirst(currentPackage.getName());
			currentPackage = currentPackage.getParent();
		}

		Path outputPath = Paths.get("");
		for (String packageName : packages)
			outputPath = outputPath.resolve(packageName);
		
		return outputPath;
	}
	
	/** Creates the requested number of repetitions of "../" */
	public static String upDir(int count)
	{
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < count; ++i)
			builder.append("../");
		return builder.toString();
	}
	
	/** 
	 * Creates a unique ID based on a method's signature, which can then be used 
	 * as an HTML bookmark
	 */
	private static String getUniqueID(MethodSignature method)
	{
		return method.getSymbol() 
				+ method.getParameterTypes().toString().replaceAll("\\s", "");
	}

	public Path getRelativePath() 
	{
		return relativePath;
	}
}
