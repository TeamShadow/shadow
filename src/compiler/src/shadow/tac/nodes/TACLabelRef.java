package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACLabelRef extends TACOperand
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

		public String getName()
		{
			return TACLabelRef.this.getSymbol();
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
			return getName();
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
		return getSymbol();
	}
}
