package shadow.typecheck.type;

import shadow.parse.ShadowParser;
import shadow.parse.ShadowParser.AttributeInvocationContext;
import shadow.parse.ShadowVisitorErrorReporter;
import shadow.typecheck.TypeCheckException.Error;

import java.util.*;

/**
 * Represents a particular invocation of an attribute type, including any fields set during that invocation. E.g.
 * {@code SomeAttribute(a = "alpha", b = 5)}.
 */
public class AttributeInvocation {
    private final AttributeType type;
    private final AttributeInvocationContext invocationCtx; // The AST node for this invocation

    // Note that this does not contain the default expressions provided in the attribute declaration
    private final Map<String, ShadowParser.VariableDeclaratorContext> fieldExpressions = new HashMap<>();

    public AttributeInvocation(AttributeInvocationContext ctx, ShadowVisitorErrorReporter errorReporter) {
        // TypeUpdater.visitClassOrInterfaceType() should guarantee this is an AttributeType
        type = (AttributeType) ctx.getType();
        invocationCtx = ctx;

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

    /** Must be called after type updating to ensure the fields of this attribute will all have values. */
    public void checkForMissingFields(ShadowVisitorErrorReporter errorReporter) {
        for (String requiredFieldName : type.getUninitializedFields()) {
            if (!fieldExpressions.containsKey(requiredFieldName)) {
                errorReporter.addError(
                    invocationCtx, Error.UNINITIALIZED_FIELD, "A value must be provided for \""
                        + requiredFieldName + "\" within " + type.getTypeName(), type);
            }
        }
    }

    public AttributeType getType() {
        return type;
    }
}
