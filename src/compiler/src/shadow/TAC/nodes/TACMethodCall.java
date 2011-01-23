package shadow.TAC.nodes;

import java.util.LinkedList;
import java.util.List;

import shadow.TAC.TACVariable;
import shadow.parser.javacc.Node;

/**
 * Represents a method call in the TAC.
 */
public class TACMethodCall extends TACNode {
	
	private List<TACVariable> parameters;

	public TACMethodCall(Node astNode) {
		super(astNode, "CALL", null);
		parameters = new LinkedList<TACVariable>();
	}

	public TACMethodCall(Node astNode, String name, TACNode parent, TACNode next) {
		super(astNode, name, parent, next);
		parameters = new LinkedList<TACVariable>();
	}
	
	public void addParameter(TACVariable var) {
		parameters.add(var);
	}

}
