package shadow.typecheck.type;

public interface UninstantiatedType
{
	Type instantiate() throws InstantiationException;
}
