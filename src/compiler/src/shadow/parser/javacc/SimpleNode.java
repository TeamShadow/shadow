package shadow.parser.javacc;

public class SimpleNode extends ASTBase {
	public SimpleNode(int i) {
		super(i);
	}

	public SimpleNode(ShadowParser p, int i) {
		super(p, i);
	}

    public Object jjtAccept(ShadowParserVisitor visitor, Object data) {
    	return visitor.visit(this, data);
    }


}
