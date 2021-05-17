package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVariable;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

public class TACLocalRecover extends TACOperand {

  private final TACVariable variable;
  private final int index;

  public TACLocalRecover(TACNode node, TACVariable variable, int index) {
    super(node);
    this.variable = variable;
    this.index = index;
  }

  public int getIndex() {
    return index;
  }

  @Override
  public Type getType() {
    return variable.getType();
  }

  public TACVariable getVariable() {
    return variable;
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
