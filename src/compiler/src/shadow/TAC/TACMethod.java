package shadow.TAC;

import shadow.TAC.nodes.TACNode;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.SimpleNode;

public class TACMethod {
	private TACNode entry;
	private TACNode exit;
	private String name;
	
	
	/**
	 * Create a TACMethod given an AST node AFTER it has been walked!
	 * @param name The name of the method
	 * @param astRoot The AST node for the root of the method
	 * @throws ShadowException
	 */
	public TACMethod(String name, Node astRoot) throws ShadowException {
		this.name = name;
		entry = ((SimpleNode)astRoot).getEntryNode();
		exit = ((SimpleNode)astRoot).getExitNode();
	}
	
	/**
	 * Create a TACMethod given an entry and exit for that method
	 * @param name The name of the method
	 * @param entry The entry into the method
	 * @param exit The exit from the method
	 */
	public TACMethod(String name, TACNode entry, TACNode exit) {
		this.name = name;
		this.entry = entry;
		this.exit = exit;
	}
	
	public TACNode getEntry() {
		return entry;
	}
	
	public TACNode getExit() {
		return exit;
	}
	
	public String getName() {
		return name;
	}
}
