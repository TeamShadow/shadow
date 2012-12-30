package shadow.tac.nodes;

import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public abstract class TACOperand extends TACSimpleNode implements ModifiedType
{
	protected TACOperand()
	{
		super();
	}
	protected TACOperand(TACNode node)
	{
		super(node);
	}

	private String symbol;
	public final void setSymbol(String newSymbol)
	{
		symbol = newSymbol;
	}
	public final String getSymbol()
	{
		return symbol;
	}

	public abstract Type getType();
	@Override
	public Modifiers getModifiers()
	{
		return new Modifiers();
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
