package shadow.TAC.nodes;

import java.util.LinkedList;

public class TACBranch extends TACNode {

	protected TACNode trueEntry;
	protected TACNode falseEntry;
	protected TACNode exit;
	
	public TACBranch(TACNode parent) {
		super("BRANCH", parent);
	}
	
	public void dump(String prefix) {
		System.out.println(prefix + "T: " + trueEntry);
		trueEntry.next.dump(prefix + "   ");
		
		System.out.println(prefix + "F: " + falseEntry);
		falseEntry.next.dump(prefix + "   ");
		
		exit.dump(prefix);
	}
}
