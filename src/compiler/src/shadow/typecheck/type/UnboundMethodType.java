package shadow.typecheck.type;

import java.util.List;

public class UnboundMethodType extends Type
{
	public UnboundMethodType(String typeName, ClassInterfaceBaseType outer )
	{
		this(typeName, outer, new Modifiers());		
	}
	
	public UnboundMethodType(String typeName, ClassInterfaceBaseType outer, Modifiers modifiers)
	{
		super( typeName, modifiers, outer);
	}
	
	public boolean isSubtype(Type t)
	{
		return equals(t) || t == Type.OBJECT;
	}

	@Override
	public UnboundMethodType replace(SequenceType values,
			SequenceType replacements) {		
		return this;
	}
}
