package shadow.tac.nodes;

import shadow.parser.javacc.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class TACFieldRef extends TACReference
{
	private int index;
	private TACOperand prefix;
	private ModifiedType type;
	private String name;
	public TACFieldRef(TACOperand fieldPrefix, String fieldName)
	{
		this(null, fieldPrefix, fieldName);
	}
	public TACFieldRef(TACNode node, TACOperand fieldPrefix, String fieldName)
	{
		this(node, fieldPrefix, fieldPrefix.getType().getField(fieldName),
				fieldName);
	}
	public TACFieldRef(TACNode node, TACOperand fieldPrefix,
			ModifiedType fieldType, String fieldName)
	{
		super(node);
		if (fieldType == null)
			throw new NullPointerException();
		ClassType prefixType = (ClassType)fieldPrefix.getType();
//		while (prefixType != null && !prefixType.containsField(fieldName))
//			prefixType = prefixType.getExtendType();
//		if (prefixType == null)
//			throw new IllegalArgumentException("field fieldName not found");
		index = prefixType.getFieldIndex(fieldName);
		prefix = check(fieldPrefix, fieldPrefix);
		type = fieldType;
		name = fieldName;
	}

	public int getIndex()
	{
		return index;
	}
	public Type getPrefixType()
	{
		return prefix.getType();
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
		return prefix.toString() + ':' + name;
	}
}
