package shadow.tac.nodes;

import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class TACFieldRef implements TACReference
{
	private int index;
	private TACOperand prefix;
	private ModifiedType type;
	private String name;

	public TACFieldRef(TACOperand fieldPrefix, String fieldName)
	{
		this(fieldPrefix, fieldPrefix.getType().getField(fieldName),
				fieldName);
	}
	public TACFieldRef(TACOperand fieldPrefix,
			ModifiedType fieldType, String fieldName)
	{

		if (fieldType == null)
			throw new NullPointerException();		
		if( fieldName.equals("class"))
			index = 0;
		else if( fieldName.equals("_methods"))
			index = 1;
		else
		{
			//0 is class
			//1 is methods
			ClassType prefixType = (ClassType)fieldPrefix.getType();
			
			// _outer is a member for inner classes, but it is made available through normal field lookup
			index = prefixType.getFieldIndex(fieldName) + 2;
		}
		prefix = fieldPrefix;//check(fieldPrefix, fieldPrefix);
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
	public String toString()
	{
		return prefix.toString() + ':' + name;
	}
}
