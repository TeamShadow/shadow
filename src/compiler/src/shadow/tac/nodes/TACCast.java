package shadow.tac.nodes;

import shadow.output.AbstractTACVisitor;
import shadow.typecheck.type.Type;

public class TACCast extends TACNode
{
	private Type type;
	private TACNode operand;
	public TACCast(Type newType, TACNode operandNode)
	{
		type = newType;
		operand = operandNode;
	}
	
	@Override
	public Type getType()
	{
		return type;
	}
	public TACNode getOperand()
	{
		return operand;
	}
	
	@Override
	public String toString()
	{
		return "cast<" + type + ">(" + operand + ')';
	}
	
	@Override
	public void accept(AbstractTACVisitor visitor)
	{
		visitor.visit(this);
	}
}
