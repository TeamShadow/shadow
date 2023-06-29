package shadow.interpreter;

import shadow.interpreter.InterpreterException.Error;
import shadow.tac.nodes.TACCall;
import shadow.tac.nodes.TACLiteral;
import shadow.tac.nodes.TACMethodName;
import shadow.tac.nodes.TACOperand;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.Type;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ShadowString extends ShadowObject {
  private static final Charset UTF8 = StandardCharsets.UTF_8;
  private final String value;

  public ShadowString(String value) {
    super(Type.STRING, makeObject(), new HashMap<>());
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public ShadowValue convert(Type type) throws InterpreterException {
    if (type.equals(Type.STRING)) return this;

    if (type.equals(Type.BYTE)) return new ShadowInteger(new BigInteger(value), 1, true);
    else if (type.equals(Type.SHORT)) return new ShadowInteger(new BigInteger(value), 2, true);
    else if (type.equals(Type.INT)) return new ShadowInteger(new BigInteger(value), 4, true);
    else if (type.equals(Type.LONG)) return new ShadowInteger(new BigInteger(value), 8, true);
    else if (type.equals(Type.UBYTE)) return new ShadowInteger(new BigInteger(value), 1, false);
    else if (type.equals(Type.USHORT)) return new ShadowInteger(new BigInteger(value), 2, false);
    else if (type.equals(Type.UINT)) return new ShadowInteger(new BigInteger(value), 4, false);
    else if (type.equals(Type.ULONG)) return new ShadowInteger(new BigInteger(value), 8, false);
    else if (type.equals(Type.DOUBLE)) return new ShadowDouble(Double.parseDouble(value));
    else if (type.equals(Type.FLOAT)) return new ShadowFloat(Float.parseFloat(value));

    throw new InterpreterException(
        Error.MISMATCHED_TYPE, "Cannot convert type " + Type.STRING + " to " + type);
  }

  @Override
  public ShadowObject copy(Map<ShadowValue, ShadowValue> newValues) throws InterpreterException {
    return new ShadowString(getValue());
  }

  @Override
  public ShadowInteger hash() throws InterpreterException {
    int code = 0;
    byte[] data = value.getBytes(StandardCharsets.UTF_8);

    for (byte datum : data) {
      code *= 31;
      code += Math.abs(datum);
    }

    return new ShadowInteger(BigInteger.valueOf(code), 8, false);
  }

  @Override
  public String toLiteral() {
    return "\"" + escape(getValue()) + "\"";
  }

  /* From: https://stackoverflow.com/questions/2406121/how-do-i-escape-a-string-in-java
   * Alternative: https://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/StringEscapeUtils.html#escapeJava-java.lang.String-
   * I'm hesitant to use the latter method because it would requires Apache commons-text for a single method.
   */
  public static String escape(String input) {
    return input
        .replace("\\", "\\\\")
        .replace("\t", "\\t")
        .replace("\b", "\\b")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\f", "\\f")
        .replace("'", "\\'")
        .replace("\"", "\\\"");
  }

  @Override
  public String toString() {
    return getValue();
  }

  public static ShadowString parseString(String string) {
    StringBuilder builder = new StringBuilder(string.substring(1, string.length() - 1));
    int index = 0;
    while ((index = builder.indexOf("\\", index)) != -1) {
      switch (builder.charAt(index + 1)) {
        case 'b':
          builder.replace(index, index + 2, "\b");
          break;
        case 't':
          builder.replace(index, index + 2, "\t");
          break;
        case 'n':
          builder.replace(index, index + 2, "\n");
          break;
        case 'f':
          builder.replace(index, index + 2, "\f");
          break;
        case 'r':
          builder.replace(index, index + 2, "\r");
          break;
        case '\"':
          builder.replace(index, index + 2, "\"");
          break;
        case '\'':
          builder.replace(index, index + 2, "'");
          break;
        case '\\':
          builder.replace(index, index + 2, "\\");
        case 'u':
          // add in high surrogates and full 32-bit values at some point
          char code = (char) Integer.parseInt(builder.substring(index + 2, index + 6), 16);
          builder.replace(index, index + 6, String.valueOf(code));
          break;
        default:
          throw new IllegalArgumentException(
              "Unknown escape sequence \\" + builder.charAt(index + 1));
      }
      index++;
    }
    return new ShadowString(builder.toString());
  }

  public static boolean isSupportedMethod(MethodSignature signature) {
    if (signature.getOuter().equals(Type.STRING)) {
      switch (signature.getSymbol()) {
        case "index":
        case "size":
        case "isEmpty":
        case "substring":
        case "toLowerCase":
        case "toUpperCase":
        case "equal":
        case "compare":
        case "toString":
        case "hash":
        case "toByte":
        case "toUByte":
        case "toShort":
        case "toUShort":
        case "toInt":
        case "toUInt":
        case "toLong":
        case "toULong":
        case "toFloat":
        case "toDouble":
          return true;

        case "concatenate":
          return signature.getParameterTypes().get(0).getType().equals(Type.STRING);
      }
    }

    return false;
  }

  @Override
  public ShadowValue[] callMethod(String method, ShadowValue... arguments)
      throws InterpreterException {
    if (arguments.length == 0) {
      switch (method) {
        case "size":
          return new ShadowInteger[]{new ShadowInteger(value.length())};
        case "isEmpty":
          return new ShadowBoolean[]{new ShadowBoolean(value.isEmpty())};
        case "toLowerCase":
          return new ShadowString[]{new ShadowString(value.toLowerCase())};
        case "toUpperCase":
          return new ShadowString[]{new ShadowString(value.toUpperCase())};
        case "toString":
          return new ShadowString[]{this};
        case "toByte":
          return new ShadowValue[]{convert(Type.BYTE)};
        case "toUByte":
          return new ShadowValue[]{convert(Type.UBYTE)};
        case "toShort":
          return new ShadowValue[]{convert(Type.SHORT)};
        case "toUShort":
          return new ShadowValue[]{convert(Type.USHORT)};
        case "toInt":
          return new ShadowValue[]{convert(Type.INT)};
        case "toUInt":
          return new ShadowValue[]{convert(Type.UINT)};
        case "toLong":
          return new ShadowValue[]{convert(Type.LONG)};
        case "toULong":
          return new ShadowValue[]{convert(Type.ULONG)};
        case "toFloat":
          return new ShadowValue[]{convert(Type.FLOAT)};
        case "toDouble":
          return new ShadowValue[]{convert(Type.DOUBLE)};
      }
    } else if (arguments.length == 1) {
      switch (method) {
        case "index":
          {
            if (arguments[0] instanceof ShadowInteger) {
              int index = ((ShadowInteger) arguments[0]).getValue().intValue();
              return new ShadowInteger[]{new ShadowInteger(BigInteger.valueOf(value.getBytes(UTF8)[index]), 1, false)};
            }
          }
        case "substring":
          if (arguments[0] instanceof ShadowInteger) {
            byte[] bytes = value.getBytes(UTF8);
            int firstIndex = ((ShadowInteger) arguments[0]).getValue().intValue();
            int secondIndex = bytes.length;
            bytes = Arrays.copyOfRange(bytes, firstIndex, secondIndex);
            return new ShadowString[]{new ShadowString(new String(bytes, UTF8))};
          }
        case "concatenate":
          return new ShadowString[]{new ShadowString(value + arguments[0].unaryCat().getValue())};
      }
    } else if (arguments.length == 2) {
      if ("substring".equals(method)) {
        if (arguments[0] instanceof ShadowInteger && arguments[1] instanceof ShadowInteger) {
          byte[] bytes = value.getBytes(UTF8);
          int firstIndex = ((ShadowInteger) arguments[0]).getValue().intValue();
          int secondIndex = ((ShadowInteger) arguments[1]).getValue().intValue();
          bytes = Arrays.copyOfRange(bytes, firstIndex, secondIndex);
          return new ShadowString[]{new ShadowString(new String(bytes, UTF8))};
        }
      }
    }

    return super.callMethod(method, arguments);
  }

  public ShadowValue callMethod(TACCall call) throws InterpreterException {
    MethodSignature signature = ((TACMethodName) call.getMethodRef()).getSignature();

    try {
      List<TACOperand> operands = call.getParameters();
      List<ShadowValue> parameters = new ArrayList<>(operands.size() - 1);

      for (int i = 1; i < operands.size(); ++i) {
        TACOperand operand = TACOperand.value(operands.get(i));
        TACLiteral literal = (TACLiteral) operand;
        parameters.add(literal.getValue());
      }

      switch (signature.getSymbol()) {
        case "index":
          {
            int index = ((ShadowInteger) parameters.get(0)).getValue().intValue();
            return new ShadowInteger(BigInteger.valueOf(value.getBytes(UTF8)[index]), 1, false);
          }
        case "size":
          return new ShadowInteger(value.getBytes(UTF8).length);
        case "isEmpty":
          return new ShadowBoolean(value.isEmpty());
        case "substring":
          {
            byte[] bytes = value.getBytes(UTF8);
            int firstIndex = ((ShadowInteger) parameters.get(0)).getValue().intValue();
            int secondIndex = bytes.length;
            if (parameters.size() == 2)
              secondIndex = ((ShadowInteger) parameters.get(1)).getValue().intValue();
            bytes = Arrays.copyOfRange(bytes, firstIndex, secondIndex);
            return new ShadowString(new String(bytes, UTF8));
          }
        case "toLowerCase":
          return new ShadowString(value.toLowerCase());
        case "toUpperCase":
          return new ShadowString(value.toUpperCase());
        case "toString":
          return this;
        case "hash":
          return hash();
        case "toByte":
          return new ShadowInteger(new BigInteger(value), 1, true);
        case "toUByte":
          return new ShadowInteger(new BigInteger(value), 1, false);
        case "toShort":
          return new ShadowInteger(new BigInteger(value), 2, true);
        case "toUShort":
          return new ShadowInteger(new BigInteger(value), 2, false);
        case "toInt":
          return new ShadowInteger(new BigInteger(value), 4, true);
        case "toUInt":
          return new ShadowInteger(new BigInteger(value), 4, false);
        case "toLong":
          return new ShadowInteger(new BigInteger(value), 8, true);
        case "toULong":
          return new ShadowInteger(new BigInteger(value), 8, false);
        case "toFloat":
          return new ShadowFloat(Float.parseFloat(value));
        case "toDouble":
          return new ShadowDouble(Double.parseDouble(value));
        case "concatenate":
          return new ShadowString(value + ((ShadowString) parameters.get(0)).value);
      }
    }
    // Any problems should bring us down here to throw the ShadowException
    catch (Exception ignored) {
    }

    throw new InterpreterException(
        Error.UNSUPPORTED_OPERATION,
        "Evaluation of String method "
            + signature.getSymbol()
            + signature.getMethodType()
            + " failed");
  }
}
