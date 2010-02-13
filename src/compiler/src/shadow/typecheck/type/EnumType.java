package shadow.typecheck.type;


public class EnumType extends ClassType
{
	public EnumType(String typeName, int modifiers, Type outer ) {
		super( typeName, modifiers, outer, Kind.ENUM );
	}
}
