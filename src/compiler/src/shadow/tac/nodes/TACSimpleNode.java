package shadow.tac.nodes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;
import shadow.typecheck.type.TypeParameter;

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
		if (type.getType() instanceof TypeParameter &&
				!(operand.getType() instanceof TypeParameter))
			type = new SimpleModifiedType(Type.OBJECT);		
		
		//for splats //no! handled in SequenceAssignment
		/*
		if( type.getType() instanceof SequenceType && !(operand.getType() instanceof SequenceType))
		{
			SequenceType sequenceType = (SequenceType) type.getType(); 
			List<TACOperand> list = new ArrayList<TACOperand>( sequenceType.size() );
			for( int i = 0; i < sequenceType.size(); ++i )
				list.add(new TACNodeRef(operand));
			operand = new TACSequence(list);			
		}
		*/
		
		operand = operand.checkVirtual(type, this); //puts in casts where needed
		if (operand.getType().equals(type.getType()))
			return operand;
			
		
		if (operand.getType() instanceof SequenceType &&
				type.getType() instanceof SequenceType &&
				((SequenceType)operand.getType()).matches( //replace with subtype?
						((SequenceType)type.getType())))
			return operand;		

		if (operand.getType().getPackage().equals(type.getType().
				getPackage()) && operand.getType().getTypeName().equals(type.
				getType().getTypeName()))
			return operand;
		throw new IllegalArgumentException();
	}
}
