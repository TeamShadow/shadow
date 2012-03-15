package shadow.tac.nodes;

import java.util.Collections;

import shadow.tac.AbstractTACVisitor;
import shadow.typecheck.type.Type;

public class TACReturn extends TACNode
{
	private TACSequence ret;
	public TACReturn()
	{
		ret = new TACSequence();
	}
	public TACReturn(TACNode retValue)
	{
		if (retValue instanceof TACSequence)
			ret = (TACSequence)retValue;
		else
			ret = new TACSequence(Collections.singletonList(retValue));
	}
	
	public Type getType()
	{
		return null;
	}
	public boolean hasReturnValue()
	{
		return !ret.getNodes().isEmpty();
	}
	public TACSequence getReturnValue()
	{
		return ret;
	}
	
	@Override
	public String toString()
	{
		return "return " + ret;
	}
	
	@Override
	public void accept(AbstractTACVisitor visitor)
	{
		visitor.visit(this);
	}
}
