package shadow;

import shadow.parse.Context;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

@SuppressWarnings("unused")
public abstract class ShadowException extends Exception {
  private static final String EOL = System.getProperty("line.separator", "\n");

  private Context context;
  private final ShadowExceptionErrorKind kind;

  public ShadowException(String message) {
    this(message, null);
  }

  public ShadowException(String message, Context context) {
    super(message);
    this.context = context;
    this.kind = null;
  }

  public ShadowException(ShadowExceptionErrorKind kind, Context context) {
    this(kind, kind.getDefaultMessage(), context);
  }

  public ShadowException(ShadowExceptionErrorKind kind, String message) {
    this(kind, message, null);
  }

  public ShadowException(ShadowExceptionErrorKind kind, String message, Context context) {
    super(message);
    this.kind = kind;
    this.context = context;
  }

  public ShadowException setContext(Context context) {
    this.context = context;
    return this;
  }

  public String getMessageText() {
    return super.getMessage();
  }

  @Override
  public String getMessage() {
    StringBuilder message = new StringBuilder();

    if (context != null) {
      if (context.getSourcePath() != null)
        message.append("(").append(context.getSourcePath().getFileName().toString()).append(")");

      if (context.lineStart() != -1 && context.columnStart() != -1)
        message
            .append("[")
            .append(context.lineStart())
            .append(":")
            .append(context.columnStart())
            .append("] ");
      else message.append(" ");

      if (kind != null) message.append(kind.getName()).append(": ");
      message.append(super.getMessage());

      message.append(
          showCode(
              context.getSourcePath(),
              context.lineStart(),
              context.lineEnd(),
              context.columnStart(),
              context.columnEnd()));
    } else {
      if (kind != null) message.append(kind.getName()).append(": ");
      message.append(super.getMessage());
    }

    return message.toString();
  }

  @Override
  public String getLocalizedMessage() {
    StringBuilder message = new StringBuilder();

    if (context != null) {
      if (context.getSourcePath() != null)
        message.append("(").append(context.getSourcePath().getFileName().toString()).append(")");

      if (context.lineStart() != -1 && context.columnStart() != -1)
        message
            .append("[")
            .append(context.lineStart())
            .append(":")
            .append(context.columnStart())
            .append("] ");
      else message.append(" ");

      if (kind != null) message.append(kind.getName()).append(": ");
      message.append(super.getLocalizedMessage());

      message.append(
          showCode(
              context.getSourcePath(),
              context.lineStart(),
              context.lineEnd(),
              context.columnStart(),
              context.columnEnd()));
    } else {
      if (kind != null) message.append(kind.getName()).append(": ");
      message.append(super.getLocalizedMessage());
    }

    return message.toString();
  }

  /**
   * Gets starting line where error happened.
   *
   * @return line
   */
  public int lineStart() {
    if (context != null) return context.lineStart();
    return -1;
  }

  /**
   * Gets ending line where error happened.
   *
   * @return line
   */
  public int lineEnd() {
    if (context != null) return context.lineEnd();
    return -1;
  }

  /**
   * Gets starting column where error happened.
   *
   * @return column
   */
  public int columnStart() {
    if (context != null) return context.columnStart();
    return -1;
  }

  /**
   * Gets ending column where error happened.
   *
   * @return column
   */
  public int columnEnd() {
    if (context != null) return context.columnEnd();
    return -1;
  }

  public int startCharacter() {
    if (context != null) return context.start.getStartIndex();
    return -1;
  }

  public int stopCharacter() {
    if (context != null) return context.stop.getStopIndex();
    return -1;
  }

  public boolean isInside(ShadowException other) {
    if (context != null && other.context != null) {
      Path path1 = context.getSourcePath();
      Path path2 = other.context.getSourcePath();
      if (path1 != null && path2 != null)
        return path1.equals(path2)
            && lineStart() >= other.lineStart()
            && lineEnd() <= other.lineEnd()
            && (lineStart() > other.lineStart() || columnStart() >= other.columnStart())
            && (lineEnd() < other.lineEnd() || columnEnd() <= other.columnEnd());
    }
    return false;
  }

  public static String showCode(
      Path path, int lineStart, int lineEnd, int columnStart, int columnEnd) {
    StringBuilder error = new StringBuilder();

    /* If file is available, find problematic text and include it in the message. */
    if (path != null
        && lineStart >= 0
        && lineEnd == lineStart
        && columnStart >= 0
        && columnEnd >= 0) {
      BufferedReader reader = null;
      try {
        reader = new BufferedReader(new FileReader(path.toFile()));
        String line = "";
        for (int i = 1; i <= lineStart; ++i) line = reader.readLine();
        error.append(EOL);
        if (line == null) line = ""; // needed sometimes when the error comes *after* the last line
        line = line.replace('\t', ' ');
        error.append(line);
        error.append(EOL);

        for (int i = 0; i <= columnEnd; ++i)
          if (i >= columnStart) error.append('^');
          else error.append(' ');

      }
      // Do nothing, can't add additional file data
      catch (IOException ignored) {
      } finally {
        if (reader != null)
          try {
            reader.close();
          } catch (IOException ignored) {
          }
      }
    }

    return error.toString();
  }

  public ShadowExceptionErrorKind getError() {
    return kind;
  }
}
