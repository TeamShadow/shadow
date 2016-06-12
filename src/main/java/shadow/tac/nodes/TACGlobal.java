package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACGlobal extends TACOperand {
	private Type type;
	private String name;
	
	public TACGlobal(TACNode node, Type type, String name)
	{
		super(node);
		this.type = type;
		this.name = name;
	}
	
	@Override
	public Type getType() {
		return type;
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

	public String getName() {
		return name;
	}
	
	@Override
	public boolean canPropagate() {
		return true;
	}
}
