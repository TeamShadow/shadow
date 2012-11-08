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
	
	@Override
	public boolean equals(Object o) {
		if( o instanceof ModifiedType  )
		{
			ModifiedType modifiedType = (ModifiedType) o;
			return type.equals(modifiedType.getType()) && modifiers == modifiedType.getModifiers();				
		}
		
		return false;
	}
	

	public SimpleModifiedType replace(SequenceType values, SequenceType replacements )
	{	
		return new SimpleModifiedType( type.replace(values, replacements), modifiers );		
	}
}
