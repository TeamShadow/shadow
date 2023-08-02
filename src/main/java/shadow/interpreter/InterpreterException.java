package shadow.interpreter;

import shadow.ShadowException;
import shadow.ShadowExceptionErrorKind;
import shadow.parse.Context;

public class InterpreterException extends ShadowException {

  public InterpreterException(Error kind, String message) {
    super(kind, message);
  }

  public InterpreterException(Error kind, String message, Context context) {
    super(kind, message, context);
  }

  public enum Error implements ShadowExceptionErrorKind {
    CIRCULAR_REFERENCE(
        "Circular reference", "Encountered a circular reference while evaluating this field"),
    ILLEGAL_ACCESS(
        "Illegal access", "Class, member, method, or property not accessible from this context"),
    INVALID_ARGUMENTS("Invalid arguments", "Supplied method arguments do not match parameters"),
    INVALID_ASSIGNMENT(
        "Invalid assignment", "Right hand side cannot be assigned to left hand side"),
    INVALID_CAST("Invalid cast", "Result type cannot be cast to specified type"),
    INVALID_CREATE("Invalid create", "Target cannot be created"),

    INVALID_DEREFERENCE("Invalid dereference", "Nullable reference cannot be dereferenced"),
    INVALID_FIELD("Invalid field", "Field not available in object"),
    INVALID_METHOD("Invalid method", "Method cannot be called"),
    INVALID_PROPERTY("Invalid property", "No matching property can be found"),
    INVALID_REFERENCE("Invalid reference", "Property, subscript, variable, or field is invalid"),
    INVALID_SELF_REFERENCE("Invalid self reference", "Self reference is invalid"),
    INVALID_STRUCTURE("Invalid structure", "Language construct cannot be used in this way"),
    INVALID_SUBSCRIPT("Invalid subscript", "Subscript is of wrong type or number"),
    INVALID_TYPE("Invalid type", "Supplied type cannot be used with this language construct"),
    INVALID_TYPE_ARGUMENTS(
        "Invalid type arguments", "Supplied type arguments do not match type parameters"),
    MISMATCHED_TYPE(
        "Mismatched type",
        "Supplied type does not match another type supplied to this language construct"),
    MISSING_TYPE_ARGUMENTS(
        "Missing type arguments", "Type arguments not supplied for parameterized type"),
    NOT_OBJECT("Not object", "Object reference must be used"),
    NOT_TYPE("Not type", "Type name must be used"),
    UNAVAILABLE_SOURCE("Unavailable source", "Source code is not available for interpretation"),
    UNDEFINED_SYMBOL("Undefined symbol", "Symbol has not been defined in this context"),
    UNINITIALIZED_CONSTANT("Uninitialized constant", "Constant was not initialized before use"),
    UNKNOWN_REFERENCE("Unknown reference", "Symbol could not be resolved"),

    UNNECESSARY_TYPE_ARGUMENTS(
        "Unnecessary type arguments", "Type arguments supplied for non-parameterized type"),
    UNSUPPORTED_OPERATION(
        "Unsupported operation",
        "This operation is not supported in compile-time constant expressions"),
    NON_CONSTANT_REFERENCE(
        "Reference to non-constant value",
        "Only other constants can be referenced when defining a constant");

    private final String name;
    private final String message;

    Error(String name, String message) {
      this.name = name;
      this.message = message;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public String getDefaultMessage() {
      return message;
    }

    @Override
    public InterpreterException getException(String message, Context context) {
      return new InterpreterException(this, message, context);
    }
  }
}
