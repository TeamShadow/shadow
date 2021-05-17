package shadow.tac.nodes;

import shadow.typecheck.type.*;

public abstract class TACOperand extends TACNode implements ModifiedType {
  private Object data;
  private TACLocalStore store = null;
  private TACStore memoryStore = null;

  public boolean hasLocalStore() {
    return store != null;
  }

  public boolean hasMemoryStore() {
    return memoryStore != null;
  }

  public void setLocalStore(TACLocalStore store) {
    this.store = store;
  }

  public void setMemoryStore(TACStore store) {
    this.memoryStore = store;
  }

  public TACStore getMemoryStore() {
    return memoryStore;
  }

  public TACLocalStore getLocalStore() {
    return store;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  protected TACOperand(TACNode node) {
    super(node);
  }

  @Override
  public Modifiers getModifiers() {
    return new Modifiers();
  }

  @Override
  public abstract Type getType();

  @Override
  public void setType(Type type) {
    throw new UnsupportedOperationException();
  }

  protected TACOperand checkVirtual(ModifiedType type, TACNode node) {
    if (getType().isSubtype(type.getType())
        && (getType().isPrimitive() || type.getType().isPrimitive())
        && getModifiers().isNullable() != type.getModifiers().isNullable())
      return TACCast.cast(node, type, this);

    // If no change needed...
    if (getType().equals(type.getType())) return this;

    if (getType().isStrictSubtype(type.getType())) return TACCast.cast(node, type, this);

    // Allows cast from Object[] to Array<Object>
    if (((type.getType() instanceof ArrayType)
        && getType().equals(type.getType())
        && !(getType() instanceof ArrayType))) return TACCast.cast(node, type, this);

    if (type.getType().isParameterized()) {
      if (getType().getTypeWithoutTypeArguments().isStrictSubtype(type.getType()))
        return TACCast.cast(node, type, this);
    }

    // If it got past the type-checker, we need to cast this type parameter into a real thing
    if ((getType() instanceof TypeParameter) && !(type.getType() instanceof TypeParameter))
      return TACCast.cast(node, type, this);

    if (getType() instanceof PointerType
        && !type.getType().isPrimitive()
        && type.getType() instanceof ClassType
        && !(type.getType() instanceof ArrayType)) return TACCast.cast(node, type, this);

    return this;
  }

  /*
   * Whether or not a value can propagate forward when doing constant and value propagation.
   * By default, TACOperands cannot.
   * A guideline: Anything whose result is stored in a temporary variable cannot.
   */
  public boolean canPropagate() {
    return false;
  }

  public static TACOperand value(TACOperand operand) {
    TACOperand value = operand;

    if (value instanceof TACUpdate) {
      TACUpdate update = (TACUpdate) value;
      value = update.getValue();
    }

    return value;
  }
}
