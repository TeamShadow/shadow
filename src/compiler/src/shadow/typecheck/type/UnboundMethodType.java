package shadow.typecheck.type;

import java.util.List;

public class UnboundMethodType extends Type
{
	public UnboundMethodType(String typeName, ClassInterfaceBaseType outer )
	{
		this(typeName, outer, 0);		
	}
	
	public UnboundMethodType(String typeName, ClassInterfaceBaseType outer, int modifiers)
	{
		super( typeName, modifiers, outer);
	}
	
	public boolean isSubtype(Type t)
	{
		return equals(t) || t == Type.OBJECT;
	}

	@Override
	public UnboundMethodType replace(List<TypeParameter> values,
			List<ModifiedType> replacements) {		
		return this;
	}
}
