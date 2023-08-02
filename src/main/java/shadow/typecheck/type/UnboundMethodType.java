package shadow.typecheck.type;

import shadow.doctool.Documentation;

import java.util.List;

public class UnboundMethodType extends ClassType {

  private MethodSignature knownSignature;

  public UnboundMethodType(String typeName, Type outer) {
    this(typeName, outer, new Modifiers(), null);
  }

  public UnboundMethodType(
      String typeName, Type outer, Modifiers modifiers, Documentation documentation) {
    super(typeName, modifiers, documentation, outer);
    setExtendType(Type.OBJECT); // change to UnboundMethod if reinstated?
  }

  public boolean isSubtype(Type t) {
    if (equals(t) || t == Type.OBJECT) return true;

    if (t instanceof MethodType methodType) {
      MethodSignature signature =
          getOuter().getMatchingMethod(getTypeName(), methodType.getParameterTypes());

      if (signature == null) return false;

      return signature.getMethodType().isSubtype(methodType);
    } else if (t instanceof MethodReferenceType)
      return isSubtype(((MethodReferenceType) t).getMethodType());

    return false;
  }

  public void setKnownSignature(MethodSignature signature) {
    knownSignature = signature;
  }

  public MethodSignature getKnownSignature() {
    return knownSignature;
  }

  @Override
  public UnboundMethodType replace(List<ModifiedType> values, List<ModifiedType> replacements) {
    return this;
  }

  @Override
  public UnboundMethodType partiallyReplace(
      List<ModifiedType> values, List<ModifiedType> replacements) {
    return this;
  }
}
