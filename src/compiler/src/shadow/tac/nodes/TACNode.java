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

	public abstract void accept(TACVisitor visitor) throws ShadowException;
}
