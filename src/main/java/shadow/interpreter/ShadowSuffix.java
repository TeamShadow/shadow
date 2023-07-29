package shadow.interpreter;

public abstract class ShadowSuffix extends ShadowReference {
  private final ShadowValue prefix;

  public ShadowSuffix(ShadowValue prefix) {
    this.prefix = prefix;
  }

  protected ShadowValue getPrefix() {
    return prefix;
  }
}
