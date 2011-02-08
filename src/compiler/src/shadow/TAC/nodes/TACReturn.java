/**
 * 
 */
package shadow.TAC.nodes;

import shadow.TAC.TACVariable;
import shadow.output.AbstractTACVisitor;
import shadow.parser.javacc.Node;

/**
 * Represents returning a variable
 *
 */
public class TACReturn extends TACNode {
	
	private TACVariable returnVar;

	/**
	 * @param astNode
	 */
	public TACReturn(Node astNode, TACVariable returnVar, TACNode parent) {
		super(astNode, "RETURN", parent);
		this.returnVar = returnVar;
	}

	public TACVariable getReturn() {
		return returnVar;
	}

	public void accept(AbstractTACVisitor visitor) {
		visitor.visit(this);
	}

}
