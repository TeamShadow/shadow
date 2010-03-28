
package shadow.TAC;

import shadow.AST.AbstractASTVisitor;
import shadow.TAC.nodes.TACNode;

public class AST2TACWalker extends AbstractASTVisitor {
	private TACNode entryNode;	/** The first node in the tree */
	private TACNode exitNode;	/** The last node in the tree... only 1 exit always */
	
	/**
	 * Walks a portion of an AST and creates a TAC tree.
	 * This class does all of the heavy lifting in converting from AST -> TAC.
	 */
	public AST2TACWalker() {

	}
	
	public TACNode getEntry() {
		return entryNode;
	}
	
	public TACNode getExit() {
		return exitNode;
	}

}
