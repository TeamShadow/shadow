/* Generated By:JJTree: Do not edit this line. ASTMethodDeclaration.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package shadow.parser.javacc;

import shadow.typecheck.type.MethodSignature;

public
@SuppressWarnings("all")
class ASTMethodDeclaration extends SignatureNode {
	
  public ASTMethodDeclaration(int id) {
    super(id);
  }

  public ASTMethodDeclaration(ShadowParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ShadowParserVisitor visitor, Boolean secondVisit) throws ShadowException {
    return visitor.visit(this, secondVisit);
  }
  
  public String toString()
  {
	  return jjtGetChild(0).toString() + type;	  
  }
  
}
/* JavaCC - OriginalChecksum=1c1cdb2a47b8a755137e9b35efca7f37 (do not edit this line) */
