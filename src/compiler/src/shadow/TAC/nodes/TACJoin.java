package shadow.TAC.nodes;

public class TACJoin extends TACNode {
	private TACNode trueExit, falseExit;
	
	public TACJoin(TACNode trueExit, TACNode falseExit) {
		super("JOIN", null);
		this.trueExit = trueExit;
		this.falseExit = falseExit;
	}
	
	public void dump(String prefix) {
		
	}
}
