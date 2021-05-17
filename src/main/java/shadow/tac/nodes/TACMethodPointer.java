package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.MethodReferenceType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.SimpleModifiedType;
import shadow.typecheck.type.Type;

public class TACMethodPointer extends TACOperand implements TACMethodRef {

  private final TACOperand pointer;
  private final MethodReferenceType type;
  private final String name;

  public TACMethodPointer(TACNode node, TACOperand pointer, String name, MethodReferenceType type) {
    super(node);
    this.pointer = pointer;
    this.type = type;
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }

  public TACOperand getPointer() {
    return pointer;
  }

  @Override
  public SequenceType getParameterTypes() {
    SequenceType paramTypes = new SequenceType();
    paramTypes.add(new SimpleModifiedType(Type.OBJECT));
    paramTypes.addAll(type.getMethodType().getParameterTypes());
    return paramTypes;
  }

  @Override
  public SequenceType getUninstantiatedParameterTypes() {
    SequenceType paramTypes = new SequenceType();
    paramTypes.add(new SimpleModifiedType(Type.OBJECT));
    paramTypes.addAll(type.getMethodType().getTypeWithoutTypeArguments().getParameterTypes());
    return paramTypes;
  }

  @Override
  public SequenceType getUninstantiatedReturnTypes() {
    return type.getMethodType().getTypeWithoutTypeArguments().getReturnTypes();
  }

  @Override
  public Type getReturnType() {
    return type.getMethodType().getReturnTypes().get(0).getType();
  }

  @Override
  public SequenceType getReturnTypes() {
    return type.getMethodType().getReturnTypes();
  }

  @Override
  public MethodReferenceType getType() {
    return type;
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

  @Override
  public SequenceType getFullReturnTypes() {
    return getReturnTypes();
  }
}
