package shadow.TAC;

import shadow.TAC.nodes.TACNode;
import shadow.parser.javacc.Node;
import shadow.parser.javacc.ShadowException;
import shadow.AST.ASTUtils;

public class TACMethod {
	private TACNode entry;
	private TACNode exit;
	private String name;
	
	public TACMethod(String name, Node astRoot) throws ShadowException {
		this.name = name;
		
		// we walk the AST converting to TAC during construction
		AST2TAC a2t = new AST2TAC(astRoot);
		
		ASTUtils.DEBUG(astRoot);
		
		a2t.convert();
		
		entry = a2t.getEntry();
		exit = a2t.getExit();
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
