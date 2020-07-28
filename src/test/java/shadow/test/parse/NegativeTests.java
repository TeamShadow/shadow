package shadow.test.parse;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import shadow.Main;
import shadow.parse.ParseException;
import shadow.parse.ParseException.Error;

public class NegativeTests {

	private ArrayList<String> args = new ArrayList<String>();

	@BeforeEach
	public void setup() throws Exception {
		
		args.add("--typecheck");
		
		String os = System.getProperty("os.name").toLowerCase();
		
		args.add("-c");
		if( os.contains("windows") )
			args.add("windows.xml");		
		else if( os.contains("mac") )
			args.add("mac.xml");
		else
			args.add("linux.xml");
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
		enforce(Error.EMPTY_STATEMENT);
	}
	
	@Test public void testSyntaxError() throws Exception
	{
		args.add("tests-negative/parser/syntax-error/Test.shadow");
		enforce(Error.SYNTAX_ERROR);
	}

	
	@Test public void testPrefixPlusPlus() throws Exception
	{
		args.add("tests-negative/parser/prefix-plus-plus/Test.shadow");
		enforce(Error.ILLEGAL_OPERATOR);
	}
	
	@Test public void testPostfixPlusPlus() throws Exception
	{
		args.add("tests-negative/parser/postfix-plus-plus/Test.shadow");
		enforce(Error.ILLEGAL_OPERATOR);
	}
	
	@Test public void testMemberVisibility() throws Exception
	{
		args.add("tests-negative/parser/member-visibility/Test.shadow");
		enforce(Error.ILLEGAL_MODIFIER);
	}
	
	@Test public void testMissingLeftBrace() throws Exception
	{
		args.add("tests-negative/parser/missing-left-brace/Test.shadow");
		enforce(Error.SYNTAX_ERROR);
	}
	
	@Test public void testMissingRightBrace() throws Exception
	{
		args.add("tests-negative/parser/missing-right-brace/Test.shadow");
		enforce(Error.SYNTAX_ERROR);
	}
	
	@Test public void testNewlineInString() throws Exception
	{
		args.add("tests-negative/parser/newline-in-string/Test.shadow");
		enforce(Error.NEWLINE_IN_STRING);
	}
	
	@Test public void testUnterminatedString() throws Exception
	{
		args.add("tests-negative/parser/unterminated-string/Test.shadow");
		enforce(Error.SYNTAX_ERROR);
	}

	@Test public void testAttributeInvocationFieldWithoutAssignment() throws Exception
	{
		args.add("tests-negative/parser/attribute-invocation-field-without-assignment/Test.shadow");
		enforce(Error.MISSING_ASSIGNMENT);
	}
}
