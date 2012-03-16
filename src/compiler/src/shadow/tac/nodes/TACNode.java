package shadow.tac.nodes;

import java.io.IOException;

import shadow.tac.AbstractTACVisitor;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Type;

public abstract class TACNode implements ModifiedType
{
	protected TACNode()
	{
		next = null;
		symbol = null;
	}
	
	private TACNode next;
	public TACNode getNext()
	{
		return next;
	}
	public void append(TACNode node)
	{
		if (next == null)
			next = node;
		else
			next.append(node);
	}
	
	private String symbol;
	public String getSymbol()
	{
		return symbol;
	}
	public void setSymbol(String symbol)
	{
		this.symbol = symbol;
	}
	
	@Override
	public int getModifiers() {			
		return 0;
	}
	
	public abstract Type getType();
	public abstract void accept(AbstractTACVisitor visitor) throws IOException;
}
