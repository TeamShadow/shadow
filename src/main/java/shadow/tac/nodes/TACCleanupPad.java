package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACCleanupPad extends TACOperand {

  public TACCleanupPad(TACNode node) {
    super(node);
  }

  @Override
  public void accept(TACVisitor visitor) throws ShadowException {
    visitor.visit(this);
  }

  @Override
  public TACOperand getOperand(int num) {
    throw new IndexOutOfBoundsException("" + num);
  }

  @Override
  public Type getType() {
    return null;
  }

  @Override
  public int getNumOperands() {
    // TODO Auto-generated method stub
    return 0;
  }
}
