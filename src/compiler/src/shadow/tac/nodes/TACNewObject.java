package shadow.tac.nodes;

import java.util.Collection;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACMethod;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ClassInterfaceBaseType;

public class TACNewObject extends TACCall
{
	public TACNewObject(TACMethod method, Collection<TACOperand> params)
	{
		this(null, method, params);
	}
	public TACNewObject(TACNode node, TACMethod method,
			Collection<TACOperand> params)
	{
		super(node, method, params);
	}

	@Override
	public ClassInterfaceBaseType getType()
	{
		return getMethod().getPrefixType();
	}
	@Override
	public int getNumOperands()
	{
		return 1 + super.getNumOperands();
	}
	@Override
	public TACOperand getOperand(int num)
	{
		return super.getOperand(num);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}
}
