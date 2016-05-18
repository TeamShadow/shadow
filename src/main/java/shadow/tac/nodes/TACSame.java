package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

/** 
 * TAC representation of reference equals
 * Example: x === y
 * @author Jacob Young
 */

public class TACSame extends TACOperand
{
	private TACOperand first, second;

	public TACSame(TACNode node, TACOperand firstOperand,
			TACOperand secondOperand)
	{
		super(node);
		firstOperand = check(firstOperand, firstOperand);		
		secondOperand = check(secondOperand, secondOperand);
		Type firstType = firstOperand.getType();
		Type secondType = secondOperand.getType();
		
		
		if (firstType.isSubtype(secondType))
			firstOperand = check(firstOperand, secondOperand);
		else if(secondType.isSubtype(firstType) )
			secondOperand = check(secondOperand, firstOperand);		
		//primitive exceptions, since they can be equal to each other with no clear subtype relation
		else if( firstType.isPrimitive() && secondType.isPrimitive() ) {
			
			if( firstType.getWidth() >= secondType.getWidth() )
				secondOperand = new TACCast(this, firstOperand, secondOperand);
			else
				firstOperand = new TACCast(this, secondOperand, firstOperand);			
		}
		else
			throw new UnsupportedOperationException();
			
			
		first = firstOperand;
		second = secondOperand;
	}

	public TACOperand getFirst()
	{
		return first;
	}
	public TACOperand getSecond()
	{
		return second;
	}

	@Override
	public Type getType()
	{
		return Type.BOOLEAN;
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
	public String toString()
	{
		return first + " === " + second;
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}
}
