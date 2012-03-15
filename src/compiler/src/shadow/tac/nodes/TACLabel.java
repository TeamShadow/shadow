package shadow.tac.nodes;

import shadow.tac.AbstractTACVisitor;
import shadow.typecheck.type.Type;

public class TACLabel extends TACNode
{
	public TACLabel() { }
	public TACLabel(String label)
	{
		setSymbol(label);
	}

	@Override
	public Type getType()
	{
		return null;
	}

	@Override
	public void accept(AbstractTACVisitor visitor)
	{
		visitor.visit(this);
	}
}
