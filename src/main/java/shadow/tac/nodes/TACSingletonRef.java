package shadow.tac.nodes;

import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.SingletonType;
import shadow.typecheck.type.Type;

public class TACSingletonRef extends TACReference {
  private final SingletonType type;

  public TACSingletonRef(SingletonType instanceType) {
    type = instanceType;
  }

  @Override
  public SingletonType getType() {
    return type;
  }

  @Override
  public String toString() {
    return type + ":instance";
  }

  @Override
  public void setType(Type type) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Modifiers getModifiers() {
    return new Modifiers();
  }
}
