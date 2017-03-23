package shadow.test.typecheck;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import shadow.Main;
import shadow.typecheck.TypeCheckException;
import shadow.typecheck.TypeCheckException.Error;

public class WarningTests {

	private ArrayList<String> args = new ArrayList<String>();

	@Before
	public void setup() throws Exception {
		//args.add("-v");
		args.add("--typecheck");
		
		args.add("-w");
		args.add("error");

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
		catch( TypeCheckException e )
		{
			if( !e.getError().equals(type) )
				throw new Exception("Test failed");
		}		
	}
	
	@Test public void testPackage() throws Exception
	{
		args.add("tests-negative/warnings/package/Test1.shadow");
		enforce(Error.MISMATCHED_PACKAGE);		
	}
	
	
	@Test public void testFieldNotUsed() throws Exception
	{
		args.add("tests-negative/warnings/field-not-used/Test.shadow");
		enforce(Error.UNUSED_FIELD);		
	}
	
	@Test public void testPrivateMethodNotUsed() throws Exception
	{
		args.add("tests-negative/warnings/private-method-not-used/Test.shadow");
		enforce(Error.UNUSED_METHOD);		
	}
	
	@Test public void testVariableNotUsed() throws Exception
	{
		args.add("tests-negative/warnings/variable-not-used/Test.shadow");
		enforce(Error.UNUSED_VARIABLE);		
	}	
}
