package shadow.output;

import java.util.LinkedHashSet;
import java.util.LinkedList;

import shadow.TAC.nodes.TACBranch;
import shadow.TAC.nodes.TACLoop;
import shadow.TAC.nodes.TACNode;
import shadow.TAC.nodes.TACNodeInterface;

/**
 * Walks the TAC in a depth-first fashion.
 * Based on the second algorithm here: http://en.wikipedia.org/wiki/Topological_sort
 */
public class TACWalker {

	private AbstractTACVisitor visitor;
	private LinkedList<TACNode> nodes;
	private LinkedHashSet<TACNodeInterface> sortedNodes;
	
	public TACWalker(AbstractTACVisitor visitor) {
		this.visitor = visitor;
		this.nodes = visitor.getRoot().getNodes();
		this.sortedNodes = new LinkedHashSet<TACNodeInterface>();
	}
	
	public void walk() {
		for(TACNode node:nodes)
			visit(node);
		
		visitor.start();
		
		for(TACNodeInterface node:sortedNodes) {
			visitor.visit(node);
		}
		
		visitor.end();
	}
	
	private void visit(TACNode node) {
		if(sortedNodes.contains(node))
			return;
		
		sortedNodes.add(node);
		
		if(node instanceof TACBranch) {
			TACBranch branch = (TACBranch)node;
			
			visit(branch.getTrueEntry());
			visit(branch.getFalseEntry());
		} else if(node instanceof TACLoop) {
			TACLoop loop = (TACLoop)node;
			
			visit(loop.getLoopNode());
			visit(loop.getBreakNode());
		} else if(node.getNext() != null) {
			visit(node.getNext());
		}
	}
}
