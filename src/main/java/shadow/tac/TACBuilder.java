package shadow.tac;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import shadow.Configuration;
import shadow.interpreter.ConstantFieldInterpreter.FieldKey;
import shadow.interpreter.*;
import shadow.parse.Context;
import shadow.parse.ShadowBaseVisitor;
import shadow.parse.ShadowParser;
import shadow.parse.ShadowParser.*;
import shadow.tac.TACMethod.TACFinallyFunction;
import shadow.tac.nodes.*;
import shadow.typecheck.type.InstantiationException;
import shadow.typecheck.type.*;

import java.util.*;
import java.util.Map.Entry;

public class TACBuilder extends ShadowBaseVisitor<Void> {
  private TACNode anchor;
  private TACMethod method;
  private TACOperand prefix;
  private boolean explicitSuper;
  private TACBlock block;
  private final Deque<TACModule> moduleStack = new ArrayDeque<>();

  public TACModule build(Context node) {
    method = null;
    prefix = null;
    explicitSuper = false;
    block = null;
    visit(node);
    return moduleStack.pop();
  }

  @Override
  public Void visit(ParseTree node) {
    Context context = (Context) node;
    TACNode saveList = anchor;
    anchor = new TACDummyNode(context, block);
    context.accept(this);
    // take out dummy node and save the resulting list in the context
    context.setList(anchor.remove());
    anchor = saveList;

    return null;
  }

  @Override
  public Void visitChildren(RuleNode node) {
    TACNode saveList = anchor;

    for (int i = 0; i < node.getChildCount(); i++) {
      ParseTree child = node.getChild(i);

      if (child instanceof Context) {
        Context context = (Context) child;
        anchor = new TACDummyNode(context, block);
        context.accept(this);
        // take out dummy node and save the resulting list in the context
        context.setList(anchor.remove());
      } else child.accept(this);
    }

    anchor = saveList;

    return null;
  }

  private static Type resolveType(Type type) {
    if (type instanceof PropertyType) return ((PropertyType) type).getGetType().getType();
    else return type;
  }

  @SuppressWarnings("ConstantConditions")
  @Override
  public Void visitClassOrInterfaceDeclaration(
      ShadowParser.ClassOrInterfaceDeclarationContext ctx) {
    Type type = ctx.getType();
    TACModule newModule = new TACModule(type);

    if (!moduleStack.isEmpty()) moduleStack.peek().addInnerClass(newModule);
    moduleStack.push(newModule);

    // Constant fields
    for (String fieldName : type.getFields().keySet()) {
      ShadowParser.VariableDeclaratorContext fieldCtx = type.getField(fieldName);
      if (fieldCtx.getModifiers().isConstant()) {
        moduleStack.peek().addConstant(new TACConstant(new FieldKey(type, fieldName), fieldCtx));
      }
    }

    for (List<MethodSignature> methods : type.getMethodMap().values())
      for (MethodSignature method : methods)
        if (method.isCreate() || method.getModifiers().isPrivate()) visitMethod(method);
    if (newModule.isClass())
      for (InterfaceType interfaceType : type.getAllInterfaces())
        for (MethodSignature method : interfaceType.orderMethods(moduleStack.peek().getClassType()))
          if (method.isWrapper()) visitMethod(method);

    for (MethodSignature method : type.orderMethods()) visitMethod(method);

    // tree saved by visitor
    ShadowParser.ClassOrInterfaceBodyContext body = ctx.classOrInterfaceBody();
    for (ShadowParser.ClassOrInterfaceBodyDeclarationContext declaration :
        body.classOrInterfaceBodyDeclaration()) {
      if (declaration.classOrInterfaceDeclaration() != null)
        build(declaration.classOrInterfaceDeclaration());
    }

    return null; // no children
  }

  @Override
  public Void visitAttributeDeclaration(ShadowParser.AttributeDeclarationContext ctx) {
    Type type = ctx.getType();
    TACModule newModule = new TACModule(type);

    moduleStack.push(newModule);

    // TODO: Do something with attributes one day

    return null; // no children
  }

  @Override
  public Void visitVariableDeclarator(ShadowParser.VariableDeclaratorContext ctx) {
    visitChildren(ctx);

    if (!(ctx.getType() instanceof SingletonType)) {
      String name = ctx.generalIdentifier().getText();
      if (ctx.getModifiers().isField()) {
        TACReference ref = new TACFieldRef(new TACLocalLoad(anchor, method.getThis()), name);

        if (ctx.conditionalExpression() != null)
          new TACStore(anchor, ref, ctx.conditionalExpression().appendBefore(anchor));
        // Note that default values are now removed for regular types
        // Defaults for nullables and ArrayTypes are unnecessary, since calloc is used to allocate
        // objects
      } else {
        TACVariable var = method.addLocal(ctx, name);
        if (ctx.conditionalExpression() != null)
          new TACLocalStore(anchor, var, ctx.conditionalExpression().appendBefore(anchor));
        else
          new TACLocalStore(
              anchor, var, new TACLiteral(anchor, new ShadowUndefined(ctx.getType())));
      }
    }

    return null;
  }

  @Override
  public Void visitArrayInitializer(ShadowParser.ArrayInitializerContext ctx) {
    visitChildren(ctx);

    List<TACOperand> sizes = new ArrayList<>();
    // Either the list of initializers or conditional expressions will be empty
    sizes.add(
        new TACLiteral(
            anchor,
            new ShadowInteger(ctx.arrayInitializer().size() + ctx.conditionalExpression().size())));
    ArrayType arrayType = (ArrayType) ctx.getType();
    TACClass arrayClass = new TACClass(anchor, arrayType);
    // allocate array
    prefix = visitArrayAllocation(arrayType, arrayClass, sizes);

    List<? extends Context> list;
    if (!ctx.arrayInitializer().isEmpty()) list = ctx.arrayInitializer();
    else list = ctx.conditionalExpression();

    // store each element in initializer into the array
    for (int i = 0; i < list.size(); ++i) {
      // last parameter of false means no array bounds checking needed
      TACArrayRef ref =
          new TACArrayRef(anchor, prefix, new TACLiteral(anchor, new ShadowInteger(i)), false);
      new TACStore(anchor, ref, list.get(i).appendBefore(anchor));
    }

    ctx.setOperand(prefix);

    return null;
  }

  private void initializeSingletons(MethodSignature signature) {
    for (SingletonType type : signature.getSingletons()) {
      TACLabel initLabel = new TACLabel(method), doneLabel = new TACLabel(method);
      TACSingletonRef reference = new TACSingletonRef(type);
      TACOperand instance = new TACLoad(anchor, reference);
      new TACBranch(
          anchor,
          new TACBinary(
              anchor, instance, new TACLiteral(anchor, new ShadowNull(instance.getType()))),
          initLabel,
          doneLabel);
      initLabel.insertBefore(anchor);

      TACMethodRef methodRef = new TACMethodName(anchor, type.getMethodOverloads("create").get(0));
      TACOperand object = new TACNewObject(anchor, type);
      TACCall call = new TACCall(anchor, methodRef, object);
      new TACStore(anchor, reference, call);
      new TACBranch(anchor, doneLabel);
      doneLabel.insertBefore(anchor);
    }
  }

  private static boolean isTerminator(TACNode node) {
    return (node instanceof TACBranch
        || node instanceof TACCleanupRet
        || node instanceof TACReturn
        || node instanceof TACThrow);
  }

  @Override
  public Void visitMethodDeclaration(ShadowParser.MethodDeclarationContext ctx) {

    initializeSingletons(ctx.getSignature());
    visitChildren(ctx);

    ctx.block().appendBefore(anchor);

    TACNode last = anchor.getPrevious();
    // A non-void method should always have explicit TACReturns
    // A void one might need one inserted at the end
    if (method.getSignature().isVoid() && !isTerminator(last)) {

      // Turn context off to avoid dead code removal errors
      Context context = anchor.getContext();
      anchor.setContext(null);

      // Do the cleanup, de-referencing variables
      visitAllCleanups();

      // Explicit return statement
      new TACReturn(anchor, new SequenceType());

      anchor.setContext(context);

      new TACLabel(method).insertBefore(anchor); // Unreachable label
    }

    return null;
  }

  @Override
  public Void visitCreateDeclaration(ShadowParser.CreateDeclarationContext ctx) {
    initializeSingletons(ctx.getSignature());
    visitChildren(ctx);

    if (ctx.createBlock()
        != null) // Possible because we still walk dummy nodes created for default creates
    ctx.createBlock().appendBefore(anchor);

    TACNode last = anchor.getPrevious();

    if (last instanceof TACLabel && last.getPrevious() instanceof TACReturn) last.remove();
    else {
      anchor.setContext(null);
      // Do the cleanup, de-referencing variables
      visitAllCleanups();
      // Turn context back on
      anchor.setContext(ctx);

      new TACReturn(
          anchor,
          method.getSignature().getSignatureWithoutTypeArguments().getFullReturnTypes(),
          new TACLocalLoad(anchor, method.getLocal("this")));
      new TACLabel(method).insertBefore(anchor); // Unreachable label
    }

    return null;
  }

  @Override
  public Void visitDestroyDeclaration(ShadowParser.DestroyDeclarationContext ctx) {
    initializeSingletons(ctx.getSignature());
    visitChildren(ctx);
    if (ctx.block()
        != null) // Possible because we still walk dummy nodes created for default destroys
    ctx.block().appendBefore(anchor);

    return null;
  }

  // here

  @Override
  public Void visitExplicitCreateInvocation(ShadowParser.ExplicitCreateInvocationContext ctx) {
    visitChildren(ctx);

    boolean isSuper = ctx.getChild(0).getText().equals("super");
    ClassType thisType = (ClassType) method.getSignature().getOuter();
    List<TACOperand> params = new ArrayList<>();
    params.add(new TACLocalLoad(anchor, method.getThis()));

    for (ShadowParser.ConditionalExpressionContext child : ctx.conditionalExpression())
      params.add(child.appendBefore(anchor));

    TACCall call = new TACCall(anchor, new TACMethodName(anchor, ctx.getSignature()), params);
    call.setDelegatedCreate(true);

    // If a super create, walk the fields.
    // Walking the fields is unnecessary if there is a this create,
    // since it will be taken care of by the this create, either explicitly or implicitly.
    if (isSuper) {
      // Walk fields in *exactly* the order they were declared since
      // some fields depend on prior fields.
      // This is accomplished by using a LinkedHashMap.
      for (Context field : thisType.getFields().values())
        if (!field.getModifiers().isConstant() && !(field.getType() instanceof SingletonType)) {
          visit(field);
          field.appendBefore(anchor);
        }
    }

    return null;
  }

  @Override
  public Void visitFormalParameters(ShadowParser.FormalParametersContext ctx) {
    return null; // no children
  }

  @Override
  public Void visitFormalParameter(ShadowParser.FormalParameterContext ctx) {
    visitChildren(ctx);

    // The FormalParameters rule is NOT visited
    // Parameters for methods are handled separately
    // The only thing that comes in here are the declarations
    // in catch blocks
    method.addLocal(ctx, ctx.Identifier().getText());
    return null;
  }

  public static TACOperand arraySize(TACNode anchor, TACOperand array, boolean isLong) {
    Type type = array.getType();

    MethodSignature signature;
    if (isLong) signature = type.getMatchingMethod("sizeLong", new SequenceType());
    else signature = type.getMatchingMethod("size", new SequenceType());

    List<TACOperand> params = new ArrayList<>();
    params.add(array);

    return new TACCall(anchor, new TACMethodName(anchor, signature), params);
  }

  private void doAssignment(
      TACOperand left, Context leftAST, TACOperand right, String operation, Context node) {
    TACMethodName methodName;
    MethodSignature signature;
    Type leftType = leftAST.getType();

    operation = operation.substring(0, operation.length() - 1); // clip off last character (=)

    if (leftType instanceof PropertyType) {
      PropertyType propertyType = (PropertyType) leftType;
      List<TACOperand> parameters = new ArrayList<>();

      parameters.add(left);
      if (propertyType instanceof SubscriptType) {
        // must be a primary expression with a subscript at the end in order to have SubscriptType
        ShadowParser.PrimaryExpressionContext expression = (PrimaryExpressionContext) leftAST;
        ShadowParser.SubscriptContext subscript =
            expression
                .primarySuffix(expression.primarySuffix().size() - 1)
                .subscript(); // subscript on last suffix
        parameters.add(
            subscript
                .conditionalExpression()
                .getOperand()); // there should only be one index in this list (not needed for
        // properties)
      }

      if (!operation.isEmpty()) {
        signature = propertyType.getGetter();
        TACOperand result;
        // optimize gets that are generated (and locked)
        if (signature.getNode().getParent() == null && signature.getModifiers().isLocked())
          result = new TACLoad(anchor, new TACFieldRef(left, signature.getSymbol()));
        else {
          methodName =
              new TACMethodName(
                  anchor, left, // prefix
                  signature);
          parameters.set(
              0,
              methodName
                  .getPrefix()); // replacing left with the method prefix can prevent duplicate code
          // (if there were casts)
          result = new TACCall(anchor, methodName, parameters);
        }

        // signature for other operation
        signature = node.getOperations().get(0);

        if (left.getType().isPrimitive() && signature.isImport())
          right = new TACBinary(anchor, result, signature, operation, right);
        else {
          TACVariable temp = method.addTempLocal(signature.getReturnTypes().get(0));
          methodName = new TACMethodName(anchor, result, signature);
          new TACLocalStore(
              anchor, temp, new TACCall(anchor, methodName, methodName.getPrefix(), right));
          right = new TACLocalLoad(anchor, temp);
        }

        parameters = new ArrayList<>(parameters);
      }

      parameters.add(right); // value to store (possibly updated by code above)

      signature = propertyType.getSetter();
      // optimize sets that are generated (and locked)
      if (signature.getNode().getParent() == null && signature.getModifiers().isLocked())
        new TACStore(anchor, new TACFieldRef(left, signature.getSymbol()), right);
      else {
        methodName =
            new TACMethodName(
                anchor, left, // prefix
                signature);
        parameters.set(
            0,
            methodName
                .getPrefix()); // replacing left with the method prefix can prevent duplicate code
        // (if there were casts)
        new TACCall(anchor, methodName, parameters);
      }
    } else if (left instanceof TACLoad) { // memory operation: field, array, etc.
      TACReference var = ((TACLoad) left).getReference();
      if (!operation.isEmpty()) {
        signature = node.getOperations().get(0);
        if (left.getType().isPrimitive() && signature.isImport())
          right = new TACBinary(anchor, left, signature, operation, right);
        else {
          TACVariable temp = method.addTempLocal(signature.getReturnTypes().get(0));
          methodName = new TACMethodName(anchor, left, signature);
          new TACLocalStore(
              anchor, temp, new TACCall(anchor, methodName, methodName.getPrefix(), right));
          right = new TACLocalLoad(anchor, temp);
        }
      } else {
        // in straight assignment, loading the left is unnecessary because we're just going to store
        // there
        // we had to build in the load so that we had something to get, but now we don't need it
        left.remove();
      }

      new TACStore(anchor, var, right);
    } else if (left instanceof TACLocalLoad
        || left instanceof TACLocalStore) { // local variable operation
      TACVariable var;
      if (left instanceof TACLocalLoad) var = ((TACLocalLoad) left).getVariable();
      else var = ((TACLocalStore) left).getVariable();
      if (!operation.isEmpty()) {
        signature = node.getOperations().get(0);
        if (left.getType().isPrimitive() && signature.isImport())
          right = new TACBinary(anchor, left, signature, operation, right);
        else {
          TACVariable temp = method.addTempLocal(signature.getReturnTypes().get(0));
          methodName = new TACMethodName(anchor, left, signature);
          new TACLocalStore(
              anchor, temp, new TACCall(anchor, methodName, methodName.getPrefix(), right));
          right = new TACLocalLoad(anchor, temp);
        }
      } else if (left instanceof TACLocalLoad)
        left.remove(); // once we know the var, we can remove the local load

      new TACLocalStore(anchor, var, right);
    } else throw new UnsupportedOperationException();
  }

  @Override
  public Void visitExpression(ShadowParser.ExpressionContext ctx) {
    visitChildren(ctx);

    TACOperand left = ctx.primaryExpression().appendBefore(anchor);

    if (ctx.assignmentOperator() != null) {
      TACOperand right = ctx.conditionalExpression().appendBefore(anchor);
      String operation = ctx.assignmentOperator().getText();
      doAssignment(left, ctx.primaryExpression(), right, operation, ctx);
    }

    return null;
  }

  @Override
  public Void visitConditionalExpression(ShadowParser.ConditionalExpressionContext ctx) {
    visitChildren(ctx);

    TACOperand condition = ctx.coalesceExpression().appendBefore(anchor);
    ctx.setOperand(condition);

    if (ctx.throwOrConditionalExpression().size() > 0) {
      ThrowOrConditionalExpressionContext first = ctx.throwOrConditionalExpression(0);
      ThrowOrConditionalExpressionContext second = ctx.throwOrConditionalExpression(1);

      TACLabel trueLabel = new TACLabel(method),
          falseLabel = new TACLabel(method),
          doneLabel = new TACLabel(method);
      TACVariable var = method.addTempLocal(ctx);
      new TACBranch(anchor, condition, trueLabel, falseLabel);

      trueLabel.insertBefore(anchor);
      if (first.throwStatement() != null) {
        first.throwStatement().getList().appendBefore(anchor);
      } else {
        new TACLocalStore(anchor, var, first.appendBefore(anchor));
        new TACBranch(anchor, doneLabel);
      }

      falseLabel.insertBefore(anchor);
      if (second.throwStatement() != null) {
        second.throwStatement().getList().appendBefore(anchor);
      } else {
        new TACLocalStore(anchor, var, second.appendBefore(anchor));
        new TACBranch(anchor, doneLabel);
      }

      doneLabel.insertBefore(anchor);

      ctx.setOperand(new TACLocalLoad(anchor, var));
    }

    return null;
  }

  @Override
  public Void visitThrowOrConditionalExpression(ThrowOrConditionalExpressionContext ctx) {
    visitChildren(ctx);

    if (ctx.conditionalExpression() != null) {
      ctx.setOperand(ctx.conditionalExpression().appendBefore(anchor));
    }

    return null;
  }

  @Override
  public Void visitCoalesceExpression(ShadowParser.CoalesceExpressionContext ctx) {
    visitChildren(ctx);

    TACOperand value = ctx.conditionalOrExpression(0).appendBefore(anchor);
    ctx.setOperand(value);

    if (ctx.conditionalOrExpression().size() > 1) {
      TACLabel doneLabel = new TACLabel(method);
      TACVariable var = method.addTempLocal(ctx);

      for (int i = 1; i < ctx.conditionalOrExpression().size(); ++i) {
        TACLabel nullLabel = new TACLabel(method);
        TACLabel nonNullLabel = new TACLabel(method);
        new TACBranch(
            anchor,
            new TACBinary(anchor, value, new TACLiteral(anchor, new ShadowNull(value.getType()))),
            nullLabel,
            nonNullLabel);
        nonNullLabel.insertBefore(anchor);
        new TACLocalStore(anchor, var, value);
        new TACBranch(anchor, doneLabel);
        nullLabel.insertBefore(anchor);
        value = ctx.conditionalOrExpression(i).appendBefore(anchor);
      }

      // whatever the final thing is, we're stuck with it if we got that far
      new TACLocalStore(anchor, var, value);
      new TACBranch(anchor, doneLabel);
      doneLabel.insertBefore(anchor);
      ctx.setOperand(new TACLocalLoad(anchor, var));
    }

    return null;
  }

  @Override
  public Void visitConditionalOrExpression(ShadowParser.ConditionalOrExpressionContext ctx) {
    visitChildren(ctx);

    TACOperand value = ctx.conditionalExclusiveOrExpression(0).appendBefore(anchor);
    ctx.setOperand(value);

    if (ctx.conditionalExclusiveOrExpression().size() > 1) {
      TACLabel doneLabel = new TACLabel(method);
      TACVariable var = method.addTempLocal(ctx);
      new TACLocalStore(anchor, var, value);
      for (int i = 1; i < ctx.conditionalExclusiveOrExpression().size(); i++) {
        TACLabel nextLabel = new TACLabel(method);
        new TACBranch(anchor, value, doneLabel, nextLabel);
        nextLabel.insertBefore(anchor);
        value = ctx.conditionalExclusiveOrExpression(i).appendBefore(anchor);
        new TACLocalStore(anchor, var, value);
      }
      new TACBranch(anchor, doneLabel);
      doneLabel.insertBefore(anchor);
      ctx.setOperand(new TACLocalLoad(anchor, var));
    }

    return null;
  }

  @Override
  public Void visitConditionalExclusiveOrExpression(
      ShadowParser.ConditionalExclusiveOrExpressionContext ctx) {
    visitChildren(ctx);
    TACOperand value = ctx.conditionalAndExpression(0).appendBefore(anchor);
    for (int i = 1; i < ctx.conditionalAndExpression().size(); i++) {
      TACOperand next = ctx.conditionalAndExpression(i).appendBefore(anchor);
      value = new TACBinary(anchor, value, TACBinary.Boolean.XOR, next);
    }

    ctx.setOperand(value);

    return null;
  }

  @Override
  public Void visitConditionalAndExpression(ShadowParser.ConditionalAndExpressionContext ctx) {
    visitChildren(ctx);
    TACOperand value = ctx.bitwiseOrExpression(0).appendBefore(anchor);
    ctx.setOperand(value);

    if (ctx.bitwiseOrExpression().size() > 1) {
      TACLabel doneLabel = new TACLabel(method);
      TACVariable var = method.addTempLocal(ctx);
      new TACLocalStore(anchor, var, value);
      for (int i = 1; i < ctx.bitwiseOrExpression().size(); i++) {
        TACLabel nextLabel = new TACLabel(method);
        new TACBranch(anchor, value, nextLabel, doneLabel);
        nextLabel.insertBefore(anchor);
        value = ctx.bitwiseOrExpression(i).appendBefore(anchor);
        new TACLocalStore(anchor, var, value);
      }
      new TACBranch(anchor, doneLabel);
      doneLabel.insertBefore(anchor);
      ctx.setOperand(new TACLocalLoad(anchor, var));
    }

    return null;
  }

  @Override
  public Void visitBitwiseOrExpression(ShadowParser.BitwiseOrExpressionContext ctx) {
    visitChildren(ctx);
    visitBinaryOperation(ctx, ctx.bitwiseExclusiveOrExpression());

    return null;
  }

  @Override
  public Void visitBitwiseExclusiveOrExpression(
      ShadowParser.BitwiseExclusiveOrExpressionContext ctx) {
    visitChildren(ctx);
    visitBinaryOperation(ctx, ctx.bitwiseAndExpression());

    return null;
  }

  @Override
  public Void visitBitwiseAndExpression(ShadowParser.BitwiseAndExpressionContext ctx) {
    visitChildren(ctx);
    visitBinaryOperation(ctx, ctx.equalityExpression());

    return null;
  }

  @Override
  public Void visitEqualityExpression(ShadowParser.EqualityExpressionContext ctx) {
    visitChildren(ctx);

    TACOperand current = ctx.isExpression(0).appendBefore(anchor);
    for (int i = 1; i < ctx.isExpression().size(); i++) {
      String op = ctx.getChild(2 * i - 1).getText(); // the operations are every other child
      TACOperand next = ctx.isExpression(i).appendBefore(anchor);
      Type currentType = current.getType();
      Type nextType = next.getType();

      if (op.equals("==") || op.equals("!=")) { // == or !=
        if (currentType.isPrimitive() && nextType.isPrimitive()) // if not, methods are needed
        current = new TACBinary(anchor, current, next);
        else {
          // no nullables allowed
          TACVariable var = method.addTempLocal(ctx);
          Type valueType = resolveType(current.getType());
          MethodSignature signature = valueType.getMatchingMethod("equal", new SequenceType(next));
          new TACLocalStore(
              anchor,
              var,
              new TACCall(anchor, new TACMethodName(anchor, current, signature), current, next));
          current = new TACLocalLoad(anchor, var);
        }
      } else { // === or !==
        boolean currentNullable = current.getModifiers().isNullable();
        boolean nextNullable = next.getModifiers().isNullable();
        if (currentType.isPrimitive()
            && nextType.isPrimitive()
            && (currentNullable || nextNullable)) {
          TACVariable var = method.addTempLocal(new SimpleModifiedType(Type.BOOLEAN));
          TACLabel done = new TACLabel(method);

          if (currentNullable && nextNullable) {
            TACOperand valueNull =
                new TACBinary(
                    anchor, current, new TACLiteral(anchor, new ShadowNull(current.getType())));
            TACOperand otherNull =
                new TACBinary(anchor, next, new TACLiteral(anchor, new ShadowNull(next.getType())));
            TACOperand bothNull =
                new TACBinary(anchor, valueNull, TACBinary.Boolean.AND, otherNull);
            TACOperand eitherNull =
                new TACBinary(anchor, valueNull, TACBinary.Boolean.OR, otherNull);
            TACLabel notBothNull = new TACLabel(method);
            TACLabel noNull = new TACLabel(method);

            new TACLocalStore(anchor, var, bothNull);
            new TACBranch(anchor, bothNull, done, notBothNull); // var will be true (both null)

            notBothNull.insertBefore(anchor);
            new TACBranch(
                anchor, eitherNull, done, noNull); // var will be false (one but not both null)

            noNull.insertBefore(anchor);
            new TACLocalStore(
                anchor,
                var,
                new TACBinary(
                    anchor,
                    TACCast.cast(anchor, new SimpleModifiedType(current.getType()), current),
                    TACCast.cast(anchor, new SimpleModifiedType(next.getType()), next)));
            new TACBranch(anchor, done);
          } else if (currentNullable) { // only current nullable
            TACOperand currentNull =
                new TACBinary(
                    anchor, current, new TACLiteral(anchor, new ShadowNull(current.getType())));
            TACLabel oneNull = new TACLabel(method);
            TACLabel noNull = new TACLabel(method);
            new TACBranch(anchor, currentNull, oneNull, noNull);
            oneNull.insertBefore(anchor);
            new TACLocalStore(anchor, var, new TACLiteral(anchor, new ShadowBoolean(false)));
            new TACBranch(anchor, done);
            noNull.insertBefore(anchor);
            new TACLocalStore(
                anchor,
                var,
                new TACBinary(
                    anchor,
                    TACCast.cast(anchor, new SimpleModifiedType(current.getType()), current),
                    next));
            new TACBranch(anchor, done);
          } else { // only next nullable
            TACOperand nextNull =
                new TACBinary(anchor, next, new TACLiteral(anchor, new ShadowNull(next.getType())));
            TACLabel oneNull = new TACLabel(method);
            TACLabel noNull = new TACLabel(method);
            new TACBranch(anchor, nextNull, oneNull, noNull);
            oneNull.insertBefore(anchor);
            new TACLocalStore(anchor, var, new TACLiteral(anchor, new ShadowBoolean(false)));
            new TACBranch(anchor, done);
            noNull.insertBefore(anchor);
            new TACLocalStore(
                anchor,
                var,
                new TACBinary(
                    anchor,
                    current,
                    TACCast.cast(anchor, new SimpleModifiedType(next.getType()), next)));
            new TACBranch(anchor, done);
          }

          done.insertBefore(anchor);
          current = new TACLocalLoad(anchor, var);
        } else // both non-nullable primitives or both references, no problem
        current = new TACBinary(anchor, current, next);
      }
      if (op.startsWith("!")) current = new TACUnary(anchor, "!", current);
    }
    ctx.setOperand(current);

    return null;
  }

  @Override
  public Void visitIsExpression(ShadowParser.IsExpressionContext ctx) {
    visitChildren(ctx);
    TACOperand value = ctx.relationalExpression().appendBefore(anchor);
    ctx.setOperand(value);

    if (ctx.type() != null) {
      Type comparisonType = ctx.type().getType();

      TACOperand comparisonClass = new TACClass(anchor, comparisonType).getClassData();

      // get class from object
      TACMethodName methodName =
          new TACMethodName(
              anchor, value, Type.OBJECT.getMatchingMethod("getClass", new SequenceType()));
      TACOperand valueClass = new TACCall(anchor, methodName, methodName.getPrefix());

      methodName =
          new TACMethodName(
              anchor,
              valueClass,
              Type.CLASS.getMatchingMethod("isSubtype", new SequenceType(Type.CLASS)));

      ctx.setOperand(new TACCall(anchor, methodName, methodName.getPrefix(), comparisonClass));
    }

    return null;
  }

  @Override
  public Void visitRelationalExpression(ShadowParser.RelationalExpressionContext ctx) {
    visitChildren(ctx);
    visitBinaryOperation(ctx, ctx.concatenationExpression());

    return null;
  }

  private TACOperand convertToString(TACOperand operand) {
    Type type = resolveType(operand.getType());
    if (!type.equals(Type.STRING)) {
      if (operand.getModifiers().isNullable()
          && !(type
              instanceof ArrayType)) { // || !type.isPrimitive() && !(type instanceof ArrayType))
        TACLabel nullLabel = new TACLabel(method),
            nonnullLabel = new TACLabel(method),
            doneLabel = new TACLabel(method);
        TACVariable var = method.addTempLocal(new SimpleModifiedType(Type.STRING));
        new TACBranch(
            anchor,
            new TACBinary(
                anchor, operand, new TACLiteral(anchor, new ShadowNull(operand.getType()))),
            nullLabel,
            nonnullLabel);
        nullLabel.insertBefore(anchor);
        new TACLocalStore(anchor, var, new TACLiteral(anchor, new ShadowString("null")));
        new TACBranch(anchor, doneLabel);
        nonnullLabel.insertBefore(anchor);

        if (type.isPrimitive()) // convert non null primitive wrapper to real primitive
        operand = TACCast.cast(anchor, new SimpleModifiedType(type), operand);

        TACMethodName methodName =
            new TACMethodName(
                anchor, operand, type.getMatchingMethod("toString", new SequenceType()));

        new TACLocalStore(anchor, var, new TACCall(anchor, methodName, methodName.getPrefix()));
        new TACBranch(anchor, doneLabel);
        doneLabel.insertBefore(anchor);
        operand = new TACLocalLoad(anchor, var);
      } else {
        TACMethodName methodRef =
            new TACMethodName(
                anchor, operand, type.getMatchingMethod("toString", new SequenceType()));
        operand = new TACCall(anchor, methodRef, Collections.singletonList(methodRef.getPrefix()));
      }
    }

    return operand;
  }

  @Override
  public Void visitConcatenationExpression(ShadowParser.ConcatenationExpressionContext ctx) {
    visitChildren(ctx);
    TACOperand last = ctx.shiftExpression(0).appendBefore(anchor);

    for (int i = 1; i < ctx.shiftExpression().size(); ++i) {
      if (i == 1) last = convertToString(last);

      TACOperand next = convertToString(ctx.shiftExpression(i).appendBefore(anchor));
      last =
          new TACCall(
              anchor,
              new TACMethodName(anchor, Type.STRING.getMethodOverloads("concatenate").get(0)),
              Arrays.asList(last, next));
    }

    ctx.setOperand(last);

    return null;
  }

  @Override
  public Void visitShiftExpression(ShadowParser.ShiftExpressionContext ctx) {
    visitChildren(ctx);
    visitBinaryOperation(ctx, ctx.rotateExpression());

    return null;
  }

  @Override
  public Void visitRotateExpression(ShadowParser.RotateExpressionContext ctx) {
    visitChildren(ctx);
    visitBinaryOperation(ctx, ctx.additiveExpression());

    return null;
  }

  @Override
  public Void visitAdditiveExpression(ShadowParser.AdditiveExpressionContext ctx) {
    visitChildren(ctx);
    visitBinaryOperation(ctx, ctx.multiplicativeExpression());

    return null;
  }

  @Override
  public Void visitMultiplicativeExpression(ShadowParser.MultiplicativeExpressionContext ctx) {
    visitChildren(ctx);
    visitBinaryOperation(ctx, ctx.unaryExpression());

    return null;
  }

  @Override
  public Void visitUnaryExpression(ShadowParser.UnaryExpressionContext ctx) {
    visitChildren(ctx);

    if (ctx.primaryExpression() != null)
      ctx.setOperand(ctx.primaryExpression().appendBefore(anchor));
    else if (ctx.inlineMethodDefinition() != null)
      ctx.setOperand(ctx.inlineMethodDefinition().appendBefore(anchor));
    else { // unary is happening
      TACOperand operand = ctx.unaryExpression().appendBefore(anchor);
      String op = ctx.getChild(0).getText();
      if (op.equals("#")) // string is special because of nulls
      ctx.setOperand(convertToString(operand));
      else {
        Type type = resolveType(operand.getType());
        if (op.equals("!")) ctx.setOperand(new TACUnary(anchor, "!", operand));
        else {
          MethodSignature signature = ctx.getOperations().get(0);
          if (type.isPrimitive() && signature.isImport())
            ctx.setOperand(new TACUnary(anchor, signature, op, operand));
          else
            ctx.setOperand(
                new TACCall(
                    anchor,
                    new TACMethodName(anchor, operand, ctx.getOperations().get(0)),
                    operand));
        }
      }
    }

    return null;
  }

  @Override
  public Void visitInlineMethodDefinition(ShadowParser.InlineMethodDefinitionContext ctx) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Void visitCastExpression(ShadowParser.CastExpressionContext ctx) {
    // one day this will be supported
    if (ctx.getType() instanceof MethodType) throw new UnsupportedOperationException();

    visitChildren(ctx);
    ctx.setOperand(
        TACCast.cast(anchor, ctx, ctx.conditionalExpression().appendBefore(anchor), true));

    return null;
  }

  @Override
  public Void visitCheckExpression(ShadowParser.CheckExpressionContext ctx) {
    visitChildren(ctx);
    TACLabel recover;
    if (block.hasRecover()) // Direct recover only
    recover = block.getRecover();
    else recover = new TACLabel(method);

    TACLabel continueLabel = new TACLabel(method);
    TACOperand operand = ctx.conditionalExpression().appendBefore(anchor);

    // Interfaces themselves are value types, so extract the object pointer inside
    if (operand.getType() instanceof InterfaceType)
      operand = TACCast.cast(anchor, new SimpleModifiedType(Type.OBJECT), operand);

    // Branch to the handler, either a recover block or the unexpected null exception
    new TACBranch(
        anchor,
        new TACBinary(anchor, operand, new TACLiteral(anchor, new ShadowNull(operand.getType()))),
        recover,
        continueLabel);

    if (!block.hasRecover()) { // No direct recover
      recover.insertBefore(anchor);

      if (block.getRecover()
          != null) { // Recover, but not direct, potentially unwinding to finally blocks on the way
        TACBlock currentBlock = block;
        while (currentBlock != null && !currentBlock.hasRecover())
          currentBlock = currentBlock.getParent();

        visitCleanups(currentBlock);
        new TACBranch(anchor, block.getRecover());
      } else {
        TACOperand object = new TACNewObject(anchor, Type.UNEXPECTED_NULL_EXCEPTION);
        MethodSignature signature =
            Type.UNEXPECTED_NULL_EXCEPTION.getMatchingMethod("create", new SequenceType());
        TACCall exception = new TACCall(anchor, new TACMethodName(anchor, signature), object);

        new TACThrow(anchor, exception);
      }
    }

    continueLabel.insertBefore(anchor);

    // Add in cast to remove nullable
    prefix = TACCast.cast(anchor, ctx, operand);
    ctx.setOperand(prefix);

    return null;
  }

  @Override
  public Void visitPrimaryExpression(ShadowParser.PrimaryExpressionContext ctx) {
    TACOperand savePrefix = prefix;
    prefix = null;
    visitChildren(ctx);

    TACOperand operand = ctx.primaryPrefix().appendBefore(anchor);
    for (ShadowParser.PrimarySuffixContext suffix : ctx.primarySuffix())
      operand = suffix.appendBefore(anchor);

    ctx.setOperand(operand);
    prefix = savePrefix;

    return null;
  }

  @Override
  public Void visitPrimarySuffix(ShadowParser.PrimarySuffixContext ctx) {
    visitChildren(ctx);

    Context child = (Context) ctx.getChild(0);
    ctx.setOperand(child.appendBefore(anchor));

    return null;
  }

  @Override
  public Void visitAllocation(ShadowParser.AllocationContext ctx) {
    visitChildren(ctx);

    Context child = (Context) ctx.getChild(0);
    ctx.setOperand(child.appendBefore(anchor));

    return null;
  }

  private TACOperand makeMethodReference(UnboundMethodType methodType) {
    if (prefix == null) prefix = new TACLocalLoad(anchor, method.getThis());

    List<TACOperand> params = new ArrayList<>(4);

    TACMethodName methodName = new TACMethodName(anchor, prefix, methodType.getKnownSignature());
    TACOperand refAsMethodTable =
        TACCast.cast(anchor, new SimpleModifiedType(Type.METHOD_TABLE), methodName, false);

    params.add(refAsMethodTable);
    params.add(prefix);
    params.add(new TACLiteral(anchor, new ShadowNull(new ArrayType(Type.OBJECT))));

    MethodSignature signature = Type.METHOD.recursivelyGetMethodOverloads("create").get(0);
    // internally sets prefix
    return callCreate(signature, params, signature.getOuter());
  }

  @Override
  public Void visitPrimaryPrefix(ShadowParser.PrimaryPrefixContext ctx) {
    visitChildren(ctx);

    // TODO: remove prefix, since the value can be gotten from the Context node?
    if (ctx.literal() != null) prefix = ctx.literal().appendBefore(anchor);
    else if (ctx.checkExpression() != null) prefix = ctx.checkExpression().appendBefore(anchor);
    else if (ctx.copyExpression() != null) prefix = ctx.copyExpression().appendBefore(anchor);
    else if (ctx.castExpression() != null) prefix = ctx.castExpression().appendBefore(anchor);
    else if (ctx.conditionalExpression() != null)
      prefix = ctx.conditionalExpression().appendBefore(anchor);
    else if (ctx.primitiveType() != null) prefix = ctx.primitiveType().appendBefore(anchor);
    else if (ctx.functionType() != null) prefix = ctx.functionType().appendBefore(anchor);
    else if (ctx.arrayInitializer() != null) prefix = ctx.arrayInitializer().appendBefore(anchor);
    else if (ctx.spawnExpression() != null) prefix = ctx.spawnExpression().appendBefore(anchor);
    else if (ctx.getType() instanceof SingletonType)
      prefix = new TACLoad(anchor, new TACSingletonRef((SingletonType) ctx.getType()));
    else {
      String name = ctx.getText();
      explicitSuper = name.equals("super");

      if (ctx.getType() instanceof UnboundMethodType) {
        // must create a Method reference if it's not a method call
        PrimaryExpressionContext parent = (PrimaryExpressionContext) ctx.getParent();
        List<PrimarySuffixContext> primarySuffix = parent.primarySuffix();
        // no suffix or no method call
        if (primarySuffix.isEmpty() || primarySuffix.get(0).methodCall() == null) {
          // Create Method object
          UnboundMethodType unboundMethod = (UnboundMethodType) ctx.getType();
          prefix = makeMethodReference(unboundMethod);
        }
      } else if (!ctx.getModifiers().isTypeName()) {
        TACVariable local;
        if (explicitSuper) {
          local = method.getLocal("this");
        } else {
          local = method.getLocal(name);
        }
        if (local != null) {
          prefix = new TACLocalLoad(anchor, local);
        } else {
          if (ctx.getModifiers().isConstant()) { // Constant
            Type thisType = method.getSignature().getOuter();
            prefix =
                new TACLiteral(anchor, thisType.recursivelyGetConstant(name).getInterpretedValue());
          } else { // field
            TACVariable thisRef = method.getThis();
            TACOperand op = new TACLocalLoad(anchor, thisRef);

            prefix = new TACLoad(anchor, new TACFieldRef(op, name));
          }
        }
      }
    }
    ctx.setOperand(prefix);

    return null;
  }

  private void methodCall(MethodSignature signature, Context node, List<? extends Context> list) {
    methodCall(signature, node, list, false);
  }

  private void methodCall(
      MethodSignature signature,
      Context node,
      List<? extends Context> list,
      boolean fromMethodReference) {
    if (prefix == null) prefix = new TACLocalLoad(anchor, method.getThis());

    TACMethodRef methodRef;
    List<TACOperand> params = new ArrayList<>();
    if (fromMethodReference) {
      MethodReferenceType methodReferenceType =
          (MethodReferenceType) node.getType(); // method reference must have method type
      // for method references, signature will be null
      String name = "";
      if (prefix instanceof TACLocalLoad) name = ((TACLocalLoad) prefix).getVariable().getName();
      else if (prefix instanceof TACLoad) name = ((TACLoad) prefix).getReference().toString();
      TACOperand methodPointerAsMethodTable =
          new TACLoad(anchor, new TACFieldRef(prefix, "method"));
      TACOperand methodPointer =
          TACCast.cast(
              anchor,
              new SimpleModifiedType(methodReferenceType.getMethodType()),
              methodPointerAsMethodTable,
              false);

      TACOperand object = new TACLoad(anchor, new TACFieldRef(prefix, "object"));
      methodRef = new TACMethodPointer(anchor, methodPointer, name, methodReferenceType);

      params.add(object);
    } else {
      TACMethodName methodName = new TACMethodName(anchor, prefix, signature);
      methodName.setSuper(explicitSuper);

      if (!signature.isImportAssembly()) params.add(methodName.getPrefix());

      methodRef = methodName;
    }

    for (Context child : list) // potentially empty list
    params.add(child.appendBefore(anchor));

    prefix = new TACCall(anchor, methodRef, params);

    // sometimes a cast is needed when dealing with generic types
    SequenceType requiredReturnTypes = methodRef.getReturnTypes();
    SequenceType methodReturnTypes = methodRef.getUninstantiatedReturnTypes();

    if (!methodReturnTypes.matches(requiredReturnTypes)) {
      if (requiredReturnTypes.size() == 1)
        prefix = TACCast.cast(anchor, requiredReturnTypes.get(0), prefix);
      else prefix = TACCast.cast(anchor, new SimpleModifiedType(requiredReturnTypes), prefix);
    }

    node.setOperand(prefix);
    explicitSuper = false;
  }

  private static Context getPrefix(Context ctx) {
    ShadowParser.PrimarySuffixContext parent = (PrimarySuffixContext) ctx.getParent();
    ShadowParser.PrimaryExpressionContext expression =
        (PrimaryExpressionContext) parent.getParent();
    int i = 0;
    //noinspection StatementWithEmptyBody
    for (; i < expression.primarySuffix().size() && expression.primarySuffix(i) != parent; ++i)
      ;
    if (i > 0) return expression.primarySuffix(i - 1);
    else return expression.primaryPrefix();
  }

  public static Context getPreviousSuffix(ShadowParser.PrimarySuffixContext ctx) {
    ShadowParser.PrimaryExpressionContext primaryExpression =
        (ShadowParser.PrimaryExpressionContext) ctx.getParent();

    int index = primaryExpression.primarySuffix().indexOf(ctx);
    return index == 0
        ? primaryExpression.primaryPrefix()
        : primaryExpression.primarySuffix(index - 1);
  }

  @Override
  public Void visitScopeSpecifier(ShadowParser.ScopeSpecifierContext ctx) {
    visitChildren(ctx);
    if (ctx.getModifiers().isConstant()) {
      // TODO: Replace this TACLoad with a TACLiteral since the value is known
      Type parentType = getPreviousSuffix((PrimarySuffixContext) ctx.getParent()).getType();
      // prefix = new TACLoad(anchor, new TACConstantRef(parentType, ctx.Identifier().getText()));
      Context constant = parentType.recursivelyGetConstant(ctx.Identifier().getText());
      prefix = new TACLiteral(anchor, constant.getInterpretedValue()); // should work?
    } else if (ctx.getType() instanceof SingletonType) {
      prefix = new TACLoad(anchor, new TACSingletonRef((SingletonType) ctx.getType()));
    } else if (!ctx.getModifiers()
        .isTypeName()) { // doesn't do anything at this stage if it's just a type name
      prefix = new TACLoad(anchor, new TACFieldRef(prefix, ctx.Identifier().getText()));
    }

    ctx.setOperand(prefix);

    return null;
  }

  @Override
  public Void visitClassSpecifier(ShadowParser.ClassSpecifierContext ctx) {
    visitChildren(ctx);

    Type type = getPrefix(ctx).getType();

    if (ctx.typeArguments() != null) {
      ShadowParser.TypeArgumentsContext arguments = ctx.typeArguments();

      try {
        type = type.replace(type.getTypeParameters(), (SequenceType) arguments.getType());
      } catch (InstantiationException ignored) {
      } // Should not happen
    }

    prefix = new TACClass(anchor, type).getClassData();

    ctx.setOperand(prefix);

    return null;
  }

  private static Context getSuffix(ShadowParser.PrimaryExpressionContext expression) {
    if (expression.primarySuffix().size() > 0)
      return expression.primarySuffix(expression.primarySuffix().size() - 1);
    else return expression.primaryPrefix();
  }

  @Override
  public Void visitSubscript(ShadowParser.SubscriptContext ctx) {
    visitChildren(ctx);
    // last suffix and on the LHS
    ShadowParser.PrimarySuffixContext suffix = (PrimarySuffixContext) ctx.getParent();
    ShadowParser.PrimaryExpressionContext expression =
        (ShadowParser.PrimaryExpressionContext) suffix.getParent();
    boolean isStore = isLHS(expression) && suffix == getSuffix(expression);
    Type prefixType = resolveType(prefix.getType());

    if (prefixType instanceof ArrayType
        && !(((ArrayType) prefixType).getBaseType() instanceof TypeParameter)) {
      TACOperand index = ctx.conditionalExpression().appendBefore(anchor);
      Type indexType = index.getType();
      if (!indexType.equals(Type.LONG))
        index =
            TACCast.cast(anchor, new SimpleModifiedType(Type.LONG, index.getModifiers()), index);

      prefix = new TACLoad(anchor, new TACArrayRef(anchor, prefix, index));
    } else if (ctx.getType() instanceof SubscriptType) {
      SubscriptType subscriptType = (SubscriptType) ctx.getType();
      // only do the straight loads
      // stores (and +='s) are handled in ASTExpression
      // only one conditionalExpression is possible
      // Arrays of type parameters (T[]) are handled here as well, since it's easy to use index()
      // methods
      if (!isStore) {
        MethodSignature signature = subscriptType.getGetter();
        methodCall(
            signature,
            ctx,
            Collections.singletonList(ctx.conditionalExpression())); // handles appending
      } else {
        // simply append everything (of which there is only one)
        ctx.conditionalExpression().appendBefore(anchor);
      }
    } else throw new UnsupportedOperationException();

    ctx.setOperand(prefix);

    return null;
  }

  private static boolean isLHS(shadow.parse.ShadowParser.PrimaryExpressionContext context) {
    ParserRuleContext parent = context.getParent();

    if (parent instanceof shadow.parse.ShadowParser.SequenceLeftSideContext) return true;

    if (parent instanceof shadow.parse.ShadowParser.ExpressionContext) {
      shadow.parse.ShadowParser.ExpressionContext expression = (ExpressionContext) parent;
      return expression.assignmentOperator() != null;
    }

    return false;
  }

  @Override
  public Void visitProperty(ShadowParser.PropertyContext ctx) {
    visitChildren(ctx);

    // last suffix and on the LHS
    ShadowParser.PrimarySuffixContext suffix = (PrimarySuffixContext) ctx.getParent();
    ShadowParser.PrimaryExpressionContext expression =
        (ShadowParser.PrimaryExpressionContext) suffix.getParent();
    boolean isStore = isLHS(expression) && suffix == getSuffix(expression);

    PropertyType propertyType = (PropertyType) ctx.getType();
    // only do the straight loads
    // stores (and +='s) are handled in ASTExpression
    if (!isStore) {
      MethodSignature signature = propertyType.getGetter();
      // automatically generated get can be optimized to load
      if (signature.getNode().getParent() == null && signature.getModifiers().isLocked())
        prefix = new TACLoad(anchor, new TACFieldRef(prefix, signature.getSymbol()));
      else methodCall(signature, ctx, new ArrayList<>()); // no parameters to add
    }

    ctx.setOperand(prefix);

    return null;
  }

  @Override
  public Void visitMethod(ShadowParser.MethodContext ctx) {
    visitChildren(ctx);

    PrimarySuffixContext parent = (PrimarySuffixContext) ctx.getParent();
    List<PrimarySuffixContext> suffixes =
        ((PrimaryExpressionContext) parent.getParent()).primarySuffix();
    int index = 0;
    while (suffixes.get(index) != parent) index++;
    index++;

    // if there are no more suffixes after this one or the next suffix is not a method call
    // make a method references
    if (index == suffixes.size() || suffixes.get(index).methodCall() == null) {
      // Create Method object
      UnboundMethodType unboundMethod = (UnboundMethodType) ctx.getType();
      prefix = makeMethodReference(unboundMethod);
      ctx.setOperand(prefix);
    }

    return null;
  }

  @Override
  public Void visitMethodCall(ShadowParser.MethodCallContext ctx) {
    boolean fromMethodReference = prefix != null && prefix.getType() instanceof MethodReferenceType;

    visitChildren(ctx);
    methodCall(
        ctx.getSignature(),
        ctx,
        ctx.conditionalExpression(),
        fromMethodReference); // handles appending

    return null;
  }

  @Override
  public Void visitLiteral(ShadowParser.LiteralContext ctx) {
    // no children
    prefix = ctx.setOperand(new TACLiteral(anchor, ctx.getInterpretedValue()));

    return null;
  }

  @Override
  public Void visitArguments(ShadowParser.ArgumentsContext ctx) {
    visitChildren(ctx);
    for (ShadowParser.ConditionalExpressionContext child : ctx.conditionalExpression())
      child.appendBefore(anchor);
    // no operand saved because the operands are in the children

    return null;
  }

  @Override
  public Void visitArrayCreate(ShadowParser.ArrayCreateContext ctx) {
    visitChildren(ctx);

    ShadowParser.ArrayDimensionsContext dimensions = ctx.arrayDimensions();
    dimensions.appendBefore(anchor);

    ArrayType arrayType = (ArrayType) ctx.getType();
    TACClass arrayClass = new TACClass(anchor, arrayType);
    List<TACOperand> indices = new ArrayList<>(dimensions.conditionalExpression().size());
    for (ShadowParser.ConditionalExpressionContext dimension : dimensions.conditionalExpression())
      indices.add(dimension.getOperand());

    if (ctx.getSignature() != null) { // either explicit or implicit create call
      MethodSignature create = ctx.getSignature();
      List<TACOperand> arguments = new ArrayList<>();
      if (ctx.arrayCreateCall() != null) {
        ctx.arrayCreateCall().appendBefore(anchor);
        for (ShadowParser.ConditionalExpressionContext child :
            ctx.arrayCreateCall().conditionalExpression()) arguments.add(child.getOperand());
      }
      prefix = visitArrayAllocation(arrayType, arrayClass, indices, create, arguments);
    } else if (ctx.arrayDefault() != null) {
      TACOperand value = ctx.arrayDefault().appendBefore(anchor);
      prefix = visitArrayAllocation(arrayType, arrayClass, indices, value);
    }
    // fills ragged arrays like int[]:create[5] with arrays of size 0
    else if (ctx.prefixType instanceof ArrayType) {
      ArrayType baseType = (ArrayType) ctx.prefixType;
      TACClass baseClass = new TACClass(anchor, baseType);
      TACOperand newArray =
          new TACNewArray(
              anchor, baseType, baseClass, new TACLiteral(anchor, new ShadowInteger(0L)));
      // For GC reasons, we need to save this value, then load it
      // Otherwise, each store of the new array will not be incremented in order to avoid
      // double-incrementing a freshly allocated array
      TACVariable temporary = method.addTempLocal(newArray);
      new TACLocalStore(anchor, temporary, newArray);
      TACOperand value = new TACLocalLoad(anchor, temporary);
      prefix = visitArrayAllocation(arrayType, arrayClass, indices, value);
    } else
      // nullable array only
      prefix = visitArrayAllocation(arrayType, arrayClass, indices);

    ctx.setOperand(prefix);

    return null;
  }

  private TACOperand callCreate(
      MethodSignature signature, List<TACOperand> params, Type prefixType) {
    TACOperand object = new TACNewObject(anchor, prefixType);

    params.add(0, object); // put object in front of other things

    TACMethodRef methodRef;
    if (signature.getOuter() instanceof InterfaceType)
      methodRef =
          new TACMethodName(
              anchor,
              TACCast.cast(anchor, new SimpleModifiedType(signature.getOuter()), object),
              signature);
    else methodRef = new TACMethodName(anchor, signature);

    prefix = new TACCall(anchor, methodRef, params);

    // sometimes a cast is needed when dealing with generic types
    if (!prefix.getType().equals(prefixType))
      prefix = TACCast.cast(anchor, new SimpleModifiedType(prefixType), prefix);

    return prefix;
  }

  @Override
  public Void visitCreate(ShadowParser.CreateContext ctx) {
    visitChildren(ctx);

    List<TACOperand> params = new ArrayList<>(ctx.conditionalExpression().size() + 1);
    for (ShadowParser.ConditionalExpressionContext child : ctx.conditionalExpression())
      params.add(child.appendBefore(anchor));

    MethodSignature signature = ctx.getSignature();
    TACOperand operand;
    // both set prefix
    if (signature.getOuter() instanceof InterfaceType)
      operand = callCreate(signature, params, ((Context) ctx.getParent()).getType());
    else operand = callCreate(signature, params, signature.getOuter());

    ctx.setOperand(operand);

    return null;
  }

  @Override
  public Void visitStatement(ShadowParser.StatementContext ctx) {
    visitChildren(ctx);
    Context context = (Context) ctx.getChild(0);
    context.appendBefore(anchor);

    return null;
  }

  @Override
  public Void visitAssertStatement(ShadowParser.AssertStatementContext ctx) {
    visitChildren(ctx);

    TACLabel doneLabel = new TACLabel(method), errorLabel = new TACLabel(method);

    TACOperand condition = ctx.conditionalExpression(0).appendBefore(anchor);

    new TACBranch(anchor, condition, doneLabel, errorLabel);
    errorLabel.insertBefore(anchor);

    TACOperand object = new TACNewObject(anchor, Type.ASSERT_EXCEPTION);
    List<TACOperand> params = new ArrayList<>();
    params.add(object);
    MethodSignature signature;

    if (ctx.conditionalExpression().size() > 1) { // has message
      TACOperand message = convertToString(ctx.conditionalExpression(1).appendBefore(anchor));
      signature = Type.ASSERT_EXCEPTION.getMatchingMethod("create", new SequenceType(message));
      params.add(message);
    } else signature = Type.ASSERT_EXCEPTION.getMatchingMethod("create", new SequenceType());

    TACCall exception = new TACCall(anchor, new TACMethodName(anchor, signature), params);
    new TACThrow(anchor, exception);
    doneLabel.insertBefore(anchor);

    return null;
  }

  @Override
  public Void visitBlock(ShadowParser.BlockContext ctx) {
    method.enterScope();
    visitChildren(ctx);

    for (ShadowParser.BlockStatementContext statement : ctx.blockStatement())
      statement.appendBefore(anchor);

    method.exitScope();
    return null;
  }

  @Override
  public Void visitCreateBlock(ShadowParser.CreateBlockContext ctx) {
    method.enterScope();
    visitChildren(ctx);

    if (ctx.explicitCreateInvocation() != null) ctx.explicitCreateInvocation().appendBefore(anchor);
    for (ShadowParser.BlockStatementContext statement : ctx.blockStatement())
      statement.appendBefore(anchor);

    method.exitScope();
    return null;
  }

  @Override
  public Void visitBlockStatement(ShadowParser.BlockStatementContext ctx) {
    visitChildren(ctx);
    if (ctx.localDeclaration() != null) ctx.localDeclaration().appendBefore(anchor);
    else ctx.statement().appendBefore(anchor);

    return null;
  }

  @Override
  public Void visitLocalDeclaration(ShadowParser.LocalDeclarationContext ctx) {
    visitChildren(ctx);

    if (ctx.localMethodDeclaration() != null) ctx.localMethodDeclaration().appendBefore(anchor);
    else ctx.localVariableDeclaration().appendBefore(anchor);

    return null;
  }

  @Override
  public Void visitLocalMethodDeclaration(ShadowParser.LocalMethodDeclarationContext ctx) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Void visitLocalVariableDeclaration(ShadowParser.LocalVariableDeclarationContext ctx) {
    visitChildren(ctx);

    for (ShadowParser.VariableDeclaratorContext child : ctx.variableDeclarator())
      child.appendBefore(anchor);

    return null;
  }

  @Override
  public Void visitStatementExpression(ShadowParser.StatementExpressionContext ctx) {
    visitChildren(ctx);

    if (ctx.sequenceAssignment() != null) ctx.sequenceAssignment().appendBefore(anchor);
    else ctx.expression().appendBefore(anchor);

    return null;
  }

  // private void doAssignment(TACOperand left, TACOperand right, char operation, OperationNode node
  // ) {
  @Override
  public Void visitSequenceAssignment(ShadowParser.SequenceAssignmentContext ctx) {
    visitChildren(ctx);

    ShadowParser.RightSideContext rightSide = ctx.rightSide();
    ShadowParser.SequenceLeftSideContext leftSide = ctx.sequenceLeftSide();

    // add computation of all left values
    leftSide.appendBefore(anchor);
    // add computation of all right values
    rightSide.appendBefore(anchor);

    List<TACOperand> left = new ArrayList<>();
    for (ParseTree child : leftSide.children)
      if (child instanceof Context) left.add(((Context) child).getOperand());

    SequenceType sequence = (SequenceType) leftSide.getType();
    // create splat
    if (!(rightSide.getType() instanceof SequenceType)) {
      TACOperand right = rightSide.getOperand();
      TACVariable temporary = method.addTempLocal(rightSide);
      new TACLocalStore(anchor, temporary, right);

      int index = 0;
      for (ModifiedType modifiedType : sequence)
        if (modifiedType != null) {
          doAssignment(
              left.get(index),
              (Context) modifiedType,
              new TACLocalLoad(anchor, temporary),
              "=",
              null);
          index++;
        }
    } else if (rightSide.sequenceRightSide() != null) { // sequence on the right

      // store all right hand side things into temporary variables (for garbage collection purposes)
      ArrayList<TACVariable> variables = new ArrayList<>();
      for (int i = 0; i < sequence.size(); ++i)
        if (sequence.get(i) != null) {
          TACOperand rightOperand =
              rightSide.sequenceRightSide().conditionalExpression(i).getOperand();
          TACVariable variable = method.addTempLocal(rightOperand);
          variables.add(variable);
          new TACLocalStore(anchor, variable, rightOperand);
        }

      // Then store all temporary variables into the left
      int index = 0;
      // Sequence size must match right hand size
      for (ModifiedType modifiedType : sequence)
        if (modifiedType != null) {
          doAssignment(
              left.get(index),
              (Context) modifiedType,
              new TACLocalLoad(anchor, variables.get(index)),
              "=",
              null);
          index++;
        }
    } else { // method call on the right whose output must be broken into parts
      int index = 0;
      for (int i = 0; i < sequence.size(); ++i)
        if (sequence.get(i) != null) {
          doAssignment(
              left.get(index),
              (Context) sequence.get(i),
              new TACSequenceElement(anchor, rightSide.getOperand(), i),
              "=",
              null);
          index++;
        }
    }

    return null;
  }

  @Override
  public Void visitSwitchStatement(ShadowParser.SwitchStatementContext ctx) {
    visitChildren(ctx);

    TACOperand value = ctx.conditionalExpression().appendBefore(anchor);
    Type type = value.getType();
    TACLabel defaultLabel = null;
    TACLabel doneLabel = new TACLabel(method);
    if (ctx.hasDefault) defaultLabel = new TACLabel(method);
    List<TACLabel> labels = new ArrayList<>(ctx.switchLabel().size());

    if (!(value.getType() instanceof EnumType)) {
      // first go through and do the conditions
      for (int i = 0; i < ctx.switchLabel().size(); ++i) {
        ShadowParser.SwitchLabelContext label = ctx.switchLabel(i);

        if (label.primaryExpression().size() == 0) labels.add(defaultLabel);
        else { // not default
          label.appendBefore(anchor); // append (all) label conditions
          TACLabel matchingCase = new TACLabel(method);
          labels.add(matchingCase);

          for (int j = 0; j < label.primaryExpression().size(); ++j) {
            TACOperand operand = label.primaryExpression(j).getOperand();
            TACOperand comparison;
            MethodSignature signature = type.getMatchingMethod("equal", new SequenceType(operand));

            if (type.isPrimitive() && signature.isImport())
              comparison = new TACBinary(anchor, value, operand); // equivalent to ===
            else
              comparison =
                  new TACCall(anchor, new TACMethodName(anchor, value, signature), value, operand);

            boolean moreConditions;
            if (j < label.primaryExpression().size() - 1) // more conditions in this label
            moreConditions = true;
            else // one more label which isn't default
            if (i
                < ctx.switchLabel().size()
                    - 2) // at least two more labels (of which only one can be default)
            moreConditions = true;
            else
              moreConditions =
                  i < ctx.switchLabel().size() - 1
                      && ctx.switchLabel(i + 1).primaryExpression().size() > 0;

            TACLabel next;

            if (moreConditions) next = new TACLabel(method);
            else if (ctx.hasDefault) next = defaultLabel;
            else next = doneLabel;

            new TACBranch(anchor, comparison, matchingCase, next);

            if (moreConditions) next.insertBefore(anchor);
          }
        }
      }

      if (ctx.hasDefault
          && ctx.switchLabel().size() == 1) // only default exists, needs a direct jump
      new TACBranch(anchor, defaultLabel);

      // then go through and add the executable blocks of code to jump to
      for (int i = 0; i < ctx.statement().size(); ++i) {
        labels.get(i).insertBefore(anchor); // mark start of code
        ctx.statement(i).appendBefore(anchor); // add block of code (the child after each label)
        TACBranch branch = new TACBranch(anchor, doneLabel);
        branch.setContext(null); // prevents dead code removal error
      }

      doneLabel.insertBefore(anchor);
    } else throw new UnsupportedOperationException();

    return null;
  }

  @Override
  public Void visitSwitchLabel(ShadowParser.SwitchLabelContext ctx) {
    visitChildren(ctx);

    for (ShadowParser.PrimaryExpressionContext child : ctx.primaryExpression())
      child.appendBefore(anchor);

    return null;
  }

  @Override
  public Void visitIfStatement(ShadowParser.IfStatementContext ctx) {
    visitChildren(ctx);

    TACLabel trueLabel = new TACLabel(method),
        falseLabel = new TACLabel(method),
        endLabel = ctx.statement().size() > 1 ? new TACLabel(method) : falseLabel;

    new TACBranch(anchor, ctx.conditionalExpression().appendBefore(anchor), trueLabel, falseLabel);
    trueLabel.insertBefore(anchor);
    ctx.statement(0).appendBefore(anchor);
    new TACBranch(anchor, endLabel).setContext(null); // won't cause a dead code problem if removed

    if (ctx.statement().size() > 1) { // else case
      falseLabel.insertBefore(anchor);
      ctx.statement(1).appendBefore(anchor);
      new TACBranch(anchor, endLabel)
          .setContext(null); // won't cause a dead code problem if removed
    }

    endLabel.insertBefore(anchor);

    return null;
  }

  @Override
  public Void visitWhileStatement(ShadowParser.WhileStatementContext ctx) {
    block = new TACBlock(anchor, block).addBreak().addContinue();
    visitChildren(ctx);

    TACLabel bodyLabel = new TACLabel(method),
        conditionLabel = block.getContinue(),
        endLabel = block.getBreak();
    new TACBranch(anchor, conditionLabel);

    // body
    bodyLabel.insertBefore(anchor);
    ctx.statement().appendBefore(anchor);
    new TACBranch(anchor, conditionLabel).setContext(null); // prevents dead code removal error

    // condition
    conditionLabel.insertBefore(anchor);
    TACOperand condition = ctx.conditionalExpression().appendBefore(anchor);
    new TACBranch(anchor, condition, bodyLabel, endLabel);

    endLabel.insertBefore(anchor);
    block = block.getParent();

    return null;
  }

  @Override
  public Void visitDoStatement(ShadowParser.DoStatementContext ctx) {
    block = new TACBlock(anchor, block).addBreak().addContinue();
    visitChildren(ctx);

    TACLabel bodyLabel = new TACLabel(method),
        conditionLabel = block.getContinue(),
        endLabel = block.getBreak();
    // only difference from while is this jump to body instead of condition
    new TACBranch(anchor, bodyLabel);

    // body
    bodyLabel.insertBefore(anchor);
    ctx.statement().appendBefore(anchor);
    new TACBranch(anchor, conditionLabel);

    // condition
    conditionLabel.insertBefore(anchor);
    TACOperand condition = ctx.conditionalExpression().appendBefore(anchor);
    TACBranch branch = new TACBranch(anchor, condition, bodyLabel, endLabel);
    branch.setContext(null); // avoids dead code removal error

    endLabel.insertBefore(anchor);
    block = block.getParent();

    return null;
  }

  @Override
  public Void visitForeachStatement(ShadowParser.ForeachStatementContext ctx) {
    block = new TACBlock(anchor, block).addBreak().addContinue();
    method.enterScope(); // needed because a variable is declared in a foreach
    visitChildren(ctx);

    ShadowParser.ForeachInitContext init = ctx.foreachInit();
    Type type = init.conditionalExpression().getType();
    TACOperand collection = init.appendBefore(anchor);
    TACVariable variable = method.getLocal(init.Identifier().getText());
    TACVariable iterator;
    TACOperand condition;
    TACOperand value;
    TACLabel bodyLabel = new TACLabel(method), endLabel = block.getBreak();

    // Optimization for (non-generic) arrays
    if (type instanceof ArrayType && !(((ArrayType) type).getBaseType() instanceof TypeParameter)) {
      TACLabel updateLabel = block.getContinue(), conditionLabel = new TACLabel(method);

      // Make iterator (long index)
      iterator = method.addTempLocal(new SimpleModifiedType(Type.LONG));
      new TACLocalStore(anchor, iterator, new TACLiteral(anchor, new ShadowInteger(0L)));
      TACOperand length = arraySize(anchor, collection, true);
      new TACBranch(anchor, conditionLabel); // init is done, jump to condition

      // body
      bodyLabel.insertBefore(anchor);

      // store array element into local variable before executing body
      value =
          new TACLoad(
              anchor,
              new TACArrayRef(anchor, collection, new TACLocalLoad(anchor, iterator), false));
      new TACLocalStore(anchor, variable, value);

      ctx.statement().appendBefore(anchor); // body

      TACBranch branch = new TACBranch(anchor, updateLabel);
      branch.setContext(null); // avoid dead code removal error
      updateLabel.insertBefore(anchor);

      // increment iterator
      value = new TACLocalLoad(anchor, iterator);
      value.setContext(null); // avoid dead code removal error
      TACLiteral one = new TACLiteral(anchor, new ShadowInteger(1L));
      one.setContext(null);
      value =
          new TACBinary(
              anchor,
              value,
              Type.LONG.getMatchingMethod("add", new SequenceType(Type.LONG)),
              "+",
              one,
              false);
      value.setContext(null);
      new TACLocalStore(anchor, iterator, value).setContext(null);

      new TACBranch(anchor, conditionLabel).setContext(null);
      conditionLabel.insertBefore(anchor);

      // check if iterator < array length
      value = new TACLocalLoad(anchor, iterator);
      condition =
          new TACBinary(
              anchor,
              value,
              Type.LONG.getMatchingMethod("compare", new SequenceType(Type.LONG)),
              "<",
              length,
              true);

    } else {
      TACLabel conditionLabel = block.getContinue();
      MethodSignature signature;

      // get iterator
      signature = type.getMatchingMethod("iterator", new SequenceType());
      ModifiedType iteratorType = signature.getReturnTypes().get(0);
      iterator = method.addTempLocal(iteratorType);
      TACMethodName getIterator = new TACMethodName(anchor, collection, signature);
      new TACLocalStore(
          anchor, iterator, new TACCall(anchor, getIterator, getIterator.getPrefix()));

      new TACBranch(anchor, conditionLabel); // init is done, jump to condition

      bodyLabel.insertBefore(anchor);

      // put variable update before main body
      signature = iteratorType.getType().getMatchingMethod("next", new SequenceType());
      TACMethodName next = new TACMethodName(anchor, new TACLocalLoad(anchor, iterator), signature);
      new TACLocalStore(
          anchor,
          variable,
          new TACCall(
              anchor, next, new TACLocalLoad(anchor, iterator))); // internally updates iterator

      ctx.statement().appendBefore(anchor); // body

      new TACBranch(anchor, conditionLabel).setContext(null);
      conditionLabel.insertBefore(anchor);

      // check if iterator has next
      signature = iteratorType.getType().getMatchingMethod("hasNext", new SequenceType());
      TACMethodName hasNext =
          new TACMethodName(anchor, new TACLocalLoad(anchor, iterator), signature);
      condition = new TACCall(anchor, hasNext, new TACLocalLoad(anchor, iterator));
    }
    new TACBranch(anchor, condition, bodyLabel, endLabel);
    endLabel.insertBefore(anchor);

    method.exitScope();
    block = block.getParent();

    return null;
  }

  @Override
  public Void visitForStatement(ShadowParser.ForStatementContext ctx) {
    block = new TACBlock(anchor, block).addBreak().addContinue();
    method.enterScope(); // needed because a variable can be declared in a for
    visitChildren(ctx);

    if (ctx.forInit() != null) ctx.forInit().appendBefore(anchor);

    TACLabel bodyLabel = new TACLabel(method),
        updateLabel = block.getContinue(),
        conditionLabel = (ctx.forUpdate() != null) ? new TACLabel(method) : updateLabel,
        endLabel = block.getBreak();
    // branch to condition
    new TACBranch(anchor, conditionLabel);

    // body
    bodyLabel.insertBefore(anchor);
    ctx.statement().appendBefore(anchor);

    // update (if exists)
    if (ctx.forUpdate() != null) {
      new TACBranch(anchor, updateLabel).setContext(null); // prevents dead code removal error
      updateLabel.insertBefore(anchor);
      ctx.forUpdate().appendBefore(anchor);
    }

    // condition
    new TACBranch(anchor, conditionLabel).setContext(null); // prevents dead code removal error
    conditionLabel.insertBefore(anchor);
    new TACBranch(anchor, ctx.conditionalExpression().appendBefore(anchor), bodyLabel, endLabel);

    endLabel.insertBefore(anchor);
    method.exitScope();
    block = block.getParent();

    return null;
  }

  @Override
  public Void visitForInit(ShadowParser.ForInitContext ctx) {
    visitChildren(ctx);

    if (ctx.localVariableDeclaration() != null) ctx.localVariableDeclaration().appendBefore(anchor);
    else ctx.statementExpressionList().appendBefore(anchor);

    return null;
  }

  @Override
  public Void visitStatementExpressionList(ShadowParser.StatementExpressionListContext ctx) {
    visitChildren(ctx);

    for (ShadowParser.StatementExpressionContext child : ctx.statementExpression())
      child.appendBefore(anchor);

    return null;
  }

  @Override
  public Void visitForUpdate(ShadowParser.ForUpdateContext ctx) {
    visitChildren(ctx);
    ctx.statementExpressionList().appendBefore(anchor);

    return null;
  }

  @Override
  public Void visitBreakOrContinueStatement(ShadowParser.BreakOrContinueStatementContext ctx) {
    // No children
    // An unreachable label is needed in case a programmer writes code after the break or continue.
    // Such code is unreachable, but a branch is a terminator instruction.
    // Thus, the result is badly formed LLVM if a block of instructions follows a terminator
    // without a label first.
    // Unreachable code will be detected and removed in TAC analysis.
    TACLabel unreachableLabel = new TACLabel(method);
    TACBlock exitBlock;

    if (ctx.getChild(0).getText().equals("break")) {
      exitBlock = block.getBreakBlock();
      visitCleanups(exitBlock);
      new TACBranch(anchor, exitBlock.getBreak());
    } else {
      exitBlock = block.getContinueBlock();
      visitCleanups(exitBlock);
      new TACBranch(anchor, exitBlock.getContinue());
    }

    unreachableLabel.insertBefore(anchor);

    return null;
  }

  @Override
  public Void visitReturnStatement(ShadowParser.ReturnStatementContext ctx) {
    visitChildren(ctx);

    TACOperand rightSide = null;
    if (ctx.rightSide() != null) rightSide = ctx.rightSide().appendBefore(anchor);

    TACLabel unreachableLabel = new TACLabel(method);

    // turn context off to avoid dead code removal errors
    Context context = anchor.getContext();
    anchor.setContext(null);

    // store the return value before the cleanup
    if (!method.getSignature().isCreate()) {
      if (rightSide != null) {
        Type type = rightSide.getType();

        if (type instanceof SequenceType) {
          TACSequence sequence = (TACSequence) rightSide;
          SequenceType sequenceType = (SequenceType) type;
          for (int i = 0; i < sequenceType.size(); ++i)
            new TACLocalStore(anchor, method.getLocal("_return" + i), sequence.get(i));
        } else new TACLocalStore(anchor, method.getLocal("return"), rightSide);
      }
    }

    // do the cleanup, de-referencing variables
    visitAllCleanups();

    // turn context back on
    anchor.setContext(context);

    if (method.getSignature().isCreate())
      new TACReturn(
          anchor,
          method.getSignature().getSignatureWithoutTypeArguments().getFullReturnTypes(),
          new TACLocalLoad(anchor, method.getLocal("this")));
    else {
      if (rightSide != null) {
        Type type = rightSide.getType();

        if (type instanceof SequenceType) {
          SequenceType sequenceType = (SequenceType) type;
          List<TACOperand> operands = new ArrayList<>(sequenceType.size());
          for (int i = 0; i < sequenceType.size(); ++i)
            operands.add(new TACLocalLoad(anchor, method.getLocal("_return" + i)));
          new TACReturn(anchor, (SequenceType) ctx.getType(), new TACSequence(anchor, operands));
        } else
          new TACReturn(
              anchor,
              (SequenceType) ctx.getType(),
              new TACLocalLoad(anchor, method.getLocal("return")));
      } else new TACReturn(anchor, new SequenceType());
    }

    unreachableLabel.insertBefore(anchor);

    return null;
  }

  @Override
  public Void visitThrowStatement(ShadowParser.ThrowStatementContext ctx) {
    visitChildren(ctx);
    TACLabel unreachableLabel = new TACLabel(method);
    new TACThrow(anchor, ctx.conditionalExpression().appendBefore(anchor));
    unreachableLabel.insertBefore(anchor);

    return null;
  }
  /*
   * finally block structure:
   *
   * finally
   *  ______
   * |
   * |  recover/catch statements (both)
   * |  ______
   * | |
   * | | try stuff
   * | |______
   * |
   * |  recover statements code (no block needed)
   * |
   * |  catch 1
   * |  ______
   * | |______
   * |
   * |  catch 2
   * |  ______
   * | |______
   * | ...
   * |______
   */
  @Override
  public Void visitFinallyStatement(ShadowParser.FinallyStatementContext ctx) {
    block = new TACBlock(anchor, block).addDone();

    if (ctx.block() != null) { // Has a finally block
      block = new TACBlock(anchor, block).addCleanup();
      TACBlock tryBlock = block;
      visit(ctx.catchStatements());
      block = block.getParent();
      anchor.setBlock(block); // move the current block to the parent on the anchor

      // The finally block is a mess: often has to be copied twice (once for normal execution, once
      // for unwind)

      /* This special block is added only so we can mark cleanup code.
       * Cleanup code *might* not cause an error if it needs to be removed because it's unreachable.
       * Example: A try block only contains a throw statement.
       * Only the unwind copy of the finally will be reachable.
       */
      block = new TACBlock(anchor, block);
      TACBlock cleanupBlock = block;
      TACFinallyFunction function = visitFinallyFunction(ctx.block());
      block = block.getParent();
      anchor.setBlock(block); // move the current block to the parent on the anchor

      Context context = anchor.getContext();
      anchor.setContext(null);

      TACLabel tryLabel = new TACLabel(method);
      new TACBranch(anchor, tryLabel);

      // Add in cleanup code (1st time, for non-unwinding code)
      tryBlock.getCleanup().insertBefore(anchor);
      TACPhi phi = tryBlock.getCleanupPhi();
      phi.insertBefore(anchor);
      if (function != null) new TACCallFinallyFunction(anchor, function, null);

      // ctx.block().appendBefore(anchor);
      new TACBranch(anchor, phi);

      if (tryBlock.isUnwindTarget()) {
        /* We only mark the cleanup block as a cleanup target if
         * we have to make the second unwinding copy.
         * Thus, code in the cleanup target can be removed without an
         * unreachable code error.
         * (If there is one, the unreachable code error will occur inside the unwind block.)
         */
        cleanupBlock.setCleanupTarget();

        TACLabel cleanupUnwindLabel = tryBlock.getCleanupUnwind();
        cleanupUnwindLabel.appendBefore(anchor);

        if (Configuration.isWindows()) {
          TACCleanupPad cleanupPad = new TACCleanupPad(anchor);
          if (function != null) new TACCallFinallyFunction(anchor, function, cleanupPad);
          // ctx.block().appendBefore(anchor);
          new TACCleanupRet(anchor, cleanupPad);
        } else {

          TACLandingPad landingPad = tryBlock.getLandingPad();
          landingPad.appendBefore(anchor);
          TACLabel finallyBody = landingPad.getBody();
          TACVariable exceptionVariable = method.getLocal("_exception");
          new TACLocalStore(anchor, exceptionVariable, landingPad);
          new TACBranch(anchor, finallyBody);
          finallyBody.appendBefore(anchor);

          if (function != null) new TACCallFinallyFunction(anchor, function, null);

          TACLandingPad parentLandingPad = block.getLandingPad();
          if (parentLandingPad != null) new TACBranch(anchor, parentLandingPad.getBody());
          else
            new TACResume(
                anchor, new TACLocalLoad(anchor, exceptionVariable)); // This is unlikely to happen
        }
      }
      // Turn context back on
      anchor.setContext(context);

      // Put try body after finally bodies (just so that TAC code doesn't try to reference unvisited
      // nodes)
      tryLabel.appendBefore(anchor);
    } else visitChildren(ctx);

    ctx.catchStatements().appendBefore(anchor);
    block.getDone().appendBefore(anchor);
    block = block.getParent();

    return null;
  }

  @Override
  public Void visitCatchStatements(ShadowParser.CatchStatementsContext ctx) {
    block = new TACBlock(anchor, block);
    TACBlock tryBlock = block;

    /*
    catchStatements
    : tryStatement
    catchStatement*
    ('recover' block )?
    ;
    	 */

    TACLabel preCleanupLabel = new TACLabel(method);

    if (ctx.catchStatement().size() > 0) block.addCatches();

    if (ctx.block() != null) block.addRecover();

    visit(ctx.tryStatement());
    ctx.tryStatement().appendBefore(anchor); // Appends try block
    block = block.getParent();
    anchor.setBlock(block); // move the current block to the parent on the anchor

    new TACBranch(anchor, preCleanupLabel).setContext(null); // jump to shared cleanup

    // Handle catches
    if (ctx.catchStatement().size() > 0) {
      // Ignores context for a while, preventing dead code removal errors
      // They'll be caught inside the catch
      // Catching them here creates misleading error messages
      Context context = anchor.getContext();
      anchor.setContext(null);

      if (Configuration.isWindows()) {
        TACCatchPad previousCatch = null;
        for (int i = 0; i < ctx.catchStatement().size(); ++i) {
          TACLabel label;
          if (i == 0) label = tryBlock.getCatches();
          else {
            label = new TACLabel(method);
            previousCatch.setSuccessor(label);
          }
          label.appendBefore(anchor);
          ShadowParser.CatchStatementContext catchStatement = ctx.catchStatement(i);
          ShadowParser.FormalParameterContext parameter = catchStatement.formalParameter();

          method.enterScope();
          visit(catchStatement);
          TACLabel catchLabel = new TACLabel(method);
          TACCatchPad catchPad = new TACCatchPad(anchor, (ExceptionType) parameter.getType());
          // TODO: Should this be a no-increment reference?
          new TACLocalStore(
              anchor, method.getLocal(parameter.Identifier().getText()), catchPad, false, catchPad);
          new TACCatchRet(anchor, catchPad, catchLabel);
          catchLabel.appendBefore(anchor);
          catchStatement.appendBefore(anchor); // append catch i
          method.exitScope();

          previousCatch = catchPad;
          new TACBranch(anchor, preCleanupLabel);
        }
      } else {
        // Insert landing pad
        tryBlock.getCatches().appendBefore(anchor);

        TACVariable exceptionVariable = method.getLocal("_exception");

        TACLandingPad landingPad = tryBlock.getLandingPad();
        landingPad.appendBefore(anchor);
        TACLabel catchBodies = landingPad.getBody();

        new TACLocalStore(anchor, exceptionVariable, landingPad);
        new TACBranch(anchor, catchBodies);

        catchBodies.appendBefore(anchor);
        TACLocalLoad exceptionLoad = new TACLocalLoad(anchor, method.getLocal("_exception"));
        TACOperand typeid = new TACSequenceElement(anchor, exceptionLoad, 1);
        TACOperand exception = new TACSequenceElement(anchor, exceptionLoad, 0);

        int catches = ctx.catchStatement().size();

        for (int i = 0; i < catches; ++i) {
          ShadowParser.CatchStatementContext catchStatement = ctx.catchStatement(i);
          ShadowParser.FormalParameterContext parameter = catchStatement.formalParameter();
          ExceptionType exceptionType = (ExceptionType) parameter.getType();

          TACLabel skipLabel = new TACLabel(method);
          TACLabel catchLabel = new TACLabel(method);
          new TACBranch(
              anchor,
              new TACBinary(
                  anchor,
                  typeid,
                  new TACTypeId(anchor, new TACClass(anchor, exceptionType).getClassData())),
              catchLabel,
              skipLabel);

          method.enterScope();
          visit(catchStatement);
          catchLabel.insertBefore(anchor);
          // TODO: Should this be a no-increment reference?
          new TACLocalStore(
              anchor,
              method.getLocal(parameter.Identifier().getText()),
              new TACCatch(anchor, exception, exceptionType, landingPad),
              false);
          catchStatement.appendBefore(anchor); // append catch i
          method.exitScope();

          new TACBranch(anchor, preCleanupLabel);

          // Last skip label is for continuing to unwind
          skipLabel.insertBefore(anchor);
        }

        TACLandingPad parentLandingPad = block.getLandingPad();
        if (parentLandingPad != null)
          new TACBranch(
              anchor,
              parentLandingPad
                  .getBody()); // Skip the landing pad part and go straight to exception checking
        else new TACResume(anchor, exceptionLoad); // Only happens inside finally functions
      }

      anchor.setContext(context);
    }

    // Handle recover
    if (ctx.block() != null) {
      visit(ctx.block());
      tryBlock.getRecover().insertBefore(anchor);
      ctx.block().appendBefore(anchor);

      new TACBranch(anchor, preCleanupLabel).setContext(null);
    }

    preCleanupLabel.appendBefore(anchor);

    visitCleanups(block); // One set of cleanup for try/catches/recover
    // Turn context off to avoid dead code removal errors
    new TACBranch(anchor, block.getDone()).setContext(null);

    return null;
  }

  /*
  * ShadowParser.RecoverStatementContext parent = (RecoverStatementContext) ctx.getParent();

  block = new TACBlock(anchor, block);
  if( parent.block() != null ) //parent has recover
  	block.addRecover();

  block.addCatches(ctx.catchStatement().size());

  visitChildren(ctx);

  ctx.tryStatement().appendBefore(anchor); //appends try block

  if( ctx.catchStatement().size() > 0 ) {
  	//ignores context for a while, preventing dead code removal errors
  	//they'll be caught inside the catch
  	//catching them here creates misleading error messages
  	Context context = anchor.getContext();
  	anchor.setContext(null);

  	TACOperand typeid = new TACSequenceElement(anchor, new TACLocalLoad(anchor, method.getLocal("_exception")), 1 );
  	for( int i = 0; i < ctx.catchStatement().size(); ++i ) {
  		ShadowParser.CatchStatementContext child = ctx.catchStatement(i);
  		Type type = child.formalParameter().getType();
  		TACLabel catchLabel = block.getCatch(i),
  				skip = new TACLabel(method);

  		new TACBranch(anchor, new TACBinary(anchor, typeid, new TACTypeId(anchor, new TACClass(anchor, type).getClassData())),
  				catchLabel, skip);

  		catchLabel.insertBefore(anchor);
  		child.appendBefore(anchor); //append catch i
  		new TACBranch(anchor, block.getDone()); //prevents dead code removal error
  		skip.insertBefore(anchor);
  	}

  	TACLabel continueUnwind = block.getUnwind();
  	if (continueUnwind != null)
  		new TACBranch(anchor, continueUnwind); //try inside of try
  	else
  		new TACResume(anchor, new TACLocalLoad(anchor, method.getLocal("_exception")));

  	anchor.setContext(context);
  }

  if( parent.block() != null ) //parent has recover
  	block.getRecover().insertBefore(anchor);

  block = block.getParent();

  return null;
  */

  @Override
  public Void visitCatchStatement(ShadowParser.CatchStatementContext ctx) {
    visitChildren(ctx);
    ctx.block().appendBefore(anchor);

    return null;
  }

  @Override
  public Void visitTryStatement(ShadowParser.TryStatementContext ctx) {
    visitChildren(ctx);
    ctx.block().appendBefore(anchor);

    return null;
  }

  // Visits all cleanups out to the outermostBlock, returning to the current location each time
  // This is done for code that is recovering, returning, breaking or continuing, allowing all the
  // finally blocks to be visited *without* exception handling
  // (Possibly including the outermost cleanup which contains reference-count decrements)
  private void visitCleanups(TACBlock outermostBlock) {
    TACBlock currentBlock = block;
    boolean done = false;
    TACLabel currentLabel = null;

    Context context = anchor.getContext();
    anchor.setContext(null);

    while (!done) {
      if (currentBlock.hasCleanup()) {
        if (currentLabel == null) {
          currentLabel = new TACLabel(method);
          new TACBranch(anchor, currentLabel);
          currentLabel.insertBefore(anchor);
        }

        // Branch to cleanup
        new TACBranch(anchor, currentBlock.getCleanup());
        TACLabel nextLabel = new TACLabel(method);

        // Phi branch back to nextLabel (after cleanup code)
        currentBlock
            .getCleanupPhi()
            .addPreviousStore(currentLabel, new TACLabelAddress(anchor, nextLabel, method));
        nextLabel.insertBefore(anchor);

        currentLabel = nextLabel;
      }

      if (currentBlock == outermostBlock || currentBlock.getParent() == null) done = true;
      else currentBlock = currentBlock.getParent();
    }

    anchor.setContext(context);
  }

  // Visits *all* cleanups out to full method scope, including the outermost cleanup with
  // reference-count decrements
  // Done only for returns
  private void visitAllCleanups() {
    TACBlock outerBlock = block;
    while (outerBlock.getParent() != null) outerBlock = outerBlock.getParent();
    visitCleanups(outerBlock);
  }

  private TACLabel setupMethod() {
    // Outermost block (contains cleanup for GC purposes)
    block = new TACBlock(method).addCleanup().addDone();
    anchor = new TACDummyNode(null, block);

    TACLabel methodBody = new TACLabel(method);
    TACLabel startingLabel = new TACLabel(method);
    startingLabel.appendBefore(anchor); // always start with a label

    // Variable to hold low-level exception data
    // Note that variables cannot start with underscore (_) in Shadow, so no collision is possible
    if (!Configuration.isWindows())
      new TACLocalStore(
          anchor,
          method.addLocal(new SimpleModifiedType(Type.getExceptionType()), "_exception"),
          new TACLiteral(anchor, new ShadowUndefined(Type.getExceptionType())));

    new TACBranch(anchor, methodBody); // branch to method body

    // Add in cleanup code (1st time, for non-unwinding code)
    block.getCleanup().insertBefore(anchor);
    TACPhi phi = block.getCleanupPhi();
    phi.insertBefore(anchor);

    // Cleanup code goes here
    TACFinallyFunction function = visitFinallyFunction(null);
    method.setCleanupFinallyFunction(function);

    new TACCallFinallyFunction(anchor, function, null);
    new TACBranch(anchor, phi);

    // Add in cleanup code (2nd time, for unwinding code)
    TACLabel cleanupPadLabel = block.getCleanupUnwind();
    cleanupPadLabel.appendBefore(anchor);
    if (Configuration.isWindows()) {
      TACCleanupPad cleanupPad = new TACCleanupPad(anchor);
      new TACCallFinallyFunction(anchor, function, cleanupPad);
      // method.setUnwindCleanupAnchor(new TACCleanupRet(anchor, cleanupPad));
      new TACCleanupRet(anchor, cleanupPad);
    } else {

      TACLandingPad landingPad = block.getLandingPad();
      landingPad.appendBefore(anchor);
      TACLabel finallyBody = landingPad.getBody();
      TACVariable exceptionVariable = method.getLocal("_exception");
      new TACLocalStore(anchor, exceptionVariable, landingPad);
      new TACBranch(anchor, finallyBody);
      finallyBody.appendBefore(anchor);
      new TACCallFinallyFunction(anchor, function, null);
      new TACResume(anchor, new TACLocalLoad(anchor, method.getLocal("_exception")));
    }

    methodBody.appendBefore(anchor);

    return startingLabel;
  }

  private void cleanupMethod() {
    block.getDone().insertBefore(anchor); // Catches things that should have a return but don't
    block = block.getParent(); // should return block to null, just for fun
  }

  private void visitCopyMethod(MethodSignature methodSignature) {
    ClassType type = (ClassType) methodSignature.getOuter();
    method.addParameters(anchor); // address map called "addresses"

    Context context = anchor.getContext();
    anchor.setContext(null);

    // Unreachable label
    if (type.getModifiers().isImmutable()) {
      // Local store includes increase of reference count
      new TACLocalStore(
          anchor, method.getLocal("return"), new TACLocalLoad(anchor, method.getThis()));

      visitAllCleanups();

      new TACReturn(
          anchor,
          methodSignature.getSignatureWithoutTypeArguments().getFullReturnTypes(),
          new TACLocalLoad(anchor, method.getLocal("return")));

      anchor.setContext(context);

    } else {
      TACOperand this_ = new TACLocalLoad(anchor, method.getThis());
      TACOperand address = new TACPointerToLong(anchor, this_);

      TACOperand map = new TACLocalLoad(anchor, method.getParameter("addresses"));
      TACMethodName indexMethod =
          new TACMethodName(
              anchor,
              Type.ADDRESS_MAP.getMatchingMethod("containsKey", new SequenceType(Type.ULONG)));
      TACOperand test = new TACCall(anchor, indexMethod, map, address);

      TACLabel copyLabel = new TACLabel(method), returnLabel = new TACLabel(method);

      new TACBranch(anchor, test, returnLabel, copyLabel);
      copyLabel.insertBefore(anchor);

      if (type.getTypeWithoutTypeArguments().equals(Type.ARRAY)
          || type.getTypeWithoutTypeArguments().equals(Type.ARRAY_NULLABLE)) {
        Type genericArray = type.getTypeWithoutTypeArguments();
        boolean isNullable = type.getTypeWithoutTypeArguments().equals(Type.ARRAY_NULLABLE);

        TACOperand arrayClass =
            TACCast.cast(
                anchor,
                new SimpleModifiedType(Type.GENERIC_CLASS, new Modifiers(Modifiers.IMMUTABLE)),
                new TACLoad(
                    anchor,
                    new TACFieldRef(
                        this_,
                        new SimpleModifiedType(Type.CLASS, new Modifiers(Modifiers.IMMUTABLE)),
                        "class")));
        TACOperand length = arraySize(anchor, this_, true);

        // allocate a new array (which by default gets a ref count of 1)
        TACOperand array =
            new TACNewArray(
                anchor,
                new ArrayType(genericArray.getTypeParameters().get(0).getType(), isNullable),
                arrayClass,
                length);

        TACOperand classArray =
            new TACLoad(
                anchor,
                new TACFieldRef(
                    arrayClass,
                    new SimpleModifiedType(
                        new ArrayType(Type.CLASS), new Modifiers(Modifiers.IMMUTABLE)),
                    "parameters"));
        TACOperand baseClass =
            new TACLoad(
                anchor,
                new TACArrayRef(
                    anchor, classArray, new TACLiteral(anchor, new ShadowInteger(0L)), false));

        TACLabel primitive = new TACLabel(method);
        TACLabel startLoop = new TACLabel(method);
        TACLabel done = new TACLabel(method);
        TACLabel body = new TACLabel(method);
        TACLabel condition = new TACLabel(method);

        TACMethodName isPrimitive =
            new TACMethodName(
                anchor, baseClass, Type.CLASS.getMatchingMethod("isPrimitive", new SequenceType()));
        TACOperand checkPrimitive = new TACCall(anchor, isPrimitive, isPrimitive.getPrefix());
        new TACBranch(anchor, checkPrimitive, primitive, startLoop);

        // if primitive
        primitive.insertBefore(anchor);

        TACMethodName getWidth =
            new TACMethodName(
                anchor, baseClass, Type.CLASS.getMatchingMethod("width", new SequenceType()));
        TACOperand width = new TACCall(anchor, getWidth, getWidth.getPrefix());
        TACOperand sizeInBytes =
            new TACBinary(
                anchor,
                length,
                Type.LONG.getMatchingMethod("multiply", new SequenceType(Type.LONG)),
                "*",
                width);

        new TACCopyMemory(anchor, array, this_, sizeInBytes, true);

        new TACBranch(anchor, done);

        // start loop
        startLoop.insertBefore(anchor);

        TACVariable i = method.addTempLocal(new SimpleModifiedType(Type.LONG));
        new TACLocalStore(anchor, i, new TACLiteral(anchor, new ShadowInteger(0L)));
        new TACBranch(anchor, condition);

        condition.insertBefore(anchor);

        TACOperand loop =
            new TACBinary(
                anchor,
                new TACLocalLoad(anchor, i),
                Type.LONG.getMatchingMethod("compare", new SequenceType(Type.LONG)),
                "<",
                length,
                true);
        new TACBranch(anchor, loop, body, done);
        body.insertBefore(anchor);

        SequenceType indexArguments = new SequenceType();
        indexArguments.add(i);
        TACMethodName indexLoad =
            new TACMethodName(anchor, genericArray.getMatchingMethod("index", indexArguments));
        indexArguments.add(genericArray.getTypeParameters().get(0));
        TACMethodName indexStore =
            new TACMethodName(anchor, genericArray.getMatchingMethod("index", indexArguments));

        TACOperand value = new TACCall(anchor, indexLoad, this_, new TACLocalLoad(anchor, i));

        TACLabel skipLabel = new TACLabel(method);
        TACLabel makeCopyLabel = new TACLabel(method);
        TACOperand isNull =
            new TACBinary(anchor, value, new TACLiteral(anchor, new ShadowNull(value.getType())));
        new TACBranch(anchor, isNull, skipLabel, makeCopyLabel);

        makeCopyLabel.insertBefore(anchor);

        TACMethodName copy =
            new TACMethodName(
                anchor,
                value,
                Type.OBJECT.getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)));

        value = new TACCall(anchor, copy, copy.getPrefix(), map);
        new TACCall(anchor, indexStore, array, new TACLocalLoad(anchor, i), value);
        new TACBranch(anchor, skipLabel);

        skipLabel.insertBefore(anchor);

        new TACLocalStore(
            anchor,
            i,
            new TACBinary(
                anchor,
                new TACLocalLoad(anchor, i),
                Type.LONG.getMatchingMethod("add", new SequenceType(Type.LONG)),
                "+",
                new TACLiteral(anchor, new ShadowInteger(1L)),
                false));
        new TACBranch(anchor, condition);

        done.insertBefore(anchor);

        new TACLocalStore(anchor, method.getLocal("return"), array);
      } else {
        // allocate a new object (which by default gets a ref count of 1)
        TACNewObject object = new TACNewObject(anchor, type);
        TACOperand duplicate;

        // add it to the map of addresses
        SequenceType arguments = new SequenceType();
        arguments.add(new SimpleModifiedType(Type.ULONG)); // key
        arguments.add(new SimpleModifiedType(Type.ULONG)); // value
        indexMethod =
            new TACMethodName(anchor, Type.ADDRESS_MAP.getMatchingMethod("index", arguments));
        TACOperand newAddress = new TACPointerToLong(anchor, object);
        new TACCall(anchor, indexMethod, map, address, newAddress);

        // perform a memcopy to sweep up all the primitives and immutable data (and nulls)
        TACOperand size = new TACLoad(anchor, new TACFieldRef(object.getClassData(), "size"));
        new TACCopyMemory(anchor, object, this_, size);

        if (type.equals(Type.OBJECT)) duplicate = object;
        else
          duplicate =
              TACCast.cast(anchor, new SimpleModifiedType(type), object); // casts object to type

        // copy other fields in using their copy methods
        // arrays need special attention
        TACMethodName copyMethod;
        TACOperand field;
        TACFieldRef newField;
        TACOperand copiedField;
        for (Entry<String, ? extends ModifiedType> entry : type.orderAllFields()) {
          ModifiedType entryType = entry.getValue();
          // only copy non-primitive types and non-singletons
          if (!entryType.getType().isPrimitive()
              && !(entryType.getType() instanceof SingletonType)) {
            // increment ref count on immutables (which have already been copied through memcpy)
            if (entryType.getModifiers().isImmutable())
              new TACChangeReferenceCount(
                  anchor, new TACFieldRef(duplicate, entryType, entry.getKey()), true);
            else {
              // get field references
              field = new TACLoad(anchor, new TACFieldRef(this_, entryType, entry.getKey()));
              newField = new TACFieldRef(duplicate, entryType, entry.getKey());

              TACLabel copyField = new TACLabel(method);
              TACLabel skipField = new TACLabel(method);

              if (entryType.getType() instanceof InterfaceType) {
                // cast converts from interface to object
                field = TACCast.cast(anchor, new SimpleModifiedType(Type.OBJECT), field);
                copyMethod =
                    new TACMethodName(
                        anchor,
                        field,
                        Type.OBJECT.getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)));
              } else // normal object or array
              copyMethod =
                    new TACMethodName(
                        anchor,
                        field,
                        entryType
                            .getType()
                            .getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)));

              TACOperand nullCondition =
                  new TACBinary(
                      anchor, field, new TACLiteral(anchor, new ShadowNull(field.getType())));
              new TACBranch(anchor, nullCondition, skipField, copyField); // if null, skip

              copyField.insertBefore(anchor);
              copiedField = new TACCall(anchor, copyMethod, field, map);

              if (entryType.getType() instanceof InterfaceType)
                // and then cast back to interface
                copiedField = TACCast.cast(anchor, newField, copiedField);

              // store copied value
              // should increment ref count
              TACStore store = new TACStore(anchor, newField, copiedField);
              // don't decrement old value, since the old field is sitting there because of memcpy
              store.setDecrementReference(false);
              new TACBranch(anchor, skipField);

              skipField.insertBefore(anchor);
            }
          }
        }

        new TACLocalStore(anchor, method.getLocal("return"), duplicate);
      }

      visitAllCleanups();

      new TACReturn(
          anchor,
          methodSignature.getSignatureWithoutTypeArguments().getFullReturnTypes(),
          new TACLocalLoad(anchor, method.getLocal("return")));
      new TACLabel(method).insertBefore(anchor); // Unreachable label

      returnLabel.insertBefore(anchor);

      indexMethod =
          new TACMethodName(
              anchor, Type.ADDRESS_MAP.getMatchingMethod("index", new SequenceType(Type.ULONG)));
      TACOperand index = new TACCall(anchor, indexMethod, map, address);
      TACOperand existingObject = new TACLongToPointer(anchor, index, new SimpleModifiedType(type));

      new TACLocalStore(anchor, method.getLocal("return"), existingObject);

      visitAllCleanups();

      new TACReturn(
          anchor,
          methodSignature.getSignatureWithoutTypeArguments().getFullReturnTypes(),
          new TACLocalLoad(anchor, method.getLocal("return")));
    }
    new TACLabel(method).insertBefore(anchor); // Unreachable label
  }

  private void visitGetOrSetMethod(MethodSignature methodSignature) {
    method.addParameters(anchor);
    TACFieldRef field =
        new TACFieldRef(new TACLocalLoad(anchor, method.getThis()), methodSignature.getSymbol());
    if (methodSignature.isGet()) {
      TACLoad load = new TACLoad(anchor, field);
      new TACLocalStore(anchor, method.getLocal("return"), load);

      visitAllCleanups();

      new TACReturn(
          anchor, methodSignature.getSignatureWithoutTypeArguments().getFullReturnTypes(), load);
      new TACLabel(method).insertBefore(anchor); // unreachable label
    } else if (methodSignature.isSet()) {
      TACVariable value = null;
      for (TACVariable parameter : method.getParameters()) value = parameter;
      new TACStore(anchor, field, new TACLocalLoad(anchor, value));

      visitAllCleanups();

      new TACReturn(
          anchor, methodSignature.getSignatureWithoutTypeArguments().getFullReturnTypes());
      new TACLabel(method).insertBefore(anchor); // unreachable label
    }
  }

  // Anything that's a pure TypeParameter in the unparameterized version will be kept, since
  // primitives and interfaces need special casting into TypeParameters
  private static SequenceType keepTypeParameters(
      SequenceType withArguments, SequenceType withoutArguments) {
    SequenceType result = new SequenceType();

    for (int i = 0; i < withArguments.size(); ++i) {
      if (withoutArguments.getType(i) instanceof TypeParameter) result.add(withoutArguments.get(i));
      else result.add(withArguments.get(i));
    }

    return result;
  }

  private void visitWrapperMethod(MethodSignature methodSignature) {
    // Do nothing for primitive wrappers for destroys
    if (methodSignature.isDestroy() && methodSignature.getOuter().isPrimitive()) {
      // add explicit return
      TACReturn explicitReturn = new TACReturn(anchor, new SequenceType());
      // prevents an error from being recorded if this return is later removed as dead code
      explicitReturn.setContext(null);
      new TACLabel(method).insertBefore(anchor); // unreachable label
    }

    MethodSignature wrapped = methodSignature.getWrapped();
    SequenceType fromTypes =
        keepTypeParameters(
            methodSignature.getFullParameterTypes(),
            methodSignature.getSignatureWithoutTypeArguments().getFullParameterTypes());
    SequenceType toTypes =
        keepTypeParameters(
            wrapped.getFullParameterTypes(),
            wrapped.getSignatureWithoutTypeArguments().getFullParameterTypes());
    Iterator<TACVariable> fromArguments =
        method.addParameters(anchor, true).getParameters().iterator();
    List<TACOperand> toArguments = new ArrayList<>(toTypes.size());

    for (int i = 0; i < toTypes.size(); i++) {
      TACOperand argument = new TACLocalLoad(anchor, fromArguments.next());
      if (!fromTypes.getType(i).isSubtype(toTypes.getType(i)))
        argument = TACCast.cast(anchor, toTypes.get(i), argument);
      toArguments.add(argument);
    }

    TACOperand value = new TACCall(anchor, new TACMethodName(anchor, wrapped), toArguments);

    TACLabel unreachableLabel = new TACLabel(method);

    // turn context off to avoid dead code removal errors
    Context context = anchor.getContext();
    anchor.setContext(null);

    if (methodSignature.getFullReturnTypes().isEmpty()) {
      visitAllCleanups();

      // turn context back on
      anchor.setContext(context);

      new TACReturn(
          anchor, methodSignature.getSignatureWithoutTypeArguments().getFullReturnTypes(), null);
    } else {
      fromTypes =
          keepTypeParameters(
              wrapped.getFullReturnTypes(),
              wrapped.getSignatureWithoutTypeArguments().getFullReturnTypes());
      toTypes =
          keepTypeParameters(
              methodSignature.getFullReturnTypes(),
              methodSignature.getSignatureWithoutTypeArguments().getFullReturnTypes());

      // cast all returns and store them in appropriate variables
      if (value.getType() instanceof SequenceType) {
        SequenceType sequenceType = (SequenceType) value.getType();
        for (int i = 0; i < sequenceType.size(); ++i)
          new TACLocalStore(
              anchor,
              method.getLocal("_return" + i),
              TACCast.cast(anchor, toTypes.get(i), new TACSequenceElement(anchor, value, i)));
      } else {
        if (!fromTypes.getType(0).isSubtype(toTypes.getType(0)))
          value = TACCast.cast(anchor, toTypes.get(0), value);
        new TACLocalStore(anchor, method.getLocal("return"), value);
      }

      visitAllCleanups();

      // turn context back on
      anchor.setContext(context);

      if (value.getType() instanceof SequenceType) {
        SequenceType sequenceType = (SequenceType) value.getType();
        List<TACOperand> operands = new ArrayList<>(sequenceType.size());
        for (int i = 0; i < sequenceType.size(); ++i)
          operands.add(new TACLocalLoad(anchor, method.getLocal("_return" + i)));
        new TACReturn(anchor, toTypes, new TACSequence(anchor, operands));
      } else new TACReturn(anchor, toTypes, new TACLocalLoad(anchor, method.getLocal("return")));
    }

    unreachableLabel.insertBefore(anchor);
  }

  private void visitRegularMethod(MethodSignature methodSignature) {
    boolean implicitCreate = false;

    method.addParameters(anchor);

    if (methodSignature.isCreate()) {
      ShadowParser.CreateDeclarationContext declaration =
          (ShadowParser.CreateDeclarationContext) methodSignature.getNode();
      implicitCreate =
          declaration.createBlock() == null
              || declaration.createBlock().explicitCreateInvocation() == null;
    }

    // Call parent create if implicit create.
    if (implicitCreate) {
      ClassType thisType = (ClassType) methodSignature.getOuter(),
          superType = thisType.getExtendType();
      if (superType != null) {
        TACCall call =
            new TACCall(
                anchor,
                new TACMethodName(
                    anchor, superType.getMatchingMethod("create", new SequenceType())),
                new TACLocalLoad(anchor, method.getThis()));

        call.setDelegatedCreate(true);
      }

      // Walk fields in *exactly* the order they were declared since
      // some fields depend on prior fields.
      // This is accomplished by using a LinkedHashMap.
      for (ShadowParser.VariableDeclaratorContext field :
          methodSignature.getOuter().getFields().values())
        if (!field.getModifiers().isConstant() && !(field.getType() instanceof SingletonType)) {
          visit(field);
          field.appendBefore(anchor);
        }
    }

    visit(methodSignature.getNode());
    methodSignature.getNode().appendBefore(anchor);

    // fill in all the fields to destroy
    if (methodSignature.isDestroy()) {

      if (!methodSignature.getOuter().isPrimitive()) {
        TACOperand this_ = new TACLocalLoad(anchor, method.getThis());

        ClassType classType = (ClassType) (methodSignature.getOuter());
        for (Entry<String, ? extends ModifiedType> entry : classType.sortFields()) {
          TACFieldRef reference = new TACFieldRef(this_, entry.getKey());
          if (reference.needsGarbageCollection())
            new TACChangeReferenceCount(anchor, reference, false);
        }

        // the mirror image of a create: calls parent destroy *afterwards*
        ClassType thisType = (ClassType) methodSignature.getOuter(),
            superType = thisType.getExtendType();
        if (superType != null)
          new TACCall(
              anchor,
              new TACMethodName(anchor, superType.getMatchingMethod("destroy", new SequenceType())),
              new TACLocalLoad(anchor, method.getThis()));
      }

      // add explicit return
      TACReturn explicitReturn = new TACReturn(anchor, new SequenceType());
      // prevents an error from being recorded if this return is later removed as dead code
      explicitReturn.setContext(null);
      new TACLabel(method).insertBefore(anchor); // unreachable label
    }
  }

  private TACFinallyFunction visitFinallyFunction(ShadowParser.BlockContext ctx) {
    TACNode saveTree = anchor;
    // Creation of the finally function adds it to the module
    TACFinallyFunction function = method.new TACFinallyFunction(block.getFinallyFunction());

    TACBlock saveBlock = block;
    block = new TACBlock(method);
    block.setFinallyFunction(function);
    anchor = new TACDummyNode(null, block);

    TACLabel label = new TACLabel(method); // always start with a label
    label.appendBefore(anchor);

    // The finally that decrements references has no block and must be added later
    if (ctx != null) {
      // We use this variable to hold in-flight exceptions temporarily
      if (!Configuration.isWindows())
        function.setExceptionStorage(
            new TACVariable(
                new SimpleModifiedType(Type.getExceptionType()),
                "_exception" + function.getNumber(),
                method));
      visitBlock(ctx);
    }

    // All finally functions end with void return
    function.setReturn(new TACReturn(anchor, new SequenceType()));

    anchor = anchor.remove(); // Gets node before anchor (and removes dummy)
    function.setNode(label); // The label is the first node

    anchor = saveTree;
    block = saveBlock;

    // Check for empty finally
    if (ctx != null) {
      TACNode node = label;
      boolean meaningful = false;
      do {
        if (isMeaningfulInFinally(node)) meaningful = true;

        node = node.getNext();
      } while (node != label && !meaningful);

      if (meaningful) {
        // Store the backup of the in-flight exception back into the _exception variable
        if (!Configuration.isWindows())
          new TACLocalStore(
              function.getReturn(),
              method.getLocal("_exception"),
              new TACLocalLoad(function.getReturn(), function.getExceptionStorage()));
        return function;
      } else {
        method.removeFinallyFunction(function);
        return null;
      }
    } else return function;
  }

  private static boolean isMeaningfulInFinally(TACNode node) {
    return !(node instanceof TACAllocateVariable
        || node instanceof TACBinary
        || node instanceof TACBranch
        || node instanceof TACCast
        || node instanceof TACClass
        || node instanceof TACDummyNode
        || node instanceof TACLabel
        || node instanceof TACLabelAddress
        || node instanceof TACLiteral
        || node instanceof TACLocalEscape
        || node instanceof TACLocalRecover
        || node instanceof TACLocalLoad
        || node instanceof TACPhi
        || node instanceof TACPointerToLong
        || node instanceof TACReturn
        || // Doesn't matter in finally
        node instanceof TACSequence
        || node instanceof TACTypeId
        || node instanceof TACUnary);
  }

  @SuppressWarnings("ConstantConditions")
  private void visitMethod(MethodSignature methodSignature) {
    TACNode saveTree = anchor;
    TACMethod method = this.method = new TACMethod(methodSignature);

    if (moduleStack.peek().isClass()
        && !methodSignature.isImport()
        && !methodSignature.isAbstract()) {

      TACLabel startingLabel = setupMethod();

      if (methodSignature.getSymbol().equals("copy") && !methodSignature.isWrapper())
        visitCopyMethod(methodSignature);
      // Gets and sets that were created by default (that's why they have a null parent)
      else if (methodSignature.getNode().getParent() == null
          && (methodSignature.isGet() || methodSignature.isSet()))
        visitGetOrSetMethod(methodSignature);
      else if (methodSignature.isWrapper()) visitWrapperMethod(methodSignature);
      else // Regular method, create, or destroy (includes empty creates and destroys)
      visitRegularMethod(methodSignature);

      cleanupMethod();

      anchor = anchor.remove(); // Gets node before anchor (and removes dummy)
      method.setNode(startingLabel); // Starting node for the method
    }

    moduleStack.peek().addMethod(method);
    block = null;
    this.method = null;
    anchor = saveTree;
  }

  private void visitBinaryOperation(Context node, List<? extends Context> list) {
    TACOperand current = list.get(0).appendBefore(anchor);

    for (int i = 1; i < list.size(); i++) {
      String op = node.getChild(2 * i - 1).getText(); // the operations are every other child
      TACOperand next = list.get(i).appendBefore(anchor);
      MethodSignature signature = node.getOperations().get(i - 1);
      boolean isCompare = (op.equals("<") || op.equals(">") || op.equals("<=") || op.equals(">="));
      Type currentType = resolveType(current.getType());
      if (currentType.isPrimitive() && signature.isImport()) // operation based on method
      current = new TACBinary(anchor, current, signature, op, next, isCompare);
      else {
        // comparisons will always give positive, negative or zero integer
        // must be compared to 0 with regular int comparison to work
        if (isCompare) {
          TACVariable var = method.addTempLocal(new SimpleModifiedType(Type.INT));
          new TACLocalStore(
              anchor,
              var,
              new TACCall(anchor, new TACMethodName(anchor, current, signature), current, next));
          current = new TACLocalLoad(anchor, var);
          current =
              new TACBinary(
                  anchor,
                  current,
                  Type.INT.getMatchingMethod("compare", new SequenceType(Type.INT)),
                  op,
                  new TACLiteral(anchor, new ShadowInteger(0)),
                  true);
        } else
          current =
              new TACCall(anchor, new TACMethodName(anchor, current, signature), current, next);
      }
    }

    node.setOperand(current);
  }

  private void initializeArray(
      TACOperand array, MethodSignature create, List<TACOperand> params, TACOperand defaultValue) {
    // nothing to do
    if (create == null && defaultValue == null) return;

    TACLabel bodyLabel = new TACLabel(method),
        updateLabel = new TACLabel(method),
        conditionLabel = new TACLabel(method),
        endLabel = new TACLabel(method);

    // make iterator (int index)
    TACVariable iterator = method.addTempLocal(new SimpleModifiedType(Type.LONG));
    new TACLocalStore(anchor, iterator, new TACLiteral(anchor, new ShadowInteger(0)));
    TACOperand length = arraySize(anchor, array, true);
    new TACBranch(anchor, conditionLabel); // init is done, jump to condition

    bodyLabel.insertBefore(anchor);

    // put initialization before main body
    TACArrayRef location =
        new TACArrayRef(anchor, array, new TACLocalLoad(anchor, iterator), false);

    if (create != null)
      new TACStore(anchor, location, callCreate(create, params, create.getOuter()));
    else new TACStore(anchor, location, defaultValue);

    new TACBranch(anchor, updateLabel);
    updateLabel.insertBefore(anchor);

    // increment iterator
    TACOperand value =
        new TACBinary(
            anchor,
            new TACLocalLoad(anchor, iterator),
            Type.LONG.getMatchingMethod("add", new SequenceType(Type.LONG)),
            "+",
            new TACLiteral(anchor, new ShadowInteger(1)));
    new TACLocalStore(anchor, iterator, value);

    new TACBranch(anchor, conditionLabel);
    conditionLabel.insertBefore(anchor);

    // check if iterator < array length
    value = new TACLocalLoad(anchor, iterator);
    TACOperand condition =
        new TACBinary(
            anchor,
            value,
            Type.LONG.getMatchingMethod("compare", new SequenceType(Type.LONG)),
            "<",
            length,
            true);

    new TACBranch(anchor, condition, bodyLabel, endLabel);
    endLabel.insertBefore(anchor);
  }

  private TACOperand visitArrayAllocation(
      ArrayType type,
      TACClass arrayClass,
      List<TACOperand> sizes,
      MethodSignature create,
      List<TACOperand> params) {
    return visitArrayAllocation(type, arrayClass, sizes, 0, create, params, null);
  }

  private TACOperand visitArrayAllocation(
      ArrayType type, TACClass arrayClass, List<TACOperand> sizes, TACOperand defaultValue) {
    return visitArrayAllocation(type, arrayClass, sizes, 0, null, null, defaultValue);
  }

  private TACOperand visitArrayAllocation(
      ArrayType type, TACClass arrayClass, List<TACOperand> sizes) {
    return visitArrayAllocation(type, arrayClass, sizes, 0, null, null, null);
  }

  private TACOperand visitArrayAllocation(
      ArrayType type,
      TACClass arrayClass,
      List<TACOperand> sizes,
      int dimension,
      MethodSignature create,
      List<TACOperand> params,
      TACOperand defaultValue) {
    TACOperand arrayClassData = arrayClass.getClassData();
    TACNewArray alloc = new TACNewArray(anchor, type, arrayClassData, sizes.get(dimension));
    if (dimension < sizes.size() - 1) {
      ArrayType baseType = (ArrayType) type.getBaseType();
      TACVariable index = method.addTempLocal(new SimpleModifiedType(Type.INT));
      new TACLocalStore(anchor, index, new TACLiteral(anchor, new ShadowInteger(0)));
      TACClass class_ = new TACClass(anchor, baseType);
      TACLabel bodyLabel = new TACLabel(method),
          condLabel = new TACLabel(method),
          endLabel = new TACLabel(method);
      new TACBranch(anchor, condLabel);
      bodyLabel.insertBefore(anchor);
      new TACStore(
          anchor,
          new TACArrayRef(anchor, alloc, new TACLocalLoad(anchor, index), false),
          visitArrayAllocation(
              baseType, class_, sizes, dimension + 1, create, params, defaultValue));
      new TACLocalStore(
          anchor,
          index,
          new TACBinary(
              anchor,
              new TACLocalLoad(anchor, index),
              Type.INT.getMatchingMethod("add", new SequenceType(Type.INT)),
              "+",
              new TACLiteral(anchor, new ShadowInteger(1))));
      new TACBranch(anchor, condLabel);
      condLabel.insertBefore(anchor);
      new TACBranch(
          anchor,
          new TACBinary(anchor, new TACLocalLoad(anchor, index), sizes.get(dimension)),
          endLabel,
          bodyLabel);
      endLabel.insertBefore(anchor);
    } else {
      // fill with values
      initializeArray(alloc, create, params, defaultValue);
    }

    return alloc;
  }

  @Override
  public Void visitDestroy(ShadowParser.DestroyContext ctx) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Void visitForeachInit(ShadowParser.ForeachInitContext ctx) {
    visitChildren(ctx);

    method.addLocal(ctx, ctx.Identifier().getText());
    // append collection
    ctx.setOperand(ctx.conditionalExpression().appendBefore(anchor));
    return null;
  }

  @Override
  public Void visitInlineResults(ShadowParser.InlineResultsContext ctx) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Void visitSequenceRightSide(ShadowParser.SequenceRightSideContext ctx) {
    visitChildren(ctx);

    // Note: their instructions are appended, but the operands for each sequence element are
    // retrieved later and not saved here
    for (ShadowParser.ConditionalExpressionContext child : ctx.conditionalExpression())
      child.appendBefore(anchor);

    return null;
  }

  @Override
  public Void visitSequenceLeftSide(ShadowParser.SequenceLeftSideContext ctx) {
    visitChildren(ctx);

    // Note: their instructions are appended, but the operands for each sequence element are
    // retrieved later and not saved here
    for (ParseTree child : ctx.children)
      if (child instanceof Context) ((Context) child).appendBefore(anchor);

    return null;
  }

  @Override
  public Void visitSequenceVariable(ShadowParser.SequenceVariableContext ctx) {
    visitChildren(ctx);

    TACVariable var = method.addLocal(ctx, ctx.Identifier().getText());

    prefix = new TACLocalLoad(anchor, var);
    ctx.setOperand(prefix);

    return null;
  }

  @Override
  public Void visitRightSide(ShadowParser.RightSideContext ctx) {
    visitChildren(ctx);

    if (ctx.sequenceRightSide() != null) {
      ctx.sequenceRightSide().appendBefore(anchor);
      List<TACOperand> sequence =
          new ArrayList<>(ctx.sequenceRightSide().conditionalExpression().size());
      for (ShadowParser.ConditionalExpressionContext child :
          ctx.sequenceRightSide().conditionalExpression()) sequence.add(child.getOperand());
      ctx.setOperand(new TACSequence(anchor, sequence));
    } else ctx.setOperand(ctx.conditionalExpression().appendBefore(anchor));

    return null;
  }

  @Override
  public Void visitCopyExpression(ShadowParser.CopyExpressionContext ctx) {
    visitChildren(ctx);

    TACOperand value = ctx.conditionalExpression().appendBefore(anchor);
    prefix = value;
    Type type = ctx.getType();

    if (!type.getModifiers().isImmutable()) { // if immutable, do nothing, the old one is fine
      TACNewObject object = new TACNewObject(anchor, Type.ADDRESS_MAP);
      TACMethodName create =
          new TACMethodName(
              anchor, Type.ADDRESS_MAP.getMatchingMethod("create", new SequenceType()));
      TACOperand map = new TACCall(anchor, create, object);

      TACMethodName copyMethod;
      TACVariable result = method.addTempLocal(ctx);
      TACOperand data = value;

      TACLabel nullLabel = new TACLabel(method);
      TACLabel doneLabel = new TACLabel(method);
      TACLabel copyLabel = new TACLabel(method);

      if (type instanceof InterfaceType) {
        // cast converts from interface to object
        data = TACCast.cast(anchor, new SimpleModifiedType(Type.OBJECT), data);
        TACOperand nullCondition =
            new TACBinary(anchor, data, new TACLiteral(anchor, new ShadowNull(data.getType())));
        new TACBranch(anchor, nullCondition, nullLabel, copyLabel);
        copyLabel.insertBefore(anchor);
        copyMethod =
            new TACMethodName(
                anchor,
                data,
                Type.OBJECT.getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)));
      } else {
        TACOperand nullCondition =
            new TACBinary(anchor, data, new TACLiteral(anchor, new ShadowNull(data.getType())));
        new TACBranch(anchor, nullCondition, nullLabel, copyLabel);
        copyLabel.insertBefore(anchor);
        copyMethod =
            new TACMethodName(
                anchor, data, type.getMatchingMethod("copy", new SequenceType(Type.ADDRESS_MAP)));
      }

      TACOperand copy = new TACCall(anchor, copyMethod, data, map);

      if (type instanceof InterfaceType)
        // and then a cast back to interface
        copy = TACCast.cast(anchor, ctx, copy);

      new TACLocalStore(anchor, result, copy);
      new TACBranch(anchor, doneLabel);

      nullLabel.insertBefore(anchor);

      new TACLocalStore(anchor, result, value);
      new TACBranch(anchor, doneLabel);

      doneLabel.insertBefore(anchor);
      prefix = new TACLocalLoad(anchor, result);
    }

    ctx.setOperand(prefix);

    return null;
  }

  @Override
  public Void visitArrayDimensions(ShadowParser.ArrayDimensionsContext ctx) {
    visitChildren(ctx);

    // Note: their instructions are appended, but the operands for each dimension are retrieved
    // later and not saved here
    for (ShadowParser.ConditionalExpressionContext child : ctx.conditionalExpression())
      child.appendBefore(anchor);

    return null;
  }

  @Override
  public Void visitArrayCreateCall(ArrayCreateCallContext ctx) {
    visitChildren(ctx);
    for (ShadowParser.ConditionalExpressionContext child : ctx.conditionalExpression())
      child.appendBefore(anchor);

    return null;
  }

  @Override
  public Void visitArrayDefault(ArrayDefaultContext ctx) {
    visitChildren(ctx);
    // For GC reasons, we need to save this value, then load it
    // Otherwise, each store of a new value might not be incremented in order to avoid
    // double-incrementing a freshly allocated object
    TACOperand defaultValue = ctx.conditionalExpression().appendBefore(anchor);
    TACVariable temporary = method.addTempLocal(defaultValue);
    new TACLocalStore(anchor, temporary, defaultValue);
    TACOperand value = new TACLocalLoad(anchor, temporary);
    ctx.setOperand(value);

    return null;
  }

  @Override
  public Void visitSpawnExpression(shadow.parse.ShadowParser.SpawnExpressionContext ctx) {
    visitChildren(ctx);

    Type runnerType = ctx.type().getType();

    MethodSignature runnerCreateSignature = ctx.spawnRunnerCreateCall().getSignature();
    List<TACOperand> runnerParams =
        new ArrayList<>(((SequenceType) ctx.spawnRunnerCreateCall().getType()).size() + 1);

    ctx.spawnRunnerCreateCall().appendBefore(anchor);
    for (ShadowParser.ConditionalExpressionContext child :
        ctx.spawnRunnerCreateCall().conditionalExpression()) {
      runnerParams.add(child.getOperand());
    }

    TACOperand runnerRef = callCreate(runnerCreateSignature, runnerParams, runnerType);

    MethodSignature threadCreateSignature = ctx.getSignature();
    ArrayList<TACOperand> params = new ArrayList<>();
    if (ctx.StringLiteral() != null) {
      params.add(new TACLiteral(anchor, ShadowString.parseString(ctx.StringLiteral().getText())));
    }
    params.add(runnerRef);

    prefix = callCreate(threadCreateSignature, params, Type.THREAD);
    ctx.setOperand(prefix);

    return null;
  }

  @Override
  public Void visitSpawnRunnerCreateCall(
      shadow.parse.ShadowParser.SpawnRunnerCreateCallContext ctx) {
    visitChildren(ctx);

    for (ShadowParser.ConditionalExpressionContext child : ctx.conditionalExpression()) {
      child.appendBefore(anchor);
    }

    return null;
  }

  @Override
  public Void visitSendStatement(SendStatementContext ctx) {
    visitChildren(ctx);

    TACOperand object = ctx.conditionalExpression(0).appendBefore(anchor);
    TACOperand thread = ctx.conditionalExpression(1).appendBefore(anchor);

    TACMethodName methodRef = new TACMethodName(anchor, thread, ctx.getSignature());
    List<TACOperand> params = new ArrayList<>();
    params.add(methodRef.getPrefix());
    params.add(object);

    new TACCall(anchor, methodRef, params);

    return null;
  }
}
