package shadow.tac;

import java.util.ArrayList;
import java.util.List;

import shadow.parser.javacc.ASTClassOrInterfaceDeclaration;
import shadow.parser.javacc.SimpleNode;
import shadow.tac.nodes.TACNode;
import shadow.typecheck.type.ClassInterfaceBaseType;

public class TACModule
{
	private ClassInterfaceBaseType type;
	private String name;
	private TACNode initsEntryNode, initsExitNode;
	private List<TACField> fields;
	private List<TACMethod> methods;
	public TACModule(ClassInterfaceBaseType moduleType, String moduleName)
	{
		type = moduleType;
		name = moduleName;
		fields = new ArrayList<TACField>();
		methods = new ArrayList<TACMethod>();
	}

	public ClassInterfaceBaseType getType()
	{
		return type;
	}
	public String getName()
	{
		return name;
	}

	public String getMangledName() {
		return type.getMangledName();
	}
	
	public void addField(TACField field)
	{
		fields.add(field);
	}
	public List<TACField> getFields()
	{
		return fields;
	}
	
	public void addMethod(TACMethod method)
	{
		methods.add(method);
	}
	public List<TACMethod> getMethods()
	{
		return methods;
	}

	public void generateInitMethod(ASTClassOrInterfaceDeclaration node) {
		// TODO
	}

	// TODO: oh, duh, this is a TACDeclaration!
	// 		 extend TACDeclaration
	// 		 move and merge the following methods to TACDeclaration
	public void appendInitializationNode(SimpleNode node)
	{
		if (initsExitNode == null)
		{
			initsEntryNode = node.getEntryNode();
			initsExitNode = node.getExitNode();
		}
		else
		{
			initsExitNode.append(node.getEntryNode());
			initsExitNode = node.getExitNode();
		}
	}
	public void appendInitializationNode(TACNode node)
	{
		if (initsExitNode == null)
			initsEntryNode = initsExitNode = node;
		else
		{
			initsExitNode.append(node);
			initsExitNode = node;
		}
	}

	public TACNode getInitializationNode()
	{
		return initsExitNode;
	}
	public void setInitializationNode(TACNode node)
	{
		initsExitNode = node;
	}
	
	public TACNode getInitializationEntryNode() {
		return initsEntryNode;
	}

	public void setInitializationEntryNode(TACNode entryNode) {
		this.initsEntryNode = entryNode;
	}

	public TACNode getInitializationExitNode() {
		return initsExitNode;
	}

	public void setInitializationExitNode(TACNode exitNode) {
		this.initsExitNode = exitNode;
	}
}
