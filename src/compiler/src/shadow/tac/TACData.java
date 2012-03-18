package shadow.tac;

import java.util.Arrays;

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
	public TACNode appendAndGetChild(int index)
	{
		if (!hasChild(index))
			return null;
		TACData child = getChild(index);
		append(child);
		return child.getNode();
	}
	public void appendChildren()
	{
		for (int i = 0; i < getChildCount(); i++)
			appendChild(i);
	}
	
	@Override
	public String toString()
	{
		try {
			return super.toString();
		} catch (NullPointerException ex) {
			return Arrays.toString(children);
		}
	}
}
