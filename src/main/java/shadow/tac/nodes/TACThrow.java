package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

/** 
 * TAC representation of exception throw
 * Example: throw e
 * @author Jacob Young
 * @author Barry Wittman
 */

public class TACThrow extends TACNode
{	
	private TACOperand exception;
	private boolean rethrow;

	public TACThrow(TACNode node, TACOperand op) {
		this(node, op, false);
	}
	
	public TACThrow(TACNode node, TACOperand op, boolean rethrow) {
		super(node);
		node.getBlock().addUnwindSource();
		this.rethrow = rethrow;
		if(rethrow)
			exception = op;
		else
			exception = check(op, new SimpleModifiedType(Type.OBJECT));
	}
	
	public TACOperand getException() {
		return exception;
	}

	@Override
	public int getNumOperands() {
		return rethrow ? 0 : 1;
	}
	@Override
	public TACOperand getOperand(int num) {
		if (!rethrow && num == 0)
			return getException();
		throw new IndexOutOfBoundsException("" + num);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException {
		visitor.visit(this);
	}
	
	public boolean isRethrow() {
		return rethrow;
	}

	@Override
	public String toString() {
		if(isRethrow())
			return "rethrow";
		else
			return "throw " + getException();
	}
}
