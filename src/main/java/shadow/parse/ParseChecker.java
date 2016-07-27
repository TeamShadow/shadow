package shadow.parse;

import java.io.IOException;
import java.nio.file.Path;

import org.antlr.v4.runtime.BufferedTokenStream;

import shadow.ShadowException;
import shadow.parse.ParseException.Error;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.type.Modifiers;

public class ParseChecker extends ShadowVisitorErrorReporter {

	public ParseChecker(ErrorReporter reporter) 
	{
		super(reporter);
	}
	
	public Context getCompilationUnit(Path path) throws IOException, ShadowException
	{	
		//DocumentationBuilder docBuilder = new DocumentationBuilder();		
		ParseErrorListener listener = new ParseErrorListener(getErrorReporter());
		
		ShadowLexer lexer = new ShadowLexer(new PathStream(path));
		lexer.removeErrorListeners();
		lexer.addErrorListener(listener);
		
		ShadowParser parser = new ShadowParser(new BufferedTokenStream(lexer));
		parser.removeErrorListeners();
		parser.addErrorListener(listener);
		//parser.docBuilder = docBuilder;
		
		Context context = null;
		context = parser.compilationUnit();
		visit(context);
		
		/*
		try
		{		
			 	
		}
		catch(RecognitionException e)
		{
			addError(new ParseException(Error.SYNTAX_ERROR, e.getLocalizedMessage()));
		}
		*/
		
		printAndReportErrors();
		return context;
	}
	
	
	
	@Override public Void visitModifiers(ShadowParser.ModifiersContext ctx)
	{ 		
		for( ShadowParser.ModifierContext modifier : ctx.modifier() ) {			
			boolean newModifier = true;
			
			switch( modifier.getText() ) {
				case "public": newModifier = ctx.addModifiers(Modifiers.PUBLIC); break; 
				case "protected": newModifier = ctx.addModifiers(Modifiers.PROTECTED); break;  
				case "private":  newModifier = ctx.addModifiers(Modifiers.PRIVATE); break;
				case "abstract":  newModifier = ctx.addModifiers(Modifiers.ABSTRACT); break;
				case "readonly":  newModifier = ctx.addModifiers(Modifiers.READONLY); break;
				case "native":  newModifier = ctx.addModifiers(Modifiers.NATIVE); break;
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
			addErrors(ctx.modifiers(), modifiers.checkEnumModifiers());
		}	
		
		return null;
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
			case "class": addErrors(mods, modifiers.checkClassModifiers()); break;
			case "singleton": addErrors(mods, modifiers.checkSingletonModifiers()); break;
			case "exception": addErrors(mods, modifiers.checkExceptionModifiers()); break;
			case "interface": addErrors(mods, modifiers.checkInterfaceModifiers()); break;
		}
	}
	
	@Override public Void visitTypeParameter(ShadowParser.TypeParameterContext ctx)
	{		
		ctx.addModifiers(Modifiers.TYPE_NAME);
		return visitChildren(ctx); 
	}
	
	@Override public Void visitClassOrInterfaceBodyDeclaration(ShadowParser.ClassOrInterfaceBodyDeclarationContext ctx) { 
		visitChildren(ctx);
		
		Modifiers modifiers = ctx.modifiers().getModifiers();
		
		if( ctx.classOrInterfaceDeclaration() != null )
			checkClassOrInterfaceDeclaration( ctx.classOrInterfaceDeclaration(), ctx.modifiers());
		else if( ctx.enumDeclaration() != null ) {
			ctx.enumDeclaration().addModifiers(modifiers);
			addErrors(ctx.modifiers(), modifiers.checkEnumModifiers());
		}
		else if( ctx.createDeclaration() != null ) {
			ctx.createDeclaration().addModifiers(modifiers);
			addErrors(ctx.modifiers(), modifiers.checkCreateModifiers());
		}
		else if( ctx.destroyDeclaration() != null ) {
			ctx.destroyDeclaration().addModifiers(modifiers);
			addErrors(ctx.modifiers(), modifiers.checkDestroyModifiers());
		}
		else if( ctx.fieldDeclaration() != null ) {
			ctx.fieldDeclaration().addModifiers(modifiers);
			addErrors(ctx.modifiers(), modifiers.checkFieldModifiers());
			ctx.fieldDeclaration().type().addModifiers(modifiers);
		}
		else if( ctx.methodDeclaration() != null ) {
			ctx.methodDeclaration().addModifiers(modifiers);
			addErrors(ctx.modifiers(), modifiers.checkMethodModifiers());
		}
		
		return null;
		
	}
	
	@Override public Void visitFormalParameter(ShadowParser.FormalParameterContext ctx) { 
		visitChildren(ctx);
		
		Modifiers modifiers = ctx.modifiers().getModifiers();
		ctx.type().addModifiers(modifiers);
		ctx.addModifiers(modifiers);
		addErrors(ctx.modifiers(), modifiers.checkParameterAndReturnModifiers());
		
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
		addErrors(ctx.modifiers(), modifiers.checkParameterAndReturnModifiers());
		
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
		addErrors(ctx.modifiers(), modifiers.checkLocalVariableModifiers());
		
		return null;
	}
	
	@Override public Void visitLiteral(ShadowParser.LiteralContext ctx) { 
		if( ctx.NullLiteral() != null )
			ctx.addModifiers(Modifiers.NULLABLE | Modifiers.CONSTANT);
		else
			ctx.addModifiers(Modifiers.IMMUTABLE | Modifiers.CONSTANT);
		return null;
	}
	
	@Override public Void visitLocalDeclaration(ShadowParser.LocalDeclarationContext ctx) {
		visitChildren(ctx);
		
		Modifiers modifiers = ctx.modifiers().getModifiers();
		if( ctx.localMethodDeclaration() != null ) {
			ctx.localMethodDeclaration().addModifiers(modifiers);
			addErrors(ctx.modifiers(), modifiers.checkLocalMethodModifiers());
		}
		else if( ctx.localVariableDeclaration() != null ) {
			ctx.localVariableDeclaration().addModifiers(modifiers);
			addErrors(ctx.modifiers(), modifiers.checkLocalVariableModifiers());
			if( ctx.localVariableDeclaration().type() != null )
				ctx.localVariableDeclaration().type().addModifiers(modifiers);
		}
		
		return null;
	}
	
	@Override public Void visitForeachInit(ShadowParser.ForeachInitContext ctx) {
		visitChildren(ctx);
		
		Modifiers modifiers = ctx.modifiers().getModifiers();
		ctx.addModifiers(modifiers);
		if( ctx.type() != null )
			ctx.type().addModifiers(modifiers);
		addErrors(ctx.modifiers(), modifiers.checkLocalVariableModifiers());
		
		return null;
	}
	
	@Override public Void visitForInit(ShadowParser.ForInitContext ctx) {
		visitChildren(ctx);
		
		if( ctx.localVariableDeclaration() != null ) {
			Modifiers modifiers = ctx.modifiers().getModifiers();
			ctx.localVariableDeclaration().addModifiers(modifiers);
			addErrors(ctx.modifiers(), modifiers.checkLocalVariableModifiers());
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