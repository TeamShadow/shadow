package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVariable;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACTypeId extends TACOperand
{
	private TACOperand operand;
	private TACVariable variable;
	
	/**
	 * TACTypeId can take a class operand, in which case it will call a
	 * typeid LLVM intrinsic when converted into LLVM.
	 * @param node
	 * @param op
	 */
	public TACTypeId(TACNode node, TACOperand op)
	{
		super(node);
		operand = op;
	}
	
	
	/**
	 * TACTypeId can also take a variable referencing an existing exception,
	 * in which case it simply return the integer typeid already stored in
	 * the exception. 
	 * @param node
	 * @param variable
	 */	
	public TACTypeId(TACNode node, TACVariable variable)
	{
		super(node);
		this.variable = variable;
	}
	
	public boolean hasOperand() {
		return operand != null;
	}
	
	public TACVariable getVariable() {
		return variable;
	}	

	public TACOperand getOperand()
	{
		return operand;
	}

	@Override
	public Type getType()
	{
		return Type.INT;
	}
	@Override
	public int getNumOperands()
	{
		return hasOperand() ? 1 : 0;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (num == 0 && hasOperand() )
			return getOperand();
		throw new IndexOutOfBoundsException("" + num);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return "typeid(" + (hasOperand() ? getOperand() : getVariable()) + ')';
	}
}
