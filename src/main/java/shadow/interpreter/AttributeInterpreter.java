package shadow.interpreter;

import shadow.Loggers;
import shadow.parse.ShadowParser;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.Package;

public class AttributeInterpreter extends ASTInterpreter {
    public AttributeInterpreter(Package packageTree, ErrorReporter reporter) {
        super(packageTree, reporter);
    }

    public static ShadowValue getAttributeInvocationArgument(ShadowParser.ConditionalExpressionContext ctx, Package packageTree) {
        // Skip if already evaluated
        if (ctx.getInterpretedValue() != null) {
            return ctx.getInterpretedValue();
        }

        AttributeInterpreter visitor = new AttributeInterpreter(packageTree, new ErrorReporter(Loggers.AST_INTERPRETER));
        visitor.visit(ctx);

        return ctx.getInterpretedValue();
    }
}
