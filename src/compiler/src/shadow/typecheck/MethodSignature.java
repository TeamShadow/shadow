package shadow.typecheck;

import java.util.LinkedList;

public class MethodSignature {
	protected LinkedList<String> parameters;
	protected LinkedList<String> returns;
	
	public MethodSignature() {
		parameters = new LinkedList<String>();
		returns = new LinkedList<String>();
	}
	
	public void addParameter(String param) {
		parameters.add(param);
	}
	
	public void addReturn(String ret) {
		returns.add(ret);
	}
}
