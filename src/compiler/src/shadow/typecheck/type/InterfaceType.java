package shadow.typecheck.type;

import java.util.ArrayList;

import shadow.typecheck.type.Type.Kind;

public class InterfaceType extends ClassInterfaceBaseType {
	protected ArrayList<InterfaceType> extendType;

	public InterfaceType(String typeName) {
		this( typeName, 0 );
	}
	
	public InterfaceType(String typeName, int modifiers) {
		this( typeName, modifiers, null );
	}
	
	public InterfaceType(String typeName, int modifiers, Type outer ) {
		this( typeName, modifiers, outer, Kind.INTERFACE );
	}	
	
	public InterfaceType(String typeName, int modifiers, Type outer, Kind kind ) {
		this( typeName, modifiers, outer, kind, OBJECT );
	}
	
	public InterfaceType(String typeName, int modifiers, Type outer, Kind kind, Type parent ) {
		super( typeName, modifiers, outer, kind, parent );
	}

	public void addExtendType(InterfaceType extendType) {
		this.extendType.add(extendType);
	}
}
