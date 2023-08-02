package shadow.parse;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.PredictionMode;
import shadow.doctool.Documentation;
import shadow.doctool.DocumentationBuilder;
import shadow.doctool.DocumentationException;
import shadow.parse.ParseException.Error;
import shadow.parse.ShadowParser.ClassOrInterfaceBodyDeclarationContext;
import shadow.parse.ShadowParser.CompilationUnitContext;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.type.Modifiers;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class ParseChecker extends ShadowVisitorErrorReporter {

  private CommonTokenStream tokens;
  private int checkedIndex = -1;
  private DocumentationBuilder docBuilder;

  public ParseChecker(ErrorReporter reporter) {
    super(reporter);
  }

  public CompilationUnitContext getCompilationUnit(Path path) throws IOException {
    CharStream stream = CharStreams.fromPath(path);
    return getCompilationUnit(stream);
  }

  public CompilationUnitContext getCompilationUnit(String source, Path path) {
    CharStream stream = CharStreams.fromString(source, path.toString());
    return getCompilationUnit(stream);
  }

  private CompilationUnitContext getCompilationUnit(CharStream stream) {
    ParseErrorListener listener = new ParseErrorListener(getErrorReporter());

    ShadowLexer lexer = new ShadowLexer(stream);
    lexer.removeErrorListeners();

    tokens = new CommonTokenStream(lexer);
    docBuilder = new DocumentationBuilder();
    checkedIndex = -1;

    CompilationUnitContext context;

    ShadowParser parser = new ShadowParser(tokens);

    // two-step parsing uses simpler parser unless there's an error
    parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
    parser.removeErrorListeners();
    parser.setErrorHandler(new BailErrorStrategy());

    try {
      context = parser.compilationUnit();
    } catch (Exception e) {
      tokens.seek(0);
      parser.reset();
      lexer.addErrorListener(listener);
      parser.addErrorListener(listener);
      parser.setErrorHandler(new DefaultErrorStrategy());

      // use more complex prediction mode if failed before
      parser.getInterpreter().setPredictionMode(PredictionMode.LL);
      context = parser.compilationUnit();
    }

    if (getErrorReporter().getErrorList().size() == 0) visit(context);

    return context;
  }

  @Override
  public Void visitModifiers(ShadowParser.ModifiersContext ctx) {
    addDocumentation(ctx);

    for (ShadowParser.ModifierContext modifier : ctx.modifier()) {
      boolean newModifier = switch (modifier.getText()) {
        case "public" -> ctx.addModifiers(Modifiers.PUBLIC);
        case "protected" -> ctx.addModifiers(Modifiers.PROTECTED);
        case "private" -> ctx.addModifiers(Modifiers.PRIVATE);
        case "abstract" -> ctx.addModifiers(Modifiers.ABSTRACT);
        case "readonly" -> ctx.addModifiers(Modifiers.READONLY);
        case "weak" -> ctx.addModifiers(Modifiers.WEAK);
        case "immutable" -> ctx.addModifiers(Modifiers.IMMUTABLE);
        case "nullable" -> ctx.addModifiers(Modifiers.NULLABLE);
        case "get" -> ctx.addModifiers(Modifiers.GET);
        case "set" -> ctx.addModifiers(Modifiers.SET);
        case "constant" -> ctx.addModifiers(Modifiers.CONSTANT);
        case "locked" -> ctx.addModifiers(Modifiers.LOCKED);
        default -> true;
      };

      if (!newModifier)
        addError(modifier, Error.REPEATED_MODIFIERS, "Repeated modifier " + modifier.getText());
    }

    return null;
  }

  @Override
  public Void visitCompilationUnit(ShadowParser.CompilationUnitContext ctx) {
    visitChildren(ctx);

    Modifiers modifiers = ctx.modifiers().getModifiers();

    if (ctx.classOrInterfaceDeclaration() != null)
      // TODO: Determine if this is redundant with the same check in
      // visitClassOrInterfaceDeclaration
      checkClassOrInterfaceDeclaration(ctx.classOrInterfaceDeclaration(), ctx.modifiers());
    else if (ctx.enumDeclaration() != null) {
      // TODO: Add enum case to getModifiers instead of calling addModifiers here
      ctx.enumDeclaration().addModifiers(modifiers);
      addErrors(ctx.modifiers(), modifiers.checkEnumModifiers(ctx));
    }

    return null;
  }

  private ShadowParser.ModifiersContext getModifiers(Context ctx) {
    ParserRuleContext parent = ctx.getParent();
    if (parent instanceof ClassOrInterfaceBodyDeclarationContext declaration) {
      return declaration.modifiers();
    } else if (parent instanceof CompilationUnitContext unit) {
      return unit.modifiers();
    }

    // TODO: Choose a more specific exception type
    throw new RuntimeException(
        "Unexpected getModifiers() call for a context that doesn't support modifiers");
  }

  @Override
  public Void visitCreateDeclaration(ShadowParser.CreateDeclarationContext ctx) {
    addDocumentation(ctx);
    ctx.setDocumentation(getDocumentation());

    ShadowParser.ModifiersContext mods = getModifiers(ctx);
    Modifiers modifiers = mods.getModifiers();
    ctx.addModifiers(modifiers);
    addErrors(mods, modifiers.checkCreateModifiers(ctx));

    visitChildren(ctx);

    return null;
  }

  private void addDocumentation(Context ctx) {
    Token start = ctx.getStart();
    if (checkedIndex < start.getTokenIndex()) { // only get comments that haven't been gotten
      checkedIndex = start.getTokenIndex();
      List<Token> comments = tokens.getHiddenTokensToLeft(start.getTokenIndex());

      if (comments != null && comments.size() > 0) {
        for (Token token : comments) {
          if (token.getType() == ShadowLexer.DOCUMENTATION_COMMENT) {
            String block = token.getText();
            block = block.substring(3); // clean off /**
            block = block.substring(0, block.length() - 2); // clean off */
            docBuilder.addBlock(block);
          } else if (token.getType() == ShadowLexer.LINE_DOCUMENTATION_COMMENT) {
            docBuilder.appendLine(token.getText().substring(3)); // clean off ///
          }
        }
      }
    }
  }

  private Documentation getDocumentation() {
    try {
      return docBuilder.process();
    } catch (DocumentationException e) {
      addError(e);
    }

    return null;
  }

  @Override
  public Void visitClassOrInterfaceDeclaration(
      ShadowParser.ClassOrInterfaceDeclarationContext ctx) {
    addDocumentation(ctx);
    ctx.setDocumentation(getDocumentation());

    checkClassOrInterfaceDeclaration(ctx, getModifiers(ctx));

    return visitChildren(ctx);
  }

  @Override
  public Void visitEnumDeclaration(ShadowParser.EnumDeclarationContext ctx) {
    addDocumentation(ctx);
    ctx.setDocumentation(getDocumentation());

    ShadowParser.ModifiersContext mods = getModifiers(ctx);
    Modifiers modifiers = mods.getModifiers();
    ctx.addModifiers(modifiers);
    addErrors(mods, modifiers.checkEnumModifiers(ctx));

    return visitChildren(ctx);
  }

  @Override
  public Void visitDestroyDeclaration(ShadowParser.DestroyDeclarationContext ctx) {
    addDocumentation(ctx);
    ctx.setDocumentation(getDocumentation());

    ShadowParser.ModifiersContext mods = getModifiers(ctx);
    Modifiers modifiers = mods.getModifiers();
    ctx.addModifiers(modifiers);
    addErrors(mods, modifiers.checkDestroyModifiers(ctx));

    return visitChildren(ctx);
  }

  @Override
  public Void visitAttributeInvocation(ShadowParser.AttributeInvocationContext ctx) {
    return visitChildren(ctx);
  }

  @Override
  public Void visitFieldDeclaration(ShadowParser.FieldDeclarationContext ctx) {
    addDocumentation(ctx);
    ctx.setDocumentation(getDocumentation());

    ShadowParser.ModifiersContext mods = getModifiers(ctx);
    Modifiers modifiers = mods.getModifiers();
    ctx.addModifiers(modifiers);
    addErrors(mods, modifiers.checkFieldModifiers(ctx));
    ctx.type().addModifiers(modifiers); // also add to type

    return visitChildren(ctx);
  }

  @Override
  public Void visitMethodDeclaration(ShadowParser.MethodDeclarationContext ctx) {
    addDocumentation(ctx);
    ctx.setDocumentation(getDocumentation());

    ShadowParser.ModifiersContext mods = getModifiers(ctx);
    Modifiers modifiers = mods.getModifiers();
    ctx.addModifiers(modifiers);
    addErrors(mods, modifiers.checkMethodModifiers(ctx));

    return visitChildren(ctx);
  }

  @Override
  public Void visitEmptyStatement(ShadowParser.EmptyStatementContext ctx) {
    if (ctx.getChild(0).getText().equals(";"))
      addError(ctx, Error.EMPTY_STATEMENT, "An empty statement requires the skip keyword");

    return null;
  }

  private void checkClassOrInterfaceDeclaration(
      ShadowParser.ClassOrInterfaceDeclarationContext ctx, ShadowParser.ModifiersContext mods) {

    Modifiers modifiers = mods.getModifiers();
    ctx.addModifiers(modifiers);
    switch (ctx.getStart().getText()) {
      case "attribute" -> addErrors(mods, modifiers.checkAttributeModifiers(ctx));
      case "class" -> addErrors(mods, modifiers.checkClassModifiers(ctx));
      case "singleton" -> addErrors(mods, modifiers.checkSingletonModifiers(ctx));
      case "exception" -> addErrors(mods, modifiers.checkExceptionModifiers(ctx));
      case "interface" -> addErrors(mods, modifiers.checkInterfaceModifiers(ctx));
    }
  }

  @Override
  public Void visitTypeParameter(ShadowParser.TypeParameterContext ctx) {
    ctx.addModifiers(Modifiers.TYPE_NAME);
    return visitChildren(ctx);
  }

  @Override
  public Void visitFormalParameter(ShadowParser.FormalParameterContext ctx) {
    visitChildren(ctx);

    Modifiers modifiers = ctx.modifiers().getModifiers();
    ctx.type().addModifiers(modifiers);
    ctx.addModifiers(modifiers);
    addErrors(ctx.modifiers(), modifiers.checkParameterAndReturnModifiers(ctx));

    return null;
  }

  @Override
  public Void visitFunctionType(ShadowParser.FunctionTypeContext ctx) {
    ctx.addModifiers(Modifiers.TYPE_NAME);
    return visitChildren(ctx);
  }

  @Override
  public Void visitPrimitiveType(ShadowParser.PrimitiveTypeContext ctx) {
    ctx.addModifiers(Modifiers.TYPE_NAME);
    return null;
  }

  @Override
  public Void visitResultType(ShadowParser.ResultTypeContext ctx) {
    visitChildren(ctx);

    Modifiers modifiers = ctx.modifiers().getModifiers();
    ctx.type().addModifiers(modifiers);
    ctx.addModifiers(modifiers);
    addErrors(ctx.modifiers(), modifiers.checkParameterAndReturnModifiers(ctx));

    return null;
  }

  @Override
  public Void visitType(ShadowParser.TypeContext ctx) {
    ctx.addModifiers(Modifiers.TYPE_NAME); // in case nothing sets modifiers
    return visitChildren(ctx);
  }

  @Override
  public Void visitSequenceVariable(ShadowParser.SequenceVariableContext ctx) {
    visitChildren(ctx);

    Modifiers modifiers = ctx.modifiers().getModifiers();
    ctx.addModifiers(modifiers);
    if (ctx.type() != null) ctx.type().addModifiers(modifiers);
    addErrors(ctx.modifiers(), modifiers.checkLocalVariableModifiers(ctx));

    return null;
  }

  @Override
  public Void visitLiteral(ShadowParser.LiteralContext ctx) {
    if (ctx.NullLiteral() != null) ctx.addModifiers(Modifiers.NULLABLE | Modifiers.CONSTANT);
    else ctx.addModifiers(Modifiers.IMMUTABLE | Modifiers.CONSTANT);

    // check to see if string contains newlines
    if (ctx.StringLiteral() != null)
      if (ctx.getText().indexOf('\n') >= 0 || ctx.getText().indexOf('\r') >= 0)
        addError(ctx, Error.NEWLINE_IN_STRING, "String literal contains newline");
    return null;
  }

  @Override
  public Void visitLocalDeclaration(ShadowParser.LocalDeclarationContext ctx) {
    visitChildren(ctx);

    Modifiers modifiers = ctx.modifiers().getModifiers();
    if (ctx.localMethodDeclaration() != null) {
      ctx.localMethodDeclaration().addModifiers(modifiers);
      addErrors(ctx.modifiers(), modifiers.checkLocalMethodModifiers(ctx));
    } else if (ctx.localVariableDeclaration() != null) {
      ctx.localVariableDeclaration().addModifiers(modifiers);
      addErrors(ctx.modifiers(), modifiers.checkLocalVariableModifiers(ctx));
      if (ctx.localVariableDeclaration().type() != null)
        ctx.localVariableDeclaration().type().addModifiers(modifiers);
    }

    return null;
  }

  @Override
  public Void visitPrimaryExpression(ShadowParser.PrimaryExpressionContext ctx) {
    if (ctx.getChild(0).getText().equals("++"))
      addError(ctx, Error.ILLEGAL_OPERATOR, "++ is not a legal operator");

    visitChildren(ctx);

    return null;
  }

  @Override
  public Void visitPrimarySuffix(ShadowParser.PrimarySuffixContext ctx) {
    if (!(ctx.getChild(0) instanceof Context)) {
      String text = ctx.getChild(0).getText();
      if (text.equals("++") || text.equals("--"))
        addError(ctx, Error.ILLEGAL_OPERATOR, text + " is not a legal operator");
    }

    visitChildren(ctx);

    return null;
  }

  @Override
  public Void visitForeachInit(ShadowParser.ForeachInitContext ctx) {
    visitChildren(ctx);

    Modifiers modifiers = ctx.modifiers().getModifiers();
    ctx.addModifiers(modifiers);
    if (ctx.type() != null) ctx.type().addModifiers(modifiers);
    addErrors(ctx.modifiers(), modifiers.checkLocalVariableModifiers(ctx));

    return null;
  }

  @Override
  public Void visitForInit(ShadowParser.ForInitContext ctx) {
    visitChildren(ctx);

    if (ctx.localVariableDeclaration() != null) {
      Modifiers modifiers = ctx.modifiers().getModifiers();
      ctx.localVariableDeclaration().addModifiers(modifiers);
      addErrors(ctx.modifiers(), modifiers.checkLocalVariableModifiers(ctx));
      if (ctx.localVariableDeclaration().type() != null)
        ctx.localVariableDeclaration().type().addModifiers(modifiers);
    }

    return null;
  }

  @Override
  public Void visitFinallyStatement(ShadowParser.FinallyStatementContext ctx) {
    boolean recover = false;
    boolean catch_ = false;
    boolean finally_ = ctx.block() != null;

    if (!ctx.catchStatements().catchStatement().isEmpty()) catch_ = true;

    if (ctx.catchStatements().block() != null) recover = true;

    if (!recover && !catch_ && !finally_)
      addError(
          ctx.catchStatements().tryStatement(),
          Error.INCOMPLETE_TRY,
          "Given try statement is not followed by catch, recover, or finally statements");

    return visitChildren(ctx);
  }
}
