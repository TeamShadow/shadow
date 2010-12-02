package shadow.test;

import java.io.IOException;

import shadow.parser.javacc.ShadowException;
import shadow.parser.test.ParserTest;
import shadow.typecheck.test.TypeCheckerTest;
import shadow.TAC.test.TACTest;

public class AllTests {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException, ShadowException {
		// add any new tests here
		ParserTest pt = new ParserTest(false, true);	// dump = false, debug = true
		TypeCheckerTest tct = new TypeCheckerTest(false, true); // dump = false, debug = true
		//TACTest tact = new TACTest(true, true); // dump = false, debug = true

		pt.testAll();
		tct.testAll();
		//tact.testAll();
	}
}
