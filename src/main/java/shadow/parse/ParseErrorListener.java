package shadow.parse;

import org.antlr.v4.runtime.*;
import shadow.ShadowException;
import shadow.parse.ParseException.Error;
import shadow.typecheck.ErrorReporter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ParseErrorListener extends BaseErrorListener {

  private final ErrorReporter reporter;

  public ParseErrorListener(ErrorReporter reporter) {
    this.reporter = reporter;
  }

  @Override
  public void syntaxError(
      Recognizer<?, ?> recognizer,
      Object offendingSymbol,
      int line,
      int charPositionInLine,
      String msg,
      RecognitionException e) {
    IntStream stream = recognizer.getInputStream();
    Path path = Paths.get(stream.getSourceName());
    if (!Files.exists(path)) path = null;

    if (msg != null && !msg.isEmpty())
      msg = Character.toUpperCase(msg.charAt(0)) + msg.substring(1);

    String error;

    if (path != null)
      error =
          String.format(
              "(%s)[%d:%d] %s: %s",
              path.getFileName().toString(),
              line,
              charPositionInLine,
              Error.SYNTAX_ERROR.getName(),
              msg
                  + ShadowException.showCode(
                      path, line, line, charPositionInLine, charPositionInLine));
    else
      error =
          String.format(
              "[%d:%d] %s: %s", line, charPositionInLine, Error.SYNTAX_ERROR.getName(), msg);

    if (e != null) {
      Token t = e.getOffendingToken();
      reporter.addError(
          new ParseException(
              Error.SYNTAX_ERROR,
              error,
              line,
              charPositionInLine,
              t.getStartIndex(),
              t.getStopIndex()));
    } else
      reporter.addError(
          new ParseException(Error.SYNTAX_ERROR, error, line, charPositionInLine, -1, -1));
  }
}
