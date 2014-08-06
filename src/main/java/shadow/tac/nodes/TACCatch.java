package shadow.tac.nodes;

import shadow.parser.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ExceptionType;

public class TACCatch extends TACOperand
{
	private ExceptionType type;

	public TACCatch(TACNode node, ExceptionType catchType)
	{
		super(node);
		type = catchType;
	}

	@Override
	public ExceptionType getType()
	{
		return type;
	}

	@Override
	public int getNumOperands()
	{
		return 0;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		throw new IndexOutOfBoundsException();
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}
}
