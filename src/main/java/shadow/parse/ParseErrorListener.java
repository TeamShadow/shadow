package shadow.parse;

import java.nio.file.Path;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.IntStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;

import shadow.ShadowException;
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
		Path path = null;
		String sourceName = null;
		
		if( stream instanceof BufferedTokenStream ) {
			BufferedTokenStream buffered = (BufferedTokenStream)stream;
			CharStream input = buffered.getTokenSource().getInputStream();
			if( input instanceof PathStream )			
				path = ((PathStream)input).getPath();
			else
				sourceName = recognizer.getInputStream().getSourceName();
		}
		else		
			sourceName = recognizer.getInputStream().getSourceName();
		
		if( msg != null && !msg.isEmpty() )
			msg = Character.toUpperCase(msg.charAt(0)) + msg.substring(1);
		
		String error;
		
		if( path != null )
			error = String.format("(%s)[%d:%d] %s: %s", path.getFileName().toString(), line, charPositionInLine, Error.SYNTAX_ERROR.getName(), msg + ShadowException.showCode(path, line, line, charPositionInLine, charPositionInLine));
		else if( sourceName != null && !sourceName.isEmpty() )
			error = String.format("(%s)[%d:%d] %s: %s", sourceName, line, charPositionInLine, Error.SYNTAX_ERROR.getName(), msg);
		else
			error = String.format("[%d:%d] %s: %s", line, charPositionInLine, Error.SYNTAX_ERROR.getName(), msg);

		if( e != null ) {
			Token t = e.getOffendingToken();		
			reporter.addError(new ParseException(Error.SYNTAX_ERROR, error, line, charPositionInLine, t.getStartIndex(), t.getStopIndex()));
		}
		else
			reporter.addError(new ParseException(Error.SYNTAX_ERROR, error, line, charPositionInLine, -1, -1));
	}

}
