package shadow.interpreter;

import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;

import java.util.Arrays;
import java.util.Map;

public class ShadowSequence extends ShadowValue {
  private final ShadowValue[] values;

  public ShadowSequence(ShadowValue[] values) {
    this.values = values;
  }

  @Override
  public ShadowValue cast(Type type) throws InterpreterException {
    if (type instanceof SequenceType sequenceType && ((SequenceType) type).size() == values.length) {
      ShadowValue[] castValues = new ShadowValue[values.length];
      for (int i = 0; i < values.length; ++i)
        castValues[i] = values[i].cast(sequenceType.getType(i));

      return new ShadowSequence(castValues);
    }

    throw new InterpreterException(
        InterpreterException.Error.INVALID_CAST,
        "Cannot cast sequence to a type that isn't a sequence of the same length");
  }

  @Override
  public ShadowSequence copy(Map<ShadowValue, ShadowValue> newValues) throws InterpreterException {
    ShadowValue[] copiedValues = new ShadowValue[values.length];
    for (int i = 0; i < values.length; ++i) copiedValues[i] = values[i].copy(newValues);

    return new ShadowSequence(copiedValues);
  }

  @Override
  public String toLiteral() {
    throw new UnsupportedOperationException("Cannot convert a sequence to a literal");
  }

  @Override
  public Type getType() {
    return new SequenceType(Arrays.asList(values));
  }
}
