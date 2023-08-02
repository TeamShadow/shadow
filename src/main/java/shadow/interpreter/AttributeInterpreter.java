package shadow.interpreter;

import shadow.parse.ShadowParser;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.Package;
import shadow.typecheck.type.AttributeInvocation;
import shadow.typecheck.type.Type;

import java.util.List;

public class AttributeInterpreter extends ASTInterpreter {
  public AttributeInterpreter(Package packageTree, ErrorReporter reporter) {
    super(packageTree, reporter);
  }

  public static ShadowValue getAttributeInvocationArgument(
      Type enclosingType,
      ShadowParser.ConditionalExpressionContext ctx,
      Package packageTree,
      ErrorReporter errorReporter) {
    // Skip if already evaluated
    if (ctx.getInterpretedValue() != null)
      return ctx.getInterpretedValue();

    ctx.setEnclosingType(enclosingType);
    AttributeInterpreter visitor = new AttributeInterpreter(packageTree, errorReporter);
    visitor.currentType = enclosingType;
    visitor.visit(ctx);

    return ctx.getInterpretedValue();
  }

  public static ShadowObject getAttributeInvocation(
      AttributeInvocation attribute, Package packageTree, ErrorReporter errorReporter)
      throws InterpreterException {
    if (attribute.getInvocationContext().getInterpretedValue() != null)
      return (ShadowObject) attribute.getInvocationContext().getInterpretedValue();

    List<ShadowParser.ConditionalExpressionContext> arguments = attribute.getValues();
    ShadowValue[] argumentValues = new ShadowValue[arguments.size()];
    for (int i = 0; i < arguments.size(); ++i)
      argumentValues[i] =
          getAttributeInvocationArgument(
              attribute.getEnclosingType(), arguments.get(i), packageTree, errorReporter);

    ShadowObject value =
        AttributeInterpreter.callCreate(
            packageTree, errorReporter, attribute.getType(), argumentValues);
    attribute.getInvocationContext().setInterpretedValue(value);
    return value;
  }
}
