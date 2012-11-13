package shadow.typecheck.type;

import java.io.PrintWriter;

public class SingletonType extends ClassType {
	
	public SingletonType(String typeName, ClassType parent ) {
		super( typeName, parent );

	}

	public SingletonType(String typeName, Modifiers modifiers, ClassInterfaceBaseType outer ) {
		super( typeName, modifiers, outer );
	}
	
	public void printMetaFile(PrintWriter out, String linePrefix )
	{
		printMetaFile(out, linePrefix, "singleton");	
	}
}