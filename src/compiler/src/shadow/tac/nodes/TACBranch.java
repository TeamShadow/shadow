package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACBranch extends TACSimpleNode
{
	private TACOperand condition;
	private TACLabelRef trueLabel, falseLabel;
	public TACBranch(TACLabelRef labelRef)
	{
		this(null, labelRef);
	}
	public TACBranch(TACNode node, TACLabelRef labelRef)
	{
		this(node, null, labelRef, labelRef);
	}
	public TACBranch(TACOperand cond, TACLabelRef trueRef, TACLabelRef falseRef)
	{
		this(null, cond, trueRef, falseRef);
	}
	public TACBranch(TACNode node, TACOperand cond, TACLabelRef trueRef,
			TACLabelRef falseRef)
	{
		super(node);
		if (cond != null)
			condition = check(cond, Type.BOOLEAN);
		trueLabel = trueRef;
		falseLabel = falseRef;
	}

	public boolean isConditional()
	{
		return trueLabel != falseLabel;
	}
	public TACOperand getCondition()
	{
		return condition;
	}
	public TACLabelRef getLabel()
	{
		if (isConditional())
			throw new IllegalStateException();
		return trueLabel;
	}
	public TACLabelRef getTrueLabel()
	{
		return trueLabel;
	}
	public TACLabelRef getFalseLabel()
	{
		return falseLabel;
	}

	@Override
	public int getNumOperands()
	{
		return isConditional() ? 1 : 0;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (num == 0 && isConditional())
			return condition;
		throw new IndexOutOfBoundsException("num");
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	@Override
	public String toString() {
		if (isConditional())
			return "goto " + getCondition() + " ? " + getTrueLabel() + " : " +
				getFalseLabel();
		return "goto " + getLabel();
	}
}
