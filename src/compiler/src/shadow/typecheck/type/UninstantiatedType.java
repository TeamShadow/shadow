package shadow.typecheck.type;

public interface UninstantiatedType
{
	ClassInterfaceBaseType instantiate() throws InstantiationException;
}
