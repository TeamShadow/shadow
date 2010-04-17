package shadow.TAC.nodes;

public class TACNoOp extends TACNode {

	public TACNoOp(TACNode parent, TACNode next) {
		super("NO OP", parent, next);
	}
	
	public TACNoOp(TACNode parent) {
		super("NO OP", parent);
	}
}
