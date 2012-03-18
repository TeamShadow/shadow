package shadow.tac.nodes;

import java.io.IOException;

import shadow.tac.AbstractTACVisitor;
import shadow.typecheck.type.Type;

public class TACGetLength extends TACPrefixed
{
	private int dimension;
	public TACGetLength(int dimensionParameter)
	{
		dimension = dimensionParameter;
	}
	public TACGetLength(TACPrefixed prefixRefNode, int dimensionParameter)
	{
		super(prefixRefNode);
		dimension = dimensionParameter;
	}

	@Override
	public boolean expectsPrefix()
	{
		return true;
	}
	@Override
	public Type expectedPrefixType()
	{
		return null;
	}
	@Override
	public Type getType()
	{
		return Type.INT;
	}
	public int getDimension()
	{
		return dimension;
	}
	
	@Override
	public void accept(AbstractTACVisitor visitor) throws IOException
	{
		visitor.visit(this);
	}
}
