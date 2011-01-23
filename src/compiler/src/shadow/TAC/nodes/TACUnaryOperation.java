package shadow.TAC.nodes;

import shadow.TAC.TACVariable;
import shadow.output.AbstractTACVisitor;
import shadow.parser.javacc.Node;

public class TACUnaryOperation extends TACAssign {

	// target and operand1 come from TACAssign
	private TACOperation operation;
	
	public TACUnaryOperation(Node astNode, TACVariable target, TACVariable op1, TACOperation operation) {
		super(astNode, "UNARY OP", null);
		
		setTarget(target);
		setOperand1(op1);
		
		this.operation = operation;
	}

	public void accept(AbstractTACVisitor visitor) {
		visitor.visit(this);
	}

}
