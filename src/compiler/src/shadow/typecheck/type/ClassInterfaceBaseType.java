package shadow.typecheck.type;

import java.util.HashMap;

import shadow.typecheck.MethodSignature;

public class ClassInterfaceBaseType extends Type {
	protected HashMap<String, Type> fieldTable;
	protected HashMap<String, MethodSignature> methodTable;
	protected String packageName; //package
	
	public ClassInterfaceBaseType(String typeName) {
		this( typeName, 0 );
	}
	
	public ClassInterfaceBaseType(String typeName, int modifiers) {
		this( typeName, modifiers, null );
	}
	
	public ClassInterfaceBaseType(String typeName, int modifiers, Type outer ) {
		this( typeName, modifiers, outer, Kind.CLASS );
	}	
	
	public ClassInterfaceBaseType(String typeName, int modifiers, Type outer, Kind kind ) {
		super( typeName, modifiers, outer, kind );
		fieldTable = new HashMap<String, Type>();
		methodTable = new HashMap<String, MethodSignature>();
	}	
	
	public boolean containsField(String fieldName) {
		return fieldTable.containsKey(fieldName);
	}
	
	public void addField(String fieldName, Type type) {
		fieldTable.put(fieldName, type);
	}
	
	public Type getField(String fieldName) {
		return fieldTable.get(fieldName);
	}
	
	public boolean containsMethod(MethodSignature signature) {
		return methodTable.containsValue(signature);
	}
	
	public void addMethod(String name, MethodSignature signature) {
		methodTable.put(name, signature);
	}
	
	public MethodSignature getMethod(String methodName) {
		return methodTable.get(methodName);
	}
	
	/**
	 * This function is really only used for error reporting as it finds a duplicate signature.
	 * @param signature
	 * @return
	 */
	public MethodSignature getMethodSignature(MethodSignature signature) {
		MethodSignature ret = null;
		
		for(MethodSignature ms:methodTable.values()) {
			if(ms.equals(signature)) {
				ret = ms;
				break;
			}
		}
		
		return ret;
	}

}
