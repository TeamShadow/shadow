package shadow.parser.javacc;

public class ASTCompilationUnit extends SimpleNode {

	public ASTCompilationUnit(int id) {
		super(id);
	}

	public ASTCompilationUnit(ShadowParser sp, int id) {
		super(sp, id);
	}

    public Object jjtAccept(ShadowParserVisitor visitor, Boolean secondVisit) throws ShadowException {
    	return visitor.visit(this, secondVisit);
    }
}
