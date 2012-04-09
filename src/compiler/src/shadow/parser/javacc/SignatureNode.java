package shadow.parser.javacc;

import shadow.typecheck.type.MethodSignature;

public class SignatureNode extends SimpleNode {
	
	public SignatureNode(int id) {
    	super(id);
    }
    
    public SignatureNode(ShadowParser sp, int id) {
    	super(sp, id);
    }
	
	private MethodSignature signature;
	
	public MethodSignature getMethodSignature()
	{
		return signature;
	}
	
	public void setMethodSignature(MethodSignature signature)
	{
		this.signature = signature;
	}
}
