package shadow.interpreter;

import shadow.typecheck.type.Type;

public class ShadowSubscript extends ShadowSuffix {

  private final ShadowValue index;
  private final Type type;

  public ShadowSubscript(ShadowValue prefix, ShadowValue index, Type type) {
    super(prefix);
    this.index = index;
    this.type = type;
  }

  @Override
  public ShadowValue get() throws InterpreterException {
    // Works even for arrays
    return getPrefix().callMethod("index", index)[0];
  }

  @Override
  public void set(ShadowValue value) throws InterpreterException {
    // Works even for arrays
    getPrefix().callMethod("index", index, value);
  }

  @Override
  public Type getType() {
    return type;
  }
}
