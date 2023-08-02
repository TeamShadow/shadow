package shadow.interpreter;

import shadow.typecheck.type.Type;

public class ShadowField extends ShadowSuffix {

  private final String name;
  private final Type type;

  public ShadowField(ShadowObject prefix, String name, Type type) {
    super(prefix);
    this.name = name;
    this.type = type;
  }

  @Override
  public ShadowValue get() throws InterpreterException {
    return ((ShadowObject) getPrefix()).getField(name);
  }

  @Override
  public void set(ShadowValue value) throws InterpreterException {
    ((ShadowObject) getPrefix()).setField(name, value);
  }

  @Override
  public Type getType() {
    return type;
  }
}
