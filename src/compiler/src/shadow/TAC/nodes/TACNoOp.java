package shadow.TAC.nodes;

import shadow.TAC.AbstractTACVisitor;

public class TACNoOp extends TACNode {

	public TACNoOp(TACNode parent, TACNode next) {
		super("NO OP", parent, next);
	}
	
	public TACNoOp(TACNode parent) {
		super("NO OP", parent);
	}
	
	public void accept(AbstractTACVisitor visitor) {
		visitor.visit(this);
	}

}
