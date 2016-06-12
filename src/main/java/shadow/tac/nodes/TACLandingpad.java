package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.PointerType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

public class TACLandingpad extends TACOperand
{
	private TACBlock block;	
	
	public TACLandingpad(TACNode node, TACBlock blockRef)
	{
		super(node);
		block = blockRef;
		
	}

	public TACBlock getBlock()
	{
		return block;
	}

	@Override
	public SequenceType getType()
	{
		return Type.getExceptionType();
	}

	@Override
	public int getNumOperands()
	{
		return 0;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		throw new IndexOutOfBoundsException("" + num);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}
}
