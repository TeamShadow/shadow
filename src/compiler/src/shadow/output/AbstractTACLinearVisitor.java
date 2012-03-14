package shadow.output;

import shadow.tac.TACModule;
import shadow.tac.nodes.TACNode;

public abstract class AbstractTACLinearVisitor extends AbstractTACVisitor {
	
	public AbstractTACLinearVisitor(TACModule theClass) {
		super(theClass);
	}
	
	/**
	 * This is a special method called only when the FINAL join is visited.
	 * @param join A TACJoin node.
	 */
	public void visitJoin(TACNode join) {
	}

	/**
	 * This method is called before traversing the false part of a branch
	 */
	public void visitElse() {
	}

}
