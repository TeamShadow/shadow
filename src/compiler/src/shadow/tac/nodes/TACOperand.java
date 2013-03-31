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

	@Override
	public Modifiers getModifiers()
	{
		return new Modifiers();
	}
	public abstract Type getType();
	public void setType(Type type)
	{
		throw new UnsupportedOperationException();
	}

	protected TACOperand checkVirtual(ModifiedType type, TACNode node)
	{
		if (getType().isStrictSubtype(type.getType()))
			return new TACCast(node, type, this);
		return this;
	}
}
