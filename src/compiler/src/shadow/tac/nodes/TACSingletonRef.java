package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.SingletonType;

public class TACSingletonRef extends TACReference
{
	private SingletonType type;
	public TACSingletonRef(SingletonType instanceType)
	{
		this(null, instanceType);
	}
	public TACSingletonRef(TACNode node, SingletonType instanceType)
	{
		super(node);
		type = instanceType;
	}

	@Override
	public SingletonType getType()
	{
		return type;
	}
	@Override
	public int getNumOperands()
	{
		return 0;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		throw new IndexOutOfBoundsException();
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return type + ":instance";
	}
}
