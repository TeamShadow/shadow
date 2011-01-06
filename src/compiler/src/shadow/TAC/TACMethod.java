package shadow.TAC;

import shadow.TAC.nodes.TACNode;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.AST.ASTUtils;

public class TACMethod {
	private TACNode entry;
	private TACNode exit;
	private String name;
	
	
	/**
	 * Create a TACMethod given an AST node
	 * @param name The name of the method
	 * @param astRoot The AST node for the root of the method
	 * @throws ShadowException
	 */
	public TACMethod(String name, Node astRoot) throws ShadowException {
		this.name = name;
		
		// we walk the AST converting to TAC during construction
		AST2TAC a2t = new AST2TAC(astRoot);
		
		ASTUtils.DEBUG(astRoot);
		
		a2t.convert();
		
		entry = a2t.getEntry();
		exit = a2t.getExit();
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
