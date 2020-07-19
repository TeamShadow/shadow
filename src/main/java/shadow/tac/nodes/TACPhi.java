package shadow.tac.nodes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import shadow.ShadowException;
import shadow.interpreter.ShadowUndefined;
import shadow.tac.TACVariable;
import shadow.tac.TACVisitor;

public class TACPhi extends TACLocalStorage {		
	private Map<TACLabel, TACOperand> previousStores = new HashMap<TACLabel, TACOperand>();	
	boolean undefined = false;
	
	public TACPhi(TACNode node, TACVariable variable) {
		super(node, variable);
	}

	@Override
	public boolean update(Set<TACUpdate> currentlyUpdating) {
		if( currentlyUpdating.contains(this) )
			return false;		
		boolean changed = false;
		currentlyUpdating.add(this);		
		
		Map<TACLabel, TACOperand> tempStores = new HashMap<TACLabel, TACOperand>();
		undefined = false;
		
		for( Map.Entry<TACLabel, TACOperand> entry : previousStores.entrySet() ) {
			TACOperand temp = entry.getValue();
			
			if( temp instanceof TACUpdate ) {
				TACUpdate update = (TACUpdate) temp;
				if( update.update(currentlyUpdating) )
					changed = true;
				
				TACOperand op = update.getValue();
				if( op != temp && op.canPropagate() ) {
					changed = true;
					temp = op;
				}
			}
			
			if( temp instanceof TACLiteral ) {
				TACLiteral literal = (TACLiteral) temp;
				if( literal.getValue() instanceof ShadowUndefined )
					undefined = true;
			}
			else if( temp instanceof TACPhi ) {
				TACPhi phiStore = (TACPhi) temp;
				if( phiStore.isUndefined() )
					undefined = true;
			}
			
			tempStores.put(entry.getKey(), temp);
		}	
		
		if( tempStores.size() > 1 ) {			
			Iterator<TACOperand> iterator = tempStores.values().iterator();
			TACOperand op = iterator.next();
			boolean same = true;
			while( iterator.hasNext() && same ) {
				TACOperand temp = iterator.next();
				if( !op.equals(temp) )
					same = false;
			}
			
			if( same ) {
				tempStores.clear();
				tempStores.put(null, op);
				changed = true;
			}
		}
		
		if( changed )
			previousStores = tempStores;
		
		currentlyUpdating.remove(this);
		return changed;
	}

	@Override
	public int getNumOperands()
	{
		return 0;
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer(getVariable() + " = phi (");
		for( TACOperand op : previousStores.values() )
			sb.append(op.toString()).append(", ");
		if( previousStores.size() > 0 )
			sb.delete(sb.length() - 2, sb.length());
		sb.append(')');
		return sb.toString();
	}	
	
	
	@Override
	public boolean equals(Object other) {
		if( other == null || !(other instanceof TACPhi)  )
			return false;
		
		if( other == this )
			return true;
		
		TACPhi store = (TACPhi) other;
		
		return getNumber() == store.getNumber() && getVariable().equals(store.getVariable());
	}
	
	public void addPreviousStore(TACLabel label, TACOperand store)
	{
		if( store instanceof TACLiteral ) {
			TACLiteral literal = (TACLiteral) store;
			if( literal.getValue() instanceof ShadowUndefined )
				undefined = true;
		}
		
		previousStores.put(label, store);
	}
	
	public boolean removePreviousStore(TACLabel label)
	{
		TACOperand store = previousStores.get(label);
		
		if( store != null ) {
			previousStores.remove(label);
			if( store instanceof TACLiteral ) {			
				TACLiteral literal = (TACLiteral) store;
				if( literal.getValue() instanceof ShadowUndefined ) { //may need to check to see if still undefined
					undefined = false;
					for( TACOperand op : previousStores.values() ) {
						if( op instanceof TACLiteral ) {
							literal = (TACLiteral) store;
							if( literal.getValue() instanceof ShadowUndefined )
								undefined = true;
						}
					}					
				}					
			}
			return true;
		}
		
		return false;
	}
	
	public Map<TACLabel, TACOperand> getPreviousStores()
	{
		return previousStores;		
	}

	@Override
	public TACOperand getValue() {
		if( previousStores.size() == 1 )
			return previousStores.values().iterator().next();
		else
			return this;
	}
	
	public boolean isUndefined() {
		return undefined;
	}

	@Override
	public TACOperand getOperand(int num) {
		throw new IndexOutOfBoundsException("" + num);
	}	
}
