package shadow.tac.nodes;

import java.util.ArrayList;
import java.util.List;

import shadow.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACCatchSwitch extends TACPad {
	
	private List<TACCatchPad> catchPads = new ArrayList<>();

	public TACCatchSwitch(TACNode node, TACLabel label) {
		super(node, label);
	}
	
	public boolean isCatchAll() {
		return catchPads.size() == 0;
	}
	

	@Override
	public int getNumOperands()
	{
		return catchPads.size();
	}
	@Override
	public TACCatchPad getOperand(int i)
	{	
		if(i >= 0 && i < catchPads.size())
			return catchPads.get(i);
		throw new IndexOutOfBoundsException("" + i);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	@Override
	public Type getType() {
		return null;
	}
	
	public void addCatchPad(TACCatchPad catchPad) {
		catchPads.add(catchPad);
		catchPad.setCatchSwitch(this);
	}
}
