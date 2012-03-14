package shadow.output;

import shadow.tac.TACMethod;
import shadow.tac.TACModule;
import shadow.tac.nodes.TACAllocation;
import shadow.tac.nodes.TACAssign;
import shadow.tac.nodes.TACBinary;
import shadow.tac.nodes.TACBranch;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACCast;
import shadow.tac.nodes.TACComparison;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACNode;
import shadow.tac.nodes.TACPhi;
import shadow.tac.nodes.TACPhiBranch;
import shadow.tac.nodes.TACReference;
import shadow.tac.nodes.TACReturn;
import shadow.tac.nodes.TACSequence;
import shadow.tac.nodes.TACUnary;
import shadow.tac.nodes.TACVariable;
import shadow.typecheck.type.ClassType;

public abstract class AbstractTACVisitor {
//	private static final Log logger = Loggers.TAC;
	
	private TACModule theClass;
	
	public AbstractTACVisitor(TACModule theClass) {
		this.theClass = theClass;
	}
	
	public TACModule getTheClass() {
		return theClass;
	}
	
	public ClassType getClassType() {
		return (ClassType)theClass.getType();
	}
	
	abstract public void startFile();
	abstract public void endFile();
	
	abstract public void startFields();
	abstract public void endFields();
	
	abstract public void startMethod(TACMethod method);
	abstract public void endMethod(TACMethod method);
	
	public void visit(TACNode node)
	{
		node.accept(this);
	}

	public abstract void visit(TACAllocation node);
	public abstract void visit(TACAssign node);
	public abstract void visit(TACBinary node);
	public abstract void visit(TACBranch node);
	public abstract void visit(TACCall node);
	public abstract void visit(TACCast node);
	public abstract void visit(TACComparison node);
	public abstract void visit(TACLiteral node);
	public abstract void visit(TACPhi node);
	public abstract void visit(TACPhiBranch node);
	public abstract void visit(TACReference node);
	public abstract void visit(TACReturn node);
	public abstract void visit(TACSequence node);
	public abstract void visit(TACUnary node);
	public abstract void visit(TACVariable node);
	
	/*public void visit(TACAllocation node) {
		logger.debug(node);
	}

	public void visit(TACOperator node) {
		logger.debug(node);
	}
	
	/*public void visit(TACNode node) {
		logger.debug(node);
	}
	
	public void visit(TACAssign node) {
		logger.debug(node);
	}
	
	public void visit(TACBinaryOperation node) {
		logger.debug(node);
	}
	
	public void visit(TACBranch node) {
		logger.debug(node);
	}
	
	public void visit(TACLoop node) {
		logger.debug(node);
	}
	
	public void visit(TACJoin node) {
		logger.debug(node);
	}
	
	public void visit(TACNoOp node) {
		logger.debug(node);
	}

	public void visit(TACUnaryOperation node) {
		logger.debug(node);
	}

	public void visit(TACMethodCall node) {
		logger.debug(node);
	}

	public void visit(TACReturn node) {
		logger.debug(node);
	}*/
}
