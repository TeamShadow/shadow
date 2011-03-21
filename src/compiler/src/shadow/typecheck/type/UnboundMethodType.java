package shadow.typecheck.type;

public class UnboundMethodType extends Type
{
	//ClassInterfaceBaseType outer;

	public UnboundMethodType(String typeName, ClassInterfaceBaseType outer )
	{
		this(typeName, outer, 0);
		
	}
	
	public UnboundMethodType(String typeName, ClassInterfaceBaseType outer, int modifiers) {
		super( typeName, modifiers, outer, Kind.UNBOUND_METHOD );
	}

}
