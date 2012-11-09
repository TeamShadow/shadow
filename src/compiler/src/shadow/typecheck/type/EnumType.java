package shadow.typecheck.type;


public class EnumType extends ClassType
{
	public EnumType(String typeName, Modifiers modifiers, ClassInterfaceBaseType outer ) {
		this( typeName, modifiers, outer, Type.ENUM );
	}
	
	public EnumType(String typeName, Modifiers modifiers, ClassInterfaceBaseType outer, ClassType extendType ) {
		super( typeName, modifiers, outer );
		setExtendType( extendType );
	}
}
