package shadow.parser.javacc;

import shadow.typecheck.type.MethodType;

public class ASTMethodCall extends SimpleNode {
	
	private MethodType methodType;
	
	public ASTMethodCall(int id) {
		super(id);
	}
	
	public ASTMethodCall(ShadowParser p, int id) {
	    super(p, id);
	}

	/** Accept the visitor. **/
	public Object jjtAccept(ShadowParserVisitor visitor, Boolean secondVisit) throws ShadowException {
		  return visitor.visit(this, secondVisit);
	}
	  
	public String toString()
	{
		  return /*jjtGetChild(0).toString() + */type.toString();
	}
	
	public MethodType getMethodType() {
		return methodType;
	}
	
	public void setMethodType( MethodType methodType ) {
		this.methodType = methodType;
	}
}
