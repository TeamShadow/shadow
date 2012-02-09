package shadow.TAC.nodes;

import org.apache.commons.logging.Log;

import shadow.Loggers;
import shadow.TAC.TACVariable;
import shadow.output.AbstractTACVisitor;
import shadow.parser.javacc.Node;

public class TACLoop extends TACNode {
	private static final Log logger = Loggers.TAC;

	private TACVariable lhs;
	private TACVariable rhs;
	private TACComparison comparison;
	
	private TACNode loopNode;	/** The path that brings you back to this loop */
	private TACNode breakNode;	/** The path that breaks you out of the loop */

	public TACLoop(Node astNode, TACVariable lhs, TACComparison comparison, TACVariable rhs) {
		super(astNode, "LOOP: ", null);
		
		this.lhs = lhs;
		this.comparison = comparison;
		this.rhs = rhs;
	}
	
	public TACVariable getLHS() {
		return lhs;
	}
	
	public TACComparison getComparision() {
		return comparison;
	}
	
	public TACVariable getRHS() {
		return rhs;
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
		return super.toString() + lhs + " " + comparison + " " + rhs;	
	}

	public void dump(String prefix) {
		logger.debug(prefix + "LOOP: " + lhs + " " + comparison + " " + rhs);
		
		logger.debug(prefix + "  BODY: ");
		TACNode node = loopNode;
		
		// the best we can do here
		while(node != null && node != this) {
			logger.debug(prefix + "        " + node);
			node = node.getNext();
		}
		
		logger.debug(prefix + " BREAK: ");
		if(breakNode != null)
			breakNode.dump(prefix + "        ");
	}

}
