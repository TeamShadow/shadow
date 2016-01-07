package shadow.test.output;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shadow.Job;
import shadow.Main;

public class TACTest {
	
	// To simplify removal, every unit test executable will have the same name
	private static final String executableName = Job.properExecutableName("TACTest");
	private static final Path executable = Paths.get("shadow", "test", executableName);
	
	private ArrayList<String> args = new ArrayList<String>();
	
	@Before
	public void setup() throws Exception {		
		args.add("-o");
		args.add(executableName);
		
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
	
	@After
	public void cleanup() throws IOException {
		
		// To to remove the unit test executable
		try {
			Files.delete(executable);
		}
		catch(Exception e) {}
	}

	@Test public void testCanCreate() throws Exception {
		args.add("shadow/test/CanCreateTest.shadow");
		Main.run(args.toArray(new String[] { }));
		// can't test output because of timing dependence		
	}
	
	@Test public void testCommandLine() throws Exception {
		args.add("shadow/test/CommandLine.shadow");
		Main.run(args.toArray(new String[] { }));
		// can't test because I/O is needed
	}
	
	@Test public void testConsole() throws Exception {
		args.add("shadow/test/ConsoleTest.shadow");
		Main.run(args.toArray(new String[] { }));	
		// can't test because I/O is needed
	}
	
	@Test public void testFile() throws Exception {
		args.add("shadow/test/FileTest.shadow");
		Main.run(args.toArray(new String[] { }));
		// can't test this without a file
	}

	@Test public void testSort() throws Exception {
		args.add("shadow/test/SortMain.shadow");
		Main.run(args.toArray(new String[] { }));
		// can't test because output is connected to timing
	}
	
	@Test public void testEcho() throws Exception {
		args.add("shadow/test/Echo.shadow");
		Main.run(args.toArray(new String[] { }));
		// can't test because I/O is needed
	}	
	
	@Test public void testDouble() throws Exception {
		args.add("shadow/test/DoubleTest.shadow");
		Main.run(args.toArray(new String[] { }));
		// can't test without more predictable floating point output
	}
	
	@Test public void testFloat() throws Exception {
		args.add("shadow/test/FloatTest.shadow");
		Main.run(args.toArray(new String[] { }));
		// can't test without more predictable floating point output
	}
	
	@Test public void testMath() throws Exception {
		args.add("shadow/test/MathTest.shadow");
		Main.run(args.toArray(new String[] { }));
		// can't test without more predictable floating point output
	}
	
	@Test public void testRandom() throws Exception {
		args.add("shadow/test/RandomTest.shadow");
		Main.run(args.toArray(new String[] { }));
		// can't test without more predictable floating point output
	}
	
	@Test public void testString() throws Exception {
		args.add("shadow/test/StringTest.shadow");
		Main.run(args.toArray(new String[] { }));
		// can't test without more predictable floating point output
	}	
}
