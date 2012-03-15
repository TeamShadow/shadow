package shadow.tac.nodes;

import shadow.output.AbstractTACVisitor;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Type;

public abstract class TACNode implements ModifiedType
{
	public static class Dangling extends TACNode
	{
		private TACNode delegate;
		@Override
		public void append(TACNode node)
		{
			delegate.append(node);
			delegate = node;
		}

		@Override
		public TACNode getNext()
		{
			return delegate.getNext();
		}

		@Override
		public String getSymbol()
		{
			return delegate.getSymbol();
		}

		@Override
		public void setSymbol(String symbol)
		{
			delegate.setSymbol(symbol);
		}

		@Override
		public String getLabel()
		{
			return delegate.getLabel();
		}

		@Override
		public void setLabel(String label)
		{
			delegate.setLabel(label);
		}

		@Override
		public Type getType()
		{
			return delegate.getType();
		}
		
		@Override
		public String toString()
		{
			return delegate.toString();
		}
		
		@Override
		public void accept(AbstractTACVisitor visitor)
		{
			delegate.accept(visitor);
		}

		
	}
	
	protected TACNode()
	{
		targeted = consumed = 0;
		next = null;
		symbol = label = null;
	}
	
	private int targeted, consumed;
	public final void addTarget()
	{
		if (consumed != 0)
			throw new IllegalStateException("Cannot add target while consuming targets.");
		++targeted;
	}
	public final boolean consumeTarget()
	{
		if (++consumed == targeted)
		{
			consumed = 0;
			return true;
		}
		return false;
	}

	private TACNode next;
	public TACNode getNext()
	{
		return next;
	}
	public void append(TACNode node)
	{
		if (next == null)
		{
			if (node instanceof Dangling)
				((Dangling)node).delegate = this;
			else
				next = node;
		}
		else
			next.append(node);
	}
	
	private String symbol, label;
	public String getSymbol()
	{
		return symbol;
	}
	public void setSymbol(String symbol)
	{
		this.symbol = symbol;
	}
	public String getLabel()
	{
		return label;
	}
	public void setLabel(String label)
	{
		this.label = label;
	}
	
	@Override
	public int getModifiers() {			
		return 0;
	}
	
	public abstract Type getType();
	public abstract void accept(AbstractTACVisitor visitor);
}
