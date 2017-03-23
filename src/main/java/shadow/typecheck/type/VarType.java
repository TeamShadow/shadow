package shadow.typecheck.type;

public class VarType extends ClassType {
	
	public VarType() {
		super("Var Type", null);
	}	
	
	@Override
	public boolean isSubtype(Type type) {
		return false;
	}
}
