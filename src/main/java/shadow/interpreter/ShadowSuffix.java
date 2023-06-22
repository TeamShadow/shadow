package shadow.interpreter;

public abstract  class ShadowSuffix extends ShadowReference {
  private ShadowValue prefix;

  public ShadowSuffix(ShadowValue prefix) {
    this.prefix = prefix;
  }

  protected ShadowValue getPrefix() {
    return prefix;
  }
}
