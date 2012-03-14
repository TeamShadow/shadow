package shadow.tac.nodes;

import java.math.BigInteger;

import shadow.output.AbstractTACVisitor;
import shadow.typecheck.type.Type;

public class TACLiteral extends TACNode
{
	private Type type;
	private Object value;
	public TACLiteral(Type literalType, String str)
	{
		type = literalType;
		setSymbol(str);
		if (str.equals("null"))
			value = null;
		else if (literalType.equals(Type.CODE))
			value = str;
		else if (literalType.equals(Type.STRING))
			value = str;
		else if (literalType.equals(Type.BOOLEAN))
			value = Boolean.valueOf(str);
		else if (literalType.equals(Type.FLOAT))
			value = Float.valueOf(str.replaceFirst("(?i)f$", ""));
		else if (literalType.equals(Type.DOUBLE))
			value = Double.valueOf(str.replaceFirst("(?i)d$", ""));
		else
		{
			str = str.replaceFirst("(?i)u$|u?[ysil]$", "");
			int base = 10;
			if (str.startsWith("0x"))
			{
				base = 16;
				str = str.substring(2);
			}
			else if (str.length() > 2 && str.charAt(0) == '0')
			{
				base = 7;
				str = str.substring(1);
			}
			value = new BigInteger(str, base);
		}
	}
	
	@Override
	public Type getType() {
		return type;
	}
	public Object getValue()
	{
		return value;
	}
	
	@Override
	public String toString()
	{
		return String.valueOf(value);
	}

	@Override
	public void accept(AbstractTACVisitor visitor)
	{
		visitor.visit(this);
	}
}
