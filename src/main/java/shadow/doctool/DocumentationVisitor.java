package shadow.doctool;

import java.io.StringWriter;
import java.util.ArrayDeque;

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

import shadow.Loggers;
import shadow.AST.ASTWalker.WalkType;
import shadow.AST.AbstractASTVisitor;
import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.ASTCompilationUnit;
import shadow.parser.javacc.ASTEnumDeclaration;
import shadow.parser.javacc.ASTFieldDeclaration;
import shadow.parser.javacc.ASTGenericDeclaration;
import shadow.parser.javacc.ASTMethodDeclaration;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.Package;

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

		for (String parameter : type.getParameterNames())
		{
			System.out.println(parameter);
			System.out.println(type.getParameterType(parameter).getType());
		}
		*/
		
		Element method = (Element) currentNode.appendChild(document.createElement("method"));
		
		// The first child of an ASTMethodDeclaration is an ASTMethodDeclarator.
		// We retrieve the method name from this child
		method.setAttribute("name", node.jjtGetChild(0).getImage());
		
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
		
		return WalkType.NO_CHILDREN;
	}
	
	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit) throws ShadowException
	{
		if (secondVisit) {
			// Exit the tag for this class
			currentNode = currentNode.getParentNode();
		} else {
			// Create package tags if this is the outermost class
			if (node.jjtGetParent() instanceof ASTCompilationUnit)
			{
				ArrayDeque<String> packages = new ArrayDeque<String>();
				
				// Climb the chain of packages
				Package currentPackage = node.getType().getPackage();
				do
				{
					packages.addFirst(getPackageName(currentPackage));
					currentPackage = currentPackage.getParent();
				}
				while (currentPackage != null);
				
				for (String packageName : packages)
				{
					currentNode = currentNode.appendChild(document.createElement("package"));
					((Element) currentNode).setAttribute("name", packageName);
				}
			}
			
			currentNode = currentNode.appendChild(document.createElement("class"));
			((Element) currentNode).setAttribute("name", node.getImage());
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTEnumDeclaration node, Boolean secondVisit) throws ShadowException
	{
		if (secondVisit) {
			
		} else {
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	// TODO: Figure out what exactly this is and whether or not it will need to
	// have documentation support
	@Override
	public Object visit(ASTGenericDeclaration node, Boolean secondVisit) throws ShadowException 
	{
		if (secondVisit) {
			
		} else {
		}
		
		return WalkType.POST_CHILDREN;
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
