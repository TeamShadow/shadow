package shadow.TAC.nodes;

import shadow.TAC.TACVariable;
import shadow.output.AbstractTACVisitor;

public class TACBranch extends TACNode {

	protected TACNode trueEntry;
	protected TACNode falseEntry;
	protected TACVariable lhs;
	protected TACVariable rhs;
	protected TACComparison comparision;
	protected TACJoin join;
	
	public TACBranch(TACVariable lhs, TACVariable rhs, TACComparison comparison) {
		this(lhs, rhs, comparison, null, null);
	}
	
	public TACBranch(TACVariable lhs, TACVariable rhs, TACComparison comparison, TACNode trueEntry, TACNode falseEntry) {
		super("", null);
		
		this.lhs = lhs;
		this.rhs = rhs;
		this.comparision = comparison;
		this.trueEntry = trueEntry;
		this.falseEntry = falseEntry;
		this.join = null;
	}

	public void accept(AbstractTACVisitor visitor) {
		visitor.visit(this);
	}
	
	public void setJoin(TACJoin join) {
		this.join = join;
	}
	
	public TACJoin getJoin() {
		return join;
	}
	
	public void setTrueEntry(TACNode trueEntry) {
		this.trueEntry = trueEntry;
	}
	
	public TACNode getTrueEntry() {
		return trueEntry;
	}
	
	public void setFalseEntry(TACNode falseEntry) {
		this.falseEntry = falseEntry;
	}
	
	public TACNode getFalseEntry() {
		return falseEntry;
	}
	
	public String toString() {
		return super.toString() + lhs + " " + comparision + " " + rhs;	
	}
	
	public void dump(String prefix) {
		System.out.println(prefix + "BRANCH: " + lhs + " " + comparision + " " + rhs);
		
		System.out.println(prefix + "  TRUE: ");
		if(trueEntry != null)
			trueEntry.dump(prefix + "        ");
		
		System.out.println(prefix + " FALSE: ");
		if(falseEntry != null)
			falseEntry.dump(prefix + "        ");
		
		join.dump(prefix);
	}
}
