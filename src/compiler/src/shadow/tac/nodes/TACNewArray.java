package shadow.tac.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.Type;

public class TACNewArray extends TACOperand
{
	private ArrayType type;
	private List<TACOperand> dimensions;
	private TACOperand total;
	public TACNewArray(ArrayType newType, TACOperand... dims)
	{
		this(null, newType, Arrays.asList(dims));
	}
	public TACNewArray(TACNode node, ArrayType newType, TACOperand... dims)
	{
		this(node, newType, Arrays.asList(dims));
	}
	public TACNewArray(ArrayType newType, Collection<TACOperand> dims)
	{
		this(null, newType, dims);
	}
	public TACNewArray(TACNode node, ArrayType newType,
			Collection<TACOperand> dims)
	{
		super(node);
		type = newType;
		dimensions = new ArrayList<TACOperand>(dims.size());
		Iterator<TACOperand> iter = dims.iterator();
		TACOperand current = check(iter.next(), Type.LONG);
		dimensions.add(current);
		while (iter.hasNext())
		{
			TACOperand next = check(iter.next(), Type.LONG);
			dimensions.add(next);
			current = new TACBinary(this, current, '*', next);
		}
		total = check(current, Type.LONG);
	}

	public int getDimensions()
	{
		return dimensions.size();
	}
	public TACOperand getDimension(int index)
	{
		return dimensions.get(index);
	}
	public Iterator<TACOperand> dimensionIterator()
	{
		return dimensions.iterator();
	}
	public TACOperand getTotalSize()
	{
		return total;
	}

	public Type getBaseType()
	{
		return getType().getBaseType();
	}
	@Override
	public ArrayType getType()
	{
		return type;
	}
	@Override
	public int getNumOperands()
	{
		return getDimensions() + 1;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (num == getDimensions())
			return getTotalSize();
		return getDimension(num);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("new ").append(getType().
				getSuperBaseType());
		Iterator<TACOperand> dims = dimensionIterator();
		Type current = getType();
		while (current instanceof ArrayType)
		{
			ArrayType arrayType = (ArrayType)current;
			sb.append('[');
			for (int i = 0; i < arrayType.getDimensions(); i++)
			{
				if (dims.hasNext())
				{
					if (i != 0)
						sb.append(", ");
					sb.append(dims.next());
				}
				else if (i != 0)
					sb.append(',');
			}
			sb.append(']');
			current = arrayType.getBaseType();
		}
		return sb.toString();
	}
}
