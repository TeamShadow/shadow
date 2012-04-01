package shadow.typecheck.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shadow.parser.javacc.Node;

public class ViewType extends ClassInterfaceBaseType {

	public ViewType(String typeName) {
		super(typeName);
	}

	public ViewType(String typeName, int modifiers) {
		super(typeName, modifiers);
	}

	@Override
	public boolean isSubtype(Type t)
	{
		return equals(t);
	}

	@Override
	public ViewType replace(List<TypeParameter> values,	List<ModifiedType> replacements)
	{		
		return this;
	}
	
	@Override
	public String toString()
	{		
		return "#" + super.toString();
	}
}
