package shadow.TAC.nodes;

import java.util.LinkedList;

public class TACBranch extends TACNode {

	protected LinkedList<TACNode> trueBody;
	protected LinkedList<TACNode> falseBody;
	
	public TACBranch(TACNode parent) {
		super("", parent);
		trueBody = new LinkedList<TACNode>();
		falseBody = new LinkedList<TACNode>();
	}
	
	public void addTrueNode(TACNode node) {
		trueBody.add(node);
	}
	
	public LinkedList<TACNode> getTrueBranch() {
		return trueBody;
	}
	
	public void addFalseNode(TACNode node) {
		falseBody.add(node);
	}
	
	public LinkedList<TACNode> getFalseBranch() {
		return falseBody;
	}
}
