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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Exception to capture various standard errors than can occur during type-checking.
 * 
 * @author Barry Wittman 
 */
public class TypeCheckException extends Exception implements Comparable<TypeCheckException> {
	
	private static final long serialVersionUID = 5033658866742389524L;	
	private static final String EOL = System.getProperty("line.separator", "\n");

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
		NOT_ALL_PATHS_RETURN("Not all paths return", "Every path in a value-returning method must return"),
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
	
	private final Error error;
	private final File file;
	private final int lineStart;
	private final int lineEnd;
	private final int columnStart;
	private final int columnEnd;
	
	/**
	 * Creates a <code>TypeCheckException</code> with a specified kind of error and a message.
	 * @param kind			kind of error
	 * @param message		explanatory error message
	 */
	public TypeCheckException( Error kind, String message ) {
		super( message);
		error = kind;
		this.file = null;
		this.lineStart = -1;
		this.lineEnd = -1;
		this.columnStart = -1;
		this.columnEnd = -1;
	}
	
	/**
	 * Creates a <code>TypeCheckException</code> with a specified kind of error and a message
	 * at a particular location in a file.
	 * @param kind			kind of error
	 * @param message		explanatory error message
	 * @param file			which file
	 * @param lineStart		line error starts on
	 * @param lineEnd		line error ends on
	 * @param columnStart	column error starts on
	 * @param columnEnd		column error ends on
	 */
	public TypeCheckException( Error kind, String message, File file, int lineStart,
			int lineEnd, int columnStart, int columnEnd ) {
		super( makeMessage( kind, message, file, lineStart, lineEnd, columnStart, columnEnd ) );
		error = kind;
		this.file = file;
		this.lineStart = lineStart;
		this.lineEnd = lineEnd;
		this.columnStart = columnStart;
		this.columnEnd = columnEnd;
	}
	
	
	/**
	 * Gets kind of error.
	 * @return			kind of error
	 */
	public Error getError() {	
		return error;
	}
	
	/**
	 * Gets file where error happened.
	 * @return			file
	 */
	public File getFile() {
		return file;
	}
	
	/**
	 * Gets starting line where error happened.
	 * @return			line
	 */
	public int lineStart() {
		return lineStart;
	}
	
	/**
	 * Gets ending line where error happened.
	 * @return			line
	 */
	public int lineEnd() {
		return lineEnd;
	}
	
	/**
	 * Gets starting column where error happened.
	 * @return			column
	 */
	public int columnStart() {
		return columnStart;
	}
	
	/**
	 * Gets ending column where error happened.
	 * @return			column
	 */
	public int columnEnd() {
		return columnEnd;
	}

	@Override
	public int compareTo(TypeCheckException other) {		
		int difference = error.ordinal() - other.error.ordinal();
		if( difference == 0 )
			difference = getMessage().compareTo(other.getMessage());
		return difference;
	}
	
	public boolean isInside(TypeCheckException other)
	{
		if( file != null && other.file != null )
			return file.equals(other.file) &&
					lineStart >= other.lineStart &&
					lineEnd <= other.lineEnd &&
					(lineStart > other.lineStart || columnStart >= other.columnStart ) &&
					(lineEnd < other.lineEnd || columnEnd <= other.columnEnd );
		return false;			
	}
	
	/**
	 * Creates a formatted message for an error or warning, including file name and
	 * line and column numbers and possibly text from the file itself where the error is.
	 * File, line, and column values may be special defaults which will cause
	 * them to be ignored in the return message.
	 * @param kind				kind or error or warning		
	 * @param message			message explaining the error or warning
	 * @param file				file where problem is occurring
	 * @param lineStart			starting line of problem
	 * @param lineEnd			ending line of problem
	 * @param columnStart		starting column of problem
	 * @param columnEnd			ending column of problem
	 * @return					formatted message
	 */
	public static String makeMessage( Error kind, String message, File file, int lineStart,
			int lineEnd, int columnStart, int columnEnd ) {
		StringBuilder error = new StringBuilder();
		
		if( file != null )
			error.append("(" + file.getName() + ")");
		
		if( lineStart != -1 && columnStart != -1 )
			error.append("[" + lineStart + ":" + columnStart + "] ");
		else
			error.append(" ");
		
		if( kind != null )
			error.append(kind.getName() + ": ");		
		
		error.append(message);
		
		/* If file is available, find problematic text and include it in the message. */	
		if( file != null && lineStart >= 0 && lineEnd >= lineStart &&
				columnStart >= 0 && columnEnd >= 0 ) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(file));
				String line = "";			
				for( int i = 1; i <= lineStart; ++i )
					line = reader.readLine();
				error.append(EOL);
				error.append(line);
				if( lineStart == lineEnd ) {
					error.append(EOL);
					for( int i = 1; i <= columnEnd; ++i )
						if( i >= columnStart )
							error.append('^');
						else
							error.append(' ');
				}
			} 
			// Do nothing, can't add additional file data
			catch (FileNotFoundException e) {}
			catch (IOException e) {}
			finally {
			  if( reader != null )
				try { reader.close(); }
			  	catch (IOException e) {}
		  }
		}
		
		return error.toString();
	}
}
