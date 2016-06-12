package shadow.tac.nodes;

import java.util.Set;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVariable;
import shadow.tac.TACVisitor;

public class TACLocalStore extends TACLocalStorage {
	private TACOperand value;

	public TACLocalStore(TACNode node, TACVariable variable, TACOperand op)
	{
		super(node, variable);		
		value = check(op, variable);
		value.setLocalStore(this);
		//new TACNodeRef(node, value);
	}

	@Override
	public boolean update(Set<TACUpdate> currentlyUpdating) {
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
				//if(temp instanceof TACLiteral || temp instanceof TACParameter || temp instanceof TACLocalStore || temp instanceof TACPhiStore  )  
				//{
					setUpdatedValue(temp);
					changed = true;
				//}
			}				
		}	
		
		currentlyUpdating.remove(this);
		return changed;
		/*
		
		while( !done ) {
			if( temp instanceof TACLocalStore && temp != updatedValue ) {
				updatedValue = temp;
				changed = true;
				temp = ((TACLocalStore) temp).getValue();				
			}
			else if( temp instanceof TACLocalLoad ) {
				TACLocalLoad load = (TACLocalLoad) temp;
				Map<TACLabel, TACOperand> stores = load.getPreviousStores();
				if( stores.size() == 1 ) {
					TACOperand op = stores.values().iterator().next();
					if( updatedValue != op ) {
						updatedValue = op;
						temp = op;
						changed = true;
					}
					else
						done = true;
				}
				else
					done = true;
			}
			else if( (temp instanceof TACLiteral || temp instanceof TACParameter) && temp != updatedValue ) {
				updatedValue = temp;
				changed = true;
				done = true;				
			}
			else
				done = true;
		}
		
		return changed;
		*/
	}

	
	public TACOperand getValue()
	{
		if( getUpdatedValue() == null )
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
