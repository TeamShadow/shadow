package shadow.tac.nodes;

import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.Type;

public class TACSingletonRef extends TACReference
{
	private SingletonType type;

	public TACSingletonRef(SingletonType instanceType)
	{
		//super(node);
		type = instanceType;
	}

	@Override
	public SingletonType getType()
	{
		return type;
	}
	/*
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
	*/

	@Override
	public String toString()
	{
		return type + ":instance";
	}

	@Override
	public void setType(Type type) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public Modifiers getModifiers() {
		return new Modifiers();
	}
}
