package shadow.tac.nodes;

import java.util.ArrayList;
import java.util.List;

import shadow.ShadowException;
import shadow.tac.TACMethod;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;

public class TACLandingPad extends TACOperand {
	
	private List<TACCatch> catches = new ArrayList<>(); 
	private TACLabel body;
	
	public TACLandingPad(TACMethod method) {
		super(null);
		body = new TACLabel(method);
	}
	
	public TACLabel getBody() {
		return body;
	}
	
	public void addCatch(TACCatch catch_) {
		catches.add(catch_);
	}
	
	public List<TACCatch> getCatches() {
		return catches;
	}

	@Override
	public SequenceType getType() {
		return Type.getExceptionType();
	}

	@Override
	public int getNumOperands() {
		return 0;
	}
	
	@Override
	public TACOperand getOperand(int num){
		throw new IndexOutOfBoundsException("" + num);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException {
		visitor.visit(this);
	}
}