package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.PropertyType;

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
		prefix = check(propertyPrefix, propertyPrefix);
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
	public ModifiedType getGetType()
	{
		return type.getGetType();
	}
	@Override
	public ModifiedType getSetType()
	{
		return type.getSetType();
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
