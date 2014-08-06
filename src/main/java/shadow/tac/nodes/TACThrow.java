package shadow.tac.nodes;

import shadow.parser.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

/** 
 * TAC representation of exception throw
 * Example: throw e
 * @author Jacob Young
 */

public class TACThrow extends TACSimpleNode
{
	private TACBlock block;
	private TACOperand exception;

	public TACThrow(TACNode node, TACBlock blockRef, TACOperand op)
	{
		super(node);
		block = blockRef;
		exception = check(op, new SimpleModifiedType(Type.OBJECT));
	}

	public TACBlock getBlock()
	{
		return block;
	}
	public TACOperand getException()
	{
		return exception;
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
			return getException();
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
		return "throw " + getException();
	}
}
