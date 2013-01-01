package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;

public class TACLandingpad extends TACSimpleNode
{
	private TACBlock block;
	public TACLandingpad(TACBlock blockRef)
	{
		this(null, blockRef);
	}
	public TACLandingpad(TACNode node, TACBlock blockRef)
	{
		super(node);
		block = blockRef;
	}

	public TACBlock getBlock()
	{
		return block;
	}

	@Override
	public int getNumOperands()
	{
		return 1;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (num == 0)
			return getBlock();
		throw new IndexOutOfBoundsException();
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}
}
