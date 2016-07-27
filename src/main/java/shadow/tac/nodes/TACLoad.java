package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class TACLoad extends TACOperand
{
	private TACReference reference;	

	public TACLoad(TACNode node, TACReference ref)
	{
		super(node);
		reference = ref;
	}

	public TACReference getReference()
	{
		return reference;
	}

	@Override
	public Modifiers getModifiers()
	{
		return reference.getModifiers();
	}
	@Override
	public Type getType()
	{
		return reference.getType();
	}
	@Override
	public void setType(Type newType)
	{
		reference.setType(newType);
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
		return reference.toString();
	}
}
