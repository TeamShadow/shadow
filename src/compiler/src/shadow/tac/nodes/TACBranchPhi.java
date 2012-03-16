package shadow.tac.nodes;

import java.io.IOException;

import shadow.tac.AbstractTACVisitor;
import shadow.typecheck.type.Type;

public class TACBranchPhi extends TACBranch
{
	private TACNode value;
	private TACPhi phi;
	public TACBranchPhi(TACLabel labelNode, TACNode valueNode, TACPhi phiNode)
	{
		super(labelNode);
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
	public void accept(AbstractTACVisitor visitor) throws IOException
	{
		visitor.visit(this);
	}
}
