package shadow.parse;

import shadow.ShadowException;
import shadow.ShadowExceptionErrorKind;
import shadow.typecheck.ErrorReporter;
import shadow.typecheck.type.Type;

import java.util.List;

public class ShadowVisitorErrorReporter extends ShadowBaseVisitor<Void> {
  private final ErrorReporter reporter;

  public ShadowVisitorErrorReporter(ErrorReporter reporter) {
    this.reporter = reporter;
  }

  public ErrorReporter getErrorReporter() {
    return reporter;
  }

  public void printAndReportErrors() throws ShadowException {
    reporter.printAndReportErrors();
  }

  /**
   * Adds a temporary list of errors associated with a particular context to the main list of
   * errors.
   *
   * @param ctx context related to errors
   * @param errors list of errors
   */
  public final void addErrors(Context ctx, List<ShadowException> errors) {
    reporter.addErrors(ctx, errors);
  }

  /**
   * Adds an error associated with a context to the main list of errors.
   *
   * @param ctx context related to error
   * @param error kind of error
   */
  public void addError(Context ctx, ShadowExceptionErrorKind error) {
    reporter.addError(error.getException(ctx));
  }

  /**
   * Adds an error associated with a context to the main list of errors.
   *
   * @param ctx context related to error
   * @param error kind of error
   * @param message message explaining error
   * @param errorTypes types associated with error
   */
  public void addError(
      Context ctx, ShadowExceptionErrorKind error, String message, Type... errorTypes) {
    reporter.addError(ctx, error, message, errorTypes);
  }

  /**
   * Adds an error to the main list of errors.
   *
   * @param exception exception related to the error
   */
  public void addError(ShadowException exception) {
    reporter.addError(exception);
  }

  /**
   * Adds a warning associated with a context to the main list of warnings.
   *
   * @param ctx context related to warning
   * @param warning kind of warning
   * @param message message explaining warning
   */
  public void addWarning(Context ctx, ShadowExceptionErrorKind warning, String message) {
    reporter.addWarning(ctx, warning, message);
  }

  /**
   * Adds a warning to the main list of warning.
   *
   * @param exception exception related to the warning
   */
  public void addWarning(ShadowException exception) {
    reporter.addWarning(exception);
  }
}
