package shadow.typecheck.type;

import shadow.typecheck.type.Type.Kind;

public class EnumType extends ClassType
{
	public EnumType(String typeName, int modifiers, Type outer ) {
		super( typeName, modifiers, outer, Kind.ENUM );
	}
}
