package shadow.tac.nodes;

import shadow.tac.AbstractTACVisitor;
import shadow.typecheck.type.Type;

public class TACBranch extends TACNode
{
	private TACNode condition;
	private TACLabel trueLabel, falseLabel;
	public TACBranch(TACLabel branchNode)
	{
		trueLabel = branchNode;
		condition = falseLabel = null;
	}
	public TACBranch(TACNode conditionNode, TACLabel trueNode, TACLabel falseNode)
	{
		if (!conditionNode.getType().equals(Type.BOOLEAN))
			throw new IllegalArgumentException("Condition must be of type boolean.");
		condition = conditionNode;
		trueLabel = trueNode;
		falseLabel = falseNode;
	}
	
	@Override
	public Type getType()
	{
		return null;
	}
	public boolean isConditional()
	{
		return condition != null;
	}
	public TACNode getCondition()
	{
		return condition;
	}
	public TACNode getBranch()
	{
		return trueLabel;
	}
	public TACNode getTrueBranch()
	{
		return trueLabel;
	}
	public TACNode getFalseBranch()
	{
		return falseLabel;
	}
	
	@Override
	public String toString()
	{
		if (isConditional())
			return "goto " + condition + " ? " +
				trueLabel.getSymbol() + " : " +
				falseLabel.getSymbol();
		else
			return "goto " + trueLabel.getSymbol();
	}
	
	@Override
	public void accept(AbstractTACVisitor visitor)
	{
		visitor.visit(this);
	}
}
