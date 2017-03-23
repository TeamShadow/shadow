package shadow.typecheck.type;

public interface UninstantiatedType {
	Type instantiate() throws InstantiationException;
	Type partiallyInstantiate() throws InstantiationException;
	SequenceType getTypeArguments();
	Type getType();
}
