package shadow.output;

import shadow.TAC.nodes.TACAssign;
import shadow.TAC.nodes.TACBinaryOperation;
import shadow.TAC.nodes.TACBranch;
import shadow.TAC.nodes.TACJoin;
import shadow.TAC.nodes.TACLoop;
import shadow.TAC.nodes.TACNoOp;
import shadow.TAC.nodes.TACNode;
import shadow.TAC.nodes.TACNodeInterface;
import shadow.TAC.nodes.TACUnaryOperation;

public abstract class AbstractTACVisitor {
	private TACNode root;
	
	public AbstractTACVisitor(TACNode root) {
		this.root = root;
	}
	
	public TACNode getRoot() {
		return root;
	}
	
	abstract public void start();
	abstract public void end();
	
	public void visit(TACNodeInterface node) {
		node.accept(this);
	}
	
	public void visit(TACNode node) {
		System.out.println(node);
	}
	
	public void visit(TACAssign node) {
		System.out.println(node);
	}
	
	public void visit(TACBinaryOperation node) {
		System.out.println(node);
	}
	
	public void visit(TACBranch node) {
		System.out.println(node);
	}
	
	public void visit(TACLoop node) {
		System.out.println(node);
	}
	
	public void visit(TACJoin node) {
		System.out.println(node);
	}
	
	public void visit(TACNoOp node) {
		System.out.println(node);
	}

	public void visit(TACUnaryOperation node) {
		System.out.println(node);
	}
}
