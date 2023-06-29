package shadow.interpreter;

import shadow.interpreter.InterpreterException.Error;
import shadow.typecheck.type.*;

import java.util.HashMap;

public class ShadowClass extends ShadowObject {
  private final Type representedType;


  public ShadowClass(Type representedType) throws InterpreterException {
    this(representedType, representedType.isParameterized());
  }

  private ShadowClass(Type representedType, boolean hasTypeParameters) {
    // TODO: Fill out all class fields?
    super(hasTypeParameters ? Type.GENERIC_CLASS : Type.CLASS, hasTypeParameters ? new ShadowClass(representedType, false) : ShadowObject.makeObject(), new HashMap<>());
    this.representedType = representedType;
  }

  @Override
  public ShadowValue[] callMethod(String method, ShadowValue... arguments)
      throws InterpreterException {
    if (arguments.length == 0) {
      switch (method) {
        case "isArray":
          return new ShadowBoolean[]{isArray()};
        case "isGeneric":
          return new ShadowBoolean[]{isGeneric()};
        case "isInterface":
          return new ShadowBoolean[]{isInterface()};
        case "isMethod":
          return new ShadowBoolean[]{isMethod()};
        case "isPrimitive":
          return new ShadowBoolean[]{isPrimitive()};
        case "isSingleton":
          return new ShadowBoolean[]{isSingleton()};
        case "name":
          return new ShadowString[]{name()};
        case "parent":
          return new ShadowValue[]{parent()};
      }
    }

    return super.callMethod(method, arguments);
  }

  public ShadowString name() {
    return new ShadowString(representedType.toString());
  }

  public ShadowValue parent() throws InterpreterException {
    if (representedType instanceof ClassType) {
      ClassType classType = (ClassType) representedType;
      if (classType.getExtendType() == null) return new ShadowNull(Type.CLASS);
      else return new ShadowClass(classType.getExtendType());
    }

    return new ShadowNull(Type.CLASS);
  }

  public ShadowBoolean isInterface() {
    return new ShadowBoolean(representedType instanceof InterfaceType);
  }

  public ShadowBoolean isPrimitive() {
    return new ShadowBoolean(representedType.isPrimitive());
  }

  public ShadowBoolean isGeneric() {
    return new ShadowBoolean(representedType.isParameterized());
  }

  public ShadowBoolean isArray() {
    return new ShadowBoolean(representedType instanceof ArrayType);
  }

  public ShadowBoolean isSingleton() {
    return new ShadowBoolean(representedType instanceof SingletonType);
  }

  public ShadowBoolean isMethod() {
    return new ShadowBoolean(representedType instanceof MethodType);
  }

  public ShadowBoolean equal(ShadowValue value) throws InterpreterException {
    if (value instanceof ShadowClass) {
      ShadowClass other = (ShadowClass) value;
      return new ShadowBoolean(representedType.equals(other.representedType));
    }

    return new ShadowBoolean(false);
  }

  @SuppressWarnings("unused")
  public ShadowBoolean classIsSubtype(ShadowValue value) {

    if (value instanceof ShadowClass) {
      ShadowClass other = (ShadowClass) value;
      return new ShadowBoolean(representedType.isSubtype(other.representedType));
    }

    return new ShadowBoolean(false);
  }

  public String toString() {
    return representedType.toString();
  }

  public ShadowInteger hash() throws InterpreterException {
    return new ShadowString(toString()).hash();
  }

  /**
   * Gets the type that this Class object was created from.
   *
   * <p>E.g. returns {@code String} for a Class object created via {@code String:class}.
   */
  public Type getRepresentedType() {
    return representedType;
  }

  @Override
  public String toLiteral() {
    return representedType.toString() + ":class";
  }
}
