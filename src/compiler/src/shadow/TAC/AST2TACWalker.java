
package shadow.TAC;

import shadow.AST.AbstractASTVisitor;
import shadow.TAC.nodes.TACNode;

public class AST2TACWalker extends AbstractASTVisitor {
	private TACNode root;
	
	/**
	 * Walks a portion of an AST and creates a TAC tree
	 */
	public AST2TACWalker() {

	}
	
	public TACNode getTACRoot() {
		return root;
	}

}
