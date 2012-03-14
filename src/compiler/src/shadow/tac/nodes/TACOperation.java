package shadow.tac.nodes;

import shadow.typecheck.type.Type;

abstract class TACOperation extends TACNode
{
	public static interface TACOperator
	{
		char getSymbol();
	}
	
	private TACOperator operator;
	private TACNode left, right;
	protected TACOperation(TACOperator op, TACNode op1)
	{
		operator = op;
		left = op1;
		right = null;
	}
	protected TACOperation(TACNode op1, TACOperator op, TACNode op2)
	{
		if (!op1.getType().equals(op2.getType()))
		{
			if (op1.getType().isSubtype(op2.getType()));
// FIXME:		op1 = new TACCast(op2.getType(), op1);
			else if (op2.getType().isSubtype(op1.getType()));
// FIXME:		op2 = new TACCast(op1.getType(), op2);
			else
				throw new IllegalArgumentException("Incompatible types.");
		}
		operator = op;
		left = op1;
		right = op2;
	}
	
	@Override
	public Type getType()
	{
		return left.getType();
	}
	public TACNode getOperand()
	{
		return left;
	}
	public TACNode getFirstOperand()
	{
		return left;
	}
	public TACOperator getOperator()
	{
		return operator;
	}
	public TACNode getSecondOperand()
	{
		return right;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append('(');
		if (right == null)
			sb.append(operator.getSymbol());
		sb.append(left);
		if (right != null)
			sb.append(' ').append(operator.getSymbol()).append(' ').append(right);
		return sb.append(')').toString();
	}
}
