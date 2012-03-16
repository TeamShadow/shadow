package shadow.tac.nodes;

import java.io.IOException;

import shadow.tac.AbstractTACVisitor;
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
	public void accept(AbstractTACVisitor visitor) throws IOException
	{
		visitor.visit(this);
	}
}
