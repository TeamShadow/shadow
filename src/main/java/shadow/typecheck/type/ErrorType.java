package shadow.typecheck.type;

import java.io.PrintWriter;

public class ErrorType extends ClassType
{
	public ErrorType(String typeName, Modifiers modifiers, Type outer ) {
		this( typeName, modifiers, outer, Type.ERROR );
	}
	
	public ErrorType(String typeName, Modifiers modifiers, Type outer, ClassType extendType ) {
		super( typeName, modifiers, outer );
		setExtendType( extendType );
	}
	
	public boolean isSubtype(Type t)
	{
		if( equals(t) )
			return true;	
		
		if ( t.equals(OBJECT) || t.equals(Type.EXCEPTION) )
			return true;
		
		if( t instanceof ErrorType )			
			return isDescendentOf(t);
		else
			return false;
	}
	
	public void printMetaFile(PrintWriter out, String linePrefix )
	{
		printMetaFile(out, linePrefix, "error");	
	}
}