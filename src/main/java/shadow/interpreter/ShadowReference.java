package shadow.interpreter;

import shadow.typecheck.type.Type;

import java.util.Map;

public abstract class ShadowReference extends ShadowValue {
  public abstract ShadowValue get() throws InterpreterException;

  public abstract void set(ShadowValue value) throws InterpreterException;

  @Override
  public final String toLiteral() {
    throw new UnsupportedOperationException(
        "Cannot convert a reference to a literal without first getting its contents");
  }

  @Override
  public final ShadowValue cast(Type type) throws InterpreterException {
    throw new InterpreterException(
        InterpreterException.Error.UNSUPPORTED_OPERATION,
        "Cannot cast a reference without first getting its contents");
  }

  @Override
  public final ShadowValue copy(Map<ShadowValue, ShadowValue> newValues)
      throws InterpreterException {
    throw new InterpreterException(
        InterpreterException.Error.UNSUPPORTED_OPERATION,
        "Cannot copy a reference without first getting its contents");
  }
}
