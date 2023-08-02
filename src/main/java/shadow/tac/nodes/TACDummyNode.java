package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.parse.Context;
import shadow.tac.TACBlock;
import shadow.tac.TACVisitor;

public class TACDummyNode extends TACNode {

  public TACDummyNode(Context node, TACBlock block) {
    super(null);
    setContext(node);
    setBlock(block);
  }

  @Override
  public int getNumOperands() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public TACOperand getOperand(int num) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void accept(TACVisitor visitor) throws ShadowException {
    throw new UnsupportedOperationException(); // DummyNode should never get walked
  }
}
