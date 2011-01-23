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
	private TACVariable variable;

	public TACAllocation(Node astNode, TACVariable variable) {
		super(astNode, "allocate: ", null);
		this.variable = variable;
	}
	
	public TACAllocation(Node astNode, TACVariable variable, TACNode parent) {
		super(astNode, "allocate: ", parent);
		this.variable = variable;
	}
	
	public TACAllocation(Node astNode, TACVariable variable, TACNode parent, TACNode next) {
		super(astNode, "allocate: ", parent, next);
		this.variable = variable;
	}

	public void accept(AbstractTACVisitor visitor) {
		visitor.visit(this);
	}

	public TACVariable getVariable() {
		return variable;
	}
	
	public String toString() {
		return name + variable;
	}
}
