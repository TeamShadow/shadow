package shadow.tac.nodes;

import shadow.parser.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACTypeId extends TACOperand
{
	private TACOperand operand;
	
	public TACTypeId(TACNode node, TACOperand op)
	{
		super(node);
		operand = op;
	}

	public TACOperand getOperand()
	{
		return operand;
	}

	@Override
	public Type getType()
	{
		return Type.INT;
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
			return getOperand();
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
		return "typeid(" + getOperand() + ')';
	}
}
