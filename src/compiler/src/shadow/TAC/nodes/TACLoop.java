package shadow.TAC.nodes;

import shadow.TAC.TACVariable;
import shadow.output.AbstractTACVisitor;

public class TACLoop extends TACNode {

	private TACVariable lhs;
	private TACVariable rhs;
	private TACComparison comparision;
	
	private TACNode loopNode;	/** The path that brings you back to this loop */
	private TACNode breakNode;	/** The path that breaks you out of the loop */

	public TACLoop(TACVariable lhs, TACComparison comparison, TACVariable rhs) {
		super("LOOP: ", null);
		
		this.lhs = lhs;
		this.comparision = comparison;
		this.rhs = rhs;
	}

	public TACNode getLoopNode() {
		return loopNode;
	}

	public void setLoopNode(TACNode loopNode) {
		this.loopNode = loopNode;
	}

	public TACNode getBreakNode() {
		return breakNode;
	}

	public void setBreakNode(TACNode breakNode) {
		this.breakNode = breakNode;
	}
	
	public void accept(AbstractTACVisitor visitor) {
		visitor.visit(this);
	}
	
	public String toString() {
		return super.toString() + lhs + " " + comparision + " " + rhs;	
	}

	public void dump(String prefix) {
		System.out.println(prefix + "LOOP: " + lhs + " " + comparision + " " + rhs);
		
		System.out.println(prefix + "  BODY: ");
		TACNode node = loopNode;
		
		// the best we can do here
		while(node != null && node != this) {
			System.out.println(prefix + "        " + node);
			node = node.getNext();
		}
		
		System.out.println(prefix + " BREAK: ");
		if(breakNode != null)
			breakNode.dump(prefix + "        ");
	}

}
