package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

/**
 * TAC representation of a branch, used to implement if statements,
 * switch statements, breaks, continues, and most other control flow.
 * A TACBranch can be direct, meaning that it branches to one location,
 * conditional, meaning that it branches to one of two locations depending
 * on a boolean value, or indirect, meaning that it branches to a label
 * stored in a variable.  Indirect branches only arise in the context of
 * a finally block, which may have many entry points and may need to branch
 * back to an appropriate location depending on which entry point.  
 * @author Jacob Young
 * @author Barry Wittman
 */
public class TACBranch extends TACNode
{
	private TACLabel trueLabel, falseLabel;
	private TACPhiRef destination;
	private TACOperand operand;
	private Kind kind;
	
	public enum Kind { DIRECT, INDIRECT, CONDITIONAL };

	public TACBranch(TACNode node, TACOperand op, TACPhiRef dest)
	{
		super(node);
		kind = Kind.INDIRECT;
		operand = op;
		destination = dest;
	}
	public TACBranch(TACNode node, TACLabel label)
	{
		super(node);
		kind = Kind.DIRECT;
		trueLabel = falseLabel = label;		
	}
	public TACBranch(TACNode node, TACOperand cond, TACLabel trueRef,
			TACLabel falseRef)
	{
		super(node);
		kind = Kind.CONDITIONAL;
		trueLabel = trueRef;
		falseLabel = falseRef;
		operand = check(cond, new SimpleModifiedType(Type.BOOLEAN));	
	}
	
	public void convertToDirect( TACLabel label )
	{
		kind = Kind.DIRECT;
		destination = null;
		trueLabel = falseLabel = label;
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
	public TACPhiRef getDestination()
	{
		if (!isIndirect())
			throw new IllegalStateException();
		return destination;
	}
	public TACLabel getLabel()
	{
		if (!isDirect())
			throw new IllegalStateException();
		return trueLabel;
	}
	public TACLabel getTrueLabel()
	{
		if (!isConditional())
			throw new IllegalStateException();
		return trueLabel;
	}
	public TACLabel getFalseLabel()
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
