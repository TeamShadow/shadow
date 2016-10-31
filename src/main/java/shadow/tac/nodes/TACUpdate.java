package shadow.tac.nodes;

import java.util.Set;

public abstract class TACUpdate extends TACOperand {
	private TACOperand updatedValue;
	
	public TACOperand getUpdatedValue()
	{
		return updatedValue;
	}
	
	public void setUpdatedValue(TACOperand value)
	{
		updatedValue = value;
	}
	
	public final boolean hasUpdatedValue() {
		return updatedValue != null;
	}
	
	protected TACUpdate(TACNode node)
	{
		super(node);
	}
	
	abstract public TACOperand getValue();
	abstract public boolean update(Set<TACUpdate> currentlyUpdating);
}
