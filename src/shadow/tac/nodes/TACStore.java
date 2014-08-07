package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;

/** 
 * TAC representation of assignment
 * Example: x = y
 * @author Jacob Young
 */

public class TACStore extends TACSimpleNode
{
	private TACReference reference;
	private TACOperand value;
	public TACStore(TACReference ref, TACOperand op)
	{
		this(null, ref, op);
	}
	public TACStore(TACNode node, TACReference ref, TACOperand op)
	{
		super(node);
		reference = ref;
		value = check(op, ref.getSetType());
		new TACNodeRef(node, value);
	}

	public TACReference getReference()
	{
		return reference;
	}
	public TACOperand getValue()
	{
		return value;
	}

	@Override
	public int getNumOperands()
	{
		return 2;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (num == 0)
			return reference;
		if (num == 1)
			return value;
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
		return reference + " = " + value;
	}
}
