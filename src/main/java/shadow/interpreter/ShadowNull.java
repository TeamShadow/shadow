package shadow.interpreter;

import shadow.interpreter.InterpreterException.Error;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

import java.util.Map;

public class ShadowNull extends ShadowValue {
  private final Type type;

  public ShadowNull(Type type) {
    super(Modifiers.NULLABLE);
    this.type = type;
  }

  @Override
  public Type getType() {
    return type;
  }

  @Override
  public ShadowValue cast(Type type) throws InterpreterException {
    return new ShadowNull(type);
  }

  @Override
  public ShadowValue copy(Map<ShadowValue, ShadowValue> newValues) throws InterpreterException {
    return new ShadowNull(type);
  }

  @Override
  public ShadowBoolean equal(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowNull) return new ShadowBoolean(true);

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public String toString() {
    return toLiteral();
  }

  @Override
  public String toLiteral() {
    return "null";
  }
}
