package shadow.tac;

import java.util.Iterator;
import java.util.NoSuchElementException;

import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACOperand;
import shadow.tac.nodes.TACSimpleNode;

public class TACNodeList extends TACNode implements Iterable<TACSimpleNode>
{
	public void append(TACNode node)
	{
		node.insertAfter(getPrevious());
	}
	public void prepend(TACNode node)
	{
		node.insertBefore(getNext());
	}

	public void insertAfter(TACNode node)
	{
		if (node == null || node == this || isEmpty())
			return;
		connect(getPrevious(), node.getNext());
		connect(node, getNext());
		clear();
	}
	public void insertBefore(TACNode node)
	{
		if (node == null || node == this || isEmpty())
			return;
		connect(node.getPrevious(), getNext());
		connect(getPrevious(), node);
		clear();
	}

	@Override
	public void remove()
	{
		throw new IllegalStateException();
	}

	public TACSimpleNode getFirst()
	{
		TACNode first = getNext();
		if (first instanceof TACSimpleNode)
			return (TACSimpleNode)first;
		return null;
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
		throw new UnsupportedOperationException("Cannot visit TACNodeList");
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

	protected static int indent = 0;
	@Override
	public String toString()
	{
		final String newline = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();
		for (TACSimpleNode node : this)
		{
			if (node instanceof TACCall && ((TACCall)node).getType() != null)
				continue;
			if (node instanceof TACOperand)
				continue;
			for (int i = 0; i < indent; i++)
				sb.append('\t');
			sb.append(node).append(';').append(newline);
		}
		return sb.toString();
	}
}
