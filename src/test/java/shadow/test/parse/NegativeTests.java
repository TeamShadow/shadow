package shadow.test.parse;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import shadow.Main;
import shadow.parser.javacc.ParseException;

public class NegativeTests {

	private ArrayList<String> args = new ArrayList<String>();

	@Before
	public void setup() throws Exception {
		
		args.add("--typecheck");
		
		String os = System.getProperty("os.name").toLowerCase();
		
		if( os.contains("windows") ) {
			args.add("-c");
			args.add("windows.xml");
		}
		else if( os.contains("mac") ) {
			args.add("-c");
			args.add("mac.xml");
		}
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
	
	@Test public void testNewlineInString() throws Exception
	{
		args.add("tests-negative/parser/newline-in-string/Test.shadow");
		enforce();
	}

}
