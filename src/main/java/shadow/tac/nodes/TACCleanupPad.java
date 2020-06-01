package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVisitor;

public class TACCleanupPad extends TACNode implements TACPad {
	
	private String token;
	private TACLabel label;

	public TACCleanupPad(TACNode node, TACLabel label)
	{
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
	
	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}


	@Override
	public void setToken(String token) {
		this.token = token;		
	}

	@Override
	public String getToken() {
		return token;
	}
}
