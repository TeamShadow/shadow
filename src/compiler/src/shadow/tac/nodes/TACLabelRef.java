package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACLabelRef extends TACOperand implements TACDestination
{
	private TACLabel label;
	public TACLabelRef()
	{
		this(null);
	}
	public TACLabelRef(TACNode node)
	{
		super(node);
		label = null;
	}

	@Override
	public int getNumPossibilities()
	{
		return 1;
	}
	@Override
	public TACLabelRef getPossibility(int num)
	{
		if (num == 0)
			return this;
		throw new IndexOutOfBoundsException("num");
	}

	public TACLabel getLabel()
	{
		return label;
	}
	public class TACLabel extends TACSimpleNode
	{
		public TACLabel()
		{
			this(null);
		}
		public TACLabel(TACNode node)
		{
			super(node);
			if (label != null)
				throw new IllegalStateException("a TACLabel was already " +
						"created for this TACLabelRef");
			label = this;
		}

		public TACLabelRef getRef()
		{
			return TACLabelRef.this;
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

		@Override
		public String toString()
		{
			return TACLabelRef.this.toString();
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

	@Override
	public String toString()
	{
		return getData().toString();
	}
}
