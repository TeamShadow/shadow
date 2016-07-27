package shadow.test.parse;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import shadow.Main;
import shadow.parse.ParseException;
import shadow.parse.ParseException.Error;

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
	
	private void enforce(Error type) throws Exception
	{
		try
		{
			Main.run(args.toArray(new String[] { }));
			throw new Exception("Test failed");
		}
		catch( ParseException e )
		{
			if( !e.getError().equals(type) )
				throw new Exception("Test failed");			
		}
		catch( Exception e )
		{
			throw new Exception("Test failed");
		}
	}
	

	@Test public void testEmptyStatement() throws Exception
	{
		args.add("tests-negative/parser/empty-statement/Test.shadow");
		enforce(Error.EMPTY_STATMENT);
	}
	
	@Test public void testSyntaxError() throws Exception
	{
		args.add("tests-negative/parser/syntax-error/Test.shadow");
		enforce(Error.SYNTAX_ERROR);
	}

	/*	
	@Test public void testPlusPlus() throws Exception
	{
		args.add("tests-negative/parser/plus-plus/Test.shadow");
		enforce();
	}
	
	*/
	
	@Test public void testMemberVisibility() throws Exception
	{
		args.add("tests-negative/parser/member-visibility/Test.shadow");
		enforce(Error.ILLEGAL_MODIFIER);
	}
	
	/*
	@Test public void testNewlineInString() throws Exception
	{
		args.add("tests-negative/parser/newline-in-string/Test.shadow");
		enforce();
	}
	*/

}
