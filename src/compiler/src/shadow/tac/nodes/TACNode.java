package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACBuilder;
import shadow.tac.TACVisitor;

/**
 * Abstract class to organize basic TAC operations
 * Each TACNode serves as a node in a circular doubly-linked list
 * @author Jacob Young
 */


public abstract class TACNode
{
	private TACNode prev, next;
	
	private static TACBuilder builder;
	
	public static TACBuilder getBuilder()
	{
		return builder;
	}
	
	public static void setBuilder(TACBuilder builder)
	{
		TACNode.builder = builder;
	}
	
	protected TACNode()
	{
		this(null);
	}
	
	/**
	 * Constructor adds current node *before* parameter node
	 * @param node
	 */
	
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
	
	/**
	 * Puts input node after current node
	 * @param node
	 */
	
	public void append(TACNode node)
	{
		node.insertAfter(this);
	}
	
	/**
	 * Puts input node before current node
	 * @param node
	 */
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

	/**
	 * Inserts current node after parameter node
	 * @param node
	 */
	
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
		connect(first, second, second, third);
	}
	protected final void connect(TACNode first, TACNode second,
			TACNode third, TACNode fourth)
	{
		connect(first, second);
		connect(third, fourth);
	}
	protected final void connect(TACNode first, TACNode second)
	{
		first.next = second;
		second.prev = first;
	}

	public abstract void accept(TACVisitor visitor) throws ShadowException;
}
