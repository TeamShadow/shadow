package shadow.typecheck.type;

import java.util.List;


public class UnboundMethodType extends ClassType
{
	public UnboundMethodType(String typeName, Type outer )
	{
		this(typeName, outer, new Modifiers());		
	}
	
	public UnboundMethodType(String typeName, Type outer, Modifiers modifiers)
	{
		super( typeName, modifiers, outer);
		setExtendType(Type.UNBOUND_METHOD); // added
	}
	
	public boolean isSubtype(Type t)
	{
		return equals(t) || t == Type.OBJECT;
	}

	@Override
	public UnboundMethodType replace(List<ModifiedType> values,
			List<ModifiedType> replacements) {		
		return this;
	}
	
	@Override
	public UnboundMethodType partiallyReplace(List<ModifiedType> values,
			List<ModifiedType> replacements) {		
		return this;
	}
}
