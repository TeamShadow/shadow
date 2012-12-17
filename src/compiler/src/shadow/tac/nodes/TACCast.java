package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACCast extends TACOperand
{
	private Type type;
	private TACOperand operand;
	public TACCast(TACNode node, Type newType, TACOperand op)
	{
		super(node);
		type = newType;
		operand = check(op, op.getType());
	}

	public TACOperand getOperand()
	{
		return operand;
	}

	@Override
	public Type getType()
	{
		return type;
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
			return operand;
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
		return "cast<" + type + '>' + operand;
	}
}
