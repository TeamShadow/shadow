package shadow.doctool;

import java.io.StringWriter;
import java.util.ArrayDeque;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import shadow.Loggers;
import shadow.AST.ASTWalker.WalkType;
import shadow.AST.AbstractASTVisitor;
import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.ASTCompilationUnit;
import shadow.parser.javacc.ASTFieldDeclaration;
import shadow.parser.javacc.ASTMethodDeclaration;
import shadow.parser.javacc.ASTTypeParameter;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.ShadowParser.TypeKind;
import shadow.typecheck.Package;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.InterfaceType;
import shadow.typecheck.type.MethodType;
import shadow.typecheck.type.SequenceType;

/**
 * Visits a previously type-checked source file in order to generate
 * documentation
 */
public class DocumentationVisitor extends AbstractASTVisitor 
{
	private static final Logger logger = Loggers.DOC_TOOL;
	
	private Document document;
	private org.w3c.dom.Node currentNode;;
	
	public DocumentationVisitor() throws ParserConfigurationException
	{
		// Create a new document to represent the top-level class in question
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		document = builder.newDocument();
		document.setXmlStandalone(true);
		currentNode = document.appendChild(document.createElement("shadowdoc"));
	}
	
	public void OutputDocumentation()
	{
		try
		{
			// Convert and output the DOM as an XML document
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(document), new StreamResult(writer));
			
			System.out.println(writer.toString());
		}
		catch (Exception e)
		{
			System.out.println("Unable to output documentation:");
			System.out.println(e);
		}
	}
	
	@Override
	public Object visit(ASTMethodDeclaration node, Boolean secondVisit) throws ShadowException
	{
		//printDocumentation(node, depth);
		
		/*
		System.out.println(node.getModifiers());
		System.out.println(getPackageName(node.getType()));
		System.out.println(node.jjtGetChild(0).getImage());
		
		MethodType type = (MethodType)node.getType();
		
		System.out.println(type.getReturnTypes());
		*/
		
		Element method = (Element) currentNode.appendChild(document.createElement("method"));
		
		// The first child of an ASTMethodDeclaration is an ASTMethodDeclarator.
		// We retrieve the method name from this child
		method.setAttribute("name", node.jjtGetChild(0).getImage());
		
		// Create child tags for each parameter
		MethodType type = (MethodType)node.getType();
		List<String> parameterNames = type.getParameterNames();
		SequenceType parameterTypes = type.getParameterTypes();
		for (int i = 0; i < parameterNames.size(); ++i)
		{
			Element parameter = (Element) method.appendChild(document.createElement("parameter"));
			parameter.setAttribute("name", parameterNames.get(i));
			parameter.setAttribute("type", parameterTypes.get(i).getType().getQualifiedName());
		}
		
		// Add a description tag if this node has documentation
		if (node.hasDocumentation())
		{
			Node description = method.appendChild(document.createElement("description"));
			description.setTextContent(node.getDocumentation().toString());
		}
		
		return WalkType.NO_CHILDREN;
	}
	
	@Override
	public Object visit(ASTFieldDeclaration node, Boolean secondVisit) throws ShadowException
	{
		Element field = (Element) currentNode.appendChild(document.createElement("field"));
		
		// The second child of an ASTFieldDeclaration is an ASTVariableDeclarator.
		// We retrieve the variable name from this child
		field.setAttribute("name", node.jjtGetChild(1).getImage());
		field.setAttribute("type", node.getType().getQualifiedName());
		
		// Add a description tag if this node has documentation
		if (node.hasDocumentation())
		{
			Node description = field.appendChild(document.createElement("description"));
			description.setTextContent(node.getDocumentation().toString());
		}
		
		return WalkType.NO_CHILDREN;
	}
	
	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit) throws ShadowException
	{
		if (secondVisit) {
			// Step out of tag for this class
			currentNode = currentNode.getParentNode();
		} else {
			// Create package tags if this is the outermost class
			if (node.jjtGetParent() instanceof ASTCompilationUnit)
				currentNode = appendPackages(currentNode, node.getType().getPackage());
			
			// Create a tag using the name associated with this nodes ClassType/TypeKind
			currentNode = currentNode.appendChild(document.createElement(getClassTag(node.getKind())));
			((Element) currentNode).setAttribute("name", node.getImage());
			
			// Add an attribute if the class extends something
			ClassType extendType = ((ClassType)node.getType()).getExtendType();
			if (extendType != null)
				((Element) currentNode).setAttribute("extends", extendType.getQualifiedName());
			
			// Append all interfaces as tags
			for (InterfaceType currentInterface : node.getType().getAllInterfaces())
			{
				Element interfaceElement = document.createElement("implements");
				interfaceElement.setAttribute("interface", currentInterface.getQualifiedName());
				
				currentNode.appendChild(interfaceElement);
			}
			
			// Add a description tag if this node has documentation
			if (node.hasDocumentation())
			{
				Node description = currentNode.appendChild(document.createElement("description"));
				description.setTextContent(node.getDocumentation().toString());
			}
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	/** 
	 * Generates the tags associated with generic type parameters for classes
	 * and methods
	 */
	@Override
	public Object visit(ASTTypeParameter node, Boolean secondVisit) throws ShadowException
	{
		// TODO: Capture/compute the type pattern associated with a type parameter
		
		currentNode = currentNode.appendChild(document.createElement("generic-parameter"));
		
		((Element) currentNode).setAttribute("name", node.getImage());
		
		currentNode = currentNode.getParentNode();
		
		return WalkType.NO_CHILDREN;
	}
	
	/** 
	 * Climbs the tree of packages and then appends corresponding tags to the
	 * given document node in top-bottom order. Returns the node corresponding
	 * to the lowest package.
	 */
	private Node appendPackages(Node root, Package lowest)
	{
		ArrayDeque<String> packages = new ArrayDeque<String>();
		
		// Climb up the chain of packages
		Package currentPackage = lowest;
		do
		{
			packages.addFirst(getPackageName(currentPackage));
			currentPackage = currentPackage.getParent();
		}
		while (currentPackage != null);
		
		// Create tags for them in top-bottom order
		Node current = root;
		for (String packageName : packages)
		{
			current = current.appendChild(document.createElement("package"));
			((Element) current).setAttribute("name", packageName);
		}
		
		return current;
	}
	
	/** Returns the proper name for a ClassType tag based on its TypeKind */
	private static String getClassTag(TypeKind kind)
	{
		switch (kind)
		{
			case CLASS: 	return "class";
			case EXCEPTION:	return "exception";
			case ENUM:		return "enum";
			case INTERFACE: return "interface";
			case SINGLETON:	return "singleton";
			default:		return null; // Shouldn't be possible
		}
	}
	
	/** Returns the qualified name of a given package */
	private static String getPackageName(Package containingPackage)
	{	
		if (containingPackage == null || containingPackage.getQualifiedName().isEmpty())
			return "default";
		else
			return containingPackage.getQualifiedName();
	}
}
