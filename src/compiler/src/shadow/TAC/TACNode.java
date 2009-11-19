package shadow.TAC;

public abstract class TACNode {
	protected String name;
	protected TACNode parent;
	
	public TACNode(String name, TACNode parent) {
		this.name = name;
		this.parent = parent;
	}
	
	public TACNode getParent() {
		return this.parent;
	}
	
	public String toString() {
		return this.getClass().getSimpleName() + ": " + name;
	}
}
