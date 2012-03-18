package shadow.tac.nodes;

import shadow.typecheck.type.Type;

public abstract class TACPrefixed extends TACNode
{
	private TACNode prefix;
	
	public TACPrefixed()
	{
		prefix = null;
	}
	public TACPrefixed(TACNode prefixNode)
	{
		prefix = prefixNode;
	}
	
	public void setPrefix(TACNode prefixNode)
	{
		prefix = prefixNode;
	}
	public abstract boolean expectsPrefix();
	public abstract Type expectedPrefixType();
	public boolean isPrefixed()
	{
		return prefix != null;
	}
	public TACNode getPrefix()
	{
		return prefix;
	}
}
