package shadow.TAC.nodes;

public abstract class TACNode {
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
	 * This sets BOTH in the node's parent and this.next
	 * @param node The node to link onto the end of this node
	 */
	public void setNext(TACNode node) {
		node.parent = this;
		this.next = node;
	}
	
	public String toString() {
		return this.getClass().getSimpleName() + ": " + name;
	}
}
