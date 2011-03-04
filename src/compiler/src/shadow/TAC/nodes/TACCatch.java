/**
 * 
 */
package shadow.TAC.nodes;

import shadow.TAC.TACVariable;
import shadow.parser.javacc.Node;

/**
 * @author wspeirs
 *
 */
public class TACCatch extends TACNode {
	
	private TACVariable variable;

	/**
	 * @param astNode
	 * @param name
	 * @param parent
	 */
	public TACCatch(Node astNode, TACNode parent, TACNode child) {
		super(astNode, "CATCH", parent, child);
	}

	public TACVariable getVariable() {
		return variable;
	}

	public void setVariable(TACVariable variable) {
		this.variable = variable;
	}
	

}
