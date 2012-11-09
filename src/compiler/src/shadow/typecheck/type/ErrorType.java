package shadow.typecheck.type;

public class ErrorType extends ClassType
{
	public ErrorType(String typeName, Modifiers modifiers, ClassInterfaceBaseType outer ) {
		this( typeName, modifiers, outer, Type.ERROR );
	}
	
	public ErrorType(String typeName, Modifiers modifiers, ClassInterfaceBaseType outer, ClassType extendType ) {
		super( typeName, modifiers, outer );
		setExtendType( extendType );
	}
	
	public boolean isSubtype(Type t)
	{
		if( equals(t) )
			return true;		
		
		if( t instanceof ErrorType )			
			return isDescendentOf(t);
		else
			return false;
	}	
}
