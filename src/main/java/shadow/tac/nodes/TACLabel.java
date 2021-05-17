package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACMethod;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACLabel extends TACOperand {

  private final String name;
  private final int number;

  public TACLabel(TACMethod method) {
    super(null);
    number = method.incrementLabelCounter();
    name = "_label" + number;
  }

  public int getNumber() {
    return number;
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
  public Object getData() {
    return name;
  }

  @Override
  public void accept(TACVisitor visitor) throws ShadowException {
    visitor.visit(this);
  }

  @Override
  public Type getType() {
    return null;
  }

  @Override
  public String toString() {
    return name;
  }
}
