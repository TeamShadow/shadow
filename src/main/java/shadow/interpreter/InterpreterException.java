package shadow.interpreter;

import shadow.ShadowException;
import shadow.ShadowExceptionErrorKind;
import shadow.parse.Context;

public class InterpreterException extends ShadowException {

	private static final long serialVersionUID = 6292713104787662488L;

	public InterpreterException(String message) {
		super(message);
	}

	public InterpreterException(Error kind, String message, Context context) {
		super(kind, message, context);
	}

	public enum Error implements ShadowExceptionErrorKind {
		CIRCULAR_REFERENCE("Circular reference", "Encountered a circular reference while evaluating this field"),
		ILLEGAL_ACCESS("Illegal access", "Class, member, method, or property not accessible from this context"),
		INVALID_ARGUMENTS("Invalid arguments", "Supplied method arguments do not match parameters"),
		INVALID_CREATE("Invalid create", "Target cannot be created"),
		INVALID_SELF_REFERENCE("Invalid self reference", "Self reference is invalid"),
		INVALID_TYPE_ARGUMENTS("Invalid type arguments", "Supplied type arguments do not match type parameters"),
		MISSING_TYPE_ARGUMENTS("Missing type arguments", "Type arguments not supplied for parameterized type"),
		UNDEFINED_SYMBOL("Undefined symbol", "Symbol has not been defined in this context"),
		UNKNOWN_REFERENCE("Unknown reference", "This symbol could not be resolved"),
		UNSUPPORTED_OPERATION("Unsupported operation", "This operation is not supported in compile-time constant expressions");

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