package shadow.doctool;

import org.apache.logging.log4j.Logger;

import shadow.Loggers;
import shadow.AST.ASTWalker.WalkType;
import shadow.AST.AbstractASTVisitor;
import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.ASTEnumDeclaration;
import shadow.parser.javacc.ASTFieldDeclaration;
import shadow.parser.javacc.ASTGenericDeclaration;
import shadow.parser.javacc.ASTMethodDeclaration;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;

public class DocumentationVisitor extends AbstractASTVisitor 
{
	private static final Logger logger = Loggers.DOC_TOOL;
	
	int depth = 0;
	
	@Override
	public Object visit(ASTMethodDeclaration node, Boolean secondVisit) throws ShadowException
	{
		printDocumentation(node, depth);
		
		return WalkType.NO_CHILDREN;
	}
	
	@Override
	public Object visit(ASTFieldDeclaration node, Boolean secondVisit) throws ShadowException
	{
		printDocumentation(node, depth);
		
		return WalkType.NO_CHILDREN;
	}
	
	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit) throws ShadowException
	{
		if (secondVisit) {
			depth--;
		} else {
			printDocumentation(node, depth);
			depth++;
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTEnumDeclaration node, Boolean secondVisit) throws ShadowException
	{
		if (secondVisit) {
			depth--;
		} else {
			printDocumentation(node, depth);
			depth++;
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	// TODO: Figure out what exactly this is and whether or not it will need to
	// have documentation support
	@Override
	public Object visit(ASTGenericDeclaration node, Boolean secondVisit) throws ShadowException 
	{
		if (secondVisit) {
			depth--;
		} else {
			printDocumentation(node, depth);
			depth++;
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	private static void printDocumentation(Node node, int depth)
	{
		System.out.print(node.getDocumentation());
		System.out.println(node);
	}
}
