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
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.type.Type;

public class DocumentationVisitor extends AbstractASTVisitor 
{
	private static final Logger logger = Loggers.DOC_TOOL;
	
	int depth = 0;
	
	@Override
	public Object visit(ASTMethodDeclaration node, Boolean secondVisit) throws ShadowException
	{
		printType(node.getType(), depth);
		
		return WalkType.NO_CHILDREN;
	}
	
	@Override
	public Object visit(ASTFieldDeclaration node, Boolean secondVisit) throws ShadowException
	{
		printType(node.getType(), depth);
		
		return WalkType.NO_CHILDREN;
	}
	
	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit) throws ShadowException
	{
		if (secondVisit) {
			depth--;
		} else {
			System.out.print(node.getDocumentation().toString());
			printType(node.getType(), depth);
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
			printType(node.getType(), depth);
			depth++;
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	@Override
	public Object visit(ASTGenericDeclaration node, Boolean secondVisit) throws ShadowException 
	{
		if (secondVisit) {
			depth--;
		} else {
			printType(node.getType(), depth);
			depth++;
		}
		
		return WalkType.POST_CHILDREN;
	}
	
	private static void printType(Type type, int depth)
	{
		if (type == null)
			return;
		
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < depth; i++)
			builder.append('\t');
		
		builder.append(type.getQualifiedName());
		//logger.info(builder.toString());
		System.out.println(builder.toString());
	}
}
