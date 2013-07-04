package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

public class TACBranch extends TACSimpleNode
{
	private TACLabelRef trueLabel, falseLabel;
	private TACOperand operand;
	public TACBranch(TACDestination dest)
	{
		this(null, dest);
	}
	public TACBranch(TACNode node, TACDestination dest)
	{
		this(node, (TACOperand)dest, null, null);
	}
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
		trueLabel = trueRef;
		falseLabel = falseRef;
		if (isConditional())
			operand = check(cond, new SimpleModifiedType(Type.BOOLEAN));
		else if (isDirect())
			if (cond == null)
				operand = cond;
			else
				throw new IllegalArgumentException("not null");
		else if (isIndirect())
			if (cond != null && cond instanceof TACDestination)
				operand = cond;
			else
				throw new IllegalArgumentException("null");
	}

	public boolean isConditional()
	{
		return trueLabel != falseLabel;
	}
	public boolean isDirect()
	{
		return trueLabel == falseLabel && trueLabel != null;
	}
	public boolean isIndirect()
	{
		return trueLabel == null && falseLabel == null;
	}
	public TACOperand getCondition()
	{
		return operand;
	}
	public TACOperand getOperand()
	{
		return operand;
	}
	public TACDestination getDestination()
	{
		return (TACDestination)operand;
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
		return isConditional() ? 3 : 1;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (num == 0)
			return isDirect() ? getLabel() : getCondition();
		if (!isConditional())
			throw new IndexOutOfBoundsException("num");
		if (num == 1)
			return getTrueLabel();
		if (num == 2)
			return getFalseLabel();
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
		if (isDirect())
			return "goto " + getLabel();
		return "goto " + getDestination();
	}
}
