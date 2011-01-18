/**
 * 
 */
package shadow.TAC.nodes;

import shadow.TAC.TACVariable;

/**
 * @author wspeirs
 *
 */
public class TACAllocation extends TACNode {
	private TACVariable variable;

	public TACAllocation(TACVariable variable) {
		super("allocate: ", null);
		this.variable = variable;
	}
	
	public TACAllocation(TACVariable variable, TACNode parent) {
		super("allocate: ", parent);
		this.variable = variable;
	}
	
	public TACAllocation(TACVariable variable, TACNode parent, TACNode next) {
		super("allocate: ", parent, next);
		this.variable = variable;
	}

	public TACVariable getVariable() {
		return variable;
	}
	
	public String toString() {
		return name + variable;
	}
}
