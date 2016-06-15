package shadow.tac;

import java.io.StringWriter;

import shadow.tac.nodes.TACNode;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class TACConstant implements ModifiedType
{
	private Type prefix;
	private ModifiedType type;
	private String name;
	private TACNode node;
	
	public TACConstant(Type prefixType, String constantName)
	{
		ModifiedType constantType = prefixType.getField(constantName);
		if (!constantType.getModifiers().isConstant())
			throw new IllegalArgumentException(constantType + "is not constant");
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
	public TACNode getNode()
	{
		return node;
	}
	
	public void setNode(TACNode node)
	{
		this.node = node;
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
		StringWriter writer = new StringWriter();
		writer.write(getModifiers().toString());
		writer.write(getType().toString(Type.TYPE_PARAMETERS));
		writer.write(' ');
		writer.write(getName());
		writer.write(" = ");
		writer.write(getNode().toString());
		return writer.toString();		
	}

}
