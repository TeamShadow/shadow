package shadow.tac.nodes;

import java.util.Set;

import shadow.ShadowException;
import shadow.tac.TACVariable;
import shadow.tac.TACVisitor;

public class TACLocalStore extends TACLocalStorage {
	private TACOperand value;	

	public TACLocalStore(TACNode node, TACVariable variable, TACOperand op)
	{
		super(node, variable);		
		value = check(op, variable);
		value.setLocalStore(this);
	}
	

	@Override
	public boolean update(Set<TACUpdate> currentlyUpdating)
	{
		if( currentlyUpdating.contains(this) )
			return false;
				
		currentlyUpdating.add(this);
		boolean changed = false;
		TACOperand temp = getValue();
		
		if( temp instanceof TACUpdate ) {
			TACUpdate update = (TACUpdate) temp;
			if( update.update(currentlyUpdating) )
				changed = true;
			
			temp = update.getValue();
			if( temp != getUpdatedValue() && temp.canPropagate() ) {
				setUpdatedValue(temp);
				changed = true;
			}				
		}	
		
		currentlyUpdating.remove(this);
		return changed;		
	}
	
	public TACOperand getValue()
	{	
		if( getUpdatedValue() == null || isGarbageCollected() )
			return value;
		else
			return getUpdatedValue();
	}

	@Override
	public int getNumOperands()
	{
		return 1;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (num == 0)			
			return value;
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
		return getVariable() + " = " + value;
	}	
		
	@Override
	public boolean equals(Object other) {
		if( other == null || !(other instanceof TACLocalStore)  )
			return false;
		
		if( other == this )
			return true;
		
		TACLocalStore store = (TACLocalStore) other;
		
		return getNumber() == store.getNumber() && getVariable().equals(store.getVariable());
	}
}
