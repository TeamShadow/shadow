package shadow.tac.nodes;

import java.io.IOException;
import java.util.List;

import shadow.tac.AbstractTACVisitor;
import shadow.typecheck.type.Type;

public class TACAllocation extends TACNode
{
	private boolean onHeap;
	private Type type;
	private int sizesIndex;
	private List<TACNode> sizes;
	private TACNode size;
	public TACAllocation(Type varType, TACNode sizeNode, List<TACNode> sizeNodes, int sizesStart)
	{
		onHeap = true;
		type = varType;
		sizesIndex = sizesStart;
		sizes = sizeNodes;
		size = sizeNode;
	}
	
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
	public TACNode getSizes(int index)
	{
		return sizes.get(sizesIndex + index);
	}
	
	@Override
	public String toString()
	{
		if (isArray())
			return "alloc<" + type + '>' + size;
		else
			return type.toString() + ' ' + getSymbol();
	}
	
	@Override
	public void accept(AbstractTACVisitor visitor) throws IOException
	{
		visitor.visit(this);
	}
}
