package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.interpreter.ShadowString;
import shadow.interpreter.ShadowValue;
import shadow.tac.TACBlock;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.Modifiers;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;

import java.util.*;

public class TACCall extends TACUpdate {
  private final TACMethodRef methodRef;
  private final List<TACOperand> parameters;
  private TACLabel noExceptionLabel;
  private boolean delegatedCreate; // used to mark calls to super() or this() creates for GC reasons

  public boolean isDelegatedCreate() {
    return delegatedCreate;
  }

  public void setDelegatedCreate(boolean value) {
    delegatedCreate = value;
  }

  public TACLabel getNoExceptionLabel() {
    return noExceptionLabel;
  }

  public TACCall(TACNode node, TACMethodRef methodRef, TACOperand... params) {
    this(node, methodRef, Arrays.asList(params));
  }

  public TACCall(TACNode node, TACMethodRef methodRef, Collection<? extends TACOperand> params) {
    super(node);

    node.getBlock().addUnwindSource();

    this.methodRef = methodRef;
    SequenceType types = methodRef.getParameterTypes();
    SequenceType uninstantiatedTypes = methodRef.getUninstantiatedParameterTypes();
    if (params.size() != types.size())
      throw new IllegalArgumentException("Wrong # args");
    Iterator<? extends TACOperand> paramIter = params.iterator();
    int i = 0;
    parameters = new ArrayList<>(params.size());

    while (paramIter.hasNext()) {
      TACOperand parameter = paramIter.next();
      ModifiedType type = types.get(i);
      parameter = check(parameter, type);
      ModifiedType uninstantiatedType = uninstantiatedTypes.get(i);

      if (!uninstantiatedType.getType().equals(type.getType()))
        parameter = check(parameter, uninstantiatedType);

      parameters.add(parameter);

      i++;
    }

    TACBlock block = getBlock();
    // If there's a catch or a cleanup, we will always need the chance to unwind
    // (Note that the cleanup is actually non-unwinding, but it is used because one always
    // accompanies a finally)
    if (block.getUnwind() != null) {
      noExceptionLabel = new TACLabel(getMethod());
      noExceptionLabel.insertBefore(node); // before the node but after the call
    }
  }

  public TACMethodRef getMethodRef() {
    return methodRef;
  }

  public TACOperand getPrefix() {
    return parameters.get(0);
  }

  public int getNumParameters() {
    return parameters.size();
  }

  public TACOperand getParameter(int index) {
    return parameters.get(index);
  }

  public List<TACOperand> getParameters() {
    return new ArrayList<>(parameters);
  }

  @Override
  public Type getType() {
    return methodRef.getReturnType();
  }

  @Override
  public Modifiers getModifiers() {
    SequenceType returns = methodRef.getReturnTypes();
    if (returns.size() == 1) return returns.getModifiers(0);

    return super.getModifiers();
  }

  @Override
  public int getNumOperands() {
    return 1 + parameters.size();
  }

  @Override
  public TACOperand getOperand(int num) {
    if (num == 0) return (TACOperand) methodRef;
    return parameters.get(num - 1);
  }

  @Override
  public void accept(TACVisitor visitor) throws ShadowException {
    visitor.visit(this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    TACMethodRef method = getMethodRef();
    sb.append(method.toString()).append('(');
    for (TACOperand parameter : getParameters()) sb.append(parameter).append(", ");
    if (getNumParameters() != 0) sb.delete(sb.length() - 2, sb.length());
    return sb.append(')').toString();
  }

  @Override
  public boolean update(Set<TACUpdate> currentlyUpdating) {
    if (currentlyUpdating.contains(this)) return false;

    currentlyUpdating.add(this);
    boolean changed = false;
    boolean allLiterals = true;

    for (TACOperand parameter : parameters) {
      if (parameter instanceof TACUpdate) {
        TACUpdate update = (TACUpdate) parameter;
        if (update.update(currentlyUpdating)) changed = true;
        // parameters.set(i, update.getValue());
        if (!(update.getValue() instanceof TACLiteral)) allLiterals = false;
      } else if (!(parameter instanceof TACLiteral)) allLiterals = false;
    }

    // right now, the only calls we're doing are on String objects
    if (methodRef instanceof TACMethodName) {
      TACMethodName methodName = (TACMethodName) methodRef;

      if ((changed || getUpdatedValue() == null)
          && allLiterals
          && methodName.getSignature().getOuter().equals(Type.STRING)
          && ShadowString.isSupportedMethod(methodName.getSignature())) {
        try {
          TACLiteral string = (TACLiteral) value(parameters.get(0));
          ShadowValue result = ((ShadowString) string.getValue()).callMethod(this);
          setUpdatedValue(new TACLiteral(this, result));
          changed = true;
        } catch (ShadowException ignored) {
        } // Do nothing, failed to evaluate
      }
    }

    currentlyUpdating.remove(this);
    return changed;
  }

  @Override
  public TACOperand getValue() {
    if (getUpdatedValue() == null) return this;
    else return getUpdatedValue();
  }
}
