package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVariable;
import shadow.tac.TACVisitor;

import java.util.Set;

public class TACLocalStore extends TACLocalStorage {
  private final TACOperand value;
  private boolean incrementReference;
  private boolean decrementReference = true;
  private boolean previousStore;
  private final TACCatchPad catchPad;

  public TACLocalStore(TACNode node, TACVariable variable, TACOperand op) {
    this(node, variable, op, true);
  }

  public TACLocalStore(
      TACNode node, TACVariable variable, TACOperand op, boolean incrementReference) {
    this(node, variable, op, incrementReference, null);
  }

  public TACLocalStore(
      TACNode node,
      TACVariable variable,
      TACOperand op,
      boolean incrementReference,
      TACCatchPad catchPad) {
    super(node, variable);
    value = check(op, variable);
    value.setLocalStore(this);
    this.incrementReference = incrementReference;
    this.catchPad = catchPad;
  }

  public TACCatchPad getCatchPad() {
    return catchPad;
  }

  public boolean isIncrementReference() {
    return incrementReference;
  }

  public void setIncrementReference(boolean value) {
    incrementReference = value;
  }

  public boolean isDecrementReference() {
    return decrementReference;
  }

  public void setDecrementReference(boolean value) {
    decrementReference = value;
  }

  @Override
  public boolean update(Set<TACUpdate> currentlyUpdating) {
    if (currentlyUpdating.contains(this)) return false;

    currentlyUpdating.add(this);
    boolean changed = false;
    TACOperand temp = getValue();

    if (temp instanceof TACUpdate) {
      TACUpdate update = (TACUpdate) temp;
      if (update.update(currentlyUpdating)) changed = true;

      temp = update.getValue();
      if (temp != getUpdatedValue() && temp.canPropagate()) {
        setUpdatedValue(temp);
        changed = true;
      }
    }

    currentlyUpdating.remove(this);
    return changed;
  }

  public TACOperand getValue() {
    if (getUpdatedValue() == null || isGarbageCollected() || getVariable().isFinallyVariable())
      return value;
    else return getUpdatedValue();
  }

  @Override
  public int getNumOperands() {
    return 1;
  }

  @Override
  public TACOperand getOperand(int num) {
    if (num == 0) return value;
    throw new IndexOutOfBoundsException("" + num);
  }

  @Override
  public void accept(TACVisitor visitor) throws ShadowException {
    visitor.visit(this);
  }

  @Override
  public String toString() {
    return getVariable() + " = " + value;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof TACLocalStore)) return false;

    if (other == this) return true;

    TACLocalStore store = (TACLocalStore) other;

    return getNumber() == store.getNumber() && getVariable().equals(store.getVariable());
  }

  public void setPreviousStore(boolean value) {
    previousStore = value;
  }

  public boolean hasPreviousStore() {
    return previousStore;
  }
}
