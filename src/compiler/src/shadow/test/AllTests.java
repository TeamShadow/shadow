package shadow.test;

import java.io.IOException;

import shadow.parser.javacc.ParseException;
import shadow.parser.test.ParserTest;
import shadow.typecheck.test.TypeCheckerTest;

public class AllTests {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException, ParseException {
		// add any new tests here
		ParserTest pt = new ParserTest(false, false);
		TypeCheckerTest tct = new TypeCheckerTest(false);

		pt.testAll();
		tct.testAll();
	}
}
