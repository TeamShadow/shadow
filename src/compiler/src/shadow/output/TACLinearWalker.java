/**
 * 
 */
package shadow.output;

import shadow.tac.TACField;
import shadow.tac.TACMethod;
import shadow.tac.TACModule;
import shadow.tac.nodes.TACNode;

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
		
		TACModule theClass = visitor.getTheClass();

		visitor.startFields();

		// walk through the fields first
		for (TACField field : theClass.getFields())
			walk(field.getEntryNode(), field.getExitNode());

		visitor.endFields();
		
		// walk through the methods
		for (TACMethod method : theClass.getMethods())
		{
			visitor.startMethod(method);
			if (method.getName().equals("constructor"))
			{
//				// walk through the fields at the beginning of every constructor (I think)
//				for (TACField field : theClass.getFields())
//					walk(field.getEntryNode(), null);
				// go through class allocs at the beginning of every constructor?
				walk(theClass.getInitializationEntryNode(), theClass.getInitializationExitNode());
			}
			walk(method.getEntryNode(), method.getExitNode());
			visitor.endMethod(method);
		}
		visitor.endFile();
	}
	
	private void walk(TACNode entryNode, TACNode exitNode) {
		while (entryNode != null)
		{
			visitor.visit(entryNode);
			entryNode = entryNode == exitNode ? null : entryNode.getNext();
		}
//		if(node == null)
//			return;
		
//		if(join != null && node == join) {
//			return;
//		} else if(node instanceof TACBranch) {
//			TACJoin realJoin = ((TACBranch) node).getJoin();
//
//			visitor.visit(node);
//			
//			walk(((TACBranch) node).getTrueEntry(), realJoin);
//			visitor.visitJoin(realJoin);
//			
//			TACNode falseEntry = ((TACBranch) node).getFalseEntry();
//
//			if(! (falseEntry instanceof TACJoin) ) {
//				visitor.visitElse();
//				walk(falseEntry, realJoin);
//				visitor.visitJoin(realJoin);
//			}
//
//			realJoin.decreaseCount();
//			if(realJoin.getCount() == 0)
//				walk(realJoin.getNext(), null);
//		} else if (node instanceof TACLoop) {
//			TACJoin realJoin = null;//((TACLoop) node).getJoin();
//
//			visitor.visit(node);
//			
//			walk(((TACLoop) node).getLoopNode(), realJoin);
//			visitor.visitJoin(realJoin);
//			walk(((TACLoop) node).getBreakNode(), realJoin);
//		} else {
//			visitor.visit(node);
//			TACNode next = node.getNext();
//			walk(next, join);
//		}
	}
	
}
