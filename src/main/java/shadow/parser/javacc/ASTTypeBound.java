/* Generated By:JJTree: Do not edit this line. ASTTypeBound.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package shadow.parser.javacc;

import java.util.ArrayList;
import java.util.List;

import shadow.typecheck.type.TypeParameter;

public
@SuppressWarnings("all")
class ASTTypeBound extends SimpleNode {

  public ASTTypeBound(int id) {
    super(id);
  }

  public ASTTypeBound(ShadowParser p, int id) {
    super(p, id);
  }
  
  /** Accept the visitor. **/
  public Object jjtAccept(ShadowParserVisitor visitor, Boolean secondVisit) throws ShadowException {
    return visitor.visit(this, secondVisit);
  }
}
/* JavaCC - OriginalChecksum=12d28a4045aefce4e614246f8fa2a277 (do not edit this line) */