package shadow.test.doctool;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.Level;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shadow.Loggers;
import shadow.Main;

public class DocToolTest
{
	private ArrayList<String> args = new ArrayList<String>();
	
	@Before
	public void setup() throws Exception {
		
		// Set logger levels			
		Loggers.setAllToLevel(Level.INFO);
		
		args.add("-d");
		args.add("-v");
		
		if( System.getProperty("os.name").contains("Windows")) {
			args.add("-c");
			args.add("windows.xml");
		}
	}
	
	@After
	public void cleanup() throws IOException {}
	
	@Test 
	public void testTest() throws Exception 
	{
		args.add("src/test/resources/doctest/Test.shadow");
		Main.run(args.toArray(new String[] {}));
	}	
}
