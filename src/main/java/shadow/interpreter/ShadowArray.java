package shadow.interpreter;

import shadow.Loggers;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.type.*;

import java.util.HashMap;
import java.util.Map;

public class ShadowArray extends ShadowObject {
  private final ArrayType type;
  private final ShadowValue[] data;

  private static Map<String, ShadowValue> makeFields(long length) {
    Map<String, ShadowValue> fields = new HashMap<>();
    fields.put("sizeLong", new ShadowInteger(length));
    return fields;
  }

  public ShadowArray(ArrayType type, long length) {
    super(type, ShadowObject.makeObject(), makeFields(length));
    this.type = type;
    this.data = new ShadowValue[(int)length];
  }

  @Override
  public ArrayType getType() {
    return this.type;
  }

  public ShadowValue get(int index) {
    return data[index];
  }

  public void set(int index, ShadowValue value) {
    data[index] = value;
  }

  public int getLength() {
    return data.length;
  }

  @Override
  public ShadowObject copy(Map<ShadowValue, ShadowValue> copies) throws InterpreterException {
    ShadowArray copy = new ShadowArray(getType(), data.length);
    for (int i = 0; i < data.length; ++i) copy.data[i] = data[i].copy(copies);
    return copy;
  }

  @Override
  public ShadowValue[] callMethod(String method, ShadowValue... values)
          throws InterpreterException {

    if (method.equals("index") && values.length >= 1) {
      ShadowInteger index = (ShadowInteger) values[0].cast(Type.INT);
      int location = index.getValue().intValue();

      if (location >= 0 && location < data.length) {
        if (values.length == 1) {
          return new ShadowValue[]{data[location]};
        }
        else if(values.length == 2) {
          data[location] = values[1];
          return new ShadowValue[]{data[location]};
        }
      }
      else
        throw new InterpreterException(InterpreterException.Error.INVALID_SUBSCRIPT, "Array out of bounds");
    }

    return ASTInterpreter.callMethod(type.getPackage().getRoot(), new ErrorReporter(Loggers.AST_INTERPRETER), this, method, null, values);
  }

  @Override
  public String toLiteral() {
    StringBuilder builder = new StringBuilder("{");
    for (int i = 0; i < data.length; ++i) {
      if (i > 0)
        builder.append(", ");
      if (data[i] == null)
        builder.append("null");
      else
        builder.append(data[i].toLiteral());
    }
    builder.append("}");
    return builder.toString();
  }
}
