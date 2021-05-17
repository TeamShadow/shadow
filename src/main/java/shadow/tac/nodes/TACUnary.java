package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.interpreter.ShadowValue;
import shadow.interpreter.TACInterpreter;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.*;

import java.util.Set;

/**
 * TAC representation of unary operator Example: -x
 *
 * @author Jacob Young
 */
public class TACUnary extends TACUpdate {
  // private UnaryOperation operation;
  private final TACOperand operand;
  private final ModifiedType result;
  private final String operation;

  // Only for !
  public TACUnary(TACNode node, String op, TACOperand operand) {
    this(
        node,
        op,
        operand,
        new SimpleModifiedType(Type.BOOLEAN),
        new SimpleModifiedType(Type.BOOLEAN));
  }

  public TACUnary(TACNode node, MethodSignature signature, String op, TACOperand operand) {
    this(
        node,
        op,
        operand,
        new SimpleModifiedType(signature.getOuter()),
        signature.getReturnTypes().get(0));
  }

  private TACUnary(
      TACNode node,
      String op,
      TACOperand operand,
      ModifiedType operandType,
      ModifiedType resultType) {
    super(node);

    if (operandType.getType() instanceof PropertyType)
      operandType = ((PropertyType) operandType.getType()).getGetType();

    operation = op;
    this.operand = check(operand, operandType);
    result = resultType;
  }

  public String getOperation() {
    return operation;
  }

  public TACOperand getOperand() {
    return operand;
  }

  @Override
  public Type getType() {
    return result.getType();
  }

  @Override
  public int getNumOperands() {
    return 1;
  }

  @Override
  public TACOperand getOperand(int num) {
    if (num == 0) return operand;
    throw new IndexOutOfBoundsException();
  }

  @Override
  public void accept(TACVisitor visitor) throws ShadowException {
    visitor.visit(this);
  }

  @Override
  public String toString() {
    return "" + operation + operand;
  }

  @Override
  public TACOperand getValue() {
    if (getUpdatedValue() == null) return this;
    else return getUpdatedValue();
  }

  @Override
  public boolean update(Set<TACUpdate> currentlyUpdating) {
    if (currentlyUpdating.contains(this)) return false;

    currentlyUpdating.add(this);
    boolean changed = false;
    TACOperand value = operand;

    if (operand instanceof TACUpdate) {
      TACUpdate update = (TACUpdate) operand;
      if (update.update(currentlyUpdating)) changed = true;
      value = update.getValue();
    }

    if ((changed || getUpdatedValue() == null) && value instanceof TACLiteral) {
      try {
        ShadowValue result = TACInterpreter.evaluate(this);
        setUpdatedValue(new TACLiteral(this, result));
        changed = true;
      } catch (ShadowException ignored) {
      } // Do nothing, failed to evaluate
    }

    currentlyUpdating.remove(this);
    return changed;
  }
}
