package shadow.typecheck;

public class Type {
	protected String typeName;	/** A string the represents the type */
	protected int modifiers;
	
	public Type(String typeName) {
		this.typeName = typeName;
	}
	
	public Type(String typeName, int modifiers) {
		this.typeName = typeName;
		this.modifiers = modifiers;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public int getModifiers() {
		return modifiers;
	}
	
	public String toString() {
		return typeName;
	}
	
	/**
	 * Need to override equals as we're doing special things
	 * @param ms The method signature to compare to
	 * @return True if the methods can co-exist, False otherwise
	 */
	public boolean equals(Object o) {
		Type t = (Type)o;
		
		// if the type names are the same, then we're good
		if(typeName.equals(t.typeName))
			return true;
		
		//
		// Put in sub-typing logic here
		//
		
		return false;
	}
	
	// TODO: Will this work?
	public int hashCode() {
		return typeName.hashCode();
	}

}
