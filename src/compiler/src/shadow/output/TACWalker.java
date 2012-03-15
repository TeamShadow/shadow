package shadow.output;

import shadow.tac.AbstractTACVisitor;
import shadow.tac.TACDeclaration;
import shadow.tac.TACField;
import shadow.tac.TACMethod;
import shadow.tac.TACModule;
import shadow.tac.nodes.TACNode;

public class TACWalker
{
	private AbstractTACVisitor visitor;
	public TACWalker(AbstractTACVisitor visitor)
	{
		this.visitor = visitor;
	}
	
	public void walk()
	{
		visitor.startFile();
		
		TACModule module = visitor.getModule();

		// walk through the fields first
		visitor.startFields();
		for (TACField field : module.getFields())
			walk(field);
		visitor.endFields();
		
		// walk through the methods
		for (TACMethod method : module.getMethods())
		{
			visitor.startMethod(method);
			if (method.getName().equals("constructor"))
				walk(module);
			walk(method);
			visitor.endMethod(method);
		}
		
		visitor.endFile();
	}
	
	private void walk(TACDeclaration declaration)
	{
		TACNode entryNode = declaration.getEntryNode(), exitNode = declaration.getExitNode();
		do {
			visitor.visit(entryNode);
			entryNode = entryNode.getNext();
		} while (entryNode != null && entryNode != exitNode);
	}
}
