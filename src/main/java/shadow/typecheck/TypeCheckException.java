/*
 * Copyright 2015 Team Shadow
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 	
 * 	    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package shadow.typecheck;

/**
 * Exception to capture various standard errors than can occur during type-checking.
 * 
 * @author Barry Wittman 
 */
public class TypeCheckException extends Exception {
	private static final long serialVersionUID = -2238483523241380406L;

	/**
	 *  Constants for each kind of supported error, with default error messages.
	 *  Listing all supported errors increases consistency.
	 */
	public static enum Error {		
		ILLEGAL_ACCESS("Illegal access", "Class, member, method, or property not accessible from this context"),
		INVALID_ARGUMENTS("Invalid arguments", "Supplied method arguments do not match parameters"),
		INVALID_ASSIGNMENT("Invalid assignment", "Right hand side cannot be assigned to left hand side"),
		INVALID_CAST("Invalid cast", "Result type cannot be cast to specified type"),
		INVALID_CREATE("Invalid create", "Target cannot be created"),
		INVALID_DEFAULT("Invalid default", "Can only apply default value to array creation"),
		INVALID_DEPENDENCY("Invalid dependency", "Type is dependent on an invalid or unavailable type"),
		INVALID_DEREFERENCE("Invalid dereference", "Nullable reference cannot be dereferenced"),
		INVALID_DESTROY("Invalid destroy", "Target cannot be destroyed"),
		INVALID_PARENT("Invalid parent", "Type cannot extend given type as a parent class"),
		INVALID_FILE("Invalid file", "Outermost type must be declared in a file with a matching name"),
		INVALID_HIERARCHY("Invalid hierarchy", "Type contains a circular extends or implements definition"),
		INVALID_INTERFACE("Invalid interface", "Type cannot implement given type as an interface"),
		INVALID_IMPORT("Invalid import", "Type or package cannot be imported"),
		INVALID_INSTANCE("Invalid instance", "Target cannot be instanced"),
		INVALID_LABEL("Invalid label", "Switch label is invalid"),
		INVALID_LITERAL("Invalid literal", "Literal is invalid"),
		INVALID_METHOD("Invalid method", "Method with specified signature is not defined in this context"),
		INVALID_MODIFIER("Invalid modifier", "Modifier cannot be applied to this declaration"),
		INVALID_OVERRIDE("Invalid override", "Method cannot override parent method"),
		INVALID_PACKAGE("Invalid package", "Package cannot be defined"),
		INVALID_PARAMETERS("Invalid parameters", "Method cannot be declared with the given parameters"),
		INVALID_PROPERTY("Invalid property", "No matching property can be found"),
		INVALID_RETURNS("Invalid returns", "Supplied return values do not match method signature"),
		INVALID_SELF_REFERENCE("Invalid self reference", "Self reference is invalid"),
		INVALID_SINGLETON_CREATE("Invalid singleton create", "Singleton type can only specify a default create"),
		INVALID_STRUCTURE("Invalid structure", "Language construct cannot be used in this way"),
		INVALID_SUBSCRIPT("Invalid subscript", "Subscript is of wrong type or number"),
		INVALID_TRY("Invalid try", "Try statement incorrectly constructed"),
		INVALID_TYPE("Invalid type", "Supplied type cannot be used with this language construct"),
		INVALID_TYPE_ARGUMENTS("Invalid type arguments", "Supplied type arguments do not match type parameters"),
		INVALID_TYPE_PARAMETERS("Invalid type parameters", "Type cannot be parameterized"),
		MISMATCHED_TYPE("Mismatched type", "Supplied type does not match another type supplied to this language construct"),
		MISMATCHED_PACKAGE("Mismatched package", "Package for type does not match type directory"),
		MISSING_CREATE("Missing create invocation", "Explicit create invocation is missing, and parent class does not implement the default create"),
		MISSING_INTERFACE("Missing interface", "Type does not implement a required interface"),
		MISSING_TYPE_ARGUMENTS("Missing type arguments", "Type arguments not supplied for parameterized type"),
		MULTIPLY_DEFINED_SYMBOL("Multiply defined symbol", "Symbol cannot be redefined in this context"),
		NO_ACTION("No action", "Statement does not perform an action"),
		NOT_OBJECT("Not object", "Object reference must be used"),
		NOT_TYPE("Not type", "Type name must be used"),
		UNDEFINED_PACKAGE("Undefined package", "Package not defined in this context"),
		UNDEFINED_SYMBOL("Undefined symbol", "Symbol has not been defined in this context"),
		UNDEFINED_TYPE("Undefined type", "Type not defined in this context"),
		UNNECESSARY_TYPE_ARGUMENTS("Unnecessary type arguments", "Type arguments supplied for non-parameterized type"),
		UNREACHABLE_CODE("Unreachable code", "Code cannot be reached");
		
		private final String name;
		private final String message;		
		
		Error( String name, String message ) {
			this.name = name;
			this.message = message;
		}
		
		public String getName() {
			return name;
		}
		
		public String getMessage() {
			return message;			
		}
	}
	
	private Error error;	
	
	
	/**
	 * Creates a <code>TypeCheckException</code> with a specified kind of error and a message.
	 * @param error		kind of error
	 * @param message	explanatory error message
	 */
	public TypeCheckException( Error error, String message ) {
		super( message );
		this.error = error;				
	}
	
	/**
	 * Gets kind of error.
	 * @return			kind of error
	 */
	public Error getError() {	
		return error;
	}
}
