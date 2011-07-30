package shadow.TAC.nodes;

import java.util.LinkedList;

import org.apache.commons.logging.Log;

import shadow.Loggers;
import shadow.output.AbstractTACVisitor;
import shadow.parser.javacc.Node;

public abstract class TACNode implements TACNodeInterface {
	private static final Log logger = Loggers.TAC;
	
	private static int labelCounter = 0;
	protected String name;
	protected TACNode parent;
	protected TACNode next;
	private Node astNode;	/** This is the AST node that caused the creation of the TAC node */
	
	private static LinkedList<TACNode> allNodes = new LinkedList<TACNode>();
	
	/**
	 * These are the different types of operations that can be performed in a TAC(Unary|Binary)Operation
	 */
	public enum TACOperation {
		ADDITION,
		SUBTRACTION,
		MULTIPLICATION,
		DIVISION,
		MOD,
		OR,		/** bit-wise */
		AND,	/** bit-wise */
		XOR,
		RSHIFT,
		RROTATE,
		LSHIFT,
		LROTATE
	}

	/**
	 * These are the various types of comparisons we find in a TACBranch
	 */
	public enum TACComparison {
		GREATER,
		GREATER_EQUAL,
		LESS,
		LESS_EQUAL,
		EQUAL,
		NOT_EQUAL,
		IS
	}

	
	public TACNode(Node astNode, String name, TACNode parent) {
		this.astNode = astNode;
		this.name = "label_" + labelCounter++ + ": " + name;
		this.parent = parent;
		this.next = null;
		TACNode.allNodes.add(this);
	}
	
	public TACNode(Node astNode, String name, TACNode parent, TACNode next) {
		this(astNode, name, parent);
		this.next = next;
	}
	
	public void accept(AbstractTACVisitor visitor) {
		visitor.visit(this);
	}
	
	public LinkedList<TACNode> getNodes() {
		return allNodes;
	}
	
	public TACNode getParent() {
		return this.parent;
	}

	public void setParent(TACNode node) {
		this.parent = node;
	}
	
	public TACNode getNext() {
		return this.next;
	}

	public void setNext(TACNode node) {
		this.next = node;
	}
	
	public Node getAstNode() {
		return astNode;
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
		return name;
	}
	
	public void dump(String prefix) {
//		logger.debug(prefix + this.parent + " <- " + this + " -> " + this.next);
		logger.debug(prefix + this);
		
		if(next == null)
			return;
		
		if(next instanceof TACJoin) {
			return;
		}
		
		next.dump(prefix);
	}
}
