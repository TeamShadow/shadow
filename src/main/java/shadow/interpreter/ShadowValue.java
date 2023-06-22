package shadow.interpreter;

import shadow.interpreter.InterpreterException.Error;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

import java.math.BigInteger;
import java.util.Map;

/**
 * An abstract class that is the base of all values (array, boolean, code, number, object, string)
 * in Shadow.
 */
public abstract class ShadowValue implements ModifiedType {

  // Exposed here for convenience
  @SuppressWarnings("StaticInitializerReferencesSubClass")
  public static final ShadowInvalid INVALID = ShadowInvalid.INVALID;

  private final Modifiers modifiers;

  protected ShadowValue() {
    this.modifiers = new Modifiers();
  }

  protected ShadowValue(int modifiers) {
    this.modifiers = new Modifiers(modifiers);
  }

  @Override
  public Modifiers getModifiers() {
    return modifiers;
  }

  @Override
  public final void setType(Type type) {
    throw new UnsupportedOperationException();
  }

  /** Applies the specified binary operation to this {@link ShadowValue} and another */
  public final ShadowValue apply(BinaryOperator operator, ShadowValue right)
      throws InterpreterException {
    if (this instanceof ShadowInvalid || right instanceof ShadowInvalid) {
      return INVALID;
    }

    switch (operator) {
      case COALESCE:
        return coalesce(right);
      case OR:
        return or(right);
      case XOR:
        return xor(right);
      case AND:
        return and(right);
      case BITWISE_OR:
        return bitwiseOr(right);
      case BITWISE_XOR:
        return bitwiseXor(right);
      case BITWISE_AND:
        return bitwiseAnd(right);
      case EQUAL:
        return equal(right);
      case NOT_EQUAL:
        return notEqual(right);
      case REFERENCE_EQUAL:
        return referenceEqual(right);
      case REFERENCE_NOT_EQUAL:
        return referenceNotEqual(right);
      case LESS_THAN:
        return lessThan(right);
      case GREATER_THAN:
        return greaterThan(right);
      case LESS_THAN_OR_EQUAL:
        return lessThanOrEqual(right);
      case GREATER_THAN_OR_EQUAL:
        return greaterThanOrEqual(right);
      case CAT:
        return cat(right);
      case RIGHT_SHIFT:
        return bitShiftRight(right);
      case LEFT_SHIFT:
        return bitShiftLeft(right);
      case RIGHT_ROTATE:
        return bitRotateRight(right);
      case LEFT_ROTATE:
        return bitRotateLeft(right);
      case ADD:
        return add(right);
      case SUBTRACT:
        return subtract(right);
      case MULTIPLY:
        return multiply(right);
      case DIVIDE:
        return divide(right);
      case MODULUS:
        return modulus(right);
      default:
        throw new InterpreterException(
            Error.UNSUPPORTED_OPERATION, "Unexpected binary operator " + operator.getName());
    }
  }

  /** Applies the specified unary operation to this {@link ShadowValue} */
  public final ShadowValue apply(UnaryOperator operator) throws InterpreterException {
    if (this instanceof ShadowInvalid) {
      return INVALID;
    }

    switch (operator) {
      case CAT:
        return unaryCat();
      case BITWISE_COMPLEMENT:
        return bitwiseComplement();
      case NOT:
        return not();
      case NEGATE:
        return negate();
      default:
        throw new UnsupportedOperationException("Unexpected unary operator " + operator.getName());
    }
  }

  public ShadowString unaryCat() {
    return new ShadowString(toString());
  }

  public ShadowValue negate() throws InterpreterException {
    throw new InterpreterException(Error.UNSUPPORTED_OPERATION, "Negate operation not supported");
  }

  public ShadowValue bitwiseComplement() throws InterpreterException {
    throw new InterpreterException(
        Error.UNSUPPORTED_OPERATION, "Bitwise complement operation not supported");
  }

  public ShadowBoolean not() throws InterpreterException {
    throw new InterpreterException(Error.UNSUPPORTED_OPERATION, "Not operation not supported");
  }

  public final ShadowValue coalesce(ShadowValue value) throws InterpreterException {
    return (this instanceof ShadowNull) ? value : this;
  }

  public ShadowValue cat(ShadowValue value) throws InterpreterException {
    return new ShadowString(this + value.toString());
  }

  // binary operations
  public ShadowValue add(ShadowValue value) throws InterpreterException {
    throw new InterpreterException(Error.UNSUPPORTED_OPERATION, "Add operation not supported");
  }

  public ShadowValue subtract(ShadowValue value) throws InterpreterException {
    throw new InterpreterException(Error.UNSUPPORTED_OPERATION, "Subtract operation not supported");
  }

  public ShadowValue multiply(ShadowValue value) throws InterpreterException {
    throw new InterpreterException(Error.UNSUPPORTED_OPERATION, "Multiply operation not supported");
  }

  public ShadowValue divide(ShadowValue value) throws InterpreterException {
    throw new InterpreterException(Error.UNSUPPORTED_OPERATION, "Divide operation not supported");
  }

  public ShadowValue modulus(ShadowValue value) throws InterpreterException {
    throw new InterpreterException(Error.UNSUPPORTED_OPERATION, "Modulus operation not supported");
  }

  public ShadowValue bitShiftLeft(ShadowValue value) throws InterpreterException {
    throw new InterpreterException(
        Error.UNSUPPORTED_OPERATION, "Left shift operation not supported");
  }

  public ShadowValue bitShiftRight(ShadowValue value) throws InterpreterException {
    throw new InterpreterException(
        Error.UNSUPPORTED_OPERATION, "Right shift operation not supported");
  }

  public ShadowValue bitRotateLeft(ShadowValue value) throws InterpreterException {
    throw new InterpreterException(
        Error.UNSUPPORTED_OPERATION, "Left rotate operation not supported");
  }

  public ShadowValue bitRotateRight(ShadowValue value) throws InterpreterException {
    throw new InterpreterException(
        Error.UNSUPPORTED_OPERATION, "Right rotate operation not supported");
  }

  public ShadowBoolean equal(ShadowValue value) throws InterpreterException {
    throw new InterpreterException(Error.UNSUPPORTED_OPERATION, "Equal operation not supported");
  }

  public final ShadowBoolean notEqual(ShadowValue value) throws InterpreterException {
    try {
      final ShadowBoolean result = equal(value);
      return new ShadowBoolean(!result.getValue());
    } catch (Exception e) {
      throw new InterpreterException(
          Error.UNSUPPORTED_OPERATION, "Not equal operation not supported");
    }
  }

  @SuppressWarnings("unused")
  public ShadowBoolean referenceEqual(ShadowValue value) throws InterpreterException {
    throw new InterpreterException(
        Error.UNSUPPORTED_OPERATION, "Reference equal operation not supported");
  }

  public final ShadowBoolean referenceNotEqual(ShadowValue value) throws InterpreterException {
    try {
      ShadowBoolean result = this.referenceEqual(value);
      return new ShadowBoolean(!result.getValue());
    } catch (Exception e) {
      throw new InterpreterException(
          Error.UNSUPPORTED_OPERATION, "Reference not equal operation not supported");
    }
  }

  public ShadowBoolean lessThan(ShadowValue value) throws InterpreterException {
    throw new InterpreterException(
        Error.UNSUPPORTED_OPERATION, "Less than operation not supported");
  }

  public ShadowBoolean lessThanOrEqual(ShadowValue value) throws InterpreterException {
    throw new InterpreterException(
        Error.UNSUPPORTED_OPERATION, "Less than or equal operation not supported");
  }

  public ShadowBoolean greaterThan(ShadowValue value) throws InterpreterException {
    throw new InterpreterException(
        Error.UNSUPPORTED_OPERATION, "Greater than operation not supported");
  }

  public ShadowBoolean greaterThanOrEqual(ShadowValue value) throws InterpreterException {
    throw new InterpreterException(
        Error.UNSUPPORTED_OPERATION, "Greater than or equal operation not supported");
  }

  @SuppressWarnings("unused")
  public ShadowBoolean or(ShadowValue value) throws InterpreterException {
    throw new InterpreterException(Error.UNSUPPORTED_OPERATION, "Or operation not supported");
  }

  @SuppressWarnings("unused")
  public ShadowBoolean xor(ShadowValue value) throws InterpreterException {
    throw new InterpreterException(
        Error.UNSUPPORTED_OPERATION, "Exclusive or operation not supported");
  }

  @SuppressWarnings("unused")
  public ShadowBoolean and(ShadowValue value) throws InterpreterException {
    throw new InterpreterException(Error.UNSUPPORTED_OPERATION, "And operation not supported");
  }

  public ShadowValue bitwiseAnd(ShadowValue value) throws InterpreterException {
    throw new InterpreterException(
        Error.UNSUPPORTED_OPERATION, "Bitwise and operation not supported");
  }

  public ShadowValue bitwiseOr(ShadowValue value) throws InterpreterException {
    throw new InterpreterException(
        Error.UNSUPPORTED_OPERATION, "Bitwise or operation not supported");
  }

  public ShadowValue bitwiseXor(ShadowValue value) throws InterpreterException {
    throw new InterpreterException(
        Error.UNSUPPORTED_OPERATION, "Bitwise xor operation not supported");
  }

  public final boolean isSubtype(ShadowValue other) {
    return getType().isSubtype(other.getType());
  }

  public final boolean isStrictSubtype(ShadowValue other) {
    return getType().isStrictSubtype(other.getType());
  }

  public abstract ShadowValue cast(Type type) throws InterpreterException;

  public abstract ShadowValue copy(Map<ShadowValue, ShadowValue> newValues) throws InterpreterException;

  /**
   * Sets the {@link Modifiers} immutable flag on the value.
   *
   * @return a copy of the value, or this if it is already immutable.
   * @throws InterpreterException thrown if impossible to determine value
   */
  public ShadowValue freeze(Map<ShadowValue, ShadowValue> newValues) throws InterpreterException {
    if (getModifiers().isImmutable()) return this;

    // One might ask: Why make a copy here?
    // Answer: Because an immutable value can't be changed
    // which would be possible when there are other references to the original
    final ShadowValue copy = copy(newValues);

    copy.getModifiers().addModifier(Modifiers.IMMUTABLE);

    return copy;
  }

  @Override
  public String toString() {
    return getType().toString(Type.PACKAGES | Type.TYPE_PARAMETERS);
  }

  /**
   * Returns a "default" value for a type if it's supported: array or nullable
   *
   * @param modifiedType the {@link ModifiedType} to get the default value for.
   * @return the default value.
   * @throws InterpreterException thrown if no default value is supported
   */
  public static ShadowValue getDefault(ModifiedType modifiedType) throws InterpreterException {
    if (modifiedType.getModifiers().isNullable()) return new ShadowNull(modifiedType.getType());

    Type type = modifiedType.getType();

    if (type.equals(Type.BOOLEAN))
      return new ShadowBoolean(false);
    else if(type.equals(Type.CODE))
      return new ShadowCode(0);
    else if(type.equals(Type.DOUBLE))
      return new ShadowDouble(0.0);
    else if(type.equals(Type.FLOAT))
      return new ShadowFloat(0.0f);
    else if(type.equals(Type.BYTE))
      return new ShadowInteger(BigInteger.valueOf(0), 1, true);
    else if(type.equals(Type.SHORT))
      return new ShadowInteger(BigInteger.valueOf(0), 2, true);
    else if(type.equals(Type.INT))
      return new ShadowInteger(0);
    else if(type.equals(Type.LONG))
      return new ShadowInteger(0L);
    else if(type.equals(Type.UBYTE))
      return new ShadowInteger(BigInteger.valueOf(0), 1, false);
    else if(type.equals(Type.USHORT))
      return new ShadowInteger(BigInteger.valueOf(0), 2, false);
    else if(type.equals(Type.UINT))
      return new ShadowInteger(BigInteger.valueOf(0), 4, false);
    else if(type.equals(Type.ULONG))
      return new ShadowInteger(BigInteger.valueOf(0), 8, false);

    throw new InterpreterException(Error.INVALID_TYPE, "Unsupported type " + type);
  }

  public ShadowInteger hash() throws InterpreterException {
    throw new InterpreterException(Error.UNSUPPORTED_OPERATION, "Hash not supported");
  }

  /**
   * Checks to see if two values are equal.
   *
   * @param second the other value to compare.
   * @return true if they are "equal", or false otherwise.
   * @throws InterpreterException thrown if comparison is impossible
   */
  public boolean equals(ShadowValue second) throws InterpreterException {
    ShadowValue first = this;

    if (first.isStrictSubtype(second)) first = first.cast(second.getType());
    else if (second.isStrictSubtype(first)) second = second.cast(first.getType());

    if ((first instanceof ShadowUndefined) != (second instanceof ShadowUndefined)) return false;

    if (first.getType().equals(second.getType())) {
      if (first instanceof ShadowInteger) {
        BigInteger value1 = ((ShadowInteger) first).getValue();
        BigInteger value2 = ((ShadowInteger) second).getValue();
        return value1.equals(value2);
      } else if (first instanceof ShadowFloat) {
        float value1 = ((ShadowFloat) first).getValue();
        float value2 = ((ShadowFloat) second).getValue();
        return value1 == value2;
      } else if (first instanceof ShadowDouble) {
        double value1 = ((ShadowDouble) first).getValue();
        double value2 = ((ShadowDouble) second).getValue();
        return value1 == value2;
      } else if (first instanceof ShadowString) {
        String value1 = ((ShadowString) first).getValue();
        String value2 = ((ShadowString) second).getValue();
        return value1.equals(value2);
      } else if (first instanceof ShadowUndefined) return true;
      else if (first instanceof ShadowNull) return second instanceof ShadowNull;
    }

    return false;
  }

  /**
   * Compares one value to another.
   *
   * @param second the other value to compare to.
   * @return same values as Java's compareTo
   * @throws InterpreterException thrown if comparison is impossible
   */
  public int compareTo(ShadowValue second) throws InterpreterException {
    ShadowValue first = this;

    if (first.isStrictSubtype(second)) first = first.cast(second.getType());
    else if (second.isStrictSubtype(first)) second = second.cast(first.getType());

    if (first.getType().equals(second.getType())) {
      if (first instanceof ShadowInteger) {
        BigInteger value1 = ((ShadowInteger) first).getValue();
        BigInteger value2 = ((ShadowInteger) second).getValue();
        return value1.compareTo(value2);
      } else if (first instanceof ShadowFloat) {
        float value1 = ((ShadowFloat) first).getValue();
        float value2 = ((ShadowFloat) second).getValue();
        return Float.compare(value1, value2);
      } else if (first instanceof ShadowDouble) {
        double value1 = ((ShadowDouble) first).getValue();
        double value2 = ((ShadowDouble) second).getValue();
        return Double.compare(value1, value2);
      } else if (first instanceof ShadowString) {
        String value1 = ((ShadowString) first).getValue();
        String value2 = ((ShadowString) second).getValue();
        return value1.compareTo(value2);
      }
    }

    throw new InterpreterException(
        Error.MISMATCHED_TYPE,
        "Cannot compare types " + first.getType() + " and " + second.getType());
  }

  /** Returns a valid Shadow literal representation of the value */
  public abstract String toLiteral();

  public ShadowValue[] callMethod(String method, ShadowValue... arguments)
      throws InterpreterException {
    if (arguments.length == 0) {
      switch (method) {
        case "bitwiseComplement":
          return new ShadowValue[]{bitwiseComplement()};
        case "hash":
          return new ShadowInteger[]{hash()};
        case "negate":
          return new ShadowValue[]{negate()};
        case "not":
          return new ShadowBoolean[]{not()};
        case "toString":
          return new ShadowString[]{new ShadowString(toLiteral())};
      }
    } else if (arguments.length == 1) {
      ShadowValue value = arguments[0];
      switch (method) {
        case "add":
          return new ShadowValue[]{add(value)};
        case "and":
          return new ShadowBoolean[]{and(value)};
        case "bitRotateLeft":
          return new ShadowValue[]{bitRotateLeft(value)};
        case "bitRotateRight":
          return new ShadowValue[]{bitRotateRight(value)};
        case "bitShiftLeft":
          return new ShadowValue[]{bitShiftLeft(value)};
        case "bitShiftRight":
          return new ShadowValue[]{bitShiftRight(value)};
        case "bitwiseAnd":
          return new ShadowValue[]{bitwiseAnd(value)};
        case "bitwiseOr":
          return new ShadowValue[]{bitwiseOr(value)};
        case "bitwiseXor":
          return new ShadowValue[]{bitwiseXor(value)};
        case "compareTo":
          return new ShadowInteger[]{new ShadowInteger(compareTo(value))};
        case "divide":
          return new ShadowValue[]{divide(value)};
        case "equal":
          return new ShadowBoolean[]{equal(value)};
        case "greaterThan":
          return new ShadowBoolean[]{greaterThan(value)};
        case "greaterThanOrEqual":
          return new ShadowBoolean[]{greaterThanOrEqual(value)};
        case "lessThan":
          return new ShadowBoolean[]{lessThan(value)};
        case "lessThanOrEqual":
          return new ShadowBoolean[]{lessThanOrEqual(value)};
        case "modulus":
          return new ShadowValue[]{modulus(value)};
        case "multiply":
          return new ShadowValue[]{multiply(value)};
        case "notEqual":
          return new ShadowBoolean[]{notEqual(value)};
        case "or":
          return new ShadowBoolean[]{or(value)};
        case "referenceEqual":
          return new ShadowBoolean[]{referenceEqual(value)};
        case "referenceNotEqual":
          return new ShadowBoolean[]{referenceNotEqual(value)};
        case "subtract":
          return new ShadowValue[]{subtract(value)};
        case "xor":
          return new ShadowBoolean[]{xor(value)};
      }
    }

    StringBuilder builder = new StringBuilder("(");
    boolean first = true;
    for (ShadowValue value : arguments) {
      if (first) first = false;
      else builder.append(", ");
      builder.append(value);
    }
    builder.append(")");
    throw new InterpreterException(
        Error.UNSUPPORTED_OPERATION,
        "Method " + method + " not supported with arguments " + builder);
  }
}
