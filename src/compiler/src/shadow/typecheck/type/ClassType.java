package shadow.typecheck.type;

import java.util.ArrayList;

public class ClassType extends ClassInterfaceBaseType {
	protected ClassType extendType;
	protected ArrayList<InterfaceType> implementTypes;
	
	public ClassType(String typeName) {
		super(typeName);
	}

	public ClassType(String typeName, int modifiers) {
		super(typeName, modifiers);
	}

	public ClassType(String typeName, int modifiers, Type enclosing) {
		super(typeName, modifiers, enclosing);
	}

	public ClassType(String typeName, int modifiers, Type enclosing, Type parent) {
		super(typeName, modifiers, enclosing, parent);
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
