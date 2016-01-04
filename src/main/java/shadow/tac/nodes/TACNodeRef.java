package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class TACNodeRef extends TACOperand
{
	private TACOperand reference;
	private ModifiedType type;

	public TACNodeRef(TACNode node, TACOperand op)
	{
		this(node, op, op);		
	}
	
	public TACNodeRef(TACNode node, TACOperand op, ModifiedType type)
	{
		super(node);
		reference = op;
		this.type = type;
	}

	public TACOperand getReference()
	{
		return reference;
	}
	
	@Override
	public Object getData()
	{	
		if( reference != null )
			return reference.getData();
		
		return null;
	}
	

	@Override
	public Modifiers getModifiers()
	{
		return type.getModifiers();
	}
	@Override
	public Type getType()
	{
		return type.getType();
	}
	@Override
	public void setType(Type newType)
	{
		type.setType(newType);
	}
	@Override
	public int getNumOperands()
	{
		return 1;
	}
	@Override
	public TACOperand getOperand(int num)
	{
		if (num == 0)
			return reference;
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
		return reference.toString();
	}

	@Override
	protected TACOperand checkVirtual(ModifiedType type, TACNode node)
	{
		return reference.checkVirtual(type, node);
	}
}
