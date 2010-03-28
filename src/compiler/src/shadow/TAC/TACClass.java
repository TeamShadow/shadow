package shadow.TAC;

import java.util.LinkedList;

public class TACClass {

	private LinkedList<TACVariable> fields;
	private LinkedList<TACMethod> methods;
	private String className;
	
	public TACClass(String name) {
		fields = new LinkedList<TACVariable>();
		methods = new LinkedList<TACMethod>();
		className = name;
	}
	
	public void addField(TACVariable var) {
		fields.add(var);
	}
	
	public LinkedList<TACVariable> getFields() {
		return fields;
	}
	
	public void addMethod(TACMethod method) {
		methods.add(method);
	}
	
	public LinkedList<TACMethod> getMethods() {
		return methods;
	}
	
	public String getName() {
		return className;
	}
	
	public void dump() {
		System.out.println("CLASS: " + className);
		
		for(TACVariable v:fields) 
			System.out.println("FIELD: " + v);
		
		for(TACMethod m:methods) {
			System.out.println("METHOD: " + m.getName());
			m.getEntry().dump(" ");
		}
	}
}
