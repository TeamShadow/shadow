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
		UNSUPPORTED_OPERATION("Unsupported operation", "This operation is not supported in compile-time constant expressions"),
		CIRCULAR_REFERENCE("Circular reference", "Encountered a circular reference while evaluating this field"),
		UNKNOWN_REFERENCE("Unknown reference", "This symbol could not be resolved");

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