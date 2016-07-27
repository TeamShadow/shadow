package shadow.parse;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.IntStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.TokenSource;

import shadow.parse.ParseException.Error;
import shadow.typecheck.ErrorReporter;

public class ParseErrorListener extends BaseErrorListener {
	
	private ErrorReporter reporter;
	
	public ParseErrorListener(ErrorReporter reporter)
	{
		this.reporter = reporter;
	}

	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
			int line, int charPositionInLine,
			String msg, RecognitionException e)
	{
		IntStream stream = recognizer.getInputStream();
		String sourceName;		
		
		if( stream instanceof BufferedTokenStream ) {
			BufferedTokenStream buffered = (BufferedTokenStream)stream;
			CharStream input = buffered.getTokenSource().getInputStream();
			if( input instanceof PathStream )			
				sourceName = ((PathStream)input).getPath().getFileName().toString();
			else
				sourceName = recognizer.getInputStream().getSourceName();
		}
		else		
			sourceName = recognizer.getInputStream().getSourceName();
		
		String error;
		
		if( !sourceName.isEmpty() )
			error = String.format("(%s)[%d:%d] %s: %s", sourceName, line, charPositionInLine, Error.SYNTAX_ERROR.getName(), msg);
		else
			error = String.format("[%d:%d] %s: %s", line, charPositionInLine, Error.SYNTAX_ERROR.getName(), msg);

		reporter.addError(new ParseException(Error.SYNTAX_ERROR, error));
	}

}
