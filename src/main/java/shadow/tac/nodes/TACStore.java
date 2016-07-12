package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;

/** 
 * TAC representation of a memory assignment to a field or an array cell.
 * Local variable assignment is represented by TACLocalStore. 
 * Example: x = y
 * @author Jacob Young
 */

public class TACStore extends TACNode
{
	private TACReference reference;
	private TACOperand value;

	public TACStore(TACNode node, TACReference ref, TACOperand op)
	{
		super(node);
		reference = ref;
		value = check(op, ref);		
		op.setMemoryStore(this);
		//new TACNodeRef(node, value);
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
		return 1;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (num == 0)		
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
