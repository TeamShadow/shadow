package shadow.test.doctool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.log4j.Level;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shadow.Job;
import shadow.Loggers;
import shadow.Main;

public class DocToolTest
{
	private ArrayList<String> args = new ArrayList<String>();
	
	@Before
	public void setup() throws Exception {
		
		// Set logger levels			
		Loggers.SHADOW.setLevel(Level.INFO);
		Loggers.TYPE_CHECKER.setLevel(Level.OFF);
		Loggers.PARSER.setLevel(Level.OFF);
		
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
