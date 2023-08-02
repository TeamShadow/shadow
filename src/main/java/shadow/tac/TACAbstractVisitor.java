package shadow.tac;

import shadow.ShadowException;
import shadow.tac.nodes.*;
import shadow.tac.nodes.TACClass.TACClassData;
import shadow.tac.nodes.TACClass.TACMethodTable;

public abstract class TACAbstractVisitor implements TACVisitor {

  protected TACNode current = null;

  public void walk(TACNode nodes) throws ShadowException {
    current = nodes;
    do {
      visit(current);
      current = current.getNext();
    } while (current != nodes);
  }

  protected void visit(TACNode node) throws ShadowException {
    if (node == null) {
      throw new RuntimeException();
    }
    node.accept(this);
  }

  @Override
  public void visit(TACAllocateVariable node) throws ShadowException {}

  @Override
  public void visit(TACBinary node) throws ShadowException {}

  @Override
  public void visit(TACBlock node) throws ShadowException {}

  @Override
  public void visit(TACBranch node) throws ShadowException {}

  @Override
  public void visit(TACCall node) throws ShadowException {}

  @Override
  public void visit(TACCallFinallyFunction node) throws ShadowException {}

  @Override
  public void visit(TACCast node) throws ShadowException {}

  @Override
  public void visit(TACCatch node) throws ShadowException {}

  @Override
  public void visit(TACCatchPad node) throws ShadowException {}

  @Override
  public void visit(TACCatchRet node) throws ShadowException {}

  @Override
  public void visit(TACCleanupPad node) throws ShadowException {}

  @Override
  public void visit(TACCleanupRet node) throws ShadowException {}

  @Override
  public void visit(TACChangeReferenceCount node) throws ShadowException {}

  @Override
  public void visit(TACClass node) throws ShadowException {}

  @Override
  public void visit(TACClassData node) throws ShadowException {}

  @Override
  public void visit(TACCopyMemory node) throws ShadowException {}

  @Override
  public void visit(TACLabel node) throws ShadowException {}

  @Override
  public void visit(TACLabelAddress node) throws ShadowException {}

  @Override
  public void visit(TACLandingPad node) throws ShadowException {}

  @Override
  public void visit(TACLiteral node) throws ShadowException {}

  @Override
  public void visit(TACLoad node) throws ShadowException {}

  @Override
  public void visit(TACLocalEscape node) throws ShadowException {}

  @Override
  public void visit(TACLocalLoad node) throws ShadowException {}

  @Override
  public void visit(TACLocalRecover node) throws ShadowException {}

  @Override
  public void visit(TACLocalStore node) throws ShadowException {}

  @Override
  public void visit(TACLongToPointer node) throws ShadowException {}

  @Override
  public void visit(TACMethodName node) throws ShadowException {}

  @Override
  public void visit(TACMethodPointer node) throws ShadowException {}

  @Override
  public void visit(TACMethodTable node) throws ShadowException {}

  @Override
  public void visit(TACNewArray node) throws ShadowException {}

  @Override
  public void visit(TACNewObject node) throws ShadowException {}

  @Override
  public void visit(TACPhi node) throws ShadowException {}

  @Override
  public void visit(TACPointerToLong node) throws ShadowException {}

  @Override
  public void visit(TACSequenceElement node) throws ShadowException {}

  @Override
  public void visit(TACReturn node) throws ShadowException {}

  @Override
  public void visit(TACResume node) throws ShadowException {}

  @Override
  public void visit(TACSequence node) throws ShadowException {}

  @Override
  public void visit(TACStore node) throws ShadowException {}

  @Override
  public void visit(TACThrow node) throws ShadowException {}

  @Override
  public void visit(TACTypeId node) throws ShadowException {}

  @Override
  public void visit(TACUnary node) throws ShadowException {}

  @Override
  public void visit(TACParameter node) throws ShadowException {}
}
