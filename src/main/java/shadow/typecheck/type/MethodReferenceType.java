package shadow.typecheck.type;

public class MethodReferenceType extends ClassType {	
	
	MethodType methodType;
	
	public MethodReferenceType(MethodType methodType) {
		super(null, methodType.getModifiers(), null, methodType.getOuter());
		this.methodType = methodType;		
		setExtendType(Type.METHOD);
	}	
	
	public MethodType getMethodType() {
		return methodType;
	}
	
	@Override
	public String toString(int options) {	
		if( (options & MANGLE) != 0 )
			return Type.METHOD.toString(options);
		
		return methodType.toString(options);
	}

	@Override
	public boolean equals(Type o) {
		if( o != null && o instanceof MethodReferenceType )
			return methodType.equals(((MethodReferenceType)o).getMethodType());
		else
			return false;
	}
	
	
	@Override
	public boolean isSubtype(Type t) {
		if( t == Type.METHOD )
			return true;	
		
		if( t instanceof MethodType )
			return methodType.isSubtype(t);
	
		if( t instanceof MethodReferenceType )
			return methodType.isSubtype(((MethodReferenceType)t).getMethodType());
		
		return super.isSubtype(t);
	}
	
}
