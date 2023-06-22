package shadow.interpreter;

import shadow.Loggers;
import shadow.parse.ShadowParser;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.Package;
import shadow.typecheck.type.AttributeInvocation;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.Type;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AttributeInterpreter extends ASTInterpreter {
    public AttributeInterpreter(Package packageTree, ErrorReporter reporter) {
        super(packageTree, reporter);
    }

    public static ShadowValue getAttributeInvocationArgument(Type enclosingType, ShadowParser.ConditionalExpressionContext ctx, Package packageTree, ErrorReporter errorReporter) {
        // Skip if already evaluated
        if (ctx.getInterpretedValue() != null) {
            return ctx.getInterpretedValue();
        }

        ctx.setEnclosingType(enclosingType);
        AttributeInterpreter visitor = new AttributeInterpreter(packageTree, errorReporter);
        visitor.visit(ctx);

        return ctx.getInterpretedValue();
    }

    public static ShadowValue getAttributeInvocation(AttributeInvocation attribute, Package packageTree, ErrorReporter errorReporter) throws InterpreterException {
        if (attribute.getInvocationContext().getInterpretedValue() != null)
            return attribute.getInvocationContext().getInterpretedValue();

        List<ShadowParser.ConditionalExpressionContext> arguments = attribute.getValues();
        ShadowValue[] argumentValues = new ShadowValue[arguments.size()];
        for (int i = 0; i < arguments.size(); ++i)
            argumentValues[i] = getAttributeInvocationArgument(attribute.getEnclosingType(), arguments.get(i), packageTree, errorReporter);

        ShadowObject value = AttributeInterpreter.callCreate(packageTree, errorReporter, attribute.getType(), argumentValues);
        attribute.getInvocationContext().setInterpretedValue(value);
        return value;
    }

    // Evaluates fields on attribute types and attribute invocations. We don't store these in the
    // constantFields because they can't be referenced by each other or by other constants.
    //public static void evaluateAttributes(List<Type> typesIncludingInner) {

    // Not needed because interpretation happens for each method with attributes in the statement checker

    /*
    for (Type type : typesIncludingInner) {
      if (type instanceof AttributeType) {
        for (Map.Entry<String, VariableDeclaratorContext> field :
            type.getFields().entrySet()) {
          visitor.visitRootField(new FieldKey(type, field.getKey()), field.getValue());
        }
      }
    }
    */
/*
        List<MethodSignature> allMethods =
                typesIncludingInner.stream()
                        .map(Type::getAllMethods)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());
        List<AttributeInvocation> attributeInvocations =
                allMethods.stream()
                        .map(MethodSignature::getAttributes)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());
        for (AttributeInvocation attributeInvocation : attributeInvocations) {
            for (ShadowParser.ConditionalExpressionContext ctx :
                    attributeInvocation.getValues()) {
                //TODO: Actually call constructor via interpretation
                //visitor.visitRootField(
                //  new FieldKey(attributeInvocation.getType(), field.getKey()), field.getValue());
            }
        }

 */

        /*
        for (MethodSignature method : allMethods) {
            method.processAttributeValues(errorReporter);
        }
        */
    //}
}
