package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVariable;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ExceptionType;

public class TACCatchPad extends TACPad
{
	private ExceptionType type;
	private TACVariable variable;

	public TACCatchPad(TACNode node, ExceptionType catchType, TACLabel label, TACVariable variable) {
		super(node, label);
		type = catchType;
		this.variable = variable;
	}

	@Override
	public ExceptionType getType() {
		return type;
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException {
		visitor.visit(this);
	}
	
	public TACVariable getVariable() {
		return variable;
	}
}
