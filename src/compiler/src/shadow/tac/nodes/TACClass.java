package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACClass extends TACOperand
{
	private Type type;
	public TACClass(Type classType)
	{
		this(null, classType);
	}
	public TACClass(TACNode node, Type classType)
	{
		super(node);
		type = classType;
	}

	public Type getClassType()
	{
		return type;
	}

	@Override
	public Type getType()
	{
		return Type.CLASS;
	}
	@Override
	public int getNumOperands()
	{
		return 0;
	}
	@Override
	public TACOperand getOperand(int num)
	{
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
		return type + ".class";
	}
}
