package shadow.tac.nodes;

import shadow.parser.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACConstructGeneric extends TACOperand
{
	TACOperand[] elements;
	Type type;
	
	public TACConstructGeneric(TACNode node, TACOperand[] elements, Type type)
	{
		super(node);
		this.elements = elements;
		this.type = type;
	}
	
	public Type getClassType()
	{
		return type;
	}
	

	@Override
	public Type getType() {		
		return Type.CLASS;
	}

	@Override
	public int getNumOperands() {		
		return elements.length;
	}

	@Override
	public TACOperand getOperand(int num) {
		return elements[num];
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

}
