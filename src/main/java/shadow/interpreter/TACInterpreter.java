package shadow.interpreter;

import shadow.ShadowException;
import shadow.interpreter.InterpreterException.Error;
import shadow.tac.TACAbstractVisitor;
import shadow.tac.TACVariable;
import shadow.tac.nodes.*;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Interpreter that walks TAC nodes in order to determine the values for members marked constant. At
 * present, method calls for primitive types and strings are hardcoded, but a future version of the
 * interpreter should actually walk method definitions, failing only if it reaches unsupported
 * native methods.
 *
 * <p>Compile-time constant evaluation is mostly in {@link ASTInterpreter}. There are a few cases
 * where interpretation of TAC nodes is desired, so this code is being preserved.
 *
 * @author Barry Wittman
 * @author Jacob Young
 */
public class TACInterpreter extends TACAbstractVisitor {
  private final Map<String, ShadowValue> constants;
  private final Map<String, ShadowValue> variables = new HashMap<>();

  public TACInterpreter(Map<String, ShadowValue> constants) {
    this.constants = constants;
  }

  @Override
  public void visit(TACLiteral node) throws ShadowException {
    node.setData(node.getValue());
  }

  public static ShadowValue evaluate(TACUnary node) throws ShadowException {
    ShadowValue data = null;
    ShadowValue op = value(TACOperand.value(node.getOperand()));

    switch (node.getOperation()) {
      case "-" -> data = op.negate();
      case "#" -> data = new ShadowString(op.toString());
      case "~" -> data = op.bitwiseComplement();
      case "!" -> data = op.not();
    }

    return data;
  }

  @Override
  public void visit(TACUnary node) throws ShadowException {
    node.setData(evaluate(node));
  }

  public static ShadowValue evaluate(TACBinary node) throws ShadowException {
    ShadowValue data = null;
    ShadowValue left = value(TACOperand.value(node.getFirst())),
        right = value(TACOperand.value(node.getSecond()));

    switch (node.getOperation()) {
      case "+", "-", "*", "/", "%", "|", "&", "^", "==", "===", "!=", "<", "<=", ">", ">=" -> {
        if (left.isStrictSubtype(right)) left = left.cast(right.getType());
        else if (right.isStrictSubtype(left)) right = right.cast(left.getType());

        // sure, a bit ugly
        switch (node.getOperation()) {
          case "+":
            data = left.add(right);
            break;
          case "-":
            data = left.subtract(right);
            break;
          case "*":
            data = left.multiply(right);
            break;
          case "/":
            data = left.divide(right);
            break;
          case "%":
            data = left.modulus(right);
            break;
          case "|":
            data = left.bitwiseOr(right);
            break;
          case "&":
            data = left.bitwiseAnd(right);
            break;
          case "^":
            data = left.bitwiseXor(right);
            break;
          case "==":
            data = left.equal(right);
            break;
          case "===":
            if ((left.getType().isPrimitive() && right.getType().isPrimitive())
                    || (left instanceof ShadowNull && right instanceof ShadowNull))
              data = left.equal(right);
            else
              throw new InterpreterException(
                      Error.UNSUPPORTED_OPERATION,
                      "Interpreter cannot perform reference comparison on non-primitive types");
            break;
          case "!=":
            data = left.notEqual(right);
            break;
          case "<":
            data = left.lessThan(right);
            break;
          case "<=":
            data = left.lessThanOrEqual(right);
            break;
          case ">":
            data = left.greaterThan(right);
            break;
          case ">=":
            data = left.greaterThanOrEqual(right);
            break;
        }
      }
      case "<<" -> data = left.bitShiftLeft(right);
      case ">>" -> data = left.bitShiftRight(right);
      case "<<<" -> data = left.bitRotateLeft(right);
      case ">>>" -> data = left.bitRotateRight(right);
      case "or" -> data = left.or(right);
      case "xor" -> data = left.xor(right);
      case "and" -> data = left.and(right);
      case "#" -> // ever happens?
              data = new ShadowString(left.toString() + right);
    }

    return data;
  }

  @Override
  public void visit(TACBinary node) throws ShadowException {
    node.setData(evaluate(node));
  }

  @Override
  public void visit(TACLocalStore node) throws ShadowException {
    TACVariable variable = node.getVariable();
    TACOperand data = node.getValue();
    variables.put(variable.getName(), value(data));
  }

  @Override
  public void visit(TACStore node) throws ShadowException {
    /*
    TACReference reference = node.getReference();
    TACOperand data = node.getValue();

    if( reference instanceof TACVariableRef ) {
    	TACVariable variable = ((TACVariableRef)reference).getVariable();
    	variables.put(variable.getName(), value(data));
    }
    else
     */
    throw new UnsupportedOperationException();
  }

  @Override
  public void visit(TACLoad node) throws ShadowException {
    TACReference reference = node.getReference();

    if (reference instanceof TACConstantRef constant) {
      String name = constant.getPrefixType().toString() + ":" + constant.getName();
      ShadowValue data = constants.get(name);
      if (data == null)
        throw new InterpreterException(
            Error.CIRCULAR_REFERENCE,
            "Initialization dependencies prevent the value of constant "
                + name
                + " from being used here");
      node.setData(data);
    }
    // should never happen
    /*
    else if( reference instanceof TACVariableRef ) {
    	TACVariable variable = ((TACVariableRef)reference).getVariable();
    	node.setData(variables.get(variable.getName()));
    }
     */
    else throw new UnsupportedOperationException();
  }

  @Override
  public void visit(TACLocalLoad node) throws ShadowException {
    TACVariable variable = node.getVariable();
    node.setData(variables.get(variable.getName()));
  }

  @Override
  public void visit(TACCall node) throws ShadowException {
    TACMethodRef methodRef = node.getMethodRef();

    if (methodRef instanceof TACMethodName method) {
      MethodSignature signature = method.getSignature();
      List<TACOperand> parameters = node.getParameters();

      if (signature.isCreate()) {

        // must be String
        if (!signature.getOuter().equals(Type.STRING))
          throw new InterpreterException(
              Error.UNSUPPORTED_OPERATION, "Cannot call method " + signature);
        else {
          if (parameters.size() == 1) node.setData(new ShadowString(""));
          else if (parameters.size() == 2) {
            ShadowValue value = value(parameters.get(1));
            if (value instanceof ShadowString) node.setData(value);
            else
              throw new InterpreterException(
                  Error.UNSUPPORTED_OPERATION, "Cannot call method " + signature);
          } else
            throw new InterpreterException(
                Error.UNSUPPORTED_OPERATION, "Cannot call method " + signature);
        }
      } else {
        ShadowValue prefix = value(parameters.get(0));
        ShadowValue[] arguments = new ShadowValue[parameters.size() - 1];
        for (int i = 1; i < parameters.size(); i++)
          arguments[i - 1] = value(parameters.get(i)); // First argument is always the prefix

        ShadowValue data = prefix.callMethod(method.getName(), arguments)[0];
        node.setData(data);
      }
    } else
      throw new InterpreterException(
          Error.UNSUPPORTED_OPERATION, "Cannot call method reference " + methodRef.toString());
  }

  @Override
  public void visit(TACNewObject node) throws ShadowException {
    if (!node.getClassType().equals(Type.STRING))
      throw new InterpreterException(
          Error.INVALID_CREATE, "Cannot create non-String type " + node.getClassType());
  }

  private static ShadowValue value(TACOperand node) throws ShadowException {
    Object value = node.getData();
    if (value instanceof ShadowValue) return (ShadowValue) value;
    throw new InterpreterException(Error.UNSUPPORTED_OPERATION, "Operand does not contain a value");
  }
}
