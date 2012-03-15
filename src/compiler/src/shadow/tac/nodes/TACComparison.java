package shadow.tac.nodes;

import shadow.tac.AbstractTACVisitor;
import shadow.typecheck.type.Type;

public class TACComparison extends TACOperation
{
	public static enum Operator implements TACOperator
	{
		EQUAL('='),
		NOT_EQUAL('!'),
		LESS_THAN('<'),
		LESS_THAN_OR_EQUAL('{'),
		GREATER_THAN('>'),
		GREATER_THAN_OR_EQUAL('}'),
		IS('i');
		
		private final char symbol;
		private Operator(char c)
		{
			symbol = c;
		}
		@Override
		public char getSymbol()
		{
			return symbol;
		}
		
		public static Operator parse(char c)
		{
			for (Operator op : Operator.values())
				if (op.getSymbol() == c)
					return op;
			return null;
		}
	}
	
	public TACComparison(TACNode leftOperand, Operator comparisonOperator, TACNode rightOperand)
	{
		super(leftOperand, comparisonOperator, rightOperand);
	}
	
	@Override
	public Type getType()
	{
		return Type.BOOLEAN;
	}
	@Override
	public Operator getOperator()
	{
		return (Operator)super.getOperator();
	}
	
	@Override
	public void accept(AbstractTACVisitor visitor)
	{
		visitor.visit(this);
	}
}
