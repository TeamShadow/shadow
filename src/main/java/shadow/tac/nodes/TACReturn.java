package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;

/** 
 * TAC representation of return statement
 * Example: return 5
 * @author Jacob Young
 */

public class TACReturn extends TACNode
{
	private TACOperand returnValue;

	public TACReturn(TACNode node, SequenceType expected)
	{
		this(node, expected, null);
	}

	public TACReturn(TACNode node, SequenceType expected, TACOperand op)
	{
		super(node);
		if (expected.isEmpty())
		{
			if (op != null)
				throw new IllegalArgumentException("Operand is not equal to null: " + op);
		}
		else if (expected.size() == 1)
			returnValue = check(op, expected.get(0));
		else
			returnValue = check(op, new SimpleModifiedType(expected));
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
		throw new IndexOutOfBoundsException("" + num);
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
