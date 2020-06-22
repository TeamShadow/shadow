package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVisitor;

public class TACCatchRet extends TACNode {
	private TACLabel label;
	private TACCatchPad catchPad;
	
	public TACCatchRet(TACNode node, TACLabel label, TACCatchPad catchPad)
	{
		super(node);
		this.label = label;
		this.catchPad = catchPad;
	}
	
	public TACCatchPad getCatchPad() {
		return catchPad;
	}
	
	public TACLabel getLabel() {
		return label;
	}

	@Override
	public int getNumOperands() {
		return 0;
	}

	@Override
	public TACOperand getOperand(int num) {
		throw new IndexOutOfBoundsException("" + num);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return "goto " + label;
	}

}
