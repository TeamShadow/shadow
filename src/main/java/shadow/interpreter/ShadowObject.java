package shadow.interpreter;

import shadow.Loggers;
import shadow.interpreter.InterpreterException.Error;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.type.*;

import java.util.*;

public class ShadowObject extends ShadowValue {
  private final ClassType type;
  private final Map<String, ShadowValue> fields;
  private final ShadowObject parent;

  public static ShadowObject makeObject() {
    return new ShadowObject(Type.OBJECT, null, new HashMap<>());
  }

  public ShadowObject(ClassType type, ShadowObject parent, Map<String, ShadowValue> fields) {
    if (type.isPrimitive())
      throw new RuntimeException("Cannot create an object with a primitive type");
    this.type = type;
    this.parent = parent;
    this.fields = fields;
  }

  public void setField(String name, ShadowValue value) throws InterpreterException {
    if (type.containsField(name)) {
      fields.put(name, value);
    } else if (parent != null) parent.setField(name, value);
    else
      throw new InterpreterException(
          Error.INVALID_FIELD, "Type " + type + " does not contain field " + name);
  }

  public ShadowValue getField(String name) throws InterpreterException {
    if (fields.containsKey(name)) return fields.get(name);

    if (parent != null) return parent.getField(name);

    throw new InterpreterException(
        Error.INVALID_FIELD, "Type " + type + " does not contain field " + name);
  }

  public Map<String, ShadowValue> getFields() {
    return Collections.unmodifiableMap(fields);
  }

  @Override
  public ClassType getType() {
    return type;
  }

  @Override
  public ShadowObject copy(Map<ShadowValue, ShadowValue> newValues) throws InterpreterException {
    if (newValues.containsKey(this)) return (ShadowObject) newValues.get(this);

    Map<String, ShadowValue> newFields = new HashMap<>();
    for (Map.Entry<String, ShadowValue> entry : fields.entrySet()) {
      newFields.put(entry.getKey(), entry.getValue().copy(newValues));
    }

    ShadowObject replacement =
        new ShadowObject(getType(), parent == null ? null : parent.copy(newValues), newFields);
    newValues.put(this, replacement);
    return replacement;
  }

  @Override
  public String toLiteral() {
    throw new UnsupportedOperationException("Cannot convert an arbitrary object to a literal");
  }

  @Override
  public ShadowValue cast(Type type) throws InterpreterException {
    if (type.equals(this.type)) return this;
    else if (type instanceof ClassType && (this.type.isSubtype(type) || type.isSubtype(this.type)))
      return new ShadowCast(this, (ClassType) type);
    throw new InterpreterException(Error.INVALID_CAST, "Cannot cast " + this.type + " to " + type);
  }

  @Override
  public ShadowValue[] callMethod(String method, ShadowValue... values)
      throws InterpreterException {

    SequenceType arguments = new SequenceType(Arrays.asList(values));
    MethodSignature signature = type.getMatchingMethod(method, arguments);
    if (signature == null)
      throw new InterpreterException(
          Error.INVALID_METHOD, "Method " + method + "." + arguments + " not interpretable");

    if (signature.getOuter().equals(Type.OBJECT) && values.length == 0) {
      switch (method) {
        case "getClass":
          return new ShadowClass[] {new ShadowClass(type)};
        case "toString":
          return new ShadowString[] {new ShadowClass(type).name()};
      }
    }

    return ASTInterpreter.callMethod(
        type.getPackage().getRoot(),
        new ErrorReporter(Loggers.AST_INTERPRETER),
        this,
        method,
        null,
        values);
  }

  public ShadowObject getParent() {
    return parent;
  }
}
