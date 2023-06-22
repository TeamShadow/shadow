package shadow.interpreter;

import shadow.typecheck.ScopedChecker;
import shadow.typecheck.type.Type;
public class ShadowVariable extends ShadowReference {

  private final ScopedChecker checker;
  private final String name;

  public ShadowVariable(ScopedChecker checker, String name) {
    this.checker = checker;
    this.name = name;
  }

  @Override
  public ShadowValue get() throws InterpreterException {
    return (ShadowValue) checker.findSymbol(name);
  }

  @Override
  public void set(ShadowValue value) throws InterpreterException {
    checker.setSymbol(name, value);
  }

  @Override
  public Type getType() {
    return checker.findSymbol(name).getType();
  }
}
