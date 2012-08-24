package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACLoad extends TACOperand
{
	private TACReference reference;
	public TACLoad(TACReference ref)
	{
		reference = ref;
	}
	public TACLoad(TACNode node, TACReference ref)
	{
		super(node);
		reference = ref;
	}

	public TACReference getReference()
	{
		return reference;
	}

	@Override
	public Type getType()
	{
		return reference.getType();
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
			return reference;
		throw new IndexOutOfBoundsException("num");
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return reference.toString();
	}
}
