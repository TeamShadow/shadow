package shadow.tac;

import shadow.tac.nodes.TACNode;

public abstract class TACDeclaration
{
	private TACNode entry, exit;
	public TACDeclaration(TACNode singleNode)
	{
		entry = exit = singleNode;
	}
	public TACDeclaration(TACNode entryNode, TACNode exitNode)
	{
		entry = entryNode;
		exit = exitNode;
	}

	public TACNode getEntryNode()
	{
		return entry;
	}
	public TACNode getExitNode()
	{
		return exit;
	}
}
