package shadow.parser.javacc;

import shadow.typecheck.type.ClassType;


public class ASTMethodCall extends SimpleNode {
	
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
	
	private boolean isConstructor = false;	
	private ClassType constructorType;
	
	public void setConstructorType(ClassType type)
	{
		constructorType = type;
		isConstructor = true;
	}
	
	public boolean isConstructor()
	{
		return isConstructor;
	}
	
	public ClassType getConstructorType()
	{
		return constructorType;
	}
	
}
