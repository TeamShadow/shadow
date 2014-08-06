package shadow.tac.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shadow.parser.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACPhiRef extends TACOperand
{
	private TACPhi phi;
	private List<TACLabelRef> labels;
	private Map<TACLabelRef, TACOperand> values;

	public TACPhiRef(TACNode node)
	{
		super(node);
		phi = null;
		labels = new ArrayList<TACLabelRef>();
		values = new HashMap<TACLabelRef, TACOperand>();
	}

	public void addEdge(TACOperand value, TACLabelRef label)
	{
		if (value == null || label == null)
			throw new IllegalArgumentException("null");
		labels.add(label);
		values.put(label, value);
	}
	public int getSize()
	{
		return labels.size();
	}
	public TACOperand getValue(TACLabelRef label)
	{
		return values.get(label);
	}
	public TACLabelRef getLabel(int num)
	{
		return labels.get(num);
	}
	public TACOperand getValue(int num)
	{
		return getValue(getLabel(num));
	}
	public Collection<TACLabelRef> getLabels()
	{
		return labels;
	}
	public Collection<TACOperand> getValues()
	{
		return values.values();
	}

	public TACPhi getPhi()
	{
		return phi;
	}
	public class TACPhi extends TACSimpleNode
	{
		public TACPhi()
		{
			this(null);
		}
		public TACPhi(TACNode node)
		{
			super(node);
		}

		public TACPhiRef getRef()
		{
			return TACPhiRef.this;
		}

		@Override
		public int getNumOperands()
		{
			return 0;
		}
		@Override
		public TACOperand getOperand(int num)
		{
			throw new IndexOutOfBoundsException("num");
		}

		@Override
		public void accept(TACVisitor visitor) throws ShadowException
		{
			visitor.visit(this);
		}
	}

	@Override
	public Type getType()
	{
		return null;
	}
	@Override
	public int getNumOperands()
	{
		return getSize() * 2;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (num < getSize())
			return getValue(num);
		if ((num -= getSize()) < getSize())
			return getLabel(num);
		throw new IndexOutOfBoundsException("num");
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}
}
