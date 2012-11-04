package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ClassInterfaceBaseType;
import shadow.typecheck.type.ClassType;

public class TACNewObject extends TACOperand
{
	private ClassType type;
	public TACNewObject(ClassType objectType)
	{
		this(null, objectType);
	}
	public TACNewObject(TACNode node, ClassType objectType)
	{
		super(node);
		type = objectType;
	}

	@Override
	public ClassInterfaceBaseType getType()
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
}
