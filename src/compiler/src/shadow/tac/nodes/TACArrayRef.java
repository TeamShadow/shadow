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

public class TACArrayRef extends TACReference
{
	private TACOperand array, total;
	private List<TACOperand> indicies;
	public TACArrayRef(Type type, TACOperand reference, TACOperand... ops)
	{
		this(null, type, reference, Arrays.asList(ops));
	}
	public TACArrayRef(TACNode node, Type type, TACOperand reference,
			TACOperand... ops)
	{
		this(node, type, reference, Arrays.asList(ops));
	}
	public TACArrayRef(Type type, TACOperand reference, Collection<TACOperand> ops)
	{
		this(null, type, reference, ops);
	}
	public TACArrayRef(TACNode node, Type type, TACOperand reference,
			Collection<TACOperand> ops)
	{
		super(node);
		array = check(reference, type);
		Iterator<TACOperand> iter = ops.iterator();
		indicies = new ArrayList<TACOperand>(ops.size());
		TACOperand current = check(iter.next(), Type.INT);
		indicies.add(current);
		while (iter.hasNext())
		{
			current = new TACBinary(this, current, '*', new TACLength(this,
					array, indicies.size()));
			TACOperand next = check(iter.next(), Type.INT);
			indicies.add(next);
			current = new TACBinary(this, current, '+', next);
		}
		total = current;
	}

	public TACOperand getArray()
	{
		return array;
	}
	public int getNumIndicies()
	{
		return indicies.size();
	}
	public TACOperand getIndex(int index)
	{
		return indicies.get(index);
	}
	public TACOperand getTotal()
	{
		return total;
	}

	@Override
	public Type getType()
	{
		return ((ArrayType)array.getType()).getBaseType();
	}
	@Override
	public int getNumOperands()
	{
		return 2 + indicies.size();
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (num == 0)
			return array;
		if (num == 1)
			return total;
		return indicies.get(num - 2);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder(array.toString()).append('[');
		for (TACOperand node : indicies)
			sb.append(node).append(", ");
		return sb.delete(sb.length() - 2, sb.length()).append(']').toString();
	}
}
