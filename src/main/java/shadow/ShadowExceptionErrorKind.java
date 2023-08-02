package shadow;

import shadow.parse.Context;

/**
 * Used for error variants that help make a top-level exception type more specific.
 *
 * <p>E.g. a TypeCheckException has an enum of variants such as INVALID_ACCESS or MISMATCHED_TYPE.
 */
public interface ShadowExceptionErrorKind {

  ShadowException getException(String message, Context context);

  default ShadowException getException(String message) {
    return getException(message, null);
  }

  default ShadowException getException(Context context) {
    return getException(getDefaultMessage(), context);
  }

  default ShadowException getException() {
    return getException(getDefaultMessage(), null);
  }

  /** Returns a detailed description of this error variant */
  String getDefaultMessage();

  /** Returns the name of this error variant * */
  String getName();
}
