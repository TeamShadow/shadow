package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACLength extends TACOperand
{
	private TACOperand array;
	private int dimension;
	public TACLength(TACOperand arrayRef, int dim)
	{
		this(null, arrayRef, dim);
	}
	public TACLength(TACNode node, TACOperand arrayRef, int dim)
	{
		super(node);
		array = check(arrayRef, arrayRef);
		dimension = dim;
	}

	public TACOperand getArray()
	{
		return array;
	}
	public int getDimension()
	{
		return dimension;
	}
	
	@Override
	public Type getType()
	{
		return Type.INT;
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
			return array;
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
		return array + "->lengths[" + dimension + ']';
	}
}
