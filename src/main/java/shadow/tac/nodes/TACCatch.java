package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACMethod;
import shadow.tac.TACVariable;
import shadow.tac.TACVisitor;
import shadow.tac.TACMethod.TACFinallyFunction;
import shadow.typecheck.type.ExceptionType;

public class TACCatch extends TACOperand {
	private TACLabel successor;
	private ExceptionType type;
	private TACVariable variable;
	private TACLabel catchBody;
	
	public TACCatch(TACNode node, ExceptionType catchType, TACVariable variable, TACLabel catchBody) {
		super(node);
		type = catchType;
		this.variable = variable;
		this.catchBody = catchBody;
	}	

	public TACLabel getCatchBody() {
		return catchBody;
	}
	
	public void setSuccessor(TACLabel successor ) {
		this.successor = successor;
	}
	
	public TACLabel getSuccessor() {
		return successor;
	}
	
	@Override
	public int getNumOperands() {
		return 0;
	}
	
	@Override
	public TACOperand getOperand(int num) {
		throw new IndexOutOfBoundsException("" + num);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException {
		visitor.visit(this);
	}

	@Override
	public ExceptionType getType() {
		return type;
	}
	
	public TACVariable getVariable() {
		return variable;
	}

}
