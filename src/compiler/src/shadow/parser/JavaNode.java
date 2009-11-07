package shadow.parser;
import shadow.parser.javacc.*;

public interface JavaNode extends Node {

    /**
     * Accept the visitor. *
     */
    public Object jjtAccept(JavaParserVisitor visitor, Object data);
}
