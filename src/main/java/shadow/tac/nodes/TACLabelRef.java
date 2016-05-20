package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACLabelRef
{
	private TACLabel label;
	private String name;	
	
	public TACLabelRef()
	{
		label = null;
		name = null;
	}	
	
	public String getName()
	{
		return name;
	}

	public TACLabel getLabel()
	{
		return label;
	}
	public class TACLabel extends TACOperand
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
			throw new IndexOutOfBoundsException("" + num);
		}
		
		@Override
		public void setData(Object data) {
			super.setData(data);
			//clear out label ref during cleanup 
			if( data == null )
				name = null;			
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
		@Override
		public Type getType() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	@Override
	public String toString()
	{
		if( name == null )
			return "label unknown";
		return name;
	}

	public void setName(String value) {
		name = value;
	}
}
