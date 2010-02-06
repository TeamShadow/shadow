/**
 * 
 */
package shadow.parser.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import shadow.parser.javacc.ASTCompilationUnit;
import shadow.parser.javacc.ParseException;
import shadow.parser.javacc.ShadowException;
import shadow.parser.javacc.ShadowParser;
import shadow.test.BaseTest;

/**
 * @author wspeirs
 *
 */
public final class ParserTest extends BaseTest {

	boolean dump = true;
	boolean debug = true;
	
	/**
	 * Tests all of the parse test files.
	 * @param args
	 */
	public static void main(String[] args) throws IOException, ShadowException {
		ParserTest pt = new ParserTest(false, false);
		
		// no args, we test everything
		if(args.length == 0)
			pt.testAll();
		else {
			for(String arg:args) {
				pt.testAll(new File(arg));
			}
		}	
	}
	
	public ParserTest(boolean dump, boolean debug) {
		super("./src/shadow/parser/test");
		this.debug = debug;
		this.dump = dump;
	}
	
	protected void runTest(File sourceFile) throws ShadowException {
		try {
			System.out.println("CHECKING: " + sourceFile.getPath());
			FileInputStream fis = new FileInputStream(sourceFile);
			ShadowParser parser = new ShadowParser(fis);
		  
			if(debug)
				parser.enableDebug();
			
			long startTime = System.currentTimeMillis();
			
			ASTCompilationUnit n = parser.CompilationUnit();
			
			long stopTime = System.currentTimeMillis();
			long runTime = stopTime - startTime;
			
			if(dump)
				n.dump("");
			  
			printResult(sourceFile.getPath(), "PASS", runTime);

		  } catch (ParseException e) {
				System.err.println("BAD PARSE IN " + sourceFile.getName());
				System.err.println(e.getMessage());
				throw new ShadowException(e.getMessage());
		  } catch (Error e) {
				System.err.println("Oops.");
				System.err.println(e.getMessage());
				e.printStackTrace(System.err);
				throw new ShadowException(e.getMessage());
		  } catch(FileNotFoundException e) {
				System.err.println(e.getMessage());
				throw new ShadowException(e.getMessage());
		  }
	}
}
