package shadow.tac.nodes;

import java.util.Iterator;
import java.util.NoSuchElementException;

import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SequenceType;

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

	protected final TACOperand check(TACOperand operand, ModifiedType type)
	{
		if (type instanceof TACReference)
			type = ((TACReference)type).getGetType();
		/*if (type.getType() instanceof TypeParameter &&
				!(operand.getType() instanceof TypeParameter))
			type = new SimpleModifiedType(Type.OBJECT);*/
		operand = operand.checkVirtual(type, this);
		if (operand.getType().equals(type.getType()))
			return operand;
		if (operand.getType() instanceof SequenceType &&
				type.getType() instanceof SequenceType &&
				((SequenceType)operand.getType()).matches(
						((SequenceType)type.getType())))
			return operand;
		if (operand.getType().getPackage().equals(type.getType().
				getPackage()) && operand.getType().getTypeName().equals(type.
				getType().getTypeName()))
			return operand;
		throw new IllegalArgumentException();
	}
}
