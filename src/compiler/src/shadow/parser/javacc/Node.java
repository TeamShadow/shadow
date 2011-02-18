/* Generated By:JJTree: Do not edit this line. Node.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package shadow.parser.javacc;

import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Type;

/* All AST nodes must implement this interface.  It provides basic
   machinery for constructing the parent and child relationships
   between nodes. */

public
@SuppressWarnings("all")
interface Node extends ModifiedType {

  /** This method is called after the node has been made the current
    node.  It indicates that child nodes can now be added to it. */
  public void jjtOpen();

  /** This method is called after all the child nodes have been
    added. */
  public void jjtClose();

  /** This pair of methods are used to inform the node of its
    parent. */
  public void jjtSetParent(Node n);
  public Node jjtGetParent();

  /** This method tells the node to add its argument to the node's
    list of children.  */
  public void jjtAddChild(Node n, int i);
  
  public void jjtSwapChild(Node n, int i);

  /** This method returns a child node.  The children are numbered
     from zero, left to right. */
  public Node jjtGetChild(int i);

  /** Return the number of children the node has. */
  public int jjtGetNumChildren();
  
	public String getImage();
	
	public void setImage(String image);
	
	public int getLine();
	
	public void setLine(int line);
	
	public int getColumn();
	
	public void setColumn(int column);
	
	public String getLocation();

	public Type getType();
	
	public void setType(Type type);
	
	
	public int getModifiers();
	public void setModifiers( int modifiers );
	public void addModifier( int mod );
	public void removeModifier( int mod );	
	public void setEnclosingType(Type type);
	public Type getEnclosingType();

  /** Accept the visitor. **/
  public Object jjtAccept(ShadowParserVisitor visitor, Boolean secondVisit) throws ShadowException;
}
/* JavaCC - OriginalChecksum=477229613677e4513ff761a31677b937 (do not edit this line) */
