package shadow.tac.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACPhiRef
{
	private TACPhi phi;
	private List<TACLabel> labels;
	private Map<TACLabel, TACLabel> values;

	public TACPhiRef()
	{
		phi = null;
		labels = new ArrayList<TACLabel>();
		values = new HashMap<TACLabel, TACLabel>();
	}

	public void addEdge(TACLabel value, TACLabel label)
	{
		if (value == null || label == null)
			throw new IllegalArgumentException("null");
		labels.add(label);
		values.put(label, value);
	}
	
	public void removeLabel(TACLabel label)
	{
		labels.remove(label);
		values.remove(label);
	}
	
	public int getSize()
	{
		return labels.size();
	}
	public TACLabel getValue(TACLabel label)
	{
		return values.get(label);
	}
	public TACLabel getLabel(int num)
	{
		return labels.get(num);
	}
	public TACLabel getValue(int num)
	{
		return getValue(getLabel(num));
	}
	public Collection<TACLabel> getLabels()
	{
		return labels;
	}
	public Collection<TACLabel> getValues()
	{
		return values.values();
	}

	public TACPhi getPhi()
	{
		return phi;
	}
	public class TACPhi extends TACOperand
	{
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
			throw new IndexOutOfBoundsException("" + num);
		}

		@Override
		public void accept(TACVisitor visitor) throws ShadowException
		{
			visitor.visit(this);
		}
		@Override
		public Type getType() {
			return null;
		}
	}
}
