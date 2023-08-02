package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACMethod.TACFinallyFunction;
import shadow.tac.TACVisitor;

public class TACCallFinallyFunction extends TACNode {

  private final TACFinallyFunction function;
  private final TACCleanupPad cleanupPad;

  public TACCallFinallyFunction(
      TACNode node, TACFinallyFunction function, TACCleanupPad cleanupPad) {
    super(node);
    this.function = function;
    this.cleanupPad = cleanupPad;
  }

  public TACFinallyFunction getFinallyFunction() {
    return function;
  }

  public TACCleanupPad getCleanupPad() {
    return cleanupPad;
  }

  @Override
  public int getNumOperands() {
    return 0;
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
