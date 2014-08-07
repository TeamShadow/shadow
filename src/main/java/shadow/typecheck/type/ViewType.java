package shadow.typecheck.type;

import java.io.PrintWriter;


public class ViewType extends Type {

	public ViewType(String typeName) {
		super(typeName);
	}

	public ViewType(String typeName, Modifiers modifiers) {
		super(typeName, modifiers);
	}

	@Override
	public boolean isSubtype(Type t)
	{
		return equals(t);
	}

	@Override
	public ViewType replace(SequenceType values,	SequenceType replacements)
	{		
		return this;
	}
	
	@Override
	public String toString(boolean withBounds)
	{		
		return "#" + super.toString(withBounds);
	}

	@Override
	public boolean isRecursivelyParameterized() {
		return false;
	}

	@Override
	public void printMetaFile(PrintWriter out, String linePrefix) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDescendentOf(Type type) {
		return false;
	}

	@Override
	public boolean hasInterface(InterfaceType type) {
		// TODO Auto-generated method stub
		return false;
	}
}
