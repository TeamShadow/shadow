package shadow.test.output;

import static junit.framework.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.junit.Before;
import org.junit.Test;

import shadow.Loggers;
import shadow.Main;

public class OutputTest {
	
	private ArrayList<String> args = new ArrayList<String>();

	@Before
	public void setUp() throws Exception {
		
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
	
	private void run(String[] programArgs, String expectedOutput) throws IOException {
		List<String> programCommand = new ArrayList<String>();
		String osName = System.getProperty("os.name");
		if(osName.startsWith("Windows")) {
			programCommand.add("a.exe");
		} else {
			programCommand.add("./a.out");
		}
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
		assertEquals(output, expectedOutput);
	}
	
	@Test public void testTest() throws Exception {
		args.add(0, "shadow/test/Test.shadow");
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
				"shall\n" + 
				"\n" + 
				"all\n" + 
				"good\n" + 
				"men\n" +
				"perish\n");
	}	
	
	@Test public void testArray() throws Exception {
		args.add("shadow/test/ArrayTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"[ 0, 1, 2, 3, 4 ]\n" +
				"[ 0, zero, one, two, shadow.standard@Object ]\n" + 
				"[ zero, one, two ]\n" +
				"[]\n" +
				"[ 0 ]\n" +
				"[ 0, 1 ]\n" +
				"[ 0, 1, 2 ]\n" +
				"[ 0, 1, 2, 3 ]\n" +
				"[ 0, 1, 2, 3, 4 ]\n" +
				"[]\n" +
				"[ 1 ]\n" +
				"[ 1, 2 ]\n" +
				"[ 1, 2, 3 ]\n" +
				"[ 1, 2, 3, 4 ]\n" +
				"[]\n" +
				"[ 2 ]\n" +
				"[ 2, 3 ]\n" +
				"[ 2, 3, 4 ]\n" +
				"[]\n" +
				"[ 3 ]\n" +
				"[ 3, 4 ]\n" +
				"[]\n" +
				"[ 4 ]\n" +
				"[]\n");
	}	
	
	@Test public void testChild() throws Exception {
		args.add("shadow/test/ChildTest.shadow");
		Main.run(args.toArray(new String[] { }));
		run(new String[0],
				"shadow.test@ParentTest:create(\"Hello World!\")\n" + 
				"shadow.test@ChildTest:main([])\n");
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
