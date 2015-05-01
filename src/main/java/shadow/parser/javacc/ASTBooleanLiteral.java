package shadow.parser.javacc;

import org.apache.logging.log4j.Logger;

import shadow.Loggers;
import shadow.typecheck.type.Type;


public class ASTBooleanLiteral extends SimpleNode {
	private static final Logger logger = Loggers.TYPE_CHECKER;
	private boolean isTrue = false;
	
	public ASTBooleanLiteral(int i) {
		super(i);
		this.setImage("false");
		this.setType(Type.BOOLEAN);
	}

	public ASTBooleanLiteral(ShadowParser p, int i) {
		super(p, i);
		this.setImage("false");
		this.setType(Type.BOOLEAN);
	}
	
    public Object jjtAccept(ShadowParserVisitor visitor, Boolean secondVisit) throws ShadowException {
    	return visitor.visit(this, secondVisit);
    }

    public void dump(String prefix) {
    	if(isTrue())
    		logger.debug(prefix + "ASTBooleanLiteral" + "(" + line + ":" + column + "): true");
    	else
    		logger.debug(prefix + "ASTBooleanLiteral" + "(" + line + ":" + column + "): false");
        dumpChildren(prefix);
    }

	public void setTrue() {
		isTrue = true;
		this.setImage("true");
		this.setType(Type.BOOLEAN);
	}
	
	public Boolean isTrue() {
		return isTrue;
	}
}
