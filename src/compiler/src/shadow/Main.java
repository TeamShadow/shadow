/**
 * 
 */
package shadow;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import shadow.parser.javacc.ASTBase;
import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowParser;

/**
 * @author wspeirs
 *
 */
public class Main {

	/**
	 * This is the starting point of the compiler
	 * @param args Command line arguments to control the compiler
	 */
	public static void main(String[] args) {
		
		try {
			FileInputStream sourceStream = new FileInputStream(args[0]);
			ShadowParser parser = new ShadowParser(sourceStream);
			
			// parse the translation unit
			ASTBase n = parser.CompilationUnit();
			
			n.dump("");
			System.out.println("GOOD PARSE");
			
		} catch(FileNotFoundException fnfe) {
			System.err.println("Source file (" + args[0] + ") not found: " + fnfe.getLocalizedMessage());
		} catch(ParseException pe) {
			System.err.println("Error parsing " + args[0]);
			System.err.println(pe.getLocalizedMessage());
		}
	}

}
