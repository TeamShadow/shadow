package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

public class TACCast extends TACOperand
{
	private ModifiedType type;
	private TACOperand operand;
	public TACCast(ModifiedType newType, TACOperand op)
	{
		this(null, newType, op);
	}
	public TACCast(TACNode node, ModifiedType newType, TACOperand op)
	{
		super(node);
		if (newType.getType() == Type.NULL)
			newType = new SimpleModifiedType(Type.OBJECT,
					new Modifiers(Modifiers.NULLABLE));
		type = newType;
		operand = check(op, op);
	}

	public TACOperand getOperand()
	{
		return operand;
	}

	@Override
	public Modifiers getModifiers()
	{
		return type.getModifiers();
	}
	@Override
	public Type getType()
	{
		return type.getType();
	}
	@Override
	public int getNumOperands()
	{
		return 1;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (num == 0)
			return operand;
		throw new IndexOutOfBoundsException("num");
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return "cast<" + getModifiers() + getType() + '>' + getOperand();
	}
}
