package shadow.tac.nodes;

import java.util.List;

import shadow.ShadowException;
import shadow.tac.TACVariable;
import shadow.tac.TACVisitor;

public class TACLocalEscape extends TACNode {
	
	private List<TACVariable> escapedVariables;

	public TACLocalEscape(TACNode node, List<TACVariable> escapedVariables) {
		super(node);
		this.escapedVariables = escapedVariables;
	}

	@Override
	public int getNumOperands() {
		return 0;
	}

	@Override
	public TACOperand getOperand(int num) {
		throw new IndexOutOfBoundsException("" + num);
	}
	
	public List<TACVariable> getEscapedVariables() {
		return escapedVariables;
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException  {
		visitor.visit(this);
	}

}
