package shadow.tac.nodes;

import java.util.ArrayList;
import java.util.List;

import shadow.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;

public class TACLandingPad extends TACOperand {
	
	private List<TACCatch> catches = new ArrayList<>(); 

	
	public TACLandingPad(TACNode node) {
		super(node);
	}
	
	public void addCatch(TACCatch catch_) {
		catches.add(catch_);
		catch_.setLandingPad(this);
	}
	
	public List<TACCatch> getCatches() {
		return catches;
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
	public void accept(TACVisitor visitor) throws ShadowException {
		visitor.visit(this);
	}
}