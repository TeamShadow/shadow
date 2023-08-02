package shadow.interpreter;

import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.PropertyType;

public class ShadowProperty extends ShadowSuffix {
  private final PropertyType type;

  public ShadowProperty(ShadowObject outer, PropertyType type) {
    super(outer);
    this.type = type;
  }

  @Override
  public PropertyType getType() {
    return type;
  }

  public ShadowValue get() throws InterpreterException {

    MethodSignature signature = type.getGetter();
    if (signature.getNode().getParent() == null && signature.getModifiers().isLocked())
      return ((ShadowObject) getPrefix()).getField(signature.getSymbol());
    else return getPrefix().callMethod(type.getGetter().getSymbol())[0];
  }

  @Override
  public void set(ShadowValue value) throws InterpreterException {
    MethodSignature signature = type.getSetter();
    if (signature.getNode().getParent() == null && signature.getModifiers().isLocked())
      ((ShadowObject) getPrefix()).setField(signature.getSymbol(), value);
    else getPrefix().callMethod(type.getSetter().getSymbol(), value);
  }
}
