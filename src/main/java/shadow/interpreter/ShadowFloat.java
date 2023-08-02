package shadow.interpreter;

import shadow.interpreter.InterpreterException.Error;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

import java.math.BigInteger;
import java.util.Map;

public class ShadowFloat extends ShadowNumeric {
  private final float value;

  public ShadowFloat(float value) {
    super(Modifiers.IMMUTABLE);
    this.value = value;
  }

  @Override
  public Type getType() {
    return Type.FLOAT;
  }

  public float getValue() {
    return value;
  }

  @Override
  public ShadowValue[] callMethod(String method, ShadowValue... arguments)
      throws InterpreterException {
    if (arguments.length == 0) {
      switch (method) {
        case "ceiling":
          return new ShadowFloat[] {ceiling()};
        case "floor":
          return new ShadowFloat[] {floor()};
        case "round":
          return new ShadowFloat[] {round()};
      }
    }

    return super.callMethod(method, arguments);
  }

  @Override
  public ShadowFloat negate() throws InterpreterException {
    return new ShadowFloat(-value);
  }

  @Override
  public ShadowValue cast(Type type) throws InterpreterException {
    BigInteger integer = BigInteger.valueOf(Math.round((double) value));

    if (type.equals(Type.BYTE)) return new ShadowInteger(integer, 1, true);
    if (type.equals(Type.SHORT)) return new ShadowInteger(integer, 2, true);
    if (type.equals(Type.INT)) return new ShadowInteger(integer, 4, true);
    if (type.equals(Type.LONG)) return new ShadowInteger(integer, 8, true);
    if (type.equals(Type.UBYTE)) return new ShadowInteger(integer, 1, false);
    if (type.equals(Type.USHORT)) return new ShadowInteger(integer, 2, false);
    if (type.equals(Type.UINT)) return new ShadowInteger(integer, 4, false);
    if (type.equals(Type.ULONG)) return new ShadowInteger(integer, 8, false);
    if (type.equals(Type.FLOAT)) return this;
    if (type.equals(Type.DOUBLE)) return new ShadowDouble(getValue());
    if (type.equals(Type.CODE)) return new ShadowCode(integer.intValue());

    throw new UnsupportedOperationException("Cannot cast " + getType() + " to " + type);
  }

  @Override
  public ShadowFloat add(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowFloat input) {
      return new ShadowFloat(value + input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowFloat subtract(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowFloat input) {
      return new ShadowFloat(value - input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowFloat multiply(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowFloat input) {
      return new ShadowFloat(value * input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowFloat divide(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowFloat input) {
      return new ShadowFloat(value / input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowFloat modulus(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowFloat input) {
      return new ShadowFloat(value % input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowBoolean equal(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowFloat input) {
      return new ShadowBoolean(value == input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowBoolean lessThan(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowFloat input) {
      return new ShadowBoolean(value <= input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowBoolean lessThanOrEqual(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowFloat input) {
      return new ShadowBoolean(value <= input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowBoolean greaterThan(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowFloat input) {
      return new ShadowBoolean(value > input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowBoolean greaterThanOrEqual(ShadowValue other) throws InterpreterException {
    if (other instanceof ShadowFloat input) {
      return new ShadowBoolean(value >= input.value);
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Type " + getType() + " does not match " + other.getType());
  }

  @Override
  public ShadowInteger hash() throws InterpreterException {
    return new ShadowInteger(BigInteger.valueOf(Float.floatToIntBits(getValue())), 4, false);
  }

  @Override
  public ShadowValue copy(Map<ShadowValue, ShadowValue> newValues) throws InterpreterException {
    return new ShadowFloat(getValue());
  }

  @Override
  public String toLiteral() {
    float value = getValue();

    // These special values cannot be represented as a single literal
    if (Float.isNaN(value)) {
      return "0f / 0f";
    } else if (Float.isInfinite(value)) {
      if (value > 0) {
        return "1f / 0f";
      } else {
        return "-1f / 0f";
      }
    }

    return value + "f";
  }

  @Override
  public String toString() {
    return toLiteral();
  }

  @Override
  public ShadowFloat abs() throws InterpreterException {
    return new ShadowFloat(Math.abs(value));
  }

  @Override
  public ShadowFloat cos() throws InterpreterException {
    return new ShadowFloat((float) Math.cos(value));
  }

  @Override
  public ShadowFloat sin() throws InterpreterException {
    return new ShadowFloat((float) Math.sin(value));
  }

  @Override
  public ShadowFloat power(ShadowNumber number) throws InterpreterException {
    double exponent = ((ShadowDouble) number.cast(Type.DOUBLE)).getValue();
    return new ShadowFloat((float) Math.pow(value, exponent));
  }

  @Override
  public ShadowFloat squareRoot() throws InterpreterException {
    return new ShadowFloat((float) Math.sqrt(value));
  }

  @Override
  public ShadowFloat logBase10() throws InterpreterException {
    return new ShadowFloat((float) Math.log10(value));
  }

  @Override
  public ShadowFloat logBase2() throws InterpreterException {
    return new ShadowFloat((float) (Math.log(value) / Math.log(2.0)));
  }

  @Override
  public ShadowFloat logBaseE() throws InterpreterException {
    return new ShadowFloat((float) Math.log(value));
  }

  @Override
  public ShadowFloat max(ShadowNumber number) throws InterpreterException {
    float other = ((ShadowFloat) number.cast(Type.FLOAT)).getValue();
    return new ShadowFloat(Math.max(value, other));
  }

  @Override
  public ShadowFloat min(ShadowNumber number) throws InterpreterException {
    float other = ((ShadowFloat) number.cast(Type.FLOAT)).getValue();
    return new ShadowFloat(Math.min(value, other));
  }

  public ShadowFloat floor() throws InterpreterException {
    return new ShadowFloat((float) Math.floor(value));
  }

  public ShadowFloat ceiling() throws InterpreterException {
    return new ShadowFloat((float) Math.ceil(value));
  }

  public ShadowFloat round() {
    return new ShadowFloat(Math.round(value));
  }

  public static ShadowFloat parseFloat(String string) {
    return new ShadowFloat(Float.parseFloat(string));
  }
}
