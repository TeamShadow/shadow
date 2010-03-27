package shadow.TAC;

import java.util.LinkedList;

import shadow.TAC.nodes.TACNode;
import shadow.typecheck.MethodSignature;
import shadow.typecheck.type.Type;

public class TACMethod {
	
	private LinkedList<TACNode> body;
	private String name;
	
	public TACMethod(String name, MethodSignature signature, Type type) {
		this.name = name;
		body = new LinkedList<TACNode>();
	}
	
	public void addNode(TACNode node) {
		body.add(node);
	}
	
}
