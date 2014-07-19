package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;

public class TACDestinationPhiRef extends TACPhiRef implements TACDestination
{
	public TACDestinationPhiRef(TACNode node)
	{
		super(node);
	}

	@Override
	public void addEdge(TACOperand value, TACLabelRef label)
	{
		if (!(value instanceof TACLabelRef))
			throw new IllegalArgumentException("value must be a TACLabelRef");
		super.addEdge(value, label);
	}
	@Override
	public int getNumPossibilities()
	{
		return getSize();
	}
	@Override
	public TACLabelRef getPossibility(int num)
	{
		return (TACLabelRef)getValue(num);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}
}
