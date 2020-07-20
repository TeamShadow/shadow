package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ExceptionType;

public class TACCatch extends TACOperand
{
	private ExceptionType type;
	private TACLandingPad landingPad;

	public TACCatch(TACNode node, ExceptionType catchType) {
		super(node);
		type = catchType;
	}
	
	public void setLandingPad(TACLandingPad landingPad) {
		this.landingPad = landingPad;
	}
	
	public TACLandingPad getLandingPad() {
		return landingPad;
	}
	
	@Override
	public ExceptionType getType() {
		return type;
	}

	@Override
	public int getNumOperands()
	{
		return 1;
	}
	@Override
	public TACOperand getOperand(int num) {
		throw new IndexOutOfBoundsException("" + num);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException {
		visitor.visit(this);
	}
}