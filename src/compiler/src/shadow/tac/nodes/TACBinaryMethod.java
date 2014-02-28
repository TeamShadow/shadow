package shadow.tac.nodes;


public class TACBinaryMethod extends TACCall {
	
	public TACBinaryMethod(TACNode node, TACBlock block, TACOperand firstOperand, char op,
			TACOperand secondOperand)
	{
		this(node, block, firstOperand, new BinaryOperation(firstOperand, secondOperand, op), secondOperand);
	}	

	public TACBinaryMethod(TACNode node, TACBlock block, TACOperand firstOperand, BinaryOperation op,
			TACOperand secondOperand)
	{
		super(node,  block, new TACMethodRef( firstOperand, op.getMethod() ), firstOperand, secondOperand );
	}
	
	/*
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(first).append(operation.getMethod().getSymbol()).append('(').append(second).append(')');
		return sb.toString();
	}
	*/

}
