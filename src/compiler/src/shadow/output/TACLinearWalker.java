/**
 * 
 */
package shadow.output;

import shadow.TAC.TACClass;
import shadow.TAC.TACMethod;
import shadow.TAC.nodes.TACBranch;
import shadow.TAC.nodes.TACJoin;
import shadow.TAC.nodes.TACLoop;
import shadow.TAC.nodes.TACNode;
import shadow.TAC.nodes.TACNodeInterface;

/**
 * Walks the TAC in a linear fashion, the way you'd want when printing code
 *
 */
public class TACLinearWalker {
	private AbstractTACLinearVisitor visitor;
	
	public TACLinearWalker(AbstractTACLinearVisitor visitor) {
		this.visitor = visitor;
	}
	
	public void walk() {
		visitor.startFile();
		
		TACClass theClass = visitor.getTheClass();

		visitor.startFields();

		// walk through the fields first
		for(TACNode[] field:theClass.getFields()) {
			walk(field[0], null);
		}

		visitor.endFields();
		
		// walk through the methods
		for(TACMethod method:theClass.getMethods()) {
			visitor.startMethod(method);
			if (method.getName().equals("constructor")) {
				// walk through the fields at the beginning of every constructor (I think)
				for(TACNode[] field:theClass.getFields()) {
					walk(field[0], null);
				}
			}
			walk(method.getEntry(), null);
			visitor.endMethod(method);
		}
		visitor.endFile();
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
			
			TACNode falseEntry = ((TACBranch) node).getFalseEntry();

			if(! (falseEntry instanceof TACJoin) ) {
				visitor.visitElse();
				walk(falseEntry, realJoin);
				visitor.visitJoin(realJoin);
			}

			realJoin.decreaseCount();
			if(realJoin.getCount() == 0)
				walk(realJoin.getNext(), null);
		} else if (node instanceof TACLoop) {
			TACJoin realJoin = null;//((TACLoop) node).getJoin();

			visitor.visit(node);
			
			walk(((TACLoop) node).getLoopNode(), realJoin);
			visitor.visitJoin(realJoin);
			walk(((TACLoop) node).getBreakNode(), realJoin);
		} else {
			visitor.visit(node);
			TACNodeInterface next = ((TACNode)node).getNext();
			walk(next, join);
		}
	}
	
}
