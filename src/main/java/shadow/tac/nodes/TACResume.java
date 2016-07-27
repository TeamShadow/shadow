package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVisitor;

public class TACResume extends TACNode
{	
	private TACOperand exception;
	
	public TACResume(TACNode node, TACOperand exception)
	{
		super(node);
		this.exception = exception;
	}
	
	public TACOperand getException() {
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
