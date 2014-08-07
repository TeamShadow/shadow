package shadow.tac;

import java.io.StringWriter;
import java.util.Arrays;

import shadow.output.text.TextOutput;
import shadow.parser.javacc.ShadowException;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACOperand;
import shadow.tac.nodes.TACSequence;
import shadow.tac.nodes.TACSimpleNode;

/**
 * TACTree contains a dynamic array of TACTrees
 * Element 0 is always the parent TACTree 
 * @author Jacob Young
 *
 */

public class TACTree extends TACNodeList
{
	private int index = 0;
	private TACTree[] children;
	protected TACTree()
	{
		this(null, 0);
	}
	protected TACTree(int numChildren)
	{
		this(null, numChildren);
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
	
	/**
	 * Takes the ith child tree and appends it to the current tree.
	 * Bizarrely, appending means inserting the child tree *before* the current tree.
	 * Sets ith child to null after appending.
	 * If the last thing in the child tree is an operand (not another tree),
	 * it is returned.
	 * Otherwise, null is returned.   
	 * @param i
	 * @return
	 */
	
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
	public TACSimpleNode appendAllChildren()
	{
		TACSimpleNode last = null;
		for (int i = 1; i <= index; i++)
		{
			TACNodeList child = children[i];
			if (child != null)
			{
				last = child.getLast();
				append(child);
				children[i] = null;
			}
		}
		index = 0;
		return last;
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
		appendAllChildren();
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
		StringWriter writer = new StringWriter();
		try
		{
			TextOutput output = new TextOutput(writer);
			output.walk(this);
			if (!writer.toString().isEmpty())
				writer.write(System.getProperty("line.separator"));
			for (int i = 1; i <= index; i++)
				if (children[i] != null)
					output.walk(children[i]);
		}
		catch (ShadowException ex)
		{
			return "Error";
		}
		return writer.toString();
	}
}
