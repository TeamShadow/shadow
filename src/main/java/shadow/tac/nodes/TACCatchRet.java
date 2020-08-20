package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVisitor;

public class TACCatchRet extends TACNode {
	
	private TACCatchPad catchPad;
	private TACLabel label;

	public TACCatchRet(TACNode node, TACCatchPad catchPad, TACLabel label) {
		super(node);
		this.catchPad = catchPad;
		this.label = label;
	}
	
	public TACLabel getLabel() {
		return label;
	}
	
	public TACCatchPad getCatchPad() {
		return catchPad;
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

}
