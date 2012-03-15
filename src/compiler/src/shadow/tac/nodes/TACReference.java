package shadow.tac.nodes;

import shadow.tac.AbstractTACVisitor;
import shadow.typecheck.type.Type;

public class TACReference extends TACNode
{
	private TACNode ref;
	public TACReference(TACNode refNode)
	{
		ref = refNode;
	}
	
	@Override
	public Type getType()
	{
		return ref.getType();
	}
	@Override
	public String getSymbol()
	{
		return ref.getSymbol();
	}
	public TACNode getReference()
	{
		return ref;
	}
	
	@Override
	public String toString()
	{
		return getSymbol();
	}
	
	@Override
	public void accept(AbstractTACVisitor visitor)
	{
		visitor.visit(this);
	}
}
