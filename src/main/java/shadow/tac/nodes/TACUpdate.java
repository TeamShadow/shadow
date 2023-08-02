package shadow.tac.nodes;

import java.util.Set;

public abstract class TACUpdate extends TACOperand {
  private TACOperand updatedValue;

  public TACOperand getUpdatedValue() {
    return updatedValue;
  }

  public void setUpdatedValue(TACOperand value) {
    updatedValue = value;
  }

  protected TACUpdate(TACNode node) {
    super(node);
  }

  public abstract TACOperand getValue();

  public abstract boolean update(Set<TACUpdate> currentlyUpdating);

  public boolean isNull() {
    TACOperand value = getValue();
    if (value == null || value == this) return false; // unknown value might not be null
    return value.isNull();
  }
}
