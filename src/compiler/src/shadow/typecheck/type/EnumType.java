package shadow.typecheck.type;


public class EnumType extends ClassType
{
	public EnumType(String typeName, int modifiers, ClassInterfaceBaseType outer ) {
		this( typeName, modifiers, outer, Type.ENUM );
	}
	
	public EnumType(String typeName, int modifiers, ClassInterfaceBaseType outer, ClassType extendType ) {
		super( typeName, modifiers, outer, Kind.ENUM );
		setExtendType( extendType );
	}
}
