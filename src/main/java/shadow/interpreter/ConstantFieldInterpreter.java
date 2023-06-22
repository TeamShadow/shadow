package shadow.interpreter;

import shadow.Loggers;
import shadow.ShadowException;
import shadow.interpreter.InterpreterException.Error;
import shadow.parse.Context;
import shadow.parse.ShadowParser;
import shadow.parse.ShadowParser.VariableDeclaratorContext;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.Package;
import shadow.typecheck.TypeChecker;
import shadow.typecheck.type.AttributeInvocation;
import shadow.typecheck.type.AttributeType;
import shadow.typecheck.type.MethodSignature;
import shadow.typecheck.type.Type;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A lightweight wrapper around {@link ASTInterpreter} that evaluates compile-time constant fields.
 *
 * <p>In particular, this class handles dependencies between constant fields and ensures no circular
 * references exist.
 */
public class ConstantFieldInterpreter extends ASTInterpreter {

  private final Map<FieldKey, VariableDeclaratorContext> allFields;

  // Used to find circular dependencies - should be cleared after each top-level call to
  // visit(variableDeclarator)
  private final Set<FieldKey> visitedFields = new HashSet<>();

  // The "root" of the current dependency graph being evaluated
  private VariableDeclaratorContext rootFieldCtx = null;

  private ConstantFieldInterpreter(
      Package packageTree,
      ErrorReporter reporter,
      Map<FieldKey, VariableDeclaratorContext> allFields) {
    super(packageTree, reporter);
    this.allFields = allFields;
  }

  /** A key that uniquely identifies a field (including taking its parent type into account) */
  public static class FieldKey {
    public final Type parentType;
    public final String fieldName;

    public FieldKey(Type parentType, String fieldName) {
      this.parentType = parentType;
      this.fieldName = fieldName;
    }

    @Override
    public int hashCode() {
      return Objects.hash(parentType, fieldName);
    }

    @Override
    public boolean equals(Object o) {
      if (!(o instanceof FieldKey)) {
        return false;
      }

      FieldKey other = (FieldKey) o;
      return Objects.equals(parentType, other.parentType)
          && Objects.equals(fieldName, other.fieldName);
    }
  }

  /**
   * Assumes that type-checking has already happened, meaning that all statements are valid and all
   * references to fields are legitimate.
   *
   */
  public static void evaluateConstants(Package packageTree, List<Type> typesIncludingInner)
      throws ShadowException {
    // We also want to process fields from inner types. Order doesn't really matter.


    Map<FieldKey, VariableDeclaratorContext> constantFields = new HashMap<>();

    for (Type type : typesIncludingInner) {
      for (Map.Entry<String, VariableDeclaratorContext> entry : type.getConstants().entrySet()) {
        constantFields.put(new FieldKey(type, entry.getKey()), entry.getValue());
      }
    }

    ErrorReporter errorReporter = new ErrorReporter(Loggers.AST_INTERPRETER);
    ConstantFieldInterpreter visitor =
        new ConstantFieldInterpreter(packageTree, errorReporter, constantFields);
    for (FieldKey fieldKey : constantFields.keySet()) {
      VariableDeclaratorContext fieldCtx = constantFields.get(fieldKey);

      // Skip fields that were evaluated recursively while evaluating other fields
      if (fieldCtx.getInterpretedValue() != null) {
        continue;
      }

      visitor.visitRootField(fieldKey, fieldCtx);
    }


    visitor.printAndReportErrors();
  }

  @Override
  public Void visitPrimaryPrefix(ShadowParser.PrimaryPrefixContext ctx) {
    visitChildren(ctx);

    String image = ctx.getText();
    ShadowValue value = ShadowValue.INVALID;

    if (image.equals("this") || image.equals("super")) {
        addError(
                ctx,
                Error.INVALID_SELF_REFERENCE,
                "Reference " + image + " invalid for compile-time constants");
    }
    else if (ctx.generalIdentifier() != null && !ctx.getModifiers().isTypeName()) {
      String name = ctx.generalIdentifier().getText();
      ShadowValue variable = (ShadowValue) findSymbol(name);
      if (variable != null)
        value = new ShadowVariable(this, name);
      else if( currentType.recursivelyContainsConstant(name))
        value = getConstant(currentType, name, ctx);
      else
        addError(ctx, Error.NON_CONSTANT_REFERENCE);
    }
    else if(ctx.conditionalExpression() != null) {
      value = ctx.conditionalExpression().getInterpretedValue();
    }
    // literal, check expression, copy expression,
    // spawn expression, receive expression, cast expression
    // primitive and function types, and array initializer
    else {
      Context child = (Context) ctx.getChild(0);
      value = child.getInterpretedValue();
      ctx.addModifiers(child.getModifiers());
    }

    ctx.setInterpretedValue(value);
    curPrefix.set(0, ctx); // so that the suffix can figure out where it's at

    return null;
  }


  @Override
  public Void visitScopeSpecifier(ShadowParser.ScopeSpecifierContext ctx) {
    visitChildren(ctx);

    // Always part of a suffix, thus always has a prefix
    Context prefixNode = curPrefix.getFirst();
    Type prefixType = prefixNode.getType();

    String name = ctx.Identifier().getText();
      // Check field first
      if (prefixType.containsField(name)) {
        addError(
                ctx,
                Error.ILLEGAL_ACCESS,
                "Field "
                        + name
                        + " cannot be referenced in a compile-time constant because it is not constant");
        ctx.setInterpretedValue(ShadowValue.INVALID);
      } else if (prefixType.recursivelyContainsConstant(name))
        ctx.setInterpretedValue(getConstant(prefixType, name, ctx));

    return null;
  }

  public void visitRootField(FieldKey rootFieldKey, VariableDeclaratorContext rootFieldCtx) {
    // TODO: Consider calling BaseChecker#clear (but make sure errors are reported before clearing)
    this.rootFieldCtx = rootFieldCtx;
    visitedFields.clear();
    visitedFields.add(rootFieldKey);
    visit(rootFieldCtx);
  }

  // This should be the entry point for constant interpretation. Assumes the variable
  // declarator includes a definition.
  @Override
  public Void visitVariableDeclarator(ShadowParser.VariableDeclaratorContext ctx) {
    // Normally BaseChecker would do this for us if we weren't using VariableDeclarator
    // as an entry point
    Type oldType = currentType;
    currentType = ctx.getEnclosingType();

    visitChildren(ctx);

    ShadowValue value = ShadowValue.INVALID;
    if (ctx.conditionalExpression() != null) {
      try {
        // This cast seems wacky, but if we don't do it, then constant long X = 5; stores 5 into X,
        // not 5L
        value = ctx.conditionalExpression().getInterpretedValue().cast(ctx.getType());
      } catch (InterpreterException e) {
        addError(e.setContext(ctx));
      }
    }
    ctx.setInterpretedValue(value);

    currentType = oldType;
    return null;
  }

  public ShadowValue getConstant(Type parentType, String fieldName, Context referenceCtx) {
    FieldKey fieldKey = new FieldKey(parentType, fieldName);

    if (!allFields.containsKey(fieldKey)) {
      addError(Error.UNKNOWN_REFERENCE.getException(referenceCtx));
    }
    VariableDeclaratorContext fieldCtx = allFields.get(fieldKey);

    // We don't need to check if a node has been visited if it already has a value - that
    // couldn't have happened if it led to a cycle
    if (fieldCtx.getInterpretedValue() != null) {
      return fieldCtx.getInterpretedValue();
    }

    // If we've already seen this field without getting a value for it, that means we've found
    // a cycle
    if (visitedFields.contains(fieldKey)) {
      addError(Error.CIRCULAR_REFERENCE.getException(rootFieldCtx.generalIdentifier()));
      return ShadowValue.INVALID;
    }
    visitedFields.add(fieldKey);

    visit(fieldCtx);
    return fieldCtx.getInterpretedValue();
  }
}
