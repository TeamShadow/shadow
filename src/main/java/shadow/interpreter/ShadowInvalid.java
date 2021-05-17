package shadow.interpreter;

import shadow.typecheck.type.Type;

/**
 * A dummy value produced when an invalid or unsupported expression is interpreted. Should propagate
 * quietly as a result of any operation that involves it - only the initial expression of concern
 * should report an error (and possibly later compilation stages).
 */
public class ShadowInvalid extends ShadowValue {

  public static final ShadowInvalid INVALID = new ShadowInvalid();

  @Override
  public ShadowValue cast(Type type) throws InterpreterException {
    return INVALID;
  }

  @Override
  public ShadowValue copy() throws InterpreterException {
    return INVALID;
  }

  @Override
  public String toString() {
    return "Invalid";
  }

  @Override
  public String toLiteral() {
    throw new UnsupportedOperationException("Cannot convert an invalid value to a literal");
  }

  @Override
  public Type getType() {
    return Type.UNKNOWN;
  }
}
