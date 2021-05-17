package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.parse.Context;
import shadow.tac.TACBlock;
import shadow.tac.TACMethod;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An abstract class that is the base of all TAC operations.
 *
 * <p>Each TACNode is part of a circular doubly-linked list.
 *
 * @author Jacob Young
 * @author Barry Wittman
 */
public abstract class TACNode implements Iterable<TACOperand> {
  private TACNode prev, next; // the next and prev nodes in our circular linked list
  private Context context; // associated AST context, used for error messages
  private TACBlock block; // which block the node is in

  private boolean garbageCollected = false;

  public boolean isGarbageCollected() {
    return garbageCollected;
  }

  public void setGarbageCollected(boolean value) {
    garbageCollected = value;
  }

  /**
   * Constructor adds current node *before* parameter node
   *
   * @param node node the current node is being added in front of
   */
  protected TACNode(TACNode node) {
    prev = this;
    next = this;
    insertBefore(node);
  }

  public TACBlock getBlock() {
    return block;
  }

  public TACMethod getMethod() {
    return block.getMethod();
  }

  public void setBlock(TACBlock block) {
    this.block = block;
  }

  public final TACNode getPrevious() {
    return prev;
  }

  public final TACNode getNext() {
    return next;
  }

  public void insertBefore(final TACNode node) {
    if (node == this) return;

    if (node != null) {
      context = node.getContext();
      block = node.getBlock();
    }

    remove();
    if (node != null) connect(node.getPrevious(), this, node);
  }

  /** Removes this node from the circular linked-list and returns the previous node. */
  public TACNode remove() {
    TACNode previous = prev;
    if (previous == this) previous = null;
    connect(prev, next);
    this.prev = this.next = this;
    return previous;
  }
  /**
   * Appends the list ending with this right before node.
   *
   * @param node at the end of the other list
   */
  public void appendBefore(TACNode node) {
    // should work with only one node in the list
    connect(node.prev, this.next); // this.next is actually the beginning of the list
    connect(this, node);
  }

  protected final void connect(TACNode first, TACNode second, TACNode third) {
    connect(first, second);
    connect(second, third);
  }

  /**
   * Connects two nodes together: first <-> second
   *
   * @param first the first node to connect.
   * @param second the second node to connect.
   */
  protected final void connect(TACNode first, TACNode second) {
    if (first != null) first.next = second;
    if (second != null) second.prev = first;
  }

  public Context getContext() {
    return context;
  }

  public void setContext(Context node) {
    context = node;
  }

  @Override
  public Iterator<TACOperand> iterator() {
    return new OperandIterator();
  }

  private final class OperandIterator implements Iterator<TACOperand> {
    private int index = 0;

    @Override
    public boolean hasNext() {
      return index != getNumOperands();
    }

    @Override
    public TACOperand next() {
      if (index == getNumOperands()) throw new NoSuchElementException();
      return getOperand(index++);
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  public abstract int getNumOperands();

  public abstract TACOperand getOperand(int num);

  /*
   * protected final TACOperand check(TACOperand operand, ModifiedType type) {
   * return check(operand, type, false); }
   */
  protected final TACOperand check(TACOperand operand, ModifiedType type) {
    if (type.getType() instanceof TypeParameter
        && !(operand.getType() instanceof TypeParameter)
        && !operand.getType().equals(Type.NULL)) {
      TypeParameter parameter = (TypeParameter) type.getType();
      type = new SimpleModifiedType(parameter.getClassBound());
    }

    operand = operand.checkVirtual(type, this); // puts in casts where needed

    Type operandType = operand.getType();
    Type typeType = type.getType();

    if (operandType instanceof ArrayType)
      operandType = ((ArrayType) operandType).convertToGeneric();

    if (typeType instanceof ArrayType) typeType = ((ArrayType) typeType).convertToGeneric();

    if (operandType.equals(typeType)
        || (operandType instanceof TypeParameter && typeType instanceof TypeParameter))
      return operand;

    if (operandType instanceof SequenceType
        && typeType instanceof SequenceType
        && ((SequenceType) operandType)
            .matches(((SequenceType) typeType))) { // replace with subtype? no!
      return operand;
    } else if ((operandType instanceof SequenceType) != (typeType instanceof SequenceType))
      throw new IllegalArgumentException(
          operandType + " and " + typeType + " are not both sequence types");

    if (operandType.equals(Type.METHOD) && typeType instanceof MethodReferenceType) return operand;

    final shadow.typecheck.Package operandPackage = operandType.getPackage();
    final shadow.typecheck.Package typePackage = typeType.getPackage();

    if (operandPackage != null
        && operandPackage.equals(typePackage)
        && operandType.getTypeName().equals(typeType.getTypeName())) return operand;

    throw new IllegalArgumentException(
        "Check of operand "
            + operand
            + " of type "
            + operandType
            + " and "
            + type
            + " of type "
            + typeType
            + " couldn't be resolved!");
  }

  public abstract void accept(TACVisitor visitor) throws ShadowException;
}
