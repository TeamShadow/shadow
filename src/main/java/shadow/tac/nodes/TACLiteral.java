package shadow.tac.nodes;

import java.math.BigInteger;

import shadow.interpreter.ShadowBoolean;
import shadow.interpreter.ShadowCode;
import shadow.interpreter.ShadowDouble;
import shadow.interpreter.ShadowFloat;
import shadow.interpreter.ShadowInteger;
import shadow.interpreter.ShadowString;
import shadow.interpreter.ShadowValue;
import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class TACLiteral extends TACOperand
{
	private ShadowValue value;	

	//negated lets us know that this literal is negated
	//allows us to be more precise about allowing -128y but not 128y
	public TACLiteral(TACNode node, ShadowValue value)
	{
		super(node);
		this.value = value;		
	}

	public ShadowValue getValue()
	{
		return value;
	}
	@Override
	public Modifiers getModifiers()
	{
		return getValue().getModifiers();
	}
	@Override
	public Type getType()
	{
		return getValue().getType();
	}
	@Override
	public void setType(Type type)
	{
		getValue().setType(type);
	}
	@Override
	public int getNumOperands()
	{
		return 0;
	}
	@Override
	public TACOperand getOperand(int num)
	{
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
		return value.toString();
	}
}
