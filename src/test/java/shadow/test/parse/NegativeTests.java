package shadow.test.parse;

import java.util.ArrayList;

import org.apache.commons.logging.impl.SimpleLog;
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
		args.add("--typecheck");
		args.add("--config");

		String osName = System.getProperty("os.name");

		if(osName.startsWith("Windows"))
			args.add("shadow-windows-32.xml");
		else
			args.add("shadow-linux-64.xml");

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
