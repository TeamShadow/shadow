package shadow.TAC.nodes;

import java.util.LinkedList;
import java.util.List;

import shadow.TAC.TACVariable;
import shadow.output.AbstractTACVisitor;
import shadow.parser.javacc.Node;

/**
 * Represents a method call in the TAC.
 */
public class TACMethodCall extends TACNode {
	
	private List<TACVariable> parameters;
	private String methodName;

	public TACMethodCall(Node astNode) {
		super(astNode, "CALL", null);
		parameters = new LinkedList<TACVariable>();
		methodName = astNode.getImage();
	}

	public TACMethodCall(Node astNode, String name, TACNode parent, TACNode next) {
		super(astNode, name, parent, next);
		parameters = new LinkedList<TACVariable>();
	}
	
	public void accept(AbstractTACVisitor visitor) {
		visitor.visit(this);
	}
	
	public void addParameter(TACVariable var) {
		parameters.add(var);
	}
	
	public String getMethodName() {
		return methodName;
	}
	
	public int getParamCount() {
		return parameters.size();
	}
	
	public TACVariable[] getParameters() {
		TACVariable[] ret = new TACVariable[parameters.size()];
		
		return parameters.toArray(ret);
	}
}
