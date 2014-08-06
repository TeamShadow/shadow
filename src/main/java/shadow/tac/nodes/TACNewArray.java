package shadow.tac.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import shadow.parser.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

/** 
 * TAC representation of array allocation
 * Example: int:create[5]
 * @author Jacob Young
 */

public class TACNewArray extends TACOperand
{
	private ArrayType type;
	private TACOperand base;
	private List<TACOperand> dimensions;
	private TACOperand total;

	public TACNewArray(TACNode node, ArrayType arrayType, TACOperand baseClass,
			TACOperand... dims)
	{
		this(node, arrayType, baseClass, Arrays.asList(dims));
	}

	public TACNewArray(TACNode node, ArrayType arrayType, TACOperand baseClass,
			Collection<TACOperand> dims)
	{
		super(node);
		type = arrayType;
		base = check(baseClass, new SimpleModifiedType(Type.CLASS));
		dimensions = new ArrayList<TACOperand>(dims.size());
		Iterator<TACOperand> iter = dims.iterator();
		TACOperand current = check(iter.next(),
				new SimpleModifiedType(Type.INT));
		dimensions.add(current);
		while (iter.hasNext())
		{
			TACOperand next = check(iter.next(),
					new SimpleModifiedType(Type.INT));
			dimensions.add(next);
			current = new TACBinary(this, current, Type.INT.getMatchingMethod("multiply", new SequenceType(Type.INT)), '*', next);
		}
		total = check(current, new SimpleModifiedType(Type.INT));
	}

	public TACOperand getBaseClass()
	{
		return base;
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
		StringBuilder sb = new StringBuilder(getType().
				getSuperBaseType().toString()).append(":create");
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
