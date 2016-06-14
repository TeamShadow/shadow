package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACBlock;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

//TODO: Should this really be an operand?
public class TACUnwind extends TACOperand
{
	private TACBlock block;
	private TACOperand exception;

	public TACUnwind(TACNode node, TACBlock blockRef, TACOperand exception)
	{
		super(node);
		block = blockRef;
		this.exception = exception;
	}
	
	public TACOperand getException() {
		return exception;
	}

	public TACBlock getBlock()
	{
		return block;
	}

	@Override
	public Type getType()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int getNumOperands()
	{
		return 1;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if( num == 0 )
			return exception;
		
		throw new IndexOutOfBoundsException("" + num);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}
}
