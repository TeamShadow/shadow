package shadow.interpreter;

import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

public class ShadowBoolean extends ShadowValue {
  private final boolean value;

  public ShadowBoolean(boolean value) {
    super(Modifiers.IMMUTABLE);
    this.value = value;
  }

  @Override
  public Type getType() {
    return Type.BOOLEAN;
  }

  public boolean getValue() {
    return value;
  }

  public ShadowBoolean not() throws InterpreterException {
    return new ShadowBoolean(!value);
  }

  @Override
  public ShadowValue cast(Type type) throws InterpreterException {
    if (type.equals(Type.BOOLEAN)) return this;
    throw new UnsupportedOperationException("Cannot cast " + getType() + " to " + type);
  }

  @Override
  public ShadowValue copy() throws InterpreterException {
    return new ShadowBoolean(value);
  }

  @Override
  public String toString() {
    return toLiteral();
  }

  @Override
  public String toLiteral() {
    return Boolean.toString(value);
  }
}
