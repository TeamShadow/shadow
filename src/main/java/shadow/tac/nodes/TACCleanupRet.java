package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACBlock;
import shadow.tac.TACVisitor;

public class TACCleanupRet extends TACNode {
  private final TACCleanupPad cleanupPad;

  public TACCleanupRet(TACNode node, TACCleanupPad cleanupPad) {
    super(node);
    this.cleanupPad = cleanupPad;
  }

  public TACLabel getUnwind() {
    TACLabel unwindLabel = null;
    // Have to get parent block or else we'll unwind to the same place
    TACBlock parent = getBlock().getParent();
    if (parent != null) unwindLabel = parent.getUnwind();
    return unwindLabel;
  }

  @Override
  public int getNumOperands() {
    return 0;
  }

  public TACCleanupPad getCleanupPad() {
    return cleanupPad;
  }

  @Override
  public TACOperand getOperand(int num) {
    throw new IndexOutOfBoundsException("" + num);
  }

  @Override
  public void accept(TACVisitor visitor) throws ShadowException {
    visitor.visit(this);
  }
}
