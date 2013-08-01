package shadow.test.typecheck;

import java.util.ArrayList;

import org.apache.commons.logging.impl.SimpleLog;
import org.apache.log4j.Level;
import org.junit.Before;
import org.junit.Test;

import shadow.Loggers;
import shadow.Main;
import shadow.TypeCheckException;
import shadow.TypeCheckException.Error;

public class NegativeTests {

	private ArrayList<String> args = new ArrayList<String>();

	@Before
	public void setUp() throws Exception {
		args.add("--check");
		args.add("--config");

		String osName = System.getProperty("os.name");

		if(osName.startsWith("Windows"))
			args.add("src/test_windows_config.xml");
		else
			args.add("src/test_linux_config.xml");		
		
		// set the levels of our loggers
		Loggers.SHADOW.setLevel(Level.DEBUG);
		Loggers.TYPE_CHECKER.setLevel(Level.INFO);
		Loggers.PARSER.setLevel(Level.INFO);
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
		args.add("tests-negative/typechecker/package/Test.shadow");
		enforce(Error.INVALID_PACKAGE);		
	}

}
