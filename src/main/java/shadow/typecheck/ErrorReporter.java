package shadow.typecheck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import shadow.parser.javacc.Node;
import shadow.typecheck.TypeCheckException.Error;
import shadow.typecheck.type.ArrayType;
import shadow.typecheck.type.ModifiedType;
import shadow.typecheck.type.SequenceType;
import shadow.typecheck.type.Type;

public abstract class ErrorReporter {
	protected final ArrayList<TypeCheckException> errorList = new ArrayList<TypeCheckException>();
	protected final ArrayList<TypeCheckException> warningList = new ArrayList<TypeCheckException>();	
	
	private final Logger LOGGER;	
	protected static final String EOL = System.getProperty("line.separator", "\n");
	
	public ErrorReporter(Logger logger) {
		LOGGER = logger;
	}	
	
	public void clear() {
		errorList.clear();
		warningList.clear();
	}
	
	/**
	 * Adds a temporary list of errors associated with a particular 
	 * node to the main list of errors.
	 * @param node				node related to errors
	 * @param errors			list of errors
	 */	
	protected final void addErrors(Node node, List<TypeCheckException> errors ) {		
		if( errors != null )
			for( TypeCheckException error : errors )
				addError( node, error.getError(), error.getMessage() );
	}
	
	/**
	 * Adds an error associated with a node to the main list of errors.
	 * @param node				node related to error	
	 * @param error				kind of error
	 * @param message			message explaining error
	 * @param errorTypes		types associated with error
	 */
	protected void addError(Node node, Error error, String message, Type... errorTypes) {
		if( containsUnknown(errorTypes) )
			return; // Don't add error if it has an unknown type in it.
		
		if( node != null ) {			
			message = makeMessage(error, message, node.getFile(), node.getLineStart(),
					node.getLineEnd(), node.getColumnStart(), node.getColumnEnd() );
			errorList.add(new TypeCheckException(error, message));
		}
	}
	

	
	/**
	 * Adds a warning associated with a node to the main list of warnings.
	 * @param node				node related to warning	
	 * @param warning			kind of warning
	 * @param message			message explaining warning
	 */
	protected void addWarning(Node node, Error warning, String message) {
		if( node != null ) {		
			message = makeMessage(warning, message, node.getFile(), node.getLineStart(),
					node.getLineEnd(), node.getColumnStart(), node.getColumnEnd() );
			warningList.add(new TypeCheckException(warning, message));
		}
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
	
	/**
	 * Prints the list of errors to the appropriate logger.
	 */
	public final void printErrors() {
		for(TypeCheckException exception : errorList)
			LOGGER.error(exception.getMessage());
	}
	
	/**
	 * Prints the list of warnings to the appropriate logger.
	 */
	public final void printWarnings() {
		for(TypeCheckException exception : warningList)
			LOGGER.warn(exception.getMessage());
	}
	
	
	/**
	 * Checks to see if the array contains an unknown type. 
	 * @param types	array of types to be checked
	 * @return 		if the array contains <code>Type.UNKNOWN</code>
	 * @see			Type.UNKNOWN
	 */
	protected static boolean containsUnknown( Type[] types ) {
		for( Type type : types )
			if( containsUnknown(type) )
				return true;
		
		return false;
	}
	
	/**
	 * Checks to see if the type is unknown or contains an unknown type.
	 * Unknown types are generated by the type-checker to avoid 
	 * null pointer exceptions, but errors involving unknown types are
	 * suppressed to avoid a cascade of errors from the same source.
	 * @param type	type to be checked
	 * @return 		if the type contains <code>Type.UNKNOWN</code>
	 * @see			Type.UNKNOWN
	 */
	private static boolean containsUnknown( Type type ) {
		if( type == null )
			return false;
		if( type == Type.UNKNOWN )
			return true;
		if( type instanceof SequenceType ) {
			SequenceType sequenceType = (SequenceType) type;
			for(ModifiedType modifiedType : sequenceType)
				if( containsUnknown( modifiedType.getType() ) )
					return true;
		}
		else if( type instanceof ArrayType )
			return containsUnknown( ((ArrayType)type).getBaseType() );
		
		return false;
	}
	
	/**
	 * Adds an error to the given error list, unless that error refers to 
	 * unknown types. Unknown type errors are usually symptoms of other errors
	 * (like undeclared variables), and are thus unnecessary to report.
	 * @param errors		list of errors
	 * @param type			kind of error
	 * @param reason		message explaining error
	 * @param errorTypes	types of errors involved, used for suppressing redundant errors
	 */
	public static void addError( List<TypeCheckException> errors, Error type,
			String reason, Type... errorTypes ) {
		// Don't add an error if it has an Unknown Type in it.
		if( containsUnknown( errorTypes ) )
			return; 		
		if( errors != null )
			errors.add( new TypeCheckException( type, reason ) );		
	}	

}
