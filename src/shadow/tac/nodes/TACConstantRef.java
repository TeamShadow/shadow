package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class TACConstantRef extends TACReference
{
	private Type prefix;
	private ModifiedType type;
	private String name;
	public TACConstantRef(TACNode node, Type prefixType, String constantName)
	{
		super(node);
		ModifiedType constantType = prefixType.getField(constantName);
		if (constantType == null)
			throw new IllegalArgumentException("field does not exist");
		if (!constantType.getModifiers().isConstant())
			throw new IllegalArgumentException("field is not a constant");
		prefix = prefixType;
		type = constantType;
		name = constantName;
	}

	public Type getPrefixType()
	{
		return prefix;
	}
	public String getName()
	{
		return name;
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
		return 0;
	}

	@Override
	public TACOperand getOperand(int num)
	{
		throw new IndexOutOfBoundsException(""+num);
	}

	@Override
	public void accept(TACVisitor visitor) throws ShadowException
	{
		visitor.visit(this);
	}
}
