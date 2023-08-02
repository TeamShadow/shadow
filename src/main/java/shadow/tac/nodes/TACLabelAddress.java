package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.output.llvm.IrOutput;
import shadow.tac.TACMethod;
import shadow.tac.TACMethod.TACFinallyFunction;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.PointerType;
import shadow.typecheck.type.Type;

public class TACLabelAddress extends TACOperand {

  private final TACLabel label;
  private final TACMethod method;

  public TACLabelAddress(TACNode node, TACLabel label, TACMethod method) {
    super(node);
    this.label = label;
    this.method = method;
  }

  public TACLabel getLabel() {
    return label;
  }

  public TACMethod getMethod() {
    return method;
  }

  @Override
  public int getNumOperands() {
    return 0;
  }

  @Override
  public TACOperand getOperand(int num) {
    throw new IndexOutOfBoundsException("" + num);
  }

  @Override
  public Object getData() {
    TACFinallyFunction function = label.getBlock().getFinallyFunction();
    if (function != null)
      return "blockaddress(" + IrOutput.name(function) + ", " + IrOutput.symbol(label) + ")";
    else return "blockaddress(" + IrOutput.name(method) + ", " + IrOutput.symbol(label) + ")";
  }

  @Override
  public Type getType() {
    return new PointerType();
  }

  @Override
  public String toString() {
    return "address(" + label.toString() + ")";
  }

  @Override
  public void accept(TACVisitor visitor) throws ShadowException {
    visitor.visit(this);
  }
}
