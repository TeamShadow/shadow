/**
 * 
 */
package shadow.TAC.nodes;

import java.util.ArrayList;
import java.util.List;

import shadow.parser.javacc.Node;

/**
 * @author William R. Speirs
 *
 */
public class TACTry extends TACNode {
	
	private List<TACCatch> catchNodes;
	private TACNode finallyNode;

	/**
	 * @param astNode
	 * @param name
	 * @param parent
	 */
	public TACTry(Node astNode, TACNode parent) {
		super(astNode, "TRY", parent);
		catchNodes = new ArrayList<TACCatch>();
	}

	public TACNode getFinallyNode() {
		return finallyNode;
	}

	public void setFinallyNode(TACNode finallyNode) {
		this.finallyNode = finallyNode;
	}

	public List<TACCatch> getCatchNodes() {
		return catchNodes;
	}

	public void addCatchNode(TACCatch catchNode) {
		catchNodes.add(catchNode);
	}

}
