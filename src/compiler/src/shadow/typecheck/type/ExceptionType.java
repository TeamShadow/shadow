package shadow.typecheck.type;


public class ExceptionType extends ClassType
{
	public ExceptionType(String typeName, int modifiers, Type outer ) {
		super( typeName, modifiers, outer, Kind.EXCEPTION );
	}

}
