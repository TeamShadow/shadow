package shadow.tac.nodes;

import java.io.IOException;
import java.util.Collections;

import shadow.tac.AbstractTACVisitor;
import shadow.typecheck.type.Type;

public class TACIndexed extends TACPrefixed
{
	private Type type;
	private TACNode index;
	private TACPrefixed[] dependencies;
	public TACIndexed(Type elementType, TACNode indexNode, TACPrefixed[] depends)
	{
		type = elementType;
		index = indexNode;
		dependencies = depends;
	}
	public TACIndexed(Type elementType, TACNode prefixNode, TACNode indexNode, TACPrefixed[] depends)
	{
		super(prefixNode);
		type = elementType;
		index = new TACSequence(Collections.singletonList(indexNode));
		dependencies = depends;
	}
	
	@Override
	public void setPrefix(TACNode prefixNode)
	{
		super.setPrefix(prefixNode);
		for (TACPrefixed prefixed : dependencies)
			prefixed.setPrefix(prefixNode);
	}
	@Override
	public boolean expectsPrefix()
	{
		return true;
	}
	public Type getType()
	{
		return type;
	}
	public TACNode getIndex()
	{
		return index;
	}
	
	@Override
	public String toString()
	{
		return '[' + index.toString() + ']';
	}
	
	public void accept(AbstractTACVisitor visitor) throws IOException
	{
		visitor.visit(this);
	}
}
