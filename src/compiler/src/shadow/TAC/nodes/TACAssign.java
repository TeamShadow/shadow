package shadow.TAC.nodes;

import shadow.TAC.TACVariable;
import shadow.output.AbstractTACVisitor;
import shadow.parser.javacc.Node;

public class TACAssign extends TACNode {

	private TACVariable lhs;
	private TACVariable rhs;	/** lhs = rhs; */
	
	public TACAssign(Node astNode, TACVariable lhs, TACVariable rhs) {
		super(astNode, "", null);
		this.lhs = lhs;
		this.rhs = rhs;
	}
	
	protected TACAssign(Node astNode, String name, TACNode parent) {
		super(astNode, name, parent);
	}
	
	public void accept(AbstractTACVisitor visitor) {
		visitor.visit(this);
	}
	
	public String toString() {
		return name  + lhs + " = " + rhs; 
	}

	public void setLHS(TACVariable lhs) {
		this.lhs = lhs;
	}

	public TACVariable getLHS() {
		return lhs;
	}

	public void setRHS(TACVariable rhs) {
		this.rhs = rhs;
	}

	public TACVariable getRHS() {
		return rhs;
	}
	
}
