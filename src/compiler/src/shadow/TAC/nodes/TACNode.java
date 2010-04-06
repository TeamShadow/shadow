package shadow.TAC.nodes;

public class TACNode {
	protected String name;
	protected TACNode parent;
	protected TACNode next;
	
	public TACNode(String name, TACNode parent) {
		this.name = name;
		this.parent = parent;
		this.next = null;
	}
	
	public TACNode(String name, TACNode parent, TACNode next) {
		this(name, parent);
		this.next = next;
	}
	
	public TACNode getParent() {
		return this.parent;
	}
	
	public TACNode getNext() {
		return this.next;
	}
	
	/**
	 * Inserts a node after this node
	 * @param node The node to link onto the end of this node
	 */
	public void insertAfter(TACNode node) {
		node.parent = this;
		node.next = this.next;

		if(this.next != null)
			this.next.parent = node;

		this.next = node;
	}

	/**
	 * Inserts a node before this node
	 * @param node The node to link onto the end of this node
	 */
	public void insertBefore(TACNode node) {
		node.parent = this.parent;
		node.next = this;

		if(this.parent != null)
			this.parent.next = node;

		this.parent = node;
	}
	
	public String toString() {
		return this.getClass().getSimpleName() + ": " + name;
	}
	
	public void dump(String prefix) {
		System.out.println(prefix + this);
		if(next != null && !(next instanceof TACJoin))
			next.dump(prefix);
	}
}
