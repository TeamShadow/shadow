package shadow.tac.nodes;

import shadow.output.AbstractTACVisitor;
import shadow.typecheck.type.Type;

public class TACBranch extends TACNode
{
	private TACNode condition, trueBranch, falseBranch;
	public TACBranch(TACNode branchNode)
	{
		trueBranch = branchNode;
		branchNode.addTarget();
		condition = falseBranch = null;
	}
	public TACBranch(TACNode conditionNode, TACNode trueBranchNode, TACNode falseBranchNode)
	{
		if (!conditionNode.getType().equals(Type.BOOLEAN))
			throw new IllegalArgumentException("Condition must be of type boolean.");
		condition = conditionNode;
		trueBranch = trueBranchNode;
		trueBranchNode.addTarget();
		falseBranch = falseBranchNode;
		falseBranchNode.addTarget();
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
		return trueBranch;
	}
	public TACNode getTrueBranch()
	{
		return trueBranch;
	}
	public TACNode getFalseBranch()
	{
		return falseBranch;
	}
	
	@Override
	public String toString()
	{
		if (isConditional())
			return "goto " + condition + " ? " + trueBranch.getLabel() + " : " + falseBranch.getLabel();
		else
			return "goto " + trueBranch.getLabel();
	}
	
	@Override
	public void accept(AbstractTACVisitor visitor)
	{
		visitor.visit(this);
	}
}
