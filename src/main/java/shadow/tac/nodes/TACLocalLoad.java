package shadow.tac.nodes;

import java.util.Set;

import shadow.interpreter.ShadowUndefined;
import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVariable;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class TACLocalLoad extends TACOperand implements TACUpdate {

	private final TACVariable variable;
	private TACOperand previousStore;
	private boolean undefined = false;

	public TACLocalLoad(TACNode node, TACVariable var)
	{
		super(node);
		variable = var;
	}
	
	public boolean isUndefined() {
		return undefined;
	}

	public TACVariable getVariable()
	{
		return variable;
	}
	
	public boolean update(Set<TACUpdate> currentlyUpdating) {
		if( currentlyUpdating.contains(this) )
			return false;		
		boolean changed = false;
		currentlyUpdating.add(this);
		
		undefined = false;
		TACOperand temp = previousStore;
		
		if( temp instanceof TACUpdate ) {
			TACUpdate update = (TACUpdate) temp;
			if( update.update(currentlyUpdating) )
				changed = true;
			
			TACOperand op = update.getValue();
			//if( op != temp && (op instanceof TACLiteral || op instanceof TACParameter || op instanceof TACLocalStore || op instanceof TACPhiStore ) ) {
			if( op != temp && op.canPropagate()  ) {
				changed = true;
				temp = op;
			}
		}
		
		if( temp instanceof TACLiteral ) {
			TACLiteral literal = (TACLiteral) temp;
			if( literal.getValue() instanceof ShadowUndefined )
				undefined = true;
		}
		else if( temp instanceof TACPhiStore ) {
			TACPhiStore phiStore = (TACPhiStore) temp;
			if( phiStore.isUndefined() )
				undefined = true;
		}
		
		if( changed )
			previousStore = temp;
		
		currentlyUpdating.remove(this);
		return changed;
	}

	@Override
	public Modifiers getModifiers()
	{
		return variable.getModifiers();
	}
	@Override
	public Type getType()
	{
		return variable.getType();
	}
	@Override
	public void setType(Type newType)
	{
		variable.setType(newType);
	}
	@Override
	public int getNumOperands()
	{
		return 0;
	}
	@Override
	public TACOperand getOperand(int num)
	{
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
		return variable.toString();
	}

	public void setPreviousStore(TACOperand store)
	{
		if( store instanceof TACLiteral ) {
			TACLiteral literal = (TACLiteral) store;
			if( literal.getValue() instanceof ShadowUndefined )
				undefined = true;
		}
		
		previousStore = store;
	}
	
	public TACOperand getPreviousStore()
	{
		return previousStore;		
	}

	@Override
	public TACOperand getValue() {
		if( previousStore != null )
			return previousStore;
		else
			return this;
	}
	
	@Override
	public boolean canPropagate() {
		return true;
	}	
}
