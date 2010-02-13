package shadow.typecheck.type;

import java.util.ArrayList;

public class ClassType extends ClassInterfaceBaseType {
	protected ClassType extendType;
	protected ArrayList<InterfaceType> implementTypes;
	
	public ClassType(String typeName) {
		this( typeName, 0 );
	}
	
	public ClassType(String typeName, int modifiers) {
		this( typeName, modifiers, null );
	}
	
	public ClassType(String typeName, int modifiers, Type outer ) {
		this( typeName, modifiers, outer, Kind.CLASS );
	}	
	
	public ClassType(String typeName, int modifiers, Type outer, Kind kind ) {
		super( typeName, modifiers, outer, kind);
	}	
	
	public void setExtendType(ClassType extendType) {
		this.extendType = extendType;
	}
	
	public ClassType getExtendType() {
		return extendType;
	}
	
	public void addImplementType(InterfaceType implementType) {
		this.implementTypes.add(implementType);
	}
}
