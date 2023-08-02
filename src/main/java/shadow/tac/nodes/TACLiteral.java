package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.interpreter.ShadowNull;
import shadow.interpreter.ShadowValue;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class TACLiteral extends TACOperand {
  private final ShadowValue value;

  public TACLiteral(TACNode node, ShadowValue value) {
    super(node);
    this.value = value;
  }

  public ShadowValue getValue() {
    return value;
  }

  @Override
  public ShadowValue getData() {
    return value;
  }

  @Override
  public Modifiers getModifiers() {
    return getValue().getModifiers();
  }

  @Override
  public Type getType() {
    return getValue().getType();
  }

  @Override
  public void setType(Type type) {
    getValue().setType(type);
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

  @Override
  public String toString() {
    return value.toString();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof TACLiteral literal)) return false;

    if (object == this) return true;

    try {
      return value.equals(literal.value);
    } catch (ShadowException e) {
      return false;
    }
  }

  @Override
  public boolean canPropagate() {
    return true;
  }

  @Override
  public boolean isNull() {
    return value instanceof ShadowNull;
  }
}
