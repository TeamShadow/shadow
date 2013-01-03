package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ClassType;

public class TACInit extends TACSimpleNode
{
	private ClassType type;
	public TACInit(ClassType classType)
	{
		this(null, classType);
	}
	public TACInit(TACNode node, ClassType thisType)
	{
		super(node);
		type = thisType;
	}

	public ClassType getThisType()
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
