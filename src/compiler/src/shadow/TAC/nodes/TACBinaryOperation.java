package shadow.TAC.nodes;

import shadow.TAC.AbstractTACVisitor;
import shadow.TAC.TACOperation;
import shadow.TAC.TACVariable;

public class TACBinaryOperation extends TACAssign {

	// target and operand1 come from TACAssign
	private TACVariable operand2;
	private TACOperation operation;
	
	public TACBinaryOperation(TACVariable target, TACVariable op1, TACVariable op2, TACOperation operation) {
		super("", null);
		
		setTarget(target);
		setOperand1(op1);
		this.operand2 = op2;
		this.operation = operation;
	}

	public void accept(AbstractTACVisitor visitor) {
		visitor.visit(this);
	}

	public String toString() {
		return super.toString() + " " + operation + " " + operand2;
	}

}
