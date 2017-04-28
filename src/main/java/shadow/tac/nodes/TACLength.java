package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACLength extends TACOperand
{
	private TACOperand array;
	private boolean isLong;
	
	public TACLength(TACNode node, TACOperand arrayRef) {
		this( node, arrayRef, false);		
	}
	

	public TACLength(TACNode node, TACOperand arrayRef, boolean isLong) {
		super(node);
		array = check(arrayRef, arrayRef);
		this.isLong = isLong;
	}

	public TACOperand getArray()
	{
		return array;
	}	
	
	@Override
	public Type getType() {
		if( isLong )
			return Type.LONG;
		else					
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
		throw new IndexOutOfBoundsException("" + num);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		if( isLong )
			return array + "->longSize";
		else
			return array + "->size";
	}
}
