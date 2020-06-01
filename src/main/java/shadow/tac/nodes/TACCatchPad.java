package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ExceptionType;

public class TACCatchPad extends TACOperand implements TACPad
{
	private ExceptionType type;
	private TACOperand exception;
	private String token;
	private TACLabel label;

	public TACCatchPad(TACNode node, ExceptionType catchType, TACLabel label)
	{
		super(node);
		type = catchType;
		this.label = label;
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

	@Override
	public void setToken(String token) {
		this.token = token;		
	}

	@Override
	public String getToken() {
		return token;
	}

	@Override
	public TACLabel getLabel() {
		return label;
	}
}
