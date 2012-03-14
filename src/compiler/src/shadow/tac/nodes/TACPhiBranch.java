package shadow.tac.nodes;

import shadow.output.AbstractTACVisitor;
import shadow.typecheck.type.Type;

public class TACPhiBranch extends TACNode
{
	private TACNode value;
	private TACPhi phi;
	public TACPhiBranch(TACNode valueNode, TACPhi phiNode)
	{
		value = valueNode;
		phi = phiNode;
	}
	
	@Override
	public Type getType()
	{
		return value.getType();
	}
	public TACNode getValue()
	{
		return value;
	}
	public TACPhi getPhi()
	{
		return phi;
	}
	
	@Override
	public void accept(AbstractTACVisitor visitor)
	{
		visitor.visit(this);
	}
}
