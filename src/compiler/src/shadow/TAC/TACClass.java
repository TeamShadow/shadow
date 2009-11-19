package shadow.TAC;

import java.util.LinkedList;

public class TACClass extends TACNode {

	protected int modifier;
	protected LinkedList<TACVariable> members;
	protected LinkedList<TACMethod> methods;
	protected LinkedList<TACClass> classes;
	
	public TACClass(String name, TACNode parent) {
		super(name, parent);
		members = new LinkedList<TACVariable>();
		methods = new LinkedList<TACMethod>();
		classes = new LinkedList<TACClass>();
	}
	
	public void setModifier(int modifier) {
		this.modifier = modifier;
	}
	
	public void addMember(TACVariable var) {
		members.add(var);
	}
	
	public LinkedList<TACVariable> getMembers() {
		return members;
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
}
