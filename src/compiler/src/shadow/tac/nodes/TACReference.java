package shadow.tac.nodes;

import shadow.typecheck.type.Type;

public abstract class TACReference extends TACOperand
{
	public TACReference()
	{
		super();
	}
	public TACReference(TACNode node)
	{
		super(node);
	}

	public Type getGetType()
	{
		return getType();
	}
	public Type getSetType()
	{
		return getType();
	}

	@Override
	protected TACOperand checkVirtual(Type type, TACNode node)
	{
		return new TACLoad(node, this).checkVirtual(type, node);
	}
}
