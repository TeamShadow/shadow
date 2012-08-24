package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.SequenceType;

public class TACReturn extends TACSimpleNode
{
	private TACOperand returnValue;
	public TACReturn()
	{
		this(null, new SequenceType(), null);
	}
	public TACReturn(TACNode node)
	{
		this(node, new SequenceType(), null);
	}
	public TACReturn(SequenceType expected, TACOperand op)
	{
		this(null, expected, op);
	}
	public TACReturn(TACNode node, SequenceType expected, TACOperand op)
	{
		super(node);
		if (expected.isEmpty())
		{
			if (op != null)
				throw new IllegalArgumentException();
		}
		else if (expected.size() == 1)
			returnValue = check(op, expected.getType(0));
		else
			returnValue = check(op, expected);
	}

	public boolean hasReturnValue()
	{
		return returnValue != null;
	}
	public TACOperand getReturnValue()
	{
		return returnValue;
	}

	@Override
	public int getNumOperands()
	{
		return hasReturnValue() ? 1 : 0;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (num == 0 && hasReturnValue())
			return getReturnValue();
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
		return hasReturnValue() ? "return " + getReturnValue() : "return";
	}
}
