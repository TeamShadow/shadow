package shadow.tac.nodes;

import shadow.ShadowException;
import shadow.interpreter.ShadowInteger;
import shadow.output.llvm.LLVMOutput;
import shadow.tac.TACMethod;
import shadow.tac.TACVisitor;
import shadow.typecheck.type.*;

/**
 * TAC representation of object allocation Example: Object:create()
 *
 * @author Jacob Young
 */
public class TACNewObject extends TACOperand {
  private final Type type;
  private TACOperand classData;
  private final TACOperand methodTable;

  public TACNewObject(TACNode node, Type type) {
    super(node);
    this.type = type;

    // class needs real type
    TACClass _class = new TACClass(this, type);
    classData = _class.getClassData();
    if (!classData.getType().equals(Type.CLASS))
      classData = TACCast.cast(this, new SimpleModifiedType(Type.CLASS), classData);
    methodTable = _class.getMethodTable();

    // there's a chance that it could be an interface, which isn't allowed
    if (type instanceof TypeParameter) {
      TACOperand flags =
          new TACLoad(this, new TACFieldRef(classData, Type.CLASS.getField("flags"), "flags"));
      TACLiteral interfaceFlag = new TACLiteral(this, new ShadowInteger(LLVMOutput.INTERFACE));
      TACOperand value =
          new TACBinary(
              this,
              flags,
              Type.INT.getMatchingMethod("bitAnd", new SequenceType(interfaceFlag)),
              "&",
              interfaceFlag);
      TACOperand test =
          new TACBinary(
              this,
              value,
              new TACLiteral(this, new ShadowInteger(0))); // no operand is straight compare ===

      TACMethod method = getMethod();
      TACLabel throwLabel = new TACLabel(method);
      TACLabel doneLabel = new TACLabel(method);
      new TACBranch(this, test, doneLabel, throwLabel);

      throwLabel.insertBefore(this);
      TACOperand object = new TACNewObject(this, Type.INTERFACE_CREATE_EXCEPTION);
      TACOperand name =
          new TACLoad(this, new TACFieldRef(classData, Type.CLASS.getField("name"), "name"));
      MethodSignature signature =
          Type.INTERFACE_CREATE_EXCEPTION.getMatchingMethod("create", new SequenceType(name));

      TACCall exception = new TACCall(this, new TACMethodName(this, signature), object, name);
      new TACThrow(this, exception);

      doneLabel.insertBefore(this);
    }
  }

  public TACOperand getClassData() {
    return classData;
  }

  public TACOperand getMethodTable() {
    return methodTable;
  }

  public Type getClassType() {
    return type;
  }

  @Override
  public ClassType getType() {
    return Type.OBJECT;
  }

  @Override
  public int getNumOperands() {
    return 0;
  }

  @Override
  public TACOperand getOperand(int num) {
    throw new IndexOutOfBoundsException();
  }

  @Override
  public void accept(TACVisitor visitor) throws ShadowException {
    visitor.visit(this);
  }

  @Override
  public String toString() {
    return type + ":create()";
  }
}
