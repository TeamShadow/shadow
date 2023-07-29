package shadow.interpreter;

import shadow.typecheck.type.Type;

public abstract class ShadowNumber extends ShadowValue {

  public ShadowNumber(int modifiers) {
    super(modifiers);
  }

  @Override
  public ShadowValue[] callMethod(String method, ShadowValue... arguments)
      throws InterpreterException {
    if (arguments.length == 0) {
      switch (method) {
        case "toByte":
          return new ShadowNumber[] {toByte()};
        case "toUByte":
          return new ShadowNumber[] {toUByte()};
        case "toShort":
          return new ShadowNumber[] {toShort()};
        case "toUShort":
          return new ShadowNumber[] {toUShort()};
        case "toInt":
          return new ShadowNumber[] {toInt()};
        case "toLong":
          return new ShadowNumber[] {toLong()};
        case "toULong":
          return new ShadowNumber[] {toULong()};
        case "toCode":
          return new ShadowNumber[] {toCode()};
        case "toFloat":
          return new ShadowNumber[] {toFloat()};
        case "toDouble":
          return new ShadowNumber[] {toDouble()};
      }
    } else if (arguments.length == 1 && arguments[0] instanceof ShadowNumber) {
      ShadowNumber number = (ShadowNumber) (arguments[0].cast(getType()));
      switch (method) {
        case "max":
          return new ShadowNumber[] {max(number)};
        case "min":
          return new ShadowNumber[] {min(number)};
      }
    }

    return super.callMethod(method, arguments);
  }

  public ShadowNumber toByte() throws InterpreterException {
    return (ShadowNumber) cast(Type.BYTE);
  }

  public ShadowNumber toUByte() throws InterpreterException {
    return (ShadowNumber) cast(Type.UBYTE);
  }

  public ShadowNumber toShort() throws InterpreterException {
    return (ShadowNumber) cast(Type.SHORT);
  }

  public ShadowNumber toUShort() throws InterpreterException {
    return (ShadowNumber) cast(Type.USHORT);
  }

  public ShadowNumber toInt() throws InterpreterException {
    return (ShadowNumber) cast(Type.INT);
  }

  public ShadowNumber toUInt() throws InterpreterException {
    return (ShadowNumber) cast(Type.UINT);
  }

  public ShadowNumber toLong() throws InterpreterException {
    return (ShadowNumber) cast(Type.LONG);
  }

  public ShadowNumber toULong() throws InterpreterException {
    return (ShadowNumber) cast(Type.ULONG);
  }

  public ShadowNumber toCode() throws InterpreterException {
    return (ShadowNumber) cast(Type.CODE);
  }

  public ShadowNumber toFloat() throws InterpreterException {
    return (ShadowNumber) cast(Type.FLOAT);
  }

  public ShadowNumber toDouble() throws InterpreterException {
    return (ShadowNumber) cast(Type.DOUBLE);
  }

  public ShadowNumber max(ShadowNumber number) throws InterpreterException {
    ShadowNumber first = this;
    ShadowNumber second = number;
    if (first.isStrictSubtype(second)) first = (ShadowNumber) first.cast(second.getType());
    else if (second.isStrictSubtype(first)) second = (ShadowNumber) second.cast(first.getType());

    if (first.compareTo(second) >= 0) return first;
    else return second;
  }

  public ShadowNumber min(ShadowNumber number) throws InterpreterException {
    ShadowNumber first = this;
    ShadowNumber second = number;
    if (first.isStrictSubtype(second)) first = (ShadowNumber) first.cast(second.getType());
    else if (second.isStrictSubtype(first)) second = (ShadowNumber) second.cast(first.getType());

    if (first.compareTo(second) <= 0) return first;
    else return second;
  }
}
