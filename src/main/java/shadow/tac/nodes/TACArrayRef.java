package shadow.tac.nodes;

import shadow.tac.TACBuilder;
import shadow.tac.TACMethod;
import shadow.typecheck.type.*;

import java.util.ArrayList;
import java.util.List;

/**
 * TAC representation of array subscript Example: [x]
 *
 * @author Jacob Young
 * @author Barry Wittman
 */
public class TACArrayRef extends TACReference {
  private final TACOperand array;
  private final TACOperand index;

  public TACArrayRef(TACNode node, TACOperand reference, TACOperand index) {
    this(node, reference, index, true);
  }

  public TACArrayRef(TACNode node, TACOperand reference, TACOperand index, boolean check) {
    array = reference;
    this.index = index;

    if (check) {
      TACMethod method = node.getMethod();
      TACLabel throwLabel = new TACLabel(method);

      // by casting to unsigned, we don't need to do a negative check
      TACOperand unsignedLength = TACCast.cast(node, new SimpleModifiedType(Type.ULONG), index);
      TACOperand unsignedBound =
          TACCast.cast(
              node, new SimpleModifiedType(Type.ULONG), TACBuilder.arraySize(node, array, true));
      TACOperand condition =
          new TACBinary(
              node,
              unsignedLength,
              Type.ULONG.getMatchingMethod("compare", new SequenceType(Type.ULONG)),
              "<",
              unsignedBound,
              true);

      TACLabel done = new TACLabel(method);
      new TACBranch(node, condition, done, throwLabel);

      throwLabel.insertBefore(node);

      TACOperand object = new TACNewObject(node, Type.INDEX_OUT_OF_BOUNDS_EXCEPTION);
      List<TACOperand> params = new ArrayList<>();
      params.add(object);
      params.add(index);
      MethodSignature signature =
          Type.INDEX_OUT_OF_BOUNDS_EXCEPTION.getMatchingMethod(
              "create", new SequenceType(index.getType()));

      TACCall exception = new TACCall(node, new TACMethodName(node, signature), params);
      new TACThrow(node, exception);

      done.insertBefore(node); // done label
    }
  }

  public TACOperand getArray() {
    return array;
  }

  public TACOperand getIndex() {
    return index;
  }

  @Override
  public Modifiers getModifiers() {
    if (((ArrayType) array.getType()).isNullable()) return new Modifiers(Modifiers.NULLABLE);
    return new Modifiers();
  }

  @Override
  public Type getType() {
    return ((ArrayType) array.getType()).getBaseType();
  }

  @Override
  public String toString() {
    return array.toString() + '[' + index + ']';
  }

  @Override
  public void setType(Type type) {
    throw new UnsupportedOperationException();
  }
}
