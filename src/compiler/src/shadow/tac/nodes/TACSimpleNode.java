package shadow.tac.nodes;

import java.util.Iterator;
import java.util.NoSuchElementException;

import shadow.typecheck.type.Type;

public abstract class TACSimpleNode extends TACNode
		implements Iterable<TACOperand>
{
	protected TACSimpleNode()
	{
		this(null);
	}
	protected TACSimpleNode(TACNode node)
	{
		super(node);
	}

	@Override
	public final Iterator<TACOperand> iterator()
	{
		return new OperandIterator();
	}
	private final class OperandIterator implements Iterator<TACOperand>
	{
		private int index = 0;

		@Override
		public boolean hasNext()
		{
			return index != getNumOperands();
		}

		@Override
		public TACOperand next()
		{
			if (index == getNumOperands())
				throw new NoSuchElementException();
			return getOperand(index++);
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}

	public abstract int getNumOperands();
	public abstract TACOperand getOperand(int num);

	protected final TACOperand check(TACOperand operand, Type type)
	{
		operand = operand.checkVirtual(type, this);
		if (operand.getType().equals(type))
			return operand;
		throw new IllegalArgumentException();
	}
}
