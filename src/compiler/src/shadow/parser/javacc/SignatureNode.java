package shadow.parser.javacc;

import shadow.Configuration;
import shadow.typecheck.MethodSignature;

public class SignatureNode extends SimpleNode {
	
	public SignatureNode(int id) {
    	super(id);
    }
    
    public SignatureNode(ShadowParser sp, int id) {
    	super(sp, id);
    }
	
	private MethodSignature signature;
	private int modifiers;	
	
	public MethodSignature getMethodSignature()
	{
		return signature;
	}
	
	public void setMethodSignature(MethodSignature signature)
	{
		this.signature = signature;
	}
	

	  
	  public void setModifiers(int modifiers) {
		  this.modifiers = modifiers;
	  }
	  
	  public int getModifiers() {
		  return modifiers;
	  }
	

}
