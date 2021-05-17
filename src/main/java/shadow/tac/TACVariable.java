package shadow.tac;

import shadow.typecheck.type.*;

public class TACVariable implements ModifiedType {
  private final ModifiedType type;
  private final String name;
  private int suffix;
  private final TACMethod method;
  private boolean finallyVariable = false;

  public TACVariable(ModifiedType varType, String varName, TACMethod varMethod) {
    type = varType;
    name = varName;
    suffix = 0;
    method = varMethod;
  }

  public TACMethod getMethod() {
    return method;
  }

  public boolean hasType() {
    return type != null;
  }

  @Override
  public Modifiers getModifiers() {
    return type.getModifiers();
  }

  @Override
  public Type getType() {
    return type.getType();
  }

  @Override
  public void setType(Type newType) {
    type.setType(newType);
  }

  public ModifiedType getModifiedType() {
    return type;
  }

  public String getOriginalName() {
    return name;
  }

  public String getName() {
    if (suffix == 0) return name;
    return name + suffix;
  }

  protected void rename() {
    suffix++;
  }

  @Override
  public String toString() {
    if (!hasType()) return getName();
    return getType().toString() + ' ' + getName();
  }

  @Override
  public int hashCode() {
    return getName().hashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof TACVariable)) return false;

    if (other == this) return true;

    TACVariable var = (TACVariable) other;

    return getName().equals(var.getName());
  }

  public boolean isReturn() {
    return getOriginalName().equals("return") || getOriginalName().startsWith("_return");
  }

  public boolean isFinallyVariable() {
    return finallyVariable;
  }

  public void makeFinallyVariable() {
    finallyVariable = true;
  }

  public boolean needsGarbageCollection() {
    Type type = getType();

    return !getOriginalName().startsWith("_exception")
        && !getOriginalName().equals("this")
        && !(type instanceof PointerType)
        && !(type instanceof MethodType)
        && !(type instanceof SingletonType)
        && !type.equals(Type.CLASS)
        && !type.equals(Type.GENERIC_CLASS)
        && !type.equals(Type.METHOD_TABLE)
        && (!type.isPrimitive() || getModifiers().isNullable());
  }
}
