package shadow.tac.nodes;

import shadow.typecheck.type.*;

public class TACFieldRef extends TACReference {
  private final int index;
  private final TACOperand prefix;
  private final ModifiedType type;
  private final String name;

  public TACFieldRef(TACOperand fieldPrefix, String fieldName) {
    this(fieldPrefix, fieldPrefix.getType().getField(fieldName), fieldName);
  }

  public TACFieldRef(TACOperand fieldPrefix, ModifiedType fieldType, String fieldName) {

    if (fieldType == null) throw new NullPointerException();
    switch (fieldName) {
      case "reference count" -> index = 0;
      case "class" -> index = 1;
      case "_methods" -> index = 2;
      default -> {
        // 0 is reference count
        // 1 is class
        // 2 is methods
        ClassType prefixType = (ClassType) fieldPrefix.getType();
        if (prefixType instanceof ArrayType)
          prefixType = ((ArrayType) prefixType).convertToGeneric();
        int value = prefixType.getFieldIndex(fieldName);
        if (value < 0)
          throw new IllegalArgumentException(
                  "Field " + fieldName + " not found in type " + prefixType);
        index = value + 3;
      }
    }
    prefix = fieldPrefix;
    type = fieldType;
    name = fieldName;
  }

  public int getIndex() {
    return index;
  }

  public Type getPrefixType() {
    return prefix.getType();
  }

  public TACOperand getPrefix() {
    return prefix;
  }

  public String getName() {
    return name;
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

  @Override
  public String toString() {
    return prefix.toString() + ':' + name;
  }
}
