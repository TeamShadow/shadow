package shadow.tac.nodes;

import java.io.IOException;

import shadow.tac.AbstractTACVisitor;
import shadow.typecheck.type.Type;

public class TACAllocation extends TACNode
{
	private boolean onHeap;
	private Type type;
	private TACNode size;
	public TACAllocation(TACVariable var)
	{
		this(var.getType(), null, var.getSymbol(), false);
	}
	public TACAllocation(TACVariable var, boolean heap)
	{
		this(var.getType(), null, var.getSymbol(), heap);
	}
	public TACAllocation(TACVariable var, TACNode sizeNode, String varSymbol)
	{
		this(var.getType(), sizeNode, var.getSymbol(), false);
	}
	public TACAllocation(TACVariable var, TACNode sizeNode, String varSymbol, boolean heap)
	{
		this(var.getType(), sizeNode, var.getSymbol(), heap);
	}
	public TACAllocation(Type varType, String varSymbol)
	{
		this(varType, null, varSymbol, false);
	}
	public TACAllocation(Type varType, String varSymbol, boolean heap)
	{
		this(varType, null, varSymbol, heap);
	}
	public TACAllocation(Type varType, TACNode sizeNode, String varSymbol)
	{
		this(varType, sizeNode, varSymbol, false);
	}
	public TACAllocation(Type varType, TACNode sizeNode, String varSymbol, boolean heap)
	{
		if (sizeNode != null && !sizeNode.getType().equals(Type.INT))
		{
			if (sizeNode.getType().isSubtype(Type.INT));
// FIXME:		sizeNode = new TACCast(Type.INT, sizeNode);
			else
				throw new IllegalArgumentException("Size must be compatible with int.");
		}
		onHeap = heap;
		type = varType;
		size = sizeNode;
		setSymbol(varSymbol);
	}
	
	@Override
	public Type getType()
	{
		return type;
	}
	public boolean isOnHeap()
	{
		return onHeap;
	}
	public boolean isArray()
	{
		return size != null;
	}
	public TACNode getSize()
	{
		return size;
	}
	
	@Override
	public String toString()
	{
		if (isArray())
			return "alloc<" + type + ">(" + size + ')';
		else
			return "" + type + ' ' + getSymbol();
	}
	
	@Override
	public void accept(AbstractTACVisitor visitor) throws IOException
	{
		visitor.visit(this);
	}
}
