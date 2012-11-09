package shadow.tac.nodes;

import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public abstract class TACOperand extends TACSimpleNode
		implements ModifiedType
{
	private String name;
	protected TACOperand()
	{
		super();
	}
	protected TACOperand(TACNode node)
	{
		super(node);
	}

	public final void setName(String newName)
	{
		name = newName;
	}

	public abstract Type getType();
	@Override
	public Modifiers getModifiers()
	{
		return new Modifiers();
	}
	public final String getName()
	{
		return name;
	}

	protected final TACOperand check(TACOperand operand)
	{
		return check(operand, getType());
	}

	protected TACOperand checkVirtual(Type type, TACNode node)
	{
		if (getType().isStrictSubtype(type))
			return new TACCast(node, type, this);
		return this;
	}
}
