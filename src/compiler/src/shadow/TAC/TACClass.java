package shadow.TAC;

import java.util.LinkedList;

public class TACClass {

	private LinkedList<TACVariable> fields;
	private LinkedList<TACMethod> methods;
	private LinkedList<TACClass> classes;	// do we even want these here?
	private String className;
	
	public TACClass(String name) {
		fields = new LinkedList<TACVariable>();
		methods = new LinkedList<TACMethod>();
		classes = new LinkedList<TACClass>();
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
	
	public void addClass(TACClass c) {
		classes.add(c);
	}
	
	public LinkedList<TACClass> getClasses() {
		return classes;
	}
	
	public String getName() {
		return className;
	}
}
