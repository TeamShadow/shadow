package shadow.output;

import shadow.TAC.TACClass;
import shadow.TAC.TACMethod;
import shadow.TAC.nodes.TACAllocation;
import shadow.TAC.nodes.TACAssign;
import shadow.TAC.nodes.TACBinaryOperation;
import shadow.TAC.nodes.TACBranch;
import shadow.TAC.nodes.TACJoin;
import shadow.TAC.nodes.TACLoop;
import shadow.TAC.nodes.TACMethodCall;
import shadow.TAC.nodes.TACNoOp;
import shadow.TAC.nodes.TACNode;
import shadow.TAC.nodes.TACNodeInterface;
import shadow.TAC.nodes.TACUnaryOperation;

public abstract class AbstractTACVisitor {
	private TACClass theClass;
	
	public AbstractTACVisitor(TACClass theClass) {
		this.theClass = theClass;
	}
	
	public TACClass getTheClass() {
		return theClass;
	}
	
	abstract public void startFile();
	abstract public void endFile();
	
	abstract public void startFields();
	abstract public void endFields();
	
	abstract public void startMethod(TACMethod method);
	abstract public void endMethod(TACMethod method);
	
	public void visit(TACNodeInterface node) {
		node.accept(this);
	}
	
	public void visit(TACNode node) {
		System.out.println(node);
	}
	
	public void visit(TACAssign node) {
		System.out.println(node);
	}
	
	public void visit(TACAllocation node) {
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

	public void visit(TACMethodCall node) {
		System.out.println(node);
	}
}
