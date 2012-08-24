package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACNodeRef extends TACOperand
{
	private TACOperand reference;
	public TACNodeRef(TACOperand op)
	{
		this(null, op);
	}
	public TACNodeRef(TACNode node, TACOperand op)
	{
		super(node);
		reference = op;
	}

	public TACOperand getReference()
	{
		return reference;
	}

	@Override
	public Type getType()
	{
		return reference.getType();
	}
	@Override
	public int getNumOperands()
	{
		return 1;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (num == 0)
			return reference;
		throw new IndexOutOfBoundsException("num");
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

	@Override
	protected TACOperand checkVirtual(Type type, TACNode node)
	{
		return reference.checkVirtual(type, node);
	}
}
