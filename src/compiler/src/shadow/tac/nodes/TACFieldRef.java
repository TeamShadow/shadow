package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.Type;

public class TACFieldRef extends TACReference
{
	private int index;
	private TACOperand prefix;
	private Type type;
	private String name;
	public TACFieldRef(TACOperand fieldPrefix, Type fieldType, String fieldName)
	{
		this(null, fieldPrefix, fieldType, fieldName);
	}
	public TACFieldRef(TACNode node, TACOperand fieldPrefix, Type fieldType,
			String fieldName)
	{
		super(node);
		if (!(fieldPrefix.getType() instanceof ClassType))
			throw new IllegalArgumentException("fieldPrefix is not a class " +
					"type");
		ClassType prefixType = (ClassType)fieldPrefix.getType();
		while (prefixType != null && !prefixType.containsField(fieldName))
			prefixType = prefixType.getExtendType();
		if (prefixType == null)
			throw new IllegalArgumentException("field fieldName not found");
		index = prefixType.getFieldIndex(fieldName);
		prefix = check(fieldPrefix, prefixType);
		type = fieldType;
		name = fieldName;
	}

	public int getIndex()
	{
		return index;
	}
	public TACOperand getPrefix()
	{
		return prefix;
	}
	public String getReferenceName()
	{
		return name;
	}

	@Override
	public Type getType()
	{
		return type;
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
		return prefix.toString() + '.' + name;
	}
}
