package shadow.typecheck.type;

import java.util.List;
import java.util.Map;


public class SimpleModifiedType implements ModifiedType {

	Type type;
	int modifiers;
	
	public SimpleModifiedType( Type type, int modifiers )
	{
		this.type = type;
		this.modifiers = modifiers;
	}
	
	@Override
	public Type getType() {
		return type;
	}

	@Override
	public int getModifiers() {
		return modifiers;
	}	

	public SimpleModifiedType replace(List<Type> values, List<ModifiedType> replacements )
	{	
		return new SimpleModifiedType( type.replace(values, replacements), modifiers );		
	}
}
