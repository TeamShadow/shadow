package shadow.interpreter;

import shadow.interpreter.InterpreterException.Error;
import shadow.typecheck.type.Type;

public class ShadowObject extends ShadowValue {
  private final Type type;

  public ShadowObject(Type type) throws InterpreterException {
    if (type.isPrimitive())
      throw new InterpreterException(
          Error.INVALID_TYPE, "Cannot create an object with a " + "primitive type");
    this.type = type;
  }

  @Override
  public Type getType() {
    return type;
  }

  @Override
  public ShadowValue copy() throws InterpreterException {
    return new ShadowObject(getType());
  }

  @Override
  public String toLiteral() {
    throw new UnsupportedOperationException("Cannot convert an arbitrary object to a literal");
  }

  @Override
  public ShadowValue cast(Type type) throws InterpreterException {
    return new ShadowObject(type);
  }
}
