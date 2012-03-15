package shadow.tac;

import shadow.tac.nodes.TACNode;

public class TACData extends TACDeclaration
{
	private TACData[] children;
	public TACData(TACData[] childrenData)
	{
		children = childrenData;
	}
	
	public int getChildCount()
	{
		return children.length;
	}
	public TACData getChild(int index)
	{
		return children[index];
	}
	public boolean hasChild(int index)
	{
		return index < getChildCount() &&
				getChild(index) != null;
	}
	
	public TACNode getChildNode(int index)
	{
		return getChild(index).getNode();
	}
	public void appendChild(int index)
	{
		if (hasChild(index))
			append(getChild(index));
	}
	public void appendChildren()
	{
		for (int i = 0; i < getChildCount(); i++)
			appendChild(i);
	}
}
