/**
 * 
 */
package shadow.TAC.nodes;

import java.util.LinkedList;
import java.util.List;

import shadow.TAC.TACVariable;
import shadow.output.AbstractTACVisitor;
import shadow.parser.javacc.Node;

/**
 * Represents returning a variable
 *
 */
public class TACReturn extends TACNode {
	
	private List<TACVariable> returnVar;

	/**
	 * @param astNode
	 */
	public TACReturn(Node astNode) {
		super(astNode, "RETURN", null);
		this.returnVar = new LinkedList<TACVariable>();
	}
	
	public void addReturn(TACVariable returnVar) {
		this.returnVar.add(returnVar);
	}

	public List<TACVariable> getReturns() {
		return returnVar;
	}

	public void accept(AbstractTACVisitor visitor) {
		visitor.visit(this);
	}

}
