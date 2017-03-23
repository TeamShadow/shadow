package shadow.test.output;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import shadow.CompileException;
import shadow.Main;

public class NegativeTests {

	private ArrayList<String> args = new ArrayList<String>();

	@Before
	public void setup() throws Exception {		
		String os = System.getProperty("os.name").toLowerCase();
		
		args.add("-c");
		if( os.contains("windows") )
			args.add("windows.xml");		
		else if( os.contains("mac") )
			args.add("mac.xml");
		else
			args.add("linux.xml");
	}
	
	private void enforce() throws Exception
	{
		try
		{
			Main.run(args.toArray(new String[] { }));
			throw new Exception("Test failed");
		}
		catch( CompileException e )
		{	
			System.err.println(e.getLocalizedMessage());
		}		
	}	
	
	@Test public void testConstantInitializationFailure() throws Exception
	{
		args.add("tests-negative/compile/constant-initialization-failure/Test.shadow");
		enforce();		
	}
	
	@Test public void testCircularConstantDependency() throws Exception
	{
		args.add("tests-negative/compile/circular-constant-dependency/Test.shadow");
		enforce();		
	}
	
	@Test public void testInvalidConstantDependency() throws Exception
	{
		args.add("tests-negative/compile/invalid-constant-dependency/Test.shadow");
		enforce();		
	}	
}
