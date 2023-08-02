package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.*;

public class TACMethodName extends TACOperand implements TACMethodRef {
  private TACOperand prefix;
  private TACMethodRef wrapped;
  private MethodSignature signature;
  private boolean isSuper = false;

  public TACMethodName(TACNode node, MethodSignature sig) {
    this(node, null, sig);
  }

  public TACMethodName(TACNode node, TACOperand prefixNode, MethodSignature sig) {
    super(node);
    initialize(prefixNode, sig);
    if (sig.isWrapper()) wrapped = new TACMethodName(this, sig.getWrapped());
  }

  private void initialize(TACOperand prefixNode, MethodSignature sig) {
    if (prefixNode != null) {
      prefix = prefixNode;

      if (prefix.getType() instanceof ArrayType arrayType) {
        Type genericArray = arrayType.convertToGeneric();
        prefix = check(prefixNode, new SimpleModifiedType(genericArray));
      } else // Don't change to parent type for methods not implemented by the child
        prefix = check(prefixNode, new SimpleModifiedType(sig.getOuter()));
    }
    signature = sig;
  }

  public TACMethodName(TACNode node, TACMethodName other) {
    this(node, null, other.signature);
  }

  public Type getOuterType() {
    return signature.getOuter();
  }

  public boolean hasPrefix() {
    return prefix != null;
  }

  public TACOperand getPrefix() {
    return prefix;
  }

  public int getIndex() {
    int index = signature.getOuter().getMethodIndex(signature);
    if (index == -1 || prefix == null) throw new UnsupportedOperationException();
    return index;
  }

  @Override
  public MethodType getType() {
    if (isWrapper() || getOuterType() instanceof InterfaceType)
      return signature.getSignatureWithoutTypeArguments().getMethodType();
    else return signature.getMethodType();
  }

  public String getName() {
    return signature.getSymbol();
  }

  public boolean isWrapper() {
    return wrapped != null;
  }

  public SequenceType getParameterTypes() {
    return signature.getFullParameterTypes();
  }

  public SequenceType getUninstantiatedParameterTypes() {
    return signature.getSignatureWithoutTypeArguments().getFullParameterTypes();
  }

  public SequenceType getReturnTypes() {
    return signature.getFullReturnTypes();
  }

  public SequenceType getUninstantiatedReturnTypes() {
    return signature.getSignatureWithoutTypeArguments().getFullReturnTypes();
  }

  public int getReturnCount() {
    return getReturnTypes().size();
  }

  public boolean isVoid() {
    return getReturnCount() == 0;
  }

  public Type getReturnType() {
    SequenceType returnTypes = getUninstantiatedReturnTypes();
    if (returnTypes.isEmpty()) return null;
    else if (returnTypes.size() == 1) return returnTypes.get(0).getType();
    else return returnTypes;
  }

  @Override
  public int getNumOperands() {
    return hasPrefix() ? 1 : 0;
  }

  @Override
  public TACOperand getOperand(int num) {
    if (hasPrefix() && num == 0) return getPrefix();
    throw new IndexOutOfBoundsException("" + num);
  }

  @Override
  public void accept(TACVisitor visitor) throws ShadowException {
    visitor.visit(this);
  }

  @Override
  public String toString() {
    return getOuterType() + "." + getName();
  }

  public void setSuper(boolean value) {
    isSuper = value;
  }

  public boolean isSuper() {
    return isSuper;
  }

  public MethodSignature getSignature() {
    return signature;
  }

  @Override
  public SequenceType getFullReturnTypes() {
    return signature.getFullReturnTypes();
  }
}
