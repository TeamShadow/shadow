package shadow.tac;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.NoSuchElementException;

import shadow.output.text.TextOutput;
import shadow.parser.javacc.ShadowException;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACSimpleNode;

public class TACNodeList extends TACNode implements Iterable<TACSimpleNode>
{
	/**
	 * Inserts parameter node before current list
	 */
	
	public void append(TACNode node)
	{
		node.insertAfter(getPrevious());
	}
	
	/**
	 * Inserts parameter node after current list
	 */
	public void prepend(TACNode node)
	{
		node.insertBefore(getNext());
	}

	/**
	 * Inserts parameter nodes before current list 
	 * and inserts whatever is after nodes after what used to be 
	 * before the list
	 * 
	 * How does this make sense?
	 */
	//neither of these are used, maybe they are wrong
	/*
	public void appendAll(TACNode nodes)
	{
		connect(getPrevious(), nodes.getNext(), nodes, this);
	}
	public void prependAll(TACNode nodes)
	{
		connect(this, nodes.getNext(), nodes, getNext());
	}
	*/

	
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

	@Override
	public String toString()
	{
		StringWriter writer = new StringWriter();
		try
		{
			new TextOutput(writer).walk(this);
		}
		catch (ShadowException ex)
		{
			return "Error";
		}
		return writer.toString();
	}

//	protected static int indent = 0;
//	protected static StringBuilder indent(StringBuilder sb) {
//		for (int i = 0; i < indent; i++)
//			sb.append('\t');
//		return sb;
//	}
//	@Override
//	public String toString()
//	{
//		int tempLabel = 0;
//		final String newline = System.getProperty("line.separator");
//		StringBuilder sb = new StringBuilder();
//		for (TACSimpleNode node : this)
//		{
//			if (node instanceof TACLabelRef)
//			{
//				((TACLabelRef)node).setName("label" + tempLabel++);
//				continue;
//			}
//			else if (node instanceof TACCall)
//			{
//				if (((TACCall)node).getType() != null)
//					continue;
//			}
//			else if (node instanceof TACOperand)
//				continue;
//			if (node instanceof TACLabel)
//				sb.append(node).append(':').append(newline);
//			else
//				indent(sb).append(node).append(';').append(newline);
//		}
//		return sb.toString();
//	}
}
