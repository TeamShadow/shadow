package shadow.tac.nodes;

import java.io.IOException;

import shadow.tac.AbstractTACVisitor;
import shadow.typecheck.type.Type;

public class TACPhiBranch extends TACBranch
{
	private TACLabel from;
	private TACNode value;
	private TACPhi phi;
	public TACPhiBranch(TACLabel fromLabel, TACNode valueNode, TACPhi phiNode)
	{
		super(phiNode.getLabel());
		from = fromLabel;
		value = valueNode;
		phi = phiNode;
		phiNode.addBranch(this);
	}
	public TACPhiBranch(TACLabel fromLabel, TACNode conditionNode, TACPhi truePhiNode, TACLabel falseLabel)
	{
		this(fromLabel, conditionNode, truePhiNode.getLabel(), falseLabel, truePhiNode, conditionNode);
	}
	public TACPhiBranch(TACLabel fromLabel, TACNode conditionNode, TACPhi truePhiNode, TACLabel falseLabel, TACNode valueNode)
	{
		this(fromLabel, conditionNode, truePhiNode.getLabel(), falseLabel, truePhiNode, valueNode);
	}
	public TACPhiBranch(TACLabel fromLabel, TACNode conditionNode, TACLabel trueLabel, TACPhi falsePhiNode)
	{
		this(fromLabel, conditionNode, trueLabel, falsePhiNode.getLabel(), falsePhiNode, conditionNode);
	}
	public TACPhiBranch(TACLabel fromLabel, TACNode conditionNode, TACLabel trueLabel, TACPhi falsePhiNode, TACNode valueNode)
	{
		this(fromLabel, conditionNode, trueLabel, falsePhiNode.getLabel(), falsePhiNode, valueNode);
	}
	public TACPhiBranch(TACLabel fromLabel, TACNode conditionNode, TACLabel trueLabel,
			TACLabel falseLabel, TACPhi phiNode, TACNode valueNode)
	{
		super(conditionNode, trueLabel, falseLabel);
		from = fromLabel;
		value = valueNode;
		phi = phiNode;
		phi.addBranch(this);
	}
	
	@Override
	public Type getType()
	{
		return value.getType();
	}
	public TACLabel getFromLabel()
	{
		return from;
	}
	public TACNode getValue()
	{
		return value;
	}
	public TACPhi getPhi()
	{
		return phi;
	}
	
	@Override
	public void accept(AbstractTACVisitor visitor) throws IOException
	{
		visitor.visit(this);
	}
}
