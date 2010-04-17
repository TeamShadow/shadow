package shadow.TAC.nodes;

import shadow.TAC.AbstractTACVisitor;
import shadow.TAC.TACOperation;
import shadow.TAC.TACVariable;

public class TACUnaryOperation extends TACAssign {

	// target and operand1 come from TACAssign
	private TACOperation operation;
	
	public TACUnaryOperation(TACVariable target, TACVariable op1, TACOperation operation) {
		super("UNARY OP", null);
		
		setTarget(target);
		setOperand1(op1);
		
		this.operation = operation;
	}

	public void accept(AbstractTACVisitor visitor) {
		visitor.visit(this);
	}

}
