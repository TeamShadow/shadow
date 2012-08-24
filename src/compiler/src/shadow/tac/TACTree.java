package shadow.tac;

import java.util.Arrays;

import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACOperand;
import shadow.tac.nodes.TACSequence;
import shadow.tac.nodes.TACSimpleNode;

public class TACTree extends TACNodeList
{
	private int index = 0;
	private TACTree[] children;
	protected TACTree()
	{
		this(null, 0);
	}
	public TACTree(TACTree parent, int numChildren)
	{
		if (numChildren < 0)
			throw new IllegalArgumentException("numChildren < 0");
		if (numChildren == 0)
			numChildren = 10;
		children = new TACTree[numChildren + 1];
		children[0] = parent;
	}
	public TACTree getParent()
	{
		return children[0];
	}
	public TACTree getChild(int index)
	{
		return children[index + 1];
	}
	public TACTree current()
	{
		return children[index];
	}
	public TACTree next()
	{
		return next(0);
	}
	public TACTree next(int numChildren)
	{
		if (++index == children.length)
			children = Arrays.copyOf(children, children.length + 10);
		return children[index] = new TACTree(this, numChildren);
	}
	public int getNumChildren()
	{
		return index;
	}
	public TACOperand appendChild(int i)
	{
		if (i >= index)
			return null;
		TACNodeList child = children[++i];
		if (child == null)
			return null;
		TACOperand last = null;
		if (child.getLast() instanceof TACOperand)
			last = (TACOperand)child.getLast();
		append(child);
		children[i] = null;
		return last;
	}
	public TACSequence appendChildRemoveSequence(int i)
	{
		TACOperand last = appendChild(i);
		if (!(last instanceof TACSequence))
			return null;
		last.remove();
		return (TACSequence)last;
	}
	public TACOperand deleteChild(int i)
	{
		if (i >= index)
			return null;
		TACNodeList child = children[++i];
		if (child == null)
			return null;
		TACOperand last = null;
		if (child.getLast() instanceof TACOperand)
			last = (TACOperand)child.getLast();
		children[i] = null;
		return last;
	}
	public TACOperand prependChild(int i)
	{
		if (i >= index)
			return null;
		TACNodeList child = children[++i];
		if (child == null)
			return null;
		TACOperand last = null;
		if (child.getLast() instanceof TACOperand)
			last = (TACOperand)child.getLast();
		prepend(child);
		children[i] = null;
		return last;
	}
	public TACSimpleNode prependAllChildren()
	{
		TACSimpleNode last = null;
		while (index > 0)
		{
			TACNodeList child = children[index];
			if (child != null)
			{
				if (last == null)
					last = child.getLast();
				prepend(child);
				children[index] = null;
			}
			index--;
		}
		return last;
	}
	public TACTree done()
	{
		prependAllChildren();
		return children[0];
	}
	public void appendChildTo(int index, TACNode node)
	{
		node.append(children[index + 1]);
		children[index + 1] = null;
	}
	public void appendChildrenTo(TACNode node)
	{
		for (int i = 1; i <= index; i++)
		{
			node.append(children[i]);
			children[i] = null;
		}
		index = 0;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= index; i++)
			if (children[i] != null)
				sb.append(children[i]);
		return sb.append(super.toString()).toString();
	}
}
