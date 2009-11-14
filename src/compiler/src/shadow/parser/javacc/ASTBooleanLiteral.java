package shadow.parser.javacc;


public class ASTBooleanLiteral extends SimpleNode {
	private boolean isTrue = false;
	
	public ASTBooleanLiteral(int i) {
		super(i);
	}

	public ASTBooleanLiteral(ShadowParser p, int i) {
		super(p, i);
	}
	
    public Object jjtAccept(ShadowParserVisitor visitor, Object data) {
    	return visitor.visit(this, data);
    }

    public void dump(String prefix) {
    	if(isTrue())
    		System.out.println(prefix + "ASTBooleanLiteral: true");
    	else
    		System.out.println(prefix + "ASTBooleanLiteral: false");
        dumpChildren(prefix);
    }

	public void setTrue() {
		isTrue = true;
	}
	
	public Boolean isTrue() {
		return isTrue;
	}
}
