package shadow.TAC.nodes;

import shadow.TAC.TACVariable;
import shadow.output.AbstractTACVisitor;
import shadow.parser.javacc.Node;

public class TACAssign extends TACNode {

	private TACVariable target;
	private TACVariable operand1;	/** target = operand1; */
	
	public TACAssign(Node astNode, TACVariable lhs, TACVariable rhs) {
		super(astNode, "", null);
		target = lhs;
		operand1 = rhs;
	}
	
	protected TACAssign(Node astNode, String name, TACNode parent) {
		super(astNode, name, parent);
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
