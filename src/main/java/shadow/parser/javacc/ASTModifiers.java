/* Generated By:JJTree: Do not edit this line. ASTModifiers.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package shadow.parser.javacc;

import org.apache.logging.log4j.Logger;

import shadow.Loggers;
import shadow.typecheck.type.Modifiers;

public
@SuppressWarnings("all")
class ASTModifiers extends SimpleNode {
  private static final Logger logger = Loggers.TYPE_CHECKER;
  
  public ASTModifiers(int id) {
    super(id);
  }

  public ASTModifiers(ShadowParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ShadowParserVisitor visitor, Boolean secondVisit) throws ShadowException {
    return visitor.visit(this, secondVisit);
  }
  
  Modifiers modifiers = new Modifiers();
  
  public void setModifiers(Modifiers modifiers) {
	  this.modifiers = modifiers;
  }
  
  public void dump(String prefix) {
	  StringBuilder sb = new StringBuilder(prefix + "ASTModifiers(" + lineStart + ":" + columnStart + "): ");
		  	  
	  sb.append(modifiers.toString());	  

	  logger.debug(sb.toString());
	  dumpChildren(prefix);
  }
}
/* JavaCC - OriginalChecksum=48a1e9a20c9e4e1426715db818f4bb4a (do not edit this line) */
