package shadow.tac.nodes;

import shadow.tac.TACVariable;
import shadow.typecheck.type.Type;

public abstract class TACLocalStorage extends TACOperand implements TACUpdate {
	
	private final TACVariable variable;
	private TACOperand updatedValue;
	private final int number;

	protected TACLocalStorage(TACNode node, TACVariable variable) {
		super(node);		
		this.variable = variable;
		number = getMethod().incrementVariableCounter();
	}
	
	@Override
	public Object getData() {	
		if( updatedValue == null )	
			return '%' + variable.getName() + '.' + number;		
		else
			return updatedValue.getData();		
	}

	@Override
	public Type getType() {		
		return variable.getType();
	}
	
	public TACOperand getUpdatedValue() {
		return updatedValue;
	}
	
	public void setUpdatedValue(TACOperand op) {
		updatedValue = op;
	}
	
	public TACVariable getVariable() {
		return variable;
	}
	
	public int getNumber() {
		return number;
	}
	
	@Override
	public boolean canPropagate() {
		return true;
	}
}
