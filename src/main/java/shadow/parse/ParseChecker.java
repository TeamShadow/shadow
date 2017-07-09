package shadow.parse;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import shadow.ShadowException;
import shadow.doctool.Documentation;
import shadow.doctool.DocumentationBuilder;
import shadow.doctool.DocumentationException;
import shadow.parse.ParseException.Error;
import shadow.parse.ShadowParser.ClassOrInterfaceBodyDeclarationContext;
import shadow.parse.ShadowParser.CompilationUnitContext;
import shadow.parse.ShadowParser.MethodDeclaratorContext;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.type.Modifiers;

public class ParseChecker extends ShadowVisitorErrorReporter {
	
	private CommonTokenStream tokens;
	private int checkedIndex = -1;
	private DocumentationBuilder docBuilder;

	public ParseChecker(ErrorReporter reporter) {
		super(reporter);
	}
	
	public Context getCompilationUnit(Path path) throws IOException, ShadowException {
		CharStream stream = CharStreams.fromPath(path.toAbsolutePath());		
		return getCompilationUnit(stream);
	}
	
	public Context getCompilationUnit(String data, String source) throws IOException, ShadowException {
		CharStream stream = CharStreams.fromString(data, source);		
		return getCompilationUnit(stream);
	}
	
	private Context getCompilationUnit(CharStream stream) throws IOException, ShadowException {	
		ParseErrorListener listener = new ParseErrorListener(getErrorReporter());
		
		ShadowLexer lexer = new ShadowLexer(stream);
		lexer.removeErrorListeners();
				
		tokens = new CommonTokenStream(lexer);		
		docBuilder = new DocumentationBuilder();		
		checkedIndex = -1;
		
		Context context = null;
		
		ShadowParser parser = new ShadowParser(tokens);		
		
		//two-step parsing uses simpler parser unless there's an error
		parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
		parser.removeErrorListeners();
		parser.setErrorHandler(new BailErrorStrategy());
		
		
		try {
			context = parser.compilationUnit();
		}
		catch(ParseCancellationException e) {
			tokens.seek(0);
			parser.reset();
			lexer.addErrorListener(listener);
			parser.addErrorListener(listener);	
			parser.setErrorHandler(new DefaultErrorStrategy());
			
			//use more complex prediction mode if failed before
			parser.getInterpreter().setPredictionMode(PredictionMode.LL);		
			context = parser.compilationUnit();
		}

		if( getErrorReporter().getErrorList().size() == 0)
			visit(context);

		printAndReportErrors();
		return context;
	}
	
	
	@Override public Void visitModifiers(ShadowParser.ModifiersContext ctx)
	{ 
		addDocumentation(ctx);
		
		for( ShadowParser.ModifierContext modifier : ctx.modifier() ) {			
			boolean newModifier = true;
			
			switch( modifier.getText() ) {
				case "public": newModifier = ctx.addModifiers(Modifiers.PUBLIC); break; 
				case "protected": newModifier = ctx.addModifiers(Modifiers.PROTECTED); break;  
				case "private":  newModifier = ctx.addModifiers(Modifiers.PRIVATE); break;
				case "abstract":  newModifier = ctx.addModifiers(Modifiers.ABSTRACT); break;
				case "readonly":  newModifier = ctx.addModifiers(Modifiers.READONLY); break;
				case "native":  newModifier = ctx.addModifiers(Modifiers.NATIVE); break;
				case "extern":	newModifier = ctx.addModifiers(Modifiers.EXTERN); break;
				case "weak":  newModifier = ctx.addModifiers(Modifiers.WEAK); break;
				case "immutable": newModifier = ctx.addModifiers(Modifiers.IMMUTABLE); break;
				case "nullable":  newModifier = ctx.addModifiers(Modifiers.NULLABLE); break;
				case "get":  newModifier = ctx.addModifiers(Modifiers.GET); break;
				case "set":  newModifier = ctx.addModifiers(Modifiers.SET); break;
				case "constant":  newModifier = ctx.addModifiers(Modifiers.CONSTANT); break;
				case "locked":  newModifier = ctx.addModifiers(Modifiers.LOCKED); break;
			}
			
			if( !newModifier )				
				addError(modifier, Error.REPEATED_MODIFIERS, "Repeated modifer " + modifier.getText());
		}		
		
		return null;
	}
	
	@Override public Void visitCompilationUnit(ShadowParser.CompilationUnitContext ctx)
	{	
		visitChildren(ctx);
		
		Modifiers modifiers = ctx.modifiers().getModifiers();
		
		if( ctx.classOrInterfaceDeclaration() != null )
			checkClassOrInterfaceDeclaration( ctx.classOrInterfaceDeclaration(), ctx.modifiers());		
		else if( ctx.enumDeclaration() != null ) {
			ctx.enumDeclaration().addModifiers(modifiers);
			addErrors(ctx.modifiers(), modifiers.checkEnumModifiers(ctx));
		}	
		
		return null;
	}
	
	private ShadowParser.ModifiersContext getModifiers(Context ctx) {
		ParserRuleContext parent = ctx.getParent();
		if( parent instanceof ShadowParser.ClassOrInterfaceBodyDeclarationContext ) {
			ShadowParser.ClassOrInterfaceBodyDeclarationContext declaration = (ClassOrInterfaceBodyDeclarationContext) parent;
			return declaration.modifiers();
		}
		else if( parent instanceof ShadowParser.CompilationUnitContext ) {
			ShadowParser.CompilationUnitContext unit = (CompilationUnitContext) parent;
			return unit.modifiers();
		}
		
		return null;
	}
	
	@Override public Void visitCreateDeclaration(ShadowParser.CreateDeclarationContext ctx)
	{
		addDocumentation(ctx);
		ctx.setDocumentation(getDocumentation());
		
		ShadowParser.ModifiersContext mods = getModifiers(ctx);
		Modifiers modifiers = mods.getModifiers();
		ctx.addModifiers(modifiers);
		addErrors(mods, modifiers.checkCreateModifiers(ctx));
		
		visitChildren(ctx);
		
		return null;
	}
	
	
	private void addDocumentation(Context ctx)
	{
		Token start = ctx.getStart();
		if( checkedIndex < start.getTokenIndex() ) { //only get comments that haven't been gotten
			checkedIndex = start.getTokenIndex();
			List<Token> comments = tokens.getHiddenTokensToLeft(start.getTokenIndex());
			
			
			if( comments != null && comments.size() > 0 ) {			
				for( Token token : comments ) {
					if( token.getType() == ShadowLexer.DOCUMENTATION_COMMENT ) {
						String block = token.getText();
						block = block.substring(3); //clean off /**
						block = block.substring(0, block.length() - 2); //clean off */
						docBuilder.addBlock(block);
					}
					else if( token.getType() == ShadowLexer.LINE_DOCUMENTATION_COMMENT ) {
						docBuilder.appendLine(token.getText().substring(3)); //clean off ///
					}
				}
			}
		}
	}
	
	private Documentation getDocumentation() {
		try {
			return docBuilder.process();				
		}
		catch (DocumentationException e) {
			addError(e);
		}
		
		return null;
	}
	
	
	@Override public Void visitClassOrInterfaceDeclaration(ShadowParser.ClassOrInterfaceDeclarationContext ctx)
	{
		addDocumentation(ctx);
		ctx.setDocumentation(getDocumentation());	
				
		checkClassOrInterfaceDeclaration(ctx, getModifiers(ctx));		
		
		return visitChildren(ctx);
	}
	
	@Override public Void visitEnumDeclaration(ShadowParser.EnumDeclarationContext ctx)
	{
		addDocumentation(ctx);
		ctx.setDocumentation(getDocumentation());
		
		ShadowParser.ModifiersContext mods = getModifiers(ctx);
		Modifiers modifiers = mods.getModifiers();
		ctx.addModifiers(modifiers);
		addErrors(mods, modifiers.checkEnumModifiers(ctx));
		
		return visitChildren(ctx);
	}
	
	@Override public Void visitDestroyDeclaration(ShadowParser.DestroyDeclarationContext ctx)
	{
		addDocumentation(ctx);
		ctx.setDocumentation(getDocumentation());
		
		ShadowParser.ModifiersContext mods = getModifiers(ctx);
		Modifiers modifiers = mods.getModifiers();
		ctx.addModifiers(modifiers);
		addErrors(mods, modifiers.checkDestroyModifiers(ctx));
		
		return visitChildren(ctx);		
	}
	
	@Override public Void visitFieldDeclaration(ShadowParser.FieldDeclarationContext ctx)
	{
		addDocumentation(ctx);
		ctx.setDocumentation(getDocumentation());
		
		ShadowParser.ModifiersContext mods = getModifiers(ctx);
		Modifiers modifiers = mods.getModifiers();
		ctx.addModifiers(modifiers);
		addErrors(mods, modifiers.checkFieldModifiers(ctx));
		ctx.type().addModifiers(modifiers); //also add to type
		
		return visitChildren(ctx);		
	}
	
	@Override public Void visitMethodDeclaration(ShadowParser.MethodDeclarationContext ctx)
	{
		addDocumentation(ctx);
		ctx.setDocumentation(getDocumentation());
		
		ShadowParser.ModifiersContext mods = getModifiers(ctx);
		Modifiers modifiers = mods.getModifiers();
		ctx.addModifiers(modifiers);
		addErrors(mods, modifiers.checkMethodModifiers(ctx));
		
		MethodDeclaratorContext declarator = ctx.methodDeclarator();
		String symbol = declarator.generalIdentifier().getText();
		
		if(symbol.startsWith("$") && modifiers.isPublic()) {
			addError(ctx, Error.ILLEGAL_MODIFIER, "Class externs cannot be public.");			
		}
		
		if(!declarator.type().isEmpty() && (!symbol.startsWith("$") || (symbol.startsWith("$") && modifiers.isExtern()))) {
			addError(ctx, Error.SYNTAX_ERROR, "Only class extern methods starting with $ can contain a list of allowed extern classes.");
		}
		
		return visitChildren(ctx);		
	}
	
	@Override public Void visitEmptyStatement(ShadowParser.EmptyStatementContext ctx)
	{
		if( ctx.getChild(0).getText().equals(";") )
			addError(ctx, Error.EMPTY_STATMENT, "An empty statement requires the skip keyword" );
		
		return null;		
	}
	
	private void checkClassOrInterfaceDeclaration(ShadowParser.ClassOrInterfaceDeclarationContext ctx, ShadowParser.ModifiersContext mods) {
		
		Modifiers modifiers = mods.getModifiers();
		ctx.addModifiers(modifiers);
		switch( ctx.getStart().getText() ) {
			case "class": addErrors(mods, modifiers.checkClassModifiers(ctx)); break;
			case "singleton": addErrors(mods, modifiers.checkSingletonModifiers(ctx)); break;
			case "exception": addErrors(mods, modifiers.checkExceptionModifiers(ctx)); break;
			case "interface": addErrors(mods, modifiers.checkInterfaceModifiers(ctx)); break;
		}
	}
	
	@Override public Void visitTypeParameter(ShadowParser.TypeParameterContext ctx)
	{		
		ctx.addModifiers(Modifiers.TYPE_NAME);
		return visitChildren(ctx); 
	}
	
	@Override public Void visitFormalParameter(ShadowParser.FormalParameterContext ctx) { 
		visitChildren(ctx);
		
		Modifiers modifiers = ctx.modifiers().getModifiers();
		ctx.type().addModifiers(modifiers);
		ctx.addModifiers(modifiers);
		addErrors(ctx.modifiers(), modifiers.checkParameterAndReturnModifiers(ctx));
		
		return null;
	}
	
	@Override public Void visitFunctionType(ShadowParser.FunctionTypeContext ctx)
	{		
		ctx.addModifiers(Modifiers.TYPE_NAME);
		return visitChildren(ctx); 
	}
	
	@Override public Void visitPrimitiveType(ShadowParser.PrimitiveTypeContext ctx)
	{		
		ctx.addModifiers(Modifiers.TYPE_NAME);
		return null;
	}
	
	@Override public Void visitResultType(ShadowParser.ResultTypeContext ctx) { 
		visitChildren(ctx);
		
		Modifiers modifiers = ctx.modifiers().getModifiers();
		ctx.type().addModifiers(modifiers);
		ctx.addModifiers(modifiers);
		addErrors(ctx.modifiers(), modifiers.checkParameterAndReturnModifiers(ctx));
		
		return null;
	}
	
	@Override public Void visitType(ShadowParser.TypeContext ctx) { 
		ctx.addModifiers(Modifiers.TYPE_NAME); //in case nothing sets modifiers
		return visitChildren(ctx);
	}
	
	@Override public Void visitSequenceVariable(ShadowParser.SequenceVariableContext ctx) { 
		visitChildren(ctx);
		
		Modifiers modifiers = ctx.modifiers().getModifiers();
		ctx.addModifiers(modifiers);
		if( ctx.type() != null )
			ctx.type().addModifiers(modifiers);
		addErrors(ctx.modifiers(), modifiers.checkLocalVariableModifiers(ctx));
		
		return null;
	}
	
	@Override public Void visitLiteral(ShadowParser.LiteralContext ctx) { 
		if( ctx.NullLiteral() != null )
			ctx.addModifiers(Modifiers.NULLABLE | Modifiers.CONSTANT);
		else
			ctx.addModifiers(Modifiers.IMMUTABLE | Modifiers.CONSTANT);
		
		//check to see if string contains newlines
		if( ctx.StringLiteral() != null )
			if( ctx.getText().indexOf('\n') >= 0 || ctx.getText().indexOf('\r') >= 0 )
				addError(ctx, Error.NEWLINE_IN_STRING, "String literal contains newline");
		return null;
	}
	
	@Override public Void visitLocalDeclaration(ShadowParser.LocalDeclarationContext ctx) {
		visitChildren(ctx);
		
		Modifiers modifiers = ctx.modifiers().getModifiers();
		if( ctx.localMethodDeclaration() != null ) {
			ctx.localMethodDeclaration().addModifiers(modifiers);
			addErrors(ctx.modifiers(), modifiers.checkLocalMethodModifiers(ctx));
		}
		else if( ctx.localVariableDeclaration() != null ) {
			ctx.localVariableDeclaration().addModifiers(modifiers);
			addErrors(ctx.modifiers(), modifiers.checkLocalVariableModifiers(ctx));
			if( ctx.localVariableDeclaration().type() != null )
				ctx.localVariableDeclaration().type().addModifiers(modifiers);
		}
		
		return null;
	}
	
	@Override public Void visitPrimaryExpression(ShadowParser.PrimaryExpressionContext ctx)
	{	
		if(ctx.getChild(0).getText().equals("++"))
			addError(ctx, Error.ILLEGAL_OPERATOR, "++ is not a legal operator");		
		
		visitChildren(ctx);
		
		return null;
	}
	
	@Override public Void visitPrimarySuffix(ShadowParser.PrimarySuffixContext ctx)
	{	
		if(!(ctx.getChild(0) instanceof Context) ) {
			String text = ctx.getChild(0).getText();
			if( text.equals("++") || text.equals("--"))			
				addError(ctx, Error.ILLEGAL_OPERATOR, text + " is not a legal operator");
		}
		
		visitChildren(ctx);
		
		return null;
	}
	
	@Override public Void visitForeachInit(ShadowParser.ForeachInitContext ctx) {
		visitChildren(ctx);
		
		Modifiers modifiers = ctx.modifiers().getModifiers();
		ctx.addModifiers(modifiers);
		if( ctx.type() != null )
			ctx.type().addModifiers(modifiers);
		addErrors(ctx.modifiers(), modifiers.checkLocalVariableModifiers(ctx));
		
		return null;
	}
	
	@Override public Void visitForInit(ShadowParser.ForInitContext ctx) {
		visitChildren(ctx);
		
		if( ctx.localVariableDeclaration() != null ) {
			Modifiers modifiers = ctx.modifiers().getModifiers();
			ctx.localVariableDeclaration().addModifiers(modifiers);
			addErrors(ctx.modifiers(), modifiers.checkLocalVariableModifiers(ctx));
			if( ctx.localVariableDeclaration().type() != null )
				ctx.localVariableDeclaration().type().addModifiers(modifiers);
		}
		
		return null;
	}
	
	@Override public Void visitFinallyStatement(ShadowParser.FinallyStatementContext ctx) {
		boolean recover = false;
		boolean catch_ = false;
		boolean finally_ = false;
		
		if( ctx.block() != null )
			finally_ = true;
		
		if( !ctx.recoverStatement().catchStatements().catchStatement().isEmpty() )
			catch_ = true;
		
		if( ctx.recoverStatement().block() != null )
			recover = true;
		
		if( !recover && !catch_ && !finally_ )			
			addError(ctx.recoverStatement().catchStatements().tryStatement(), Error.INCOMPLETE_TRY, "Given try statement is not followed by catch, recover, or finally statements" );
		
		return visitChildren(ctx);
	}
}