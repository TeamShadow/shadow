package shadow.TAC.nodes;

import shadow.TAC.TACVariable;
import shadow.output.AbstractTACVisitor;
import shadow.parser.javacc.Node;

/**
 * These are used as place holders for variables so we can bring them up the AST.
 * 
 * Basically they just store a variable which is used somewhere above in the AST.
 */
public class TACNoOp extends TACNode {
	
	private TACVariable variable;

	public TACNoOp(Node astNode, TACVariable variable, TACNode parent, TACNode next) {
		super(astNode, "NO OP", parent, next);
		this.variable = variable;
	}
	
	public TACNoOp(Node astNode, TACNode parent, TACNode next) {
		this(astNode, null, parent, next);
	}
	
	public void setVariable(TACVariable var) {
		variable = var;
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
