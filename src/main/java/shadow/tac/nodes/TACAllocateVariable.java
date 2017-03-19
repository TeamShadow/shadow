package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVariable;
import shadow.tac.TACVisitor;

public class TACAllocateVariable extends TACNode {

	TACVariable variable;
	
	public TACAllocateVariable(TACNode node, TACVariable variable) {
		super(node);
		this.variable = variable;
	}

	@Override
	public int getNumOperands()
	{		
		return 0;
	}
	
	public TACVariable getVariable()
	{
		return variable;
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

}
