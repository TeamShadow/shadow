package shadow.TAC.nodes;

import shadow.TAC.AbstractTACVisitor;
import shadow.TAC.TACVariable;

public class TACAssign extends TACNode {

	private TACVariable target;
	private TACVariable operand1;	/** target = operand1; */
	
	public TACAssign(TACVariable lhs, TACVariable rhs) {
		super("", null);
		target = lhs;
		operand1 = rhs;
	}
	
	protected TACAssign(String name, TACNode parent) {
		super(name, parent);
	}
	
	public void accept(AbstractTACVisitor visitor) {
		visitor.visit(this);
	}
	
	public String toString() {
		return name  + target + " = " + operand1; 
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
