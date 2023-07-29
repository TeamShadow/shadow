package shadow.interpreter;

/**
 * ShadowNumeric doesn't map to a class or interface in Shadow, but it's a convenient layer of
 * abstraction.
 *
 * @author Barry Wittman
 */
public abstract class ShadowNumeric extends ShadowNumber {

  protected ShadowNumeric(int modifiers) {
    super(modifiers);
  }

  @Override
  public ShadowValue[] callMethod(String method, ShadowValue... arguments)
      throws InterpreterException {
    if (arguments.length == 0) {
      switch (method) {
        case "abs":
          return new ShadowNumber[] {abs()};
        case "cos":
          return new ShadowNumber[] {cos()};
        case "sin":
          return new ShadowNumber[] {sin()};
        case "squareRoot":
          return new ShadowNumber[] {squareRoot()};
        case "logBase10":
          return new ShadowNumber[] {logBase10()};
        case "logBase2":
          return new ShadowNumber[] {logBase2()};
        case "logBaseE":
          return new ShadowNumber[] {logBaseE()};
      }
    } else if (arguments.length == 1 && arguments[0] instanceof ShadowNumber number) {
      switch (method) {
        case "power":
          return new ShadowNumber[] {power(number)};
        case "max":
          return new ShadowNumber[] {max(number)};
        case "min":
          return new ShadowNumber[] {min(number)};
      }
    }

    return super.callMethod(method, arguments);
  }

  public abstract ShadowNumber abs() throws InterpreterException;

  public abstract ShadowNumber cos() throws InterpreterException;

  public abstract ShadowNumber sin() throws InterpreterException;

  public abstract ShadowNumber power(ShadowNumber number) throws InterpreterException;

  public abstract ShadowNumber squareRoot() throws InterpreterException;

  public abstract ShadowNumber logBase10() throws InterpreterException;

  public abstract ShadowNumber logBase2() throws InterpreterException;

  public abstract ShadowNumber logBaseE() throws InterpreterException;
}
