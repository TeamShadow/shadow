package shadow.tac;

import shadow.ShadowException;
import shadow.tac.nodes.*;
import shadow.tac.nodes.TACClass.TACClassData;
import shadow.tac.nodes.TACClass.TACMethodTable;

public interface TACVisitor {
  void visit(TACAllocateVariable node) throws ShadowException;

  void visit(TACBinary node) throws ShadowException;

  void visit(TACBlock node) throws ShadowException;

  void visit(TACBranch node) throws ShadowException;

  void visit(TACCall node) throws ShadowException;

  void visit(TACCallFinallyFunction node) throws ShadowException;

  void visit(TACCast node) throws ShadowException;

  void visit(TACCatch node) throws ShadowException;

  void visit(TACCatchPad node) throws ShadowException;

  void visit(TACCatchRet node) throws ShadowException;

  void visit(TACClass node) throws ShadowException;

  void visit(TACClassData node) throws ShadowException;

  void visit(TACCleanupPad node) throws ShadowException;

  void visit(TACCopyMemory node) throws ShadowException;

  void visit(TACChangeReferenceCount node) throws ShadowException;

  void visit(TACLabel node) throws ShadowException;

  void visit(TACLabelAddress node) throws ShadowException;

  void visit(TACLandingPad node) throws ShadowException;

  void visit(TACLiteral node) throws ShadowException;

  void visit(TACLoad node) throws ShadowException;

  void visit(TACLocalEscape node) throws ShadowException;

  void visit(TACLocalLoad node) throws ShadowException;

  void visit(TACLocalRecover node) throws ShadowException;

  void visit(TACLocalStore node) throws ShadowException;

  void visit(TACLongToPointer node) throws ShadowException;

  void visit(TACMethodName node) throws ShadowException;

  void visit(TACMethodPointer node) throws ShadowException;

  void visit(TACMethodTable tacMethodTable) throws ShadowException;

  void visit(TACNewArray node) throws ShadowException;

  void visit(TACNewObject node) throws ShadowException;

  void visit(TACPhi node) throws ShadowException;

  void visit(TACPointerToLong node) throws ShadowException;

  void visit(TACResume node) throws ShadowException;

  void visit(TACSequenceElement node) throws ShadowException;

  void visit(TACCleanupRet node) throws ShadowException;

  void visit(TACReturn node) throws ShadowException;

  void visit(TACSequence node) throws ShadowException;

  void visit(TACStore node) throws ShadowException;

  void visit(TACThrow node) throws ShadowException;

  void visit(TACTypeId node) throws ShadowException;

  void visit(TACUnary node) throws ShadowException;

  void visit(TACParameter node) throws ShadowException;
}
