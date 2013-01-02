package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.PropertyType;
import shadow.typecheck.type.Type;

public class TACPropertyRef extends TACReference
{
	private TACBlock blockRef;
	private TACOperand prefix;
	private PropertyType type;
	private String name;
	public TACPropertyRef(TACBlock block, TACOperand propertyPrefix,
			PropertyType propertyType, String propertyName)
	{
		this(null, block, propertyPrefix, propertyType, propertyName);
	}
	public TACPropertyRef(TACNode node, TACBlock block,
			TACOperand propertyPrefix, PropertyType propertyType,
			String propertyName)
	{
		super(node);
		if (!(propertyPrefix.getType() instanceof ClassType))
			throw new IllegalArgumentException("propertyPrefix is not a " +
					"class type");
		blockRef = block;
		prefix = check(propertyPrefix, propertyPrefix.getType());
		type = propertyType;
		name = propertyName;
	}

	public TACBlock getBlock()
	{
		return blockRef;
	}
	public TACOperand getPrefix()
	{
		return prefix;
	}
	public String getName()
	{
		return name;
	}

	@Override
	public PropertyType getType()
	{
		return type;
	}
	@Override
	public Type getGetType()
	{
		return type.getGetType().getType();
	}
	@Override
	public Type getSetType()
	{
		return type.getSetType().getType();
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
			return prefix;
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
		return prefix.toString() + "->" + name;
	}
}
