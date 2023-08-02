package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SequenceType;

import java.util.*;

public class TACSequence extends TACOperand {
  private final List<TACOperand> sequence;

  public TACSequence(TACNode node, List<TACOperand> seq) {
    super(node);
    sequence = new ArrayList<>(seq);
  }

  public int size() {
    return sequence.size();
  }

  public TACOperand get(int index) {
    return sequence.get(index);
  }

  @Override
  public SequenceType getType() {
    return new SequenceType(new ArrayList<>(sequence));
  }

  @Override
  public int getNumOperands() {
    return sequence.size();
  }

  @Override
  public TACOperand getOperand(int num) {
    return sequence.get(num);
  }

  @Override
  public void accept(TACVisitor visitor) throws ShadowException {
    visitor.visit(this);
  }

  @Override
  public String toString() {
    if (sequence.size() == 1) return sequence.get(0).toString();
    StringBuilder sb = new StringBuilder().append('(');
    for (TACOperand each : sequence) sb.append(each).append(", ");
    sb.delete(sb.length() - 2, sb.length());
    return sb.append(')').toString();
  }

  @Override
  protected TACOperand checkVirtual(ModifiedType type, TACNode node) {
    Iterator<ModifiedType> types = ((SequenceType) type.getType()).iterator();
    ListIterator<TACOperand> seq = sequence.listIterator();
    while (seq.hasNext()) seq.set(check(seq.next(), types.next()));
    return this;
  }
}
