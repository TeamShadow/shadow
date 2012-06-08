package shadow.tac.nodes;

import java.io.IOException;

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
	public TACNode getReference()
	{
		return ref;
	}
	
	@Override
	public String toString()
	{
		return ref.toString();
	}
	
	@Override
	public void accept(AbstractTACVisitor visitor) throws IOException
	{
		visitor.visit(this);
	}
}
