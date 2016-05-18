package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

public class TACBranch extends TACSimpleNode
{
	private TACLabelRef trueLabel, falseLabel;
	private TACDestination destination;
	private TACOperand operand;
	private Kind kind;
	
	public enum Kind { DIRECT, INDIRECT, CONDITIONAL };

	public TACBranch(TACNode node, TACOperand op, TACDestination dest)
	{
		super(node);
		kind = Kind.INDIRECT;
		operand = op;
		destination = dest;
	}
	public TACBranch(TACNode node, TACLabelRef labelRef)
	{
		super(node);
		kind = Kind.DIRECT;
		trueLabel = falseLabel = labelRef;
		labelRef.addIncoming(this);
	}
	public TACBranch(TACNode node, TACOperand cond, TACLabelRef trueRef,
			TACLabelRef falseRef)
	{
		super(node);
		kind = Kind.CONDITIONAL;
		trueLabel = trueRef;
		falseLabel = falseRef;
		operand = check(cond, new SimpleModifiedType(Type.BOOLEAN));
		trueRef.addIncoming(this);
		falseRef.addIncoming(this);		
	}

	public boolean isConditional()
	{
		return kind == Kind.CONDITIONAL;
	}
	public boolean isDirect()
	{
		return kind == Kind.DIRECT;
	}
	public boolean isIndirect()
	{
		return kind == Kind.INDIRECT;
	}
	public TACOperand getCondition()
	{
		if (!isConditional())
			throw new IllegalStateException();
		return operand;
	}
	public TACOperand getOperand()	
	{
		if (!isIndirect())
			throw new IllegalStateException();
		return operand;
	}
	public TACDestination getDestination()
	{
		if (!isIndirect())
			throw new IllegalStateException();
		return destination;
	}
	public TACLabelRef getLabel()
	{
		if (!isDirect())
			throw new IllegalStateException();
		return trueLabel;
	}
	public TACLabelRef getTrueLabel()
	{
		if (!isConditional())
			throw new IllegalStateException();
		return trueLabel;
	}
	public TACLabelRef getFalseLabel()
	{
		if (!isConditional())
			throw new IllegalStateException();
		return falseLabel;
	}

	@Override
	public int getNumOperands()
	{
		return isDirect() ? 0 : 1;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if( num == 0 )
		{
			if( isIndirect() )
				return getOperand();
			else if( isConditional() )
				return getCondition();
		}
		throw new IndexOutOfBoundsException("" + num);
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
