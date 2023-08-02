package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ExceptionType;

public class TACCatch extends TACOperand {
  private final TACOperand exception;
  private final ExceptionType type;

  public TACCatch(
      TACNode node, TACOperand exception, ExceptionType type, TACLandingPad landingPad) {
    super(node);
    this.exception = exception;
    this.type = type;
    landingPad.addCatch(this);
  }

  @Override
  public ExceptionType getType() {
    return type;
  }

  @Override
  public int getNumOperands() {
    return 1;
  }

  @Override
  public TACOperand getOperand(int num) {
    if (num == 0) return exception;
    throw new IndexOutOfBoundsException("" + num);
  }

  @Override
  public void accept(TACVisitor visitor) throws ShadowException {
    visitor.visit(this);
  }
}
