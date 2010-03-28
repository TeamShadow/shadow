package shadow.TAC.nodes;

import java.util.LinkedList;

public class TACBranch extends TACNode {

	protected TACNode trueEntry, trueExit;
	protected TACNode falseEntry, falseExit;
	
	public TACBranch(TACNode parent) {
		super("", parent);
	}
}
