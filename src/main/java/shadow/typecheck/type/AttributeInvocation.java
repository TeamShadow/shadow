package shadow.typecheck.type;

import shadow.interpreter.AttributeInterpreter;
import shadow.interpreter.InterpreterException;
import shadow.interpreter.ShadowValue;
import shadow.parse.ShadowParser;
import shadow.parse.ShadowParser.AttributeInvocationContext;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.Package;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a particular invocation of an attribute type. E.g. {@code SomeAttribute("alpha", 5)}.
 */
public class AttributeInvocation {
  private final AttributeType type;

  public AttributeInvocationContext getInvocationContext() {
    return invocationCtx;
  }

  private final AttributeInvocationContext invocationCtx; // The AST node for this invocation

  public Type getEnclosingType() {
    return enclosingType;
  }

  private final Type enclosingType;

  private MethodSignature signature;

  // Note that this does not contain the default expressions provided in the attribute declaration
  private final List<ShadowParser.ConditionalExpressionContext> values =
      new ArrayList<>();

  public AttributeInvocation(
      AttributeInvocationContext ctx, ErrorReporter errorReporter, MethodSignature attachedTo) {
    // TypeUpdater.visitClassOrInterfaceType() should guarantee this is an AttributeType
    type = (AttributeType) ctx.getType();
    invocationCtx = ctx;
    enclosingType = attachedTo.getOuter();

    for (ShadowParser.ConditionalExpressionContext assignmentCtx : ctx.conditionalExpression()) {
      addValue(assignmentCtx);
    }
  }

  /**
   * Associates the given field assignment with its parent attribute invocation and performs sanity
   * checks.
   */
  public void addValue(
      ShadowParser.ConditionalExpressionContext ctx) {
    values.add(ctx);
  }

  public void setSignature(MethodSignature signature) {
    this.signature = signature;
  }

  public MethodSignature getSignature() {
    return signature;
  }

  /** Must be called after type updating to ensure the fields of the AttributeType are populated. */
  public void update(Package packageTree, ErrorReporter errorReporter) {
    try {
      AttributeInterpreter.getAttributeInvocation(this, packageTree, errorReporter);
    } catch (InterpreterException ignored) {
    }
  }


  public List<ShadowParser.ConditionalExpressionContext> getValues() {
    return Collections.unmodifiableList(values);
  }

  public AttributeType getType() {
    return type;
  }

  /**
   * Gets the interpreted value of the given field - only safe to call after constant interpretation
   * has occurred.
   */
  /*public ShadowValue getFieldValue(String fieldName) {
    return values.containsKey(fieldName)
        ? values.get(fieldName).getInterpretedValue()
        : type.getField(fieldName).getInterpretedValue();
  }
*/
  public String getMetaFileText() {
    String text = type.toString(Type.PACKAGES);
    if (!values.isEmpty()) {
      text += "(";
      text +=
          values.stream()
              .map(f -> f.getInterpretedValue().toLiteral())
              .collect(Collectors.joining(", "));
      text += ")";
    }
    return text;
  }
}
