package shadow.TAC.nodes;

import shadow.TAC.TACVariable;
import shadow.output.AbstractTACVisitor;

/**
 * These are used as place holders for variables so we can bring them up the AST.
 * 
 * Basically they just store a variable which is used somewhere above in the AST.
 */
public class TACNoOp extends TACNode {
	
	private TACVariable variable;

	public TACNoOp(TACVariable variable, TACNode parent, TACNode next) {
		super("NO OP", parent, next);
		this.variable = variable;
	}
	
	public TACNoOp(TACVariable variable, TACNode parent) {
		super("NO OP", parent);
		this.variable = variable;
	}
	
	public TACNoOp(TACNode parent, TACNode next) {
		this(null, parent, next);
	}
	
	public TACVariable getVariable() {
		return variable;
	}
	
	public void accept(AbstractTACVisitor visitor) {
		visitor.visit(this);
	}
	
	public String toString() {
		return name  + " VAR: " + variable; 
	}

}
