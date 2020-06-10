package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACCleanupPad extends TACPad {

	public TACCleanupPad(TACNode node, TACLabel label) {
		super(node, label);
	}	
	
	@Override
	public void accept(TACVisitor visitor) throws ShadowException {
		visitor.visit(this);
	}

	@Override
	public Type getType() {
		return null;
	}
}
