package shadow.interpreter;

import shadow.interpreter.InterpreterException.Error;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

import java.math.BigInteger;
import java.util.Map;

public class ShadowDouble extends ShadowNumeric {
  private final double value;

  public ShadowDouble(double value) {
    super(Modifiers.IMMUTABLE);
    this.value = value;
  }

  @Override
  public Type getType() {
    return Type.DOUBLE;
  }

  public double getValue() {
    return value;
  }

  @Override
  public ShadowValue[] callMethod(String method, ShadowValue... arguments)
      throws InterpreterException {
    if (arguments.length == 0) {
      switch (method) {
        case "ceiling":
          return new ShadowDouble[]{ceiling()};
        case "floor":
          return new ShadowDouble[]{floor()};
        case "round":
          return new ShadowDouble[]{round()};
      }
    }

    return super.callMethod(method, arguments);
  }

  @Override
  public ShadowDouble negate() throws InterpreterException {
    return new ShadowDouble(-value);
  }

  @Override
  public ShadowValue cast(Type type) throws InterpreterException {
    BigInteger integer = BigInteger.valueOf(Math.round(value));
    if (type.equals(Type.BYTE)) return new ShadowInteger(integer, 1, true);
    if (type.equals(Type.SHORT)) return new ShadowInteger(integer, 2, true);
    if (type.equals(Type.INT)) return new ShadowInteger(integer, 4, true);
    if (type.equals(Type.LONG)) return new ShadowInteger(integer, 8, true);
    if (type.equals(Type.UBYTE)) return new ShadowInteger(integer, 1, false);
    if (type.equals(Type.USHORT)) return new ShadowInteger(integer, 2, false);
    if (type.equals(Type.UINT)) return new ShadowInteger(integer, 4, false);
    if (type.equals(Type.ULONG)) return new ShadowInteger(integer, 8, false);
    if (type.equals(Type.FLOAT)) return new ShadowFloat((float) getValue());
    if (type.equals(Type.DOUBLE)) return this;
    if (type.equals(Type.CODE)) return new ShadowCode(integer.intValue());
    return this;
  }

  @Override
  public ShadowDouble add(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowDouble) {
      ShadowDouble input = (ShadowDouble) other;
      return new ShadowDouble(value + input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowDouble subtract(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowDouble) {
      ShadowDouble input = (ShadowDouble) other;
      return new ShadowDouble(value - input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowDouble multiply(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowDouble) {
      ShadowDouble input = (ShadowDouble) other;
      return new ShadowDouble(value * input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowDouble divide(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowDouble) {
      ShadowDouble input = (ShadowDouble) other;
      return new ShadowDouble(value / input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowDouble modulus(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowDouble) {
      ShadowDouble input = (ShadowDouble) other;
      return new ShadowDouble(value % input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowBoolean equal(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowDouble) {
      ShadowDouble input = (ShadowDouble) other;
      return new ShadowBoolean(value == input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowBoolean lessThan(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowDouble) {
      ShadowDouble input = (ShadowDouble) other;
      return new ShadowBoolean(value <= input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowBoolean lessThanOrEqual(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowDouble) {
      ShadowDouble input = (ShadowDouble) other;
      return new ShadowBoolean(value <= input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowBoolean greaterThan(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowDouble) {
      ShadowDouble input = (ShadowDouble) other;
      return new ShadowBoolean(value > input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowBoolean greaterThanOrEqual(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowDouble) {
      ShadowDouble input = (ShadowDouble) other;
      return new ShadowBoolean(value >= input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowInteger hash() throws InterpreterException {
    return new ShadowInteger(BigInteger.valueOf(Double.doubleToLongBits(getValue())), 8, false)
        .hash();
  }

  @Override
  public ShadowValue copy(Map<ShadowValue, ShadowValue> newValues) throws InterpreterException {
    return new ShadowDouble(getValue());
  }

  @Override
  public String toLiteral() {
    double value = getValue();

    // These special values cannot be represented as a single literal
    if (Double.isNaN(value)) {
      return "0d / 0d";
    } else if (Double.isInfinite(value)) {
      if (value > 0) {
        return "1d / 0d";
      } else {
        return "-1d / 0d";
      }
    }

    return Double.toString(getValue());
  }

  @Override
  public String toString() {
    return toLiteral();
  }

  @Override
  public ShadowDouble abs() throws InterpreterException {
    return new ShadowDouble(Math.abs(value));
  }

  @Override
  public ShadowDouble cos() throws InterpreterException {
    return new ShadowDouble(Math.cos(value));
  }

  @Override
  public ShadowDouble sin() throws InterpreterException {
    return new ShadowDouble(Math.sin(value));
  }

  @Override
  public ShadowDouble power(ShadowNumber number) throws InterpreterException {
    double exponent = ((ShadowDouble) number.cast(Type.DOUBLE)).getValue();
    return new ShadowDouble(Math.pow(value, exponent));
  }

  @Override
  public ShadowDouble squareRoot() throws InterpreterException {
    return new ShadowDouble(Math.sqrt(value));
  }

  @Override
  public ShadowDouble logBase10() throws InterpreterException {
    return new ShadowDouble(Math.log10(value));
  }

  @Override
  public ShadowDouble logBase2() throws InterpreterException {
    return new ShadowDouble(Math.log(value) / Math.log(2.0));
  }

  @Override
  public ShadowDouble logBaseE() throws InterpreterException {
    return new ShadowDouble(Math.log(value));
  }

  @Override
  public ShadowDouble max(ShadowNumber number) throws InterpreterException {
    double other = ((ShadowDouble) number.cast(Type.DOUBLE)).getValue();
    return new ShadowDouble(Math.max(value, other));
  }

  @Override
  public ShadowDouble min(ShadowNumber number) throws InterpreterException {
    double other = ((ShadowDouble) number.cast(Type.DOUBLE)).getValue();
    return new ShadowDouble(Math.min(value, other));
  }

  public ShadowDouble floor() throws InterpreterException {
    return new ShadowDouble(Math.floor(value));
  }

  public ShadowDouble ceiling() throws InterpreterException {
    return new ShadowDouble(Math.ceil(value));
  }

  public ShadowDouble round() {
    return new ShadowDouble(Math.round(value));
  }

  public static ShadowDouble parseDouble(String string) {
    return new ShadowDouble(Double.parseDouble(string));
  }
}
