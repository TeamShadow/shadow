package shadow.typecheck.type;

import java.io.PrintWriter;


public class EnumType extends ClassType
{
	public EnumType(String typeName, Modifiers modifiers, Type outer ) {
		this( typeName, modifiers, outer, Type.ENUM );
	}
	
	public EnumType(String typeName, Modifiers modifiers, Type outer, ClassType extendType ) {
		super( typeName, modifiers, outer );
		setExtendType( extendType );
	}
	
	public void printMetaFile(PrintWriter out, String linePrefix )
	{
		printMetaFile(out, linePrefix, "enum");	
	}
}
