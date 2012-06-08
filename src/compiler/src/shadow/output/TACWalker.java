package shadow.output;

import java.io.IOException;

import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.ShadowParser.ModifierSet;
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
	
	public void walk() throws ShadowException
	{
		try {
			visitor.startFile();
			
			TACModule module = visitor.getModule();
	
			// walk through the fields first
			visitor.startFields();
			for (TACField field : module.getFields())
				walk(field);
			visitor.endFields();
			
			// walk through the methods
			for (TACMethod method : module.getMethods())
				if (!ModifierSet.isNative(method.getSignature().getModifiers()))
			{
				visitor.startMethod(method);
				if (method.getName().equals("constructor"))
					walk(module);
				walk(method);
				visitor.endMethod(method);
			}
			
			visitor.endFile();
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new ShadowException(ex.getLocalizedMessage());
		}
	}
	
	private void walk(TACDeclaration declaration) throws IOException
	{
		TACNode entryNode = declaration.getEntryNode(),
				exitNode = declaration.getExitNode();
		while (entryNode != null) {
			visitor.visit(entryNode);
			if (entryNode == exitNode)
				return;
			entryNode = entryNode.getNext();
		}
	}
}
