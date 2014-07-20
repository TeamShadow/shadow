package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;

public class TACSequenceElement extends TACOperand
{
	private ModifiedType type;
	private TACOperand sequence;
	private int index;		

	public TACSequenceElement(TACNode node, TACOperand sequence, int index)
	{
		super(node);
		SequenceType sequenceType = (SequenceType) sequence.getType();
		type = sequenceType.get(index);
		this.sequence = sequence;
		this.index = index;
	}
	
	public int getIndex()
	{
		return index;
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
	public void setType(Type newType)
	{
		type.setType(newType);
	}

	@Override
	public int getNumOperands()
	{
		return 1;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if( num == 0 )
			return sequence;
		
		throw new IndexOutOfBoundsException("" + num);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}
}
