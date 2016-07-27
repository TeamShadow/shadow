/*
 * Copyright 2016 Team Shadow
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

package shadow.parse;

import shadow.ShadowException;
import shadow.ShadowExceptionFactory;

/**
 * Exception to capture various standard errors than can occur during type-checking.
 * 
 * @author Barry Wittman 
 */
public class ParseException extends ShadowException {
	/**
	 *  Constants for each kind of supported error, with default error messages.
	 *  Listing all supported errors increases consistency.
	 */
	public static enum Error implements ShadowExceptionFactory  {		
		EMPTY_STATMENT("Empty statement", "An empty statement requires the skip keyword"),
		INCOMPLETE_TRY("Incomplete try", "Given try statement is not followed by catch, recover, or finally statements"),
		ILLEGAL_MODIFIER("Illegal modifiers", "Cannot apply modifier in given context"),
		REPEATED_MODIFIERS("Repeated modifier", "Modifier cannot be repeated"),
		SYNTAX_ERROR("Syntax error", "Parsing failed because of syntax error");
					
		private final String name;
		private final String message;		
		
		Error( String name, String message ) {
			this.name = name;
			this.message = message;
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		public String getMessage() {
			return message;			
		}

		@Override
		public ShadowException generateException(String message, Context context) {
			return new ParseException(this, message, context);
		}
	}
	
	private final Error error;
	
	/**
	 * Creates a <code>ParseException</code> with a specified kind of error and a message.
	 * @param kind			kind of error
	 * @param message		explanatory error message
	 */
	public ParseException( Error kind, String message ) {
		super( message );
		error = kind;
	}
	
	/**
	 * Creates a <code>ParseException</code> with a specified kind of error and a message
	 * at a particular location in a file.
	 * @param kind			kind of error
	 * @param message		explanatory error message
	 * @param context		context where error occurs
	 */
	public ParseException( Error kind, String message, Context context ) {
		super( makeMessage( kind, message, context ), context );
		error = kind;			
	}		
	
	/**
	 * Gets kind of error.
	 * @return			kind of error
	 */
	public Error getError() {	
		return error;
	}
}

