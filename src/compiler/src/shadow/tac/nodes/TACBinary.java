package shadow.tac.nodes;

import java.io.IOException;

import shadow.tac.AbstractTACVisitor;

public class TACBinary extends TACOperation
{
	public static enum Operator implements TACOperator
	{
		ADD('+'),
		SUBTRACT('-'),
		MULTIPLY('*'),
		DIVIDE('/'),
		MODULUS('%'),
		
		LOGICAL_OR('o'),
		LOGICAL_XOR('x'),
		LOGICAL_AND('a'),
		
		BITWISE_OR('|'),
		BITWISE_XOR('^'),
		BITWISE_AND('&'),
		
		LEFT_SHIFT('l'),
		RIGHT_SHIFT('r'),
		LEFT_ROTATE('L'),
		RIGHT_ROTATE('R');
		
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
	
	public TACBinary(TACNode leftOperand, Operator binaryOperator, TACNode rightOperand)
	{
		super(leftOperand, binaryOperator, rightOperand);
	}
	
	@Override
	public Operator getOperator()
	{
		return (Operator)super.getOperator();
	}
	
	@Override
	public void accept(AbstractTACVisitor visitor) throws IOException
	{
		visitor.visit(this);
	}
}
