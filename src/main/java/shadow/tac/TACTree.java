package shadow.tac;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import shadow.output.text.TextOutput;
import shadow.parser.javacc.ShadowException;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACOperand;
import shadow.tac.nodes.TACSequence;
import shadow.tac.nodes.TACSimpleNode;

/**
 * TACTree contains a dynamic array of TACTrees.
 * It provides a useful way to convert a tree of TAC nodes into a linear list.
 * Element 0 is always the parent TACTree.
 * @author Jacob Young
 * @author Barry Wittman
 */
public class TACTree extends TACNode implements Iterable<TACSimpleNode>
{
	private int index = 0;
	private TACTree[] children;
	public TACTree()
	{
		this(null, 0, null);
	}
	public TACTree(int numChildren, TACBlock block)
	{
		this(null, numChildren, block);
	}
	private TACTree(TACTree parent, int numChildren, TACBlock block)
	{
		super(null);	
		if (numChildren < 0)
			throw new IllegalArgumentException("numChildren < 0");
		if (numChildren == 0)
			numChildren = 10;
		children = new TACTree[numChildren + 1];
		children[0] = parent;
		setBlock(block);
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
		return children[index] = new TACTree(this, numChildren, getBlock());
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
			TACTree child = children[i];
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
	public TACOperand appendChild(int i)
	{
		if (i >= index)
			return null;
		TACTree child = children[++i];
		if (child == null)
			return null;
		TACOperand last = null;
		if (child.getLast() instanceof TACOperand)
			last = (TACOperand)child.getLast();
		append(child);
		children[i] = null;
		return last;
	}
	
	public TACTree done()
	{
		appendAllChildren();
		return children[0];
	}
	
	public TACOperand collapseTree()
	{
		TACNode next = getNext();
		remove();  //removes tree from list
		if( next instanceof TACOperand )
			return (TACOperand)next;
		return null;
	}

	
	/**
	 * Inserts parameter node before current list
	 */
	
	public void append(TACNode node)
	{
		node.insertAfter(getPrevious());
	}
	
	public void insertAfter(TACNode node)
	{
		if (node == null || node == this || isEmpty())
			return;
		connect(getPrevious(), node.getNext(), node, getNext());
		clear();
	}
	public void insertBefore(TACNode node)
	{
		if (node == null || node == this || isEmpty())
			return;
		connect(node.getPrevious(), getNext(), getPrevious(), node);
		clear();
	}

	public TACSimpleNode getLast()
	{
		TACNode last = getPrevious();
		if (last instanceof TACSimpleNode)
			return (TACSimpleNode)last;
		return null;
	}
	
	public boolean isEmpty()
	{
		return !(getNext() instanceof TACSimpleNode);
	}

	@Override
	public void accept(TACVisitor visitor)
	{
		throw new UnsupportedOperationException("Cannot visit TACTree");
	}

	@Override
	public Iterator<TACSimpleNode> iterator()
	{
		return new ForwardIterator(getNext());
	}
	private static class ForwardIterator extends NodeIterator
	{
		public ForwardIterator(TACNode start)
		{
			super(start);
		}

		@Override
		protected TACNode iterate(TACNode current)
		{
			return current.getNext();
		}
	}

	public Iterator<TACSimpleNode> reverseIterator()
	{
		return new ReverseIterator(getPrevious());
	}
	private static class ReverseIterator extends NodeIterator
	{
		public ReverseIterator(TACNode end)
		{
			super(end);
		}

		@Override
		protected TACNode iterate(TACNode current)
		{
			return current.getPrevious();
		}
	}

	private abstract static class NodeIterator
			implements Iterator<TACSimpleNode>
	{
		private TACSimpleNode current;
		private TACNode next;
		public NodeIterator(TACNode begin)
		{
			current = null;
			next = begin;
		}

		@Override
		public boolean hasNext()
		{
			return next instanceof TACSimpleNode;
		}

		@Override
		public TACSimpleNode next()
		{
			if (!(next instanceof TACSimpleNode))
				throw new NoSuchElementException();
			current = (TACSimpleNode)next;
			next = iterate(current);
			return current;
		}

		@Override
		public void remove()
		{
			if (current == null)
				throw new IllegalStateException();
			current.remove();
			current = null;
		}

		protected abstract TACNode iterate(TACNode current);
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
