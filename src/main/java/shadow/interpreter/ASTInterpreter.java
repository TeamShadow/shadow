package shadow.interpreter;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import shadow.ShadowException;
import shadow.parse.Context;
import shadow.parse.ShadowParser;
import shadow.parse.ShadowParser.CreateContext;
import shadow.typecheck.BaseChecker;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.StatementChecker;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.Type;
import shadow.typecheck.Package;

import java.util.*;

import static shadow.interpreter.InterpreterException.Error;
import static java.util.stream.Collectors.toList;

/**
 * Interpreter that walks AST nodes in order to determine the values of expressions.
 *
 * Currently, this is only used by {@link ConstantFieldInterpreter} to determine the values of
 * compile-time constant fields.
 */
public class ASTInterpreter extends BaseChecker {

    public ASTInterpreter(Package packageTree, ErrorReporter reporter) {
        super(packageTree, reporter);
    }

    // Converts a Context object to a single "token" containing the same text.
    // Useful for passing some .*Context types to code that operates on tokens.
    private static Token toToken(Context ctx) {
        return new CommonToken(-1, ctx.getText());
    }

    /**
     * Given a primarySuffix, finds the primarySuffix or primaryPrefix immediately preceding it.
     *
     * This method might be useful in other contexts outside of {@link ASTInterpreter}, e.g.
     * TACBuilder.getPrefix() serves the same purposes. TODO: Find a better home.
     */
    public static Context getPreviousSuffix(ShadowParser.PrimarySuffixContext ctx) {
        ShadowParser.PrimaryExpressionContext primaryExpression =
                (ShadowParser.PrimaryExpressionContext) ctx.getParent();

        int index = primaryExpression.primarySuffix().indexOf(ctx);
        return index == 0 ? primaryExpression.primaryPrefix() : primaryExpression.primarySuffix(index - 1);
    }

    /** Finds the nearest ancestor type containing a field with the requested name */
    private static Type findNearestTypeContainingField(Type current, String fieldName) {
        while (!current.containsField(fieldName)) {
            current = current.getOuter();
        }

        return current;
    }

    // Assumes that operands has at least one element, and that operators has
    // operators.size() - 1 elements.
    private ShadowValue evaluateBinaryOperation(
            Context parent, List<? extends Context> operands, List<Token> operators) {
        // exp ( '<operator>' exp )*
        Iterator<? extends Context> operandIterator = operands.iterator();
        ShadowValue left = operandIterator.next().getInterpretedValue();

        for (Token operatorToken : operators) {
            BinaryOperator operator = BinaryOperator.fromString(operatorToken.getText());
            ShadowValue right = operandIterator.next().getInterpretedValue();

            try {
                left = left.apply(operator, right);
            } catch (ShadowException e) {
                addError(e);
                return ShadowValue.INVALID;
            }
        }

        return left;
    }

    // This should be the entry point for all interpretation. Assumes the variable
    // declarator includes a definition.
    @Override
    public Void visitVariableDeclarator(ShadowParser.VariableDeclaratorContext ctx) {
        // Normally BaseChecker would do this for us if we weren't using VariableDeclarator
        // as an entry point
        Type oldType = currentType;
        currentType = ctx.getEnclosingType();

        visitChildren(ctx);

        ctx.setInterpretedValue(ctx.conditionalExpression().getInterpretedValue());

        currentType = oldType;
        return null;
    }

    @Override
    public Void visitConditionalExpression(ShadowParser.ConditionalExpressionContext ctx) {
        // coalesceExpression ('?' throwOrConditionalExpression ',' throwOrConditionalExpression)?
        visitChildren(ctx);
        ShadowValue value = ctx.coalesceExpression().getInterpretedValue();

        if (ctx.getChildCount() > 1 && !(value instanceof ShadowInvalid)) {
            boolean condition = ((ShadowBoolean) value).getValue();
            value = ctx.throwOrConditionalExpression(condition ? 0 : 1).getInterpretedValue();
        }

        ctx.setInterpretedValue(value);
        return null;
    }

    @Override
    public Void visitThrowOrConditionalExpression(ShadowParser.ThrowOrConditionalExpressionContext ctx) {
        visitChildren(ctx);

        // We assume there won't be a throw statement in a constant expression
        ctx.setInterpretedValue(ctx.conditionalExpression().getInterpretedValue());

        return null;
    }

    @Override
    public Void visitCoalesceExpression(ShadowParser.CoalesceExpressionContext ctx) {
        // conditionalOrExpression ('??' conditionalOrExpression)* ('??' throwStatement)?
        visitChildren(ctx);

        // We assume there won't be a throw statement in a constant expression
        ctx.setInterpretedValue(
                evaluateBinaryOperation(
                        ctx, ctx.conditionalOrExpression(), ctx.operators));

        return null;
    }

    @Override
    public Void visitConditionalOrExpression(ShadowParser.ConditionalOrExpressionContext ctx) {
        visitChildren(ctx);

        ctx.setInterpretedValue(
                evaluateBinaryOperation(
                        ctx, ctx.conditionalExclusiveOrExpression(), ctx.operators));

        return null;
    }

    @Override
    public Void visitConditionalExclusiveOrExpression(ShadowParser.ConditionalExclusiveOrExpressionContext ctx) {
        visitChildren(ctx);

        ctx.setInterpretedValue(
                evaluateBinaryOperation(
                        ctx, ctx.conditionalAndExpression(), ctx.operators));

        return null;
    }

    @Override
    public Void visitConditionalAndExpression(ShadowParser.ConditionalAndExpressionContext ctx) {
        visitChildren(ctx);

        ctx.setInterpretedValue(
                evaluateBinaryOperation(
                        ctx, ctx.bitwiseOrExpression(), ctx.operators));

        return null;
    }

    @Override
    public Void visitBitwiseOrExpression(ShadowParser.BitwiseOrExpressionContext ctx) {
        visitChildren(ctx);

        ctx.setInterpretedValue(
                evaluateBinaryOperation(
                        ctx, ctx.bitwiseExclusiveOrExpression(), ctx.operators));

        return null;
    }

    @Override
    public Void visitBitwiseExclusiveOrExpression(ShadowParser.BitwiseExclusiveOrExpressionContext ctx) {
        visitChildren(ctx);

        ctx.setInterpretedValue(
                evaluateBinaryOperation(
                        ctx, ctx.bitwiseAndExpression(), ctx.operators));

        return null;
    }

    @Override
    public Void visitBitwiseAndExpression(ShadowParser.BitwiseAndExpressionContext ctx) {
        visitChildren(ctx);

        ctx.setInterpretedValue(
                evaluateBinaryOperation(
                        ctx, ctx.equalityExpression(), ctx.operators));

        return null;
    }

    @Override
    public Void visitEqualityExpression(ShadowParser.EqualityExpressionContext ctx) {
        visitChildren(ctx);

        ctx.setInterpretedValue(
                evaluateBinaryOperation(
                        ctx, ctx.isExpression(), ctx.operators));

        return null;
    }

    @Override
    public Void visitIsExpression(ShadowParser.IsExpressionContext ctx) {
        visitChildren(ctx);

        if (ctx.type() != null) {
            addError(Error.UNSUPPORTED_OPERATION.getException(ctx));
            ctx.setInterpretedValue(ShadowValue.INVALID);
        } else {
            ctx.setInterpretedValue(ctx.relationalExpression().getInterpretedValue());
        }

        return null;
    }

    @Override
    public Void visitRelationalExpression(ShadowParser.RelationalExpressionContext ctx) {
        visitChildren(ctx);

        ctx.setInterpretedValue(
                evaluateBinaryOperation(
                        ctx, ctx.concatenationExpression(), ctx.operators));

        return null;
    }

    @Override
    public Void visitConcatenationExpression(ShadowParser.ConcatenationExpressionContext ctx) {
        visitChildren(ctx);

        ctx.setInterpretedValue(
                evaluateBinaryOperation(
                        ctx, ctx.shiftExpression(), ctx.operators));

        return null;
    }

    @Override
    public Void visitShiftExpression(ShadowParser.ShiftExpressionContext ctx) {
        visitChildren(ctx);

        // The shift operator is a parser rule instead of a literal token (for disambiguation
        // reasons), so we create a proxy token containing its text
        ctx.setInterpretedValue(
                evaluateBinaryOperation(
                        ctx,
                        ctx.rotateExpression(),
                        ctx.operators.stream().map(ASTInterpreter::toToken).collect(toList())));

        return null;
    }

    @Override
    public Void visitRotateExpression(ShadowParser.RotateExpressionContext ctx) {
        visitChildren(ctx);

        // The rotate operator is a parser rule instead of a literal token (for disambiguation
        // reasons), so we create a proxy token containing its text
        ctx.setInterpretedValue(
                evaluateBinaryOperation(
                        ctx,
                        ctx.additiveExpression(),
                        ctx.operators.stream().map(ASTInterpreter::toToken).collect(toList())));

        return null;
    }

    @Override
    public Void visitAdditiveExpression(ShadowParser.AdditiveExpressionContext ctx) {
        visitChildren(ctx);

        ctx.setInterpretedValue(
                evaluateBinaryOperation(
                        ctx, ctx.multiplicativeExpression(), ctx.operators));

        return null;
    }

    @Override
    public Void visitMultiplicativeExpression(ShadowParser.MultiplicativeExpressionContext ctx) {
        visitChildren(ctx);

        ctx.setInterpretedValue(
                evaluateBinaryOperation(
                        ctx, ctx.unaryExpression(), ctx.operators));

        return null;
    }

    @Override
    public Void visitUnaryExpression(ShadowParser.UnaryExpressionContext ctx) {
        visitChildren(ctx);

        ShadowValue value = ShadowValue.INVALID;

        if (ctx.operator != null) {
            UnaryOperator operator = UnaryOperator.fromString(ctx.operator.getText());
            try {
                value = ctx.unaryExpression().getInterpretedValue().apply(operator);
            } catch (ShadowException e) {
                addError(e);
            }
        } else if (ctx.primaryExpression() != null) {
            value = ctx.primaryExpression().getInterpretedValue();
        } else {
            // At the time of writing, only inlineMethodDefinition falls in this case
            addError(Error.UNSUPPORTED_OPERATION.getException(ctx));
        }

        ctx.setInterpretedValue(value);

        return null;
    }

    @Override
    public Void visitPrimaryExpression(ShadowParser.PrimaryExpressionContext ctx) {
        visitChildren(ctx);

        ShadowValue value = ShadowValue.INVALID;
        ShadowParser.PrimarySuffixContext lastSuffix = ctx.primarySuffix().isEmpty()
                ? null : ctx.primarySuffix().get(ctx.primarySuffix().size() - 1);

        if (lastSuffix == null) {
            value = visitStandalonePrimaryPrefix(ctx.primaryPrefix());
        } else if (lastSuffix.scopeSpecifier() != null) {
            // Ends with scopeSpecifier, e.g. ParentType:ChildType:fieldName
            //                                                     ^^^^^^^^^
            value = dereferenceField(
                    getPreviousSuffix(lastSuffix).getType(), lastSuffix.scopeSpecifier().Identifier().getText(), ctx);
        }
        else if(lastSuffix.allocation() != null && lastSuffix.allocation().create() != null) {
        	CreateContext create = lastSuffix.allocation().create();
        	Context previousSuffix = getPreviousSuffix(lastSuffix);
        	if(previousSuffix.getType() == Type.STRING) {
        		if(create.conditionalExpression().size() == 0)
        			value = new ShadowString("");
        		else if(create.conditionalExpression().size() == 1 && create.conditionalExpression(0).getType() == Type.STRING)
        			value = create.conditionalExpression(0).getInterpretedValue();
        		else
        			addError(ctx, Error.INVALID_CREATE, "Cannot call create " + create.getSignature());
        	}
        	else
        		addError(ctx, Error.INVALID_CREATE, "For a constant field, the String type is the only one that can have a create");
        }

        if (value == ShadowValue.INVALID) {
            addError(Error.UNSUPPORTED_OPERATION.getException(ctx));
        }

        ctx.setInterpretedValue(value);
        return null;
    }

    private ShadowValue visitStandalonePrimaryPrefix(ShadowParser.PrimaryPrefixContext ctx) {
        ShadowValue value = ShadowValue.INVALID;

        if (ctx.literal() != null) {
            // Interpreted values for literals are set by StatementChecker#visitLiteral()
            value = ctx.literal().getInterpretedValue();

            // This happens because .meta files containing literals aren't processed by
            // StatementChecker. TODO: Find a better way to deal with them.
            if (value == null) {
                value = StatementChecker.processLiteral(ctx.literal(), getErrorReporter());
            }
        } else if (ctx.generalIdentifier() != null) {
            if (ctx.unqualifiedName() != null) {
                // This would be something of the form "package@Class" with no scope specifier
                // (which will probably have been caught during type-checking)
                addError(Error.UNSUPPORTED_OPERATION.getException(ctx));
            } else {
                String fieldName = ctx.generalIdentifier().getText();
                value = dereferenceField(findNearestTypeContainingField(currentType, fieldName), fieldName, ctx);
            }
        }

        return value;
    }

    /**
     * Determines the value associated with a given field, keyed by its parent type and name.
     *
     * {@link ASTInterpreter} does not support this by default - it should be overriden by classes
     * like {@link ConstantFieldInterpreter} that track field values.
     */
    protected ShadowValue dereferenceField(Type parentType, String fieldName, Context referenceCtx) {
        throw new UnsupportedOperationException(
                "ASTInterpreter cannot dereference fields. Did you mean to use ConstantFieldInterpreter?");
    }
}
