package shadow.test.output;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.junit.Before;
import org.junit.Test;

import shadow.Loggers;
import shadow.Main;

public class TACTest {
	
	private ArrayList<String> args = new ArrayList<String>();

	@Before
	public void setUp() throws Exception {
		//args.add("--check");
		args.add("--config");
		
		String osName = System.getProperty("os.name");
		
		if(osName.startsWith("Windows")) {
			args.add("src/test_windows_config.xml");
		} else {
			args.add("src/test_linux_config.xml");
		}

		// set the levels of our loggers
		Loggers.SHADOW.setLevel(Level.INFO);
		Loggers.TYPE_CHECKER.setLevel(Level.OFF);
		Loggers.PARSER.setLevel(Level.OFF);
	}	
		
	@Test public void testArrayList() throws Exception {
		args.add("shadow/test/ArrayListTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}	
	
	@Test public void testArray() throws Exception {
		args.add("shadow/test/ArrayTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}	
	
	@Test public void testChild() throws Exception {
		args.add("shadow/test/ChildTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testCommandLine() throws Exception {
		args.add("shadow/test/CommandLine.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testConsole() throws Exception {
		args.add("shadow/test/ConsoleTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testException() throws Exception {
		args.add("shadow/test/ExceptionTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testFile() throws Exception {
		args.add("shadow/test/FileTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testInterface() throws Exception {
		args.add("shadow/test/InterfaceTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testLoop() throws Exception {
		args.add("shadow/test/LoopTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testSort() throws Exception {
		args.add("shadow/test/SortMain.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testTest() throws Exception {
		args.add("shadow/test/Test.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testTry() throws Exception {
		args.add("shadow/test/TryTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testToughTry() throws Exception {
		args.add("shadow/test/ToughTry.shadow");
		Main.run(args.toArray(new String[] { }));
	}
}
