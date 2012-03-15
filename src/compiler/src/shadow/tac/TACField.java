package shadow.tac;

import shadow.tac.nodes.TACAllocation;
import shadow.typecheck.type.Type;

public class TACField extends TACDeclaration
{
	public TACField(Type type, String symbol)
	{
		super(new TACAllocation(type, symbol));
	}
}
