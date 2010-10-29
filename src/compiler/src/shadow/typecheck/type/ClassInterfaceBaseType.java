package shadow.typecheck.type;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import shadow.typecheck.MethodSignature;

public abstract class ClassInterfaceBaseType extends Type {
	protected HashMap<String, Type> fieldTable;
	protected HashMap<String, List<MethodSignature> > methodTable;
	protected HashMap<String, Type> innerClasses;
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
		methodTable = new HashMap<String, List<MethodSignature>>();
		innerClasses = new HashMap<String, Type>();
		
		if( outer != null && outer instanceof ClassInterfaceBaseType)
		{
			ClassInterfaceBaseType outerClass = (ClassInterfaceBaseType)outer;
			outerClass.innerClasses.put(typeName, this);
		}
	}	
	
	public boolean containsField(String fieldName) {
		return fieldTable.containsKey(fieldName);
	}
	
	public boolean containsInnerClass(String className) {
		return innerClasses.containsKey(className);
	}
	
	public Type getInnerClass(String className) {
		return innerClasses.get(className);
	}
	
	public void addField(String fieldName, Type type) {
		fieldTable.put(fieldName, type);
	}
	
	public Type getField(String fieldName) {
		return fieldTable.get(fieldName);
	}
	
	public Map<String, Type> getFields() {
		return fieldTable;
	}
	
	public boolean containsMethod(MethodSignature signature) {
		List<MethodSignature> list = methodTable.get(signature.getSymbol());
		
		if( list != null )
			for(MethodSignature existing : list )
				if( existing.equals(signature))
					return true;
		
		return false;
	}
	
	public void addMethod(String name, MethodSignature signature) {
		if( methodTable.containsKey(name) )		
			methodTable.get(name).add(signature);
		else
		{
			List<MethodSignature> list = new LinkedList<MethodSignature>();
			list.add(signature);
			methodTable.put(name, list);
		}
	}
	
	public Map<String, List<MethodSignature>> getMethodMap() {
		return methodTable;
	}
	
	public List<MethodSignature> getMethods(String methodName) {
		return methodTable.get(methodName);
	}
	
	/**
	 * This function is really only used for error reporting as it finds a duplicate signature.
	 * @param signature
	 * @return
	 */
	public MethodSignature getMethodSignature(MethodSignature signature) {
		MethodSignature ret = null;
		
		for(MethodSignature ms : methodTable.get(signature.getSymbol())) {
			if(ms.equals(signature)) {
				ret = ms;
				break;
			}
		}
		
		return ret;
	}

}
