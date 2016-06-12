package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ExceptionType;

public class TACCatch extends TACOperand
{
	private ExceptionType type;
	private TACOperand exception;

	public TACCatch(TACNode node, ExceptionType catchType, TACOperand exception)
	{
		super(node);
		type = catchType;
		this.exception = exception;
	}
	
	public TACOperand getException() {
		return exception;
	}

	@Override
	public ExceptionType getType()
	{
		return type;
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
