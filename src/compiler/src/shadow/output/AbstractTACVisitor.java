package shadow.output;

import org.apache.commons.logging.Log;

import shadow.Loggers;
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
import shadow.TAC.nodes.TACReturn;
import shadow.TAC.nodes.TACUnaryOperation;
import shadow.typecheck.type.ClassType;

public abstract class AbstractTACVisitor {
	private static final Log logger = Loggers.TAC;
	
	private TACClass theClass;
	
	public AbstractTACVisitor(TACClass theClass) {
		this.theClass = theClass;
	}
	
	public TACClass getTheClass() {
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
	
	public void visit(TACNodeInterface node) {
		node.accept(this);
	}
	
	public void visit(TACNode node) {
		logger.debug(node);
	}
	
	public void visit(TACAssign node) {
		logger.debug(node);
	}
	
	public void visit(TACAllocation node) {
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
	}
}
