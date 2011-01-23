package shadow.TAC.nodes;

import shadow.TAC.TACVariable;
import shadow.output.AbstractTACVisitor;
import shadow.parser.javacc.Node;

public class TACBinaryOperation extends TACAssign {

	// target and operand1 come from TACAssign
	private TACVariable operand2;
	private TACOperation operation;
	
	public TACBinaryOperation(Node astNode, TACVariable target, TACVariable op1, TACVariable op2, TACOperation operation) {
		super(astNode, "", null);
		
		setLHS(target);
		setRHS(op1);
		this.operand2 = op2;
		this.operation = operation;
	}

	public void accept(AbstractTACVisitor visitor) {
		visitor.visit(this);
	}

	public TACVariable getOperand2() {
		return operand2;
	}

	public TACOperation getOperation() {
		return operation;
	}

	public String toString() {
		return super.toString() + " " + operation + " " + operand2;
	}

}
