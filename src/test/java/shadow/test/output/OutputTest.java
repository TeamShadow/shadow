package shadow.test.output;

import static junit.framework.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shadow.Configuration;
import shadow.ConfigurationException;
import shadow.Job;
import shadow.Loggers;
import shadow.Main;

public class OutputTest {
	
	// To simplify removal, every unit test executable will have the same name
	private static final String executableName = Job.properExecutableName("OutputTest");
	private static final Path executable = Paths.get("shadow", "test", executableName);

	private ArrayList<String> args = new ArrayList<String>();
	
	@Before
	public void setup() throws Exception {
		
		// Set logger levels			
		Loggers.SHADOW.setLevel(Level.INFO);
		Loggers.TYPE_CHECKER.setLevel(Level.OFF);
		Loggers.PARSER.setLevel(Level.OFF);
		
		args.add("-v");
		args.add("-o");
		args.add(executableName);
		
		if( System.getProperty("os.name").contains("Windows") ) {
			args.add("-c");
			args.add("windows.xml");
		}
	}
	
	@After
	public void cleanup() throws IOException {
		
		// Remove the unit test executable
		Files.delete(executable);
	}
	
	private void run(String[] programArgs, String expectedOutput) throws IOException, ConfigurationException, InterruptedException {
		
		// Should be initialized at this point by call to Main.run()
		Configuration config = Configuration.getConfiguration();
		
		Path fullExecutable = config.getSystemImport().resolve(executable);
		
		List<String> programCommand = new ArrayList<String>();
		programCommand.add(fullExecutable.toAbsolutePath().toString());
		
		for (String arg : programArgs)
			programCommand.add(arg);
		
		Process program = new ProcessBuilder(programCommand).start();
		BufferedReader reader = new BufferedReader(new InputStreamReader(program.getInputStream()));
		StringBuilder builder = new StringBuilder();
		String line;
		do {
			line = reader.readLine();
			if (line != null)
				builder.append(line).append('\n');
		} while (line != null);
		String output = builder.toString(); 
		assertEquals(expectedOutput, output);
		program.waitFor(); //keeps program from being deleted while running
	}
	
	@Test public void testTest() throws Exception {
		args.add("shadow/test/Test.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"true\n" + 
				"true\n" + 
				"false\n" + 
				"false\n" + 
				"true\n");
	}	
	
	@Test public void testArrayList() throws Exception {
		args.add("shadow/test/ArrayListTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[] {"all", "good", "men", "shall", "perish"},
			"true\n" +
			"true\n" +
			"false\n" +
			"all\n" +
			"men\n" +
			"shall\n" +
			"[all, men, shall]\n");
	}	
	
	@Test public void testArray() throws Exception {
		args.add("shadow/test/ArrayTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"[0, zero, one, two, 4]\n" +
				"[zero, one, two]\n" +
				"[]\n" +
				"[0]\n" +
				"[0, zero]\n" +
				"[0, zero, one]\n" +
				"[0, zero, one, two]\n" +
				"[0, zero, one, two, 4]\n" +
				"[]\n" +
				"[zero]\n" +
				"[zero, one]\n" +
				"[zero, one, two]\n" +
				"[zero, one, two, 4]\n" +
				"[]\n" +
				"[one]\n" +
				"[one, two]\n" +
				"[one, two, 4]\n" +
				"[]\n" +
				"[two]\n" +
				"[two, 4]\n" +
				"[]\n" +
				"[4]\n" +
				"[]\n");			
	}	
	
	@Test public void testChild() throws Exception {
		args.add("shadow/test/ChildTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"shadow:test@ParentTest:create(\"Hello World!\")\n" + 
				"shadow:test@ChildTest:main([])\n");
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
	
	@Test public void testTry() throws Exception {
		args.add("shadow/test/TryTest.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testToughTry() throws Exception {
		args.add("shadow/test/ToughTry.shadow");
		Main.run(args.toArray(new String[] { }));
	}
	
	@Test public void testPrimitive() throws Exception {
		args.add("shadow/test/PrimitiveTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"5\n" + 
				"5\n" + 
				"5\n" + 
				"5\n" + 
				"5\n" +
				"8\n");
	}
}
