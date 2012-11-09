package shadow.typecheck.type;

public interface ModifiedType
{
	public Type getType();
	public Modifiers getModifiers();
	//public ModifiedType replace(SequenceType values, SequenceType replacements );
}
