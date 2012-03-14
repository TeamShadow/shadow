package shadow.tac.nodes;

import shadow.output.AbstractTACVisitor;

public class TACAssign extends TACOperation
{
	public static class Operator implements TACOperator
	{
		public static final Operator ASSIGN = new Operator();
		
		private Operator() { }
		
		@Override
		public char getSymbol()
		{
			return '=';
		}
		@Override
		public String toString() {
			return "ASSIGN";
		}
	}
	
	public TACAssign(TACNode leftOperand, TACNode rightOperand)
	{
		super(leftOperand, Operator.ASSIGN, rightOperand);
		if (!rightOperand.getType().isSubtype(leftOperand.getType()))
			throw new IllegalArgumentException("Cannot assign a supertype to a subtype.");
	}
	
	@Override
	public Operator getOperator()
	{
		return Operator.ASSIGN;
	}
	
	@Override
	public String toString()
	{
		return getFirstOperand() + " = " + getSecondOperand();
	}
	
	@Override
	public void accept(AbstractTACVisitor visitor)
	{
		visitor.visit(this);
	}
}
