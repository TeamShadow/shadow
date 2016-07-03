package shadow.tac.nodes;

import java.util.Set;

import shadow.interpreter.ShadowBoolean;
import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

/** 
 * TAC representation of reference equals
 * Example: x === y
 * @author Jacob Young
 */

public class TACSame extends TACUpdate
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
				secondOperand = TACCast.cast(this, firstOperand, secondOperand);
			else
				firstOperand = TACCast.cast(this, secondOperand, firstOperand);			
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
	
	@Override
	public boolean update(Set<TACUpdate> currentlyUpdating) {
		if( currentlyUpdating.contains(this) )
			return false;
		
		currentlyUpdating.add(this);
		boolean changed = false;		
		
		if( first instanceof TACUpdate ) {
			TACUpdate update = (TACUpdate) first;
			if( update.update(currentlyUpdating) )
				changed = true;			
			first = update.getValue();
		}		

		if( second instanceof TACUpdate ) {
			TACUpdate update = (TACUpdate) second;
			if( update.update(currentlyUpdating) )
				changed = true;			
			second = update.getValue();
		}
		
		if( (changed || getUpdatedValue() == null) && first instanceof TACLiteral && second instanceof TACLiteral
				&& first.getType().isPrimitive() && second.getType().isPrimitive() ) {
			try {
				TACLiteral firstLiteral = (TACLiteral) first;
				TACLiteral secondLiteral = (TACLiteral) second;
				
				ShadowBoolean result = firstLiteral.getValue().equal(secondLiteral.getValue());
				setUpdatedValue(new TACLiteral(this, result));
				changed = true;
			}
			catch(ShadowException e)
			{} //do nothing, failed to evaluate
		}
		
		currentlyUpdating.remove(this);
		return changed;	
	}
	
	@Override
	public TACOperand getValue()
	{
		if( getUpdatedValue() == null )
			return this;
		else
			return getUpdatedValue();
	}
}
