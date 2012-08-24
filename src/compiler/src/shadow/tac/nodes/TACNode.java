package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;

public abstract class TACNode
{
	private TACNode prev, next;
	protected TACNode()
	{
		this(null);
	}
	protected TACNode(TACNode node)
	{
		clear();
		insertBefore(node);
	}

	public final TACNode getPrevious()
	{
		return prev;
	}
	public final TACNode getNext()
	{
		return next;
	}

	public void append(TACNode node)
	{
		node.insertAfter(this);
	}
	public void prepend(TACNode node)
	{
		node.insertBefore(this);
	}

	public void replaceWith(TACNode node)
	{
		if (node == this)
			return;
		node.insertBefore(this);
		remove();
	}
	public void replace(TACNode node)
	{
		if (node == this)
			return;
		insertBefore(node);
		node.remove();
	}

	public void insertAfter(TACNode node)
	{
		if (node == this)
			return;
		remove();
		if (node != null)
			connect(node, this, node.getNext());
	}
	public void insertBefore(TACNode node)
	{
		if (node == this)
			return;
		remove();
		if (node != null)
			connect(node.getPrevious(), this, node);
	}
	public void remove()
	{
		connect(prev, next);
		clear();
	}

	protected final void clear()
	{
		connect(this, this);
	}
	protected final void connect(TACNode first, TACNode second, TACNode third)
	{
		connect(first, second);
		connect(second, third);
	}
	protected final void connect(TACNode first, TACNode second)
	{
		first.next = second;
		second.prev = first;
	}

//	private TACNode previous, next;
//	protected TACNode()
//	{
//		clear();
//	}
//	public TACNode(TACNode node)
//	{
//		node.prepend(this);
//	}
//
//	public TACNode getNext()
//	{
//		return next;
//	}
//	public TACNode getPrevious()
//	{
//		return previous;
//	}
//	public TACNode getStart()
//	{
//		return this;
//	}
//	public TACNode getEnd()
//	{
//		return this;
//	}
//	public boolean isEmpty()
//	{
//		return false;
//	}
//
//	public void prepend(TACNode node)
//	{
//		if (node.isEmpty())
//			return;
//		TACNode start = node.getStart(), end = node.getEnd();
//		node.clear();
//		previous.prependTo(start);
//		appendTo(end);
//	}
//	private void prependTo(TACNode node)
//	{
//		next = node;
//		node.previous = this;
//	}
//
//	public void append(TACNode node)
//	{
//		if (node.isEmpty())
//			return;
//		TACNode start = node.getStart(), end = node.getEnd();
//		node.clear();
//		prependTo(start);
//		next.appendTo(end);
//	}
//	private void appendTo(TACNode node)
//	{
//		node.prependTo(this);
//	}
//
//	public void replace(TACNode node)
//	{
//		prepend(node);
//		remove();
//	}
//	public void remove()
//	{
//		previous.prependTo(next);
//		clear();
//	}
//	private void clear()
//	{
//		previous = next = this;
//	}

	public abstract void accept(TACVisitor visitor) throws ShadowException;
}
