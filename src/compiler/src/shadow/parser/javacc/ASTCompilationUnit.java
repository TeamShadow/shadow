package shadow.parser.javacc;

public class ASTCompilationUnit extends SimpleNode {

	public ASTCompilationUnit(int id) {
		super(id);
	}

	public ASTCompilationUnit(ShadowParser sp, int id) {
		super(sp, id);
	}

    public Object jjtAccept(ShadowParserVisitor visitor, Object data) {
    	return visitor.visit(this, data);
    }
}
