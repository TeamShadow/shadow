package shadow.TAC.nodes;

import shadow.TAC.TACVariable;

public class TACAssign extends TACNode {

	private TACVariable target;
	private TACVariable operand1;	/** target = operand1; */
	
	public TACAssign(TACNode parent) {
		super("ASSIGN", parent);
	}
	
	protected TACAssign(String name, TACNode parent) {
		super(name, parent);
	}
	
	public String toString() {
		return super.toString() + ": " + target + " = " + operand1; 
	}

	public void setTarget(TACVariable target) {
		this.target = target;
	}

	public TACVariable getTarget() {
		return target;
	}
	
	public void setOperand1(TACVariable op1) {
		this.operand1 = op1;
	}
	
}
