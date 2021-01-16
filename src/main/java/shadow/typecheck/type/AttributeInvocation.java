package shadow.typecheck.type;

import shadow.interpreter.ShadowValue;
import shadow.parse.ShadowParser;
import shadow.parse.ShadowParser.AttributeInvocationContext;
import shadow.parse.ShadowVisitorErrorReporter;
import shadow.typecheck.TypeCheckException.Error;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * Represents a particular invocation of an attribute type, including any fields set during that invocation. E.g.
 * {@code SomeAttribute(a = "alpha", b = 5)}.
 */
public class AttributeInvocation {
    private final AttributeType type;
    private final AttributeInvocationContext invocationCtx; // The AST node for this invocation
    private final Type enclosingType;

    // Note that this does not contain the default expressions provided in the attribute declaration
    private final Map<String, ShadowParser.VariableDeclaratorContext> fieldExpressions = new HashMap<>();

    public AttributeInvocation(AttributeInvocationContext ctx, ShadowVisitorErrorReporter errorReporter, MethodSignature attachedTo) {
        // TypeUpdater.visitClassOrInterfaceType() should guarantee this is an AttributeType
        type = (AttributeType) ctx.getType();
        invocationCtx = ctx;
        enclosingType = attachedTo.getOuter();

        for (ShadowParser.VariableDeclaratorContext assignmentCtx : ctx.variableDeclarator()) {
            addFieldAssignment(assignmentCtx, errorReporter);
        }
    }

    /** Associates the given field assignment with its parent attribute invocation and performs sanity checks. */
    public void addFieldAssignment(
            ShadowParser.VariableDeclaratorContext ctx, ShadowVisitorErrorReporter errorReporter) {
        String fieldName = ctx.generalIdentifier().getText();

        // Repeated field assignment
        if (fieldExpressions.containsKey(fieldName)) {
            errorReporter.addError(
                ctx,
                Error.REPEATED_ASSIGNMENT,
                "Field \"" + fieldName + "\" was assigned more than once",
                type);
            return;
        }

        fieldExpressions.put(fieldName, ctx);
    }

    /** Must be called after type updating to ensure the fields of the AttributeType are populated. */
    public void updateFieldTypes(ShadowVisitorErrorReporter errorReporter) {
        for (String fieldName : fieldExpressions.keySet()) {
            ShadowParser.VariableDeclaratorContext fieldCtx = fieldExpressions.get(fieldName);

            // Statement checker reports an error if this isn't true
            if (type.containsField(fieldName)) {
                // Enclosing type should match the method's enclosing type, not the attribute's
                fieldCtx.setEnclosingType(enclosingType);
                fieldCtx.setType(type.getField(fieldName).getType());
            }
        }

        // Check for missing fields (i.e. required by AttributeType but not provided in this invocation)
        for (String requiredFieldName : type.getUninitializedFields()) {
            if (!fieldExpressions.containsKey(requiredFieldName)) {
                errorReporter.addError(
                    invocationCtx, Error.UNINITIALIZED_FIELD, "A value must be provided for \""
                        + requiredFieldName + "\" within " + type.getTypeName(), type);
            }
        }
    }

    public Map<String, ShadowParser.VariableDeclaratorContext> getFieldAssignments() {
        return Collections.unmodifiableMap(fieldExpressions);
    }

    public AttributeType getType() {
        return type;
    }

    /**
     * Gets the interpreted value of the given field - only safe to call after constant interpretation
     * has occurred.
     */
    public ShadowValue getFieldValue(String fieldName) {
        return fieldExpressions.containsKey(fieldName)
                ? fieldExpressions.get(fieldName).getInterpretedValue()
                : type.getField(fieldName).getInterpretedValue();
    }

    public Set<String> getFieldNames() {
        return type.getFields().keySet();
    }

    public String getMetaFileText() {
        String text = type.toString(Type.PACKAGES);
        if (!fieldExpressions.isEmpty()) {
            text += "(";
            text += fieldExpressions.entrySet().stream()
                    .map(f -> f.getKey() + " = " + f.getValue().getInterpretedValue().toLiteral())
                    .collect(Collectors.joining(", "));
            text += ")";
        }
        return text;
    }
}
