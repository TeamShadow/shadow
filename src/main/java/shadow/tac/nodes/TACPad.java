package shadow.tac.nodes;

public abstract class TACPad extends TACOperand {
	private TACLabel label;
	
	public TACPad(TACNode node, TACLabel label) {
		super(node);
		this.label = label;
	}
	
	public TACLabel getLabel() {
		return label;
	}
	
	@Override
	public int getNumOperands()
	{
		return 0;
	}
	
	@Override
	public TACOperand getOperand(int num)
	{
		throw new IndexOutOfBoundsException("" + num);
	}
}
