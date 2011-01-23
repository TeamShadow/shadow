/**
 * 
 */
package shadow.output;

import shadow.TAC.nodes.TACBranch;
import shadow.TAC.nodes.TACJoin;
import shadow.TAC.nodes.TACNode;
import shadow.TAC.nodes.TACNodeInterface;

/**
 * Walks the TAC in a linear fashion, the way you'd want when printing code
 *
 */
public class TACLinearWalker {
	private AbstractTACLinearVisitor visitor;
	private TACNode rootNode;
	
	public TACLinearWalker(AbstractTACLinearVisitor visitor) {
		this.visitor = visitor;
		this.rootNode = visitor.getRoot();
	}
	
	public void walk() {
		visitor.start();
		walk(rootNode, null);
		visitor.end();
	}
	
	private void walk(TACNodeInterface node, TACJoin join) {
		if(node == null)
			return;
		
		if(join != null && node == join) {
			return;
		} else if(node instanceof TACBranch) {
			TACJoin realJoin = ((TACBranch) node).getJoin();

			visitor.visit(node);
			
			walk(((TACBranch) node).getTrueEntry(), realJoin);
			visitor.visitJoin(realJoin);

			visitor.visitElse();
			walk(((TACBranch) node).getFalseEntry(), realJoin);
			visitor.visitJoin(realJoin);
			
			walk(realJoin.getNext(), null);
		} else {
			visitor.visit(node);
			TACNodeInterface next = ((TACNode)node).getNext();
			walk(next, join);
		}
	}
	
}
