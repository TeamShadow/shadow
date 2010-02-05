package shadow.typecheck.type;

import shadow.typecheck.type.Type.Kind;

public class ErrorType extends ClassType
{
	public ErrorType(String typeName, int modifiers, Type outer ) {
		super( typeName, modifiers, outer, Kind.ERROR );
	}
}
