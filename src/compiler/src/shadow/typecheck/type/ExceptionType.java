package shadow.typecheck.type;

import shadow.typecheck.type.Type.Kind;

public class ExceptionType extends ClassType
{
	public ExceptionType(String typeName, int modifiers, Type outer ) {
		super( typeName, modifiers, outer, Kind.EXCEPTION );
	}

}
