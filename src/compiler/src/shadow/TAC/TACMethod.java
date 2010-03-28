package shadow.TAC;

import shadow.TAC.nodes.TACNode;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.MethodSignature;

public class TACMethod {
	
	private TACNode root;
	private String name;
	
	public TACMethod(String name, MethodSignature signature) throws ShadowException {
		this.name = name;
		
		// we walk the AST converting to TAC during construction
		AST2TAC a2t = new AST2TAC(signature.getASTNode());
		
		a2t.convert();
		
		root = a2t.getTACRoot();
	}
	
}
