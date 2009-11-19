package shadow.TAC;

public class TACVariable extends TACNode {
	protected int modifiers;	// won't always be used
	protected String type;	// for now, change later...
	
	public TACVariable(String name, TACNode parent) {
		super(name, parent);
		modifiers = 0;
	}
	
	public String toString() {
		return super.toString() + " " + type; 
	}
	
	public void setModifiers(int modifiers) {
		this.modifiers = modifiers;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setType(String type){
		this.type = type;
	}
}
