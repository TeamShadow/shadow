package shadow.typecheck.type;

import java.io.PrintWriter;

import shadow.doctool.Documentation;

public class SingletonType extends ClassType {
	
	public SingletonType(String typeName, ClassType parent ) 
	{
		super(typeName, parent);
	}

	public SingletonType(String typeName, Modifiers modifiers, 
			Documentation documentation, Type outer) 
	{
		super(typeName, modifiers, documentation, outer);
	}
	
	public void printMetaFile(PrintWriter out, String linePrefix )
	{
		printMetaFile(out, linePrefix, "singleton");	
	}
}