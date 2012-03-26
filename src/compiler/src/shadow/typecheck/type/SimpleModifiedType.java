package shadow.typecheck.type;

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

}
