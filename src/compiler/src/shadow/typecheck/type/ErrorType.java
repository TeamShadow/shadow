package shadow.typecheck.type;


public class ErrorType extends ClassType
{
	public ErrorType(String typeName, int modifiers, Type outer ) {
		super( typeName, modifiers, outer, Kind.ERROR );
	}
}
