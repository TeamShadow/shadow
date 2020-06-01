package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVisitor;

public class TACCleanupRet extends TACNode
{	
	
	public TACCleanupRet(TACNode node)
	{
		super(node);
	}
	
	@Override
	public int getNumOperands()
	{
		return 0;
	}
	
	@Override
	public TACOperand getOperand(int num)
	{		
		throw new IndexOutOfBoundsException("" + num);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}
}
