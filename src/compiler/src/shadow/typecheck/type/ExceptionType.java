package shadow.typecheck.type;

import java.io.PrintWriter;



public class ExceptionType extends ClassType
{
	public ExceptionType(String typeName, Modifiers modifiers, Type outer ) {
		this( typeName, modifiers, outer, Type.EXCEPTION );
	}
	
	public ExceptionType(String typeName, Modifiers modifiers, Type outer, ClassType extendType ) {
		super( typeName, modifiers, outer );
		setExtendType( extendType );
	}
	
	public boolean isSubtype(Type t)
	{
		if( t == UNKNOWN )
			return false;
	
		if( typeEquals(t) )
			return true;		
		
		if ( t.typeEquals(OBJECT) )
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
