package shadow.test;

import java.io.IOException;

import shadow.TAC.test.TACTest;
import shadow.parser.javacc.ShadowException;
import shadow.typecheck.test.TypeCheckerTest;

public class AllTests {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException, ShadowException {
		// add any new tests here
		TypeCheckerTest tct = new TypeCheckerTest(false, true); // dump = false, debug = true
		TACTest tact = new TACTest(true, true); // dump = false, debug = true

		tct.testAll();
		tact.testAll();
	}
}
