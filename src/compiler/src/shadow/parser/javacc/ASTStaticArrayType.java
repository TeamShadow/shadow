package shadow.parser.javacc;

public class ASTStaticArrayType extends SimpleNode {

	protected int arraySize;
	
	public ASTStaticArrayType(int id) {
		super(id);
	}

	public ASTStaticArrayType(ShadowParser sp, int id) {
		super(sp, id);
	}

	@Override
	public Object jjtAccept(ShadowParserVisitor visitor, Object data) throws ShadowException {
		return null;
	}
	
	public void setArraySize(String size) {
		arraySize = Integer.parseInt(size);
	}

}
