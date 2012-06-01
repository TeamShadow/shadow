package shadow.tac;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import shadow.AST.ASTWalker;
import shadow.AST.ASTWalker.WalkType;
import shadow.AST.AbstractASTVisitor;
import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.ShadowParser.ModifierSet;
import shadow.typecheck.type.ClassInterfaceBaseType;
import shadow.typecheck.type.MethodSignature;

public class TACBuilder extends AbstractASTVisitor
{
	private List<TACModule> modules;
	private ASTToTACConverter converter;
	public TACBuilder()
	{
		modules = new LinkedList<TACModule>();
		converter = new ASTToTACConverter();
	}
	
	public void build(Node node) throws ShadowException
	{
		new ASTWalker(this).walk(node);		
	}
	
	public List<TACModule> getModules()
	{
		return modules;
	}
	
	public Object visit(ASTClassOrInterfaceDeclaration node, Boolean secondVisit) throws ShadowException {
		ClassInterfaceBaseType type = (ClassInterfaceBaseType)node.getType();
		
		// create a new class
		TACModule module = new TACModule(type, node.getImage()); 
		
		converter.outerType = type;
		
		Set<Integer> visited = new HashSet<Integer>();
		// go through the fields
		for (Node field : type.getFields().values())
		{
			if (visited.add(System.identityHashCode(field)))
			{
				module.append(converter.walk(field));
				for (int i = 1; i < field.jjtGetNumChildren(); i++)
					module.addField(new TACField(field.getType(),
							field.jjtGetChild(i).jjtGetChild(0).getImage()));
			}
		}
		visited = null;
		
		// go through the methods
		boolean foundConstructor = false;
		for (List<MethodSignature> methods : type.getMethodMap().values())
			for (MethodSignature method : methods)
		{
			if (method.getSymbol().equals("constructor"))
				foundConstructor = true;
			
			if (ModifierSet.isNative(method.getModifiers()))
				module.addMethod(new TACMethod(method));
			else
				module.addMethod(new TACMethod(method,
						converter.walk(method.getNode())));
		}
		
		// generate a default constructor
		if (!foundConstructor)
			module.addMethod(new TACMethod(
					new MethodSignature(type, "constructor", 0, null)));
		
		// add the module
		modules.add(module);
		
		return WalkType.NO_CHILDREN;
	}
}
