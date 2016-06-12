package shadow.tac;

import java.io.StringWriter;

import shadow.tac.nodes.TACOperand;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class TACConstant extends TACNodeList implements ModifiedType
{
	private Type prefix;
	private ModifiedType type;
	private String name;
	public TACConstant(Type prefixType, String constantName, TACMethod method)
	{
		ModifiedType constantType = prefixType.getField(constantName);
		if (!constantType.getModifiers().isConstant())
			throw new IllegalArgumentException("constantType is not constant");
		prefix = prefixType;
		type = constantType;
		name = constantName;
		setMethod(method);
	}

	public Type getPrefixType()
	{
		return prefix;
	}
	public String getName()
	{
		return name;
	}
	public TACOperand getValue()
	{
		return (TACOperand)getLast();
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
	public String toString() {
		StringWriter writer = new StringWriter();
		writer.write(getModifiers().toString());
		writer.write(getType().toString(Type.TYPE_PARAMETERS));
		writer.write(' ');
		writer.write(getName());
		writer.write(" = ");
		writer.write(getValue().toString());
		return writer.toString();		
	}
}
