package shadow.interpreter;

import shadow.typecheck.type.ClassType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.Type;

import java.util.Map;

public class ShadowCast extends ShadowValue {
  private final ShadowValue value;
  private final ClassType type;

  public ShadowCast(ShadowValue value, ClassType type) {
    this.value = value;
    this.type = type;
  }

  @Override
  public ShadowCast copy(Map<ShadowValue, ShadowValue> copies) throws InterpreterException {
    return new ShadowCast(value.copy(copies), type);
  }

  @Override
  public String toLiteral() {
    if (value.getType().equals(type)) return value.toLiteral();
    else return "cast<" + type.toString() + ">(" + value.toLiteral() + ")";
  }

  public Modifiers getModifiers() {
    return value.getModifiers();
  }

  @Override
  public ShadowValue cast(Type type) throws InterpreterException {
    if (type.equals(this.type)) return this;
    else if (type instanceof ClassType && (this.type.isSubtype(type) || type.isSubtype(this.type)))
      return new ShadowCast(value, (ClassType) type);
    throw new InterpreterException(
        InterpreterException.Error.INVALID_CAST, "Cannot cast " + this.type + " to " + type);
  }

  @Override
  public ClassType getType() {
    return type;
  }
}
