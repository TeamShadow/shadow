package shadow.typecheck.type;

public interface ModifiedType
{
	Type getType();
	void setType(Type type);
	Modifiers getModifiers();
}
