package shadow.TAC.nodes;

public class TACJoin extends TACNode {
	private TACNode trueExit, falseExit;
	
	public TACJoin(TACNode trueExit, TACNode falseExit) {
		super("JOIN", null);
		this.trueExit = trueExit;
		this.falseExit = falseExit;
		
		trueExit.next = this;
		falseExit.next = this;
	}
	
	public void dump(String prefix) {
		System.out.println(prefix + " *JOIN*");
		
		if(next == null)
			return;
		
		if(next instanceof TACJoin) {
			return;
		}
		
		next.dump(prefix);
	}

	public TACNode getTrueExit() {
		return trueExit;
	}
	
	public TACNode getFalseExit() {
		return falseExit;
	}
}
