package shadow.typecheck.type;

import java.io.PrintWriter;



public class ExceptionType extends ClassType
{
	public ExceptionType(String typeName, Modifiers modifiers, ClassInterfaceBaseType outer ) {
		this( typeName, modifiers, outer, Type.EXCEPTION );
	}
	
	public ExceptionType(String typeName, Modifiers modifiers, ClassInterfaceBaseType outer, ClassType extendType ) {
		super( typeName, modifiers, outer );
		setExtendType( extendType );
	}
	
	public boolean isSubtype(Type t)
	{
		if( t == UNKNOWN )
			return false;
	
		if( equals(t) )
			return true;		
		
		if( t instanceof ExceptionType )			
			return isDescendentOf(t);
		else
			return false;
	}
	
	public void printMetaFile(PrintWriter out, String linePrefix )
	{
		printMetaFile(out, linePrefix, "exception");	
	}
}
