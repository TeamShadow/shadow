package shadow.TAC.nodes;

import shadow.TAC.TACVariable;

public class TACBranch extends TACNode {

	protected TACNode trueEntry;
	protected TACNode falseEntry;
	protected TACJoin join;
	protected TACVariable conditional;
	
	public TACBranch(TACNode trueEntry, TACNode falseEntry, TACJoin join, TACVariable conditional) {
		super("BRANCH", null);
		
		this.trueEntry = trueEntry;
		this.falseEntry = falseEntry;
		this.join = join;
		this.conditional = conditional;
	}
	
	public void dump(String prefix) {
		System.out.println(prefix + "BRANCH: " + conditional);
		
		System.out.println(prefix + "  TRUE: " + trueEntry);
		if(trueEntry.next != null)
			trueEntry.next.dump(prefix + "        ");
		
		System.out.println(prefix + " FALSE: " + falseEntry);
		if(falseEntry.next != null)
			falseEntry.next.dump(prefix + "        ");
		
		join.dump(prefix);
	}
}
