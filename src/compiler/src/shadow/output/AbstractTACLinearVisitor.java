package shadow.output;

import shadow.TAC.nodes.TACJoin;
import shadow.TAC.nodes.TACNode;

public abstract class AbstractTACLinearVisitor extends AbstractTACVisitor {

	public AbstractTACLinearVisitor(TACNode root) {
		super(root);
	}

	/**
	 * This is a special method called only when the FINAL join is visited.
	 * @param join A TACJoin node.
	 */
	public void visitJoin(TACJoin join) {
	}

	/**
	 * This method is called before traversing the false part of a branch
	 */
	public void visitElse() {
	}

}
