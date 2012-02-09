/**
 * 
 */
package shadow.TAC.nodes;

import shadow.TAC.TACVariable;
import shadow.output.AbstractTACVisitor;
import shadow.parser.javacc.Node;

/**
 * @author wspeirs
 *
 */
public class TACAllocation extends TACNode {
	private TACVariable variable, size;

	public TACAllocation(Node astNode, TACVariable variable) {
		super(astNode, "allocate: ", null);
		this.variable = variable;
		this.size = null;
	}
	
	public TACAllocation(Node astNode, TACVariable variable, TACNode parent) {
		super(astNode, "allocate: ", parent);
		this.variable = variable;
		this.size = null;
	}
	
	public TACAllocation(Node astNode, TACVariable variable, TACNode parent, TACNode next) {
		super(astNode, "allocate: ", parent, next);
		this.variable = variable;
		this.size = null;
	}
	public TACAllocation(Node astNode, TACVariable variable, TACVariable size, TACNode parent, TACNode next) {
		super(astNode, "allocate: ", parent, next);
		this.variable = variable;
		this.size = size;
	}
	
	public void accept(AbstractTACVisitor visitor) {
		visitor.visit(this);
	}

	public TACVariable getVariable() {
		return variable;
	}
	
	public TACVariable getSize() {
		return size;
	}
	
	public boolean isOnHeap() {
		return size != null;
	}
	
	public String toString() {
		return name + variable;
	}
}
