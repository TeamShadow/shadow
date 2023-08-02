package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.Type;

/**
 * Instruction used to convert a pointer to a ulong. Used in the copy() method where a hashmap of
 * addresses are maintained.
 *
 * @author Barry Wittman
 */
public class TACPointerToLong extends TACOperand {

  private final TACOperand pointer;

  public TACPointerToLong(TACNode node, TACOperand pointer) {
    super(node);
    this.pointer = pointer;
  }

  @Override
  public Type getType() {
    return Type.ULONG;
  }

  @Override
  public int getNumOperands() {
    return 1;
  }

  @Override
  public TACOperand getOperand(int num) {
    if (num == 0) return pointer;

    throw new IndexOutOfBoundsException("" + num);
  }

  @Override
  public void accept(TACVisitor visitor) throws ShadowException {
    visitor.visit(this);
  }
}
