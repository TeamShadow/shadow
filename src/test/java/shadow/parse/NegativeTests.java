package shadow.parse;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.junit.Before;
import org.junit.Test;

import shadow.Loggers;
import shadow.Main;
import shadow.parser.javacc.ParseException;

public class NegativeTests {

	private ArrayList<String> args = new ArrayList<String>();

	@Before
	public void setUp() throws Exception {
		args.add("--check");
		args.add("--config");

		String osName = System.getProperty("os.name");

		if(osName.startsWith("Windows")) {
			args.add("src/test_windows_config.xml");
		} else {
			args.add("src/test_linux_config.xml");
		}

		// set the levels of our loggers
		Loggers.SHADOW.setLevel(Level.DEBUG);
		Loggers.TYPE_CHECKER.setLevel(Level.INFO);
		Loggers.PARSER.setLevel(Level.INFO);
	}

	private void enforce() throws Exception
	{
		try
		{
			Main.run(args.toArray(new String[] { }));
			throw new Exception("Test failed");
		}
		catch( ParseException e )
		{}
	}


	@Test public void testEmptyStatement() throws Exception
	{
		args.add("tests-negative/parser/empty-statement/Test.shadow");
		enforce();
	}

	@Test public void testPlusPlus() throws Exception
	{
		args.add("tests-negative/parser/plus-plus/Test.shadow");
		enforce();
	}

	@Test public void testMemberVisibility() throws Exception
	{
		args.add("tests-negative/parser/member-visibility/Test.shadow");
		enforce();
	}

}
