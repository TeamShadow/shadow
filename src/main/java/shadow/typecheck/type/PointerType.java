package shadow.typecheck.type;

import java.util.List;

public class PointerType extends Type {

  public PointerType() {
    super("Pointer Type");
  }

  @Override
  public boolean isSubtype(Type other) {
    return other instanceof PointerType;
  }

  @Override
  public Type replace(List<ModifiedType> values, List<ModifiedType> replacements)
      throws InstantiationException {
    return this;
  }

  @Override
  public Type partiallyReplace(List<ModifiedType> values, List<ModifiedType> replacements) {
    return this;
  }

  @Override
  public void updateFieldsAndMethods() throws InstantiationException {}
}
