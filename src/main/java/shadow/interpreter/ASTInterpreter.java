package shadow.interpreter;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import shadow.ShadowException;
import shadow.interpreter.InterpreterException.Error;
import shadow.parse.Context;
import shadow.parse.ShadowParser;
import shadow.tac.TACVariable;
import shadow.tac.nodes.*;
import shadow.typecheck.*;
import shadow.typecheck.Package;
import shadow.typecheck.type.InstantiationException;
import shadow.typecheck.type.*;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Interpreter that walks AST nodes in order to determine the values of expressions.
 *
 * <p>Currently, this is only used by {@link ConstantFieldInterpreter} to determine the values of
 * compile-time constant fields.
 */
public class ASTInterpreter extends ScopedChecker {

  /* Stack for current prefix (needed for arbitrarily long chains of expressions). */
  protected final LinkedList<Context> curPrefix = new LinkedList<>();
  private final Map<String, ShadowValue> fields = new HashMap<>();
  private ShadowValue[] returnValues;

  private ShadowObject curObject;

  public ASTInterpreter(Package packageTree, ErrorReporter reporter) {
    super(packageTree, reporter);
  }

  /*
  public ASTInterpreter(Package packageTree, ErrorReporter reporter, ShadowObject object) {
    super(packageTree, reporter);
    curObject = object;
  }
  */

  // Converts a Context object to a single "token" containing the same text.
  // Useful for passing some .*Context types to code that operates on tokens.
  private static Token toToken(Context ctx) {
    return new CommonToken(-1, ctx.getText());
  }

  // Assumes that operands has at least one element, and that operators has
  // operators.size() - 1 elements.
  private ShadowValue evaluateBinaryOperation(
          Context parent, List<? extends Context> operands, List<Token> operators) {

    try {
      ShadowValue current = operands.get(0).getInterpretedValue();
      for (int i = 0; i < parent.getOperations().size(); i++) {
        String op = operators.get(i).getText();
        BinaryOperator operator = BinaryOperator.fromString(op);
        ShadowValue next = operands.get(i + 1).getInterpretedValue();
        MethodSignature signature = parent.getOperations().get(i);
        boolean isCompare =
                (op.equals("<")
                        || op.equals(">")
                        || op.equals("<=")
                        || op.equals(">=")
                        || op.equals("==")
                        || op.equals("==="));
        Type currentType = current.getType();
        next = next.cast(signature.getParameterTypes().get(0).getType());
        if (currentType.isPrimitive() && signature.isImport()) { // operation based on method
          if (!isCompare) current = current.cast(signature.getReturnTypes().get(0).getType());
          current = current.apply(operator, next);
        } else {
          current = current.callMethod(signature.getSymbol(), next)[0];
        }
      }

      return current;
    } catch (InterpreterException e) {
      addError(e.setContext(parent));
      return ShadowValue.INVALID;
    }
  }


  protected ShadowValue resolveValue(ShadowValue value, Context ctx) {
    if(value instanceof ShadowReference) {
      ShadowReference reference = (ShadowReference) value;
      try {
        value = reference.get();
      } catch (InterpreterException e) {
        addError(ctx, Error.INVALID_REFERENCE);
      }
    }
    return value;
  }

  // Dereferences into PropertyType or IndexType for getter, if needed
  private ModifiedType resolveType(ModifiedType node) {
    Type type = node.getType();
    if (type instanceof PropertyType) { // includes SubscriptType as well
      PropertyType getSetType = (PropertyType) type;
      if (getSetType.isGettable()) return getSetType.getGetType();
      else {
        String kind = (type instanceof SubscriptType) ? "Subscript " : "Property ";
        if (node instanceof Context)
          addError(
              (Context) node,
              Error.ILLEGAL_ACCESS,
              kind + node + " does not have appropriate get access");
        else
          addError(
              new InterpreterException(
                  Error.ILLEGAL_ACCESS, kind + node + " does not have appropriate get access"));
        return new SimpleModifiedType(Type.UNKNOWN);
      }
    } else return node;
  }

  // This should be the entry point for all interpretation. Assumes the variable
  // declarator includes a definition.
  @Override
  public Void visitVariableDeclarator(ShadowParser.VariableDeclaratorContext ctx) {
    visitChildren(ctx);

    ShadowValue value = ShadowValue.INVALID;
    if (ctx.conditionalExpression() != null) {
      try {
        // This cast seems wacky, but if we don't do it, then constant long X = 5; stores 5 into X,
        // not 5L
        value = resolveValue(ctx.conditionalExpression().getInterpretedValue(), ctx).cast(ctx.getType());
        addSymbol(ctx.generalIdentifier().Identifier().getText(), value);
      } catch (InterpreterException e) {
        addError(e.setContext(ctx));
      }
    }
    ctx.setInterpretedValue(value);
    return null;
  }

  /**
   * attributeFieldAssignment is similar to variableDeclarator, but doesn't support @Override public
   * Void visitAttributeFieldAssignment(ShadowParser.AttributeFieldAssignmentContext ctx) { return
   * super.visitAttributeFieldAssignment(ctx); }
   */
  @Override
  public Void visitConditionalExpression(ShadowParser.ConditionalExpressionContext ctx) {
    // coalesceExpression ('?' throwOrConditionalExpression ',' throwOrConditionalExpression)?
    visitChildren(ctx);
    ShadowValue value = ShadowValue.INVALID;

    try {
      if (ctx.getChildCount() > 1) {
        if (ctx.coalesceExpression().getInterpretedValue() instanceof ShadowBoolean) {
          boolean condition =
              ((ShadowBoolean) ctx.coalesceExpression().getInterpretedValue()).getValue();
          value =
              ctx.throwOrConditionalExpression(condition ? 0 : 1)
                  .getInterpretedValue()
                  .cast(ctx.getType());
        } else
          addError(
              ctx.coalesceExpression(),
              Error.INVALID_TYPE,
              "Supplied type "
                  + value.getType()
                  + " cannot be used in the condition of a ternary operator, boolean type required",
              value.getType());
      } else value = ctx.coalesceExpression().getInterpretedValue();
    } catch (InterpreterException e) {
      addError(e.setContext(ctx));
    }

    ctx.setInterpretedValue(value);
    return null;
  }


  public static ShadowValue[] callMethod(Package packageTree, ErrorReporter errorReporter, ShadowObject object, String methodName, SequenceType typeArguments, ShadowValue ... values) throws InterpreterException {
    ClassType type = object.getType();
    SequenceType arguments = new SequenceType(Arrays.asList(values));

    List<ShadowException> errors = new ArrayList<>();
    MethodSignature signature = type.getMatchingMethod(methodName, arguments, typeArguments, errors);
    if (signature == null) throw new InterpreterException(Error.INVALID_METHOD, errors.get(0).getMessage());

    Context methodNode = signature.getNode();

    if (methodNode.isFromMetaFile())
      throw new InterpreterException(Error.UNAVAILABLE_SOURCE, "Source code for method " + type + "." + signature + " is not available for interpretation");
    // Autogenerated method (index, get, set)
    else if(methodNode.getParent() == null) {
      if(signature.isGet() && values.length == 0)
        return new ShadowValue[]{object.getField(methodName)};
      else if(signature.isSet() && values.length == 1) {
        object.setField(methodName, values[0]);
        return new ShadowValue[0];
      }
      else
        throw new InterpreterException(Error.INVALID_METHOD, "Method " +  type + "." + signature + " is not supported for interpretation");
    }
    else {
      ASTInterpreter interpreter = new ASTInterpreter(packageTree, errorReporter);
      interpreter.currentType = type;
      interpreter.currentMethod.addFirst(methodNode);
      interpreter.openScope();
      for (int i = 0; i < values.length; ++i)
        interpreter.addSymbol(signature.getMethodType().getParameterNames().get(i), values[i]);
      interpreter.visit(signature.getNode());
      interpreter.closeScope();
      return interpreter.returnValues;
    }
  }


  @Override
  public Void visitReturnStatement(ShadowParser.ReturnStatementContext ctx) {
    visitChildren(ctx);

    MethodType methodType = (MethodType) (currentMethod.getFirst().getType());
    SequenceType returnTypes = methodType.getReturnTypes();

    if (ctx.rightSide() == null)
      returnValues = new ShadowValue[0];
    else if(ctx.rightSide().conditionalExpression() != null){
      returnValues = new ShadowValue[1];
      ShadowValue value = ctx.rightSide().conditionalExpression().getInterpretedValue();
      try {
        returnValues[0] = value.cast(returnTypes.getType(0));
      } catch (InterpreterException e) {
        addError(ctx, Error.INVALID_CAST, "Cannot cast type " + value.getType() + " to " + returnTypes.getType(0));
      }
    }
    else { // sequence type return
      List<ShadowParser.ConditionalExpressionContext> values = ctx.rightSide().sequenceRightSide().conditionalExpression();
      returnValues = new ShadowValue[values.size()];
      for (int i = 0; i < values.size(); ++i) {
        ShadowValue value = values.get(i).getInterpretedValue();
        try {
          returnValues[i] = value.cast(returnTypes.getType(i));
        } catch (InterpreterException e) {
          addError(ctx, Error.INVALID_CAST, "Cannot cast type " + value.getType() + " to " + returnTypes.getType(0));
        }
      }
    }

    return null;
  }


  private static void initializeFields(ASTInterpreter interpreter, Type type) {
    // First, fill in all the values that are marked outside the create
    for (Map.Entry<String, ShadowParser.VariableDeclaratorContext> entry : type.getFields().entrySet()) {
      if (entry.getValue().conditionalExpression() != null) {
        ShadowParser.ConditionalExpressionContext expression = entry.getValue().conditionalExpression();
        interpreter.visitConditionalExpression(expression);
        interpreter.fields.put(entry.getKey(), expression.getInterpretedValue());
      }
    }
  }

  public static ShadowObject callCreate(Package packageTree, ErrorReporter errorReporter, ClassType type, ShadowValue ... values) throws InterpreterException {
    SequenceType arguments = new SequenceType(Arrays.asList(values));
    ShadowObject parent = null;

    List<ShadowException> errors = new ArrayList<>();
    MethodSignature signature = type.getMatchingMethod("create", arguments, null, errors);

    if (signature == null)
      throw new InterpreterException(Error.INVALID_CREATE, errors.get(0).getMessage());

    Context methodNode = signature.getNode();
    ASTInterpreter interpreter = new ASTInterpreter(packageTree, errorReporter);
    interpreter.currentType = type;
    interpreter.currentMethod.addFirst(methodNode);
    interpreter.openScope();
    // Add arguments to scope
    for (int i = 0; i < values.length; ++i)
      interpreter.addSymbol(signature.getMethodType().getParameterNames().get(i), values[i]);

    if (methodNode.getParent() == null) { // default (empty) create
      if (type.getExtendType() != null)
        parent = callCreate(packageTree, errorReporter, type.getExtendType());

      interpreter.curObject = new ShadowObject(type, parent, interpreter.fields);
      initializeFields(interpreter, type);
    }
    else { // not a default create and therefore might have a super or a this
      ShadowParser.CreateDeclarationContext create = (ShadowParser.CreateDeclarationContext) methodNode;
      // Watch out for null create blocks from .meta files
      if (create.createBlock() != null && create.createBlock().explicitCreateInvocation() != null) {
        ShadowParser.ExplicitCreateInvocationContext explicit = create.createBlock().explicitCreateInvocation();
        ShadowValue[] delegatedArguments = new ShadowValue[explicit.conditionalExpression().size()];
        for (int i = 0; i < delegatedArguments.length; ++i) {
          interpreter.visitConditionalExpression(explicit.conditionalExpression().get(i));
          delegatedArguments[i] = explicit.conditionalExpression().get(i).getInterpretedValue();
        }
        if (explicit.getChild(0).getText().equals("this"))
          interpreter.curObject = callCreate(packageTree, errorReporter, type, delegatedArguments);
        else { // super
          parent = callCreate(packageTree, errorReporter, type.getExtendType(), delegatedArguments);
          interpreter.curObject = new ShadowObject(type, parent, interpreter.fields);
          initializeFields(interpreter, type);
        }
      }
      else { // no explicit create
        if (type.getExtendType() != null)
          parent = callCreate(packageTree, errorReporter, type.getExtendType());
        interpreter.curObject = new ShadowObject(type, parent, interpreter.fields);
        initializeFields(interpreter, type);
      }

      if (create.createBlock() != null) {
        for (int i = 0; i < create.createBlock().blockStatement().size(); ++i) {
          interpreter.visitBlockStatement(create.createBlock().blockStatement(i));
        }
      }
    }

    interpreter.visit(signature.getNode());
    interpreter.closeScope();
    return interpreter.curObject;
  }

  @Override
  public Void visitThrowOrConditionalExpression(
      ShadowParser.ThrowOrConditionalExpressionContext ctx) {
    visitChildren(ctx);

    if (ctx.throwStatement() != null) {
      addError(
          ctx.throwStatement(),
          Error.INVALID_STRUCTURE,
          "Throw statement is not allowed in a compile-time constant");
      ctx.setInterpretedValue(ShadowValue.INVALID);
    } else ctx.setInterpretedValue(ctx.conditionalExpression().getInterpretedValue());

    return null;
  }

  @Override
  public Void visitCastExpression(ShadowParser.CastExpressionContext ctx) {
    visitChildren(ctx);

    Type type = ctx.type().getType();
    ShadowValue value = ctx.conditionalExpression().getInterpretedValue();
    try {
      value = value.cast(type);
    } catch (InterpreterException e) {
      addError(e.setContext(ctx));
    }

    ctx.setInterpretedValue(value);
    return null;
  }

  @Override
  public Void visitCheckExpression(ShadowParser.CheckExpressionContext ctx) {
    visitChildren(ctx);

    ShadowValue value = ctx.conditionalExpression().getInterpretedValue();

    if (value instanceof ShadowNull)
      addError(
          ctx.conditionalExpression(),
          Error.INVALID_TYPE,
          "Null value not permitted in a check statement in a compile-time constant");

    ctx.setInterpretedValue(value);
    return null;
  }

  @Override
  public Void visitCopyExpression(ShadowParser.CopyExpressionContext ctx) {
    visitChildren(ctx);

    ShadowValue value = ctx.conditionalExpression().getInterpretedValue();
    ctx.setInterpretedValue(value);

    return null;
  }

  @Override
  public Void visitClassSpecifier(ShadowParser.ClassSpecifierContext ctx) {
    visitChildren(ctx);

    // Always part of a suffix, thus always has a prefix
    ModifiedType prefixNode = curPrefix.getFirst();
    Type prefixType = prefixNode.getType();
    ShadowValue value = ShadowValue.INVALID;

    if (ctx.typeArguments() != null) { // has type arguments
      ShadowParser.TypeArgumentsContext arguments = ctx.typeArguments();
      SequenceType parameterTypes = prefixType.getTypeParameters();
      SequenceType argumentTypes = (SequenceType) arguments.getType();

      try {
        Type replacedType = prefixType.replace(parameterTypes, argumentTypes);
        currentType.addUsedType(replacedType);
        currentType.addPartiallyInstantiatedClass(replacedType);

        try {
          value = new ShadowClass(replacedType);
        } catch (InterpreterException e) {
          addError(e.setContext(ctx));
        }
      } catch (InstantiationException e) {
        // Should have already been caught in the typechecker
      }
    } else {
      try {
        value = new ShadowClass(prefixType);
      } catch (InterpreterException e) {
        addError(e.setContext(ctx));
      }
    }

    ctx.setInterpretedValue(value);

    return null;
  }

  @Override
  public Void visitCoalesceExpression(ShadowParser.CoalesceExpressionContext ctx) {
    // conditionalOrExpression ('??' conditionalOrExpression)* ('??' throwStatement)?
    visitChildren(ctx);
    if (ctx.throwStatement() != null) {
      addError(
          ctx.throwStatement(),
          Error.INVALID_STRUCTURE,
          "Throw statement is not allowed in a compile-time constant");
      ctx.setInterpretedValue(ShadowValue.INVALID);
    } else {
      ShadowValue value = ctx.conditionalOrExpression(0).getInterpretedValue();
      for (int i = 1; i < ctx.conditionalOrExpression().size() && value instanceof ShadowNull; ++i)
        value = ctx.conditionalOrExpression(i).getInterpretedValue();

      ctx.setInterpretedValue(value);
    }
    return null;
  }

  @Override
  public Void visitConditionalOrExpression(ShadowParser.ConditionalOrExpressionContext ctx) {
    visitChildren(ctx);

    ctx.setInterpretedValue(
        evaluateBinaryOperation(ctx, ctx.conditionalExclusiveOrExpression(), ctx.operators));

    return null;
  }

  @Override
  public Void visitConditionalExclusiveOrExpression(
      ShadowParser.ConditionalExclusiveOrExpressionContext ctx) {
    visitChildren(ctx);

    ctx.setInterpretedValue(
        evaluateBinaryOperation(ctx, ctx.conditionalAndExpression(), ctx.operators));

    return null;
  }

  @Override
  public Void visitConditionalAndExpression(ShadowParser.ConditionalAndExpressionContext ctx) {
    visitChildren(ctx);

    ctx.setInterpretedValue(evaluateBinaryOperation(ctx, ctx.bitwiseOrExpression(), ctx.operators));

    return null;
  }

  @Override
  public Void visitCreate(ShadowParser.CreateContext ctx) {
    visitChildren(ctx);

    Context prefixNode = curPrefix.getFirst();
    Type prefixType = prefixNode.getType();
    ShadowValue value = ShadowValue.INVALID;
    Context parent = (Context) ctx.getParent();

    if (prefixType.equals(Type.STRING)) {
      if (ctx.conditionalExpression().size() == 0) value = new ShadowString("");
      else if (ctx.conditionalExpression().size() == 1
          && ctx.conditionalExpression(0).getInterpretedValue() instanceof ShadowString)
        value = ctx.conditionalExpression(0).getInterpretedValue();
      else {
        StringBuilder builder = new StringBuilder("(");
        boolean first = true;
        for (Context context : ctx.conditionalExpression()) {
          if (first) first = false;
          else builder.append(", ");
          builder.append(context.getInterpretedValue());
        }
        builder.append(")");
        addError(
            curPrefix.getFirst(),
            Error.INVALID_CREATE,
            "Create for the String class with arguments "
                + builder
                + " is not allowed in a compile-time constant");
      }
    } else
      addError(
          curPrefix.getFirst(),
          Error.INVALID_CREATE,
          "Create is only allowed for the String class in a compile-time constant");

    parent.setInterpretedValue(value);
    return null;
  }

  @Override
  public Void visitBitwiseOrExpression(ShadowParser.BitwiseOrExpressionContext ctx) {
    visitChildren(ctx);

    ctx.setInterpretedValue(
        evaluateBinaryOperation(ctx, ctx.bitwiseExclusiveOrExpression(), ctx.operators));

    return null;
  }

  @Override
  public Void visitBitwiseExclusiveOrExpression(
      ShadowParser.BitwiseExclusiveOrExpressionContext ctx) {
    visitChildren(ctx);

    ctx.setInterpretedValue(
        evaluateBinaryOperation(ctx, ctx.bitwiseAndExpression(), ctx.operators));

    return null;
  }

  @Override
  public Void visitArrayCreate(ShadowParser.ArrayCreateContext ctx) {
    visitChildren(ctx);

    addError(
        ctx, Error.UNSUPPORTED_OPERATION, "Array cannot be created in a compile-time constant");
    ctx.setInterpretedValue(ShadowValue.INVALID);
    ((Context) ctx.getParent()).setInterpretedValue(ShadowValue.INVALID);

    return null;
  }

  @Override
  public Void visitArrayInitializer(ShadowParser.ArrayInitializerContext ctx) {
    visitChildren(ctx);

    // TODO: One day, make some arrays allowed?
    addError(
        ctx,
        Error.UNSUPPORTED_OPERATION,
        "Array initializer is not permitted in a compile-time constant");
    ctx.setInterpretedValue(ShadowValue.INVALID);

    return null;
  }

  @Override
  public Void visitBitwiseAndExpression(ShadowParser.BitwiseAndExpressionContext ctx) {
    visitChildren(ctx);

    ctx.setInterpretedValue(evaluateBinaryOperation(ctx, ctx.equalityExpression(), ctx.operators));

    return null;
  }

  @Override
  public Void visitEqualityExpression(ShadowParser.EqualityExpressionContext ctx) {
    visitChildren(ctx);

    ctx.setInterpretedValue(evaluateBinaryOperation(ctx, ctx.isExpression(), ctx.operators));

    return null;
  }

  @Override
  public Void visitExpression(ShadowParser.ExpressionContext ctx) {

    visitChildren(ctx);

    ShadowValue left = ctx.primaryExpression().getInterpretedValue();

    if (ctx.assignmentOperator() != null) {
      ShadowValue right = resolveValue(ctx.conditionalExpression().getInterpretedValue(), ctx);
      String operation = ctx.assignmentOperator().getText();
      try {
        doAssignment((ShadowReference) left, right, operation, ctx);
      } catch (InterpreterException e) {
        addError(ctx, e.getError());
      }
    }
    else
      ctx.setInterpretedValue(left);

    return null;
  }

  private void doAssignment(
          ShadowReference left, ShadowValue right, String operation, Context node) throws InterpreterException {
    MethodSignature signature;

    operation = operation.substring(0, operation.length() - 1); // clip off last character (=)

    if (!operation.isEmpty()) {
      ShadowValue leftValue = left.get();

      // signature for other operation
      signature = node.getOperations().get(0);
      right = right.cast(signature.getParameterTypes().get(0).getType());

      if (leftValue.getType().isPrimitive() && signature.isImport()) {
        right = leftValue.apply(BinaryOperator.fromString(operation), right);
      }
      else
        right = leftValue.callMethod(signature.getSymbol(), right)[0];
    }

    left.set(right);
  }

  @Override
  public Void visitIsExpression(ShadowParser.IsExpressionContext ctx) {
    visitChildren(ctx);

    if (ctx.type() != null) {
      boolean isType =
          ctx.relationalExpression()
              .getInterpretedValue()
              .getType()
              .isSubtype(ctx.type().getType());
      ctx.setInterpretedValue(new ShadowBoolean(isType));
    } else {
      ctx.setInterpretedValue(ctx.relationalExpression().getInterpretedValue());
    }

    return null;
  }

  @Override
  public Void visitRelationalExpression(ShadowParser.RelationalExpressionContext ctx) {
    visitChildren(ctx);

    ctx.setInterpretedValue(
        evaluateBinaryOperation(ctx, ctx.concatenationExpression(), ctx.operators));

    return null;
  }

  @Override
  public Void visitConcatenationExpression(ShadowParser.ConcatenationExpressionContext ctx) {
    visitChildren(ctx);

    // Handled differently from other binary operators since it's allowed
    // between almost any two types
    ShadowValue value;

    if (ctx.operators.size() > 0) {
      StringBuilder string =
          new StringBuilder(ctx.shiftExpression(0).getInterpretedValue().toString());
      for (int i = 0; i < ctx.operators.size(); ++i)
        string.append(ctx.shiftExpression(i + 1).getInterpretedValue().toString());
      value = new ShadowString(string.toString());
    } else value = ctx.shiftExpression(0).getInterpretedValue();

    ctx.setInterpretedValue(value);
    return null;
  }

  @Override
  public Void visitScopeSpecifier(ShadowParser.ScopeSpecifierContext ctx) {
    visitChildren(ctx);

    // Always part of a suffix, thus always has a prefix
    Context prefixNode = curPrefix.getFirst();
    Type prefixType = resolveType(prefixNode).getType();

    String name = ctx.Identifier().getText();

      if (prefixType.containsField(name)) {
        ShadowValue prefixValue = resolveValue(prefixNode.getInterpretedValue(), ctx);
        ctx.setInterpretedValue(new ShadowField((ShadowObject) prefixValue, name, resolveType(ctx).getType()));
      } else if (prefixType.recursivelyContainsConstant(name)) {
        // Constants should be evaluated ahead of time
        Context constant = prefixType.recursivelyGetConstant(name);
        ShadowValue value = constant.getInterpretedValue();

        if (value == null) {
          addError(
                  ctx, Error.UNINITIALIZED_CONSTANT, "Constant " + prefixType + ":" + name + " was not initialized before use");
          ctx.setInterpretedValue(ShadowValue.INVALID);
        }
        else
          ctx.setInterpretedValue(value);
      }
      else {
        addError(ctx, Error.UNKNOWN_REFERENCE, "Symbol " + name + " could not be resolved");
        ctx.setInterpretedValue(ShadowValue.INVALID);
      }

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
  public Void visitSpawnExpression(ShadowParser.SpawnExpressionContext ctx) {
    visitChildren(ctx);

    addError(
        ctx, Error.INVALID_STRUCTURE, "Spawn expression cannot occur in a compile-time constant");
    ctx.setInterpretedValue(ShadowValue.INVALID);

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
        evaluateBinaryOperation(ctx, ctx.multiplicativeExpression(), ctx.operators));

    return null;
  }

  @Override
  public Void visitLiteral(ShadowParser.LiteralContext ctx) {

    ctx.setInterpretedValue(StatementChecker.processLiteral(ctx, getErrorReporter()));

    return null;
  }

  @Override
  public Void visitMethod(ShadowParser.MethodContext ctx) {
    visitChildren(ctx);

    // Always part of a suffix, thus always has a prefix
    Context prefixNode = curPrefix.getFirst();
    ShadowValue prefixValue = prefixNode.getInterpretedValue();
    ShadowValue value = ShadowValue.INVALID;
    Type type = ctx.getType();

    if (type instanceof UnboundMethodType)
      value = new ShadowUnboundMethod(prefixValue, (UnboundMethodType) type);
    else
      addError(
          ctx,
          Error.UNSUPPORTED_OPERATION,
          "Accessing method references not supported in compile-time constants");

    ctx.setInterpretedValue(value);

    return null;
  }

  @Override
  public Void visitMethodCall(ShadowParser.MethodCallContext ctx) {
    visitChildren(ctx);

    // Always part of a suffix, thus always has a prefix
    Context prefixNode = curPrefix.getFirst();
    ShadowValue prefixValue = resolveValue(prefixNode.getInterpretedValue(), ctx);
    Type prefixType = resolveType(prefixNode).getType();
    ShadowValue value = ShadowValue.INVALID;

    if (prefixValue instanceof ShadowUnboundMethod) {
      try {

        ShadowValue[] arguments = new ShadowValue[ctx.conditionalExpression().size()];
        MethodSignature signature = ctx.getSignature();

        for (int i = 0; i < ctx.conditionalExpression().size(); ++i) {
          arguments[i] = ctx.conditionalExpression(i).getInterpretedValue();
          Type parameterType = signature.getMethodType().getParameterTypes().get(i).getType();
          if (arguments[i].getType().isStrictSubtype(parameterType))
            arguments[i] = arguments[i].cast(parameterType);
        }

        ShadowUnboundMethod unboundMethod = (ShadowUnboundMethod) (prefixValue);
        ShadowValue object = unboundMethod.getObject();

        ShadowValue[] results = object.callMethod(unboundMethod.getType().getTypeName(), arguments);
        if (results.length == 1)
          value = results[0];
        else if (results.length > 1)
          value = new ShadowSequence(results);
      } catch (InterpreterException e) {
        addError(e.setContext(ctx));
      }
    } else if (prefixType
        instanceof MethodReferenceType) // only happens with method pointers and local methods
    addError(
          ctx,
          Error.INVALID_STRUCTURE,
          "Method reference cannot be called inside a compile-time constant");
    else addError(Error.UNSUPPORTED_OPERATION.getException(ctx));

    ctx.setInterpretedValue(value);
    return null;
  }

  @Override
  public Void visitMultiplicativeExpression(ShadowParser.MultiplicativeExpressionContext ctx) {
    visitChildren(ctx);

    ctx.setInterpretedValue(evaluateBinaryOperation(ctx, ctx.unaryExpression(), ctx.operators));

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
      } catch (InterpreterException e) {
        addError(e.setContext(ctx));
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
    curPrefix.addFirst(null);
    visitChildren(ctx);

    Context child;

    if (!ctx.primarySuffix().isEmpty()) // Has suffixes, pull value from last suffix
      child = ctx.primarySuffix(ctx.primarySuffix().size() - 1);
    else // Just prefix
      child = ctx.primaryPrefix();

    ctx.setInterpretedValue(child.getInterpretedValue());
    curPrefix.removeFirst(); // pop prefix type off stack

    return null;
  }

  @Override
  public Void visitPrimaryPrefix(ShadowParser.PrimaryPrefixContext ctx) {
    visitChildren(ctx);

    String image = ctx.getText();
    ShadowValue value = null;

   if (image.equals("this") || image.equals("super")) {
      if(image.equals("this"))
        value = curObject;
      else
        value = curObject.getParent();
    }
   else if (ctx.generalIdentifier() != null) {
     if (!ctx.getModifiers().isTypeName()) {
       String name = ctx.generalIdentifier().getText();
       ShadowValue variable = (ShadowValue) findSymbol(name);
       if (variable != null)
         value = new ShadowVariable(this, name);
       else if( currentType.recursivelyContainsConstant(name)) {
         Context constant = currentType.recursivelyGetConstant(name);
         value = constant.getInterpretedValue();
       }
       else {
         ShadowParser.VariableDeclaratorContext field = currentType.recursivelyGetField(name);
         value = new ShadowField(curObject, field.generalIdentifier().getText(), field.getType());
       }
     }
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
  public Void visitPrimarySuffix(ShadowParser.PrimarySuffixContext ctx) {
    visitChildren(ctx);

    Context child = (Context) ctx.getChild(0);
    ctx.setInterpretedValue(child.getInterpretedValue());

    curPrefix.set(0, ctx); // so that a future suffix can figure out where it's at

    return null;
  }

  @Override
  public Void visitProperty(ShadowParser.PropertyContext ctx) {
    visitChildren(ctx);

    // Always part of a suffix, thus always has a prefix
    Context prefixNode = curPrefix.getFirst();
    ModifiedType resolvedType = resolveType(prefixNode);
    Type prefixType = resolvedType.getType();
    ShadowValue value = ShadowValue.INVALID;
    ShadowValue prefixValue = prefixNode.getInterpretedValue();

    if (prefixNode.getModifiers().isTypeName()) {
      if (prefixType instanceof SingletonType)
        addError(
            curPrefix.getFirst(),
            Error.INVALID_TYPE,
            "Properties cannot be called from singleton types in a compile-time constant");
    } else {
      prefixValue = resolveValue(prefixValue, ctx);
      value = new ShadowProperty((ShadowObject) prefixValue, (PropertyType) ctx.getType());
    }

    ctx.setInterpretedValue(value);
    return null;
  }

  @Override
  public Void visitSubscript(ShadowParser.SubscriptContext ctx) {
    visitChildren(ctx);

    Context prefixNode = curPrefix.getFirst();
    ShadowValue prefixValue = resolveValue(prefixNode.getInterpretedValue(), ctx);
    ShadowValue value = new ShadowSubscript(prefixValue, ctx.conditionalExpression().getInterpretedValue(), ctx.getType());

    ctx.setInterpretedValue(value);
    return null;
  }
}
