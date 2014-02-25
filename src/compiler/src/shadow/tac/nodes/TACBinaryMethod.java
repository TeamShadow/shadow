package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACBinaryMethod extends TACOperand {

	private BinaryOperation operation;
	private TACOperand first, second;
	
	public TACBinaryMethod(TACNode node, TACOperand firstOperand, char op,
			TACOperand secondOperand)
	{
		this(node, firstOperand, new BinaryOperation(firstOperand, secondOperand, op), secondOperand);
	}
	

	public TACBinaryMethod(TACNode node, TACOperand firstOperand, BinaryOperation op,
			TACOperand secondOperand)
	{
		super(node);

		operation = op;
		first = check(firstOperand, op.getFirst());
		second = check(secondOperand, op.getSecond());
	}

	public TACOperand getFirst()
	{
		return first;
	}
	public BinaryOperation getOperation()
	{
		return operation;
	}	
	public TACOperand getSecond()
	{
		return second;
	}

	@Override
	public Type getType()
	{
		return operation.getResultType().getType();
	}
	@Override
	public int getNumOperands()
	{
		return 2;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (num == 0)
			return first;
		if (num == 1)
			return second;
		throw new IndexOutOfBoundsException();
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(first).append(operation.getMethod().getSymbol()).append('(').append(second).append(')');
		return sb.toString();
	}

}
