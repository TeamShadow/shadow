package shadow.parser.javacc;


public class ASTBooleanLiteral extends ASTBase {
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


	public void setTrue() {
		isTrue = true;
	}
	
	public Boolean isTrue() {
		return isTrue;
	}
}
