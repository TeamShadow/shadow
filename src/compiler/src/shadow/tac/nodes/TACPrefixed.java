package shadow.tac.nodes;

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
	public boolean isPrefixed()
	{
		return prefix != null;
	}
	public TACNode getPrefix()
	{
		return prefix;
	}
}
